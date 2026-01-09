package com.example.demo.modules.actors.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActorResponseTest {

    @Test
    void constructor_WithNoArgsConstructor_ShouldCreateEmpty() {
        // Act
        ActorResponse response = new ActorResponse();

        // Assert
        assertNotNull(response);
        assertNull(response.getId());
        assertNull(response.getName());
    }

    @Test
    void constructor_WithAllArgsConstructor_ShouldSetAllFields() {
        // Act
        ActorResponse response = new ActorResponse(1L, "Actor Name", "PRMS Name");

        // Assert
        assertEquals(1L, response.getId());
        assertEquals("Actor Name", response.getName());
        assertEquals("PRMS Name", response.getPrmsNameEquivalent());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Arrange
        ActorResponse response = new ActorResponse();

        // Act
        response.setId(1L);
        response.setName("Test Actor");
        response.setPrmsNameEquivalent("PRMS");

        // Assert
        assertEquals(1L, response.getId());
        assertEquals("Test Actor", response.getName());
        assertEquals("PRMS", response.getPrmsNameEquivalent());
    }
}
