package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Aggregated innovation counts for filter availability and maps")
public record InnovationFacetsResponse(
    @Schema(description = "Total innovations matching the filters")
    Integer totalCount,

    @Schema(description = "Innovation counts by country")
    List<FacetCount> countries,

    @Schema(description = "Innovation counts by SDG")
    List<FacetCount> sdgs,

    @Schema(description = "Innovation counts by innovation type")
    List<FacetCount> innovationTypes,

    @Schema(description = "Innovation counts by actor")
    List<FacetCount> actors,

    @Schema(description = "Innovation counts by readiness scale")
    List<FacetCount> readinessScales
) {
    public record FacetCount(
        Long id,
        Integer count,
        String name
    ) {}
}
