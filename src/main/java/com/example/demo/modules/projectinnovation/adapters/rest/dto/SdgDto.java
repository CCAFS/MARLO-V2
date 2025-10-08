package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Simple SDG information")
public record SdgDto(
    @Schema(description = "SDG ID", example = "2")
    Long id,
    
    @Schema(description = "SDG short name", example = "Zero Hunger")
    String shortName,
    
    @Schema(description = "SDG full name", example = "End hunger, achieve food security and improved nutrition and promote sustainable agriculture")
    String fullName
) {}