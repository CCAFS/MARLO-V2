package com.example.demo.modules.innovationcomments.adapters.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModerationErrorResponse {

    private final String message;

    @JsonProperty("reason")
    private final String reasonCode;

    public ModerationErrorResponse(String message, String reasonCode) {
        this.message = message;
        this.reasonCode = reasonCode;
    }

    public String getMessage() {
        return message;
    }

    public String getReason() {
        return reasonCode;
    }
}
