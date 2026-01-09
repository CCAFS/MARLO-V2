package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhaseDtoTest {

    @Test
    void constructor_ShouldCreateDto() {
        // Act
        PhaseDto dto = new PhaseDto(1L, "Test Phase");

        // Assert
        assertEquals(1L, dto.id());
        assertEquals("Test Phase", dto.name());
    }

    @Test
    void constructor_WithNullValues_ShouldAccept() {
        // Act
        PhaseDto dto = new PhaseDto(null, null);

        // Assert
        assertNull(dto.id());
        assertNull(dto.name());
    }
}
