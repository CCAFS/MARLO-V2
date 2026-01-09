package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectInnovationCountryResponseTest {

    @Test
    void constructor_ShouldCreateDto() {
        // Act
        ProjectInnovationCountryResponse response = new ProjectInnovationCountryResponse(
            1, 100L, 113L, "Kenya", 428L
        );

        // Assert
        assertEquals(1, response.id());
        assertEquals(100L, response.projectInnovationId());
        assertEquals(113L, response.idCountry());
        assertEquals("Kenya", response.countryName());
        assertEquals(428L, response.idPhase());
    }

    @Test
    void constructor_WithNullValues_ShouldAccept() {
        // Act
        ProjectInnovationCountryResponse response = new ProjectInnovationCountryResponse(
            null, null, null, null, null
        );

        // Assert
        assertNull(response.id());
        assertNull(response.projectInnovationId());
    }
}
