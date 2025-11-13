package com.example.demo.modules.projectinnovation.adapters.rest;

public record UpdateProjectInnovationRequest(
    Boolean isActive,
    Long modifiedBy,
    String modificationJustification
) {}