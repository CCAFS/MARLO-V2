package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import com.example.demo.modules.projectinnovation.adapters.rest.ProjectInnovationInfoResponse;
import java.util.List;

/**
 * Response DTO for innovation search endpoint
 * Includes both the list of innovations and the total count
 */
public record ProjectInnovationSearchResponse(
    /**
     * List of innovations matching the search criteria (paginated)
     */
    List<ProjectInnovationInfoResponse> innovations,
    
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
        String searchType  // "SDG_FILTERS", "GENERAL_FILTERS", or "ALL_ACTIVE"
    ) {}
    
    /**
     * Nested record for pagination metadata
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
    public static ProjectInnovationSearchResponse of(
            List<ProjectInnovationInfoResponse> innovations,
            int totalCount,
            SearchFilters appliedFilters,
            PaginationInfo pagination) {
        return new ProjectInnovationSearchResponse(
                innovations,
                totalCount,
                appliedFilters,
                pagination
        );
    }
    
    /**
     * Constructor for creating response without pagination (legacy)
     */
    public static ProjectInnovationSearchResponse of(
            List<ProjectInnovationInfoResponse> innovations,
            SearchFilters appliedFilters) {
        return new ProjectInnovationSearchResponse(
                innovations,
                innovations != null ? innovations.size() : 0,
                appliedFilters,
                null
        );
    }
    
    /**
     * Simple constructor without filters metadata (legacy)
     */
    public static ProjectInnovationSearchResponse of(List<ProjectInnovationInfoResponse> innovations) {
        return new ProjectInnovationSearchResponse(
                innovations,
                innovations != null ? innovations.size() : 0,
                null,
                null
        );
    }
}
