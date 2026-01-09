package com.example.demo.modules.projectinnovation.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InnovationStageTest {

    private InnovationStage innovationStage;

    @BeforeEach
    void setUp() {
        innovationStage = new InnovationStage();
    }

    @Test
    void constructor_NoArgsConstructor_ShouldCreateEmptyInnovationStage() {
        // Act
        InnovationStage newStage = new InnovationStage();

        // Assert
        assertNull(newStage.getId());
        assertNull(newStage.getName());
        assertNull(newStage.getDefinition());
    }

    @Test
    void constructor_AllArgsConstructor_ShouldSetAllFields() {
        // Arrange
        Long id = 1L;
        String name = "Test Stage";
        String definition = "Test Definition";

        // Act
        InnovationStage newStage = new InnovationStage(id, name, definition);

        // Assert
        assertEquals(id, newStage.getId());
        assertEquals(name, newStage.getName());
        assertEquals(definition, newStage.getDefinition());
    }

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        // Arrange
        Long id = 1L;
        String name = "Stage Name";
        String definition = "Stage Definition";

        // Act
        innovationStage.setId(id);
        innovationStage.setName(name);
        innovationStage.setDefinition(definition);

        // Assert
        assertEquals(id, innovationStage.getId());
        assertEquals(name, innovationStage.getName());
        assertEquals(definition, innovationStage.getDefinition());
    }

    @Test
    void setName_WithNull_ShouldSetToNull() {
        // Act
        innovationStage.setName(null);

        // Assert
        assertNull(innovationStage.getName());
    }

    @Test
    void setDefinition_WithNull_ShouldSetToNull() {
        // Act
        innovationStage.setDefinition(null);

        // Assert
        assertNull(innovationStage.getDefinition());
    }

    @Test
    void setId_WithNull_ShouldSetToNull() {
        // Act
        innovationStage.setId(null);

        // Assert
        assertNull(innovationStage.getId());
    }

    @Test
    void equals_WithSameId_ShouldReturnTrue() {
        // Arrange
        InnovationStage s1 = new InnovationStage();
        InnovationStage s2 = new InnovationStage();
        s1.setId(1L);
        s2.setId(1L);

        // Act & Assert
        assertEquals(s1, s2);
    }

    @Test
    void equals_WithDifferentIds_ShouldReturnFalse() {
        // Arrange
        InnovationStage s1 = new InnovationStage();
        InnovationStage s2 = new InnovationStage();
        s1.setId(1L);
        s2.setId(2L);

        // Act & Assert
        assertNotEquals(s1, s2);
    }

    @Test
    void equals_WithNull_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(innovationStage, null);
    }

    @Test
    void equals_WithDifferentClass_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(innovationStage, "not a stage");
    }

    @Test
    void equals_WithSameInstance_ShouldReturnTrue() {
        // Act & Assert
        assertEquals(innovationStage, innovationStage);
    }

    @Test
    void equals_WithAllFieldsSame_ShouldReturnTrue() {
        // Arrange
        Long id = 1L;
        String name = "Name";
        String definition = "Definition";

        InnovationStage s1 = new InnovationStage(id, name, definition);
        InnovationStage s2 = new InnovationStage(id, name, definition);

        // Act & Assert
        assertEquals(s1, s2);
    }

    @Test
    void equals_WithDifferentFields_ShouldReturnFalse() {
        // Arrange
        InnovationStage s1 = new InnovationStage();
        InnovationStage s2 = new InnovationStage();
        s1.setId(1L);
        s1.setName("Name 1");
        s2.setId(1L);
        s2.setName("Name 2");

        // Act & Assert
        assertNotEquals(s1, s2);
    }

    @Test
    void hashCode_WithSameFields_ShouldReturnSameHashCode() {
        // Arrange
        InnovationStage s1 = new InnovationStage();
        InnovationStage s2 = new InnovationStage();
        s1.setId(1L);
        s1.setName("Name");
        s2.setId(1L);
        s2.setName("Name");

        // Act & Assert
        assertEquals(s1.hashCode(), s2.hashCode());
    }

    @Test
    void hashCode_WithDifferentFields_ShouldReturnDifferentHashCode() {
        // Arrange
        InnovationStage s1 = new InnovationStage();
        InnovationStage s2 = new InnovationStage();
        s1.setId(1L);
        s1.setName("Name 1");
        s2.setId(2L);
        s2.setName("Name 2");

        // Act & Assert
        assertNotEquals(s1.hashCode(), s2.hashCode());
    }

    @Test
    void hashCode_WithNullFields_ShouldNotThrowException() {
        // Act & Assert
        assertDoesNotThrow(() -> innovationStage.hashCode());
    }

    @Test
    void toString_ShouldContainClassName() {
        // Act
        String toString = innovationStage.toString();

        // Assert
        assertTrue(toString.contains("InnovationStage"));
    }

    @Test
    void equals_WithNullFields_ShouldHandleNulls() {
        // Arrange
        InnovationStage s1 = new InnovationStage();
        InnovationStage s2 = new InnovationStage();
        s1.setId(null);
        s1.setName(null);
        s2.setId(null);
        s2.setName(null);

        // Act & Assert
        assertEquals(s1, s2);
    }

    @Test
    void equals_WithOneNullField_ShouldReturnFalse() {
        // Arrange
        InnovationStage s1 = new InnovationStage();
        InnovationStage s2 = new InnovationStage();
        s1.setId(1L);
        s1.setName("Name");
        s2.setId(1L);
        s2.setName(null);

        // Act & Assert
        assertNotEquals(s1, s2);
    }
}
