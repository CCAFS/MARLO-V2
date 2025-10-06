package com.example.demo.modules.projectinnovation.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Example service showing how to read properties using @Value
 */
@Service
public class PropertyReaderService {
    
    @Value("${marlo.innovation.pdf-generator.url}")
    private String pdfGeneratorUrl;
    
    @Value("${marlo.innovation.pdf-generator.url:http://default-url.com}")
    private String pdfGeneratorUrlWithDefault;
    
    public String getPdfGeneratorUrl() {
        return pdfGeneratorUrl;
    }
    
    public String getPdfGeneratorUrlWithDefault() {
        return pdfGeneratorUrlWithDefault;
    }
    
    public void printConfiguration() {
        System.out.println("📋 PDF Generator Configuration:");
        System.out.println("URL: " + pdfGeneratorUrl);
        System.out.println("URL with default: " + pdfGeneratorUrlWithDefault);
    }
}