package com.example.demo.modules.projectinnovation.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Example service showing how to read properties using @Value
 */
@Service
public class PropertyReaderService {

    private static final Logger logger = LoggerFactory.getLogger(PropertyReaderService.class);
    
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
        logger.info("📋 PDF Generator Configuration:");
        logger.info("URL: {}", pdfGeneratorUrl);
        logger.info("URL with default: {}", pdfGeneratorUrlWithDefault);
    }
}