package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Statistics response containing count of innovations and unique countries")
public record InnovationCountryStatsResponse(
        @Schema(description = "Number of innovations found", example = "25")
        Integer innovationCount,
        
        @Schema(description = "Number of unique countries associated with the innovations", example = "12")
        Integer countryCount,
        
        @Schema(description = "Innovation ID filter applied", example = "1566")
        Long innovationId,
        
        @Schema(description = "Phase ID filter applied", example = "428")
        Long phaseId
) {
    
    /**
     * Factory method to create stats response
     */
    public static InnovationCountryStatsResponse of(Integer innovationCount, Integer countryCount, Long innovationId, Long phaseId) {
        return new InnovationCountryStatsResponse(innovationCount, countryCount, innovationId, phaseId);
    }
}