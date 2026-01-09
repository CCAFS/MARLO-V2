package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectInnovationContributingOrganizationResponseTest {

    @Test
    void constructor_ShouldCreateDto() {
        // Act
        ProjectInnovationContributingOrganizationResponse response = 
            new ProjectInnovationContributingOrganizationResponse(
                1L, 100L, 428L, 66L, "Test Institution", "TI", "Nairobi", "Kenya"
            );

        // Assert
        assertEquals(1L, response.id());
        assertEquals(100L, response.projectInnovationId());
        assertEquals(428L, response.idPhase());
        assertEquals(66L, response.institutionId());
        assertEquals("Test Institution", response.institutionName());
        assertEquals("TI", response.institutionAcronym());
        assertEquals("Nairobi", response.city());
        assertEquals("Kenya", response.headquarterLocationName());
    }

    @Test
    void constructor_WithNullValues_ShouldAccept() {
        // Act
        ProjectInnovationContributingOrganizationResponse response = 
            new ProjectInnovationContributingOrganizationResponse(
                null, null, null, null, null, null, null, null
            );

        // Assert
        assertNull(response.id());
        assertNull(response.projectInnovationId());
    }
}
