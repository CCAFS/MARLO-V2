package com.example.demo.modules.projectinnovation.application.service;

import com.example.demo.platform.config.InnovationPdfGeneratorProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

/**
 * Service for generating PDF reports of innovations
 */
@Service
public class InnovationPdfReportService {
    
    private final InnovationPdfGeneratorProperties pdfGeneratorProperties;
    private final RestTemplate restTemplate;
    
    public InnovationPdfReportService(InnovationPdfGeneratorProperties pdfGeneratorProperties) {
        this.pdfGeneratorProperties = pdfGeneratorProperties;
        this.restTemplate = new RestTemplate();
    }
    
    /**
     * Generate PDF report URL for a specific innovation with default parameters
     * @param innovationId The ID of the innovation
     * @return Complete URL for PDF generation
     */
    public String generateInnovationPdfUrl(Long innovationId) {
        return pdfGeneratorProperties.buildDefaultUrl(innovationId);
    }
    
    /**
     * Generate PDF report URL for a specific innovation with custom parameters
     * @param innovationId The ID of the innovation
     * @param cycle The cycle (e.g., "Reporting")
     * @param year The year (e.g., 2025)
     * @return Complete URL for PDF generation
     */
    public String generateInnovationPdfUrl(Long innovationId, String cycle, Integer year) {
        return pdfGeneratorProperties.buildUrlWithParameters(innovationId, cycle, year);
    }
    
    /**
     * Download PDF report as bytes (for direct download)
     * @param innovationId The ID of the innovation
     * @param cycle The cycle parameter
     * @param year The year parameter
     * @return PDF bytes
     */
    public byte[] downloadInnovationPdfReport(Long innovationId, String cycle, Integer year) {
        String url = generateInnovationPdfUrl(innovationId, cycle, year);
        
        try {
            ResponseEntity<byte[]> response = restTemplate.exchange(
                url, 
                HttpMethod.GET, 
                null, 
                byte[].class
            );
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error downloading PDF report for innovation " + innovationId, e);
        }
    }
    
    /**
     * Get the configured base PDF generator URL
     * @return The PDF generator service base URL
     */
    public String getPdfGeneratorBaseUrl() {
        return pdfGeneratorProperties.getUrl();
    }
    
    /**
     * Validate innovation PDF parameters
     * @param innovationId Innovation ID
     * @param cycle Cycle parameter
     * @param year Year parameter
     * @return true if parameters are valid
     */
    public boolean validateParameters(Long innovationId, String cycle, Integer year) {
        return innovationId != null && innovationId > 0 &&
               cycle != null && !cycle.trim().isEmpty() &&
               year != null && year > 2020 && year <= 2030;
    }
}