package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Sustainable Development Goal (SDG) linked to a project innovation")
public record ProjectInnovationSdgResponse(
        @Schema(description = "Unique identifier for this SDG-innovation relationship", example = "1012")
        Long id,
        
        @Schema(description = "ID of the project innovation", example = "1566")
        Long innovationId,
        
        @Schema(description = "SDG identifier (1-17)", example = "13")
        Long sdgId,
        
        @Schema(description = "Short name of the SDG", example = "SDG 13")
        String sdgShortName,
        
        @Schema(description = "Full descriptive name of the SDG", example = "Climate Action")
        String sdgFullName,
        
        @Schema(description = "Phase identifier when this SDG was linked", example = "428")
        Long idPhase,
        
        @Schema(description = "Whether this SDG relationship is currently active", example = "true")
        Boolean isActive
) {
}