package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Reference associated with a project innovation")
public record ProjectInnovationReferenceResponse(
    @Schema(description = "Reference ID", example = "1234")
    Long id,
    
    @Schema(description = "Reference description or citation")
    String reference,
    
    @Schema(description = "Phase ID linked to the reference", example = "428")
    Long phaseId,
    
    @Schema(description = "External link associated with the reference")
    String link,
    
    @Schema(description = "Indicates if the author is external", example = "false")
    Boolean isExternalAuthor,
    
    @Schema(description = "Indicates if there is evidence tied to a deliverable", example = "true")
    Boolean hasEvidenceByDeliverable,
    
    @Schema(description = "Deliverable ID associated with the reference", example = "987")
    Long deliverableId,
    
    @Schema(description = "Deliverable name associated with the reference", example = "Training manual on climate-smart agriculture")
    String deliverableName,

    @Schema(description = "Dissemination URL linked to the deliverable, if available", example = "https://example.org/deliverable")
    String disseminationUrl,
    
    @Schema(description = "Type ID associated with the reference", example = "3")
    Long typeId,
    
    @Schema(description = "Date when the reference became active")
    LocalDateTime activeSince,
    
    @Schema(description = "User who created the reference", example = "1001")
    Long createdBy,
    
    @Schema(description = "User who last modified the reference", example = "1002")
    Long modifiedBy,
    
    @Schema(description = "Justification for the last modification")
    String modificationJustification
) {
}
