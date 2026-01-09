package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InnovationTypeDtoTest {

    @Test
    void constructor_ShouldCreateDto() {
        // Act
        InnovationTypeDto dto = new InnovationTypeDto(
            1L, "Test Type", "Definition", false, 100L, "PRMS Name"
        );

        // Assert
        assertEquals(1L, dto.id());
        assertEquals("Test Type", dto.name());
        assertEquals("Definition", dto.definition());
        assertFalse(dto.isOldType());
        assertEquals(100L, dto.prmsIdEquivalent());
        assertEquals("PRMS Name", dto.prmsNameEquivalent());
    }

    @Test
    void constructor_WithNullValues_ShouldAccept() {
        // Act
        InnovationTypeDto dto = new InnovationTypeDto(
            null, null, null, null, null, null
        );

        // Assert
        assertNull(dto.id());
        assertNull(dto.name());
        assertNull(dto.definition());
        assertNull(dto.isOldType());
        assertNull(dto.prmsIdEquivalent());
        assertNull(dto.prmsNameEquivalent());
    }
}
