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
    SearchFilters appliedFilters,
    
    /**
     * Pagination information (optional)
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
        Integer totalCount,
        Integer currentPage,
        Integer totalPages,
        Boolean hasNext,
        Boolean hasPrevious
    ) {
        /**
         * Factory method to create pagination info from offset, limit and total count
         */
        public static PaginationInfo of(int offset, int limit, int totalCount) {
            int currentPage = (offset / limit) + 1;
            int totalPages = (int) Math.ceil((double) totalCount / limit);
            boolean hasNext = offset + limit < totalCount;
            boolean hasPrevious = offset > 0;
            
            return new PaginationInfo(
                offset, 
                limit, 
                totalCount, 
                currentPage, 
                totalPages, 
                hasNext, 
                hasPrevious
            );
        }
    }
    
    /**
     * Constructor for creating response with automatic count calculation (backward compatibility)
     */
    public static ProjectInnovationSimpleSearchResponse of(
            List<ProjectInnovationSimpleResponse> innovations,
            SearchFilters appliedFilters) {
        return new ProjectInnovationSimpleSearchResponse(
                innovations,
                innovations != null ? innovations.size() : 0,
                appliedFilters,
                null  // No pagination info
        );
    }
    
    /**
     * Constructor for creating response with pagination support
     */
    public static ProjectInnovationSimpleSearchResponse of(
            List<ProjectInnovationSimpleResponse> innovations,
            Integer totalCount,
            SearchFilters appliedFilters,
            PaginationInfo pagination) {
        return new ProjectInnovationSimpleSearchResponse(
                innovations,
                totalCount,
                appliedFilters,
                pagination
        );
    }
}
