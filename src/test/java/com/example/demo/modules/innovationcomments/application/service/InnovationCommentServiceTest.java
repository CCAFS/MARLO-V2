package com.example.demo.modules.innovationcomments.application.service;

import com.example.demo.modules.innovationcomments.domain.model.InnovationCatalogComment;
import com.example.demo.modules.innovationcomments.domain.port.out.InnovationCommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Unit tests for InnovationCommentService
 */
@ExtendWith(MockitoExtension.class)
class InnovationCommentServiceTest {
    
    @Mock
    private InnovationCommentRepository commentRepository;
    @Mock
    private CommentModerationService commentModerationService;
    
    @InjectMocks
    private InnovationCommentService commentService;
    
    private InnovationCatalogComment testCommentEntity;
    private Long testInnovationId;
    private String testUserName;
    private String testUserLastname;
    private String testUserEmail;
    private String testCommentText;
    
    @BeforeEach
    void setUp() {
        testInnovationId = 123L;
        testUserName = "John";
        testUserLastname = "Doe";
        testUserEmail = "john.doe@example.com";
        testCommentText = "This is a test comment";
        
        testCommentEntity = new InnovationCatalogComment(testInnovationId, testUserName, testUserLastname, testUserEmail, testCommentText);
        testCommentEntity.setId(1L);
        testCommentEntity.setActiveSince(LocalDateTime.now());
    }
    
