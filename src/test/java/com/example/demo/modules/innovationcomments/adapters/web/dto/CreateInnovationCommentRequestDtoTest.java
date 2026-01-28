package com.example.demo.modules.innovationcomments.adapters.web.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CreateInnovationCommentRequestDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void constructor_WithNoArgs_ShouldCreateEmptyObject() {
        // Act
        CreateInnovationCommentRequestDto dto = new CreateInnovationCommentRequestDto();

        // Assert
        assertNotNull(dto);
        assertNull(dto.getInnovationId());
        assertNull(dto.getUserName());
        assertNull(dto.getUserLastname());
        assertNull(dto.getUserEmail());
        assertNull(dto.getComment());
        assertNull(dto.getModificationJustification());
    }

    @Test
    void constructor_WithFiveArgs_ShouldSetFields() {
        // Act
        CreateInnovationCommentRequestDto dto = new CreateInnovationCommentRequestDto(
                100L, "John", "Doe", "john@example.com", "My comment");

        // Assert
        assertEquals(100L, dto.getInnovationId());
        assertEquals("John", dto.getUserName());
        assertEquals("Doe", dto.getUserLastname());
        assertEquals("john@example.com", dto.getUserEmail());
        assertEquals("My comment", dto.getComment());
        assertNull(dto.getModificationJustification());
    }

    @Test
    void constructor_WithSixArgs_ShouldSetAllFields() {
        // Act
        CreateInnovationCommentRequestDto dto = new CreateInnovationCommentRequestDto(
                100L, "John", "Doe", "john@example.com", "My comment", "Initial creation");

        // Assert
        assertEquals(100L, dto.getInnovationId());
        assertEquals("John", dto.getUserName());
        assertEquals("Doe", dto.getUserLastname());
        assertEquals("john@example.com", dto.getUserEmail());
        assertEquals("My comment", dto.getComment());
        assertEquals("Initial creation", dto.getModificationJustification());
    }

    @Test
    void settersAndGetters_InnovationId_ShouldWorkCorrectly() {
        // Arrange
        CreateInnovationCommentRequestDto dto = new CreateInnovationCommentRequestDto();

        // Act
        dto.setInnovationId(100L);

        // Assert
        assertEquals(100L, dto.getInnovationId());
    }

    @Test
    void settersAndGetters_UserName_ShouldWorkCorrectly() {
        // Arrange
        CreateInnovationCommentRequestDto dto = new CreateInnovationCommentRequestDto();

        // Act
        dto.setUserName("Jane");

        // Assert
        assertEquals("Jane", dto.getUserName());
    }

    @Test
    void settersAndGetters_UserEmail_ShouldWorkCorrectly() {
        // Arrange
        CreateInnovationCommentRequestDto dto = new CreateInnovationCommentRequestDto();

        // Act
        dto.setUserEmail("jane@example.com");

        // Assert
        assertEquals("jane@example.com", dto.getUserEmail());
    }

    @Test
    void settersAndGetters_Comment_ShouldWorkCorrectly() {
        // Arrange
        CreateInnovationCommentRequestDto dto = new CreateInnovationCommentRequestDto();

        // Act
        dto.setComment("This is my comment");

        // Assert
        assertEquals("This is my comment", dto.getComment());
    }

    @Test
    void settersAndGetters_ModificationJustification_ShouldWorkCorrectly() {
        // Arrange
        CreateInnovationCommentRequestDto dto = new CreateInnovationCommentRequestDto();

        // Act
        dto.setModificationJustification("Updated");

        // Assert
        assertEquals("Updated", dto.getModificationJustification());
    }

    @Test
    void validation_WithValidData_ShouldPass() {
        // Arrange
        CreateInnovationCommentRequestDto dto = new CreateInnovationCommentRequestDto(
                100L, "John", "Doe", "john@example.com", "Comment");

        // Act
        Set<ConstraintViolation<CreateInnovationCommentRequestDto>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void validation_WithNullInnovationId_ShouldFail() {
        // Arrange
        CreateInnovationCommentRequestDto dto = new CreateInnovationCommentRequestDto(
                null, "John", "Doe", "john@example.com", "Comment");

        // Act
        Set<ConstraintViolation<CreateInnovationCommentRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("innovationId")));
    }

    @Test
    void validation_WithBlankUserName_ShouldFail() {
        // Arrange
        CreateInnovationCommentRequestDto dto = new CreateInnovationCommentRequestDto(
                100L, "", "Doe", "john@example.com", "Comment");

        // Act
        Set<ConstraintViolation<CreateInnovationCommentRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("userName")));
    }

    @Test
    void validation_WithNullUserName_ShouldFail() {
        // Arrange
        CreateInnovationCommentRequestDto dto = new CreateInnovationCommentRequestDto(
                100L, null, "Doe", "john@example.com", "Comment");

        // Act
        Set<ConstraintViolation<CreateInnovationCommentRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void validation_WithBlankUserEmail_ShouldFail() {
        // Arrange
        CreateInnovationCommentRequestDto dto = new CreateInnovationCommentRequestDto(
                100L, "John", "Doe", "", "Comment");

        // Act
        Set<ConstraintViolation<CreateInnovationCommentRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("userEmail")));
    }

    @Test
    void validation_WithInvalidEmail_ShouldFail() {
        // Arrange
        CreateInnovationCommentRequestDto dto = new CreateInnovationCommentRequestDto(
                100L, "John", "Doe", "not-an-email", "Comment");

        // Act
        Set<ConstraintViolation<CreateInnovationCommentRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Invalid email") || v.getMessage().contains("valid")));
    }

    @Test
    void validation_WithNullUserEmail_ShouldFail() {
        // Arrange
        CreateInnovationCommentRequestDto dto = new CreateInnovationCommentRequestDto(
                100L, "John", "Doe", null, "Comment");

        // Act
        Set<ConstraintViolation<CreateInnovationCommentRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void setNullValues_ShouldAcceptNulls() {
        // Arrange
        CreateInnovationCommentRequestDto dto = new CreateInnovationCommentRequestDto(
                100L, "John", "Doe", "john@example.com", "Comment", "Justification");

        // Act
        dto.setInnovationId(null);
        dto.setUserName(null);
        dto.setUserLastname(null);
        dto.setUserEmail(null);
        dto.setComment(null);
        dto.setModificationJustification(null);

        // Assert
        assertNull(dto.getInnovationId());
        assertNull(dto.getUserName());
        assertNull(dto.getUserLastname());
        assertNull(dto.getUserEmail());
        assertNull(dto.getComment());
        assertNull(dto.getModificationJustification());
    }

    @Test
    void validation_WithNullOptionalFields_ShouldPass() {
        // Arrange - userLastname, comment, modificationJustification are optional
        CreateInnovationCommentRequestDto dto = new CreateInnovationCommentRequestDto();
        dto.setInnovationId(100L);
        dto.setUserName("John");
        dto.setUserEmail("john@example.com");

        // Act
        Set<ConstraintViolation<CreateInnovationCommentRequestDto>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void settersAndGetters_UserLastname_ShouldWorkCorrectly() {
        // Arrange
        CreateInnovationCommentRequestDto dto = new CreateInnovationCommentRequestDto();

        // Act
        dto.setUserLastname("Smith");

        // Assert
        assertEquals("Smith", dto.getUserLastname());
    }
}
