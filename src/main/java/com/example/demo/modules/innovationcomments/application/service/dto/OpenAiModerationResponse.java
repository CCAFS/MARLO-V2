package com.example.demo.modules.innovationcomments.application.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public record OpenAiModerationResponse(List<Result> results) {

    public record Result(
            boolean flagged,
            Map<String, Boolean> categories,
            @JsonProperty("category_scores") Map<String, Double> categoryScores) {

        public Map.Entry<String, Double> highestScoreCategory() {
            if (categoryScores == null || categoryScores.isEmpty()) {
                return null;
            }
            return categoryScores.entrySet().stream()
                    .max(Comparator.comparingDouble(Map.Entry::getValue))
                    .orElse(null);
        }
    }
}