    @Test
    void getActiveCommentsByInnovationId_Success() {
        // Arrange
        List<InnovationCatalogComment> expectedComments = Arrays.asList(testCommentEntity);
        when(commentRepository.findActiveCommentsByInnovationId(testInnovationId)).thenReturn(expectedComments);
        
        // Act
        List<InnovationCatalogComment> result = commentService.getActiveCommentsByInnovationId(testInnovationId);
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testCommentEntity.getId(), result.get(0).getId());
        verify(commentRepository, times(1)).findActiveCommentsByInnovationId(testInnovationId);
    }
    
    @Test
    void getActiveCommentsByInnovationId_NullInnovationId_ThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> commentService.getActiveCommentsByInnovationId(null));
        
        assertEquals("Innovation ID cannot be null", exception.getMessage());
        verify(commentRepository, never()).findActiveCommentsByInnovationId(any());
    }
    
    @Test
    void getActiveCommentsByInnovationIdOrderByCreatedAsc_Success() {
        // Arrange
        List<InnovationCatalogComment> expectedComments = Arrays.asList(testCommentEntity);
        when(commentRepository.findActiveCommentsByInnovationIdOrderByCreatedAsc(testInnovationId)).thenReturn(expectedComments);
        
        // Act
        List<InnovationCatalogComment> result = commentService.getActiveCommentsByInnovationIdOrderByCreatedAsc(testInnovationId);
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testCommentEntity.getId(), result.get(0).getId());
        verify(commentRepository, times(1)).findActiveCommentsByInnovationIdOrderByCreatedAsc(testInnovationId);
    }
    
    @Test
    void createComment_Success() {
        // Arrange
        when(commentRepository.save(any(InnovationCatalogComment.class))).thenReturn(testCommentEntity);

        // Act
        InnovationCatalogComment result = commentService.createComment(
            testInnovationId, testUserName, testUserLastname, testUserEmail, testCommentText);

        // Assert
        assertNotNull(result);
        assertEquals(testCommentEntity.getId(), result.getId());
        assertEquals(testCommentEntity.getInnovationId(), result.getInnovationId());
        assertEquals(testCommentEntity.getUserName(), result.getUserName());
        verify(commentRepository, times(1)).save(any(InnovationCatalogComment.class));
        verify(commentModerationService, times(1))
                .validateComment(testInnovationId, testUserEmail, testCommentText);
    }
    
    @Test
    void createComment_NullInnovationId_ThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> commentService.createComment(null, testUserName, testUserLastname, testUserEmail, testCommentText));
        
        assertEquals("Innovation ID cannot be null", exception.getMessage());
        verify(commentRepository, never()).save(any());
    }
    
    @Test
    void createComment_NullUserName_ThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> commentService.createComment(testInnovationId, null, testUserLastname, testUserEmail, testCommentText));
        
        assertEquals("User name cannot be null or empty", exception.getMessage());
        verify(commentRepository, never()).save(any());
    }
    
    @Test
    void createComment_EmptyUserName_ThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> commentService.createComment(testInnovationId, "", testUserLastname, testUserEmail, testCommentText));
        
        assertEquals("User name cannot be null or empty", exception.getMessage());
        verify(commentRepository, never()).save(any());
    }

    @Test
    void createComment_NullUserLastname_AllowsCreation() {
        // Arrange
        testCommentEntity.setUserLastname(null);
        when(commentRepository.save(any(InnovationCatalogComment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        InnovationCatalogComment result = commentService.createComment(
                testInnovationId, testUserName, null, testUserEmail, testCommentText);

        // Assert
        assertNotNull(result);
        assertNull(result.getUserLastname());
        verify(commentRepository, times(1)).save(any(InnovationCatalogComment.class));
    }
    
    @Test
    void createComment_InvalidEmail_ThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> commentService.createComment(testInnovationId, testUserName, testUserLastname, "invalid-email", testCommentText));
        
        assertEquals("Invalid email format", exception.getMessage());
        verify(commentRepository, never()).save(any());
    }
    
    @Test
    void createCommentWithAudit_Success() {
        // Arrange
        String createdBy = "admin";
        when(commentRepository.save(any(InnovationCatalogComment.class))).thenReturn(testCommentEntity);
        
        // Act
        InnovationCatalogComment result = commentService.createCommentWithAudit(
            testInnovationId, testUserName, testUserLastname, testUserEmail, testCommentText, createdBy);

        // Assert
        assertNotNull(result);
        assertEquals(testCommentEntity.getId(), result.getId());
        verify(commentRepository, times(1)).save(any(InnovationCatalogComment.class));
        verify(commentModerationService, times(1))
                .validateComment(testInnovationId, testUserEmail, testCommentText);
    }
    
    @Test
    void getActiveCommentsCount_Success() {
        // Arrange
        Long expectedCount = 5L;
        when(commentRepository.countActiveCommentsByInnovationId(testInnovationId)).thenReturn(expectedCount);
        
        // Act
        Long result = commentService.getActiveCommentsCount(testInnovationId);
        
        // Assert
        assertEquals(expectedCount, result);
        verify(commentRepository, times(1)).countActiveCommentsByInnovationId(testInnovationId);
    }
    
    @Test
    void getActiveCommentsByUserEmail_Success() {
        // Arrange
        List<InnovationCatalogComment> expectedComments = Arrays.asList(testCommentEntity);
        when(commentRepository.findActiveCommentsByUserEmail(testUserEmail)).thenReturn(expectedComments);
        
        // Act
        List<InnovationCatalogComment> result = commentService.getActiveCommentsByUserEmail(testUserEmail);
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(commentRepository, times(1)).findActiveCommentsByUserEmail(testUserEmail);
    }
    
    @Test
    void getActiveCommentsByUserEmail_NullEmail_ThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> commentService.getActiveCommentsByUserEmail(null));
        
        assertEquals("User email cannot be null or empty", exception.getMessage());
        verify(commentRepository, never()).findActiveCommentsByUserEmail(any());
    }
    
    @Test
    void getRecentActiveCommentsByInnovationId_Success() {
        // Arrange
        List<InnovationCatalogComment> expectedComments = Arrays.asList(testCommentEntity);
        when(commentRepository.findRecentActiveCommentsByInnovationId(testInnovationId)).thenReturn(expectedComments);
        
        // Act
        List<InnovationCatalogComment> result = commentService.getRecentActiveCommentsByInnovationId(testInnovationId);
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(commentRepository, times(1)).findRecentActiveCommentsByInnovationId(testInnovationId);
    }
    
    @Test
    void getAllComments_NoLimit_ReturnsAll() {
        // Arrange
        List<InnovationCatalogComment> expectedComments = Arrays.asList(testCommentEntity);
        when(commentRepository.findAllCommentsOrderByActiveSinceDesc(0, null)).thenReturn(expectedComments);
        
        // Act
        List<InnovationCatalogComment> result = commentService.getAllComments(null, null);
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(commentRepository, times(1)).findAllCommentsOrderByActiveSinceDesc(0, null);
    }
    
    @Test
    void getAllComments_WithLimit_ReturnsLimited() {
        // Arrange
        List<InnovationCatalogComment> expectedComments = Arrays.asList(testCommentEntity);
        when(commentRepository.findAllCommentsOrderByActiveSinceDesc(0, 5)).thenReturn(expectedComments);
        
        // Act
        List<InnovationCatalogComment> result = commentService.getAllComments(null, 5);
        
        // Assert
        assertNotNull(result);
        verify(commentRepository, times(1)).findAllCommentsOrderByActiveSinceDesc(0, 5);
    }
    
    @Test
    void getAllComments_InvalidLimit_ThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> commentService.getAllComments(null, 0));
        
        assertEquals("Limit must be greater than zero", exception.getMessage());
        verify(commentRepository, never()).findAllCommentsOrderByActiveSinceDesc(any(), any());
    }
    
    @Test
    void deactivateComment_Success() {
        // Arrange
        Long commentId = 1L;
        when(commentRepository.existsActiveComment(commentId)).thenReturn(true);
        when(commentRepository.softDeleteComment(commentId)).thenReturn(1);
        
        // Act
        boolean result = commentService.deactivateComment(commentId);
        
        // Assert
        assertTrue(result);
        verify(commentRepository, times(1)).existsActiveComment(commentId);
        verify(commentRepository, times(1)).softDeleteComment(commentId);
    }
    
    @Test
    void deactivateComment_CommentNotFound_ReturnsFalse() {
        // Arrange
        Long commentId = 1L;
        when(commentRepository.existsActiveComment(commentId)).thenReturn(false);
        
        // Act
        boolean result = commentService.deactivateComment(commentId);
        
        // Assert
        assertFalse(result);
        verify(commentRepository, times(1)).existsActiveComment(commentId);
        verify(commentRepository, never()).softDeleteComment(anyLong());
    }
    
    @Test
    void deactivateComment_NullCommentId_ThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> commentService.deactivateComment(null));
        
        assertEquals("Comment ID cannot be null", exception.getMessage());
        verify(commentRepository, never()).existsActiveComment(any());
        verify(commentRepository, never()).softDeleteComment(any());
    }
    
    @Test
    void isCommentActive_CommentExists_ReturnsTrue() {
        // Arrange
        Long commentId = 1L;
        when(commentRepository.existsActiveComment(commentId)).thenReturn(true);
        
        // Act
        boolean result = commentService.isCommentActive(commentId);
        
        // Assert
        assertTrue(result);
        verify(commentRepository, times(1)).existsActiveComment(commentId);
    }
    
    @Test
    void isCommentActive_CommentNotExists_ReturnsFalse() {
        // Arrange
        Long commentId = 1L;
        when(commentRepository.existsActiveComment(commentId)).thenReturn(false);
        
        // Act
        boolean result = commentService.isCommentActive(commentId);
        
        // Assert
        assertFalse(result);
        verify(commentRepository, times(1)).existsActiveComment(commentId);
    }
    
    @Test
    void isCommentActive_NullCommentId_ReturnsFalse() {
        // Act
        boolean result = commentService.isCommentActive(null);
        
        // Assert
        assertFalse(result);
        verify(commentRepository, never()).existsActiveComment(any());
    }

    @Test
    void getAllComments_WithNegativeOffset_ShouldUseZero() {
        // Arrange
        List<InnovationCatalogComment> expectedComments = Arrays.asList(testCommentEntity);
        when(commentRepository.findAllCommentsOrderByActiveSinceDesc(0, null)).thenReturn(expectedComments);
        
        // Act
        List<InnovationCatalogComment> result = commentService.getAllComments(-5, null);
        
        // Assert
        assertNotNull(result);
        verify(commentRepository, times(1)).findAllCommentsOrderByActiveSinceDesc(0, null);
    }

    @Test
    void getAllComments_WithPositiveOffset_ShouldUseOffset() {
        // Arrange
        List<InnovationCatalogComment> expectedComments = Arrays.asList(testCommentEntity);
        when(commentRepository.findAllCommentsOrderByActiveSinceDesc(10, null)).thenReturn(expectedComments);
        
        // Act
        List<InnovationCatalogComment> result = commentService.getAllComments(10, null);
        
        // Assert
        assertNotNull(result);
        verify(commentRepository, times(1)).findAllCommentsOrderByActiveSinceDesc(10, null);
    }

    @Test
    void getAllComments_WithZeroOffset_ShouldUseZero() {
        // Arrange
        List<InnovationCatalogComment> expectedComments = Arrays.asList(testCommentEntity);
        when(commentRepository.findAllCommentsOrderByActiveSinceDesc(0, 5)).thenReturn(expectedComments);
        
        // Act
        List<InnovationCatalogComment> result = commentService.getAllComments(0, 5);
        
        // Assert
        assertNotNull(result);
        verify(commentRepository, times(1)).findAllCommentsOrderByActiveSinceDesc(0, 5);
    }

    @Test
    void getAllComments_WithNegativeLimit_ThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> commentService.getAllComments(null, -1));
        
        assertEquals("Limit must be greater than zero", exception.getMessage());
        verify(commentRepository, never()).findAllCommentsOrderByActiveSinceDesc(any(), any());
    }

    @Test
    void createComment_WithEmptyEmail_ThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> commentService.createComment(testInnovationId, testUserName, testUserLastname, "", testCommentText));
        
        assertEquals("User email cannot be null or empty", exception.getMessage());
        verify(commentRepository, never()).save(any());
    }

    @Test
    void createComment_WithWhitespaceEmail_ThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> commentService.createComment(testInnovationId, testUserName, testUserLastname, "   ", testCommentText));
        
        assertEquals("User email cannot be null or empty", exception.getMessage());
        verify(commentRepository, never()).save(any());
    }

    @Test
    void createComment_WithWhitespaceUserName_ThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> commentService.createComment(testInnovationId, "   ", testUserLastname, testUserEmail, testCommentText));
        
        assertEquals("User name cannot be null or empty", exception.getMessage());
        verify(commentRepository, never()).save(any());
    }

    @Test
    void createComment_WithEmailMissingAt_ThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> commentService.createComment(testInnovationId, testUserName, testUserLastname, "invalidemail.com", testCommentText));
        
        assertEquals("Invalid email format", exception.getMessage());
        verify(commentRepository, never()).save(any());
    }

    @Test
    void createComment_WithEmailMissingDot_ThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> commentService.createComment(testInnovationId, testUserName, testUserLastname, "invalid@emailcom", testCommentText));
        
        assertEquals("Invalid email format", exception.getMessage());
        verify(commentRepository, never()).save(any());
    }

    @Test
    void createComment_WithEmailTooShort_ThrowsException() {
        // Act & Assert - email length <= 5
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> commentService.createComment(testInnovationId, testUserName, testUserLastname, "a@b.c", testCommentText));
        
        assertEquals("Invalid email format", exception.getMessage());
        verify(commentRepository, never()).save(any());
    }

    @Test
    void createComment_WithNullEmail_ThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> commentService.createComment(testInnovationId, testUserName, testUserLastname, null, testCommentText));
        
        assertEquals("User email cannot be null or empty", exception.getMessage());
        verify(commentRepository, never()).save(any());
    }

    @Test
    void getActiveCommentsByUserEmail_WithEmptyEmail_ThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> commentService.getActiveCommentsByUserEmail(""));
        
        assertEquals("User email cannot be null or empty", exception.getMessage());
        verify(commentRepository, never()).findActiveCommentsByUserEmail(any());
    }

    @Test
    void getActiveCommentsByUserEmail_WithWhitespaceEmail_ThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> commentService.getActiveCommentsByUserEmail("   "));
        
        assertEquals("User email cannot be null or empty", exception.getMessage());
        verify(commentRepository, never()).findActiveCommentsByUserEmail(any());
    }

    @Test
    void deactivateComment_WhenSoftDeleteReturnsZero_ShouldReturnFalse() {
        // Arrange
        Long commentId = 1L;
        when(commentRepository.existsActiveComment(commentId)).thenReturn(true);
        when(commentRepository.softDeleteComment(commentId)).thenReturn(0); // No rows updated
        
        // Act
        boolean result = commentService.deactivateComment(commentId);
        
        // Assert
        assertFalse(result);
        verify(commentRepository).existsActiveComment(commentId);
        verify(commentRepository).softDeleteComment(commentId);
    }

    @Test
    void createComment_WithValidEmailVariations_ShouldPass() {
        // Arrange
        when(commentRepository.save(any(InnovationCatalogComment.class))).thenReturn(testCommentEntity);
        
        // Act & Assert - Different valid email formats
        assertDoesNotThrow(() ->
            commentService.createComment(testInnovationId, testUserName, testUserLastname, "user@example.com", testCommentText)
        );
        assertDoesNotThrow(() ->
            commentService.createComment(testInnovationId, testUserName, testUserLastname, "user.name@example.co.uk", testCommentText)
        );
        assertDoesNotThrow(() ->
            commentService.createComment(testInnovationId, testUserName, testUserLastname, "user+tag@example.com", testCommentText)
        );
    }

    @Test
    void createComment_WithEmailExactlyFiveChars_ShouldReject() {
        // Act & Assert - email length exactly 5 (needs > 5)
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> commentService.createComment(testInnovationId, testUserName, testUserLastname, "a@b.c", testCommentText));
        
        assertEquals("Invalid email format", exception.getMessage());
    }

    @Test
    void createComment_WithEmailSixChars_ShouldPass() {
        // Arrange
        when(commentRepository.save(any(InnovationCatalogComment.class))).thenReturn(testCommentEntity);
        
        // Act & Assert - email length 6 (> 5)
        assertDoesNotThrow(() ->
            commentService.createComment(testInnovationId, testUserName, testUserLastname, "a@b.co", testCommentText)
        );
    }

    @Test
    void getAllComments_WithOffsetZero_ShouldUseZero() {
        // Arrange
        List<InnovationCatalogComment> expectedComments = Arrays.asList(testCommentEntity);
        when(commentRepository.findAllCommentsOrderByActiveSinceDesc(0, 10)).thenReturn(expectedComments);
        
        // Act
        List<InnovationCatalogComment> result = commentService.getAllComments(0, 10);
        
        // Assert
        assertNotNull(result);
        verify(commentRepository, times(1)).findAllCommentsOrderByActiveSinceDesc(0, 10);
    }

    @Test
    void getAllComments_WithOffsetPositive_ShouldUseOffset() {
        // Arrange
        List<InnovationCatalogComment> expectedComments = Arrays.asList(testCommentEntity);
        when(commentRepository.findAllCommentsOrderByActiveSinceDesc(20, 10)).thenReturn(expectedComments);
        
        // Act
        List<InnovationCatalogComment> result = commentService.getAllComments(20, 10);
        
        // Assert
        assertNotNull(result);
        verify(commentRepository, times(1)).findAllCommentsOrderByActiveSinceDesc(20, 10);
    }

    @Test
    void getAllComments_WithOffsetNull_ShouldUseZero() {
        // Arrange
        List<InnovationCatalogComment> expectedComments = Arrays.asList(testCommentEntity);
        when(commentRepository.findAllCommentsOrderByActiveSinceDesc(0, 10)).thenReturn(expectedComments);
        
        // Act
        List<InnovationCatalogComment> result = commentService.getAllComments(null, 10);
        
        // Assert
        assertNotNull(result);
        verify(commentRepository, times(1)).findAllCommentsOrderByActiveSinceDesc(0, 10);
    }
}
