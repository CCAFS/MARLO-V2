package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InnovationCountryStatsResponseTest {

    @Test
    void of_CreatesResponseWithAllFields() {
        // Arrange
        Integer innovationCount = 63;
        Integer countryCount = 10;
        Long innovationId = null;
        Long phaseId = 428L;

        // Act
        InnovationCountryStatsResponse response = InnovationCountryStatsResponse.of(
                innovationCount, countryCount, innovationId, phaseId);

        // Assert
        assertEquals(innovationCount, response.innovationCount());
        assertEquals(countryCount, response.countryCount());
        assertEquals(innovationId, response.innovationId());
        assertEquals(phaseId, response.phaseId());
    }

    @Test
    void of_CreatesResponseWithInnovationId() {
        // Arrange
        Integer innovationCount = 15;
        Integer countryCount = 5;
        Long innovationId = 123L;
        Long phaseId = 425L;

        // Act
        InnovationCountryStatsResponse response = InnovationCountryStatsResponse.of(
                innovationCount, countryCount, innovationId, phaseId);

        // Assert
        assertEquals(innovationCount, response.innovationCount());
        assertEquals(countryCount, response.countryCount());
        assertEquals(innovationId, response.innovationId());
        assertEquals(phaseId, response.phaseId());
    }

    @Test
    void constructor_CreatesResponseCorrectly() {
        // Act
        InnovationCountryStatsResponse response = new InnovationCountryStatsResponse(
                25, 8, null, 430L);

        // Assert
        assertEquals(25, response.innovationCount());
        assertEquals(8, response.countryCount());
        assertNull(response.innovationId());
        assertEquals(430L, response.phaseId());
    }
}