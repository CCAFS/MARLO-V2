package com.example.demo.platform.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Configuration properties for Innovation PDF Report Generator
 */
@Component
@ConfigurationProperties(prefix = "marlo.innovation.pdf-generator")
public class InnovationPdfGeneratorProperties {
    
    /**
     * Base URL of the PDF report generator service
     */
    private String url = "https://aiccratest.ciat.cgiar.org/summaries/AICCRA/projectInnovationSummary.do";
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    /**
     * Build complete URL with parameters
     * @param innovationId Innovation ID parameter
     * @param cycle Cycle parameter (e.g., "Reporting")
     * @param year Year parameter (e.g., 2025)
     * @return Complete URL with parameters
     */
    public String buildUrlWithParameters(Long innovationId, String cycle, Integer year) {
        return UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("innovationID", innovationId)
                .queryParam("cycle", cycle)
                .queryParam("year", year)
                .toUriString();
    }
    
    /**
     * Build URL with default cycle and year
     * @param innovationId Innovation ID parameter
     * @return Complete URL with default parameters
     */
    public String buildDefaultUrl(Long innovationId) {
        return buildUrlWithParameters(innovationId, "Reporting", 2025);
    }
}