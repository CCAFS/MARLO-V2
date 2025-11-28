package com.example.demo.modules.actors.adapters.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for Actor
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Actor response data")
public class ActorResponse {
    
    @Schema(description = "Actor ID", example = "1")
    private Long id;
    
    @Schema(description = "Actor name", example = "Farmer")
    private String name;
    
    @Schema(description = "PRMS name equivalent", example = "Agricultural Producer")
    private String prmsNameEquivalent;
}
