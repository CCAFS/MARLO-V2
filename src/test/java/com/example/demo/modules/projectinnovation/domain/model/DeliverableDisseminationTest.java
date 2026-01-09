package com.example.demo.modules.projectinnovation.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeliverableDisseminationTest {

    private DeliverableDissemination deliverableDissemination;

    @BeforeEach
    void setUp() {
        deliverableDissemination = new DeliverableDissemination();
    }

    @Test
    void constructor_NoArgsConstructor_ShouldCreateEmpty() {
        // Act
        DeliverableDissemination newDissemination = new DeliverableDissemination();

        // Assert
        assertNull(newDissemination.getId());
        assertNull(newDissemination.getDeliverableId());
        assertNull(newDissemination.getIdPhase());
        assertNull(newDissemination.getDisseminationUrl());
    }

    @Test
    void constructor_AllArgsConstructor_ShouldSetAllFields() {
        // Arrange
        Long id = 1L;
        Long deliverableId = 100L;
        Long idPhase = 1L;
        String disseminationUrl = "https://example.com/dissemination";

        // Act
        DeliverableDissemination newDissemination = new DeliverableDissemination(id, deliverableId, idPhase, disseminationUrl);

        // Assert
        assertEquals(id, newDissemination.getId());
        assertEquals(deliverableId, newDissemination.getDeliverableId());
        assertEquals(idPhase, newDissemination.getIdPhase());
        assertEquals(disseminationUrl, newDissemination.getDisseminationUrl());
    }

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        // Arrange
        Long id = 1L;
        Long deliverableId = 100L;
        Long idPhase = 1L;
        String disseminationUrl = "https://test.com/dissemination";

        // Act
        deliverableDissemination.setId(id);
        deliverableDissemination.setDeliverableId(deliverableId);
        deliverableDissemination.setIdPhase(idPhase);
        deliverableDissemination.setDisseminationUrl(disseminationUrl);

        // Assert
        assertEquals(id, deliverableDissemination.getId());
        assertEquals(deliverableId, deliverableDissemination.getDeliverableId());
        assertEquals(idPhase, deliverableDissemination.getIdPhase());
        assertEquals(disseminationUrl, deliverableDissemination.getDisseminationUrl());
    }

    @Test
    void equals_WithSameId_ShouldReturnTrue() {
        // Arrange
        DeliverableDissemination d1 = new DeliverableDissemination();
        DeliverableDissemination d2 = new DeliverableDissemination();
        d1.setId(1L);
        d2.setId(1L);

        // Act & Assert
        assertEquals(d1, d2);
    }

    @Test
    void equals_WithDifferentIds_ShouldReturnFalse() {
        // Arrange
        DeliverableDissemination d1 = new DeliverableDissemination();
        DeliverableDissemination d2 = new DeliverableDissemination();
        d1.setId(1L);
        d2.setId(2L);

        // Act & Assert
        assertNotEquals(d1, d2);
    }

    @Test
    void equals_WithNull_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(deliverableDissemination, null);
    }

    @Test
    void equals_WithDifferentClass_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(deliverableDissemination, "not a dissemination");
    }

    @Test
    void equals_WithSameInstance_ShouldReturnTrue() {
        // Act & Assert
        assertEquals(deliverableDissemination, deliverableDissemination);
    }

    @Test
    void equals_WithAllFieldsSame_ShouldReturnTrue() {
        // Arrange
        Long id = 1L;
        Long deliverableId = 100L;
        Long idPhase = 1L;
        String disseminationUrl = "https://example.com";

        DeliverableDissemination d1 = new DeliverableDissemination(id, deliverableId, idPhase, disseminationUrl);
        DeliverableDissemination d2 = new DeliverableDissemination(id, deliverableId, idPhase, disseminationUrl);

        // Act & Assert
        assertEquals(d1, d2);
    }

    @Test
    void equals_WithDifferentFields_ShouldReturnFalse() {
        // Arrange
        DeliverableDissemination d1 = new DeliverableDissemination();
        DeliverableDissemination d2 = new DeliverableDissemination();
        d1.setId(1L);
        d1.setDisseminationUrl("URL 1");
        d2.setId(1L);
        d2.setDisseminationUrl("URL 2");

        // Act & Assert
        assertNotEquals(d1, d2);
    }

    @Test
    void hashCode_WithSameFields_ShouldReturnSameHashCode() {
        // Arrange
        DeliverableDissemination d1 = new DeliverableDissemination();
        DeliverableDissemination d2 = new DeliverableDissemination();
        d1.setId(1L);
        d1.setDisseminationUrl("URL");
        d2.setId(1L);
        d2.setDisseminationUrl("URL");

        // Act & Assert
        assertEquals(d1.hashCode(), d2.hashCode());
    }

    @Test
    void hashCode_WithDifferentFields_ShouldReturnDifferentHashCode() {
        // Arrange
        DeliverableDissemination d1 = new DeliverableDissemination();
        DeliverableDissemination d2 = new DeliverableDissemination();
        d1.setId(1L);
        d1.setDisseminationUrl("URL 1");
        d2.setId(2L);
        d2.setDisseminationUrl("URL 2");

        // Act & Assert
        assertNotEquals(d1.hashCode(), d2.hashCode());
    }

    @Test
    void hashCode_WithNullFields_ShouldNotThrowException() {
        // Act & Assert
        assertDoesNotThrow(() -> deliverableDissemination.hashCode());
    }

    @Test
    void toString_ShouldContainClassName() {
        // Act
        String toString = deliverableDissemination.toString();

        // Assert
        assertTrue(toString.contains("DeliverableDissemination"));
    }

    @Test
    void equals_WithNullFields_ShouldHandleNulls() {
        // Arrange
        DeliverableDissemination d1 = new DeliverableDissemination();
        DeliverableDissemination d2 = new DeliverableDissemination();
        d1.setId(null);
        d1.setDisseminationUrl(null);
        d2.setId(null);
        d2.setDisseminationUrl(null);

        // Act & Assert
        assertEquals(d1, d2);
    }

    @Test
    void equals_WithOneNullField_ShouldReturnFalse() {
        // Arrange
        DeliverableDissemination d1 = new DeliverableDissemination();
        DeliverableDissemination d2 = new DeliverableDissemination();
        d1.setId(1L);
        d1.setDisseminationUrl("URL");
        d2.setId(1L);
        d2.setDisseminationUrl(null);

        // Act & Assert
        assertNotEquals(d1, d2);
    }
}
