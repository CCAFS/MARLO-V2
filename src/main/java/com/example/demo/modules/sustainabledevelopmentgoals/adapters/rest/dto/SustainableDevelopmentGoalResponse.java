package com.example.demo.modules.sustainabledevelopmentgoals.adapters.rest.dto;

public record SustainableDevelopmentGoalResponse(
        Long id,
        Long smoCode,
        String shortName,
        String fullName,
        String icon,
        String description
) {
}