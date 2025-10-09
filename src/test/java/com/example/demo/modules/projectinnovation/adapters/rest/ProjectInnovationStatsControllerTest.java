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

@ExtendWith(MockitoExtension.class)
class ProjectInnovationStatsControllerTest {

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
    void getInnovationCountryStats_WithValidPhaseId_ReturnsStats() throws Exception {
        // Arrange - Usando valores de mock independientes de la BD
        Long phaseId = 123L;
        Long mockCountryCount = 5L;
        Long mockInnovationCount = 15L;

        when(repositoryAdapter.countDistinctCountries(null, phaseId))
                .thenReturn(mockCountryCount);
        when(repositoryAdapter.countDistinctInnovations(null, phaseId))
                .thenReturn(mockInnovationCount);

        // Act & Assert
        mockMvc.perform(get("/api/innovations/stats")
                        .param("phaseId", phaseId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.innovationCount").value(mockInnovationCount.intValue()))
                .andExpect(jsonPath("$.countryCount").value(mockCountryCount.intValue()))
                .andExpect(jsonPath("$.phaseId").value(phaseId));
    }

    @Test
    void getInnovationCountryStats_WithMissingPhaseId_ReturnsBadRequest() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/innovations/stats"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getInnovationCountryStats_WithRepositoryError_ReturnsEmptyStats() throws Exception {
        // Arrange - Usando un phaseId de test
        Long phaseId = 999L;

        // Mock para que el primer método lance excepción
        when(repositoryAdapter.countDistinctCountries(null, phaseId))
                .thenThrow(new RuntimeException("Database error"));

        // Act & Assert - El controlador maneja la excepción y retorna stats vacíos
        mockMvc.perform(get("/api/innovations/stats")
                        .param("phaseId", phaseId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.innovationCount").value(0))
                .andExpect(jsonPath("$.countryCount").value(0))
                .andExpect(jsonPath("$.phaseId").value(phaseId));
    }

    @Test
    void getInnovationCountryStats_WithDifferentPhase_ReturnsCorrectStats() throws Exception {
        // Arrange - Diferentes valores mock para simular diferentes fases
        Long phaseId = 456L;
        Long expectedCountryCount = 8L;
        Long expectedInnovationCount = 25L;

        when(repositoryAdapter.countDistinctCountries(null, phaseId))
                .thenReturn(expectedCountryCount);
        when(repositoryAdapter.countDistinctInnovations(null, phaseId))
                .thenReturn(expectedInnovationCount);

        // Act & Assert
        mockMvc.perform(get("/api/innovations/stats")
                        .param("phaseId", phaseId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.innovationCount").value(expectedInnovationCount.intValue()))
                .andExpect(jsonPath("$.countryCount").value(expectedCountryCount.intValue()))
                .andExpect(jsonPath("$.phaseId").value(phaseId));
    }

    @Test
    void getInnovationCountryStats_WithZeroResults_ReturnsZeroStats() throws Exception {
        // Arrange - Caso edge: fase sin datos
        Long phaseId = 000L;
        Long zeroCount = 0L;

        when(repositoryAdapter.countDistinctCountries(null, phaseId))
                .thenReturn(zeroCount);
        when(repositoryAdapter.countDistinctInnovations(null, phaseId))
                .thenReturn(zeroCount);

        // Act & Assert
        mockMvc.perform(get("/api/innovations/stats")
                        .param("phaseId", phaseId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.innovationCount").value(0))
                .andExpect(jsonPath("$.countryCount").value(0))
                .andExpect(jsonPath("$.phaseId").value(phaseId));
    }

    @Test
    void getInnovationCountryStats_WithLargeNumbers_ReturnsCorrectStats() throws Exception {
        // Arrange - Caso edge: números grandes
        Long phaseId = 789L;
        Long largeCountryCount = 999L;
        Long largeInnovationCount = 5000L;

        when(repositoryAdapter.countDistinctCountries(null, phaseId))
                .thenReturn(largeCountryCount);
        when(repositoryAdapter.countDistinctInnovations(null, phaseId))
                .thenReturn(largeInnovationCount);

        // Act & Assert
        mockMvc.perform(get("/api/innovations/stats")
                        .param("phaseId", phaseId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.innovationCount").value(largeInnovationCount.intValue()))
                .andExpect(jsonPath("$.countryCount").value(largeCountryCount.intValue()))
                .andExpect(jsonPath("$.phaseId").value(phaseId));
    }
}