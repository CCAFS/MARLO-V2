package com.example.demo.modules.projectinnovation.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ActorTest {

    private Actor actor;

    @BeforeEach
    void setUp() {
        actor = new Actor();
    }

    @Test
    void constructor_WithNoArgsConstructor_ShouldCreateEmpty() {
        // Act
        Actor newActor = new Actor();

        // Assert
        assertNotNull(newActor);
        assertNull(newActor.getId());
        assertNull(newActor.getName());
    }

    @Test
    void constructor_WithEssentialFields_ShouldSetValues() {
        // Act
        Actor newActor = new Actor("Test Actor", "Test Description", true);

        // Assert
        assertEquals("Test Actor", newActor.getName());
        assertEquals("Test Description", newActor.getDescription());
        assertTrue(newActor.getIsActive());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Act
        actor.setId(1L);
        actor.setName("Actor Name");
        actor.setDescription("Actor Description");
        actor.setIsActive(true);
        actor.setActiveSince(LocalDateTime.now());
        actor.setCreatedBy(1L);
        actor.setModifiedBy(2L);
        actor.setModificationJustification("Justification");
        actor.setPrmsIdEquivalent(100L);
        actor.setPrmsNameEquivalent("PRMS Name");

        // Assert
        assertEquals(1L, actor.getId());
        assertEquals("Actor Name", actor.getName());
        assertEquals("Actor Description", actor.getDescription());
        assertTrue(actor.getIsActive());
        assertNotNull(actor.getActiveSince());
        assertEquals(1L, actor.getCreatedBy());
        assertEquals(2L, actor.getModifiedBy());
        assertEquals("Justification", actor.getModificationJustification());
        assertEquals(100L, actor.getPrmsIdEquivalent());
        assertEquals("PRMS Name", actor.getPrmsNameEquivalent());
    }

    @Test
    void toString_ShouldReturnStringRepresentation() {
        // Arrange
        actor.setId(1L);
        actor.setName("Test Actor");
        actor.setDescription("Test Description");
        actor.setIsActive(true);

        // Act
        String result = actor.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("Actor"));
        assertTrue(result.contains("1"));
        assertTrue(result.contains("Test Actor"));
    }
}
