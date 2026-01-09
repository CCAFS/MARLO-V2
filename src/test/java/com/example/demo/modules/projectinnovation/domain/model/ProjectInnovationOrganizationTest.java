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
}
