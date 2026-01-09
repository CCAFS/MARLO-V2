package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectInnovationRegionResponseTest {

    @Test
    void constructor_ShouldCreateDto() {
        // Act
        ProjectInnovationRegionResponse response = new ProjectInnovationRegionResponse(
            1, 100L, 5L, "Northern Africa", 428L
        );

        // Assert
        assertEquals(1, response.id());
        assertEquals(100L, response.projectInnovationId());
        assertEquals(5L, response.idRegion());
        assertEquals("Northern Africa", response.regionName());
        assertEquals(428L, response.idPhase());
    }

    @Test
    void constructor_WithNullValues_ShouldAccept() {
        // Act
        ProjectInnovationRegionResponse response = new ProjectInnovationRegionResponse(
            null, null, null, null, null
        );

        // Assert
        assertNull(response.id());
        assertNull(response.projectInnovationId());
    }
}
