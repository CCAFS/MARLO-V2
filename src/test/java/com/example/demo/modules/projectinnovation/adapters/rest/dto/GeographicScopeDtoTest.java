package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeographicScopeDtoTest {

    @Test
    void constructor_ShouldCreateDto() {
        // Act
        GeographicScopeDto dto = new GeographicScopeDto(1L, "Global");

        // Assert
        assertEquals(1L, dto.id());
        assertEquals("Global", dto.name());
    }

    @Test
    void constructor_WithNullValues_ShouldAccept() {
        // Act
        GeographicScopeDto dto = new GeographicScopeDto(null, null);

        // Assert
        assertNull(dto.id());
        assertNull(dto.name());
    }
}
