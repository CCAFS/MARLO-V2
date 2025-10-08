package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Simple country information")
public record CountryDto(
    @Schema(description = "Country ID", example = "113")
    Long id,
    
    @Schema(description = "Country name", example = "Kenya")
    String name
) {}