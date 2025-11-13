package com.example.demo.modules.innovationsubscription.adapters.web.dto;

import com.example.demo.modules.innovationsubscription.domain.model.InnovationCatalogSubscription;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public class SubscriptionResponse {

    @Schema(description = "Subscription identifier", example = "12")
    private Long id;

    @Schema(description = "Subscribed email", example = "user@example.org")
    private String email;

    @Schema(description = "Whether subscription is active", example = "true")
    private Boolean isActive;

    @Schema(description = "Timestamp when subscription became active", example = "2024-04-01T09:00:00")
    private LocalDateTime activeSince;

    public SubscriptionResponse() {}

    public SubscriptionResponse(Long id, String email, Boolean isActive, LocalDateTime activeSince) {
        this.id = id;
        this.email = email;
        this.isActive = isActive;
        this.activeSince = activeSince;
    }

    public static SubscriptionResponse fromEntity(InnovationCatalogSubscription subscription) {
        return new SubscriptionResponse(
                subscription.getId(),
                subscription.getEmail(),
                subscription.getIsActive(),
                subscription.getActiveSince()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public LocalDateTime getActiveSince() {
        return activeSince;
    }

    public void setActiveSince(LocalDateTime activeSince) {
        this.activeSince = activeSince;
    }
}
