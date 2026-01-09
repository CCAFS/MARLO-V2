package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProjectInnovationReferenceResponseTest {

    @Test
    void constructor_ShouldCreateDto() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        // Act
        ProjectInnovationReferenceResponse response = new ProjectInnovationReferenceResponse(
            1L, "Reference text", 428L, "https://example.com", false, true,
            987L, "Deliverable Name", "https://deliverable.com", 3L, now, 1001L, 1002L, "Justification"
        );

        // Assert
        assertEquals(1L, response.id());
        assertEquals("Reference text", response.reference());
        assertEquals(428L, response.phaseId());
        assertEquals("https://example.com", response.link());
        assertFalse(response.isExternalAuthor());
        assertTrue(response.hasEvidenceByDeliverable());
        assertEquals(987L, response.deliverableId());
        assertEquals("Deliverable Name", response.deliverableName());
        assertEquals(now, response.activeSince());
    }

    @Test
    void constructor_WithNullValues_ShouldAccept() {
        // Act
        ProjectInnovationReferenceResponse response = new ProjectInnovationReferenceResponse(
            null, null, null, null, null, null, null, null, null, null, null, null, null, null
        );

        // Assert
        assertNull(response.id());
        assertNull(response.reference());
    }
}
