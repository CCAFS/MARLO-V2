package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * Response DTO for complementary solutions linked to an innovation.
 */
public record ProjectInnovationComplementarySolutionResponse(
        @Schema(description = "Complementary solution ID", example = "321")
        Long id,

        @Schema(description = "Title of the complementary solution")
        String title,

        @Schema(description = "Short title or label for the complementary solution")
        String shortTitle,

        @Schema(description = "Brief description of the complementary solution")
        String shortDescription,

        @Schema(description = "Innovation type ID associated with the complementary solution", example = "5")
        Long projectInnovationTypeId,

        @Schema(description = "Detailed information about the complementary innovation type")
        InnovationTypeDto innovationType,

        @Schema(description = "Phase ID associated with the complementary solution", example = "428")
        Long phaseId,

        @Schema(description = "Indicates whether the solution is active")
        Boolean isActive,

        @Schema(description = "Activation timestamp")
        LocalDateTime activeSince,

        @Schema(description = "User who created the record", example = "1001")
        Long createdBy,

        @Schema(description = "User who last modified the record", example = "1002")
        Long modifiedBy,

        @Schema(description = "Justification for the latest modification")
        String modificationJustification
) {
}
