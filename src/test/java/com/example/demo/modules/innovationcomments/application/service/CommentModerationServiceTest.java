package com.example.demo.modules.innovationcomments.application.service;

import com.example.demo.modules.innovationcomments.application.exception.CommentRejectedException;
import com.example.demo.platform.config.CommentModerationProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentModerationServiceTest {

    private CommentModerationProperties properties;
    private ObjectProvider<CommentAiModerationClient> aiClientProvider;
    private CommentModerationService service;

    @BeforeEach
    void setUp() {
        properties = mock(CommentModerationProperties.class);
        aiClientProvider = mock(ObjectProvider.class);
        
        when(properties.isEnabled()).thenReturn(true);
        when(properties.getMaxAllowedLinks()).thenReturn(3);
        when(properties.getMaxRepeatedCharacters()).thenReturn(5);
        when(properties.getMaxRepeatedWords()).thenReturn(3);
        when(properties.getMaxUppercaseRatio()).thenReturn(0.8);
        when(properties.getExtraBannedWords()).thenReturn(Collections.emptyList());
        when(properties.isLogRejections()).thenReturn(true);
        when(aiClientProvider.getIfAvailable()).thenReturn(null);
        
        CommentModerationProperties.OpenAiProperties openAiProps = mock(CommentModerationProperties.OpenAiProperties.class);
        when(properties.getOpenAi()).thenReturn(openAiProps);
        
        service = new CommentModerationService(properties, aiClientProvider);
    }

    @Test
    void validateComment_WhenDisabled_ShouldNotValidate() {
        // Arrange
        when(properties.isEnabled()).thenReturn(false);

        // Act & Assert - should not throw
        assertDoesNotThrow(() -> 
            service.validateComment(1L, "test@example.com", "any comment")
        );
    }

    @Test
    void validateComment_WhenEmpty_ShouldReject() {
        // Act & Assert
        CommentRejectedException exception = assertThrows(CommentRejectedException.class, () ->
            service.validateComment(1L, "test@example.com", "")
        );
        assertEquals("EMPTY_COMMENT", exception.getReasonCode());
    }

    @Test
    void validateComment_WhenNull_ShouldReject() {
        // Act & Assert
        CommentRejectedException exception = assertThrows(CommentRejectedException.class, () ->
            service.validateComment(1L, "test@example.com", null)
        );
        assertEquals("EMPTY_COMMENT", exception.getReasonCode());
    }

    @Test
    void validateComment_WhenWhitespaceOnly_ShouldReject() {
        // Act & Assert
        CommentRejectedException exception = assertThrows(CommentRejectedException.class, () ->
            service.validateComment(1L, "test@example.com", "   ")
        );
        assertEquals("EMPTY_COMMENT", exception.getReasonCode());
    }

    @Test
    void validateComment_WithValidComment_ShouldPass() {
        // Act & Assert
        assertDoesNotThrow(() ->
            service.validateComment(1L, "test@example.com", "This is a valid comment")
        );
    }

    @Test
    void validateComment_WithCustomBannedWord_ShouldReject() {
        // Arrange
        when(properties.getExtraBannedWords()).thenReturn(List.of("spam", "badword"));

        CommentModerationService newService = new CommentModerationService(properties, aiClientProvider);

        // Act & Assert
        CommentRejectedException exception = assertThrows(CommentRejectedException.class, () ->
            newService.validateComment(1L, "test@example.com", "This contains spam word")
        );
        assertEquals("CUSTOM_BANNED_WORD", exception.getReasonCode());
    }

    @Test
    void validateComment_WithAiEnabled_ShouldStillApplyLocalRules() {
        // Arrange
        CommentAiModerationClient aiClient = mock(CommentAiModerationClient.class);
        when(aiClientProvider.getIfAvailable()).thenReturn(aiClient);

        CommentModerationProperties.OpenAiProperties openAiProps = mock(CommentModerationProperties.OpenAiProperties.class);
        when(openAiProps.isEnabled()).thenReturn(true);
        when(openAiProps.getBlockThreshold()).thenReturn(0.9);
        when(properties.getOpenAi()).thenReturn(openAiProps);
        when(properties.getExtraBannedWords()).thenReturn(List.of("spam"));

        ModerationVerdict verdict = new ModerationVerdict(false, "hate", 0.1, "openai");
        when(aiClient.classify(anyString())).thenReturn(Optional.of(verdict));

        CommentModerationService newService = new CommentModerationService(properties, aiClientProvider);

        // Act & Assert
        CommentRejectedException exception = assertThrows(CommentRejectedException.class, () ->
            newService.validateComment(1L, "test@example.com", "spam")
        );
        assertEquals("CUSTOM_BANNED_WORD", exception.getReasonCode());
        verify(aiClient).classify(anyString());
    }

    @Test
    void validateComment_WithExcessiveLinks_ShouldReject() {
        // Arrange
        String commentWithLinks = "Check http://link1.com and https://link2.com and www.link3.com and http://link4.com";

        // Act & Assert
        CommentRejectedException exception = assertThrows(CommentRejectedException.class, () ->
            service.validateComment(1L, "test@example.com", commentWithLinks)
        );
        assertEquals("EXCESSIVE_LINKS", exception.getReasonCode());
    }

    @Test
    void validateComment_WithAllowedLinks_ShouldPass() {
        // Arrange
        String commentWithLinks = "Check http://link1.com and https://link2.com";

        // Act & Assert
        assertDoesNotThrow(() ->
            service.validateComment(1L, "test@example.com", commentWithLinks)
        );
    }

    @Test
    void validateComment_WithExcessiveRepeatedCharacters_ShouldReject() {
        // Arrange
        String commentWithRepeats = "This is aaaaaaa test";

        // Act & Assert
        CommentRejectedException exception = assertThrows(CommentRejectedException.class, () ->
            service.validateComment(1L, "test@example.com", commentWithRepeats)
        );
        assertEquals("REPEATED_CHARACTERS", exception.getReasonCode());
    }

    @Test
    void validateComment_WithAllowedRepeatedCharacters_ShouldPass() {
        // Arrange
        String commentWithRepeats = "This is aaaa test";

        // Act & Assert
        assertDoesNotThrow(() ->
            service.validateComment(1L, "test@example.com", commentWithRepeats)
        );
    }

    @Test
    void validateComment_WithExcessiveRepeatedWords_ShouldReject() {
        // Arrange
        String commentWithRepeats = "spam spam spam";
        when(properties.getMaxRepeatedWords()).thenReturn(2);

        // Act & Assert
        CommentRejectedException exception = assertThrows(CommentRejectedException.class, () ->
            service.validateComment(1L, "test@example.com", commentWithRepeats)
        );
        assertEquals("REPEATED_WORDS", exception.getReasonCode());
    }

    @Test
    void validateComment_WithRepeatedWordsAtLimit_ShouldPass() {
        // Arrange
        String commentWithRepeats = "spam spam";
        when(properties.getMaxRepeatedWords()).thenReturn(2);

        // Act & Assert
        assertDoesNotThrow(() ->
            service.validateComment(1L, "test@example.com", commentWithRepeats)
        );
    }

    @Test
    void validateComment_WithExcessiveUppercase_ShouldReject() {
        // Arrange
        String uppercaseComment = "THIS IS ALL UPPERCASE COMMENT";

        // Act & Assert
        CommentRejectedException exception = assertThrows(CommentRejectedException.class, () ->
            service.validateComment(1L, "test@example.com", uppercaseComment)
        );
        assertEquals("UPPERCASE_RATIO", exception.getReasonCode());
    }

    @Test
    void validateComment_WithNormalCase_ShouldPass() {
        // Arrange
        String normalComment = "This is a normal comment";

        // Act & Assert
        assertDoesNotThrow(() ->
            service.validateComment(1L, "test@example.com", normalComment)
        );
    }

    @Test
    void validateComment_WithAiModerationEnabled_ShouldCallAiClient() {
        // Arrange
        CommentAiModerationClient aiClient = mock(CommentAiModerationClient.class);
        when(aiClientProvider.getIfAvailable()).thenReturn(aiClient);
        
        CommentModerationProperties.OpenAiProperties openAiProps = mock(CommentModerationProperties.OpenAiProperties.class);
        when(openAiProps.isEnabled()).thenReturn(true);
        when(openAiProps.getBlockThreshold()).thenReturn(0.5);
        when(properties.getOpenAi()).thenReturn(openAiProps);
        
        ModerationVerdict verdict = new ModerationVerdict(true, "hate", 0.8, "openai");
        when(aiClient.classify(anyString())).thenReturn(Optional.of(verdict));

        CommentModerationService newService = new CommentModerationService(properties, aiClientProvider);

        // Act & Assert
        CommentRejectedException exception = assertThrows(CommentRejectedException.class, () ->
            newService.validateComment(1L, "test@example.com", "test comment")
        );
        assertTrue(exception.getReasonCode().startsWith("AI_BLOCK_"));
        verify(aiClient).classify(anyString());
    }

    @Test
    void validateComment_WithAiRejection_ShouldPrefixUserMessage() {
        // Arrange
        CommentAiModerationClient aiClient = mock(CommentAiModerationClient.class);
        when(aiClientProvider.getIfAvailable()).thenReturn(aiClient);

        CommentModerationProperties.OpenAiProperties openAiProps = mock(CommentModerationProperties.OpenAiProperties.class);
        when(openAiProps.isEnabled()).thenReturn(true);
        when(openAiProps.getBlockThreshold()).thenReturn(0.5);
        when(properties.getOpenAi()).thenReturn(openAiProps);

        ModerationVerdict verdict = new ModerationVerdict(true, "hate", 0.8, "openai");
        when(aiClient.classify(anyString())).thenReturn(Optional.of(verdict));

        CommentModerationService newService = new CommentModerationService(properties, aiClientProvider);

        // Act & Assert
        CommentRejectedException exception = assertThrows(CommentRejectedException.class, () ->
            newService.validateComment(1L, "test@example.com", "test comment")
        );
        assertEquals("TYPE: HATE COMMENT. The comment was rejected because it violates the platform guidelines.",
                exception.getUserMessage());
    }

    @Test
    void validateComment_WithAiModerationNotFlagged_ShouldPass() {
        // Arrange
        CommentAiModerationClient aiClient = mock(CommentAiModerationClient.class);
        when(aiClientProvider.getIfAvailable()).thenReturn(aiClient);
        
        CommentModerationProperties.OpenAiProperties openAiProps = mock(CommentModerationProperties.OpenAiProperties.class);
        when(openAiProps.isEnabled()).thenReturn(true);
        when(openAiProps.getBlockThreshold()).thenReturn(0.5);
        when(properties.getOpenAi()).thenReturn(openAiProps);
        
        ModerationVerdict verdict = new ModerationVerdict(false, "hate", 0.3, "openai");
        when(aiClient.classify(anyString())).thenReturn(Optional.of(verdict));

        CommentModerationService newService = new CommentModerationService(properties, aiClientProvider);

        // Act & Assert
        assertDoesNotThrow(() ->
            newService.validateComment(1L, "test@example.com", "test comment")
        );
    }

    @Test
    void validateComment_WithAiModerationClientNull_ShouldNotCallAi() {
        // Arrange - AI client is null
        when(aiClientProvider.getIfAvailable()).thenReturn(null);
        
        CommentModerationService newService = new CommentModerationService(properties, aiClientProvider);

        // Act & Assert - should pass without AI check
        assertDoesNotThrow(() ->
            newService.validateComment(1L, "test@example.com", "test comment")
        );
    }

    @Test
    void validateComment_WithAiModerationDisabled_ShouldNotCallAi() {
        // Arrange
        CommentAiModerationClient aiClient = mock(CommentAiModerationClient.class);
        when(aiClientProvider.getIfAvailable()).thenReturn(aiClient);
        
        CommentModerationProperties.OpenAiProperties openAiProps = mock(CommentModerationProperties.OpenAiProperties.class);
        when(openAiProps.isEnabled()).thenReturn(false); // Disabled
        when(properties.getOpenAi()).thenReturn(openAiProps);
        
        CommentModerationService newService = new CommentModerationService(properties, aiClientProvider);

        // Act & Assert
        assertDoesNotThrow(() ->
            newService.validateComment(1L, "test@example.com", "test comment")
        );
        verify(aiClient, never()).classify(anyString());
    }

    @Test
    void validateComment_WithAiModerationOpenAiPropsNull_ShouldNotCallAi() {
        // Arrange
        CommentAiModerationClient aiClient = mock(CommentAiModerationClient.class);
        when(aiClientProvider.getIfAvailable()).thenReturn(aiClient);
        when(properties.getOpenAi()).thenReturn(null); // OpenAi props is null
        
        CommentModerationService newService = new CommentModerationService(properties, aiClientProvider);

        // Act & Assert
        assertDoesNotThrow(() ->
            newService.validateComment(1L, "test@example.com", "test comment")
        );
        verify(aiClient, never()).classify(anyString());
    }

    @Test
    void validateComment_WithAiModerationVerdictEmpty_ShouldPass() {
        // Arrange
        CommentAiModerationClient aiClient = mock(CommentAiModerationClient.class);
        when(aiClientProvider.getIfAvailable()).thenReturn(aiClient);
        
        CommentModerationProperties.OpenAiProperties openAiProps = mock(CommentModerationProperties.OpenAiProperties.class);
        when(openAiProps.isEnabled()).thenReturn(true);
        when(properties.getOpenAi()).thenReturn(openAiProps);
        
        when(aiClient.classify(anyString())).thenReturn(Optional.empty()); // Empty verdict

        CommentModerationService newService = new CommentModerationService(properties, aiClientProvider);

        // Act & Assert
        assertDoesNotThrow(() ->
            newService.validateComment(1L, "test@example.com", "test comment")
        );
    }

    @Test
    void validateComment_WithAiModerationScoreBelowThreshold_ShouldPass() {
        // Arrange
        CommentAiModerationClient aiClient = mock(CommentAiModerationClient.class);
        when(aiClientProvider.getIfAvailable()).thenReturn(aiClient);
        
        CommentModerationProperties.OpenAiProperties openAiProps = mock(CommentModerationProperties.OpenAiProperties.class);
        when(openAiProps.isEnabled()).thenReturn(true);
        when(openAiProps.getBlockThreshold()).thenReturn(0.8); // High threshold
        when(properties.getOpenAi()).thenReturn(openAiProps);
        
        ModerationVerdict verdict = new ModerationVerdict(false, "hate", 0.5, "openai"); // Score below threshold
        when(aiClient.classify(anyString())).thenReturn(Optional.of(verdict));

        CommentModerationService newService = new CommentModerationService(properties, aiClientProvider);

        // Act & Assert
        assertDoesNotThrow(() ->
            newService.validateComment(1L, "test@example.com", "test comment")
        );
    }

    @Test
    void validateComment_WithAiModerationScoreAtThreshold_ShouldReject() {
        // Arrange
        CommentAiModerationClient aiClient = mock(CommentAiModerationClient.class);
        when(aiClientProvider.getIfAvailable()).thenReturn(aiClient);
        
        CommentModerationProperties.OpenAiProperties openAiProps = mock(CommentModerationProperties.OpenAiProperties.class);
        when(openAiProps.isEnabled()).thenReturn(true);
        when(openAiProps.getBlockThreshold()).thenReturn(0.5);
        when(properties.getOpenAi()).thenReturn(openAiProps);
        
        ModerationVerdict verdict = new ModerationVerdict(false, "hate", 0.5, "openai"); // Score equals threshold
        when(aiClient.classify(anyString())).thenReturn(Optional.of(verdict));

        CommentModerationService newService = new CommentModerationService(properties, aiClientProvider);

        // Act & Assert
        CommentRejectedException exception = assertThrows(CommentRejectedException.class, () ->
            newService.validateComment(1L, "test@example.com", "test comment")
        );
        assertTrue(exception.getReasonCode().startsWith("AI_BLOCK_"));
    }

    @Test
    void validateComment_WithLogRejectionsDisabled_ShouldNotLog() {
        // Arrange
        when(properties.isLogRejections()).thenReturn(false);
        
        CommentModerationService newService = new CommentModerationService(properties, aiClientProvider);

        // Act & Assert - should still reject but not log
        CommentRejectedException exception = assertThrows(CommentRejectedException.class, () ->
            newService.validateComment(1L, "test@example.com", "")
        );
        assertEquals("EMPTY_COMMENT", exception.getReasonCode());
    }

    @Test
    void validateComment_WithCustomBannedWordEmptyList_ShouldPass() {
        // Arrange
        when(properties.getExtraBannedWords()).thenReturn(Collections.emptyList());
        
        CommentModerationService newService = new CommentModerationService(properties, aiClientProvider);

        // Act & Assert
        assertDoesNotThrow(() ->
            newService.validateComment(1L, "test@example.com", "This is a normal comment")
        );
    }

    @Test
    void validateComment_WithCustomBannedWordNullList_ShouldPass() {
        // Arrange
        when(properties.getExtraBannedWords()).thenReturn(null);
        
        CommentModerationService newService = new CommentModerationService(properties, aiClientProvider);

        // Act & Assert
        assertDoesNotThrow(() ->
            newService.validateComment(1L, "test@example.com", "This is a normal comment")
        );
    }

    @Test
    void validateComment_WithLinksAtLimit_ShouldPass() {
        // Arrange - exactly at limit (3 links)
        String commentWithLinks = "Check http://link1.com and https://link2.com and www.link3.com";
        when(properties.getMaxAllowedLinks()).thenReturn(3);

        // Act & Assert
        assertDoesNotThrow(() ->
            service.validateComment(1L, "test@example.com", commentWithLinks)
        );
    }

    @Test
    void validateComment_WithRepeatedCharactersAtLimit_ShouldPass() {
        // Arrange - exactly at limit (5 repeated characters)
        String commentWithRepeats = "This is aaaaa test";
        when(properties.getMaxRepeatedCharacters()).thenReturn(5);

        // Act & Assert
        assertDoesNotThrow(() ->
            service.validateComment(1L, "test@example.com", commentWithRepeats)
        );
    }

    @Test
    void validateComment_WithUppercaseRatioAtLimit_ShouldReject() {
        // Arrange - exactly at limit (80% uppercase) - should reject because condition is >
        String uppercaseComment = "THIS IS TEST";
        when(properties.getMaxUppercaseRatio()).thenReturn(0.8);

        // Act & Assert - Should reject because ratio > threshold
        CommentRejectedException exception = assertThrows(CommentRejectedException.class, () ->
            service.validateComment(1L, "test@example.com", uppercaseComment)
        );
        assertEquals("UPPERCASE_RATIO", exception.getReasonCode());
    }

    @Test
    void validateComment_WithUppercaseRatioJustBelowLimit_ShouldPass() {
        // Arrange - just below limit
        String mixedCaseComment = "This Is A Test"; // Lower ratio
        when(properties.getMaxUppercaseRatio()).thenReturn(0.8);

        // Act & Assert
        assertDoesNotThrow(() ->
            service.validateComment(1L, "test@example.com", mixedCaseComment)
        );
    }

    @Test
    void validateComment_WithUppercaseRatioBelowLimit_ShouldPass() {
        // Arrange - below limit
        String mixedCaseComment = "This Is A Test";
        when(properties.getMaxUppercaseRatio()).thenReturn(0.8);

        // Act & Assert
        assertDoesNotThrow(() ->
            service.validateComment(1L, "test@example.com", mixedCaseComment)
        );
    }

    @Test
    void validateComment_WithNoLetters_ShouldPass() {
        // Arrange - comment with only numbers and symbols
        String noLettersComment = "123 !@# $%^";
        when(properties.getMaxUppercaseRatio()).thenReturn(0.8);

        // Act & Assert
        assertDoesNotThrow(() ->
            service.validateComment(1L, "test@example.com", noLettersComment)
        );
    }

    @Test
    void validateComment_WithThresholdZero_ShouldNotCheckRepeats() {
        // Arrange - threshold is 0, should not check for repeats
        String commentWithRepeats = "This is aaaaaaa test";
        when(properties.getMaxRepeatedCharacters()).thenReturn(0);

        // Act & Assert - should pass because threshold <= 0 means no check
        assertDoesNotThrow(() ->
            service.validateComment(1L, "test@example.com", commentWithRepeats)
        );
    }

    @Test
    void validateComment_WithNegativeThreshold_ShouldNotCheckRepeats() {
        // Arrange - threshold is negative
        String commentWithRepeats = "This is aaaaaaa test";
        when(properties.getMaxRepeatedCharacters()).thenReturn(-1);

        // Act & Assert - should pass because threshold <= 0 means no check
        assertDoesNotThrow(() ->
            service.validateComment(1L, "test@example.com", commentWithRepeats)
        );
    }

    @Test
    void validateComment_WithRepeatedCharactersButNotExcessive_ShouldPass() {
        // Arrange - repeated characters but below threshold
        String commentWithRepeats = "This is aaaa test";
        when(properties.getMaxRepeatedCharacters()).thenReturn(5);

        // Act & Assert
        assertDoesNotThrow(() ->
            service.validateComment(1L, "test@example.com", commentWithRepeats)
        );
    }

    @Test
    void validateComment_WithRepeatedNonLetterDigits_ShouldPass() {
        // Arrange - repeated digits (not letters)
        String commentWithRepeats = "This is 11111 test";
        when(properties.getMaxRepeatedCharacters()).thenReturn(5);

        // Act & Assert - digits don't count as excessive repeats
        assertDoesNotThrow(() ->
            service.validateComment(1L, "test@example.com", commentWithRepeats)
        );
    }

    @Test
    void validateComment_WithSingleCharacter_ShouldPass() {
        // Arrange - single character text
        String singleChar = "a";
        when(properties.getMaxRepeatedCharacters()).thenReturn(5);

        // Act & Assert
        assertDoesNotThrow(() ->
            service.validateComment(1L, "test@example.com", singleChar)
        );
    }

    @Test
    void validateComment_WithEmptyTextAfterTrim_ShouldReject() {
        // Act & Assert
        CommentRejectedException exception = assertThrows(CommentRejectedException.class, () ->
            service.validateComment(1L, "test@example.com", "   ")
        );
        assertEquals("EMPTY_COMMENT", exception.getReasonCode());
    }

    @Test
    void validateComment_WithCustomBannedWordInListButNotMatching_ShouldPass() {
        // Arrange
        when(properties.getExtraBannedWords()).thenReturn(List.of("spam", "badword"));
        
        CommentModerationService newService = new CommentModerationService(properties, aiClientProvider);

        // Act & Assert - word "spam" is in list but not found as whole word
        assertDoesNotThrow(() ->
            newService.validateComment(1L, "test@example.com", "This is a spamming test")
        );
    }

    @Test
    void validateComment_WithCustomBannedWordListContainingEmptyStrings_ShouldIgnoreEmpty() {
        // Arrange - list with empty strings should be ignored
        when(properties.getExtraBannedWords()).thenReturn(List.of("", "   ", "spam"));
        
        CommentModerationService newService = new CommentModerationService(properties, aiClientProvider);

        // Act & Assert - empty strings should be skipped
        CommentRejectedException exception = assertThrows(CommentRejectedException.class, () ->
            newService.validateComment(1L, "test@example.com", "This contains spam word")
        );
        assertEquals("CUSTOM_BANNED_WORD", exception.getReasonCode());
    }

    @Test
    void validateComment_WithLinksExactlyAtLimit_ShouldPass() {
        // Arrange - exactly at limit (3 links)
        String commentWithLinks = "Check http://link1.com and https://link2.com and www.link3.com";
        when(properties.getMaxAllowedLinks()).thenReturn(3);

        // Act & Assert
        assertDoesNotThrow(() ->
            service.validateComment(1L, "test@example.com", commentWithLinks)
        );
    }

    @Test
    void validateComment_WithLinksOneBelowLimit_ShouldPass() {
        // Arrange - one below limit
        String commentWithLinks = "Check http://link1.com and https://link2.com";
        when(properties.getMaxAllowedLinks()).thenReturn(3);

        // Act & Assert
        assertDoesNotThrow(() ->
            service.validateComment(1L, "test@example.com", commentWithLinks)
        );
    }

    @Test
    void validateComment_WithAiModerationScoreExactlyAtThreshold_ShouldReject() {
        // Arrange
        CommentAiModerationClient aiClient = mock(CommentAiModerationClient.class);
        when(aiClientProvider.getIfAvailable()).thenReturn(aiClient);
        
        CommentModerationProperties.OpenAiProperties openAiProps = mock(CommentModerationProperties.OpenAiProperties.class);
        when(openAiProps.isEnabled()).thenReturn(true);
        when(openAiProps.getBlockThreshold()).thenReturn(0.5);
        when(properties.getOpenAi()).thenReturn(openAiProps);
        
        ModerationVerdict verdict = new ModerationVerdict(false, "hate", 0.5, "openai"); // Score equals threshold
        when(aiClient.classify(anyString())).thenReturn(Optional.of(verdict));

        CommentModerationService newService = new CommentModerationService(properties, aiClientProvider);

        // Act & Assert - score >= threshold should reject
        CommentRejectedException exception = assertThrows(CommentRejectedException.class, () ->
            newService.validateComment(1L, "test@example.com", "test comment")
        );
        assertTrue(exception.getReasonCode().startsWith("AI_BLOCK_"));
    }

    @Test
    void validateComment_WithAiModerationFlaggedTrue_ShouldReject() {
        // Arrange
        CommentAiModerationClient aiClient = mock(CommentAiModerationClient.class);
        when(aiClientProvider.getIfAvailable()).thenReturn(aiClient);
        
        CommentModerationProperties.OpenAiProperties openAiProps = mock(CommentModerationProperties.OpenAiProperties.class);
        when(openAiProps.isEnabled()).thenReturn(true);
        when(openAiProps.getBlockThreshold()).thenReturn(0.9); // High threshold
        when(properties.getOpenAi()).thenReturn(openAiProps);
        
        ModerationVerdict verdict = new ModerationVerdict(true, "hate", 0.3, "openai"); // Flagged = true
        when(aiClient.classify(anyString())).thenReturn(Optional.of(verdict));

        CommentModerationService newService = new CommentModerationService(properties, aiClientProvider);

        // Act & Assert - flagged = true should reject regardless of score
        CommentRejectedException exception = assertThrows(CommentRejectedException.class, () ->
            newService.validateComment(1L, "test@example.com", "test comment")
        );
        assertTrue(exception.getReasonCode().startsWith("AI_BLOCK_"));
    }
}
