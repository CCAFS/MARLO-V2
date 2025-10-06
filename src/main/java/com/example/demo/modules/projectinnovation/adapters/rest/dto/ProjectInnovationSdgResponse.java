package com.example.demo.modules.projectinnovation.adapters.rest.dto;

public record ProjectInnovationSdgResponse(
        Long id,
        Long innovationId,
        Long sdgId,
        String sdgShortName,
        String sdgFullName,
        Long idPhase,
        Boolean isActive
) {
}