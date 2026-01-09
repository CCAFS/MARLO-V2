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

    @Test
    void equals_WithSameId_ShouldReturnTrue() {
        // Arrange
        InnovationType t1 = new InnovationType();
        InnovationType t2 = new InnovationType();
        t1.setId(1L);
        t2.setId(1L);

        // Act & Assert
        assertEquals(t1, t2);
    }

    @Test
    void equals_WithDifferentIds_ShouldReturnFalse() {
        // Arrange
        InnovationType t1 = new InnovationType();
        InnovationType t2 = new InnovationType();
        t1.setId(1L);
        t2.setId(2L);

        // Act & Assert
        assertNotEquals(t1, t2);
    }

    @Test
    void equals_WithNull_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(innovationType, null);
    }

    @Test
    void equals_WithDifferentClass_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(innovationType, "not a type");
    }

    @Test
    void equals_WithSameInstance_ShouldReturnTrue() {
        // Act & Assert
        assertEquals(innovationType, innovationType);
    }

    @Test
    void equals_WithAllFieldsSame_ShouldReturnTrue() {
        // Arrange
        Long id = 1L;
        String name = "Name";
        String definition = "Definition";
        Boolean isOldType = false;
        Long prmsIdEquivalent = 100L;
        String prmsNameEquivalent = "PRMS Name";

        InnovationType t1 = new InnovationType(id, name, definition, isOldType, prmsIdEquivalent, prmsNameEquivalent);
        InnovationType t2 = new InnovationType(id, name, definition, isOldType, prmsIdEquivalent, prmsNameEquivalent);

        // Act & Assert
        assertEquals(t1, t2);
    }

    @Test
    void equals_WithDifferentFields_ShouldReturnFalse() {
        // Arrange
        InnovationType t1 = new InnovationType();
        InnovationType t2 = new InnovationType();
        t1.setId(1L);
        t1.setName("Name 1");
        t2.setId(1L);
        t2.setName("Name 2");

        // Act & Assert
        assertNotEquals(t1, t2);
    }

    @Test
    void hashCode_WithSameFields_ShouldReturnSameHashCode() {
        // Arrange
        InnovationType t1 = new InnovationType();
        InnovationType t2 = new InnovationType();
        t1.setId(1L);
        t1.setName("Name");
        t2.setId(1L);
        t2.setName("Name");

        // Act & Assert
        assertEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    void hashCode_WithDifferentFields_ShouldReturnDifferentHashCode() {
        // Arrange
        InnovationType t1 = new InnovationType();
        InnovationType t2 = new InnovationType();
        t1.setId(1L);
        t1.setName("Name 1");
        t2.setId(2L);
        t2.setName("Name 2");

        // Act & Assert
        assertNotEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    void hashCode_WithNullFields_ShouldNotThrowException() {
        // Act & Assert
        assertDoesNotThrow(() -> innovationType.hashCode());
    }

    @Test
    void toString_ShouldContainClassName() {
        // Act
        String toString = innovationType.toString();

        // Assert
        assertTrue(toString.contains("InnovationType"));
    }

    @Test
    void equals_WithNullFields_ShouldHandleNulls() {
        // Arrange
        InnovationType t1 = new InnovationType();
        InnovationType t2 = new InnovationType();
        t1.setId(null);
        t1.setName(null);
        t2.setId(null);
        t2.setName(null);

        // Act & Assert
        assertEquals(t1, t2);
    }

    @Test
    void equals_WithOneNullField_ShouldReturnFalse() {
        // Arrange
        InnovationType t1 = new InnovationType();
        InnovationType t2 = new InnovationType();
        t1.setId(1L);
        t1.setName("Name");
        t2.setId(1L);
        t2.setName(null);

        // Act & Assert
        assertNotEquals(t1, t2);
    }
}
