package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ContactPersonResponseTest {

    @Test
    void constructor_ShouldCreateDto() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        // Act
        ContactPersonResponse response = new ContactPersonResponse(
            1L, 3L, 3704L, "John Doe", "john.doe@example.com", true, now
        );

        // Assert
        assertEquals(1L, response.id());
        assertEquals(3L, response.partnershipId());
        assertEquals(3704L, response.userId());
        assertEquals("John Doe", response.userName());
        assertEquals("john.doe@example.com", response.userEmail());
        assertTrue(response.isActive());
        assertEquals(now, response.activeSince());
    }

    @Test
    void constructor_WithNullValues_ShouldAccept() {
        // Act
        ContactPersonResponse response = new ContactPersonResponse(
            null, null, null, null, null, null, null
        );

        // Assert
        assertNull(response.id());
        assertNull(response.partnershipId());
    }
}
