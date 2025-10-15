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
        Long innovationId = null;
        Long phaseId = 428L;

        // Act
        InnovationCountryStatsResponse response = InnovationCountryStatsResponse.of(
                innovationCount, countryCount, averageScalingReadiness, innovationId, phaseId);

        // Assert
        assertEquals(innovationCount, response.innovationCount());
        assertEquals(countryCount, response.countryCount());
        assertEquals(averageScalingReadiness, response.averageScalingReadiness());
        assertEquals(innovationId, response.innovationId());
        assertEquals(phaseId, response.phaseId());
    }

    @Test
    void of_RoundsAverageScalingReadinessToTwoDecimals() {
        // Arrange
        Double originalValue = 8.462264150943396; // Real value from phase 428
        Double expectedRounded = 8.46;

        // Act
        InnovationCountryStatsResponse response = InnovationCountryStatsResponse.of(
                102, 10, originalValue, null, 428L);

        // Assert
        assertEquals(expectedRounded, response.averageScalingReadiness());
    }

    @Test
    void of_RoundsUpWhenThirdDecimalIsGreaterThanFive() {
        // Arrange
        Double originalValue = 7.336; // Should round up to 7.34
        Double expectedRounded = 7.34;

        // Act
        InnovationCountryStatsResponse response = InnovationCountryStatsResponse.of(
                25, 5, originalValue, null, 123L);

        // Assert
        assertEquals(expectedRounded, response.averageScalingReadiness());
    }

    @Test
    void of_RoundsDownWhenThirdDecimalIsLessThanFive() {
        // Arrange
        Double originalValue = 6.124; // Should round down to 6.12
        Double expectedRounded = 6.12;

        // Act
        InnovationCountryStatsResponse response = InnovationCountryStatsResponse.of(
                15, 3, originalValue, null, 456L);

        // Assert
        assertEquals(expectedRounded, response.averageScalingReadiness());
    }

    @Test
    void of_RoundsUpWhenThirdDecimalIsExactlyFive() {
        // Arrange
        Double originalValue = 5.555; // Should round up to 5.56 (HALF_UP)
        Double expectedRounded = 5.56;

        // Act
        InnovationCountryStatsResponse response = InnovationCountryStatsResponse.of(
                20, 4, originalValue, null, 789L);

        // Assert
        assertEquals(expectedRounded, response.averageScalingReadiness());
    }

    @Test
    void of_HandlesZeroValueCorrectly() {
        // Arrange
        Double originalValue = 0.0;
        Double expectedRounded = 0.0;

        // Act
        InnovationCountryStatsResponse response = InnovationCountryStatsResponse.of(
                0, 0, originalValue, null, 999L);

        // Assert
        assertEquals(expectedRounded, response.averageScalingReadiness());
    }

    @Test
    void of_HandlesNullAverageScalingReadiness() {
        // Arrange
        Double originalValue = null;

        // Act
        InnovationCountryStatsResponse response = InnovationCountryStatsResponse.of(
                10, 2, originalValue, null, 111L);

        // Assert
        assertNull(response.averageScalingReadiness());
    }

    @Test
    void of_HandlesMaximumPrecisionValues() {
        // Arrange - Very precise decimal
        Double originalValue = 9.999999999999;
        Double expectedRounded = 10.0;

        // Act
        InnovationCountryStatsResponse response = InnovationCountryStatsResponse.of(
                50, 8, originalValue, null, 222L);

        // Assert
        assertEquals(expectedRounded, response.averageScalingReadiness());
    }

    @Test
    void of_CreatesResponseWithInnovationId() {
        // Arrange
        Integer innovationCount = 15;
        Integer countryCount = 5;
        Double averageScalingReadiness = 3.2;
        Long innovationId = 123L;
        Long phaseId = 425L;

        // Act
        InnovationCountryStatsResponse response = InnovationCountryStatsResponse.of(
                innovationCount, countryCount, averageScalingReadiness, innovationId, phaseId);

        // Assert
        assertEquals(innovationCount, response.innovationCount());
        assertEquals(countryCount, response.countryCount());
        assertEquals(averageScalingReadiness, response.averageScalingReadiness());
        assertEquals(innovationId, response.innovationId());
        assertEquals(phaseId, response.phaseId());
    }

    @Test
    void constructor_CreatesResponseCorrectly() {
        // Act
        InnovationCountryStatsResponse response = new InnovationCountryStatsResponse(
                25, 8, 1.8, null, 430L);

        // Assert
        assertEquals(25, response.innovationCount());
        assertEquals(8, response.countryCount());
        assertEquals(1.8, response.averageScalingReadiness());
        assertNull(response.innovationId());
        assertEquals(430L, response.phaseId());
    }

    @Test
    void of_HandlesZeroAverageScalingReadiness() {
        // Arrange
        Integer innovationCount = 10;
        Integer countryCount = 3;
        Double averageScalingReadiness = 0.0;
        Long innovationId = null;
        Long phaseId = 428L;

        // Act
        InnovationCountryStatsResponse response = InnovationCountryStatsResponse.of(
                innovationCount, countryCount, averageScalingReadiness, innovationId, phaseId);

        // Assert
        assertEquals(innovationCount, response.innovationCount());
        assertEquals(countryCount, response.countryCount());
        assertEquals(0.0, response.averageScalingReadiness());
        assertEquals(innovationId, response.innovationId());
        assertEquals(phaseId, response.phaseId());
    }
}