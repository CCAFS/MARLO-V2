package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegionDtoTest {

    @Test
    void constructor_ShouldCreateDto() {
        // Act
        RegionDto dto = new RegionDto(1L, "Africa");

        // Assert
        assertEquals(1L, dto.id());
        assertEquals("Africa", dto.name());
    }

    @Test
    void constructor_WithNullValues_ShouldAccept() {
        // Act
        RegionDto dto = new RegionDto(null, null);

        // Assert
        assertNull(dto.id());
        assertNull(dto.name());
    }
}
