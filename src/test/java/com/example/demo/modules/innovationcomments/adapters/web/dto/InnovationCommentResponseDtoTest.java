package com.example.demo.modules.innovationcomments.adapters.web.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InnovationCommentResponseDtoTest {

    @Test
    void constructor_WithNoArgs_ShouldCreateEmptyObject() {
        // Act
        InnovationCommentResponseDto dto = new InnovationCommentResponseDto();

        // Assert
        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getInnovationId());
        assertNull(dto.getUserName());
        assertNull(dto.getUserLastname());
        assertNull(dto.getUserEmail());
        assertNull(dto.getComment());
        assertNull(dto.getInnovationName());
        assertNull(dto.getIsActive());
        assertNull(dto.getActiveSince());
        assertNull(dto.getModificationJustification());
    }

    @Test
    void constructor_WithAllArgs_ShouldSetAllFields() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        // Act
        InnovationCommentResponseDto dto = new InnovationCommentResponseDto(
                1L, 100L, "John", "Doe", "john@example.com",
                "Great innovation!", true, now, "Initial comment");

        // Assert
        assertEquals(1L, dto.getId());
        assertEquals(100L, dto.getInnovationId());
        assertEquals("John", dto.getUserName());
        assertEquals("Doe", dto.getUserLastname());
        assertEquals("john@example.com", dto.getUserEmail());
        assertEquals("Great innovation!", dto.getComment());
        assertNull(dto.getInnovationName()); // Set to null in constructor
        assertTrue(dto.getIsActive());
        assertEquals(now, dto.getActiveSince());
        assertEquals("Initial comment", dto.getModificationJustification());
    }

    @Test
    void settersAndGetters_Id_ShouldWorkCorrectly() {
        // Arrange
        InnovationCommentResponseDto dto = new InnovationCommentResponseDto();

        // Act
        dto.setId(1L);

        // Assert
        assertEquals(1L, dto.getId());
    }

    @Test
    void settersAndGetters_InnovationId_ShouldWorkCorrectly() {
        // Arrange
        InnovationCommentResponseDto dto = new InnovationCommentResponseDto();

        // Act
        dto.setInnovationId(100L);

        // Assert
        assertEquals(100L, dto.getInnovationId());
    }

    @Test
    void settersAndGetters_UserName_ShouldWorkCorrectly() {
        // Arrange
        InnovationCommentResponseDto dto = new InnovationCommentResponseDto();

        // Act
        dto.setUserName("Jane");

        // Assert
        assertEquals("Jane", dto.getUserName());
    }

    @Test
    void settersAndGetters_UserEmail_ShouldWorkCorrectly() {
        // Arrange
        InnovationCommentResponseDto dto = new InnovationCommentResponseDto();

        // Act
        dto.setUserEmail("jane@example.com");

        // Assert
        assertEquals("jane@example.com", dto.getUserEmail());
    }

    @Test
    void settersAndGetters_Comment_ShouldWorkCorrectly() {
        // Arrange
        InnovationCommentResponseDto dto = new InnovationCommentResponseDto();

        // Act
        dto.setComment("This is my comment");

        // Assert
        assertEquals("This is my comment", dto.getComment());
    }

    @Test
    void settersAndGetters_InnovationName_ShouldWorkCorrectly() {
        // Arrange
        InnovationCommentResponseDto dto = new InnovationCommentResponseDto();

        // Act
        dto.setInnovationName("Climate Innovation");

        // Assert
        assertEquals("Climate Innovation", dto.getInnovationName());
    }

    @Test
    void settersAndGetters_IsActive_ShouldWorkCorrectly() {
        // Arrange
        InnovationCommentResponseDto dto = new InnovationCommentResponseDto();

        // Act
        dto.setIsActive(true);

        // Assert
        assertTrue(dto.getIsActive());
    }

    @Test
    void settersAndGetters_IsActiveFalse_ShouldWorkCorrectly() {
        // Arrange
        InnovationCommentResponseDto dto = new InnovationCommentResponseDto();

        // Act
        dto.setIsActive(false);

        // Assert
        assertFalse(dto.getIsActive());
    }

    @Test
    void settersAndGetters_ActiveSince_ShouldWorkCorrectly() {
        // Arrange
        InnovationCommentResponseDto dto = new InnovationCommentResponseDto();
        LocalDateTime now = LocalDateTime.now();

        // Act
        dto.setActiveSince(now);

        // Assert
        assertEquals(now, dto.getActiveSince());
    }

    @Test
    void settersAndGetters_ModificationJustification_ShouldWorkCorrectly() {
        // Arrange
        InnovationCommentResponseDto dto = new InnovationCommentResponseDto();

        // Act
        dto.setModificationJustification("Updated for clarity");

        // Assert
        assertEquals("Updated for clarity", dto.getModificationJustification());
    }

    @Test
    void setNullValues_ShouldAcceptNulls() {
        // Arrange
        InnovationCommentResponseDto dto = new InnovationCommentResponseDto(
                1L, 100L, "John", "Doe", "john@example.com",
                "Comment", true, LocalDateTime.now(), "Justification");

        // Act
        dto.setId(null);
        dto.setInnovationId(null);
        dto.setUserName(null);
        dto.setUserLastname(null);
        dto.setUserEmail(null);
        dto.setComment(null);
        dto.setInnovationName(null);
        dto.setIsActive(null);
        dto.setActiveSince(null);
        dto.setModificationJustification(null);

        // Assert
        assertNull(dto.getId());
        assertNull(dto.getInnovationId());
        assertNull(dto.getUserName());
        assertNull(dto.getUserLastname());
        assertNull(dto.getUserEmail());
        assertNull(dto.getComment());
        assertNull(dto.getInnovationName());
        assertNull(dto.getIsActive());
        assertNull(dto.getActiveSince());
        assertNull(dto.getModificationJustification());
    }

    @Test
    void settersAndGetters_AllFields_ShouldWorkCorrectly() {
        // Arrange
        InnovationCommentResponseDto dto = new InnovationCommentResponseDto();
        LocalDateTime now = LocalDateTime.now();

        // Act
        dto.setId(1L);
        dto.setInnovationId(100L);
        dto.setUserName("John");
        dto.setUserLastname("Doe");
        dto.setUserEmail("john@test.com");
        dto.setComment("My comment");
        dto.setInnovationName("Innovation Name");
        dto.setIsActive(true);
        dto.setActiveSince(now);
        dto.setModificationJustification("Initial");

        // Assert
        assertEquals(1L, dto.getId());
        assertEquals(100L, dto.getInnovationId());
        assertEquals("John", dto.getUserName());
        assertEquals("Doe", dto.getUserLastname());
        assertEquals("john@test.com", dto.getUserEmail());
        assertEquals("My comment", dto.getComment());
        assertEquals("Innovation Name", dto.getInnovationName());
        assertTrue(dto.getIsActive());
        assertEquals(now, dto.getActiveSince());
        assertEquals("Initial", dto.getModificationJustification());
    }

    @Test
    void settersAndGetters_UserLastname_ShouldWorkCorrectly() {
        // Arrange
        InnovationCommentResponseDto dto = new InnovationCommentResponseDto();

        // Act
        dto.setUserLastname("Smith");

        // Assert
        assertEquals("Smith", dto.getUserLastname());
    }
}
