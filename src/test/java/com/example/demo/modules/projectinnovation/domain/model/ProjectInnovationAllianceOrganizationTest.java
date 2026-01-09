package com.example.demo.modules.projectinnovation.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProjectInnovationAllianceOrganizationTest {

    private ProjectInnovationAllianceOrganization projectInnovationAllianceOrganization;

    @BeforeEach
    void setUp() {
        projectInnovationAllianceOrganization = new ProjectInnovationAllianceOrganization();
    }

    @Test
    void constructor_NoArgsConstructor_ShouldCreateEmpty() {
        // Act
        ProjectInnovationAllianceOrganization newOrg = new ProjectInnovationAllianceOrganization();

        // Assert
        assertNull(newOrg.getId());
        assertNull(newOrg.getProjectInnovationId());
        assertNull(newOrg.getIdPhase());
        assertNull(newOrg.getInstitutionTypeId());
        assertNull(newOrg.getOrganizationName());
    }

    @Test
    void constructor_AllArgsConstructor_ShouldSetAllFields() {
        // Arrange
        Long id = 1L;
        Long projectInnovationId = 100L;
        Long idPhase = 1L;
        Long institutionTypeId = 200L;
        String organizationName = "Test Organization";
        Boolean isScalingPartner = true;
        Boolean isActive = true;
        LocalDateTime activeSince = LocalDateTime.now();
        Long createdBy = 10L;
        Long modifiedBy = 20L;
        String modificationJustification = "Test justification";
        Long institutionId = 300L;
        Integer number = 1;

        // Act
        ProjectInnovationAllianceOrganization newOrg = new ProjectInnovationAllianceOrganization(
                id, projectInnovationId, idPhase, institutionTypeId, organizationName,
                isScalingPartner, isActive, activeSince, createdBy, modifiedBy,
                modificationJustification, institutionId, number);

        // Assert
        assertEquals(id, newOrg.getId());
        assertEquals(projectInnovationId, newOrg.getProjectInnovationId());
        assertEquals(idPhase, newOrg.getIdPhase());
        assertEquals(institutionTypeId, newOrg.getInstitutionTypeId());
        assertEquals(organizationName, newOrg.getOrganizationName());
        assertEquals(isScalingPartner, newOrg.getIsScalingPartner());
        assertEquals(isActive, newOrg.getIsActive());
        assertEquals(activeSince, newOrg.getActiveSince());
        assertEquals(createdBy, newOrg.getCreatedBy());
        assertEquals(modifiedBy, newOrg.getModifiedBy());
        assertEquals(modificationJustification, newOrg.getModificationJustification());
        assertEquals(institutionId, newOrg.getInstitutionId());
        assertEquals(number, newOrg.getNumber());
    }

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        // Arrange
        Long id = 1L;
        Long projectInnovationId = 100L;
        Long idPhase = 1L;
        Long institutionTypeId = 200L;
        String organizationName = "Organization Name";
        Boolean isScalingPartner = true;
        Boolean isActive = true;
        LocalDateTime activeSince = LocalDateTime.now();
        Long createdBy = 10L;
        Long modifiedBy = 20L;
        String modificationJustification = "Justification";
        Long institutionId = 300L;
        Integer number = 1;

        // Act
        projectInnovationAllianceOrganization.setId(id);
        projectInnovationAllianceOrganization.setProjectInnovationId(projectInnovationId);
        projectInnovationAllianceOrganization.setIdPhase(idPhase);
        projectInnovationAllianceOrganization.setInstitutionTypeId(institutionTypeId);
        projectInnovationAllianceOrganization.setOrganizationName(organizationName);
        projectInnovationAllianceOrganization.setIsScalingPartner(isScalingPartner);
        projectInnovationAllianceOrganization.setIsActive(isActive);
        projectInnovationAllianceOrganization.setActiveSince(activeSince);
        projectInnovationAllianceOrganization.setCreatedBy(createdBy);
        projectInnovationAllianceOrganization.setModifiedBy(modifiedBy);
        projectInnovationAllianceOrganization.setModificationJustification(modificationJustification);
        projectInnovationAllianceOrganization.setInstitutionId(institutionId);
        projectInnovationAllianceOrganization.setNumber(number);

        // Assert
        assertEquals(id, projectInnovationAllianceOrganization.getId());
        assertEquals(projectInnovationId, projectInnovationAllianceOrganization.getProjectInnovationId());
        assertEquals(idPhase, projectInnovationAllianceOrganization.getIdPhase());
        assertEquals(institutionTypeId, projectInnovationAllianceOrganization.getInstitutionTypeId());
        assertEquals(organizationName, projectInnovationAllianceOrganization.getOrganizationName());
        assertEquals(isScalingPartner, projectInnovationAllianceOrganization.getIsScalingPartner());
        assertEquals(isActive, projectInnovationAllianceOrganization.getIsActive());
        assertEquals(activeSince, projectInnovationAllianceOrganization.getActiveSince());
        assertEquals(createdBy, projectInnovationAllianceOrganization.getCreatedBy());
        assertEquals(modifiedBy, projectInnovationAllianceOrganization.getModifiedBy());
        assertEquals(modificationJustification, projectInnovationAllianceOrganization.getModificationJustification());
        assertEquals(institutionId, projectInnovationAllianceOrganization.getInstitutionId());
        assertEquals(number, projectInnovationAllianceOrganization.getNumber());
    }

    @Test
    void equals_WithSameId_ShouldReturnTrue() {
        // Arrange
        ProjectInnovationAllianceOrganization org1 = new ProjectInnovationAllianceOrganization();
        ProjectInnovationAllianceOrganization org2 = new ProjectInnovationAllianceOrganization();
        org1.setId(1L);
        org2.setId(1L);

        // Act & Assert
        assertEquals(org1, org2);
    }

    @Test
    void equals_WithDifferentIds_ShouldReturnFalse() {
        // Arrange
        ProjectInnovationAllianceOrganization org1 = new ProjectInnovationAllianceOrganization();
        ProjectInnovationAllianceOrganization org2 = new ProjectInnovationAllianceOrganization();
        org1.setId(1L);
        org2.setId(2L);

        // Act & Assert
        assertNotEquals(org1, org2);
    }

    @Test
    void equals_WithNull_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(projectInnovationAllianceOrganization, null);
    }

    @Test
    void equals_WithDifferentClass_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(projectInnovationAllianceOrganization, "not an org");
    }

    @Test
    void equals_WithSameInstance_ShouldReturnTrue() {
        // Act & Assert
        assertEquals(projectInnovationAllianceOrganization, projectInnovationAllianceOrganization);
    }

    @Test
    void equals_WithAllFieldsSame_ShouldReturnTrue() {
        // Arrange
        Long id = 1L;
        Long projectInnovationId = 100L;
        Long idPhase = 1L;
        Long institutionTypeId = 200L;
        String organizationName = "Org Name";
        Boolean isScalingPartner = true;
        Boolean isActive = true;
        LocalDateTime activeSince = LocalDateTime.now();
        Long createdBy = 10L;
        Long modifiedBy = 20L;
        String modificationJustification = "Justification";
        Long institutionId = 300L;
        Integer number = 1;

        ProjectInnovationAllianceOrganization org1 = new ProjectInnovationAllianceOrganization(id, projectInnovationId, idPhase, institutionTypeId, organizationName, isScalingPartner, isActive, activeSince, createdBy, modifiedBy, modificationJustification, institutionId, number);
        ProjectInnovationAllianceOrganization org2 = new ProjectInnovationAllianceOrganization(id, projectInnovationId, idPhase, institutionTypeId, organizationName, isScalingPartner, isActive, activeSince, createdBy, modifiedBy, modificationJustification, institutionId, number);

        // Act & Assert
        assertEquals(org1, org2);
    }

    @Test
    void equals_WithDifferentFields_ShouldReturnFalse() {
        // Arrange
        ProjectInnovationAllianceOrganization org1 = new ProjectInnovationAllianceOrganization();
        ProjectInnovationAllianceOrganization org2 = new ProjectInnovationAllianceOrganization();
        org1.setId(1L);
        org1.setOrganizationName("Name 1");
        org2.setId(1L);
        org2.setOrganizationName("Name 2");

        // Act & Assert
        assertNotEquals(org1, org2);
    }

    @Test
    void hashCode_WithSameFields_ShouldReturnSameHashCode() {
        // Arrange
        ProjectInnovationAllianceOrganization org1 = new ProjectInnovationAllianceOrganization();
        ProjectInnovationAllianceOrganization org2 = new ProjectInnovationAllianceOrganization();
        org1.setId(1L);
        org1.setOrganizationName("Name");
        org2.setId(1L);
        org2.setOrganizationName("Name");

        // Act & Assert
        assertEquals(org1.hashCode(), org2.hashCode());
    }

    @Test
    void hashCode_WithDifferentFields_ShouldReturnDifferentHashCode() {
        // Arrange
        ProjectInnovationAllianceOrganization org1 = new ProjectInnovationAllianceOrganization();
        ProjectInnovationAllianceOrganization org2 = new ProjectInnovationAllianceOrganization();
        org1.setId(1L);
        org1.setOrganizationName("Name 1");
        org2.setId(2L);
        org2.setOrganizationName("Name 2");

        // Act & Assert
        assertNotEquals(org1.hashCode(), org2.hashCode());
    }

    @Test
    void hashCode_WithNullFields_ShouldNotThrowException() {
        // Act & Assert
        assertDoesNotThrow(() -> projectInnovationAllianceOrganization.hashCode());
    }

    @Test
    void toString_ShouldContainClassName() {
        // Act
        String toString = projectInnovationAllianceOrganization.toString();

        // Assert
        assertTrue(toString.contains("ProjectInnovationAllianceOrganization"));
    }

    @Test
    void equals_WithNullFields_ShouldHandleNulls() {
        // Arrange
        ProjectInnovationAllianceOrganization org1 = new ProjectInnovationAllianceOrganization();
        ProjectInnovationAllianceOrganization org2 = new ProjectInnovationAllianceOrganization();
        org1.setId(null);
        org1.setOrganizationName(null);
        org2.setId(null);
        org2.setOrganizationName(null);

        // Act & Assert
        assertEquals(org1, org2);
    }

    @Test
    void equals_WithOneNullField_ShouldReturnFalse() {
        // Arrange
        ProjectInnovationAllianceOrganization org1 = new ProjectInnovationAllianceOrganization();
        ProjectInnovationAllianceOrganization org2 = new ProjectInnovationAllianceOrganization();
        org1.setId(1L);
        org1.setOrganizationName("Name");
        org2.setId(1L);
        org2.setOrganizationName(null);

        // Act & Assert
        assertNotEquals(org1, org2);
    }
}
