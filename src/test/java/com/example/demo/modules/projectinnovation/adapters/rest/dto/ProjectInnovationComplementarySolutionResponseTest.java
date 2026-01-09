package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProjectInnovationComplementarySolutionResponseTest {

    @Test
    void constructor_ShouldCreateDto() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        InnovationTypeDto innovationType = new InnovationTypeDto(5L, "Type Name", null, null, null, null);

        // Act
        ProjectInnovationComplementarySolutionResponse response = 
            new ProjectInnovationComplementarySolutionResponse(
                321L, "Solution Title", "Short Title", "Description", 5L, innovationType,
                428L, true, now, 1001L, 1002L, "Justification"
            );

        // Assert
        assertEquals(321L, response.id());
        assertEquals("Solution Title", response.title());
        assertEquals("Short Title", response.shortTitle());
        assertEquals("Description", response.shortDescription());
        assertEquals(5L, response.projectInnovationTypeId());
        assertEquals(innovationType, response.innovationType());
        assertEquals(428L, response.phaseId());
        assertTrue(response.isActive());
        assertEquals(now, response.activeSince());
        assertEquals(1001L, response.createdBy());
        assertEquals(1002L, response.modifiedBy());
        assertEquals("Justification", response.modificationJustification());
    }

    @Test
    void constructor_WithNullValues_ShouldAccept() {
        // Act
        ProjectInnovationComplementarySolutionResponse response = 
            new ProjectInnovationComplementarySolutionResponse(
                null, null, null, null, null, null, null, null, null, null, null, null
            );

        // Assert
        assertNull(response.id());
        assertNull(response.title());
    }
}
