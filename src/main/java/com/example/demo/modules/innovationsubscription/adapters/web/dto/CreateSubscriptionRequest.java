package com.example.demo.modules.innovationsubscription.adapters.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CreateSubscriptionRequest {

    @Schema(description = "Email address to subscribe", example = "user@example.org", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    public CreateSubscriptionRequest() {}

    public CreateSubscriptionRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
