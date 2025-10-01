package com.example.demo.modules.projectinnovation.adapters.rest;

import com.example.demo.modules.projectinnovation.adapters.rest.dto.ProjectInnovationActorsResponse;
import java.time.LocalDateTime;
import java.util.List;

public record ProjectInnovationResponse(
    Long id,
    Long projectId,
    Boolean isActive,
    LocalDateTime activeSince,
    Long createdBy,
    Long modifiedBy,
    String modificationJustification,
    List<ProjectInnovationActorsResponse> actors
) {}