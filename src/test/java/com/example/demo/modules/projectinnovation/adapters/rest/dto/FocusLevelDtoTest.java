package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FocusLevelDtoTest {

    @Test
    void constructor_ShouldCreateDto() {
        // Act
        FocusLevelDto dto = new FocusLevelDto(1L, "Focus Level");

        // Assert
        assertEquals(1L, dto.id());
        assertEquals("Focus Level", dto.name());
    }

    @Test
    void constructor_WithNullValues_ShouldAccept() {
        // Act
        FocusLevelDto dto = new FocusLevelDto(null, null);

        // Assert
        assertNull(dto.id());
        assertNull(dto.name());
    }
}
