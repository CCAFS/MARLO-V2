package com.example.demo.modules.innovationreports.adapters.web.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CreateInnovationReportRequestDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void constructor_WithNoArgs_ShouldCreateEmptyObject() {
        // Act
        CreateInnovationReportRequestDto dto = new CreateInnovationReportRequestDto();

        // Assert
        assertNotNull(dto);
        assertNull(dto.getInnovationId());
        assertNull(dto.getUserName());
        assertNull(dto.getUserLastname());
        assertNull(dto.getUserEmail());
        assertNull(dto.getInterestNarrative());
        assertNull(dto.getModificationJustification());
    }

    @Test
    void constructor_WithFiveArgs_ShouldSetFields() {
        // Act
        CreateInnovationReportRequestDto dto = new CreateInnovationReportRequestDto(
                100L, "John", "Doe", "john@example.com", "Interested");

        // Assert
        assertEquals(100L, dto.getInnovationId());
        assertEquals("John", dto.getUserName());
        assertEquals("Doe", dto.getUserLastname());
        assertEquals("john@example.com", dto.getUserEmail());
        assertEquals("Interested", dto.getInterestNarrative());
        assertNull(dto.getModificationJustification());
    }

    @Test
    void constructor_WithSixArgs_ShouldSetAllFields() {
        // Act
        CreateInnovationReportRequestDto dto = new CreateInnovationReportRequestDto(
                100L, "John", "Doe", "john@example.com", "Interested", "Initial");

        // Assert
        assertEquals(100L, dto.getInnovationId());
        assertEquals("John", dto.getUserName());
        assertEquals("Doe", dto.getUserLastname());
        assertEquals("john@example.com", dto.getUserEmail());
        assertEquals("Interested", dto.getInterestNarrative());
        assertEquals("Initial", dto.getModificationJustification());
    }

    @Test
    void settersAndGetters_InnovationId_ShouldWorkCorrectly() {
        // Arrange
        CreateInnovationReportRequestDto dto = new CreateInnovationReportRequestDto();

        // Act
        dto.setInnovationId(100L);

        // Assert
        assertEquals(100L, dto.getInnovationId());
    }

    @Test
    void settersAndGetters_UserName_ShouldWorkCorrectly() {
        // Arrange
        CreateInnovationReportRequestDto dto = new CreateInnovationReportRequestDto();

        // Act
        dto.setUserName("Jane");

        // Assert
        assertEquals("Jane", dto.getUserName());
    }

    @Test
    void settersAndGetters_UserLastname_ShouldWorkCorrectly() {
        // Arrange
        CreateInnovationReportRequestDto dto = new CreateInnovationReportRequestDto();

        // Act
        dto.setUserLastname("Smith");

        // Assert
        assertEquals("Smith", dto.getUserLastname());
    }

    @Test
    void settersAndGetters_UserEmail_ShouldWorkCorrectly() {
        // Arrange
        CreateInnovationReportRequestDto dto = new CreateInnovationReportRequestDto();

        // Act
        dto.setUserEmail("jane@example.com");

        // Assert
        assertEquals("jane@example.com", dto.getUserEmail());
    }

    @Test
    void settersAndGetters_InterestNarrative_ShouldWorkCorrectly() {
        // Arrange
        CreateInnovationReportRequestDto dto = new CreateInnovationReportRequestDto();

        // Act
        dto.setInterestNarrative("My interest narrative");

        // Assert
        assertEquals("My interest narrative", dto.getInterestNarrative());
    }

    @Test
    void settersAndGetters_ModificationJustification_ShouldWorkCorrectly() {
        // Arrange
        CreateInnovationReportRequestDto dto = new CreateInnovationReportRequestDto();

        // Act
        dto.setModificationJustification("Updated");

        // Assert
        assertEquals("Updated", dto.getModificationJustification());
    }

    @Test
    void validation_WithValidData_ShouldPass() {
        // Arrange
        CreateInnovationReportRequestDto dto = new CreateInnovationReportRequestDto(
                100L, "John", "Doe", "john@example.com", "Interested");

        // Act
        Set<ConstraintViolation<CreateInnovationReportRequestDto>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void validation_WithNullInnovationId_ShouldFail() {
        // Arrange
        CreateInnovationReportRequestDto dto = new CreateInnovationReportRequestDto(
                null, "John", "Doe", "john@example.com", "Interested");

        // Act
        Set<ConstraintViolation<CreateInnovationReportRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("innovationId")));
    }

    @Test
    void validation_WithBlankUserName_ShouldFail() {
        // Arrange
        CreateInnovationReportRequestDto dto = new CreateInnovationReportRequestDto(
                100L, "", "Doe", "john@example.com", "Interested");

        // Act
        Set<ConstraintViolation<CreateInnovationReportRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("userName")));
    }

    @Test
    void validation_WithNullUserName_ShouldFail() {
        // Arrange
        CreateInnovationReportRequestDto dto = new CreateInnovationReportRequestDto(
                100L, null, "Doe", "john@example.com", "Interested");

        // Act
        Set<ConstraintViolation<CreateInnovationReportRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void validation_WithNullOptionalFields_ShouldPass() {
        // Arrange - userLastname, userEmail, interestNarrative, modificationJustification are optional
        CreateInnovationReportRequestDto dto = new CreateInnovationReportRequestDto();
        dto.setInnovationId(100L);
        dto.setUserName("John");

        // Act
        Set<ConstraintViolation<CreateInnovationReportRequestDto>> violations = validator.validate(dto);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void setNullValues_ShouldAcceptNulls() {
        // Arrange
        CreateInnovationReportRequestDto dto = new CreateInnovationReportRequestDto(
                100L, "John", "Doe", "john@example.com", "Interested", "Initial");

        // Act
        dto.setInnovationId(null);
        dto.setUserName(null);
        dto.setUserLastname(null);
        dto.setUserEmail(null);
        dto.setInterestNarrative(null);
        dto.setModificationJustification(null);

        // Assert
        assertNull(dto.getInnovationId());
        assertNull(dto.getUserName());
        assertNull(dto.getUserLastname());
        assertNull(dto.getUserEmail());
        assertNull(dto.getInterestNarrative());
        assertNull(dto.getModificationJustification());
    }

    @Test
    void validation_WithWhitespaceUserName_ShouldFail() {
        // Arrange
        CreateInnovationReportRequestDto dto = new CreateInnovationReportRequestDto(
                100L, "   ", "Doe", "john@example.com", "Interested");

        // Act
        Set<ConstraintViolation<CreateInnovationReportRequestDto>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
    }
}
