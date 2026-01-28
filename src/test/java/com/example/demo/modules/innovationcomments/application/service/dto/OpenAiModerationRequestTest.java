package com.example.demo.modules.innovationcomments.application.service.dto;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OpenAiModerationRequestTest {

    @Test
    void constructor_WithModelAndInput_ShouldCreateRequest() {
        // Arrange
        String model = "text-moderation-latest";
        List<String> input = Arrays.asList("text1", "text2");

        // Act
        OpenAiModerationRequest request = new OpenAiModerationRequest(model, input);

        // Assert
        assertEquals(model, request.model());
        assertEquals(input, request.input());
        assertEquals(2, request.input().size());
    }

    @Test
    void constructor_WithEmptyInput_ShouldCreateRequest() {
        // Arrange
        String model = "text-moderation-stable";
        List<String> input = Collections.emptyList();

        // Act
        OpenAiModerationRequest request = new OpenAiModerationRequest(model, input);

        // Assert
        assertEquals(model, request.model());
        assertTrue(request.input().isEmpty());
    }

    @Test
    void constructor_WithNullValues_ShouldAcceptNulls() {
        // Act
        OpenAiModerationRequest request = new OpenAiModerationRequest(null, null);

        // Assert
        assertNull(request.model());
        assertNull(request.input());
    }

    @Test
    void ofSingleInput_ShouldCreateRequestWithSingleItemList() {
        // Arrange
        String model = "text-moderation-latest";
        String text = "This is a test message";

        // Act
        OpenAiModerationRequest request = OpenAiModerationRequest.ofSingleInput(model, text);

        // Assert
        assertEquals(model, request.model());
        assertNotNull(request.input());
        assertEquals(1, request.input().size());
        assertEquals(text, request.input().get(0));
    }

    @Test
    void ofSingleInput_WithEmptyText_ShouldCreateRequest() {
        // Arrange
        String model = "text-moderation-stable";
        String text = "";

        // Act
        OpenAiModerationRequest request = OpenAiModerationRequest.ofSingleInput(model, text);

        // Assert
        assertEquals(model, request.model());
        assertEquals(1, request.input().size());
        assertEquals("", request.input().get(0));
    }

    @Test
    void ofSingleInput_WithNullText_ShouldCreateRequestWithNullInList() {
        // Arrange
        String model = "text-moderation-latest";

        // Act
        OpenAiModerationRequest request = OpenAiModerationRequest.ofSingleInput(model, null);

        // Assert
        assertEquals(model, request.model());
        assertEquals(1, request.input().size());
        assertNull(request.input().get(0));
    }

    @Test
    void equals_WithSameValues_ShouldBeEqual() {
        // Arrange
        String model = "text-moderation-latest";
        List<String> input = Arrays.asList("text1", "text2");

        OpenAiModerationRequest request1 = new OpenAiModerationRequest(model, input);
        OpenAiModerationRequest request2 = new OpenAiModerationRequest(model, input);

        // Assert
        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void equals_WithDifferentValues_ShouldNotBeEqual() {
        // Arrange
        OpenAiModerationRequest request1 = new OpenAiModerationRequest("model1", List.of("text1"));
        OpenAiModerationRequest request2 = new OpenAiModerationRequest("model2", List.of("text2"));

        // Assert
        assertNotEquals(request1, request2);
    }

    @Test
    void toString_ShouldContainAllFields() {
        // Arrange
        OpenAiModerationRequest request = new OpenAiModerationRequest("test-model", List.of("test-text"));

        // Act
        String result = request.toString();

        // Assert
        assertTrue(result.contains("test-model"));
        assertTrue(result.contains("test-text"));
    }
}
