package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InnovationNatureDtoTest {

    @Test
    void constructor_ShouldCreateDto() {
        // Act
        InnovationNatureDto dto = new InnovationNatureDto(1L, "Nature Name");

        // Assert
        assertEquals(1L, dto.id());
        assertEquals("Nature Name", dto.name());
    }

    @Test
    void constructor_WithNullValues_ShouldAccept() {
        // Act
        InnovationNatureDto dto = new InnovationNatureDto(null, null);

        // Assert
        assertNull(dto.id());
        assertNull(dto.name());
    }
}
