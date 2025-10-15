package com.example.demo.modules.projectinnovation.adapters.rest;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for the stats endpoint
 * 
 * NOTE: This test is disabled (@Disabled) because:
 * 1. Requires a real database to be running
 * 2. Data can change and cause test failures
 * 3. Slower than unit tests with mocks
 * 
 * To enable it:
 * 1. Remove @Disabled annotation
 * 2. Configure test profile with test database
 * 3. Use known and stable test data
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Disabled("Requires test database configuration")
class ProjectInnovationStatsControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void getInnovationCountryStats_IntegrationTest_WithRealDatabase() {
        // Arrange - Este test usaría datos reales de la BD de test
        String url = "http://localhost:" + port + "/api/innovations/stats?phaseId=428";
        
        // Act
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        
        // Assert
        assertEquals(200, response.getStatusCode().value());
        String responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.contains("innovationCount"));
        assertTrue(responseBody.contains("countryCount"));
        assertTrue(responseBody.contains("phaseId"));
        
        // NOTA: No verificamos valores específicos porque pueden cambiar
        // En su lugar, verificamos la estructura de la respuesta
    }
}