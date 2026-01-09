package com.example.demo.modules.projectinnovation.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProjectInnovationReferenceTest {

    private ProjectInnovationReference projectInnovationReference;

    @BeforeEach
    void setUp() {
        projectInnovationReference = new ProjectInnovationReference();
    }

    @Test
    void constructor_NoArgsConstructor_ShouldCreateEmpty() {
        // Act
        ProjectInnovationReference newReference = new ProjectInnovationReference();

        // Assert
        assertNull(newReference.getId());
        assertNull(newReference.getProjectInnovationId());
        assertNull(newReference.getReference());
        assertNull(newReference.getIdPhase());
        assertNull(newReference.getLink());
        assertNull(newReference.getDeliverableName());
        assertNull(newReference.getDisseminationUrl());
    }

    @Test
    void constructor_AllArgsConstructor_ShouldSetAllFields() {
        // Arrange
        Long id = 1L;
        Long projectInnovationId = 100L;
        String reference = "Test Reference";
        Long idPhase = 1L;
        String link = "https://example.com";
        Boolean isExternalAuthor = true;
        Boolean hasEvidenceByDeliverable = false;
        Long deliverableId = 200L;
        Long typeId = 300L;
        Boolean isActive = true;
        LocalDateTime activeSince = LocalDateTime.now();
        Long createdBy = 10L;
        Long modifiedBy = 20L;
        String modificationJustification = "Test justification";

        // Act
        ProjectInnovationReference newReference = new ProjectInnovationReference(
                id, projectInnovationId, reference, idPhase, link, isExternalAuthor,
                hasEvidenceByDeliverable, deliverableId, typeId, isActive, activeSince,
                createdBy, modifiedBy, modificationJustification, null, null);

        // Assert
        assertEquals(id, newReference.getId());
        assertEquals(projectInnovationId, newReference.getProjectInnovationId());
        assertEquals(reference, newReference.getReference());
        assertEquals(idPhase, newReference.getIdPhase());
        assertEquals(link, newReference.getLink());
        assertEquals(isExternalAuthor, newReference.getIsExternalAuthor());
        assertEquals(hasEvidenceByDeliverable, newReference.getHasEvidenceByDeliverable());
        assertEquals(deliverableId, newReference.getDeliverableId());
        assertEquals(typeId, newReference.getTypeId());
        assertEquals(isActive, newReference.getIsActive());
        assertEquals(activeSince, newReference.getActiveSince());
        assertEquals(createdBy, newReference.getCreatedBy());
        assertEquals(modifiedBy, newReference.getModifiedBy());
        assertEquals(modificationJustification, newReference.getModificationJustification());
    }

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        // Arrange
        Long id = 1L;
        Long projectInnovationId = 100L;
        String reference = "Reference Text";
        Long idPhase = 1L;
        String link = "https://test.com";
        Boolean isExternalAuthor = true;
        Boolean hasEvidenceByDeliverable = false;
        Long deliverableId = 200L;
        Long typeId = 300L;
        Boolean isActive = true;
        LocalDateTime activeSince = LocalDateTime.now();
        Long createdBy = 10L;
        Long modifiedBy = 20L;
        String modificationJustification = "Justification";
        String deliverableName = "Deliverable Name";
        String disseminationUrl = "https://dissemination.com";

        // Act
        projectInnovationReference.setId(id);
        projectInnovationReference.setProjectInnovationId(projectInnovationId);
        projectInnovationReference.setReference(reference);
        projectInnovationReference.setIdPhase(idPhase);
        projectInnovationReference.setLink(link);
        projectInnovationReference.setIsExternalAuthor(isExternalAuthor);
        projectInnovationReference.setHasEvidenceByDeliverable(hasEvidenceByDeliverable);
        projectInnovationReference.setDeliverableId(deliverableId);
        projectInnovationReference.setTypeId(typeId);
        projectInnovationReference.setIsActive(isActive);
        projectInnovationReference.setActiveSince(activeSince);
        projectInnovationReference.setCreatedBy(createdBy);
        projectInnovationReference.setModifiedBy(modifiedBy);
        projectInnovationReference.setModificationJustification(modificationJustification);
        projectInnovationReference.setDeliverableName(deliverableName);
        projectInnovationReference.setDisseminationUrl(disseminationUrl);

        // Assert
        assertEquals(id, projectInnovationReference.getId());
        assertEquals(projectInnovationId, projectInnovationReference.getProjectInnovationId());
        assertEquals(reference, projectInnovationReference.getReference());
        assertEquals(idPhase, projectInnovationReference.getIdPhase());
        assertEquals(link, projectInnovationReference.getLink());
        assertEquals(isExternalAuthor, projectInnovationReference.getIsExternalAuthor());
        assertEquals(hasEvidenceByDeliverable, projectInnovationReference.getHasEvidenceByDeliverable());
        assertEquals(deliverableId, projectInnovationReference.getDeliverableId());
        assertEquals(typeId, projectInnovationReference.getTypeId());
        assertEquals(isActive, projectInnovationReference.getIsActive());
        assertEquals(activeSince, projectInnovationReference.getActiveSince());
        assertEquals(createdBy, projectInnovationReference.getCreatedBy());
        assertEquals(modifiedBy, projectInnovationReference.getModifiedBy());
        assertEquals(modificationJustification, projectInnovationReference.getModificationJustification());
        assertEquals(deliverableName, projectInnovationReference.getDeliverableName());
        assertEquals(disseminationUrl, projectInnovationReference.getDisseminationUrl());
    }
}
