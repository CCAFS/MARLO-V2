package com.example.demo.modules.innovationreports.adapters.infrastructure.persistence;

import com.example.demo.modules.innovationreports.domain.model.InnovationCatalogReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InnovationReportRepositoryAdapterTest {

    private InnovationCatalogReportJpaRepository jpaRepository;
    private InnovationReportRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        jpaRepository = mock(InnovationCatalogReportJpaRepository.class);
        adapter = new InnovationReportRepositoryAdapter(jpaRepository);
    }

    @Test
    void save_ShouldDelegateToJpaRepository() {
        // Arrange
        InnovationCatalogReport report = new InnovationCatalogReport(
            1L, "John", "Doe", "john@example.com", "Interest narrative"
        );
        when(jpaRepository.save(report)).thenReturn(report);

        // Act
        InnovationCatalogReport result = adapter.save(report);

        // Assert
        assertNotNull(result);
        assertEquals("john@example.com", result.getUserEmail());
        verify(jpaRepository).save(report);
    }

    @Test
    void findActiveReportsByInnovationId_ShouldReturnList() {
        // Arrange
        InnovationCatalogReport report1 = new InnovationCatalogReport(
            1L, "John", "Doe", "john@example.com", "Narrative 1"
        );
        InnovationCatalogReport report2 = new InnovationCatalogReport(
            1L, "Jane", "Smith", "jane@example.com", "Narrative 2"
        );
        when(jpaRepository.findActiveByInnovationId(1L))
            .thenReturn(Arrays.asList(report1, report2));

        // Act
        List<InnovationCatalogReport> result = adapter.findActiveReportsByInnovationId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jpaRepository).findActiveByInnovationId(1L);
    }

    @Test
    void findActiveReportsByUserEmail_ShouldReturnList() {
        // Arrange
        InnovationCatalogReport report = new InnovationCatalogReport(
            1L, "John", "Doe", "john@example.com", "Narrative"
        );
        when(jpaRepository.findActiveByUserEmail("john@example.com"))
            .thenReturn(Arrays.asList(report));

        // Act
        List<InnovationCatalogReport> result = adapter.findActiveReportsByUserEmail("john@example.com");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(jpaRepository).findActiveByUserEmail("john@example.com");
    }

    @Test
    void countActiveReportsByInnovationId_ShouldReturnCount() {
        // Arrange
        when(jpaRepository.countActiveByInnovationId(1L)).thenReturn(5L);

        // Act
        Long result = adapter.countActiveReportsByInnovationId(1L);

        // Assert
        assertEquals(5L, result);
        verify(jpaRepository).countActiveByInnovationId(1L);
    }

    @Test
    void findById_WhenExists_ShouldReturnOptional() {
        // Arrange
        InnovationCatalogReport report = new InnovationCatalogReport(
            1L, "John", "Doe", "john@example.com", "Narrative"
        );
        when(jpaRepository.findById(1L)).thenReturn(Optional.of(report));

        // Act
        Optional<InnovationCatalogReport> result = adapter.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("john@example.com", result.get().getUserEmail());
        verify(jpaRepository).findById(1L);
    }

    @Test
    void findById_WhenNotExists_ShouldReturnEmpty() {
        // Arrange
        when(jpaRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<InnovationCatalogReport> result = adapter.findById(999L);

        // Assert
        assertFalse(result.isPresent());
        verify(jpaRepository).findById(999L);
    }

    @Test
    void softDelete_WhenSuccessful_ShouldReturnTrue() {
        // Arrange
        when(jpaRepository.softDelete(1L)).thenReturn(1);

        // Act
        boolean result = adapter.softDelete(1L);

        // Assert
        assertTrue(result);
        verify(jpaRepository).softDelete(1L);
    }

    @Test
    void softDelete_WhenNotSuccessful_ShouldReturnFalse() {
        // Arrange
        when(jpaRepository.softDelete(999L)).thenReturn(0);

        // Act
        boolean result = adapter.softDelete(999L);

        // Assert
        assertFalse(result);
        verify(jpaRepository).softDelete(999L);
    }
}
