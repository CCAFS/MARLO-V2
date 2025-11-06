package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectInnovationRepositoryAdapterTest {

    @Mock
    private ProjectInnovationCountryJpaRepository countryRepository;

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @InjectMocks
    private ProjectInnovationRepositoryAdapter adapter;

    @Test
    void countDistinctCountries_ReturnsCountFromRepository() {
        // Arrange - Usando valores de test independientes de la BD
        Long mockCount = 7L;
        Long testPhaseId = 100L;
        
        when(countryRepository.countDistinctCountriesByInnovationAndPhase(null, testPhaseId))
                .thenReturn(mockCount);

        // Act
        Long result = adapter.countDistinctCountries(null, testPhaseId);

        // Assert
        assertEquals(mockCount, result);
    }

    @Test
    void countDistinctInnovations_ReturnsCountFromRepository() {
        // Arrange - Usando valores de test independientes de la BD
        Long mockCount = 42L;
        Long testPhaseId = 200L;
        
        when(countryRepository.countDistinctInnovationsByInnovationAndPhase(null, testPhaseId))
                .thenReturn(mockCount);

        // Act
        Long result = adapter.countDistinctInnovations(null, testPhaseId);

        // Assert
        assertEquals(mockCount, result);
    }

    @Test
    void countDistinctCountries_WithNullResult_ReturnsNull() {
        // Arrange - Probando caso edge donde el repositorio retorna null
        Long testPhaseId = 300L;
        
        when(countryRepository.countDistinctCountriesByInnovationAndPhase(null, testPhaseId))
                .thenReturn(null);

        // Act
        Long result = adapter.countDistinctCountries(null, testPhaseId);

        // Assert
        assertNull(result);
    }

    @Test
    void countDistinctInnovations_WithZeroResult_ReturnsZero() {
        // Arrange - Probando caso edge con resultado cero
        Long testPhaseId = 400L;
        Long zeroCount = 0L;
        
        when(countryRepository.countDistinctInnovationsByInnovationAndPhase(null, testPhaseId))
                .thenReturn(zeroCount);

        // Act
        Long result = adapter.countDistinctInnovations(null, testPhaseId);

        // Assert
        assertEquals(zeroCount, result);
    }
}
