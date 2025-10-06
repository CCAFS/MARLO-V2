package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import com.example.demo.modules.projectinnovation.adapters.rest.ProjectInnovationInfoResponse;
import java.util.List;

/**
 * Response DTO for innovation search endpoint
 * Includes both the list of innovations and the total count
 */
public record ProjectInnovationSearchResponse(
    /**
     * List of innovations matching the search criteria
     */
    List<ProjectInnovationInfoResponse> innovations,
    
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
    public static ProjectInnovationSearchResponse of(
            List<ProjectInnovationInfoResponse> innovations,
            SearchFilters appliedFilters) {
        return new ProjectInnovationSearchResponse(
                innovations,
                innovations != null ? innovations.size() : 0,
                appliedFilters
        );
    }
    
    /**
     * Simple constructor without filters metadata
     */
    public static ProjectInnovationSearchResponse of(List<ProjectInnovationInfoResponse> innovations) {
        return new ProjectInnovationSearchResponse(
                innovations,
                innovations != null ? innovations.size() : 0,
                null
        );
    }
}