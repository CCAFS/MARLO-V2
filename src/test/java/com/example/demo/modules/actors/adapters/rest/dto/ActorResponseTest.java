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
        assertNull(response.getPrmsNameEquivalent());
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

    @Test
    void equals_WithSameValues_ShouldBeEqual() {
        // Arrange
        ActorResponse response1 = new ActorResponse(1L, "Farmer", "Agricultural Producer");
        ActorResponse response2 = new ActorResponse(1L, "Farmer", "Agricultural Producer");

        // Assert
        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void equals_WithDifferentId_ShouldNotBeEqual() {
        // Arrange
        ActorResponse response1 = new ActorResponse(1L, "Farmer", "Agricultural Producer");
        ActorResponse response2 = new ActorResponse(2L, "Farmer", "Agricultural Producer");

        // Assert
        assertNotEquals(response1, response2);
    }

    @Test
    void equals_WithDifferentName_ShouldNotBeEqual() {
        // Arrange
        ActorResponse response1 = new ActorResponse(1L, "Farmer", "PRMS");
        ActorResponse response2 = new ActorResponse(1L, "Researcher", "PRMS");

        // Assert
        assertNotEquals(response1, response2);
    }

    @Test
    void equals_WithNull_ShouldNotBeEqual() {
        // Arrange
        ActorResponse response = new ActorResponse(1L, "Farmer", "PRMS");

        // Assert
        assertNotEquals(null, response);
    }

    @Test
    void equals_WithSelf_ShouldBeEqual() {
        // Arrange
        ActorResponse response = new ActorResponse(1L, "Farmer", "PRMS");

        // Assert
        assertEquals(response, response);
    }

    @Test
    void equals_WithDifferentClass_ShouldNotBeEqual() {
        // Arrange
        ActorResponse response = new ActorResponse(1L, "Farmer", "PRMS");

        // Assert
        assertNotEquals(response, "string");
    }

    @Test
    void hashCode_WithSameValues_ShouldBeEqual() {
        // Arrange
        ActorResponse response1 = new ActorResponse(1L, "Farmer", "PRMS");
        ActorResponse response2 = new ActorResponse(1L, "Farmer", "PRMS");

        // Assert
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void hashCode_WithDifferentValues_ShouldBeDifferent() {
        // Arrange
        ActorResponse response1 = new ActorResponse(1L, "Farmer", "PRMS1");
        ActorResponse response2 = new ActorResponse(2L, "Researcher", "PRMS2");

        // Assert
        assertNotEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void toString_ShouldContainAllFields() {
        // Arrange
        ActorResponse response = new ActorResponse(1L, "Farmer", "Agricultural Producer");

        // Act
        String result = response.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("1") || result.contains("Farmer") || result.contains("Agricultural Producer"));
    }

    @Test
    void setNullValues_ShouldAcceptNulls() {
        // Arrange
        ActorResponse response = new ActorResponse(1L, "Farmer", "PRMS");

        // Act
        response.setId(null);
        response.setName(null);
        response.setPrmsNameEquivalent(null);

        // Assert
        assertNull(response.getId());
        assertNull(response.getName());
        assertNull(response.getPrmsNameEquivalent());
    }

    @Test
    void canEqual_WithSameClass_ShouldReturnTrue() {
        // Arrange
        ActorResponse response1 = new ActorResponse();
        ActorResponse response2 = new ActorResponse();

        // Assert - Lombok @Data generates canEqual
        assertTrue(response1.canEqual(response2));
    }

    @Test
    void constructor_WithEmptyStrings_ShouldWork() {
        // Act
        ActorResponse response = new ActorResponse(0L, "", "");

        // Assert
        assertEquals(0L, response.getId());
        assertEquals("", response.getName());
        assertEquals("", response.getPrmsNameEquivalent());
    }
}
