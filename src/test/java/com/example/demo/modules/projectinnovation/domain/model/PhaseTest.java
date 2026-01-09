package com.example.demo.modules.projectinnovation.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhaseTest {

    private Phase phase;

    @BeforeEach
    void setUp() {
        phase = new Phase();
    }

    @Test
    void constructor_NoArgsConstructor_ShouldCreateEmptyPhase() {
        // Act
        Phase newPhase = new Phase();

        // Assert
        assertNull(newPhase.getId());
        assertNull(newPhase.getDescription());
        assertNull(newPhase.getYear());
    }

    @Test
    void constructor_AllArgsConstructor_ShouldSetAllFields() {
        // Act
        Phase newPhase = new Phase(1L, "Test Description", 2024);

        // Assert
        assertEquals(1L, newPhase.getId());
        assertEquals("Test Description", newPhase.getDescription());
        assertEquals(2024, newPhase.getYear());
    }

    @Test
    void getCycle_WhenDescriptionIsNull_ShouldReturnDefault() {
        // Arrange
        phase.setDescription(null);

        // Act
        String cycle = phase.getCycle();

        // Assert
        assertEquals("Reporting", cycle);
    }

    @Test
    void getCycle_WhenDescriptionContainsReporting_ShouldReturnReporting() {
        // Arrange
        phase.setDescription("Annual Reporting Phase");

        // Act
        String cycle = phase.getCycle();

        // Assert
        assertEquals("Reporting", cycle);
    }

    @Test
    void getCycle_WhenDescriptionContainsReportingLowerCase_ShouldReturnReporting() {
        // Arrange
        phase.setDescription("reporting phase");

        // Act
        String cycle = phase.getCycle();

        // Assert
        assertEquals("Reporting", cycle);
    }

    @Test
    void getCycle_WhenDescriptionContainsPlanning_ShouldReturnPlanning() {
        // Arrange
        phase.setDescription("Planning Phase 2024");

        // Act
        String cycle = phase.getCycle();

        // Assert
        assertEquals("Planning", cycle);
    }

    @Test
    void getCycle_WhenDescriptionContainsPlanningLowerCase_ShouldReturnPlanning() {
        // Arrange
        phase.setDescription("planning");

        // Act
        String cycle = phase.getCycle();

        // Assert
        assertEquals("Planning", cycle);
    }

    @Test
    void getCycle_WhenDescriptionDoesNotContainReportingOrPlanning_ShouldReturnFullDescription() {
        // Arrange
        String description = "Other Phase Description";
        phase.setDescription(description);

        // Act
        String cycle = phase.getCycle();

        // Assert
        assertEquals(description, cycle);
    }

    @Test
    void getCycle_WhenDescriptionIsEmpty_ShouldReturnEmpty() {
        // Arrange
        phase.setDescription("");

        // Act
        String cycle = phase.getCycle();

        // Assert
        assertEquals("", cycle);
    }

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        // Arrange
        Long id = 1L;
        String description = "Test Phase";
        Integer year = 2024;

        // Act
        phase.setId(id);
        phase.setDescription(description);
        phase.setYear(year);

        // Assert
        assertEquals(id, phase.getId());
        assertEquals(description, phase.getDescription());
        assertEquals(year, phase.getYear());
    }

    @Test
    void getCycle_WhenDescriptionContainsBothReportingAndPlanning_ShouldReturnReporting() {
        // Arrange - Reporting comes first in the if-else chain
        phase.setDescription("Reporting and Planning Phase");

        // Act
        String cycle = phase.getCycle();

        // Assert
        assertEquals("Reporting", cycle);
    }

    @Test
    void getCycle_WhenDescriptionContainsReportingUpperCase_ShouldReturnReporting() {
        // Arrange
        phase.setDescription("REPORTING PHASE");

        // Act
        String cycle = phase.getCycle();

        // Assert
        assertEquals("Reporting", cycle);
    }

    @Test
    void getCycle_WhenDescriptionContainsPlanningUpperCase_ShouldReturnPlanning() {
        // Arrange
        phase.setDescription("PLANNING PHASE");

        // Act
        String cycle = phase.getCycle();

        // Assert
        assertEquals("Planning", cycle);
    }

    @Test
    void getCycle_WhenDescriptionContainsMixedCase_ShouldReturnCorrectCycle() {
        // Arrange
        phase.setDescription("RePoRtInG PhAsE");

        // Act
        String cycle = phase.getCycle();

        // Assert
        assertEquals("Reporting", cycle);
    }

    @Test
    void getCycle_WhenDescriptionIsWhitespaceOnly_ShouldReturnWhitespace() {
        // Arrange
        phase.setDescription("   ");

        // Act
        String cycle = phase.getCycle();

        // Assert
        assertEquals("   ", cycle);
    }

    @Test
    void equals_WithSameId_ShouldReturnTrue() {
        // Arrange
        Phase p1 = new Phase();
        Phase p2 = new Phase();
        p1.setId(1L);
        p2.setId(1L);

        // Act & Assert
        assertEquals(p1, p2);
    }

    @Test
    void equals_WithDifferentIds_ShouldReturnFalse() {
        // Arrange
        Phase p1 = new Phase();
        Phase p2 = new Phase();
        p1.setId(1L);
        p2.setId(2L);

        // Act & Assert
        assertNotEquals(p1, p2);
    }

    @Test
    void equals_WithNull_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(phase, null);
    }

    @Test
    void equals_WithDifferentClass_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(phase, "not a phase");
    }

    @Test
    void equals_WithSameInstance_ShouldReturnTrue() {
        // Act & Assert
        assertEquals(phase, phase);
    }

    @Test
    void equals_WithAllFieldsSame_ShouldReturnTrue() {
        // Arrange
        Long id = 1L;
        String description = "Description";
        Integer year = 2024;

        Phase p1 = new Phase(id, description, year);
        Phase p2 = new Phase(id, description, year);

        // Act & Assert
        assertEquals(p1, p2);
    }

    @Test
    void equals_WithDifferentFields_ShouldReturnFalse() {
        // Arrange
        Phase p1 = new Phase();
        Phase p2 = new Phase();
        p1.setId(1L);
        p1.setDescription("Description 1");
        p2.setId(1L);
        p2.setDescription("Description 2");

        // Act & Assert
        assertNotEquals(p1, p2);
    }

    @Test
    void hashCode_WithSameFields_ShouldReturnSameHashCode() {
        // Arrange
        Phase p1 = new Phase();
        Phase p2 = new Phase();
        p1.setId(1L);
        p1.setDescription("Description");
        p2.setId(1L);
        p2.setDescription("Description");

        // Act & Assert
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void hashCode_WithDifferentFields_ShouldReturnDifferentHashCode() {
        // Arrange
        Phase p1 = new Phase();
        Phase p2 = new Phase();
        p1.setId(1L);
        p1.setDescription("Description 1");
        p2.setId(2L);
        p2.setDescription("Description 2");

        // Act & Assert
        assertNotEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void hashCode_WithNullFields_ShouldNotThrowException() {
        // Act & Assert
        assertDoesNotThrow(() -> phase.hashCode());
    }

    @Test
    void toString_ShouldContainClassName() {
        // Act
        String toString = phase.toString();

        // Assert
        assertTrue(toString.contains("Phase"));
    }

    @Test
    void equals_WithNullFields_ShouldHandleNulls() {
        // Arrange
        Phase p1 = new Phase();
        Phase p2 = new Phase();
        p1.setId(null);
        p1.setDescription(null);
        p2.setId(null);
        p2.setDescription(null);

        // Act & Assert
        assertEquals(p1, p2);
    }

    @Test
    void equals_WithOneNullField_ShouldReturnFalse() {
        // Arrange
        Phase p1 = new Phase();
        Phase p2 = new Phase();
        p1.setId(1L);
        p1.setDescription("Description");
        p2.setId(1L);
        p2.setDescription(null);

        // Act & Assert
        assertNotEquals(p1, p2);
    }
}
