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
}
