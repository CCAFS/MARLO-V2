package com.example.demo.modules.innovationcomments.adapters.web.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModerationErrorResponseTest {

    @Test
    void constructor_ShouldCreateResponse() {
        // Act
        ModerationErrorResponse response = new ModerationErrorResponse(
            "Comment rejected", "BANNED_WORDS"
        );

        // Assert
        assertEquals("Comment rejected", response.getMessage());
        assertEquals("BANNED_WORDS", response.getReason());
    }

    @Test
    void constructor_WithNullValues_ShouldAccept() {
        // Act
        ModerationErrorResponse response = new ModerationErrorResponse(null, null);

        // Assert
        assertNull(response.getMessage());
        assertNull(response.getReason());
    }
}
