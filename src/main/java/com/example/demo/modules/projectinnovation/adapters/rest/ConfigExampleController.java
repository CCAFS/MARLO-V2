package com.example.demo.modules.projectinnovation.adapters.rest;

import com.example.demo.platform.config.InnovationPdfGeneratorProperties;
import com.example.demo.modules.projectinnovation.application.service.PropertyReaderService;
import com.example.demo.modules.projectinnovation.application.service.EnvironmentPropertyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.HashMap;

/**
 * Example controller showing different ways to read the PDF generator property
 */
@RestController
@RequestMapping("/api/config")
public class ConfigExampleController {
    
    private final InnovationPdfGeneratorProperties pdfGeneratorProperties;
    private final PropertyReaderService propertyReaderService;
    private final EnvironmentPropertyService environmentPropertyService;
    
    @Value("${marlo.innovation.pdf-generator.url}")
    private String pdfUrlDirect;
    
    public ConfigExampleController(
            InnovationPdfGeneratorProperties pdfGeneratorProperties,
            PropertyReaderService propertyReaderService,
            EnvironmentPropertyService environmentPropertyService) {
        this.pdfGeneratorProperties = pdfGeneratorProperties;
        this.propertyReaderService = propertyReaderService;
        this.environmentPropertyService = environmentPropertyService;
    }
    
    @GetMapping("/pdf-generator-url")
    public String getPdfGeneratorUrl() {
        return pdfGeneratorProperties.getUrl();
    }
    
    @GetMapping("/config-info")
    public ConfigInfo getConfigInfo() {
        return new ConfigInfo(
            "PDF Generator URL", 
            pdfGeneratorProperties.getUrl(),
            "Configured from application-local.properties"
        );
    }
    
    @GetMapping("/all-methods")
    public Map<String, String> getAllMethods() {
        Map<String, String> methods = new HashMap<>();
        
        methods.put("1_ConfigurationProperties", pdfGeneratorProperties.getUrl());
        methods.put("2_Value_Annotation", pdfUrlDirect);
        methods.put("3_PropertyReaderService", propertyReaderService.getPdfGeneratorUrl());
        methods.put("4_EnvironmentService", environmentPropertyService.getPdfGeneratorUrl());
        
        return methods;
    }
    
    @GetMapping("/test-property")
    public TestPropertyResponse testProperty() {
        return new TestPropertyResponse(
            pdfGeneratorProperties.getUrl(),
            environmentPropertyService.hasPdfGeneratorProperty(),
            "Property loaded successfully from application-local.properties"
        );
    }
    
    public record ConfigInfo(String name, String value, String source) {}
    public record TestPropertyResponse(String url, boolean exists, String message) {}
}