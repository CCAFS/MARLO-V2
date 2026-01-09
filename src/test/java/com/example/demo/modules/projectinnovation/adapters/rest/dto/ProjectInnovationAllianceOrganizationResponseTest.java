package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProjectInnovationAllianceOrganizationResponseTest {

    @Test
    void constructor_ShouldCreateDto() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        // Act
        ProjectInnovationAllianceOrganizationResponse response = 
            new ProjectInnovationAllianceOrganizationResponse(
                10L, 1566L, 428L, 2L, "Implementing Partner", "Alliance for Development",
                true, true, now, 100L, 101L, "Justification", 66L,
                "International Livestock Research Institute", "ILRI", 12
            );

        // Assert
        assertEquals(10L, response.id());
        assertEquals(1566L, response.projectInnovationId());
        assertEquals(428L, response.idPhase());
        assertEquals(2L, response.institutionTypeId());
        assertEquals("Implementing Partner", response.institutionTypeName());
        assertEquals("Alliance for Development", response.organizationName());
        assertTrue(response.isScalingPartner());
        assertTrue(response.isActive());
        assertEquals(now, response.activeSince());
        assertEquals(100L, response.createdBy());
        assertEquals(101L, response.modifiedBy());
        assertEquals("Justification", response.modificationJustification());
        assertEquals(66L, response.institutionId());
        assertEquals("International Livestock Research Institute", response.institutionName());
        assertEquals("ILRI", response.institutionAcronym());
        assertEquals(12, response.number());
    }

    @Test
    void constructor_WithNullValues_ShouldAccept() {
        // Act
        ProjectInnovationAllianceOrganizationResponse response = 
            new ProjectInnovationAllianceOrganizationResponse(
                null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null
            );

        // Assert
        assertNull(response.id());
        assertNull(response.projectInnovationId());
    }
}
