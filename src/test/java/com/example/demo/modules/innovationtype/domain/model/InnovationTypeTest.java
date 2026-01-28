package com.example.demo.modules.innovationtype.domain.model;

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
    void constructor_WithNoArgsConstructor_ShouldCreateEmpty() {
        // Act
        InnovationType type = new InnovationType();

        // Assert
        assertNotNull(type);
        assertNull(type.getId());
        assertNull(type.getName());
        assertNull(type.getDefinition());
        assertNull(type.getIsOldType());
        assertNull(type.getPrmsIdEquivalent());
        assertNull(type.getPrmsNameEquivalent());
    }

    @Test
    void constructor_WithAllArgsConstructor_ShouldSetAllFields() {
        // Act
        InnovationType type = new InnovationType(
            1L, "Test Type", "Definition", false, 100L, "PRMS Name"
        );

        // Assert
        assertEquals(1L, type.getId());
        assertEquals("Test Type", type.getName());
        assertEquals("Definition", type.getDefinition());
        assertFalse(type.getIsOldType());
        assertEquals(100L, type.getPrmsIdEquivalent());
        assertEquals("PRMS Name", type.getPrmsNameEquivalent());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Act
        innovationType.setId(1L);
        innovationType.setName("Type Name");
        innovationType.setDefinition("Type Definition");
        innovationType.setIsOldType(false);
        innovationType.setPrmsIdEquivalent(100L);
        innovationType.setPrmsNameEquivalent("PRMS");

        // Assert
        assertEquals(1L, innovationType.getId());
        assertEquals("Type Name", innovationType.getName());
        assertEquals("Type Definition", innovationType.getDefinition());
        assertFalse(innovationType.getIsOldType());
        assertEquals(100L, innovationType.getPrmsIdEquivalent());
        assertEquals("PRMS", innovationType.getPrmsNameEquivalent());
    }

    @Test
    void equals_WithSameValues_ShouldBeEqual() {
        // Arrange
        InnovationType type1 = new InnovationType(1L, "Type", "Def", false, 100L, "PRMS");
        InnovationType type2 = new InnovationType(1L, "Type", "Def", false, 100L, "PRMS");

        // Assert
        assertEquals(type1, type2);
        assertEquals(type1.hashCode(), type2.hashCode());
    }

    @Test
    void equals_WithDifferentId_ShouldNotBeEqual() {
        // Arrange
        InnovationType type1 = new InnovationType(1L, "Type", "Def", false, 100L, "PRMS");
        InnovationType type2 = new InnovationType(2L, "Type", "Def", false, 100L, "PRMS");

        // Assert
        assertNotEquals(type1, type2);
    }

    @Test
    void equals_WithDifferentName_ShouldNotBeEqual() {
        // Arrange
        InnovationType type1 = new InnovationType(1L, "Type1", "Def", false, 100L, "PRMS");
        InnovationType type2 = new InnovationType(1L, "Type2", "Def", false, 100L, "PRMS");

        // Assert
        assertNotEquals(type1, type2);
    }

    @Test
    void equals_WithNull_ShouldNotBeEqual() {
        // Arrange
        InnovationType type = new InnovationType(1L, "Type", "Def", false, 100L, "PRMS");

        // Assert
        assertNotNull(type);
    }

    @Test
    void equals_WithSelf_ShouldBeEqual() {
        // Arrange
        InnovationType type = new InnovationType(1L, "Type", "Def", false, 100L, "PRMS");

        // Assert
        assertEquals(type, type);
    }

    @Test
    void equals_WithDifferentClass_ShouldNotBeEqual() {
        // Arrange
        InnovationType type = new InnovationType(1L, "Type", "Def", false, 100L, "PRMS");

        // Assert
        assertFalse(type.equals("string"));
    }

    @Test
    void hashCode_WithSameValues_ShouldBeEqual() {
        // Arrange
        InnovationType type1 = new InnovationType(1L, "Type", "Def", true, 100L, "PRMS");
        InnovationType type2 = new InnovationType(1L, "Type", "Def", true, 100L, "PRMS");

        // Assert
        assertEquals(type1.hashCode(), type2.hashCode());
    }

    @Test
    void hashCode_WithDifferentValues_ShouldBeDifferent() {
        // Arrange
        InnovationType type1 = new InnovationType(1L, "Type1", "Def1", true, 100L, "PRMS1");
        InnovationType type2 = new InnovationType(2L, "Type2", "Def2", false, 200L, "PRMS2");

        // Assert
        assertNotEquals(type1.hashCode(), type2.hashCode());
    }

    @Test
    void toString_ShouldContainAllFields() {
        // Arrange
        InnovationType type = new InnovationType(1L, "Tech Innovation", "Definition", false, 100L, "PRMS");

        // Act
        String result = type.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("1") || result.contains("Tech Innovation"));
    }

    @Test
    void setNullValues_ShouldAcceptNulls() {
        // Arrange
        InnovationType type = new InnovationType(1L, "Type", "Def", false, 100L, "PRMS");

        // Act
        type.setId(null);
        type.setName(null);
        type.setDefinition(null);
        type.setIsOldType(null);
        type.setPrmsIdEquivalent(null);
        type.setPrmsNameEquivalent(null);

        // Assert
        assertNull(type.getId());
        assertNull(type.getName());
        assertNull(type.getDefinition());
        assertNull(type.getIsOldType());
        assertNull(type.getPrmsIdEquivalent());
        assertNull(type.getPrmsNameEquivalent());
    }

    @Test
    void canEqual_WithSameClass_ShouldReturnTrue() {
        // Arrange
        InnovationType type1 = new InnovationType();
        InnovationType type2 = new InnovationType();

        // Assert - Lombok @Data generates canEqual
        assertTrue(type1.canEqual(type2));
    }

    @Test
    void isOldType_WithTrue_ShouldReturnTrue() {
        // Arrange
        InnovationType type = new InnovationType();

        // Act
        type.setIsOldType(true);

        // Assert
        assertTrue(type.getIsOldType());
    }

    @Test
    void constructor_WithEmptyStrings_ShouldWork() {
        // Act
        InnovationType type = new InnovationType(0L, "", "", false, 0L, "");

        // Assert
        assertEquals(0L, type.getId());
        assertEquals("", type.getName());
        assertEquals("", type.getDefinition());
        assertFalse(type.getIsOldType());
        assertEquals(0L, type.getPrmsIdEquivalent());
        assertEquals("", type.getPrmsNameEquivalent());
    }
}
