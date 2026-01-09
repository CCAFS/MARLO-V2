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
}
