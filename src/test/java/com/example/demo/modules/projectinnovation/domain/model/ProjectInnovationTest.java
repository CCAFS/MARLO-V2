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
}
