package com.example.demo.modules.projectinnovation.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectInnovationContributingOrganizationTest {

    private ProjectInnovationContributingOrganization projectInnovationContributingOrganization;

    @BeforeEach
    void setUp() {
        projectInnovationContributingOrganization = new ProjectInnovationContributingOrganization();
    }

    @Test
    void constructor_NoArgsConstructor_ShouldCreateEmpty() {
        // Act
        ProjectInnovationContributingOrganization newOrg = new ProjectInnovationContributingOrganization();

        // Assert
        assertNull(newOrg.getId());
        assertNull(newOrg.getProjectInnovationId());
        assertNull(newOrg.getIdPhase());
        assertNull(newOrg.getInstitutionId());
    }

    @Test
    void constructor_AllArgsConstructor_ShouldSetAllFields() {
        // Arrange
        Long id = 1L;
        Long projectInnovationId = 100L;
        Long idPhase = 1L;
        Long institutionId = 200L;

        // Act
        ProjectInnovationContributingOrganization newOrg = new ProjectInnovationContributingOrganization(
                id, projectInnovationId, idPhase, institutionId);

        // Assert
        assertEquals(id, newOrg.getId());
        assertEquals(projectInnovationId, newOrg.getProjectInnovationId());
        assertEquals(idPhase, newOrg.getIdPhase());
        assertEquals(institutionId, newOrg.getInstitutionId());
    }

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        // Arrange
        Long id = 1L;
        Long projectInnovationId = 100L;
        Long idPhase = 1L;
        Long institutionId = 200L;

        // Act
        projectInnovationContributingOrganization.setId(id);
        projectInnovationContributingOrganization.setProjectInnovationId(projectInnovationId);
        projectInnovationContributingOrganization.setIdPhase(idPhase);
        projectInnovationContributingOrganization.setInstitutionId(institutionId);

        // Assert
        assertEquals(id, projectInnovationContributingOrganization.getId());
        assertEquals(projectInnovationId, projectInnovationContributingOrganization.getProjectInnovationId());
        assertEquals(idPhase, projectInnovationContributingOrganization.getIdPhase());
        assertEquals(institutionId, projectInnovationContributingOrganization.getInstitutionId());
    }

    @Test
    void equals_WithSameId_ShouldReturnTrue() {
        // Arrange
        ProjectInnovationContributingOrganization org1 = new ProjectInnovationContributingOrganization();
        ProjectInnovationContributingOrganization org2 = new ProjectInnovationContributingOrganization();
        org1.setId(1L);
        org2.setId(1L);

        // Act & Assert
        assertEquals(org1, org2);
    }

    @Test
    void equals_WithDifferentIds_ShouldReturnFalse() {
        // Arrange
        ProjectInnovationContributingOrganization org1 = new ProjectInnovationContributingOrganization();
        ProjectInnovationContributingOrganization org2 = new ProjectInnovationContributingOrganization();
        org1.setId(1L);
        org2.setId(2L);

        // Act & Assert
        assertNotEquals(org1, org2);
    }

    @Test
    void equals_WithNull_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(projectInnovationContributingOrganization, null);
    }

    @Test
    void equals_WithDifferentClass_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(projectInnovationContributingOrganization, "not an org");
    }

    @Test
    void equals_WithSameInstance_ShouldReturnTrue() {
        // Act & Assert
        assertEquals(projectInnovationContributingOrganization, projectInnovationContributingOrganization);
    }

    @Test
    void equals_WithAllFieldsSame_ShouldReturnTrue() {
        // Arrange
        Long id = 1L;
        Long projectInnovationId = 100L;
        Long idPhase = 1L;
        Long institutionId = 200L;

        ProjectInnovationContributingOrganization org1 = new ProjectInnovationContributingOrganization(id, projectInnovationId, idPhase, institutionId);
        ProjectInnovationContributingOrganization org2 = new ProjectInnovationContributingOrganization(id, projectInnovationId, idPhase, institutionId);

        // Act & Assert
        assertEquals(org1, org2);
    }

    @Test
    void equals_WithDifferentFields_ShouldReturnFalse() {
        // Arrange
        ProjectInnovationContributingOrganization org1 = new ProjectInnovationContributingOrganization();
        ProjectInnovationContributingOrganization org2 = new ProjectInnovationContributingOrganization();
        org1.setId(1L);
        org1.setInstitutionId(100L);
        org2.setId(1L);
        org2.setInstitutionId(200L);

        // Act & Assert
        assertNotEquals(org1, org2);
    }

    @Test
    void hashCode_WithSameFields_ShouldReturnSameHashCode() {
        // Arrange
        ProjectInnovationContributingOrganization org1 = new ProjectInnovationContributingOrganization();
        ProjectInnovationContributingOrganization org2 = new ProjectInnovationContributingOrganization();
        org1.setId(1L);
        org1.setInstitutionId(100L);
        org2.setId(1L);
        org2.setInstitutionId(100L);

        // Act & Assert
        assertEquals(org1.hashCode(), org2.hashCode());
    }

    @Test
    void hashCode_WithDifferentFields_ShouldReturnDifferentHashCode() {
        // Arrange
        ProjectInnovationContributingOrganization org1 = new ProjectInnovationContributingOrganization();
        ProjectInnovationContributingOrganization org2 = new ProjectInnovationContributingOrganization();
        org1.setId(1L);
        org1.setInstitutionId(100L);
        org2.setId(2L);
        org2.setInstitutionId(200L);

        // Act & Assert
        assertNotEquals(org1.hashCode(), org2.hashCode());
    }

    @Test
    void hashCode_WithNullFields_ShouldNotThrowException() {
        // Act & Assert
        assertDoesNotThrow(() -> projectInnovationContributingOrganization.hashCode());
    }

    @Test
    void toString_ShouldContainClassName() {
        // Act
        String toString = projectInnovationContributingOrganization.toString();

        // Assert
        assertTrue(toString.contains("ProjectInnovationContributingOrganization"));
    }

    @Test
    void equals_WithNullFields_ShouldHandleNulls() {
        // Arrange
        ProjectInnovationContributingOrganization org1 = new ProjectInnovationContributingOrganization();
        ProjectInnovationContributingOrganization org2 = new ProjectInnovationContributingOrganization();
        org1.setId(null);
        org1.setInstitutionId(null);
        org2.setId(null);
        org2.setInstitutionId(null);

        // Act & Assert
        assertEquals(org1, org2);
    }

    @Test
    void equals_WithOneNullField_ShouldReturnFalse() {
        // Arrange
        ProjectInnovationContributingOrganization org1 = new ProjectInnovationContributingOrganization();
        ProjectInnovationContributingOrganization org2 = new ProjectInnovationContributingOrganization();
        org1.setId(1L);
        org1.setInstitutionId(100L);
        org2.setId(1L);
        org2.setInstitutionId(null);

        // Act & Assert
        assertNotEquals(org1, org2);
    }
}
