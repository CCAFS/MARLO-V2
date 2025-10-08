package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import com.example.demo.modules.innovationtype.adapters.rest.dto.InnovationTypeResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Simplified innovation information response")
public record ProjectInnovationSimpleResponse(
    @Schema(description = "Innovation ID", example = "18887")
    Long id,
    
    @Schema(description = "Project innovation ID", example = "1596") 
    Long projectInnovationId,
    
    @Schema(description = "Phase ID", example = "428")
    Long idPhase,
    
    @Schema(description = "Year", example = "2025")
    Long year,
    
    @Schema(description = "Innovation title", example = "Integrated Soil Fertility Management")
    String title,
    
    @Schema(description = "Innovation narrative/description", example = "ISFM is a set of practices...")
    String narrative,
    
    @Schema(description = "Innovation type ID", example = "4")
    Long innovationTypeId,
    
    @Schema(description = "Innovation nature ID", example = "2")
    Long innovationNatureId,
    
    @Schema(description = "Readiness scale (1-10)", example = "10")
    Integer readinessScale,
    
    @Schema(description = "Phase information")
    PhaseDto phase,
    
    @Schema(description = "Innovation type information")
    InnovationTypeResponse.Simple innovationType,
    
    @Schema(description = "Project ID", example = "102076")
    Long projectId,
    
    @Schema(description = "Is active", example = "true")
    Boolean isActive,
    
    @Schema(description = "Associated SDGs")
    List<SdgDto> sdgs,
    
    @Schema(description = "Associated regions")
    List<RegionDto> regions,
    
    @Schema(description = "Associated countries")
    List<CountryDto> countries
) {
    
    @Schema(description = "Simple phase information")
    public record PhaseDto(
        @Schema(description = "Phase ID", example = "428")
        Long id,
        
        @Schema(description = "Phase name or description", example = "2025")
        String name
    ) {}
}