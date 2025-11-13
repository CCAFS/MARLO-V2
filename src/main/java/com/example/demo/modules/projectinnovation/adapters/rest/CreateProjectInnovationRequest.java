package com.example.demo.modules.projectinnovation.adapters.rest;

import jakarta.validation.constraints.NotNull;

public record CreateProjectInnovationRequest(
    @NotNull Long projectId,
    @NotNull Long createdBy,
    String modificationJustification
) {}