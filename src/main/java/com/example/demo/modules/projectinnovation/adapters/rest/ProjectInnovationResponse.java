package com.example.demo.modules.projectinnovation.adapters.rest;

import java.time.LocalDateTime;

public record ProjectInnovationResponse(
    Long id,
    Long projectId,
    Boolean isActive,
    LocalDateTime activeSince,
    Long createdBy,
    Long modifiedBy,
    String modificationJustification
) {}