package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryDtoTest {

    @Test
    void constructor_ShouldCreateDto() {
        // Act
        CountryDto dto = new CountryDto(1L, "Kenya");

        // Assert
        assertEquals(1L, dto.id());
        assertEquals("Kenya", dto.name());
    }

    @Test
    void constructor_WithNullValues_ShouldAccept() {
        // Act
        CountryDto dto = new CountryDto(null, null);

        // Assert
        assertNull(dto.id());
        assertNull(dto.name());
    }
}
