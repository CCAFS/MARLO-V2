package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import java.util.List;

/**
 * Complete search response DTO for innovation search endpoint
 * Returns full innovation information with all relationships like the info endpoint
 */
public record ProjectInnovationCompleteSearchResponse(
    /**
     * List of complete innovations matching the search criteria (paginated)
     */
    List<InnovationInfo> innovations,
    
    /**
     * Total number of innovations found (before pagination)
     */
    Integer totalCount,
    
    /**
     * Applied filters summary (optional metadata)
     */
    SearchFilters appliedFilters,
    
    /**
     * Pagination information
     */
    PaginationInfo pagination
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
        List<Long> countryIds,
        List<Long> actorIds,
        String searchType  // "SDG_FILTERS", "GENERAL_FILTERS", or "ALL_ACTIVE"
    ) {}
    
    /**
     * Nested record for pagination information
     */
    public record PaginationInfo(
        Integer offset,
        Integer limit,
        Integer currentPage,
        Integer totalPages,
        Boolean hasNext,
        Boolean hasPrevious
    ) {
        public static PaginationInfo of(int offset, int limit, int totalCount) {
            int currentPage = (offset / limit) + 1;
            int totalPages = (int) Math.ceil((double) totalCount / limit);
            boolean hasNext = offset + limit < totalCount;
            boolean hasPrevious = offset > 0;
            
            return new PaginationInfo(offset, limit, currentPage, totalPages, hasNext, hasPrevious);
        }
    }
    
    /**
     * Constructor for creating response with pagination
     */
    public static ProjectInnovationCompleteSearchResponse of(
            List<InnovationInfo> innovations,
            int totalCount,
            SearchFilters appliedFilters,
            PaginationInfo pagination) {
        return new ProjectInnovationCompleteSearchResponse(
                innovations,
                totalCount,
                appliedFilters,
                pagination
        );
    }
    
    /**
     * Constructor for creating response without pagination (legacy)
     */
    public static ProjectInnovationCompleteSearchResponse of(
            List<InnovationInfo> innovations,
            SearchFilters appliedFilters) {
        return new ProjectInnovationCompleteSearchResponse(
                innovations,
                innovations != null ? innovations.size() : 0,
                appliedFilters,
                null
        );
    }
    
    /**
     * Simple constructor without filters metadata (legacy)
     */
    public static ProjectInnovationCompleteSearchResponse of(List<InnovationInfo> innovations) {
        return new ProjectInnovationCompleteSearchResponse(
                innovations,
                innovations != null ? innovations.size() : 0,
                null,
                null
        );
    }
}

