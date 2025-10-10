package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Innovation type information from rep_ind_innovation_types table")
public record InnovationTypeDto(
    @Schema(description = "Innovation type ID")
    Long id,
    
    @Schema(description = "Innovation type name")
    String name,
    
    @Schema(description = "Innovation type definition")
    String definition,
    
    @Schema(description = "Whether this is an old type")
    Boolean isOldType,
    
    @Schema(description = "PRMS equivalent ID")
    Long prmsIdEquivalent,
    
    @Schema(description = "PRMS equivalent name")
    String prmsNameEquivalent
) {}