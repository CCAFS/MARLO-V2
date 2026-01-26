package com.example.demo.modules.projectinnovation.adapters.rest;

import com.example.demo.modules.projectinnovation.application.service.InnovationPdfReportService;
import com.example.demo.modules.projectinnovation.domain.model.Phase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InnovationPdfControllerTest {

    @Mock
    private InnovationPdfReportService pdfReportService;

    @InjectMocks
    private InnovationPdfController controller;

    private Phase testPhase;

    @BeforeEach
    void setUp() {
        testPhase = new Phase();
        testPhase.setId(1L);
        testPhase.setDescription("Reporting Phase");
        testPhase.setYear(2025);
    }

    @Test
    void generatePdfUrlByPhase_WhenValid_ShouldReturnOk() {
        // Arrange
        Long innovationId = 100L;
        Long phaseId = 1L;
        String expectedUrl = "http://pdf-service.com/api/pdf/innovation-report?innovationId=100&cycle=Reporting&year=2025";
        
        when(pdfReportService.generateInnovationPdfUrlByPhase(innovationId, phaseId)).thenReturn(expectedUrl);
        when(pdfReportService.getPhaseById(phaseId)).thenReturn(testPhase);

        // Act
        ResponseEntity<InnovationPdfController.PdfUrlResponse> result = 
            controller.generatePdfUrlByPhase(innovationId, phaseId);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(expectedUrl, result.getBody().pdfUrl());
        assertEquals(testPhase.getCycle(), result.getBody().cycle());
        verify(pdfReportService).generateInnovationPdfUrlByPhase(innovationId, phaseId);
        verify(pdfReportService).getPhaseById(phaseId);
    }

    @Test
    void generatePdfUrlByPhase_WhenPhaseNotFound_ShouldReturnBadRequest() {
        // Arrange
        Long innovationId = 100L;
        Long phaseId = 999L;
        when(pdfReportService.generateInnovationPdfUrlByPhase(innovationId, phaseId))
            .thenThrow(new RuntimeException("Phase not found: 999"));

        // Act
        ResponseEntity<InnovationPdfController.PdfUrlResponse> result = 
            controller.generatePdfUrlByPhase(innovationId, phaseId);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNotNull(result.getBody());
        assertNull(result.getBody().pdfUrl());
        assertTrue(result.getBody().message().contains("Error"));
    }

    @Test
    void generateCustomPdfUrl_WhenValid_ShouldReturnOk() {
        // Arrange
        Long innovationId = 100L;
        String cycle = "Reporting";
        Integer year = 2025;
        String expectedUrl = "http://pdf-service.com/api/pdf/innovation-report?innovationId=100&cycle=Reporting&year=2025";
        
        when(pdfReportService.validateParameters(innovationId, cycle, year)).thenReturn(true);
        when(pdfReportService.generateInnovationPdfUrl(innovationId, cycle, year)).thenReturn(expectedUrl);

        // Act
        ResponseEntity<InnovationPdfController.PdfUrlResponse> result = 
            controller.generateCustomPdfUrl(innovationId, cycle, year);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(expectedUrl, result.getBody().pdfUrl());
        assertEquals(cycle, result.getBody().cycle());
        assertEquals(year, result.getBody().year());
        verify(pdfReportService).validateParameters(innovationId, cycle, year);
        verify(pdfReportService).generateInnovationPdfUrl(innovationId, cycle, year);
    }

    @Test
    void generateCustomPdfUrl_WhenInvalidParameters_ShouldReturnBadRequest() {
        // Arrange
        Long innovationId = 100L;
        String cycle = "Reporting";
        Integer year = 2019; // Invalid year
        
        when(pdfReportService.validateParameters(innovationId, cycle, year)).thenReturn(false);

        // Act
        ResponseEntity<InnovationPdfController.PdfUrlResponse> result = 
            controller.generateCustomPdfUrl(innovationId, cycle, year);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        verify(pdfReportService).validateParameters(innovationId, cycle, year);
        verify(pdfReportService, never()).generateInnovationPdfUrl(any(), any(), any());
    }
}
