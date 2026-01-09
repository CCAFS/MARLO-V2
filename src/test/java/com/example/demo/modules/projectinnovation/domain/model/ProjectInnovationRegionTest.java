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

    @Test
    void equals_WithSameId_ShouldReturnTrue() {
        // Arrange
        ProjectInnovationRegion r1 = new ProjectInnovationRegion();
        ProjectInnovationRegion r2 = new ProjectInnovationRegion();
        r1.setId(1);
        r2.setId(1);

        // Act & Assert
        assertEquals(r1, r2);
    }

    @Test
    void equals_WithDifferentIds_ShouldReturnFalse() {
        // Arrange
        ProjectInnovationRegion r1 = new ProjectInnovationRegion();
        ProjectInnovationRegion r2 = new ProjectInnovationRegion();
        r1.setId(1);
        r2.setId(2);

        // Act & Assert
        assertNotEquals(r1, r2);
    }

    @Test
    void equals_WithNull_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(projectInnovationRegion, null);
    }

    @Test
    void equals_WithDifferentClass_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(projectInnovationRegion, "not a region");
    }

    @Test
    void equals_WithSameInstance_ShouldReturnTrue() {
        // Act & Assert
        assertEquals(projectInnovationRegion, projectInnovationRegion);
    }

    @Test
    void equals_WithAllFieldsSame_ShouldReturnTrue() {
        // Arrange
        Integer id = 1;
        Long projectInnovationId = 100L;
        Long idRegion = 200L;
        Long idPhase = 1L;

        ProjectInnovationRegion r1 = new ProjectInnovationRegion(id, projectInnovationId, idRegion, idPhase);
        ProjectInnovationRegion r2 = new ProjectInnovationRegion(id, projectInnovationId, idRegion, idPhase);

        // Act & Assert
        assertEquals(r1, r2);
    }

    @Test
    void equals_WithDifferentFields_ShouldReturnFalse() {
        // Arrange
        ProjectInnovationRegion r1 = new ProjectInnovationRegion();
        ProjectInnovationRegion r2 = new ProjectInnovationRegion();
        r1.setId(1);
        r1.setIdRegion(100L);
        r2.setId(1);
        r2.setIdRegion(200L);

        // Act & Assert
        assertNotEquals(r1, r2);
    }

    @Test
    void hashCode_WithSameFields_ShouldReturnSameHashCode() {
        // Arrange
        ProjectInnovationRegion r1 = new ProjectInnovationRegion();
        ProjectInnovationRegion r2 = new ProjectInnovationRegion();
        r1.setId(1);
        r1.setIdRegion(100L);
        r2.setId(1);
        r2.setIdRegion(100L);

        // Act & Assert
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void hashCode_WithDifferentFields_ShouldReturnDifferentHashCode() {
        // Arrange
        ProjectInnovationRegion r1 = new ProjectInnovationRegion();
        ProjectInnovationRegion r2 = new ProjectInnovationRegion();
        r1.setId(1);
        r1.setIdRegion(100L);
        r2.setId(2);
        r2.setIdRegion(200L);

        // Act & Assert
        assertNotEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void hashCode_WithNullFields_ShouldNotThrowException() {
        // Act & Assert
        assertDoesNotThrow(() -> projectInnovationRegion.hashCode());
    }

    @Test
    void toString_ShouldContainClassName() {
        // Act
        String toString = projectInnovationRegion.toString();

        // Assert
        assertTrue(toString.contains("ProjectInnovationRegion"));
    }

    @Test
    void equals_WithNullFields_ShouldHandleNulls() {
        // Arrange
        ProjectInnovationRegion r1 = new ProjectInnovationRegion();
        ProjectInnovationRegion r2 = new ProjectInnovationRegion();
        r1.setId(null);
        r1.setIdRegion(null);
        r2.setId(null);
        r2.setIdRegion(null);

        // Act & Assert
        assertEquals(r1, r2);
    }

    @Test
    void equals_WithOneNullField_ShouldReturnFalse() {
        // Arrange
        ProjectInnovationRegion r1 = new ProjectInnovationRegion();
        ProjectInnovationRegion r2 = new ProjectInnovationRegion();
        r1.setId(1);
        r1.setIdRegion(100L);
        r2.setId(1);
        r2.setIdRegion(null);

        // Act & Assert
        assertNotEquals(r1, r2);
    }
}
