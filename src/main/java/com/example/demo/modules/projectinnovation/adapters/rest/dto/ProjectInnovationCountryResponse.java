package com.example.demo.modules.projectinnovation.adapters.rest.dto;

public record ProjectInnovationCountryResponse(
        Integer id,
        Long projectInnovationId,
        Long idCountry,
        String countryName,
        Long idPhase
) {
}