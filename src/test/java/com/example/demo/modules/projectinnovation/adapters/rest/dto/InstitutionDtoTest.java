package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InstitutionDtoTest {

    @Test
    void constructor_ShouldCreateDto() {
        // Act
        InstitutionDto dto = new InstitutionDto(1L, "Test Institution", "TI");

        // Assert
        assertEquals(1L, dto.id());
        assertEquals("Test Institution", dto.name());
        assertEquals("TI", dto.acronym());
    }

    @Test
    void constructor_WithNullValues_ShouldAccept() {
        // Act
        InstitutionDto dto = new InstitutionDto(null, null, null);

        // Assert
        assertNull(dto.id());
        assertNull(dto.name());
        assertNull(dto.acronym());
    }
}
