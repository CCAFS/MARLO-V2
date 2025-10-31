package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Alliance organization associated with a project innovation and phase")
public record ProjectInnovationAllianceOrganizationResponse(
        @Schema(description = "Unique identifier of the alliance organization record", example = "10")
        Long id,

        @Schema(description = "Related project innovation identifier", example = "1566")
        Long projectInnovationId,

        @Schema(description = "Phase identifier for this alliance organization", example = "428")
        Long idPhase,

        @Schema(description = "Institution type identifier from the alliance catalog", example = "2")
        Long institutionTypeId,

        @Schema(description = "Human readable institution type name", example = "Implementing Partner")
        String institutionTypeName,

        @Schema(description = "Organization name as stored in the alliance table", example = "Alliance for Development")
        String organizationName,

        @Schema(description = "Flag indicating whether this organization is a scaling partner", example = "true")
        Boolean isScalingPartner,

        @Schema(description = "Flag indicating active status", example = "true")
        Boolean isActive,

        @Schema(description = "Timestamp when the record became active", example = "2024-01-01T12:00:00")
        LocalDateTime activeSince,

        @Schema(description = "Identifier of the user that created the record", example = "100")
        Long createdBy,

        @Schema(description = "Identifier of the user that last modified the record", example = "101")
        Long modifiedBy,

        @Schema(description = "Justification for the last modification, if any")
        String modificationJustification,

        @Schema(description = "Linked institution identifier when available", example = "66")
        Long institutionId,

        @Schema(description = "Linked institution name, if the institution exists", example = "International Livestock Research Institute")
        String institutionName,

        @Schema(description = "Linked institution acronym", example = "CGIAR ILRI")
        String institutionAcronym,

        @Schema(description = "Reported number value for this organization", example = "12")
        Integer number
) {
}
