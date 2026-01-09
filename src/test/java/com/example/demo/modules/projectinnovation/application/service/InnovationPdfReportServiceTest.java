package com.example.demo.modules.projectinnovation.application.service;

import com.example.demo.modules.projectinnovation.adapters.outbound.persistence.PhaseJpaRepository;
import com.example.demo.modules.projectinnovation.domain.model.Phase;
import com.example.demo.platform.config.InnovationPdfGeneratorProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InnovationPdfReportServiceTest {

    @Mock
    private InnovationPdfGeneratorProperties pdfGeneratorProperties;

    @Mock
    private PhaseJpaRepository phaseRepository;

    @InjectMocks
    private InnovationPdfReportService service;

    private Phase testPhase;

    @BeforeEach
    void setUp() {
        testPhase = new Phase();
        testPhase.setId(1L);
        testPhase.setDescription("Reporting Phase");
        testPhase.setYear(2025);
    }

    @Test
    void generateInnovationPdfUrl_WithDefault_ShouldReturnUrl() {
        // Arrange
        Long innovationId = 100L;
        String expectedUrl = "http://pdf-service.com/api/pdf/innovation-report?innovationId=100";
        when(pdfGeneratorProperties.buildDefaultUrl(innovationId)).thenReturn(expectedUrl);

        // Act
        String result = service.generateInnovationPdfUrl(innovationId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedUrl, result);
        verify(pdfGeneratorProperties).buildDefaultUrl(innovationId);
    }

    @Test
    void generateInnovationPdfUrl_WithParameters_ShouldReturnUrl() {
        // Arrange
        Long innovationId = 100L;
        String cycle = "Reporting";
        Integer year = 2025;
        String expectedUrl = "http://pdf-service.com/api/pdf/innovation-report?innovationId=100&cycle=Reporting&year=2025";
        when(pdfGeneratorProperties.buildUrlWithParameters(innovationId, cycle, year)).thenReturn(expectedUrl);

        // Act
        String result = service.generateInnovationPdfUrl(innovationId, cycle, year);

        // Assert
        assertNotNull(result);
        assertEquals(expectedUrl, result);
        verify(pdfGeneratorProperties).buildUrlWithParameters(innovationId, cycle, year);
    }

    @Test
    void generateInnovationPdfUrlByPhase_WhenPhaseExists_ShouldReturnUrl() {
        // Arrange
        Long innovationId = 100L;
        Long phaseId = 1L;
        String expectedUrl = "http://pdf-service.com/api/pdf/innovation-report?innovationId=100&cycle=Reporting&year=2025";
        
        when(phaseRepository.findById(phaseId)).thenReturn(Optional.of(testPhase));
        when(pdfGeneratorProperties.buildUrlWithParameters(innovationId, testPhase.getCycle(), testPhase.getYear()))
            .thenReturn(expectedUrl);

        // Act
        String result = service.generateInnovationPdfUrlByPhase(innovationId, phaseId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedUrl, result);
        verify(phaseRepository).findById(phaseId);
        verify(pdfGeneratorProperties).buildUrlWithParameters(innovationId, testPhase.getCycle(), testPhase.getYear());
    }

    @Test
    void generateInnovationPdfUrlByPhase_WhenPhaseNotExists_ShouldThrowException() {
        // Arrange
        Long innovationId = 100L;
        Long phaseId = 999L;
        when(phaseRepository.findById(phaseId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> service.generateInnovationPdfUrlByPhase(innovationId, phaseId));
        assertTrue(exception.getMessage().contains("Phase not found"));
        verify(phaseRepository).findById(phaseId);
    }

    @Test
    void getPhaseById_WhenExists_ShouldReturnPhase() {
        // Arrange
        Long phaseId = 1L;
        when(phaseRepository.findById(phaseId)).thenReturn(Optional.of(testPhase));

        // Act
        Phase result = service.getPhaseById(phaseId);

        // Assert
        assertNotNull(result);
        assertEquals(testPhase.getId(), result.getId());
        verify(phaseRepository).findById(phaseId);
    }

    @Test
    void getPhaseById_WhenNotExists_ShouldThrowException() {
        // Arrange
        Long phaseId = 999L;
        when(phaseRepository.findById(phaseId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> service.getPhaseById(phaseId));
        assertTrue(exception.getMessage().contains("Phase not found"));
    }

    @Test
    void getPdfGeneratorBaseUrl_ShouldReturnUrl() {
        // Arrange
        String expectedUrl = "http://pdf-service.com/api/pdf";
        when(pdfGeneratorProperties.getUrl()).thenReturn(expectedUrl);

        // Act
        String result = service.getPdfGeneratorBaseUrl();

        // Assert
        assertNotNull(result);
        assertEquals(expectedUrl, result);
        verify(pdfGeneratorProperties).getUrl();
    }

    @Test
    void validateParameters_WhenValid_ShouldReturnTrue() {
        // Act
        boolean result = service.validateParameters(100L, "Reporting", 2025);

        // Assert
        assertTrue(result);
    }

    @Test
    void validateParameters_WhenInvalidInnovationId_ShouldReturnFalse() {
        // Act & Assert
        assertFalse(service.validateParameters(null, "Reporting", 2025));
        assertFalse(service.validateParameters(-1L, "Reporting", 2025));
        assertFalse(service.validateParameters(0L, "Reporting", 2025));
    }

    @Test
    void validateParameters_WhenInvalidCycle_ShouldReturnFalse() {
        // Act & Assert
        assertFalse(service.validateParameters(100L, null, 2025));
        assertFalse(service.validateParameters(100L, "", 2025));
        assertFalse(service.validateParameters(100L, "   ", 2025));
    }

    @Test
    void validateParameters_WhenInvalidYear_ShouldReturnFalse() {
        // Act & Assert
        assertFalse(service.validateParameters(100L, "Reporting", null));
        assertFalse(service.validateParameters(100L, "Reporting", 2019));
        assertFalse(service.validateParameters(100L, "Reporting", 2031));
    }
}
