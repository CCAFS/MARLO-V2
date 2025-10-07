package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Country associated with a project innovation in a specific phase")
public record ProjectInnovationCountryResponse(
        @Schema(description = "Unique identifier for this country-innovation relationship", example = "15787")
        Integer id,
        
        @Schema(description = "ID of the project innovation", example = "1566")
        Long projectInnovationId,
        
        @Schema(description = "Country identifier", example = "113")
        Long idCountry,
        
        @Schema(description = "Name of the country", example = "Kenya")
        String countryName,
        
        @Schema(description = "Phase identifier when this relationship was established", example = "428")
        Long idPhase
) {
}