package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContributionCrpDtoTest {

    @Test
    void constructor_ShouldCreateDto() {
        // Act
        ContributionCrpDto dto = new ContributionCrpDto(1L, "CRP Name");

        // Assert
        assertEquals(1L, dto.id());
        assertEquals("CRP Name", dto.name());
    }

    @Test
    void constructor_WithNullValues_ShouldAccept() {
        // Act
        ContributionCrpDto dto = new ContributionCrpDto(null, null);

        // Assert
        assertNull(dto.id());
        assertNull(dto.name());
    }
}
