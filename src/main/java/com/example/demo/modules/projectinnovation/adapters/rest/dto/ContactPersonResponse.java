package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import java.time.LocalDateTime;

public record ContactPersonResponse(
        Long id,
        Long partnershipId,
        Long userId,
        String userName,
        String userEmail,
        Boolean isActive,
        LocalDateTime activeSince
) {
}