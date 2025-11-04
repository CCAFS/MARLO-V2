package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * Response DTO for project innovation bundle associations.
 */
public record ProjectInnovationBundleResponse(
        @Schema(description = "Bundle ID", example = "1234")
        Long id,

        @Schema(description = "Primary innovation ID for the bundle", example = "1566")
        Long projectInnovationId,

        @Schema(description = "Associated bundled innovation ID", example = "2042")
        Long selectedInnovationId,

        @Schema(description = "Name/title of the bundled innovation", example = "Climate-smart irrigation package")
        String selectedInnovationName,

        @Schema(description = "Scaling readiness score of the bundled innovation", example = "6")
        Integer selectedInnovationReadinessScale,

        @Schema(description = "Phase ID associated with the bundle", example = "428")
        Long phaseId,

        @Schema(description = "Indicates whether the bundle is active", example = "true")
        Boolean isActive,

        @Schema(description = "Activation timestamp of the bundle")
        LocalDateTime activeSince,

        @Schema(description = "User who created the bundle", example = "1001")
        Long createdBy,

        @Schema(description = "User who last modified the bundle", example = "1002")
        Long modifiedBy,

        @Schema(description = "Justification for modifications")
        String modificationJustification
) {
}
