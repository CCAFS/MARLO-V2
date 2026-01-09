package com.example.demo.sharedkernel.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonalizedMessageGeneratorTest {

    private PersonalizedMessageGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new PersonalizedMessageGenerator();
    }

    @Test
    void generate_WithValidTemplateAndName_ShouldFormatCorrectly() {
        // Arrange
        String template = "Hello, %s! Welcome to MARLO.";
        String name = "John Doe";

        // Act
        String result = generator.generate(template, name);

        // Assert
        assertEquals("Hello, John Doe! Welcome to MARLO.", result);
    }

    @Test
    void generate_WithMultiplePlaceholders_ShouldFormatCorrectly() {
        // Arrange
        String template = "Hello, %s!";
        String name = "Jane";

        // Act
        String result = generator.generate(template, name);

        // Assert
        assertEquals("Hello, Jane!", result);
    }

    @Test
    void generate_WithEmptyName_ShouldFormatCorrectly() {
        // Arrange
        String template = "Hello, %s!";
        String name = "";

        // Act
        String result = generator.generate(template, name);

        // Assert
        assertEquals("Hello, !", result);
    }

    @Test
    void generate_WithNullTemplate_ShouldThrowNullPointerException() {
        // Arrange
        String template = null;
        String name = "Test";

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            generator.generate(template, name);
        });
    }

    @Test
    void generate_WithNullName_ShouldThrowNullPointerException() {
        // Arrange
        String template = "Hello, %s!";
        String name = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            generator.generate(template, name);
        });
    }

    @Test
    void generate_WithTemplateContainingNewlines_ShouldSanitizeForLog() {
        // Arrange
        String template = "Hello, %s!\nWelcome";
        String name = "Test";

        // Act
        String result = generator.generate(template, name);

        // Assert
        assertEquals("Hello, Test!\nWelcome", result);
    }

    @Test
    void generate_WithNameContainingNewlines_ShouldFormatCorrectly() {
        // Arrange
        String template = "Hello, %s!";
        String name = "Test\nUser";

        // Act
        String result = generator.generate(template, name);

        // Assert
        assertEquals("Hello, Test\nUser!", result);
    }

    @Test
    void generate_WithSpecialCharacters_ShouldFormatCorrectly() {
        // Arrange
        String template = "Hello, %s!";
        String name = "Test@User#123";

        // Act
        String result = generator.generate(template, name);

        // Assert
        assertEquals("Hello, Test@User#123!", result);
    }

    @Test
    void generate_WithUnicodeCharacters_ShouldFormatCorrectly() {
        // Arrange
        String template = "Hello, %s!";
        String name = "José María";

        // Act
        String result = generator.generate(template, name);

        // Assert
        assertEquals("Hello, José María!", result);
    }
}
