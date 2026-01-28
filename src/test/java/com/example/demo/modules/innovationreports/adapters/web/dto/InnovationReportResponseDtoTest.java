package com.example.demo.modules.innovationreports.adapters.web.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InnovationReportResponseDtoTest {

    @Test
    void constructor_WithNoArgs_ShouldCreateEmptyObject() {
        // Act
        InnovationReportResponseDto dto = new InnovationReportResponseDto();

        // Assert
        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getInnovationId());
        assertNull(dto.getUserName());
        assertNull(dto.getUserLastname());
        assertNull(dto.getUserEmail());
        assertNull(dto.getInterestNarrative());
        assertNull(dto.getIsActive());
        assertNull(dto.getActiveSince());
        assertNull(dto.getModificationJustification());
    }

    @Test
    void constructor_WithAllArgs_ShouldSetAllFields() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        // Act
        InnovationReportResponseDto dto = new InnovationReportResponseDto(
                1L, 100L, "John", "Doe", "john@example.com",
                "Interested in this innovation", true, now, "Initial report");

        // Assert
        assertEquals(1L, dto.getId());
        assertEquals(100L, dto.getInnovationId());
        assertEquals("John", dto.getUserName());
        assertEquals("Doe", dto.getUserLastname());
        assertEquals("john@example.com", dto.getUserEmail());
        assertEquals("Interested in this innovation", dto.getInterestNarrative());
        assertTrue(dto.getIsActive());
        assertEquals(now, dto.getActiveSince());
        assertEquals("Initial report", dto.getModificationJustification());
    }

    @Test
    void getters_ShouldReturnCorrectValues() {
        // Arrange
        LocalDateTime testDate = LocalDateTime.of(2024, 6, 15, 10, 30);
        InnovationReportResponseDto dto = new InnovationReportResponseDto(
                5L, 200L, "Jane", "Smith", "jane@test.com",
                "Great innovation!", false, testDate, "Updated");

        // Assert
        assertEquals(5L, dto.getId());
        assertEquals(200L, dto.getInnovationId());
        assertEquals("Jane", dto.getUserName());
        assertEquals("Smith", dto.getUserLastname());
        assertEquals("jane@test.com", dto.getUserEmail());
        assertEquals("Great innovation!", dto.getInterestNarrative());
        assertFalse(dto.getIsActive());
        assertEquals(testDate, dto.getActiveSince());
        assertEquals("Updated", dto.getModificationJustification());
    }

    @Test
    void constructor_WithNullValues_ShouldAcceptNulls() {
        // Act
        InnovationReportResponseDto dto = new InnovationReportResponseDto(
                null, null, null, null, null, null, null, null, null);

        // Assert
        assertNull(dto.getId());
        assertNull(dto.getInnovationId());
        assertNull(dto.getUserName());
        assertNull(dto.getUserLastname());
        assertNull(dto.getUserEmail());
        assertNull(dto.getInterestNarrative());
        assertNull(dto.getIsActive());
        assertNull(dto.getActiveSince());
        assertNull(dto.getModificationJustification());
    }

    @Test
    void constructor_WithEmptyStrings_ShouldWork() {
        // Act
        InnovationReportResponseDto dto = new InnovationReportResponseDto(
                0L, 0L, "", "", "", "", true, LocalDateTime.now(), "");

        // Assert
        assertEquals(0L, dto.getId());
        assertEquals(0L, dto.getInnovationId());
        assertEquals("", dto.getUserName());
        assertEquals("", dto.getUserLastname());
        assertEquals("", dto.getUserEmail());
        assertEquals("", dto.getInterestNarrative());
        assertTrue(dto.getIsActive());
        assertNotNull(dto.getActiveSince());
        assertEquals("", dto.getModificationJustification());
    }

    @Test
    void getIsActive_WithFalse_ShouldReturnFalse() {
        // Arrange
        InnovationReportResponseDto dto = new InnovationReportResponseDto(
                1L, 100L, "Test", "User", "test@test.com",
                "Narrative", false, LocalDateTime.now(), null);

        // Assert
        assertFalse(dto.getIsActive());
    }

    @Test
    void getActiveSince_WithSpecificDate_ShouldReturnCorrectDate() {
        // Arrange
        LocalDateTime specificDate = LocalDateTime.of(2024, 12, 25, 14, 30, 0);
        InnovationReportResponseDto dto = new InnovationReportResponseDto(
                1L, 100L, "Test", "User", "test@test.com",
                "Narrative", true, specificDate, null);

        // Assert
        assertEquals(specificDate, dto.getActiveSince());
        assertEquals(2024, dto.getActiveSince().getYear());
        assertEquals(12, dto.getActiveSince().getMonthValue());
        assertEquals(25, dto.getActiveSince().getDayOfMonth());
    }
}
