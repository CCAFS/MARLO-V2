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
    void getCycle_WhenDescriptionContainsReporting_ShouldReturnReporting() {
        // Arrange
        phase.setDescription("Reporting Phase 2025");

        // Act
        String result = phase.getCycle();

        // Assert
        assertEquals("Reporting", result);
    }

    @Test
    void getCycle_WhenDescriptionContainsPlanning_ShouldReturnPlanning() {
        // Arrange
        phase.setDescription("Planning Phase");

        // Act
        String result = phase.getCycle();

        // Assert
        assertEquals("Planning", result);
    }

    @Test
    void getCycle_WhenDescriptionIsNull_ShouldReturnDefault() {
        // Arrange
        phase.setDescription(null);

        // Act
        String result = phase.getCycle();

        // Assert
        assertEquals("Reporting", result);
    }

    @Test
    void getCycle_WhenDescriptionDoesNotContainKeywords_ShouldReturnFullDescription() {
        // Arrange
        phase.setDescription("Custom Phase Name");

        // Act
        String result = phase.getCycle();

        // Assert
        assertEquals("Custom Phase Name", result);
    }
}
