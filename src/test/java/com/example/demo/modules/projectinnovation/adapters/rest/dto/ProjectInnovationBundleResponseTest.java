package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProjectInnovationBundleResponseTest {

    @Test
    void constructor_ShouldCreateDto() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        // Act
        ProjectInnovationBundleResponse response = new ProjectInnovationBundleResponse(
            1234L, 1566L, 2042L, "Climate-smart irrigation package", 6, 428L,
            true, now, 1001L, 1002L, "Justification"
        );

        // Assert
        assertEquals(1234L, response.id());
        assertEquals(1566L, response.projectInnovationId());
        assertEquals(2042L, response.selectedInnovationId());
        assertEquals("Climate-smart irrigation package", response.selectedInnovationName());
        assertEquals(6, response.selectedInnovationReadinessScale());
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
        ProjectInnovationBundleResponse response = new ProjectInnovationBundleResponse(
            null, null, null, null, null, null, null, null, null, null, null
        );

        // Assert
        assertNull(response.id());
        assertNull(response.projectInnovationId());
    }
}
