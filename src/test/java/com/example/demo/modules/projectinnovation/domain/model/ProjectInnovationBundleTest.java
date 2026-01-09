package com.example.demo.modules.projectinnovation.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProjectInnovationBundleTest {

    private ProjectInnovationBundle projectInnovationBundle;

    @BeforeEach
    void setUp() {
        projectInnovationBundle = new ProjectInnovationBundle();
    }

    @Test
    void constructor_NoArgsConstructor_ShouldCreateEmpty() {
        // Act
        ProjectInnovationBundle newBundle = new ProjectInnovationBundle();

        // Assert
        assertNull(newBundle.getId());
        assertNull(newBundle.getProjectInnovationId());
        assertNull(newBundle.getSelectedInnovationId());
        assertNull(newBundle.getIdPhase());
    }

    @Test
    void constructor_AllArgsConstructor_ShouldSetAllFields() {
        // Arrange
        Long id = 1L;
        Long projectInnovationId = 100L;
        Long selectedInnovationId = 200L;
        Long idPhase = 1L;
        Boolean isActive = true;
        LocalDateTime activeSince = LocalDateTime.now();
        Long createdBy = 10L;
        Long modifiedBy = 20L;
        String modificationJustification = "Test justification";

        // Act
        ProjectInnovationBundle newBundle = new ProjectInnovationBundle(
                id, projectInnovationId, selectedInnovationId, idPhase, isActive,
                activeSince, createdBy, modifiedBy, modificationJustification);

        // Assert
        assertEquals(id, newBundle.getId());
        assertEquals(projectInnovationId, newBundle.getProjectInnovationId());
        assertEquals(selectedInnovationId, newBundle.getSelectedInnovationId());
        assertEquals(idPhase, newBundle.getIdPhase());
        assertEquals(isActive, newBundle.getIsActive());
        assertEquals(activeSince, newBundle.getActiveSince());
        assertEquals(createdBy, newBundle.getCreatedBy());
        assertEquals(modifiedBy, newBundle.getModifiedBy());
        assertEquals(modificationJustification, newBundle.getModificationJustification());
    }

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        // Arrange
        Long id = 1L;
        Long projectInnovationId = 100L;
        Long selectedInnovationId = 200L;
        Long idPhase = 1L;
        Boolean isActive = true;
        LocalDateTime activeSince = LocalDateTime.now();
        Long createdBy = 10L;
        Long modifiedBy = 20L;
        String modificationJustification = "Justification";

        // Act
        projectInnovationBundle.setId(id);
        projectInnovationBundle.setProjectInnovationId(projectInnovationId);
        projectInnovationBundle.setSelectedInnovationId(selectedInnovationId);
        projectInnovationBundle.setIdPhase(idPhase);
        projectInnovationBundle.setIsActive(isActive);
        projectInnovationBundle.setActiveSince(activeSince);
        projectInnovationBundle.setCreatedBy(createdBy);
        projectInnovationBundle.setModifiedBy(modifiedBy);
        projectInnovationBundle.setModificationJustification(modificationJustification);

        // Assert
        assertEquals(id, projectInnovationBundle.getId());
        assertEquals(projectInnovationId, projectInnovationBundle.getProjectInnovationId());
        assertEquals(selectedInnovationId, projectInnovationBundle.getSelectedInnovationId());
        assertEquals(idPhase, projectInnovationBundle.getIdPhase());
        assertEquals(isActive, projectInnovationBundle.getIsActive());
        assertEquals(activeSince, projectInnovationBundle.getActiveSince());
        assertEquals(createdBy, projectInnovationBundle.getCreatedBy());
        assertEquals(modifiedBy, projectInnovationBundle.getModifiedBy());
        assertEquals(modificationJustification, projectInnovationBundle.getModificationJustification());
    }
}
