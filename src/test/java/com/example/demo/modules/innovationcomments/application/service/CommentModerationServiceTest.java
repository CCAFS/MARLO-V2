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
}
