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
    void constructor_NoArgsConstructor_ShouldCreateEmptyActor() {
        // Act
        Actor newActor = new Actor();

        // Assert
        assertNull(newActor.getId());
        assertNull(newActor.getName());
        assertNull(newActor.getDescription());
        assertTrue(newActor.getIsActive()); // Default value is true
    }

    @Test
    void constructor_WithEssentialFields_ShouldSetFields() {
        // Act
        Actor newActor = new Actor("Test Actor", "Test Description", true);

        // Assert
        assertEquals("Test Actor", newActor.getName());
        assertEquals("Test Description", newActor.getDescription());
        assertTrue(newActor.getIsActive());
    }

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        // Arrange
        Long id = 1L;
        String name = "Actor Name";
        String description = "Actor Description";
        Boolean isActive = true;
        LocalDateTime activeSince = LocalDateTime.now();
        Long createdBy = 10L;
        Long modifiedBy = 20L;
        String modificationJustification = "Test justification";
        Long prmsIdEquivalent = 100L;
        String prmsNameEquivalent = "PRMS Name";

        // Act
        actor.setId(id);
        actor.setName(name);
        actor.setDescription(description);
        actor.setIsActive(isActive);
        actor.setActiveSince(activeSince);
        actor.setCreatedBy(createdBy);
        actor.setModifiedBy(modifiedBy);
        actor.setModificationJustification(modificationJustification);
        actor.setPrmsIdEquivalent(prmsIdEquivalent);
        actor.setPrmsNameEquivalent(prmsNameEquivalent);

        // Assert
        assertEquals(id, actor.getId());
        assertEquals(name, actor.getName());
        assertEquals(description, actor.getDescription());
        assertEquals(isActive, actor.getIsActive());
        assertEquals(activeSince, actor.getActiveSince());
        assertEquals(createdBy, actor.getCreatedBy());
        assertEquals(modifiedBy, actor.getModifiedBy());
        assertEquals(modificationJustification, actor.getModificationJustification());
        assertEquals(prmsIdEquivalent, actor.getPrmsIdEquivalent());
        assertEquals(prmsNameEquivalent, actor.getPrmsNameEquivalent());
    }

    @Test
    void setIsActive_WithFalse_ShouldSetToFalse() {
        // Act
        actor.setIsActive(false);

        // Assert
        assertFalse(actor.getIsActive());
    }

    @Test
    void setIsActive_WithNull_ShouldSetToNull() {
        // Act
        actor.setIsActive(null);

        // Assert
        assertNull(actor.getIsActive());
    }

    @Test
    void toString_ShouldContainKeyFields() {
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
        assertTrue(result.contains("id=1"));
        assertTrue(result.contains("name='Test Actor'"));
        assertTrue(result.contains("description='Test Description'"));
        assertTrue(result.contains("isActive=true"));
    }

    @Test
    void defaultIsActive_ShouldBeTrue() {
        // Assert - Default value is true
        assertTrue(actor.getIsActive());
    }
}
