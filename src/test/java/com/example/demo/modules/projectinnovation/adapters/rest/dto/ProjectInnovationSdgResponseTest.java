package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectInnovationSdgResponseTest {

    @Test
    void constructor_ShouldCreateDto() {
        // Act
        ProjectInnovationSdgResponse response = new ProjectInnovationSdgResponse(
            1L, 100L, 13L, "SDG 13", "Climate Action", 428L, true
        );

        // Assert
        assertEquals(1L, response.id());
        assertEquals(100L, response.innovationId());
        assertEquals(13L, response.sdgId());
        assertEquals("SDG 13", response.sdgShortName());
        assertEquals("Climate Action", response.sdgFullName());
        assertEquals(428L, response.idPhase());
        assertTrue(response.isActive());
    }

    @Test
    void constructor_WithNullValues_ShouldAccept() {
        // Act
        ProjectInnovationSdgResponse response = new ProjectInnovationSdgResponse(
            null, null, null, null, null, null, null
        );

        // Assert
        assertNull(response.id());
        assertNull(response.innovationId());
        assertNull(response.sdgId());
    }
}
