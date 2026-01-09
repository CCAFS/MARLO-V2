package com.example.demo.modules.projectinnovation.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProjectInnovationSdgTest {

    private ProjectInnovationSdg projectInnovationSdg;

    @BeforeEach
    void setUp() {
        projectInnovationSdg = new ProjectInnovationSdg();
    }

    @Test
    void constructor_NoArgsConstructor_ShouldCreateEmpty() {
        // Act
        ProjectInnovationSdg newSdg = new ProjectInnovationSdg();

        // Assert
        assertNull(newSdg.getId());
        assertNull(newSdg.getInnovationId());
        assertNull(newSdg.getSdgId());
        assertNull(newSdg.getIdPhase());
        assertNull(newSdg.getIsActive());
    }

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        // Arrange
        Long id = 1L;
        Long innovationId = 100L;
        Long sdgId = 200L;
        Long idPhase = 1L;
        Boolean isActive = true;
        LocalDateTime activeSince = LocalDateTime.now();
        Long createdBy = 10L;
        Long modifiedBy = 20L;
        String modificationJustification = "Test justification";

        // Act
        projectInnovationSdg.setId(id);
        projectInnovationSdg.setInnovationId(innovationId);
        projectInnovationSdg.setSdgId(sdgId);
        projectInnovationSdg.setIdPhase(idPhase);
        projectInnovationSdg.setIsActive(isActive);
        projectInnovationSdg.setActiveSince(activeSince);
        projectInnovationSdg.setCreatedBy(createdBy);
        projectInnovationSdg.setModifiedBy(modifiedBy);
        projectInnovationSdg.setModificationJustification(modificationJustification);

        // Assert
        assertEquals(id, projectInnovationSdg.getId());
        assertEquals(innovationId, projectInnovationSdg.getInnovationId());
        assertEquals(sdgId, projectInnovationSdg.getSdgId());
        assertEquals(idPhase, projectInnovationSdg.getIdPhase());
        assertEquals(isActive, projectInnovationSdg.getIsActive());
        assertEquals(activeSince, projectInnovationSdg.getActiveSince());
        assertEquals(createdBy, projectInnovationSdg.getCreatedBy());
        assertEquals(modifiedBy, projectInnovationSdg.getModifiedBy());
        assertEquals(modificationJustification, projectInnovationSdg.getModificationJustification());
    }

    @Test
    void setIsActive_WithFalse_ShouldSetToFalse() {
        // Act
        projectInnovationSdg.setIsActive(false);

        // Assert
        assertFalse(projectInnovationSdg.getIsActive());
    }

    @Test
    void equals_WithSameId_ShouldReturnTrue() {
        // Arrange
        ProjectInnovationSdg sdg1 = new ProjectInnovationSdg();
        ProjectInnovationSdg sdg2 = new ProjectInnovationSdg();
        sdg1.setId(1L);
        sdg2.setId(1L);

        // Act & Assert
        assertEquals(sdg1, sdg2);
    }

    @Test
    void equals_WithDifferentIds_ShouldReturnFalse() {
        // Arrange
        ProjectInnovationSdg sdg1 = new ProjectInnovationSdg();
        ProjectInnovationSdg sdg2 = new ProjectInnovationSdg();
        sdg1.setId(1L);
        sdg2.setId(2L);

        // Act & Assert
        assertNotEquals(sdg1, sdg2);
    }

    @Test
    void equals_WithNull_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(projectInnovationSdg, null);
    }

    @Test
    void equals_WithDifferentClass_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(projectInnovationSdg, "not an sdg");
    }

    @Test
    void equals_WithSameInstance_ShouldReturnTrue() {
        // Act & Assert
        assertEquals(projectInnovationSdg, projectInnovationSdg);
    }

    @Test
    void equals_WithAllFieldsSame_ShouldReturnTrue() {
        // Arrange
        Long id = 1L;
        Long innovationId = 100L;
        Long sdgId = 200L;
        Long idPhase = 1L;
        Boolean isActive = true;
        LocalDateTime activeSince = LocalDateTime.now();
        Long createdBy = 10L;
        Long modifiedBy = 20L;
        String modificationJustification = "Justification";

        ProjectInnovationSdg sdg1 = new ProjectInnovationSdg();
        sdg1.setId(id);
        sdg1.setInnovationId(innovationId);
        sdg1.setSdgId(sdgId);
        sdg1.setIdPhase(idPhase);
        sdg1.setIsActive(isActive);
        sdg1.setActiveSince(activeSince);
        sdg1.setCreatedBy(createdBy);
        sdg1.setModifiedBy(modifiedBy);
        sdg1.setModificationJustification(modificationJustification);

        ProjectInnovationSdg sdg2 = new ProjectInnovationSdg();
        sdg2.setId(id);
        sdg2.setInnovationId(innovationId);
        sdg2.setSdgId(sdgId);
        sdg2.setIdPhase(idPhase);
        sdg2.setIsActive(isActive);
        sdg2.setActiveSince(activeSince);
        sdg2.setCreatedBy(createdBy);
        sdg2.setModifiedBy(modifiedBy);
        sdg2.setModificationJustification(modificationJustification);

        // Act & Assert
        assertEquals(sdg1, sdg2);
    }

    @Test
    void equals_WithDifferentFields_ShouldReturnFalse() {
        // Arrange
        ProjectInnovationSdg sdg1 = new ProjectInnovationSdg();
        ProjectInnovationSdg sdg2 = new ProjectInnovationSdg();
        sdg1.setId(1L);
        sdg1.setSdgId(100L);
        sdg2.setId(1L);
        sdg2.setSdgId(200L);

        // Act & Assert
        assertNotEquals(sdg1, sdg2);
    }

    @Test
    void hashCode_WithSameFields_ShouldReturnSameHashCode() {
        // Arrange
        ProjectInnovationSdg sdg1 = new ProjectInnovationSdg();
        ProjectInnovationSdg sdg2 = new ProjectInnovationSdg();
        sdg1.setId(1L);
        sdg1.setSdgId(100L);
        sdg2.setId(1L);
        sdg2.setSdgId(100L);

        // Act & Assert
        assertEquals(sdg1.hashCode(), sdg2.hashCode());
    }

    @Test
    void hashCode_WithDifferentFields_ShouldReturnDifferentHashCode() {
        // Arrange
        ProjectInnovationSdg sdg1 = new ProjectInnovationSdg();
        ProjectInnovationSdg sdg2 = new ProjectInnovationSdg();
        sdg1.setId(1L);
        sdg1.setSdgId(100L);
        sdg2.setId(2L);
        sdg2.setSdgId(200L);

        // Act & Assert
        assertNotEquals(sdg1.hashCode(), sdg2.hashCode());
    }

    @Test
    void hashCode_WithNullFields_ShouldNotThrowException() {
        // Act & Assert
        assertDoesNotThrow(() -> projectInnovationSdg.hashCode());
    }

    @Test
    void toString_ShouldContainClassName() {
        // Act
        String toString = projectInnovationSdg.toString();

        // Assert
        assertTrue(toString.contains("ProjectInnovationSdg"));
    }

    @Test
    void equals_WithNullFields_ShouldHandleNulls() {
        // Arrange
        ProjectInnovationSdg sdg1 = new ProjectInnovationSdg();
        ProjectInnovationSdg sdg2 = new ProjectInnovationSdg();
        sdg1.setId(null);
        sdg1.setSdgId(null);
        sdg2.setId(null);
        sdg2.setSdgId(null);

        // Act & Assert
        assertEquals(sdg1, sdg2);
    }

    @Test
    void equals_WithOneNullField_ShouldReturnFalse() {
        // Arrange
        ProjectInnovationSdg sdg1 = new ProjectInnovationSdg();
        ProjectInnovationSdg sdg2 = new ProjectInnovationSdg();
        sdg1.setId(1L);
        sdg1.setSdgId(100L);
        sdg2.setId(1L);
        sdg2.setSdgId(null);

        // Act & Assert
        assertNotEquals(sdg1, sdg2);
    }
}
