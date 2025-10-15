package com.example.demo.modules.projectinnovation.adapters.rest;

import com.example.demo.modules.projectinnovation.adapters.outbound.persistence.ProjectInnovationRepositoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Comprehensive unit tests for ProjectInnovationController stats endpoint
 * focusing on the averageScalingReadiness functionality
 */
@ExtendWith(MockitoExtension.class)
class ProjectInnovationControllerScalingReadinessTest {

    @Mock
    private ProjectInnovationRepositoryAdapter repositoryAdapter;

    @InjectMocks
    private ProjectInnovationController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

        @Test
    void getInnovationCountryStats_WithPhase428_ReturnsCorrectAverageScalingReadiness() throws Exception {
        // Arrange - Real data simulation for phase 428
        Long phaseId = 428L;
        Long expectedCountryCount = 10L;
        Long expectedInnovationCount = 102L;
        Double repositoryValue = 8.462264150943396; // Actual value from repository
        Double expectedRoundedValue = 8.46; // Expected rounded value in response

        when(repositoryAdapter.countDistinctCountries(null, phaseId))
                .thenReturn(expectedCountryCount);
        when(repositoryAdapter.countDistinctInnovations(null, phaseId))
                .thenReturn(expectedInnovationCount);
        when(repositoryAdapter.findAverageScalingReadinessByPhase(phaseId))
                .thenReturn(repositoryValue);

        // Act & Assert
        mockMvc.perform(get("/api/innovations/stats")
                        .param("phaseId", phaseId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.innovationCount").value(expectedInnovationCount.intValue()))
                .andExpect(jsonPath("$.countryCount").value(expectedCountryCount.intValue()))
                .andExpect(jsonPath("$.averageScalingReadiness").value(expectedRoundedValue))
                .andExpect(jsonPath("$.phaseId").value(phaseId))
                .andExpect(jsonPath("$.innovationId").doesNotExist());
    }

    @Test
    void getInnovationCountryStats_WithHighAverageScalingReadiness_ReturnsCorrectValue() throws Exception {
        // Arrange - Testing high scaling readiness scenario
        Long phaseId = 999L;
        Long countryCount = 5L;
        Long innovationCount = 20L;
        Double highAverageScalingReadiness = 9.8;

        when(repositoryAdapter.countDistinctCountries(null, phaseId))
                .thenReturn(countryCount);
        when(repositoryAdapter.countDistinctInnovations(null, phaseId))
                .thenReturn(innovationCount);
        when(repositoryAdapter.findAverageScalingReadinessByPhase(phaseId))
                .thenReturn(highAverageScalingReadiness);

        // Act & Assert
        mockMvc.perform(get("/api/innovations/stats")
                        .param("phaseId", phaseId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.averageScalingReadiness").value(9.8))
                .andExpect(jsonPath("$.innovationCount").value(20))
                .andExpect(jsonPath("$.countryCount").value(5));
    }

    @Test
    void getInnovationCountryStats_WithLowAverageScalingReadiness_ReturnsCorrectValue() throws Exception {
        // Arrange - Testing low scaling readiness scenario
        Long phaseId = 111L;
        Long countryCount = 3L;
        Long innovationCount = 8L;
        Double lowAverageScalingReadiness = 2.1;

        when(repositoryAdapter.countDistinctCountries(null, phaseId))
                .thenReturn(countryCount);
        when(repositoryAdapter.countDistinctInnovations(null, phaseId))
                .thenReturn(innovationCount);
        when(repositoryAdapter.findAverageScalingReadinessByPhase(phaseId))
                .thenReturn(lowAverageScalingReadiness);

        // Act & Assert
        mockMvc.perform(get("/api/innovations/stats")
                        .param("phaseId", phaseId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.averageScalingReadiness").value(2.1))
                .andExpect(jsonPath("$.innovationCount").value(8))
                .andExpect(jsonPath("$.countryCount").value(3));
    }

    @Test
    void getInnovationCountryStats_WithZeroAverageScalingReadiness_ReturnsZero() throws Exception {
        // Arrange - Testing zero average scenario (no valid readiness scale values)
        Long phaseId = 222L;
        Long countryCount = 2L;
        Long innovationCount = 5L;
        Double zeroAverageScalingReadiness = 0.0;

        when(repositoryAdapter.countDistinctCountries(null, phaseId))
                .thenReturn(countryCount);
        when(repositoryAdapter.countDistinctInnovations(null, phaseId))
                .thenReturn(innovationCount);
        when(repositoryAdapter.findAverageScalingReadinessByPhase(phaseId))
                .thenReturn(zeroAverageScalingReadiness);

        // Act & Assert
        mockMvc.perform(get("/api/innovations/stats")
                        .param("phaseId", phaseId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.averageScalingReadiness").value(0.0))
                .andExpect(jsonPath("$.innovationCount").value(5))
                .andExpect(jsonPath("$.countryCount").value(2));
    }

    @Test
    void getInnovationCountryStats_WithDecimalAverageScalingReadiness_ReturnsExactValue() throws Exception {
        // Arrange - Testing decimal precision
        Long phaseId = 333L;
        Long countryCount = 7L;
        Long innovationCount = 15L;
        Double decimalAverageScalingReadiness = 7.333333333333333; // 22/3

        when(repositoryAdapter.countDistinctCountries(null, phaseId))
                .thenReturn(countryCount);
        when(repositoryAdapter.countDistinctInnovations(null, phaseId))
                .thenReturn(innovationCount);
        when(repositoryAdapter.findAverageScalingReadinessByPhase(phaseId))
                .thenReturn(decimalAverageScalingReadiness);

        // Act & Assert
        mockMvc.perform(get("/api/innovations/stats")
                        .param("phaseId", phaseId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.averageScalingReadiness").value(7.333333333333333))
                .andExpect(jsonPath("$.innovationCount").value(15))
                .andExpect(jsonPath("$.countryCount").value(7));
    }

    @Test
    void getInnovationCountryStats_WhenScalingReadinessCalculationFails_ReturnsZero() throws Exception {
        // Arrange - Testing error handling in scaling readiness calculation
        Long phaseId = 444L;
        Long countryCount = 6L;
        Long innovationCount = 12L;
        Double errorAverageScalingReadiness = 0.0; // Repository returns 0.0 on error

        when(repositoryAdapter.countDistinctCountries(null, phaseId))
                .thenReturn(countryCount);
        when(repositoryAdapter.countDistinctInnovations(null, phaseId))
                .thenReturn(innovationCount);
        when(repositoryAdapter.findAverageScalingReadinessByPhase(phaseId))
                .thenReturn(errorAverageScalingReadiness);

        // Act & Assert
        mockMvc.perform(get("/api/innovations/stats")
                        .param("phaseId", phaseId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.averageScalingReadiness").value(0.0))
                .andExpect(jsonPath("$.innovationCount").value(12))
                .andExpect(jsonPath("$.countryCount").value(6));
    }

    @Test
    void getInnovationCountryStats_WithMaximumScalingReadiness_ReturnsCorrectValue() throws Exception {
        // Arrange - Testing maximum scaling readiness (assuming scale 1-10)
        Long phaseId = 555L;
        Long countryCount = 1L;
        Long innovationCount = 3L;
        Double maxAverageScalingReadiness = 10.0;

        when(repositoryAdapter.countDistinctCountries(null, phaseId))
                .thenReturn(countryCount);
        when(repositoryAdapter.countDistinctInnovations(null, phaseId))
                .thenReturn(innovationCount);
        when(repositoryAdapter.findAverageScalingReadinessByPhase(phaseId))
                .thenReturn(maxAverageScalingReadiness);

        // Act & Assert
        mockMvc.perform(get("/api/innovations/stats")
                        .param("phaseId", phaseId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.averageScalingReadiness").value(10.0))
                .andExpect(jsonPath("$.innovationCount").value(3))
                .andExpect(jsonPath("$.countryCount").value(1));
    }

    @Test
    void getInnovationCountryStats_WithAllCalculationsSuccessful_ReturnsCompleteResponse() throws Exception {
        // Arrange - Testing complete successful response with all fields
        Long phaseId = 666L;
        Long countryCount = 8L;
        Long innovationCount = 25L;
        Double averageScalingReadiness = 6.75;

        when(repositoryAdapter.countDistinctCountries(null, phaseId))
                .thenReturn(countryCount);
        when(repositoryAdapter.countDistinctInnovations(null, phaseId))
                .thenReturn(innovationCount);
        when(repositoryAdapter.findAverageScalingReadinessByPhase(phaseId))
                .thenReturn(averageScalingReadiness);

        // Act & Assert
        mockMvc.perform(get("/api/innovations/stats")
                        .param("phaseId", phaseId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.innovationCount").value(25))
                .andExpect(jsonPath("$.countryCount").value(8))
                .andExpect(jsonPath("$.averageScalingReadiness").value(6.75))
                .andExpect(jsonPath("$.phaseId").value(666))
                .andExpect(jsonPath("$.innovationId").doesNotExist())
                .andExpect(content().contentType("application/json"));
    }
}