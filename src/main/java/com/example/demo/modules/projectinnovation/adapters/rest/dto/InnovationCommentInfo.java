package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record InnovationCommentInfo(
    Long id,
    @JsonProperty("innovation_id")
    Long innovationId,
    @JsonProperty("user_name")
    String userName,
    @JsonProperty("user_lastname")
    String userLastname,
    @JsonProperty("user_email")
    String userEmail,
    String comment,
    String title
) {
}
