package com.example.demo.modules.innovationtype.adapters.rest.dto;

/**
 * Response DTO for InnovationType REST API
 * Contains all information about an innovation type
 */
public record InnovationTypeResponse(
    Long id,
    String name,
    String definition,
    Boolean isOldType,
    Long prmsIdEquivalent,
    String prmsNameEquivalent
) {
    
    /**
     * Simplified response DTO for basic innovation type information
     * Used when full details are not needed
     */
    public record Simple(
        Long id,
        String name,
        Boolean isOldType
    ) {}
}