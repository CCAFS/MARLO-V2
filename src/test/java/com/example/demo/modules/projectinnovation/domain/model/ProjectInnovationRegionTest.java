package com.example.demo.modules.projectinnovation.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectInnovationRegionTest {

    private ProjectInnovationRegion projectInnovationRegion;

    @BeforeEach
    void setUp() {
        projectInnovationRegion = new ProjectInnovationRegion();
    }

    @Test
    void constructor_NoArgsConstructor_ShouldCreateEmpty() {
        // Act
        ProjectInnovationRegion newRegion = new ProjectInnovationRegion();

        // Assert
        assertNull(newRegion.getId());
        assertNull(newRegion.getProjectInnovationId());
        assertNull(newRegion.getIdRegion());
        assertNull(newRegion.getIdPhase());
    }

    @Test
    void constructor_AllArgsConstructor_ShouldSetAllFields() {
        // Arrange
        Integer id = 1;
        Long projectInnovationId = 100L;
        Long idRegion = 200L;
        Long idPhase = 1L;

        // Act
        ProjectInnovationRegion newRegion = new ProjectInnovationRegion(id, projectInnovationId, idRegion, idPhase);

        // Assert
        assertEquals(id, newRegion.getId());
        assertEquals(projectInnovationId, newRegion.getProjectInnovationId());
        assertEquals(idRegion, newRegion.getIdRegion());
        assertEquals(idPhase, newRegion.getIdPhase());
    }

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        // Arrange
        Integer id = 1;
        Long projectInnovationId = 100L;
        Long idRegion = 200L;
        Long idPhase = 1L;

        // Act
        projectInnovationRegion.setId(id);
        projectInnovationRegion.setProjectInnovationId(projectInnovationId);
        projectInnovationRegion.setIdRegion(idRegion);
        projectInnovationRegion.setIdPhase(idPhase);

        // Assert
        assertEquals(id, projectInnovationRegion.getId());
        assertEquals(projectInnovationId, projectInnovationRegion.getProjectInnovationId());
        assertEquals(idRegion, projectInnovationRegion.getIdRegion());
        assertEquals(idPhase, projectInnovationRegion.getIdPhase());
    }
}
