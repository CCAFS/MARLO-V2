package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImpactAreaScoreDtoTest {

    @Test
    void constructor_ShouldCreateDto() {
        // Act
        ImpactAreaScoreDto dto = new ImpactAreaScoreDto(1L, "Score Name", "Score Description");

        // Assert
        assertEquals(1L, dto.id());
        assertEquals("Score Name", dto.name());
        assertEquals("Score Description", dto.description());
    }

    @Test
    void constructor_WithNullValues_ShouldAccept() {
        // Act
        ImpactAreaScoreDto dto = new ImpactAreaScoreDto(null, null, null);

        // Assert
        assertNull(dto.id());
        assertNull(dto.name());
        assertNull(dto.description());
    }
}
