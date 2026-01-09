package com.example.demo.modules.projectinnovation.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProjectInnovationSdgTest {

    private ProjectInnovationSdg projectInnovationSdg;

    @BeforeEach
    void setUp() {
        projectInnovationSdg = new ProjectInnovationSdg();
    }

    @Test
    void constructor_NoArgsConstructor_ShouldCreateEmpty() {
        // Act
        ProjectInnovationSdg newSdg = new ProjectInnovationSdg();

        // Assert
        assertNull(newSdg.getId());
        assertNull(newSdg.getInnovationId());
        assertNull(newSdg.getSdgId());
        assertNull(newSdg.getIdPhase());
        assertNull(newSdg.getIsActive());
    }

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        // Arrange
        Long id = 1L;
        Long innovationId = 100L;
        Long sdgId = 200L;
        Long idPhase = 1L;
        Boolean isActive = true;
        LocalDateTime activeSince = LocalDateTime.now();
        Long createdBy = 10L;
        Long modifiedBy = 20L;
        String modificationJustification = "Test justification";

        // Act
        projectInnovationSdg.setId(id);
        projectInnovationSdg.setInnovationId(innovationId);
        projectInnovationSdg.setSdgId(sdgId);
        projectInnovationSdg.setIdPhase(idPhase);
        projectInnovationSdg.setIsActive(isActive);
        projectInnovationSdg.setActiveSince(activeSince);
        projectInnovationSdg.setCreatedBy(createdBy);
        projectInnovationSdg.setModifiedBy(modifiedBy);
        projectInnovationSdg.setModificationJustification(modificationJustification);

        // Assert
        assertEquals(id, projectInnovationSdg.getId());
        assertEquals(innovationId, projectInnovationSdg.getInnovationId());
        assertEquals(sdgId, projectInnovationSdg.getSdgId());
        assertEquals(idPhase, projectInnovationSdg.getIdPhase());
        assertEquals(isActive, projectInnovationSdg.getIsActive());
        assertEquals(activeSince, projectInnovationSdg.getActiveSince());
        assertEquals(createdBy, projectInnovationSdg.getCreatedBy());
        assertEquals(modifiedBy, projectInnovationSdg.getModifiedBy());
        assertEquals(modificationJustification, projectInnovationSdg.getModificationJustification());
    }

    @Test
    void setIsActive_WithFalse_ShouldSetToFalse() {
        // Act
        projectInnovationSdg.setIsActive(false);

        // Assert
        assertFalse(projectInnovationSdg.getIsActive());
    }
}
