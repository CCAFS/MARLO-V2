package com.example.demo.modules.projectinnovation.adapters.rest.dto;

public record ProjectInnovationRegionResponse(
        Integer id,
        Long projectInnovationId,
        Long idRegion,
        String regionName,
        Long idPhase
) {
}