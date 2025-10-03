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
    
    @Operation(summary = "Generate PDF report URL with default parameters")
    @GetMapping("/url/{innovationId}")
    public ResponseEntity<PdfUrlResponse> generatePdfUrl(
            @Parameter(description = "Innovation ID", example = "1558")
            @PathVariable Long innovationId) {
        
        String pdfUrl = pdfReportService.generateInnovationPdfUrl(innovationId);
        return ResponseEntity.ok(new PdfUrlResponse(
            innovationId,
            pdfUrl,
            "Reporting",
            2025,
            "URL generated with default parameters"
        ));
    }
    
    @Operation(summary = "Generate PDF report URL with custom parameters")
    @GetMapping("/url/{innovationId}/custom")
    public ResponseEntity<PdfUrlResponse> generateCustomPdfUrl(
            @Parameter(description = "Innovation ID", example = "1558")
            @PathVariable Long innovationId,
            @Parameter(description = "Cycle parameter", example = "Reporting")
            @RequestParam(defaultValue = "Reporting") String cycle,
            @Parameter(description = "Year parameter", example = "2025")
            @RequestParam(defaultValue = "2025") Integer year) {
        
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
    
    @Operation(summary = "Get base PDF generator configuration")
    @GetMapping("/config")
    public ResponseEntity<PdfConfigResponse> getPdfConfig() {
        String baseUrl = pdfReportService.getPdfGeneratorBaseUrl();
        return ResponseEntity.ok(new PdfConfigResponse(
            baseUrl,
            "Base URL configured from application-local.properties",
            new String[]{"innovationID", "cycle", "year"}
        ));
    }
    
    @Operation(summary = "Validate PDF generation parameters")
    @GetMapping("/validate")
    public ResponseEntity<ValidationResponse> validateParameters(
            @Parameter(description = "Innovation ID", example = "1558")
            @RequestParam Long innovationId,
            @Parameter(description = "Cycle parameter", example = "Reporting")
            @RequestParam String cycle,
            @Parameter(description = "Year parameter", example = "2025")
            @RequestParam Integer year) {
        
        boolean isValid = pdfReportService.validateParameters(innovationId, cycle, year);
        return ResponseEntity.ok(new ValidationResponse(
            isValid,
            isValid ? "Parameters are valid" : "Invalid parameters provided",
            innovationId,
            cycle,
            year
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
    
    public record PdfConfigResponse(
        String baseUrl,
        String source,
        String[] requiredParameters
    ) {}
    
    public record ValidationResponse(
        boolean valid,
        String message,
        Long innovationId,
        String cycle,
        Integer year
    ) {}
}