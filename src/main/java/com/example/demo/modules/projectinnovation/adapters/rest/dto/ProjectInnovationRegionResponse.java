package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Geographic region associated with a project innovation in a specific phase")
public record ProjectInnovationRegionResponse(
        @Schema(description = "Unique identifier for this region-innovation relationship", example = "755")
        Integer id,
        
        @Schema(description = "ID of the project innovation", example = "193")
        Long projectInnovationId,
        
        @Schema(description = "Region identifier", example = "1")
        Long idRegion,
        
        @Schema(description = "Name of the geographic region", example = "Northern Africa")
        String regionName,
        
        @Schema(description = "Phase identifier when this relationship was established", example = "256")
        Long idPhase
) {
}