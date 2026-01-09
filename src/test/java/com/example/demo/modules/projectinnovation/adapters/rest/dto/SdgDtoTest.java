package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SdgDtoTest {

    @Test
    void constructor_ShouldCreateDto() {
        // Act
        SdgDto dto = new SdgDto(1L, "Zero Hunger", "End hunger, achieve food security");

        // Assert
        assertEquals(1L, dto.id());
        assertEquals("Zero Hunger", dto.shortName());
        assertEquals("End hunger, achieve food security", dto.fullName());
    }

    @Test
    void constructor_WithNullValues_ShouldAccept() {
        // Act
        SdgDto dto = new SdgDto(null, null, null);

        // Assert
        assertNull(dto.id());
        assertNull(dto.shortName());
        assertNull(dto.fullName());
    }
}
