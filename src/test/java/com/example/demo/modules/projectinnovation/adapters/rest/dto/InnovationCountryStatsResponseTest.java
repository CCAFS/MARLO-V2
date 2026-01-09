package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InnovationCountryStatsResponseTest {

    @Test
    void of_ShouldRoundAverageScalingReadiness() {
        // Act
        InnovationCountryStatsResponse response = InnovationCountryStatsResponse.of(
            10, 5, 7.456789, 1L
        );

        // Assert
        assertEquals(10, response.innovationCount());
        assertEquals(5, response.countryCount());
        assertEquals(7.46, response.averageScalingReadiness(), 0.01);
        assertEquals(1L, response.phaseId());
    }

    @Test
    void of_WhenAverageIsNull_ShouldReturnNull() {
        // Act
        InnovationCountryStatsResponse response = InnovationCountryStatsResponse.of(
            10, 5, null, 1L
        );

        // Assert
        assertNull(response.averageScalingReadiness());
    }

    @Test
    void of_ShouldRoundUp() {
        // Act
        InnovationCountryStatsResponse response = InnovationCountryStatsResponse.of(
            10, 5, 7.455, 1L
        );

        // Assert
        assertEquals(7.46, response.averageScalingReadiness(), 0.01);
    }
}
