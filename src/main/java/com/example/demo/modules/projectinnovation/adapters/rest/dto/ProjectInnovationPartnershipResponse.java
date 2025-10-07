package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "External partner organization associated with a project innovation")
public record ProjectInnovationPartnershipResponse(
        @Schema(description = "Unique identifier for this partnership", example = "3")
        Long id,
        
        @Schema(description = "ID of the project innovation", example = "1566")
        Long projectInnovationId,
        
        @Schema(description = "Institution/Organization identifier", example = "66")
        Long institutionId,
        
        @Schema(description = "Full name of the partner organization", example = "International Livestock Research Institute")
        String institutionName,
        
        @Schema(description = "Acronym of the partner organization", example = "CGIAR ILRI")
        String institutionAcronym,
        
        @Schema(description = "Official website of the partner organization", example = "https://www.ilri.org/")
        String institutionWebsite,
        
        @Schema(description = "Phase identifier when this partnership was established", example = "428")
        Long idPhase,
        
        @Schema(description = "Type identifier for the partnership role", example = "1")
        Long innovationPartnerTypeId,
        
        @Schema(description = "Description of the partnership role", example = "Leading Partner")
        String partnerTypeName,
        
        @Schema(description = "Whether this partnership is currently active", example = "true")
        Boolean isActive,
        
        @Schema(description = "Date and time when this partnership became active", example = "2025-01-06T13:15:40")
        LocalDateTime activeSince,
        
        @Schema(description = "List of contact persons for this partnership")
        List<ContactPersonResponse> contactPersons
) {
}