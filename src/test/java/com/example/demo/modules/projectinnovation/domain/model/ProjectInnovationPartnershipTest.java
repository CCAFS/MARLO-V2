package com.example.demo.modules.projectinnovation.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProjectInnovationPartnershipTest {

    private ProjectInnovationPartnership projectInnovationPartnership;

    @BeforeEach
    void setUp() {
        projectInnovationPartnership = new ProjectInnovationPartnership();
    }

    @Test
    void constructor_NoArgsConstructor_ShouldCreateEmpty() {
        // Act
        ProjectInnovationPartnership newPartnership = new ProjectInnovationPartnership();

        // Assert
        assertNull(newPartnership.getId());
        assertNull(newPartnership.getProjectInnovationId());
        assertNull(newPartnership.getInstitutionId());
        assertNull(newPartnership.getIdPhase());
    }

    @Test
    void constructor_AllArgsConstructor_ShouldSetAllFields() {
        // Arrange
        Long id = 1L;
        Long projectInnovationId = 100L;
        Long institutionId = 200L;
        Long idPhase = 1L;
        Long innovationPartnerTypeId = 300L;
        Boolean isActive = true;
        LocalDateTime activeSince = LocalDateTime.now();
        Long createdBy = 10L;
        Long modifiedBy = 20L;
        String modificationJustification = "Test justification";

        // Act
        ProjectInnovationPartnership newPartnership = new ProjectInnovationPartnership(
                id, projectInnovationId, institutionId, idPhase, innovationPartnerTypeId,
                isActive, activeSince, createdBy, modifiedBy, modificationJustification);

        // Assert
        assertEquals(id, newPartnership.getId());
        assertEquals(projectInnovationId, newPartnership.getProjectInnovationId());
        assertEquals(institutionId, newPartnership.getInstitutionId());
        assertEquals(idPhase, newPartnership.getIdPhase());
        assertEquals(innovationPartnerTypeId, newPartnership.getInnovationPartnerTypeId());
        assertEquals(isActive, newPartnership.getIsActive());
        assertEquals(activeSince, newPartnership.getActiveSince());
        assertEquals(createdBy, newPartnership.getCreatedBy());
        assertEquals(modifiedBy, newPartnership.getModifiedBy());
        assertEquals(modificationJustification, newPartnership.getModificationJustification());
    }

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        // Arrange
        Long id = 1L;
        Long projectInnovationId = 100L;
        Long institutionId = 200L;
        Long idPhase = 1L;
        Long innovationPartnerTypeId = 300L;
        Boolean isActive = true;
        LocalDateTime activeSince = LocalDateTime.now();
        Long createdBy = 10L;
        Long modifiedBy = 20L;
        String modificationJustification = "Justification";

        // Act
        projectInnovationPartnership.setId(id);
        projectInnovationPartnership.setProjectInnovationId(projectInnovationId);
        projectInnovationPartnership.setInstitutionId(institutionId);
        projectInnovationPartnership.setIdPhase(idPhase);
        projectInnovationPartnership.setInnovationPartnerTypeId(innovationPartnerTypeId);
        projectInnovationPartnership.setIsActive(isActive);
        projectInnovationPartnership.setActiveSince(activeSince);
        projectInnovationPartnership.setCreatedBy(createdBy);
        projectInnovationPartnership.setModifiedBy(modifiedBy);
        projectInnovationPartnership.setModificationJustification(modificationJustification);

        // Assert
        assertEquals(id, projectInnovationPartnership.getId());
        assertEquals(projectInnovationId, projectInnovationPartnership.getProjectInnovationId());
        assertEquals(institutionId, projectInnovationPartnership.getInstitutionId());
        assertEquals(idPhase, projectInnovationPartnership.getIdPhase());
        assertEquals(innovationPartnerTypeId, projectInnovationPartnership.getInnovationPartnerTypeId());
        assertEquals(isActive, projectInnovationPartnership.getIsActive());
        assertEquals(activeSince, projectInnovationPartnership.getActiveSince());
        assertEquals(createdBy, projectInnovationPartnership.getCreatedBy());
        assertEquals(modifiedBy, projectInnovationPartnership.getModifiedBy());
        assertEquals(modificationJustification, projectInnovationPartnership.getModificationJustification());
    }

    @Test
    void equals_WithSameId_ShouldReturnTrue() {
        // Arrange
        ProjectInnovationPartnership p1 = new ProjectInnovationPartnership();
        ProjectInnovationPartnership p2 = new ProjectInnovationPartnership();
        p1.setId(1L);
        p2.setId(1L);

        // Act & Assert
        assertEquals(p1, p2);
    }

    @Test
    void equals_WithDifferentIds_ShouldReturnFalse() {
        // Arrange
        ProjectInnovationPartnership p1 = new ProjectInnovationPartnership();
        ProjectInnovationPartnership p2 = new ProjectInnovationPartnership();
        p1.setId(1L);
        p2.setId(2L);

        // Act & Assert
        assertNotEquals(p1, p2);
    }

    @Test
    void equals_WithNull_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(projectInnovationPartnership, null);
    }

    @Test
    void equals_WithDifferentClass_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(projectInnovationPartnership, "not a partnership");
    }

    @Test
    void equals_WithSameInstance_ShouldReturnTrue() {
        // Act & Assert
        assertEquals(projectInnovationPartnership, projectInnovationPartnership);
    }

    @Test
    void equals_WithAllFieldsSame_ShouldReturnTrue() {
        // Arrange
        Long id = 1L;
        Long projectInnovationId = 100L;
        Long institutionId = 200L;
        Long idPhase = 1L;
        Long innovationPartnerTypeId = 300L;
        Boolean isActive = true;
        LocalDateTime activeSince = LocalDateTime.now();
        Long createdBy = 10L;
        Long modifiedBy = 20L;
        String modificationJustification = "Justification";

        ProjectInnovationPartnership p1 = new ProjectInnovationPartnership(id, projectInnovationId, institutionId, idPhase, innovationPartnerTypeId, isActive, activeSince, createdBy, modifiedBy, modificationJustification);
        ProjectInnovationPartnership p2 = new ProjectInnovationPartnership(id, projectInnovationId, institutionId, idPhase, innovationPartnerTypeId, isActive, activeSince, createdBy, modifiedBy, modificationJustification);

        // Act & Assert
        assertEquals(p1, p2);
    }

    @Test
    void equals_WithDifferentFields_ShouldReturnFalse() {
        // Arrange
        ProjectInnovationPartnership p1 = new ProjectInnovationPartnership();
        ProjectInnovationPartnership p2 = new ProjectInnovationPartnership();
        p1.setId(1L);
        p1.setInstitutionId(100L);
        p2.setId(1L);
        p2.setInstitutionId(200L);

        // Act & Assert
        assertNotEquals(p1, p2);
    }

    @Test
    void hashCode_WithSameFields_ShouldReturnSameHashCode() {
        // Arrange
        ProjectInnovationPartnership p1 = new ProjectInnovationPartnership();
        ProjectInnovationPartnership p2 = new ProjectInnovationPartnership();
        p1.setId(1L);
        p1.setInstitutionId(100L);
        p2.setId(1L);
        p2.setInstitutionId(100L);

        // Act & Assert
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void hashCode_WithDifferentFields_ShouldReturnDifferentHashCode() {
        // Arrange
        ProjectInnovationPartnership p1 = new ProjectInnovationPartnership();
        ProjectInnovationPartnership p2 = new ProjectInnovationPartnership();
        p1.setId(1L);
        p1.setInstitutionId(100L);
        p2.setId(2L);
        p2.setInstitutionId(200L);

        // Act & Assert
        assertNotEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void hashCode_WithNullFields_ShouldNotThrowException() {
        // Act & Assert
        assertDoesNotThrow(() -> projectInnovationPartnership.hashCode());
    }

    @Test
    void toString_ShouldContainClassName() {
        // Act
        String toString = projectInnovationPartnership.toString();

        // Assert
        assertTrue(toString.contains("ProjectInnovationPartnership"));
    }

    @Test
    void equals_WithNullFields_ShouldHandleNulls() {
        // Arrange
        ProjectInnovationPartnership p1 = new ProjectInnovationPartnership();
        ProjectInnovationPartnership p2 = new ProjectInnovationPartnership();
        p1.setId(null);
        p1.setInstitutionId(null);
        p2.setId(null);
        p2.setInstitutionId(null);

        // Act & Assert
        assertEquals(p1, p2);
    }

    @Test
    void equals_WithOneNullField_ShouldReturnFalse() {
        // Arrange
        ProjectInnovationPartnership p1 = new ProjectInnovationPartnership();
        ProjectInnovationPartnership p2 = new ProjectInnovationPartnership();
        p1.setId(1L);
        p1.setInstitutionId(100L);
        p2.setId(1L);
        p2.setInstitutionId(null);

        // Act & Assert
        assertNotEquals(p1, p2);
    }
}
