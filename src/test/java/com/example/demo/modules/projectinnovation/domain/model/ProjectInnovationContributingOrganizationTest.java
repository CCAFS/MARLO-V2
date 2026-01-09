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
}
