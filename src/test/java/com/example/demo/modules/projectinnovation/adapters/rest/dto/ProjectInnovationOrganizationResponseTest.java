package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectInnovationOrganizationResponseTest {

    @Test
    void constructor_ShouldCreateDto() {
        // Act
        ProjectInnovationOrganizationResponse response = new ProjectInnovationOrganizationResponse(
            1L, 100L, 428L, 5L, "Research Organization"
        );

        // Assert
        assertEquals(1L, response.id());
        assertEquals(100L, response.projectInnovationId());
        assertEquals(428L, response.idPhase());
        assertEquals(5L, response.repIndOrganizationTypeId());
        assertEquals("Research Organization", response.organizationTypeName());
    }

    @Test
    void constructor_WithNullValues_ShouldAccept() {
        // Act
        ProjectInnovationOrganizationResponse response = new ProjectInnovationOrganizationResponse(
            null, null, null, null, null
        );

        // Assert
        assertNull(response.id());
        assertNull(response.projectInnovationId());
    }
}
