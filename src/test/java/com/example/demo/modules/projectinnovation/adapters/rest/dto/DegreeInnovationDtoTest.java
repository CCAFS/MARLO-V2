package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DegreeInnovationDtoTest {

    @Test
    void constructor_ShouldCreateDto() {
        // Act
        DegreeInnovationDto dto = new DegreeInnovationDto(1L, "High Degree");

        // Assert
        assertEquals(1L, dto.id());
        assertEquals("High Degree", dto.name());
    }

    @Test
    void constructor_WithNullValues_ShouldAccept() {
        // Act
        DegreeInnovationDto dto = new DegreeInnovationDto(null, null);

        // Assert
        assertNull(dto.id());
        assertNull(dto.name());
    }
}
