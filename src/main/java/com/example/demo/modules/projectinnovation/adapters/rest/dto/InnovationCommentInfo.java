package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import java.time.LocalDateTime;

public record InnovationCommentInfo(
    Long id,
    Long innovationId,
    String userName,
    String userEmail,
    String comment,
    String title
) {
}
