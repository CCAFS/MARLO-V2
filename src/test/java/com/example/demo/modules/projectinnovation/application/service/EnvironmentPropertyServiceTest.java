package com.example.demo.modules.projectinnovation.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnvironmentPropertyServiceTest {

    @Mock
    private Environment environment;

    @InjectMocks
    private EnvironmentPropertyService service;

    @Test
    void getPdfGeneratorUrl_WhenPropertyExists_ShouldReturnValue() {
        // Arrange
        String expectedUrl = "http://test-pdf-service.com/api/pdf";
        when(environment.getProperty("marlo.innovation.pdf-generator.url"))
            .thenReturn(expectedUrl);

        // Act
        String result = service.getPdfGeneratorUrl();

        // Assert
        assertNotNull(result);
        assertEquals(expectedUrl, result);
        verify(environment).getProperty("marlo.innovation.pdf-generator.url");
    }

    @Test
    void getPdfGeneratorUrl_WhenPropertyNotExists_ShouldReturnNull() {
        // Arrange
        when(environment.getProperty("marlo.innovation.pdf-generator.url"))
            .thenReturn(null);

        // Act
        String result = service.getPdfGeneratorUrl();

        // Assert
        assertNull(result);
        verify(environment).getProperty("marlo.innovation.pdf-generator.url");
    }

    @Test
    void getPdfGeneratorUrlWithDefault_WhenPropertyExists_ShouldReturnValue() {
        // Arrange
        String expectedUrl = "http://test-pdf-service.com/api/pdf";
        when(environment.getProperty("marlo.innovation.pdf-generator.url", "http://default-service.com"))
            .thenReturn(expectedUrl);

        // Act
        String result = service.getPdfGeneratorUrlWithDefault();

        // Assert
        assertNotNull(result);
        assertEquals(expectedUrl, result);
        verify(environment).getProperty("marlo.innovation.pdf-generator.url", "http://default-service.com");
    }

    @Test
    void getPdfGeneratorUrlWithDefault_WhenPropertyNotExists_ShouldReturnDefault() {
        // Arrange
        String defaultUrl = "http://default-service.com";
        when(environment.getProperty("marlo.innovation.pdf-generator.url", defaultUrl))
            .thenReturn(defaultUrl);

        // Act
        String result = service.getPdfGeneratorUrlWithDefault();

        // Assert
        assertNotNull(result);
        assertEquals(defaultUrl, result);
    }

    @Test
    void hasPdfGeneratorProperty_WhenPropertyExists_ShouldReturnTrue() {
        // Arrange
        when(environment.containsProperty("marlo.innovation.pdf-generator.url"))
            .thenReturn(true);

        // Act
        boolean result = service.hasPdfGeneratorProperty();

        // Assert
        assertTrue(result);
        verify(environment).containsProperty("marlo.innovation.pdf-generator.url");
    }

    @Test
    void hasPdfGeneratorProperty_WhenPropertyNotExists_ShouldReturnFalse() {
        // Arrange
        when(environment.containsProperty("marlo.innovation.pdf-generator.url"))
            .thenReturn(false);

        // Act
        boolean result = service.hasPdfGeneratorProperty();

        // Assert
        assertFalse(result);
        verify(environment).containsProperty("marlo.innovation.pdf-generator.url");
    }

    @Test
    void printAllMarloProperties_WhenPdfUrlExists_ShouldPrintIt() {
        // Arrange
        when(environment.getProperty("marlo.innovation.pdf-generator.url"))
            .thenReturn("http://test-url.com");
        when(environment.getProperty("marlo.innovation.timeout"))
            .thenReturn(null);
        when(environment.getProperty("marlo.innovation.retry-attempts"))
            .thenReturn(null);

        // Act & Assert
        assertDoesNotThrow(() -> service.printAllMarloProperties());
        verify(environment, atLeastOnce()).getProperty("marlo.innovation.pdf-generator.url");
    }

    @Test
    void printAllMarloProperties_WhenPdfUrlIsNull_ShouldNotPrintIt() {
        // Arrange
        when(environment.getProperty("marlo.innovation.pdf-generator.url"))
            .thenReturn(null);
        when(environment.getProperty("marlo.innovation.timeout"))
            .thenReturn("30");
        when(environment.getProperty("marlo.innovation.retry-attempts"))
            .thenReturn("3");

        // Act & Assert
        assertDoesNotThrow(() -> service.printAllMarloProperties());
        verify(environment, atLeastOnce()).getProperty("marlo.innovation.pdf-generator.url");
    }

    @Test
    void printAllMarloProperties_WhenAllPropertiesExist_ShouldPrintAll() {
        // Arrange
        when(environment.getProperty("marlo.innovation.pdf-generator.url"))
            .thenReturn("http://test-url.com");
        when(environment.getProperty("marlo.innovation.timeout"))
            .thenReturn("30");
        when(environment.getProperty("marlo.innovation.retry-attempts"))
            .thenReturn("3");

        // Act & Assert
        assertDoesNotThrow(() -> service.printAllMarloProperties());
        verify(environment, atLeastOnce()).getProperty("marlo.innovation.pdf-generator.url");
        verify(environment).getProperty("marlo.innovation.timeout");
        verify(environment).getProperty("marlo.innovation.retry-attempts");
    }

    @Test
    void printAllMarloProperties_WhenAllPropertiesAreNull_ShouldNotThrowException() {
        // Arrange
        when(environment.getProperty(anyString())).thenReturn(null);

        // Act & Assert
        assertDoesNotThrow(() -> service.printAllMarloProperties());
        verify(environment, atLeastOnce()).getProperty(anyString());
    }
}
