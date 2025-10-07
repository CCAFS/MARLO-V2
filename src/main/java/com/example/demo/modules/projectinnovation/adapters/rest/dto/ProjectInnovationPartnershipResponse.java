package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ProjectInnovationPartnershipResponse(
        Long id,
        Long projectInnovationId,
        Long institutionId,
        String institutionName,
        String institutionAcronym,
        String institutionWebsite,
        Long idPhase,
        Long innovationPartnerTypeId,
        String partnerTypeName,
        Boolean isActive,
        LocalDateTime activeSince,
        List<ContactPersonResponse> contactPersons
) {
}