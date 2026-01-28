package com.example.demo.modules.innovationsubscription.adapters.web.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CreateSubscriptionRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void constructor_WithNoArgs_ShouldCreateEmptyObject() {
        // Act
        CreateSubscriptionRequest request = new CreateSubscriptionRequest();

        // Assert
        assertNotNull(request);
        assertNull(request.getEmail());
    }

    @Test
    void constructor_WithEmail_ShouldSetEmail() {
        // Act
        CreateSubscriptionRequest request = new CreateSubscriptionRequest("test@example.com");

        // Assert
        assertEquals("test@example.com", request.getEmail());
    }

    @Test
    void settersAndGetters_Email_ShouldWorkCorrectly() {
        // Arrange
        CreateSubscriptionRequest request = new CreateSubscriptionRequest();

        // Act
        request.setEmail("user@test.com");

        // Assert
        assertEquals("user@test.com", request.getEmail());
    }

    @Test
    void validation_WithValidEmail_ShouldPass() {
        // Arrange
        CreateSubscriptionRequest request = new CreateSubscriptionRequest("valid@email.com");

        // Act
        Set<ConstraintViolation<CreateSubscriptionRequest>> violations = validator.validate(request);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void validation_WithNullEmail_ShouldFail() {
        // Arrange
        CreateSubscriptionRequest request = new CreateSubscriptionRequest();

        // Act
        Set<ConstraintViolation<CreateSubscriptionRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("required") || v.getMessage().contains("blank")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "user@", "userexample.com"})
    void validation_WithInvalidEmailInputs_ShouldFail(String email) {
        // Arrange
        CreateSubscriptionRequest request = new CreateSubscriptionRequest(email);

        // Act
        Set<ConstraintViolation<CreateSubscriptionRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void validation_WithInvalidEmail_ShouldFail() {
        // Arrange
        CreateSubscriptionRequest request = new CreateSubscriptionRequest("not-an-email");

        // Act
        Set<ConstraintViolation<CreateSubscriptionRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("valid")));
    }

    @Test
    void setNullEmail_ShouldAcceptNull() {
        // Arrange
        CreateSubscriptionRequest request = new CreateSubscriptionRequest("test@test.com");

        // Act
        request.setEmail(null);

        // Assert
        assertNull(request.getEmail());
    }

    @Test
    void validation_WithComplexValidEmail_ShouldPass() {
        // Arrange
        CreateSubscriptionRequest request = new CreateSubscriptionRequest("user.name+tag@subdomain.example.com");

        // Act
        Set<ConstraintViolation<CreateSubscriptionRequest>> violations = validator.validate(request);

        // Assert
        assertTrue(violations.isEmpty());
    }
}
