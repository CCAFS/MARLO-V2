package com.example.demo.platform.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class InnovationPdfGeneratorPropertiesTest {

    private InnovationPdfGeneratorProperties properties;

    @BeforeEach
    void setUp() {
        properties = new InnovationPdfGeneratorProperties();
    }

    @Test
    void setAndGetUrl_ShouldWorkCorrectly() {
        // Arrange
        String expectedUrl = "http://localhost:3000/api/pdf";
        
        // Act
        properties.setUrl(expectedUrl);
        
        // Assert
        assertEquals(expectedUrl, properties.getUrl());
    }

    @Test
    void defaultUrl_ShouldHaveDefaultValue() {
        // Assert
        assertNotNull(properties.getUrl());
        assertTrue(properties.getUrl().contains("aiccratest.ciat.cgiar.org"));
    }

    @Test
    void setUrl_WithNullValue_ShouldAcceptNull() {
        // Act
        properties.setUrl(null);
        
        // Assert
        assertNull(properties.getUrl());
    }

    @Test
    void setUrl_WithEmptyString_ShouldAcceptEmpty() {
        // Arrange
        String emptyUrl = "";
        
        // Act
        properties.setUrl(emptyUrl);
        
        // Assert
        assertEquals(emptyUrl, properties.getUrl());
    }

    @Test
    void setUrl_WithWhitespace_ShouldPreserveWhitespace() {
        // Arrange
        String urlWithSpaces = "  http://example.com  ";
        
        // Act
        properties.setUrl(urlWithSpaces);
        
        // Assert
        assertEquals(urlWithSpaces, properties.getUrl());
    }
}