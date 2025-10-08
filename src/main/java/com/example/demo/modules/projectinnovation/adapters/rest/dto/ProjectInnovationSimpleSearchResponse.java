package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import java.util.List;

/**
 * Simplified response DTO for innovation search endpoint
 * Returns only essential fields with related entities
 */
public record ProjectInnovationSimpleSearchResponse(
    /**
     * List of simplified innovations matching the search criteria
     */
    List<ProjectInnovationSimpleResponse> innovations,
    
    /**
     * Total number of innovations found
     */
    Integer totalCount,
    
    /**
     * Applied filters summary (optional metadata)
     */
    SearchFilters appliedFilters
) {
    
    /**
     * Nested record for search filters metadata
     */
    public record SearchFilters(
        Long phase,
        Integer readinessScale,
        Long innovationTypeId,
        Long innovationId,
        Long sdgId,
        String searchType  // "SDG_FILTERS", "GENERAL_FILTERS", or "ALL_ACTIVE"
    ) {}
    
    /**
     * Constructor for creating response with automatic count calculation
     */
    public static ProjectInnovationSimpleSearchResponse of(
            List<ProjectInnovationSimpleResponse> innovations,
            SearchFilters appliedFilters) {
        return new ProjectInnovationSimpleSearchResponse(
                innovations,
                innovations != null ? innovations.size() : 0,
                appliedFilters
        );
    }
}