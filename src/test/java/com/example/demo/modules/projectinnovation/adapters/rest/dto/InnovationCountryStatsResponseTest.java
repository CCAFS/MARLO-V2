package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InnovationCountryStatsResponseTest {

    @Test
    void of_CreatesResponseWithAllFields() {
        // Arrange
        Integer innovationCount = 63;
        Integer countryCount = 10;
        Double averageScalingReadiness = 2.5;
        Long phaseId = 100L; // Generic test phase ID

        // Act
        InnovationCountryStatsResponse response = InnovationCountryStatsResponse.of(
                innovationCount, countryCount, averageScalingReadiness, phaseId);

        // Assert
        assertEquals(innovationCount, response.innovationCount());
        assertEquals(countryCount, response.countryCount());
        assertEquals(averageScalingReadiness, response.averageScalingReadiness());
        assertEquals(phaseId, response.phaseId());
    }

    @Test
    void of_RoundsAverageScalingReadinessToTwoDecimals() {
        // Arrange
        Double originalValue = 8.462264150943396;
        Double expectedRounded = 8.46;

        // Act
        InnovationCountryStatsResponse response = InnovationCountryStatsResponse.of(
                102, 10, originalValue, 200L); // Generic test phase ID

        // Assert
        assertEquals(expectedRounded, response.averageScalingReadiness());
    }

    @Test
    void of_RoundsUpWhenThirdDecimalIsGreaterThanFive() {
        // Arrange
        Double originalValue = 7.336;
        Double expectedRounded = 7.34;

        // Act
        InnovationCountryStatsResponse response = InnovationCountryStatsResponse.of(
                15, 7, originalValue, 333L);

        // Assert
        assertEquals(expectedRounded, response.averageScalingReadiness());
    }

    @Test
    void of_RoundsDownWhenThirdDecimalIsLessThanFive() {
        // Arrange
        Double originalValue = 9.234;
        Double expectedRounded = 9.23;

        // Act
        InnovationCountryStatsResponse response = InnovationCountryStatsResponse.of(
                20, 5, originalValue, 111L);

        // Assert
        assertEquals(expectedRounded, response.averageScalingReadiness());
    }

    @Test
    void of_RoundsUpWhenThirdDecimalIsFive() {
        // Arrange
        Double originalValue = 6.455;
        Double expectedRounded = 6.46;

        // Act
        InnovationCountryStatsResponse response = InnovationCountryStatsResponse.of(
                8, 3, originalValue, 222L);

        // Assert
        assertEquals(expectedRounded, response.averageScalingReadiness());
    }

    @Test
    void of_HandlesNullAverageScalingReadiness() {
        // Arrange
        Double nullValue = null;

        // Act
        InnovationCountryStatsResponse response = InnovationCountryStatsResponse.of(
                5, 2, nullValue, 999L);

        // Assert
        assertNull(response.averageScalingReadiness());
    }

    @Test
    void of_HandlesZeroAverageScalingReadiness() {
        // Arrange
        Double zeroValue = 0.0;

        // Act
        InnovationCountryStatsResponse response = InnovationCountryStatsResponse.of(
                10, 4, zeroValue, 100L);

        // Assert
        assertEquals(0.0, response.averageScalingReadiness());
    }

    @Test
    void of_HandlesMaximumScalingReadiness() {
        // Arrange
        Double maxValue = 10.0;

        // Act
        InnovationCountryStatsResponse response = InnovationCountryStatsResponse.of(
                1, 1, maxValue, 500L);

        // Assert
        assertEquals(10.0, response.averageScalingReadiness());
    }

    @Test 
    void of_HandlesHighPrecisionRounding() {
        // Arrange
        Double highPrecisionValue = 7.333333333333333;
        Double expectedRounded = 7.33;

        // Act
        InnovationCountryStatsResponse response = InnovationCountryStatsResponse.of(
                15, 7, highPrecisionValue, 333L);

        // Assert
        assertEquals(expectedRounded, response.averageScalingReadiness());
    }

    @Test
    void of_PreservesExactTwoDecimalValues() {
        // Arrange
        Double exactTwoDecimals = 6.75;

        // Act
        InnovationCountryStatsResponse response = InnovationCountryStatsResponse.of(
                25, 8, exactTwoDecimals, 600L);

        // Assert
        assertEquals(exactTwoDecimals, response.averageScalingReadiness());
    }

    @Test
    void of_HandlesWholeNumbers() {
        // Arrange
        Double wholeNumber = 5.0;

        // Act
        InnovationCountryStatsResponse response = InnovationCountryStatsResponse.of(
                20, 8, wholeNumber, 200L);

        // Assert
        assertEquals(wholeNumber, response.averageScalingReadiness());
    }
}
