package com.example.demo.modules.projectinnovation.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectInnovationInfoTest {

    @Test
    void constructor_WithNoArgsConstructor_ShouldCreateEmpty() {
        // Act
        ProjectInnovationInfo info = new ProjectInnovationInfo();

        // Assert
        assertNotNull(info);
    }
}
