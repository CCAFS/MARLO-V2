package com.example.demo.modules.projectinnovation.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProjectInnovationComplementarySolutionTest {

    private ProjectInnovationComplementarySolution projectInnovationComplementarySolution;

    @BeforeEach
    void setUp() {
        projectInnovationComplementarySolution = new ProjectInnovationComplementarySolution();
    }

    @Test
    void constructor_NoArgsConstructor_ShouldCreateEmpty() {
        // Act
        ProjectInnovationComplementarySolution newSolution = new ProjectInnovationComplementarySolution();

        // Assert
        assertNull(newSolution.getId());
        assertNull(newSolution.getTitle());
        assertNull(newSolution.getShortTitle());
        assertNull(newSolution.getShortDescription());
        assertNull(newSolution.getProjectInnovationId());
    }

    @Test
    void constructor_AllArgsConstructor_ShouldSetAllFields() {
        // Arrange
        Long id = 1L;
        String title = "Test Title";
        String shortTitle = "Short Title";
        String shortDescription = "Short Description";
        Long projectInnovationTypeId = 100L;
        Boolean isActive = true;
        LocalDateTime activeSince = LocalDateTime.now();
        Long createdBy = 10L;
        Long modifiedBy = 20L;
        String modificationJustification = "Test justification";
        Long idPhase = 1L;
        Long projectInnovationId = 200L;

        // Act
        ProjectInnovationComplementarySolution newSolution = new ProjectInnovationComplementarySolution(
                id, title, shortTitle, shortDescription, projectInnovationTypeId, isActive,
                activeSince, createdBy, modifiedBy, modificationJustification, idPhase, projectInnovationId);

        // Assert
        assertEquals(id, newSolution.getId());
        assertEquals(title, newSolution.getTitle());
        assertEquals(shortTitle, newSolution.getShortTitle());
        assertEquals(shortDescription, newSolution.getShortDescription());
        assertEquals(projectInnovationTypeId, newSolution.getProjectInnovationTypeId());
        assertEquals(isActive, newSolution.getIsActive());
        assertEquals(activeSince, newSolution.getActiveSince());
        assertEquals(createdBy, newSolution.getCreatedBy());
        assertEquals(modifiedBy, newSolution.getModifiedBy());
        assertEquals(modificationJustification, newSolution.getModificationJustification());
        assertEquals(idPhase, newSolution.getIdPhase());
        assertEquals(projectInnovationId, newSolution.getProjectInnovationId());
    }

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        // Arrange
        Long id = 1L;
        String title = "Title";
        String shortTitle = "Short";
        String shortDescription = "Description";
        Long projectInnovationTypeId = 100L;
        Boolean isActive = true;
        LocalDateTime activeSince = LocalDateTime.now();
        Long createdBy = 10L;
        Long modifiedBy = 20L;
        String modificationJustification = "Justification";
        Long idPhase = 1L;
        Long projectInnovationId = 200L;

        // Act
        projectInnovationComplementarySolution.setId(id);
        projectInnovationComplementarySolution.setTitle(title);
        projectInnovationComplementarySolution.setShortTitle(shortTitle);
        projectInnovationComplementarySolution.setShortDescription(shortDescription);
        projectInnovationComplementarySolution.setProjectInnovationTypeId(projectInnovationTypeId);
        projectInnovationComplementarySolution.setIsActive(isActive);
        projectInnovationComplementarySolution.setActiveSince(activeSince);
        projectInnovationComplementarySolution.setCreatedBy(createdBy);
        projectInnovationComplementarySolution.setModifiedBy(modifiedBy);
        projectInnovationComplementarySolution.setModificationJustification(modificationJustification);
        projectInnovationComplementarySolution.setIdPhase(idPhase);
        projectInnovationComplementarySolution.setProjectInnovationId(projectInnovationId);

        // Assert
        assertEquals(id, projectInnovationComplementarySolution.getId());
        assertEquals(title, projectInnovationComplementarySolution.getTitle());
        assertEquals(shortTitle, projectInnovationComplementarySolution.getShortTitle());
        assertEquals(shortDescription, projectInnovationComplementarySolution.getShortDescription());
        assertEquals(projectInnovationTypeId, projectInnovationComplementarySolution.getProjectInnovationTypeId());
        assertEquals(isActive, projectInnovationComplementarySolution.getIsActive());
        assertEquals(activeSince, projectInnovationComplementarySolution.getActiveSince());
        assertEquals(createdBy, projectInnovationComplementarySolution.getCreatedBy());
        assertEquals(modifiedBy, projectInnovationComplementarySolution.getModifiedBy());
        assertEquals(modificationJustification, projectInnovationComplementarySolution.getModificationJustification());
        assertEquals(idPhase, projectInnovationComplementarySolution.getIdPhase());
        assertEquals(projectInnovationId, projectInnovationComplementarySolution.getProjectInnovationId());
    }

    @Test
    void equals_WithSameId_ShouldReturnTrue() {
        // Arrange
        ProjectInnovationComplementarySolution sol1 = new ProjectInnovationComplementarySolution();
        ProjectInnovationComplementarySolution sol2 = new ProjectInnovationComplementarySolution();
        sol1.setId(1L);
        sol2.setId(1L);

        // Act & Assert
        assertEquals(sol1, sol2);
    }

    @Test
    void equals_WithDifferentIds_ShouldReturnFalse() {
        // Arrange
        ProjectInnovationComplementarySolution sol1 = new ProjectInnovationComplementarySolution();
        ProjectInnovationComplementarySolution sol2 = new ProjectInnovationComplementarySolution();
        sol1.setId(1L);
        sol2.setId(2L);

        // Act & Assert
        assertNotEquals(sol1, sol2);
    }

    @Test
    void equals_WithNull_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(projectInnovationComplementarySolution, null);
    }

    @Test
    void equals_WithDifferentClass_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(projectInnovationComplementarySolution, "not a solution");
    }

    @Test
    void equals_WithSameInstance_ShouldReturnTrue() {
        // Act & Assert
        assertEquals(projectInnovationComplementarySolution, projectInnovationComplementarySolution);
    }

    @Test
    void equals_WithAllFieldsSame_ShouldReturnTrue() {
        // Arrange
        Long id = 1L;
        String title = "Title";
        String shortTitle = "Short";
        String shortDescription = "Description";
        Long projectInnovationTypeId = 100L;
        Boolean isActive = true;
        LocalDateTime activeSince = LocalDateTime.now();
        Long createdBy = 10L;
        Long modifiedBy = 20L;
        String modificationJustification = "Justification";
        Long idPhase = 1L;
        Long projectInnovationId = 200L;

        ProjectInnovationComplementarySolution sol1 = new ProjectInnovationComplementarySolution(id, title, shortTitle, shortDescription, projectInnovationTypeId, isActive, activeSince, createdBy, modifiedBy, modificationJustification, idPhase, projectInnovationId);
        ProjectInnovationComplementarySolution sol2 = new ProjectInnovationComplementarySolution(id, title, shortTitle, shortDescription, projectInnovationTypeId, isActive, activeSince, createdBy, modifiedBy, modificationJustification, idPhase, projectInnovationId);

        // Act & Assert
        assertEquals(sol1, sol2);
    }

    @Test
    void equals_WithDifferentFields_ShouldReturnFalse() {
        // Arrange
        ProjectInnovationComplementarySolution sol1 = new ProjectInnovationComplementarySolution();
        ProjectInnovationComplementarySolution sol2 = new ProjectInnovationComplementarySolution();
        sol1.setId(1L);
        sol1.setTitle("Title 1");
        sol2.setId(1L);
        sol2.setTitle("Title 2");

        // Act & Assert
        assertNotEquals(sol1, sol2);
    }

    @Test
    void hashCode_WithSameFields_ShouldReturnSameHashCode() {
        // Arrange
        ProjectInnovationComplementarySolution sol1 = new ProjectInnovationComplementarySolution();
        ProjectInnovationComplementarySolution sol2 = new ProjectInnovationComplementarySolution();
        sol1.setId(1L);
        sol1.setTitle("Title");
        sol2.setId(1L);
        sol2.setTitle("Title");

        // Act & Assert
        assertEquals(sol1.hashCode(), sol2.hashCode());
    }

    @Test
    void hashCode_WithDifferentFields_ShouldReturnDifferentHashCode() {
        // Arrange
        ProjectInnovationComplementarySolution sol1 = new ProjectInnovationComplementarySolution();
        ProjectInnovationComplementarySolution sol2 = new ProjectInnovationComplementarySolution();
        sol1.setId(1L);
        sol1.setTitle("Title 1");
        sol2.setId(2L);
        sol2.setTitle("Title 2");

        // Act & Assert
        assertNotEquals(sol1.hashCode(), sol2.hashCode());
    }

    @Test
    void hashCode_WithNullFields_ShouldNotThrowException() {
        // Act & Assert
        assertDoesNotThrow(() -> projectInnovationComplementarySolution.hashCode());
    }

    @Test
    void toString_ShouldContainClassName() {
        // Act
        String toString = projectInnovationComplementarySolution.toString();

        // Assert
        assertTrue(toString.contains("ProjectInnovationComplementarySolution"));
    }

    @Test
    void equals_WithNullFields_ShouldHandleNulls() {
        // Arrange
        ProjectInnovationComplementarySolution sol1 = new ProjectInnovationComplementarySolution();
        ProjectInnovationComplementarySolution sol2 = new ProjectInnovationComplementarySolution();
        sol1.setId(null);
        sol1.setTitle(null);
        sol2.setId(null);
        sol2.setTitle(null);

        // Act & Assert
        assertEquals(sol1, sol2);
    }

    @Test
    void equals_WithOneNullField_ShouldReturnFalse() {
        // Arrange
        ProjectInnovationComplementarySolution sol1 = new ProjectInnovationComplementarySolution();
        ProjectInnovationComplementarySolution sol2 = new ProjectInnovationComplementarySolution();
        sol1.setId(1L);
        sol1.setTitle("Title");
        sol2.setId(1L);
        sol2.setTitle(null);

        // Act & Assert
        assertNotEquals(sol1, sol2);
    }
}
