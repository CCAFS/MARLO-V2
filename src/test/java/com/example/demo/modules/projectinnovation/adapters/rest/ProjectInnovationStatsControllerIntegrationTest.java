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
 * Test de integración para el endpoint de stats
 * 
 * NOTA: Este test está deshabilitado (@Disabled) porque:
 * 1. Requiere una base de datos real funcionando
 * 2. Los datos pueden cambiar y hacer fallar los tests
 * 3. Es más lento que los tests unitarios con mocks
 * 
 * Para habilitarlo:
 * 1. Eliminar @Disabled
 * 2. Configurar perfil de test con base de datos de prueba
 * 3. Usar datos de test conocidos y estables
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Disabled("Requiere base de datos de test configurada")
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