package com.example.demo.modules.projectinnovation.application.service;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * Example service showing how to read properties using Environment
 */
@Service
public class EnvironmentPropertyService {
    
    private final Environment environment;
    
    public EnvironmentPropertyService(Environment environment) {
        this.environment = environment;
    }
    
    public String getPdfGeneratorUrl() {
        return environment.getProperty("marlo.innovation.pdf-generator.url");
    }
    
    public String getPdfGeneratorUrlWithDefault() {
        return environment.getProperty("marlo.innovation.pdf-generator.url", "http://default-service.com");
    }
    
    public boolean hasPdfGeneratorProperty() {
        return environment.containsProperty("marlo.innovation.pdf-generator.url");
    }
    
    public void printAllMarloProperties() {
        System.out.println("🔍 All MARLO properties:");
        
        // This is a simple way to check if property exists
        String pdfUrl = environment.getProperty("marlo.innovation.pdf-generator.url");
        if (pdfUrl != null) {
            System.out.println("marlo.innovation.pdf-generator.url = " + pdfUrl);
        }
        
        // You can add more properties as needed
        String[] propertiesToCheck = {
            "marlo.innovation.pdf-generator.url",
            "marlo.innovation.timeout",
            "marlo.innovation.retry-attempts"
        };
        
        for (String property : propertiesToCheck) {
            String value = environment.getProperty(property);
            if (value != null) {
                System.out.println(property + " = " + value);
            }
        }
    }
}