package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InnovationStageDtoTest {

    @Test
    void constructor_ShouldCreateDto() {
        // Act
        InnovationStageDto dto = new InnovationStageDto(1L, "Stage 1");

        // Assert
        assertEquals(1L, dto.id());
        assertEquals("Stage 1", dto.name());
    }

    @Test
    void constructor_WithNullValues_ShouldAccept() {
        // Act
        InnovationStageDto dto = new InnovationStageDto(null, null);

        // Assert
        assertNull(dto.id());
        assertNull(dto.name());
    }
}
