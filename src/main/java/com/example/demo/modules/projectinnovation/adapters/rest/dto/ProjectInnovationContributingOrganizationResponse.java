package com.example.demo.modules.projectinnovation.adapters.rest.dto;

/**
 * Response DTO for contributing organizations
 * Represents an institution that contributes to a project innovation
 */
public record ProjectInnovationContributingOrganizationResponse(
        Long id,
        Long projectInnovationId,
        Long idPhase,
        Long institutionId,
        String institutionName,
        String institutionAcronym
) {}