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

    @Test
    void equals_WithSameId_ShouldReturnTrue() {
        // Arrange
        ProjectInnovationCountry c1 = new ProjectInnovationCountry();
        ProjectInnovationCountry c2 = new ProjectInnovationCountry();
        c1.setId(1);
        c2.setId(1);

        // Act & Assert
        assertEquals(c1, c2);
    }

    @Test
    void equals_WithDifferentIds_ShouldReturnFalse() {
        // Arrange
        ProjectInnovationCountry c1 = new ProjectInnovationCountry();
        ProjectInnovationCountry c2 = new ProjectInnovationCountry();
        c1.setId(1);
        c2.setId(2);

        // Act & Assert
        assertNotEquals(c1, c2);
    }

    @Test
    void equals_WithNull_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(projectInnovationCountry, null);
    }

    @Test
    void equals_WithDifferentClass_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(projectInnovationCountry, "not a country");
    }

    @Test
    void equals_WithSameInstance_ShouldReturnTrue() {
        // Act & Assert
        assertEquals(projectInnovationCountry, projectInnovationCountry);
    }

    @Test
    void equals_WithAllFieldsSame_ShouldReturnTrue() {
        // Arrange
        Integer id = 1;
        Long projectInnovationId = 100L;
        Long idCountry = 200L;
        Long idPhase = 1L;

        ProjectInnovationCountry c1 = new ProjectInnovationCountry(id, projectInnovationId, idCountry, idPhase);
        ProjectInnovationCountry c2 = new ProjectInnovationCountry(id, projectInnovationId, idCountry, idPhase);

        // Act & Assert
        assertEquals(c1, c2);
    }

    @Test
    void equals_WithDifferentFields_ShouldReturnFalse() {
        // Arrange
        ProjectInnovationCountry c1 = new ProjectInnovationCountry();
        ProjectInnovationCountry c2 = new ProjectInnovationCountry();
        c1.setId(1);
        c1.setIdCountry(100L);
        c2.setId(1);
        c2.setIdCountry(200L);

        // Act & Assert
        assertNotEquals(c1, c2);
    }

    @Test
    void hashCode_WithSameFields_ShouldReturnSameHashCode() {
        // Arrange
        ProjectInnovationCountry c1 = new ProjectInnovationCountry();
        ProjectInnovationCountry c2 = new ProjectInnovationCountry();
        c1.setId(1);
        c1.setIdCountry(100L);
        c2.setId(1);
        c2.setIdCountry(100L);

        // Act & Assert
        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void hashCode_WithDifferentFields_ShouldReturnDifferentHashCode() {
        // Arrange
        ProjectInnovationCountry c1 = new ProjectInnovationCountry();
        ProjectInnovationCountry c2 = new ProjectInnovationCountry();
        c1.setId(1);
        c1.setIdCountry(100L);
        c2.setId(2);
        c2.setIdCountry(200L);

        // Act & Assert
        assertNotEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void hashCode_WithNullFields_ShouldNotThrowException() {
        // Act & Assert
        assertDoesNotThrow(() -> projectInnovationCountry.hashCode());
    }

    @Test
    void toString_ShouldContainClassName() {
        // Act
        String toString = projectInnovationCountry.toString();

        // Assert
        assertTrue(toString.contains("ProjectInnovationCountry"));
    }

    @Test
    void equals_WithNullFields_ShouldHandleNulls() {
        // Arrange
        ProjectInnovationCountry c1 = new ProjectInnovationCountry();
        ProjectInnovationCountry c2 = new ProjectInnovationCountry();
        c1.setId(null);
        c1.setIdCountry(null);
        c2.setId(null);
        c2.setIdCountry(null);

        // Act & Assert
        assertEquals(c1, c2);
    }

    @Test
    void equals_WithOneNullField_ShouldReturnFalse() {
        // Arrange
        ProjectInnovationCountry c1 = new ProjectInnovationCountry();
        ProjectInnovationCountry c2 = new ProjectInnovationCountry();
        c1.setId(1);
        c1.setIdCountry(100L);
        c2.setId(1);
        c2.setIdCountry(null);

        // Act & Assert
        assertNotEquals(c1, c2);
    }
}
