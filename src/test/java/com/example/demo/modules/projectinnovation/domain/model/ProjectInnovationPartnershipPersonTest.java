package com.example.demo.modules.projectinnovation.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProjectInnovationPartnershipPersonTest {

    private ProjectInnovationPartnershipPerson projectInnovationPartnershipPerson;

    @BeforeEach
    void setUp() {
        projectInnovationPartnershipPerson = new ProjectInnovationPartnershipPerson();
    }

    @Test
    void constructor_NoArgsConstructor_ShouldCreateEmpty() {
        // Act
        ProjectInnovationPartnershipPerson newPerson = new ProjectInnovationPartnershipPerson();

        // Assert
        assertNull(newPerson.getId());
        assertNull(newPerson.getPartnershipId());
        assertNull(newPerson.getUserId());
        assertNull(newPerson.getIsActive());
    }

    @Test
    void constructor_AllArgsConstructor_ShouldSetAllFields() {
        // Arrange
        Long id = 1L;
        Long partnershipId = 100L;
        Long userId = 200L;
        Boolean isActive = true;
        LocalDateTime activeSince = LocalDateTime.now();
        Long createdBy = 10L;
        Long modifiedBy = 20L;
        String modificationJustification = "Test justification";

        // Act
        ProjectInnovationPartnershipPerson newPerson = new ProjectInnovationPartnershipPerson(
                id, partnershipId, userId, isActive, activeSince,
                createdBy, modifiedBy, modificationJustification);

        // Assert
        assertEquals(id, newPerson.getId());
        assertEquals(partnershipId, newPerson.getPartnershipId());
        assertEquals(userId, newPerson.getUserId());
        assertEquals(isActive, newPerson.getIsActive());
        assertEquals(activeSince, newPerson.getActiveSince());
        assertEquals(createdBy, newPerson.getCreatedBy());
        assertEquals(modifiedBy, newPerson.getModifiedBy());
        assertEquals(modificationJustification, newPerson.getModificationJustification());
    }

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        // Arrange
        Long id = 1L;
        Long partnershipId = 100L;
        Long userId = 200L;
        Boolean isActive = true;
        LocalDateTime activeSince = LocalDateTime.now();
        Long createdBy = 10L;
        Long modifiedBy = 20L;
        String modificationJustification = "Justification";

        // Act
        projectInnovationPartnershipPerson.setId(id);
        projectInnovationPartnershipPerson.setPartnershipId(partnershipId);
        projectInnovationPartnershipPerson.setUserId(userId);
        projectInnovationPartnershipPerson.setIsActive(isActive);
        projectInnovationPartnershipPerson.setActiveSince(activeSince);
        projectInnovationPartnershipPerson.setCreatedBy(createdBy);
        projectInnovationPartnershipPerson.setModifiedBy(modifiedBy);
        projectInnovationPartnershipPerson.setModificationJustification(modificationJustification);

        // Assert
        assertEquals(id, projectInnovationPartnershipPerson.getId());
        assertEquals(partnershipId, projectInnovationPartnershipPerson.getPartnershipId());
        assertEquals(userId, projectInnovationPartnershipPerson.getUserId());
        assertEquals(isActive, projectInnovationPartnershipPerson.getIsActive());
        assertEquals(activeSince, projectInnovationPartnershipPerson.getActiveSince());
        assertEquals(createdBy, projectInnovationPartnershipPerson.getCreatedBy());
        assertEquals(modifiedBy, projectInnovationPartnershipPerson.getModifiedBy());
        assertEquals(modificationJustification, projectInnovationPartnershipPerson.getModificationJustification());
    }
}
