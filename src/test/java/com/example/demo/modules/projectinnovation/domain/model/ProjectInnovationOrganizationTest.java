package com.example.demo.modules.projectinnovation.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectInnovationOrganizationTest {

    private ProjectInnovationOrganization projectInnovationOrganization;

    @BeforeEach
    void setUp() {
        projectInnovationOrganization = new ProjectInnovationOrganization();
    }

    @Test
    void constructor_NoArgsConstructor_ShouldCreateEmpty() {
        // Act
        ProjectInnovationOrganization newOrg = new ProjectInnovationOrganization();

        // Assert
        assertNull(newOrg.getId());
        assertNull(newOrg.getProjectInnovationId());
        assertNull(newOrg.getIdPhase());
        assertNull(newOrg.getRepIndOrganizationTypeId());
    }

    @Test
    void constructor_AllArgsConstructor_ShouldSetAllFields() {
        // Arrange
        Long id = 1L;
        Long projectInnovationId = 100L;
        Long idPhase = 1L;
        Long repIndOrganizationTypeId = 200L;

        // Act
        ProjectInnovationOrganization newOrg = new ProjectInnovationOrganization(id, projectInnovationId, idPhase, repIndOrganizationTypeId);

        // Assert
        assertEquals(id, newOrg.getId());
        assertEquals(projectInnovationId, newOrg.getProjectInnovationId());
        assertEquals(idPhase, newOrg.getIdPhase());
        assertEquals(repIndOrganizationTypeId, newOrg.getRepIndOrganizationTypeId());
    }

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        // Arrange
        Long id = 1L;
        Long projectInnovationId = 100L;
        Long idPhase = 1L;
        Long repIndOrganizationTypeId = 200L;

        // Act
        projectInnovationOrganization.setId(id);
        projectInnovationOrganization.setProjectInnovationId(projectInnovationId);
        projectInnovationOrganization.setIdPhase(idPhase);
        projectInnovationOrganization.setRepIndOrganizationTypeId(repIndOrganizationTypeId);

        // Assert
        assertEquals(id, projectInnovationOrganization.getId());
        assertEquals(projectInnovationId, projectInnovationOrganization.getProjectInnovationId());
        assertEquals(idPhase, projectInnovationOrganization.getIdPhase());
        assertEquals(repIndOrganizationTypeId, projectInnovationOrganization.getRepIndOrganizationTypeId());
    }

    @Test
    void equals_WithSameId_ShouldReturnTrue() {
        // Arrange
        ProjectInnovationOrganization org1 = new ProjectInnovationOrganization();
        ProjectInnovationOrganization org2 = new ProjectInnovationOrganization();
        org1.setId(1L);
        org2.setId(1L);

        // Act & Assert
        assertEquals(org1, org2);
    }

    @Test
    void equals_WithDifferentIds_ShouldReturnFalse() {
        // Arrange
        ProjectInnovationOrganization org1 = new ProjectInnovationOrganization();
        ProjectInnovationOrganization org2 = new ProjectInnovationOrganization();
        org1.setId(1L);
        org2.setId(2L);

        // Act & Assert
        assertNotEquals(org1, org2);
    }

    @Test
    void equals_WithNull_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(projectInnovationOrganization, null);
    }

    @Test
    void equals_WithDifferentClass_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(projectInnovationOrganization, "not an org");
    }

    @Test
    void equals_WithSameInstance_ShouldReturnTrue() {
        // Act & Assert
        assertEquals(projectInnovationOrganization, projectInnovationOrganization);
    }

    @Test
    void equals_WithAllFieldsSame_ShouldReturnTrue() {
        // Arrange
        Long id = 1L;
        Long projectInnovationId = 100L;
        Long idPhase = 1L;
        Long repIndOrganizationTypeId = 200L;

        ProjectInnovationOrganization org1 = new ProjectInnovationOrganization(id, projectInnovationId, idPhase, repIndOrganizationTypeId);
        ProjectInnovationOrganization org2 = new ProjectInnovationOrganization(id, projectInnovationId, idPhase, repIndOrganizationTypeId);

        // Act & Assert
        assertEquals(org1, org2);
    }

    @Test
    void equals_WithDifferentFields_ShouldReturnFalse() {
        // Arrange
        ProjectInnovationOrganization org1 = new ProjectInnovationOrganization();
        ProjectInnovationOrganization org2 = new ProjectInnovationOrganization();
        org1.setId(1L);
        org1.setRepIndOrganizationTypeId(100L);
        org2.setId(1L);
        org2.setRepIndOrganizationTypeId(200L);

        // Act & Assert
        assertNotEquals(org1, org2);
    }

    @Test
    void hashCode_WithSameFields_ShouldReturnSameHashCode() {
        // Arrange
        ProjectInnovationOrganization org1 = new ProjectInnovationOrganization();
        ProjectInnovationOrganization org2 = new ProjectInnovationOrganization();
        org1.setId(1L);
        org1.setRepIndOrganizationTypeId(100L);
        org2.setId(1L);
        org2.setRepIndOrganizationTypeId(100L);

        // Act & Assert
        assertEquals(org1.hashCode(), org2.hashCode());
    }

    @Test
    void hashCode_WithDifferentFields_ShouldReturnDifferentHashCode() {
        // Arrange
        ProjectInnovationOrganization org1 = new ProjectInnovationOrganization();
        ProjectInnovationOrganization org2 = new ProjectInnovationOrganization();
        org1.setId(1L);
        org1.setRepIndOrganizationTypeId(100L);
        org2.setId(2L);
        org2.setRepIndOrganizationTypeId(200L);

        // Act & Assert
        assertNotEquals(org1.hashCode(), org2.hashCode());
    }

    @Test
    void hashCode_WithNullFields_ShouldNotThrowException() {
        // Act & Assert
        assertDoesNotThrow(() -> projectInnovationOrganization.hashCode());
    }

    @Test
    void toString_ShouldContainClassName() {
        // Act
        String toString = projectInnovationOrganization.toString();

        // Assert
        assertTrue(toString.contains("ProjectInnovationOrganization"));
    }

    @Test
    void equals_WithNullFields_ShouldHandleNulls() {
        // Arrange
        ProjectInnovationOrganization org1 = new ProjectInnovationOrganization();
        ProjectInnovationOrganization org2 = new ProjectInnovationOrganization();
        org1.setId(null);
        org1.setRepIndOrganizationTypeId(null);
        org2.setId(null);
        org2.setRepIndOrganizationTypeId(null);

        // Act & Assert
        assertEquals(org1, org2);
    }

    @Test
    void equals_WithOneNullField_ShouldReturnFalse() {
        // Arrange
        ProjectInnovationOrganization org1 = new ProjectInnovationOrganization();
        ProjectInnovationOrganization org2 = new ProjectInnovationOrganization();
        org1.setId(1L);
        org1.setRepIndOrganizationTypeId(100L);
        org2.setId(1L);
        org2.setRepIndOrganizationTypeId(null);

        // Act & Assert
        assertNotEquals(org1, org2);
    }
}
