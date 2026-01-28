package com.example.demo.modules.innovationcomments.application.service.dto;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class OpenAiModerationResponseTest {

    @Test
    void constructor_WithResults_ShouldCreateResponse() {
        // Arrange
        Map<String, Boolean> categories = Map.of("hate", false, "violence", true);
        Map<String, Double> categoryScores = Map.of("hate", 0.1, "violence", 0.9);
        OpenAiModerationResponse.Result result = new OpenAiModerationResponse.Result(true, categories, categoryScores);
        List<OpenAiModerationResponse.Result> results = List.of(result);

        // Act
        OpenAiModerationResponse response = new OpenAiModerationResponse(results);

        // Assert
        assertNotNull(response.results());
        assertEquals(1, response.results().size());
        assertTrue(response.results().get(0).flagged());
    }

    @Test
    void constructor_WithEmptyResults_ShouldCreateResponse() {
        // Act
        OpenAiModerationResponse response = new OpenAiModerationResponse(Collections.emptyList());

        // Assert
        assertNotNull(response.results());
        assertTrue(response.results().isEmpty());
    }

    @Test
    void constructor_WithNullResults_ShouldAcceptNull() {
        // Act
        OpenAiModerationResponse response = new OpenAiModerationResponse(null);

        // Assert
        assertNull(response.results());
    }

    @Test
    void result_WithAllFields_ShouldStoreCorrectly() {
        // Arrange
        boolean flagged = true;
        Map<String, Boolean> categories = new HashMap<>();
        categories.put("hate", true);
        categories.put("violence", false);
        categories.put("self-harm", false);

        Map<String, Double> categoryScores = new HashMap<>();
        categoryScores.put("hate", 0.85);
        categoryScores.put("violence", 0.12);
        categoryScores.put("self-harm", 0.01);

        // Act
        OpenAiModerationResponse.Result result = new OpenAiModerationResponse.Result(flagged, categories, categoryScores);

        // Assert
        assertTrue(result.flagged());
        assertEquals(3, result.categories().size());
        assertTrue(result.categories().get("hate"));
        assertFalse(result.categories().get("violence"));
        assertEquals(0.85, result.categoryScores().get("hate"));
    }

    @Test
    void result_HighestScoreCategory_WithValidScores_ShouldReturnHighest() {
        // Arrange
        Map<String, Boolean> categories = Map.of("hate", false, "violence", true);
        Map<String, Double> categoryScores = new HashMap<>();
        categoryScores.put("hate", 0.3);
        categoryScores.put("violence", 0.9);
        categoryScores.put("harassment", 0.5);

        OpenAiModerationResponse.Result result = new OpenAiModerationResponse.Result(true, categories, categoryScores);

        // Act
        Map.Entry<String, Double> highest = result.highestScoreCategory();

        // Assert
        assertNotNull(highest);
        assertEquals("violence", highest.getKey());
        assertEquals(0.9, highest.getValue());
    }

    @Test
    void result_HighestScoreCategory_WithNullScores_ShouldReturnNull() {
        // Arrange
        OpenAiModerationResponse.Result result = new OpenAiModerationResponse.Result(false, null, null);

        // Act
        Map.Entry<String, Double> highest = result.highestScoreCategory();

        // Assert
        assertNull(highest);
    }

    @Test
    void result_HighestScoreCategory_WithEmptyScores_ShouldReturnNull() {
        // Arrange
        OpenAiModerationResponse.Result result = new OpenAiModerationResponse.Result(false, Map.of(), Map.of());

        // Act
        Map.Entry<String, Double> highest = result.highestScoreCategory();

        // Assert
        assertNull(highest);
    }

    @Test
    void result_HighestScoreCategory_WithSingleScore_ShouldReturnThatScore() {
        // Arrange
        Map<String, Double> categoryScores = Map.of("hate", 0.75);
        OpenAiModerationResponse.Result result = new OpenAiModerationResponse.Result(true, Map.of(), categoryScores);

        // Act
        Map.Entry<String, Double> highest = result.highestScoreCategory();

        // Assert
        assertNotNull(highest);
        assertEquals("hate", highest.getKey());
        assertEquals(0.75, highest.getValue());
    }

    @Test
    void result_HighestScoreCategory_WithEqualScores_ShouldReturnOne() {
        // Arrange
        Map<String, Double> categoryScores = new HashMap<>();
        categoryScores.put("hate", 0.5);
        categoryScores.put("violence", 0.5);

        OpenAiModerationResponse.Result result = new OpenAiModerationResponse.Result(false, Map.of(), categoryScores);

        // Act
        Map.Entry<String, Double> highest = result.highestScoreCategory();

        // Assert
        assertNotNull(highest);
        assertEquals(0.5, highest.getValue());
    }

    @Test
    void result_Equals_WithSameValues_ShouldBeEqual() {
        // Arrange
        Map<String, Boolean> categories = Map.of("hate", true);
        Map<String, Double> scores = Map.of("hate", 0.9);

        OpenAiModerationResponse.Result result1 = new OpenAiModerationResponse.Result(true, categories, scores);
        OpenAiModerationResponse.Result result2 = new OpenAiModerationResponse.Result(true, categories, scores);

        // Assert
        assertEquals(result1, result2);
        assertEquals(result1.hashCode(), result2.hashCode());
    }

    @Test
    void result_ToString_ShouldContainFields() {
        // Arrange
        OpenAiModerationResponse.Result result = new OpenAiModerationResponse.Result(
                true, Map.of("hate", true), Map.of("hate", 0.9));

        // Act
        String resultStr = result.toString();

        // Assert
        assertTrue(resultStr.contains("true") || resultStr.contains("flagged"));
    }

    @Test
    void response_WithMultipleResults_ShouldStoreAll() {
        // Arrange
        OpenAiModerationResponse.Result result1 = new OpenAiModerationResponse.Result(true, Map.of(), Map.of());
        OpenAiModerationResponse.Result result2 = new OpenAiModerationResponse.Result(false, Map.of(), Map.of());
        List<OpenAiModerationResponse.Result> results = Arrays.asList(result1, result2);

        // Act
        OpenAiModerationResponse response = new OpenAiModerationResponse(results);

        // Assert
        assertEquals(2, response.results().size());
        assertTrue(response.results().get(0).flagged());
        assertFalse(response.results().get(1).flagged());
    }

    @Test
    void response_Equals_WithSameResults_ShouldBeEqual() {
        // Arrange
        List<OpenAiModerationResponse.Result> results = List.of(
                new OpenAiModerationResponse.Result(false, Map.of(), Map.of()));

        OpenAiModerationResponse response1 = new OpenAiModerationResponse(results);
        OpenAiModerationResponse response2 = new OpenAiModerationResponse(results);

        // Assert
        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void response_ToString_ShouldContainResults() {
        // Arrange
        OpenAiModerationResponse response = new OpenAiModerationResponse(
                List.of(new OpenAiModerationResponse.Result(true, Map.of(), Map.of())));

        // Act
        String resultStr = response.toString();

        // Assert
        assertNotNull(resultStr);
        assertFalse(resultStr.isEmpty());
    }
}
