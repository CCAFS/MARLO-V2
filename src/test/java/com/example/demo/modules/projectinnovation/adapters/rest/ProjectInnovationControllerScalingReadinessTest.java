package com.example.demo.modules.projectinnovation.adapters.rest;

import com.example.demo.modules.innovationtype.adapters.outbound.persistence.InnovationTypeRepositoryAdapter;
import com.example.demo.modules.projectinnovation.adapters.outbound.persistence.LocElementJpaRepository;
import com.example.demo.modules.projectinnovation.adapters.outbound.persistence.ProjectInnovationRepositoryAdapter;
import com.example.demo.modules.projectinnovation.adapters.rest.mapper.ProjectInnovationActorsMapper;
import com.example.demo.modules.projectinnovation.application.port.inbound.ProjectInnovationUseCase;
import com.example.demo.modules.projectinnovation.application.service.ProjectInnovationActorsService;
import com.example.demo.modules.sustainabledevelopmentgoals.adapters.outbound.persistence.SustainableDevelopmentGoalJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for ProjectInnovationController focusing on scaling readiness functionality.
 */
@ExtendWith(MockitoExtension.class)
class ProjectInnovationControllerScalingReadinessTest {

    @Mock
    private ProjectInnovationUseCase projectInnovationUseCase;

    @Mock
    private ProjectInnovationActorsService actorsService;

    @Mock
    private ProjectInnovationActorsMapper actorsMapper;

    @Mock
    private ProjectInnovationRepositoryAdapter repositoryAdapter;

    @Mock
    private LocElementJpaRepository locElementRepository;

    @Mock
    private InnovationTypeRepositoryAdapter innovationTypeRepository;

    @Mock
    private SustainableDevelopmentGoalJpaRepository sdgRepository;

    private ProjectInnovationController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new ProjectInnovationController(
                projectInnovationUseCase,
                actorsService,
                actorsMapper,
                repositoryAdapter,
                locElementRepository,
                innovationTypeRepository,
                sdgRepository
        );
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getInnovationCountryStats_WithValidPhaseId_ReturnsCorrectStructure() throws Exception {
        // Arrange - Generic test without hardcoded values
        Long phaseId = 123L;
        Long countryCount = 5L;
        Long innovationCount = 25L;
        Double averageScalingReadiness = 7.5;

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
                .andExpect(jsonPath("$.innovationCount").value(innovationCount.intValue()))
                .andExpect(jsonPath("$.countryCount").value(countryCount.intValue()))
                .andExpect(jsonPath("$.averageScalingReadiness").value(averageScalingReadiness))
                .andExpect(jsonPath("$.phaseId").value(phaseId));
    }

    @Test
    void getInnovationCountryStats_WithDecimalAverageScalingReadiness_ReturnsRoundedValue() throws Exception {
        // Arrange - Testing decimal precision with rounding to 2 decimal places
        Long phaseId = 100L;
        Long countryCount = 3L;
        Long innovationCount = 10L;
        Double decimalAverageScalingReadiness = 7.333333333333333; // Value with many decimals
        Double expectedRoundedValue = 7.33; // Expected rounded value

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
                .andExpect(jsonPath("$.averageScalingReadiness").value(expectedRoundedValue))
                .andExpect(jsonPath("$.innovationCount").value(innovationCount.intValue()))
                .andExpect(jsonPath("$.countryCount").value(countryCount.intValue()));
    }

    @Test
    void getInnovationCountryStats_WithZeroAverageScalingReadiness_ReturnsZero() throws Exception {
        // Arrange - Testing zero average scenario
        Long phaseId = 200L;
        Long countryCount = 4L;
        Long innovationCount = 8L;
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
                .andExpect(jsonPath("$.innovationCount").value(innovationCount.intValue()))
                .andExpect(jsonPath("$.countryCount").value(countryCount.intValue()));
    }

    @Test
    void getInnovationCountryStats_WithHighAverageScalingReadiness_ReturnsCorrectValue() throws Exception {
        // Arrange - Testing high scaling readiness scenario
        Long phaseId = 300L;
        Long countryCount = 6L;
        Long innovationCount = 15L;
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
                .andExpect(jsonPath("$.averageScalingReadiness").value(highAverageScalingReadiness))
                .andExpect(jsonPath("$.innovationCount").value(innovationCount.intValue()))
                .andExpect(jsonPath("$.countryCount").value(countryCount.intValue()));
    }

