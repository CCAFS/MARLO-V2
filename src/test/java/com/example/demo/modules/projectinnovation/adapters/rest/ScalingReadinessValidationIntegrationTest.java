package com.example.demo.modules.projectinnovation.adapters.rest;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
// import org.springframework.test.context.ActiveProfiles;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test to validate that the endpoint calculation matches the SQL calculation
 * for average scaling readiness
 * 
 * NOTE: This test requires a real database connection and is disabled by default.
 * To enable: Remove @Disabled and ensure test database is configured.
 */
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @ActiveProfiles("local")
@Disabled("Requires database connection - enable for integration testing")
class ScalingReadinessValidationIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DataSource dataSource;

    @Test
    void validatePhase428_EndpointMatchesSQLCalculation() throws Exception {
        // Arrange
        Long phaseId = 428L;
        
        // Get result from SQL query
        Double sqlResult = calculateAverageScalingReadinessFromSQL(phaseId);
        
        // Get result from endpoint
        String url = "http://localhost:" + port + "/api/innovations/stats?phaseId=" + phaseId;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        
        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        String responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.contains("averageScalingReadiness"));
        
        // Extract the averageScalingReadiness value from JSON response
        // For a more robust test, you would use a JSON parser like Jackson
        String jsonResponse = response.getBody();
        String pattern = "\"averageScalingReadiness\":(\\d+\\.\\d+)";
        java.util.regex.Pattern r = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = r.matcher(jsonResponse);
        
        assertTrue(m.find(), "averageScalingReadiness not found in response");
        Double endpointResult = Double.parseDouble(m.group(1));
        
        // Assert that SQL and endpoint results match (with small tolerance for floating point precision)
        assertEquals(sqlResult, endpointResult, 0.000001, 
                "SQL result (" + sqlResult + ") should match endpoint result (" + endpointResult + ")");
    }

    @Test
    void validateMultiplePhases_ConsistencyBetweenSQLAndEndpoint() throws Exception {
        // Test multiple phases to ensure consistency
        Long[] testPhases = {1L, 428L, 100L}; // Add more phases as needed
        
        for (Long phaseId : testPhases) {
            try {
                // Get SQL result
                Double sqlResult = calculateAverageScalingReadinessFromSQL(phaseId);
                
                // Get endpoint result
                String url = "http://localhost:" + port + "/api/innovations/stats?phaseId=" + phaseId;
                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
                
                if (response.getStatusCode().value() == 200) {
                    // Extract averageScalingReadiness from response
                    String jsonResponse = response.getBody();
                    String pattern = "\"averageScalingReadiness\":(\\d+\\.\\d+)";
                    java.util.regex.Pattern r = java.util.regex.Pattern.compile(pattern);
                    java.util.regex.Matcher m = r.matcher(jsonResponse);
                    
                    if (m.find()) {
                        Double endpointResult = Double.parseDouble(m.group(1));
                        assertEquals(sqlResult, endpointResult, 0.000001,
                                "Phase " + phaseId + ": SQL (" + sqlResult + ") != Endpoint (" + endpointResult + ")");
                    }
                }
            } catch (Exception e) {
                // Log warning but don't fail test if phase has no data
                System.out.println("Warning: Could not validate phase " + phaseId + ": " + e.getMessage());
            }
        }
    }

    @Test
    void validateCountCalculations_SQLvsEndpoint() throws Exception {
        // Validate that country and innovation counts also match between SQL and endpoint
        Long phaseId = 428L;
        
        // Get counts from SQL
        int[] sqlCounts = getCountsFromSQL(phaseId);
        int sqlCountryCount = sqlCounts[0];
        int sqlInnovationCount = sqlCounts[1];
        
        // Get counts from endpoint
        String url = "http://localhost:" + port + "/api/innovations/stats?phaseId=" + phaseId;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        
        assertEquals(200, response.getStatusCode().value());
        String jsonResponse = response.getBody();
        
        // Extract counts (simplified regex - in production use proper JSON parsing)
        java.util.regex.Pattern countryPattern = java.util.regex.Pattern.compile("\"countryCount\":(\\d+)");
        java.util.regex.Pattern innovationPattern = java.util.regex.Pattern.compile("\"innovationCount\":(\\d+)");
        
        java.util.regex.Matcher countryMatcher = countryPattern.matcher(jsonResponse);
        java.util.regex.Matcher innovationMatcher = innovationPattern.matcher(jsonResponse);
        
        assertTrue(countryMatcher.find(), "countryCount not found in response");
        assertTrue(innovationMatcher.find(), "innovationCount not found in response");
        
        int endpointCountryCount = Integer.parseInt(countryMatcher.group(1));
        int endpointInnovationCount = Integer.parseInt(innovationMatcher.group(1));
        
        assertEquals(sqlCountryCount, endpointCountryCount, "Country count mismatch");
        assertEquals(sqlInnovationCount, endpointInnovationCount, "Innovation count mismatch");
    }

    /**
     * Calculate average scaling readiness directly from database using SQL
     */
    private Double calculateAverageScalingReadinessFromSQL(Long phaseId) throws Exception {
        String sql = "SELECT AVG(readiness_scale) as avg_readiness " +
                    "FROM project_innovation_info " +
                    "WHERE id_phase = ? AND readiness_scale IS NOT NULL";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, phaseId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Double result = rs.getDouble("avg_readiness");
                return rs.wasNull() ? 0.0 : result;
            }
            return 0.0;
        }
    }

    /**
     * Get country and innovation counts directly from database using SQL
     * @return array with [countryCount, innovationCount]
     */
    private int[] getCountsFromSQL(Long phaseId) throws Exception {
        // Country count query
        String countrySQL = "SELECT COUNT(DISTINCT pic.id_country) as country_count " +
                           "FROM project_innovation_countries pic " +
                           "JOIN project_innovations pi ON pic.project_innovation_id = pi.id " +
                           "WHERE pi.is_active = 1 AND pic.id_phase = ?";
        
        // Innovation count query  
        String innovationSQL = "SELECT COUNT(DISTINCT pic.project_innovation_id) as innovation_count " +
                              "FROM project_innovation_countries pic " +
                              "JOIN project_innovations pi ON pic.project_innovation_id = pi.id " +
                              "WHERE pi.is_active = 1 AND pic.id_phase = ?";
        
        int countryCount = 0;
        int innovationCount = 0;
        
        try (Connection conn = dataSource.getConnection()) {
            // Get country count
            try (PreparedStatement stmt = conn.prepareStatement(countrySQL)) {
                stmt.setLong(1, phaseId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    countryCount = rs.getInt("country_count");
                }
            }
            
            // Get innovation count
            try (PreparedStatement stmt = conn.prepareStatement(innovationSQL)) {
                stmt.setLong(1, phaseId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    innovationCount = rs.getInt("innovation_count");
                }
            }
        }
        
        return new int[]{countryCount, innovationCount};
    }
}