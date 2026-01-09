package com.example.demo.modules.projectinnovation.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectInnovationCountryTest {

    private ProjectInnovationCountry projectInnovationCountry;

    @BeforeEach
    void setUp() {
        projectInnovationCountry = new ProjectInnovationCountry();
    }

    @Test
    void constructor_NoArgsConstructor_ShouldCreateEmpty() {
        // Act
        ProjectInnovationCountry newCountry = new ProjectInnovationCountry();

        // Assert
        assertNull(newCountry.getId());
        assertNull(newCountry.getProjectInnovationId());
        assertNull(newCountry.getIdCountry());
        assertNull(newCountry.getIdPhase());
    }

    @Test
    void constructor_AllArgsConstructor_ShouldSetAllFields() {
        // Arrange
        Integer id = 1;
        Long projectInnovationId = 100L;
        Long idCountry = 200L;
        Long idPhase = 1L;

        // Act
        ProjectInnovationCountry newCountry = new ProjectInnovationCountry(id, projectInnovationId, idCountry, idPhase);

        // Assert
        assertEquals(id, newCountry.getId());
        assertEquals(projectInnovationId, newCountry.getProjectInnovationId());
        assertEquals(idCountry, newCountry.getIdCountry());
        assertEquals(idPhase, newCountry.getIdPhase());
    }

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        // Arrange
        Integer id = 1;
        Long projectInnovationId = 100L;
        Long idCountry = 200L;
        Long idPhase = 1L;

        // Act
        projectInnovationCountry.setId(id);
        projectInnovationCountry.setProjectInnovationId(projectInnovationId);
        projectInnovationCountry.setIdCountry(idCountry);
        projectInnovationCountry.setIdPhase(idPhase);

        // Assert
        assertEquals(id, projectInnovationCountry.getId());
        assertEquals(projectInnovationId, projectInnovationCountry.getProjectInnovationId());
        assertEquals(idCountry, projectInnovationCountry.getIdCountry());
        assertEquals(idPhase, projectInnovationCountry.getIdPhase());
    }
}
