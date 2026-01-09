package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectInnovationPartnershipResponseTest {

    @Test
    void constructor_ShouldCreateDto() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        List<ContactPersonResponse> contacts = Collections.emptyList();

        // Act
        ProjectInnovationPartnershipResponse response = new ProjectInnovationPartnershipResponse(
            1L, 100L, 66L, "International Livestock Research Institute", "ILRI",
            "https://www.ilri.org/", 428L, 1L, "Leading Partner", true, now, contacts
        );

        // Assert
        assertEquals(1L, response.id());
        assertEquals(100L, response.projectInnovationId());
        assertEquals(66L, response.institutionId());
        assertEquals("International Livestock Research Institute", response.institutionName());
        assertEquals("ILRI", response.institutionAcronym());
        assertEquals("https://www.ilri.org/", response.institutionWebsite());
        assertEquals(428L, response.idPhase());
        assertEquals(1L, response.innovationPartnerTypeId());
        assertEquals("Leading Partner", response.partnerTypeName());
        assertTrue(response.isActive());
        assertEquals(now, response.activeSince());
        assertEquals(contacts, response.contactPersons());
    }

    @Test
    void constructor_WithNullValues_ShouldAccept() {
        // Act
        ProjectInnovationPartnershipResponse response = new ProjectInnovationPartnershipResponse(
            null, null, null, null, null, null, null, null, null, null, null, null
        );

        // Assert
        assertNull(response.id());
        assertNull(response.projectInnovationId());
    }
}
