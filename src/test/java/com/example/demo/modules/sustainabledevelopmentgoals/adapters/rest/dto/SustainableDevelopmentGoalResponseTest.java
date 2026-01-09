package com.example.demo.modules.sustainabledevelopmentgoals.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SustainableDevelopmentGoalResponseTest {

    @Test
    void constructor_ShouldCreateDto() {
        // Act
        SustainableDevelopmentGoalResponse response = new SustainableDevelopmentGoalResponse(
            1L, 1L, "Zero Hunger", "End hunger", "icon.png", "Description"
        );

        // Assert
        assertEquals(1L, response.id());
        assertEquals(1L, response.smoCode());
        assertEquals("Zero Hunger", response.shortName());
        assertEquals("End hunger", response.fullName());
        assertEquals("icon.png", response.icon());
        assertEquals("Description", response.description());
    }

    @Test
    void constructor_WithNullValues_ShouldAccept() {
        // Act
        SustainableDevelopmentGoalResponse response = new SustainableDevelopmentGoalResponse(
            null, null, null, null, null, null
        );

        // Assert
        assertNull(response.id());
        assertNull(response.smoCode());
        assertNull(response.shortName());
    }
}
