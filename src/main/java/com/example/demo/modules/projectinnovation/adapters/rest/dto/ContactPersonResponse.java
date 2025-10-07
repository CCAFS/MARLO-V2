package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Contact person information for external partnerships")
public record ContactPersonResponse(
        @Schema(description = "Unique identifier for this contact person", example = "2")
        Long id,
        
        @Schema(description = "ID of the partnership this contact belongs to", example = "3")
        Long partnershipId,
        
        @Schema(description = "User ID of the contact person", example = "3704")
        Long userId,
        
        @Schema(description = "Full name of the contact person", example = "John Doe")
        String userName,
        
        @Schema(description = "Email address of the contact person", example = "john.doe@example.com")
        String userEmail,
        
        @Schema(description = "Whether this contact is currently active", example = "true")
        Boolean isActive,
        
        @Schema(description = "Date and time when this contact became active", example = "2025-01-06T13:15:40")
        LocalDateTime activeSince
) {
}