package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhaseResearchDtoTest {

    @Test
    void constructor_ShouldCreateDto() {
        // Act
        PhaseResearchDto dto = new PhaseResearchDto(1L, "Research Phase");

        // Assert
        assertEquals(1L, dto.id());
        assertEquals("Research Phase", dto.name());
    }

    @Test
    void constructor_WithNullValues_ShouldAccept() {
        // Act
        PhaseResearchDto dto = new PhaseResearchDto(null, null);

        // Assert
        assertNull(dto.id());
        assertNull(dto.name());
    }
}
