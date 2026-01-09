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
}
