package com.example.demo.modules.projectinnovation.adapters.rest.dto;

public record ProjectInnovationOrganizationResponse(
        Long id,
        Long projectInnovationId,
        Long idPhase,
        Long repIndOrganizationTypeId,
        String organizationTypeName
) {
}