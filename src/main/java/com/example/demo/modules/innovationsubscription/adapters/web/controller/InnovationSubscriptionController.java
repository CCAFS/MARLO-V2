package com.example.demo.modules.innovationsubscription.adapters.web.controller;

import com.example.demo.modules.innovationsubscription.adapters.web.dto.CreateSubscriptionRequest;
import com.example.demo.modules.innovationsubscription.adapters.web.dto.SubscriptionResponse;
import com.example.demo.modules.innovationsubscription.domain.model.InnovationCatalogSubscription;
import com.example.demo.modules.innovationsubscription.domain.port.in.InnovationSubscriptionUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/api/innovations/subscriptions")
@Tag(name = "Innovation Subscriptions", description = "Manage innovation catalog email subscriptions")
@Validated
public class InnovationSubscriptionController {

    private final InnovationSubscriptionUseCase subscriptionUseCase;

    public InnovationSubscriptionController(InnovationSubscriptionUseCase subscriptionUseCase) {
        this.subscriptionUseCase = subscriptionUseCase;
    }

    @Operation(summary = "Subscribe email to innovation catalog updates")
    @PostMapping
    public ResponseEntity<SubscriptionResponse> subscribe(@Valid @RequestBody CreateSubscriptionRequest request) {
        InnovationCatalogSubscription subscription = subscriptionUseCase.subscribe(request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SubscriptionResponse.fromEntity(subscription));
    }

    @Operation(summary = "Unsubscribe email from innovation catalog updates (logical delete)")
    @DeleteMapping
    public ResponseEntity<Void> unsubscribe(
            @RequestParam("email")
            @NotBlank(message = "Email is required")
            @Email(message = "Email must be valid")
            String email) {
        boolean deactivated = subscriptionUseCase.unsubscribe(email);
        if (deactivated) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
