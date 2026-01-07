package com.example.demo.platform.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "Welcome", description = "Welcome and health check endpoints")
public class WelcomeController {

    @Operation(summary = "Welcome endpoint", 
               description = "Returns a welcome message and API information")
    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> welcome() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to MARLO API");
        response.put("version", "1.0.0");
        response.put("description", "MARLO is an online platform assisting AICCRA project in their strategic results-based program planning and reporting");
        response.put("timestamp", LocalDateTime.now());
        response.put("documentation", "/swagger-ui.html");
        response.put("endpoints", Map.of(
            "innovations", "/api/innovations",
            "actors", "/api/actors",
            "comments", "/api/comments",
            "swagger", "/swagger-ui.html"
        ));
        
        return ResponseEntity.ok(response);
    }
}
