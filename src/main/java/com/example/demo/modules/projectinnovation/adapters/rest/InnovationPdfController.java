package com.example.demo.modules.projectinnovation.adapters.rest;

import com.example.demo.modules.projectinnovation.application.service.InnovationPdfReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for Innovation PDF Report generation
 */
@RestController
@RequestMapping("/api/innovations/pdf")
@Tag(name = "Innovation PDF Reports", description = "API for generating innovation PDF reports")
public class InnovationPdfController {
    
    private final InnovationPdfReportService pdfReportService;
    
    public InnovationPdfController(InnovationPdfReportService pdfReportService) {
        this.pdfReportService = pdfReportService;
    }
    
    @Operation(summary = "Generate PDF report URL using phase information", 
               description = "Generates PDF URL by consulting the phases table to get cycle and year from phase ID")
    @GetMapping("/url/by-phase")
    public ResponseEntity<PdfUrlResponse> generatePdfUrlByPhase(
            @Parameter(description = "Innovation ID", example = "1558")
            @RequestParam Long innovationId,
            @Parameter(description = "Phase ID (to get cycle and year from phases table)", example = "425")
            @RequestParam Long phaseId) {
        
        try {
            String pdfUrl = pdfReportService.generateInnovationPdfUrlByPhase(innovationId, phaseId);
            var phase = pdfReportService.getPhaseById(phaseId);
            
            return ResponseEntity.ok(new PdfUrlResponse(
                innovationId,
                pdfUrl,
                phase.getCycle(),
                phase.getYear(),
                "URL generated from phase information (ID: " + phaseId + ")"
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new PdfUrlResponse(
                innovationId,
                null,
                null,
                null,
                "Error: " + e.getMessage()
            ));
        }
    }
    
    @Operation(summary = "Generate PDF report URL with custom parameters")
    @GetMapping("/url/custom")
    public ResponseEntity<PdfUrlResponse> generateCustomPdfUrl(
            @Parameter(description = "Innovation ID", example = "1558")
            @RequestParam Long innovationId,
            @Parameter(description = "Cycle parameter", example = "Reporting")
            @RequestParam String cycle,
            @Parameter(description = "Year parameter", example = "2025")
            @RequestParam Integer year) {
        
        if (!pdfReportService.validateParameters(innovationId, cycle, year)) {
            return ResponseEntity.badRequest().build();
        }
        
        String pdfUrl = pdfReportService.generateInnovationPdfUrl(innovationId, cycle, year);
        return ResponseEntity.ok(new PdfUrlResponse(
            innovationId,
            pdfUrl,
            cycle,
            year,
            "URL generated with custom parameters"
        ));
    }
    
    // Response DTOs
    public record PdfUrlResponse(
        Long innovationId,
        String pdfUrl,
        String cycle,
        Integer year,
        String message
    ) {}
}