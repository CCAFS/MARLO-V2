package com.example.demo.modules.innovationcomments.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InnovationCatalogCommentTest {

    private InnovationCatalogComment comment;

    @BeforeEach
    void setUp() {
        comment = new InnovationCatalogComment();
    }

    @Test
    void constructor_WithNoArgsConstructor_ShouldCreateEmpty() {
        // Act
        InnovationCatalogComment newComment = new InnovationCatalogComment();

        // Assert
        assertNotNull(newComment);
        assertTrue(newComment.getIsActive());
        assertNotNull(newComment.getActiveSince());
    }

    @Test
    void constructor_WithFourParams_ShouldSetValues() {
        // Act
        InnovationCatalogComment newComment = new InnovationCatalogComment(
            1L, "John", "Doe", "john@example.com"
        );

        // Assert
        assertEquals(1L, newComment.getInnovationId());
        assertEquals("John", newComment.getUserName());
        assertEquals("Doe", newComment.getUserLastname());
        assertEquals("john@example.com", newComment.getUserEmail());
        assertTrue(newComment.getIsActive());
        assertNotNull(newComment.getActiveSince());
    }

    @Test
    void constructor_WithFiveParams_ShouldSetComment() {
        // Act
        InnovationCatalogComment newComment = new InnovationCatalogComment(
            1L, "John", "Doe", "john@example.com", "Test comment"
        );

        // Assert
        assertEquals(1L, newComment.getInnovationId());
        assertEquals("Test comment", newComment.getComment());
    }

    @Test
    void onCreate_WhenActiveSinceIsNull_ShouldSetToNow() {
        // Arrange
        comment.setActiveSince(null);
        comment.setIsActive(null);

        // Act
        comment.onCreate();

        // Assert
        assertNotNull(comment.getActiveSince());
        assertTrue(comment.getIsActive());
    }

    @Test
    void onCreate_WhenIsActiveIsNull_ShouldSetToTrue() {
        // Arrange
        comment.setActiveSince(LocalDateTime.now());
        comment.setIsActive(null);

        // Act
        comment.onCreate();

        // Assert
        assertTrue(comment.getIsActive());
    }

    @Test
    void onUpdate_ShouldUpdateActiveSince() {
        // Arrange
        LocalDateTime originalDate = LocalDateTime.now().minusDays(1);
        comment.setActiveSince(originalDate);

        // Act
        comment.onUpdate();

        // Assert
        assertNotEquals(originalDate, comment.getActiveSince());
    }

    @Test
    void setIsActive_ShouldUpdateActiveSince() {
        // Arrange
        LocalDateTime originalDate = LocalDateTime.now().minusDays(1);
        comment.setActiveSince(originalDate);

        // Act
        comment.setIsActive(false);

        // Assert
        assertFalse(comment.getIsActive());
        assertNotEquals(originalDate, comment.getActiveSince());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Act
        comment.setId(1L);
        comment.setInnovationId(100L);
        comment.setUserName("John");
        comment.setUserLastname("Doe");
        comment.setUserEmail("john@example.com");
        comment.setComment("Test comment");
        comment.setIsActive(true);
        comment.setActiveSince(LocalDateTime.now());
        comment.setModificationJustification("Justification");

        // Assert
        assertEquals(1L, comment.getId());
        assertEquals(100L, comment.getInnovationId());
        assertEquals("John", comment.getUserName());
        assertEquals("Doe", comment.getUserLastname());
        assertEquals("john@example.com", comment.getUserEmail());
        assertEquals("Test comment", comment.getComment());
        assertTrue(comment.getIsActive());
        assertNotNull(comment.getActiveSince());
        assertEquals("Justification", comment.getModificationJustification());
    }

    @Test
    void toString_ShouldReturnStringRepresentation() {
        // Arrange
        comment.setId(1L);
        comment.setInnovationId(100L);
        comment.setUserName("John");
        comment.setUserLastname("Doe");
        comment.setUserEmail("john@example.com");
        comment.setIsActive(true);
        comment.setActiveSince(LocalDateTime.now());

        // Act
        String result = comment.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("InnovationCatalogComment"));
        assertTrue(result.contains("1"));
        assertTrue(result.contains("100"));
    }
}
