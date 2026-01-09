package com.example.demo.platform.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WelcomeControllerTest {

    @InjectMocks
    private WelcomeController welcomeController;

    @Test
    void welcome_ShouldReturnOkWithMessage() {
        // Act
        ResponseEntity<Map<String, Object>> result = welcomeController.welcome();

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        
        Map<String, Object> body = result.getBody();
        assertEquals("Welcome to MARLO API", body.get("message"));
        assertEquals("1.0.0", body.get("version"));
        assertNotNull(body.get("timestamp"));
        assertNotNull(body.get("documentation"));
        assertNotNull(body.get("endpoints"));
        
        @SuppressWarnings("unchecked")
        Map<String, String> endpoints = (Map<String, String>) body.get("endpoints");
        assertTrue(endpoints.containsKey("innovations"));
        assertTrue(endpoints.containsKey("actors"));
        assertTrue(endpoints.containsKey("comments"));
        assertTrue(endpoints.containsKey("swagger"));
    }
}
