package com.example.demo.modules.projectinnovation.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InnovationTypeTest {

    private InnovationType innovationType;

    @BeforeEach
    void setUp() {
        innovationType = new InnovationType();
    }

    @Test
    void constructor_NoArgsConstructor_ShouldCreateEmptyInnovationType() {
        // Act
        InnovationType newType = new InnovationType();

        // Assert
        assertNull(newType.getId());
        assertNull(newType.getName());
        assertNull(newType.getDefinition());
        assertNull(newType.getIsOldType());
        assertNull(newType.getPrmsIdEquivalent());
        assertNull(newType.getPrmsNameEquivalent());
    }

    @Test
    void constructor_AllArgsConstructor_ShouldSetAllFields() {
        // Arrange
        Long id = 1L;
        String name = "Test Innovation Type";
        String definition = "Test Definition";
        Boolean isOldType = false;
        Long prmsIdEquivalent = 100L;
        String prmsNameEquivalent = "PRMS Name";

        // Act
        InnovationType newType = new InnovationType(id, name, definition, isOldType, prmsIdEquivalent, prmsNameEquivalent);

        // Assert
        assertEquals(id, newType.getId());
        assertEquals(name, newType.getName());
        assertEquals(definition, newType.getDefinition());
        assertEquals(isOldType, newType.getIsOldType());
        assertEquals(prmsIdEquivalent, newType.getPrmsIdEquivalent());
        assertEquals(prmsNameEquivalent, newType.getPrmsNameEquivalent());
    }

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        // Arrange
        Long id = 1L;
        String name = "Innovation Type Name";
        String definition = "Type Definition";
        Boolean isOldType = true;
        Long prmsIdEquivalent = 200L;
        String prmsNameEquivalent = "PRMS Equivalent Name";

        // Act
        innovationType.setId(id);
        innovationType.setName(name);
        innovationType.setDefinition(definition);
        innovationType.setIsOldType(isOldType);
        innovationType.setPrmsIdEquivalent(prmsIdEquivalent);
        innovationType.setPrmsNameEquivalent(prmsNameEquivalent);

        // Assert
        assertEquals(id, innovationType.getId());
        assertEquals(name, innovationType.getName());
        assertEquals(definition, innovationType.getDefinition());
        assertEquals(isOldType, innovationType.getIsOldType());
        assertEquals(prmsIdEquivalent, innovationType.getPrmsIdEquivalent());
        assertEquals(prmsNameEquivalent, innovationType.getPrmsNameEquivalent());
    }

    @Test
    void setIsOldType_WithTrue_ShouldSetToTrue() {
        // Act
        innovationType.setIsOldType(true);

        // Assert
        assertTrue(innovationType.getIsOldType());
    }

    @Test
    void setIsOldType_WithFalse_ShouldSetToFalse() {
        // Act
        innovationType.setIsOldType(false);

        // Assert
        assertFalse(innovationType.getIsOldType());
    }

    @Test
    void setIsOldType_WithNull_ShouldSetToNull() {
        // Act
        innovationType.setIsOldType(null);

        // Assert
        assertNull(innovationType.getIsOldType());
    }
}
