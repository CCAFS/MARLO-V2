package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Schema(description = "Statistics response containing count of innovations, unique countries, and average scaling readiness")
public record InnovationCountryStatsResponse(
        @Schema(description = "Number of innovations found", example = "25")
        Integer innovationCount,
        
        @Schema(description = "Number of unique countries associated with the innovations", example = "12")
        Integer countryCount,
        
        @Schema(description = "Average scaling readiness of all innovations in the phase", example = "2.35")
        Double averageScalingReadiness,
        
        @Schema(description = "Innovation ID filter applied", example = "1566")
        Long innovationId,
        
        @Schema(description = "Phase ID filter applied", example = "428")
        Long phaseId
) {
    
    /**
     * Factory method to create stats response with rounded average scaling readiness
     */
    public static InnovationCountryStatsResponse of(Integer innovationCount, Integer countryCount, Double averageScalingReadiness, Long innovationId, Long phaseId) {
        Double roundedAverage = roundToTwoDecimals(averageScalingReadiness);
        return new InnovationCountryStatsResponse(innovationCount, countryCount, roundedAverage, innovationId, phaseId);
    }
    
    /**
     * Utility method to round a double to 2 decimal places
     */
    private static Double roundToTwoDecimals(Double value) {
        if (value == null) {
            return null;
        }
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}