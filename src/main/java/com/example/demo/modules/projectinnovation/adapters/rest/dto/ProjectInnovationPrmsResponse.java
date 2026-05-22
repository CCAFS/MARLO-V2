package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "PRMS innovation linked to a project innovation and phase")
public record ProjectInnovationPrmsResponse(
        @Schema(description = "PRMS innovation ID", example = "80")
        Long id,
        @Schema(description = "PRMS result ID", example = "12521")
        Long prmsResultId,
        @Schema(description = "PRMS innovation title", example = "AgWise: A Modular Framework Delivering Tailored Agronomic Recommendations for Farmers")
        String title,
        @Schema(description = "PRMS innovation PDF or report link", example = "https://reporting.cgiar.org/reports/result-details/12521?phase=6")
        String pdfLink
) {
}
