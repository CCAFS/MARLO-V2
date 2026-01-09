package com.example.demo.modules.projectinnovation.application.service;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class PropertyReaderServiceTest {

    private PropertyReaderService propertyReaderService = new PropertyReaderService();

    @Test
    void getPdfGeneratorUrl_ShouldReturnConfiguredUrl() {
        // Arrange
        String expectedUrl = "http://test-pdf-service.com/api/pdf";
        ReflectionTestUtils.setField(propertyReaderService, "pdfGeneratorUrl", expectedUrl);

        // Act
        String result = propertyReaderService.getPdfGeneratorUrl();

        // Assert
        assertNotNull(result);
        assertEquals(expectedUrl, result);
    }

    @Test
    void getPdfGeneratorUrlWithDefault_ShouldReturnConfiguredUrl() {
        // Arrange
        String expectedUrl = "http://test-pdf-service.com/api/pdf";
        ReflectionTestUtils.setField(propertyReaderService, "pdfGeneratorUrlWithDefault", expectedUrl);

        // Act
        String result = propertyReaderService.getPdfGeneratorUrlWithDefault();

        // Assert
        assertNotNull(result);
        assertEquals(expectedUrl, result);
    }

    @Test
    void printConfiguration_ShouldNotThrowException() {
        // Arrange
        ReflectionTestUtils.setField(propertyReaderService, "pdfGeneratorUrl", "http://test-url.com");
        ReflectionTestUtils.setField(propertyReaderService, "pdfGeneratorUrlWithDefault", "http://default-url.com");

        // Act & Assert - Should not throw exception
        assertDoesNotThrow(() -> propertyReaderService.printConfiguration());
    }
}
