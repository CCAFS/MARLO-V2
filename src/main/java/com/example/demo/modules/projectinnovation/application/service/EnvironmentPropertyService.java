package com.example.demo.modules.projectinnovation.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * Example service showing how to read properties using Environment
 */
@Service
public class EnvironmentPropertyService {
    
    private static final String PDF_GENERATOR_URL_PROPERTY = "marlo.innovation.pdf-generator.url";
    private static final Logger logger = LoggerFactory.getLogger(EnvironmentPropertyService.class);
    private final Environment environment;
    
    public EnvironmentPropertyService(Environment environment) {
        this.environment = environment;
    }
    
    public String getPdfGeneratorUrl() {
        return environment.getProperty(PDF_GENERATOR_URL_PROPERTY);
    }
    
    public String getPdfGeneratorUrlWithDefault() {
        return environment.getProperty(PDF_GENERATOR_URL_PROPERTY, "http://default-service.com");
    }
    
    public boolean hasPdfGeneratorProperty() {
        return environment.containsProperty(PDF_GENERATOR_URL_PROPERTY);
    }
    
    public void printAllMarloProperties() {
        logger.info("🔍 All MARLO properties:");
        
        // This is a simple way to check if property exists
        String pdfUrl = environment.getProperty(PDF_GENERATOR_URL_PROPERTY);
        if (pdfUrl != null) {
            logger.info("{} = {}", PDF_GENERATOR_URL_PROPERTY, pdfUrl);
        }
        
        // You can add more properties as needed
        String[] propertiesToCheck = {
            PDF_GENERATOR_URL_PROPERTY,
            "marlo.innovation.timeout",
            "marlo.innovation.retry-attempts"
        };
        
        for (String property : propertiesToCheck) {
            String value = environment.getProperty(property);
            if (value != null) {
                logger.info("{} = {}", property, value);
            }
        }
    }
}
