package com.example.demo.modules.projectinnovation.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DeliverableInfoTest {

    private DeliverableInfo deliverableInfo;

    @BeforeEach
    void setUp() {
        deliverableInfo = new DeliverableInfo();
    }

    @Test
    void constructor_NoArgsConstructor_ShouldCreateEmpty() {
        // Act
        DeliverableInfo newDeliverable = new DeliverableInfo();

        // Assert
        assertNull(newDeliverable.getId());
        assertNull(newDeliverable.getDeliverableId());
        assertNull(newDeliverable.getIdPhase());
        assertNull(newDeliverable.getTitle());
        assertNull(newDeliverable.getIsActive());
        assertNull(newDeliverable.getActiveSince());
    }

    @Test
    void constructor_AllArgsConstructor_ShouldSetAllFields() {
        // Arrange
        Long id = 1L;
        Long deliverableId = 100L;
        Long idPhase = 1L;
        String title = "Test Deliverable";
        Boolean isActive = true;
        LocalDateTime activeSince = LocalDateTime.now();

        // Act
        DeliverableInfo newDeliverable = new DeliverableInfo(id, deliverableId, idPhase, title, isActive, activeSince);

        // Assert
        assertEquals(id, newDeliverable.getId());
        assertEquals(deliverableId, newDeliverable.getDeliverableId());
        assertEquals(idPhase, newDeliverable.getIdPhase());
        assertEquals(title, newDeliverable.getTitle());
        assertEquals(isActive, newDeliverable.getIsActive());
        assertEquals(activeSince, newDeliverable.getActiveSince());
    }

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        // Arrange
        Long id = 1L;
        Long deliverableId = 100L;
        Long idPhase = 1L;
        String title = "Deliverable Title";
        Boolean isActive = true;
        LocalDateTime activeSince = LocalDateTime.now();

        // Act
        deliverableInfo.setId(id);
        deliverableInfo.setDeliverableId(deliverableId);
        deliverableInfo.setIdPhase(idPhase);
        deliverableInfo.setTitle(title);
        deliverableInfo.setIsActive(isActive);
        deliverableInfo.setActiveSince(activeSince);

        // Assert
        assertEquals(id, deliverableInfo.getId());
        assertEquals(deliverableId, deliverableInfo.getDeliverableId());
        assertEquals(idPhase, deliverableInfo.getIdPhase());
        assertEquals(title, deliverableInfo.getTitle());
        assertEquals(isActive, deliverableInfo.getIsActive());
        assertEquals(activeSince, deliverableInfo.getActiveSince());
    }

    @Test
    void equals_WithSameId_ShouldReturnTrue() {
        // Arrange
        DeliverableInfo d1 = new DeliverableInfo();
        DeliverableInfo d2 = new DeliverableInfo();
        d1.setId(1L);
        d2.setId(1L);

        // Act & Assert
        assertEquals(d1, d2);
    }

    @Test
    void equals_WithDifferentIds_ShouldReturnFalse() {
        // Arrange
        DeliverableInfo d1 = new DeliverableInfo();
        DeliverableInfo d2 = new DeliverableInfo();
        d1.setId(1L);
        d2.setId(2L);

        // Act & Assert
        assertNotEquals(d1, d2);
    }

    @Test
    void equals_WithNull_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(deliverableInfo, null);
    }

    @Test
    void equals_WithDifferentClass_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(deliverableInfo, "not a deliverable");
    }

    @Test
    void equals_WithSameInstance_ShouldReturnTrue() {
        // Act & Assert
        assertEquals(deliverableInfo, deliverableInfo);
    }

    @Test
    void equals_WithAllFieldsSame_ShouldReturnTrue() {
        // Arrange
        Long id = 1L;
        Long deliverableId = 100L;
        Long idPhase = 1L;
        String title = "Title";
        Boolean isActive = true;
        LocalDateTime activeSince = LocalDateTime.now();

        DeliverableInfo d1 = new DeliverableInfo(id, deliverableId, idPhase, title, isActive, activeSince);
        DeliverableInfo d2 = new DeliverableInfo(id, deliverableId, idPhase, title, isActive, activeSince);

        // Act & Assert
        assertEquals(d1, d2);
    }

    @Test
    void equals_WithDifferentFields_ShouldReturnFalse() {
        // Arrange
        DeliverableInfo d1 = new DeliverableInfo();
        DeliverableInfo d2 = new DeliverableInfo();
        d1.setId(1L);
        d1.setTitle("Title 1");
        d2.setId(1L);
        d2.setTitle("Title 2");

        // Act & Assert
        assertNotEquals(d1, d2);
    }

    @Test
    void hashCode_WithSameFields_ShouldReturnSameHashCode() {
        // Arrange
        DeliverableInfo d1 = new DeliverableInfo();
        DeliverableInfo d2 = new DeliverableInfo();
        d1.setId(1L);
        d1.setTitle("Title");
        d2.setId(1L);
        d2.setTitle("Title");

        // Act & Assert
        assertEquals(d1.hashCode(), d2.hashCode());
    }

    @Test
    void hashCode_WithDifferentFields_ShouldReturnDifferentHashCode() {
        // Arrange
        DeliverableInfo d1 = new DeliverableInfo();
        DeliverableInfo d2 = new DeliverableInfo();
        d1.setId(1L);
        d1.setTitle("Title 1");
        d2.setId(2L);
        d2.setTitle("Title 2");

        // Act & Assert
        assertNotEquals(d1.hashCode(), d2.hashCode());
    }

    @Test
    void hashCode_WithNullFields_ShouldNotThrowException() {
        // Act & Assert
        assertDoesNotThrow(() -> deliverableInfo.hashCode());
    }

    @Test
    void toString_ShouldContainClassName() {
        // Act
        String toString = deliverableInfo.toString();

        // Assert
        assertTrue(toString.contains("DeliverableInfo"));
    }

    @Test
    void equals_WithNullFields_ShouldHandleNulls() {
        // Arrange
        DeliverableInfo d1 = new DeliverableInfo();
        DeliverableInfo d2 = new DeliverableInfo();
        d1.setId(null);
        d1.setTitle(null);
        d2.setId(null);
        d2.setTitle(null);

        // Act & Assert
        assertEquals(d1, d2);
    }

    @Test
    void equals_WithOneNullField_ShouldReturnFalse() {
        // Arrange
        DeliverableInfo d1 = new DeliverableInfo();
        DeliverableInfo d2 = new DeliverableInfo();
        d1.setId(1L);
        d1.setTitle("Title");
        d2.setId(1L);
        d2.setTitle(null);

        // Act & Assert
        assertNotEquals(d1, d2);
    }
}
