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

    @Test
    void equals_WithSameId_ShouldReturnTrue() {
        // Arrange
        ProjectInnovationBundle b1 = new ProjectInnovationBundle();
        ProjectInnovationBundle b2 = new ProjectInnovationBundle();
        b1.setId(1L);
        b2.setId(1L);

        // Act & Assert
        assertEquals(b1, b2);
    }

    @Test
    void equals_WithDifferentIds_ShouldReturnFalse() {
        // Arrange
        ProjectInnovationBundle b1 = new ProjectInnovationBundle();
        ProjectInnovationBundle b2 = new ProjectInnovationBundle();
        b1.setId(1L);
        b2.setId(2L);

        // Act & Assert
        assertNotEquals(b1, b2);
    }

    @Test
    void equals_WithNull_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(projectInnovationBundle, null);
    }

    @Test
    void equals_WithDifferentClass_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(projectInnovationBundle, "not a bundle");
    }

    @Test
    void equals_WithSameInstance_ShouldReturnTrue() {
        // Act & Assert
        assertEquals(projectInnovationBundle, projectInnovationBundle);
    }

    @Test
    void equals_WithAllFieldsSame_ShouldReturnTrue() {
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

        ProjectInnovationBundle b1 = new ProjectInnovationBundle(id, projectInnovationId, selectedInnovationId, idPhase, isActive, activeSince, createdBy, modifiedBy, modificationJustification);
        ProjectInnovationBundle b2 = new ProjectInnovationBundle(id, projectInnovationId, selectedInnovationId, idPhase, isActive, activeSince, createdBy, modifiedBy, modificationJustification);

        // Act & Assert
        assertEquals(b1, b2);
    }

    @Test
    void equals_WithDifferentFields_ShouldReturnFalse() {
        // Arrange
        ProjectInnovationBundle b1 = new ProjectInnovationBundle();
        ProjectInnovationBundle b2 = new ProjectInnovationBundle();
        b1.setId(1L);
        b1.setSelectedInnovationId(100L);
        b2.setId(1L);
        b2.setSelectedInnovationId(200L);

        // Act & Assert
        assertNotEquals(b1, b2);
    }

    @Test
    void hashCode_WithSameFields_ShouldReturnSameHashCode() {
        // Arrange
        ProjectInnovationBundle b1 = new ProjectInnovationBundle();
        ProjectInnovationBundle b2 = new ProjectInnovationBundle();
        b1.setId(1L);
        b1.setSelectedInnovationId(100L);
        b2.setId(1L);
        b2.setSelectedInnovationId(100L);

        // Act & Assert
        assertEquals(b1.hashCode(), b2.hashCode());
    }

    @Test
    void hashCode_WithDifferentFields_ShouldReturnDifferentHashCode() {
        // Arrange
        ProjectInnovationBundle b1 = new ProjectInnovationBundle();
        ProjectInnovationBundle b2 = new ProjectInnovationBundle();
        b1.setId(1L);
        b1.setSelectedInnovationId(100L);
        b2.setId(2L);
        b2.setSelectedInnovationId(200L);

        // Act & Assert
        assertNotEquals(b1.hashCode(), b2.hashCode());
    }

    @Test
    void hashCode_WithNullFields_ShouldNotThrowException() {
        // Act & Assert
        assertDoesNotThrow(() -> projectInnovationBundle.hashCode());
    }

    @Test
    void toString_ShouldContainClassName() {
        // Act
        String toString = projectInnovationBundle.toString();

        // Assert
        assertTrue(toString.contains("ProjectInnovationBundle"));
    }

    @Test
    void equals_WithNullFields_ShouldHandleNulls() {
        // Arrange
        ProjectInnovationBundle b1 = new ProjectInnovationBundle();
        ProjectInnovationBundle b2 = new ProjectInnovationBundle();
        b1.setId(null);
        b1.setSelectedInnovationId(null);
        b2.setId(null);
        b2.setSelectedInnovationId(null);

        // Act & Assert
        assertEquals(b1, b2);
    }

    @Test
    void equals_WithOneNullField_ShouldReturnFalse() {
        // Arrange
        ProjectInnovationBundle b1 = new ProjectInnovationBundle();
        ProjectInnovationBundle b2 = new ProjectInnovationBundle();
        b1.setId(1L);
        b1.setSelectedInnovationId(100L);
        b2.setId(1L);
        b2.setSelectedInnovationId(null);

        // Act & Assert
        assertNotEquals(b1, b2);
    }
}
