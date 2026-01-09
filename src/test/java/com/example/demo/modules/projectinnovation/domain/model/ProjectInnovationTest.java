package com.example.demo.modules.projectinnovation.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProjectInnovationTest {

    private ProjectInnovation projectInnovation;

    @BeforeEach
    void setUp() {
        projectInnovation = new ProjectInnovation();
    }

    @Test
    void onCreate_WhenActiveSinceIsNull_ShouldSetToNow() {
        // Arrange
        projectInnovation.setActiveSince(null);
        projectInnovation.setIsActive(null);

        // Act - Simulate @PrePersist
        projectInnovation.onCreate();

        // Assert
        assertNotNull(projectInnovation.getActiveSince());
        assertTrue(projectInnovation.getIsActive());
    }

    @Test
    void onCreate_WhenIsActiveIsNull_ShouldSetToTrue() {
        // Arrange
        projectInnovation.setActiveSince(LocalDateTime.now());
        projectInnovation.setIsActive(null);

        // Act
        projectInnovation.onCreate();

        // Assert
        assertTrue(projectInnovation.getIsActive());
    }

    @Test
    void onCreate_WhenBothSet_ShouldNotChange() {
        // Arrange
        LocalDateTime customDate = LocalDateTime.of(2024, 1, 1, 12, 0);
        projectInnovation.setActiveSince(customDate);
        projectInnovation.setIsActive(false);

        // Act
        projectInnovation.onCreate();

        // Assert
        assertEquals(customDate, projectInnovation.getActiveSince());
        assertFalse(projectInnovation.getIsActive());
    }

    @Test
    void onUpdate_ShouldUpdateActiveSince() {
        // Arrange
        LocalDateTime originalDate = LocalDateTime.of(2024, 1, 1, 12, 0);
        projectInnovation.setActiveSince(originalDate);

        // Act
        projectInnovation.onUpdate();

        // Assert
        assertNotNull(projectInnovation.getActiveSince());
        assertNotEquals(originalDate, projectInnovation.getActiveSince());
    }

    @Test
    void constructor_WithAllArgsConstructor_ShouldSetAllFields() {
        // Arrange & Act
        LocalDateTime date = LocalDateTime.now();
        ProjectInnovation innovation = new ProjectInnovation(
            1L, 100L, true, date, 1L, 2L, "Justification"
        );

        // Assert
        assertEquals(1L, innovation.getId());
        assertEquals(100L, innovation.getProjectId());
        assertTrue(innovation.getIsActive());
        assertEquals(date, innovation.getActiveSince());
        assertEquals(1L, innovation.getCreatedBy());
        assertEquals(2L, innovation.getModifiedBy());
        assertEquals("Justification", innovation.getModificationJustification());
    }

    @Test
    void constructor_WithNoArgsConstructor_ShouldCreateEmpty() {
        // Act
        ProjectInnovation innovation = new ProjectInnovation();

        // Assert
        assertNull(innovation.getId());
        assertNull(innovation.getProjectId());
        assertTrue(innovation.getIsActive());
        assertNull(innovation.getActiveSince());
    }

    @Test
    void onCreate_WhenActiveSinceIsSetButIsActiveIsNull_ShouldSetIsActiveToTrue() {
        // Arrange
        LocalDateTime customDate = LocalDateTime.of(2024, 1, 1, 12, 0);
        projectInnovation.setActiveSince(customDate);
        projectInnovation.setIsActive(null);

        // Act
        projectInnovation.onCreate();

        // Assert
        assertEquals(customDate, projectInnovation.getActiveSince());
        assertTrue(projectInnovation.getIsActive());
    }

    @Test
    void onCreate_WhenIsActiveIsSetButActiveSinceIsNull_ShouldSetActiveSince() {
        // Arrange
        projectInnovation.setActiveSince(null);
        projectInnovation.setIsActive(false);

        // Act
        projectInnovation.onCreate();

        // Assert
        assertNotNull(projectInnovation.getActiveSince());
        assertFalse(projectInnovation.getIsActive());
    }

    @Test
    void onUpdate_WhenCalledMultipleTimes_ShouldUpdateEachTime() {
        // Arrange
        LocalDateTime firstDate = LocalDateTime.of(2024, 1, 1, 12, 0);
        projectInnovation.setActiveSince(firstDate);

        // Act
        projectInnovation.onUpdate();
        LocalDateTime secondDate = projectInnovation.getActiveSince();
        try {
            Thread.sleep(10); // Small delay to ensure different timestamps
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        projectInnovation.onUpdate();
        LocalDateTime thirdDate = projectInnovation.getActiveSince();

        // Assert
        assertNotNull(secondDate);
        assertNotNull(thirdDate);
        assertNotEquals(firstDate, secondDate);
        assertNotEquals(secondDate, thirdDate);
    }

    @Test
    void equals_WithSameId_ShouldReturnTrue() {
        // Arrange
        ProjectInnovation pi1 = new ProjectInnovation();
        ProjectInnovation pi2 = new ProjectInnovation();
        pi1.setId(1L);
        pi2.setId(1L);

        // Act & Assert
        assertEquals(pi1, pi2);
    }

    @Test
    void equals_WithDifferentIds_ShouldReturnFalse() {
        // Arrange
        ProjectInnovation pi1 = new ProjectInnovation();
        ProjectInnovation pi2 = new ProjectInnovation();
        pi1.setId(1L);
        pi2.setId(2L);

        // Act & Assert
        assertNotEquals(pi1, pi2);
    }

    @Test
    void equals_WithNull_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(projectInnovation, null);
    }

    @Test
    void equals_WithDifferentClass_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(projectInnovation, "not an innovation");
    }

    @Test
    void equals_WithSameInstance_ShouldReturnTrue() {
        // Act & Assert
        assertEquals(projectInnovation, projectInnovation);
    }

    @Test
    void equals_WithAllFieldsSame_ShouldReturnTrue() {
        // Arrange
        Long id = 1L;
        Long projectId = 100L;
        Boolean isActive = true;
        LocalDateTime activeSince = LocalDateTime.now();
        Long createdBy = 10L;
        Long modifiedBy = 20L;
        String modificationJustification = "Justification";

        ProjectInnovation pi1 = new ProjectInnovation(id, projectId, isActive, activeSince, createdBy, modifiedBy, modificationJustification);
        ProjectInnovation pi2 = new ProjectInnovation(id, projectId, isActive, activeSince, createdBy, modifiedBy, modificationJustification);

        // Act & Assert
        assertEquals(pi1, pi2);
    }

    @Test
    void equals_WithDifferentFields_ShouldReturnFalse() {
        // Arrange
        ProjectInnovation pi1 = new ProjectInnovation();
        ProjectInnovation pi2 = new ProjectInnovation();
        pi1.setId(1L);
        pi1.setProjectId(100L);
        pi2.setId(1L);
        pi2.setProjectId(200L);

        // Act & Assert
        assertNotEquals(pi1, pi2);
    }

    @Test
    void hashCode_WithSameFields_ShouldReturnSameHashCode() {
        // Arrange
        ProjectInnovation pi1 = new ProjectInnovation();
        ProjectInnovation pi2 = new ProjectInnovation();
        pi1.setId(1L);
        pi1.setProjectId(100L);
        pi2.setId(1L);
        pi2.setProjectId(100L);

        // Act & Assert
        assertEquals(pi1.hashCode(), pi2.hashCode());
    }

    @Test
    void hashCode_WithDifferentFields_ShouldReturnDifferentHashCode() {
        // Arrange
        ProjectInnovation pi1 = new ProjectInnovation();
        ProjectInnovation pi2 = new ProjectInnovation();
        pi1.setId(1L);
        pi1.setProjectId(100L);
        pi2.setId(2L);
        pi2.setProjectId(200L);

        // Act & Assert
        assertNotEquals(pi1.hashCode(), pi2.hashCode());
    }

    @Test
    void hashCode_WithNullFields_ShouldNotThrowException() {
        // Act & Assert
        assertDoesNotThrow(() -> projectInnovation.hashCode());
    }

    @Test
    void toString_ShouldContainClassName() {
        // Act
        String toString = projectInnovation.toString();

        // Assert
        assertTrue(toString.contains("ProjectInnovation"));
    }

    @Test
    void equals_WithNullFields_ShouldHandleNulls() {
        // Arrange
        ProjectInnovation pi1 = new ProjectInnovation();
        ProjectInnovation pi2 = new ProjectInnovation();
        pi1.setId(null);
        pi1.setProjectId(null);
        pi2.setId(null);
        pi2.setProjectId(null);

        // Act & Assert
        assertEquals(pi1, pi2);
    }

    @Test
    void equals_WithOneNullField_ShouldReturnFalse() {
        // Arrange
        ProjectInnovation pi1 = new ProjectInnovation();
        ProjectInnovation pi2 = new ProjectInnovation();
        pi1.setId(1L);
        pi1.setProjectId(100L);
        pi2.setId(1L);
        pi2.setProjectId(null);

        // Act & Assert
        assertNotEquals(pi1, pi2);
    }
}