    @Test
    void getInnovationCountryStats_WhenScalingReadinessCalculationFails_ReturnsZero() throws Exception {
        // Arrange - Testing error handling in scaling readiness calculation
        Long phaseId = 400L;
        Long countryCount = 2L;
        Long innovationCount = 7L;
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
                .andExpect(jsonPath("$.innovationCount").value(innovationCount.intValue()))
                .andExpect(jsonPath("$.countryCount").value(countryCount.intValue()));
    }

    @Test
    void getInnovationCountryStats_WithMaximumScalingReadiness_ReturnsCorrectValue() throws Exception {
        // Arrange - Testing maximum scaling readiness scenario
        Long phaseId = 500L;
        Long countryCount = 3L;
        Long innovationCount = 12L;
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
                .andExpect(jsonPath("$.averageScalingReadiness").value(maxAverageScalingReadiness))
                .andExpect(jsonPath("$.innovationCount").value(innovationCount.intValue()))
                .andExpect(jsonPath("$.countryCount").value(countryCount.intValue()));
    }

        @Test
    void getInnovationCountryStats_WithAllCalculationsSuccessful_ReturnsCompleteResponse() throws Exception {
        // Arrange - Testing complete successful response with all fields
        Long phaseId = 600L;
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
                .andExpect(jsonPath("$.innovationCount").value(innovationCount.intValue()))
                .andExpect(jsonPath("$.countryCount").value(countryCount.intValue()))
                .andExpect(jsonPath("$.averageScalingReadiness").value(averageScalingReadiness))
                .andExpect(jsonPath("$.phaseId").value(phaseId))
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void getInnovationCountryStats_WithHighPrecisionValue_ReturnsRoundedTo2Decimals() throws Exception {
        // Arrange - Testing specific rounding behavior like 8.462264150943396 → 8.46
        Long phaseId = 700L;
        Long countryCount = 10L;
        Long innovationCount = 50L;
        Double highPrecisionValue = 8.462264150943396; // Value with many decimals
        Double expectedRoundedValue = 8.46; // Expected rounded value

        when(repositoryAdapter.countDistinctCountries(null, phaseId))
                .thenReturn(countryCount);
        when(repositoryAdapter.countDistinctInnovations(null, phaseId))
                .thenReturn(innovationCount);
        when(repositoryAdapter.findAverageScalingReadinessByPhase(phaseId))
                .thenReturn(highPrecisionValue);

        // Act & Assert
        mockMvc.perform(get("/api/innovations/stats")
                        .param("phaseId", phaseId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.averageScalingReadiness").value(expectedRoundedValue))
                .andExpect(jsonPath("$.innovationCount").value(innovationCount.intValue()))
                .andExpect(jsonPath("$.countryCount").value(countryCount.intValue()));
    }

    @Test
    void getInnovationCountryStats_WithRoundingUpScenario_ReturnsRoundedValue() throws Exception {
        // Arrange - Testing rounding up scenario (6.456 → 6.46)
        Long phaseId = 800L;
        Long countryCount = 4L;
        Long innovationCount = 16L;
        Double valueToRoundUp = 6.456; // Should round up to 6.46
        Double expectedRoundedValue = 6.46;

        when(repositoryAdapter.countDistinctCountries(null, phaseId))
                .thenReturn(countryCount);
        when(repositoryAdapter.countDistinctInnovations(null, phaseId))
                .thenReturn(innovationCount);
        when(repositoryAdapter.findAverageScalingReadinessByPhase(phaseId))
                .thenReturn(valueToRoundUp);

        // Act & Assert
        mockMvc.perform(get("/api/innovations/stats")
                        .param("phaseId", phaseId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.averageScalingReadiness").value(expectedRoundedValue))
                .andExpect(jsonPath("$.innovationCount").value(innovationCount.intValue()))
                .andExpect(jsonPath("$.countryCount").value(countryCount.intValue()));
    }
}
