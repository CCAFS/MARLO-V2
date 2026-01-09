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
}
