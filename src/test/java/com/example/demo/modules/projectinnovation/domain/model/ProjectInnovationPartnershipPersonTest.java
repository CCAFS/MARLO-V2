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

    @Test
    void equals_WithSameId_ShouldReturnTrue() {
        // Arrange
        ProjectInnovationPartnershipPerson p1 = new ProjectInnovationPartnershipPerson();
        ProjectInnovationPartnershipPerson p2 = new ProjectInnovationPartnershipPerson();
        p1.setId(1L);
        p2.setId(1L);

        // Act & Assert
        assertEquals(p1, p2);
    }

    @Test
    void equals_WithDifferentIds_ShouldReturnFalse() {
        // Arrange
        ProjectInnovationPartnershipPerson p1 = new ProjectInnovationPartnershipPerson();
        ProjectInnovationPartnershipPerson p2 = new ProjectInnovationPartnershipPerson();
        p1.setId(1L);
        p2.setId(2L);

        // Act & Assert
        assertNotEquals(p1, p2);
    }

    @Test
    void equals_WithNull_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(projectInnovationPartnershipPerson, null);
    }

    @Test
    void equals_WithDifferentClass_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(projectInnovationPartnershipPerson, "not a person");
    }

    @Test
    void equals_WithSameInstance_ShouldReturnTrue() {
        // Act & Assert
        assertEquals(projectInnovationPartnershipPerson, projectInnovationPartnershipPerson);
    }

    @Test
    void equals_WithAllFieldsSame_ShouldReturnTrue() {
        // Arrange
        Long id = 1L;
        Long partnershipId = 100L;
        Long userId = 200L;
        Boolean isActive = true;
        LocalDateTime activeSince = LocalDateTime.now();
        Long createdBy = 10L;
        Long modifiedBy = 20L;
        String modificationJustification = "Justification";

        ProjectInnovationPartnershipPerson p1 = new ProjectInnovationPartnershipPerson(id, partnershipId, userId, isActive, activeSince, createdBy, modifiedBy, modificationJustification);
        ProjectInnovationPartnershipPerson p2 = new ProjectInnovationPartnershipPerson(id, partnershipId, userId, isActive, activeSince, createdBy, modifiedBy, modificationJustification);

        // Act & Assert
        assertEquals(p1, p2);
    }

    @Test
    void equals_WithDifferentFields_ShouldReturnFalse() {
        // Arrange
        ProjectInnovationPartnershipPerson p1 = new ProjectInnovationPartnershipPerson();
        ProjectInnovationPartnershipPerson p2 = new ProjectInnovationPartnershipPerson();
        p1.setId(1L);
        p1.setUserId(100L);
        p2.setId(1L);
        p2.setUserId(200L);

        // Act & Assert
        assertNotEquals(p1, p2);
    }

    @Test
    void hashCode_WithSameFields_ShouldReturnSameHashCode() {
        // Arrange
        ProjectInnovationPartnershipPerson p1 = new ProjectInnovationPartnershipPerson();
        ProjectInnovationPartnershipPerson p2 = new ProjectInnovationPartnershipPerson();
        p1.setId(1L);
        p1.setUserId(100L);
        p2.setId(1L);
        p2.setUserId(100L);

        // Act & Assert
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void hashCode_WithDifferentFields_ShouldReturnDifferentHashCode() {
        // Arrange
        ProjectInnovationPartnershipPerson p1 = new ProjectInnovationPartnershipPerson();
        ProjectInnovationPartnershipPerson p2 = new ProjectInnovationPartnershipPerson();
        p1.setId(1L);
        p1.setUserId(100L);
        p2.setId(2L);
        p2.setUserId(200L);

        // Act & Assert
        assertNotEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void hashCode_WithNullFields_ShouldNotThrowException() {
        // Act & Assert
        assertDoesNotThrow(() -> projectInnovationPartnershipPerson.hashCode());
    }

    @Test
    void toString_ShouldContainClassName() {
        // Act
        String toString = projectInnovationPartnershipPerson.toString();

        // Assert
        assertTrue(toString.contains("ProjectInnovationPartnershipPerson"));
    }

    @Test
    void equals_WithNullFields_ShouldHandleNulls() {
        // Arrange
        ProjectInnovationPartnershipPerson p1 = new ProjectInnovationPartnershipPerson();
        ProjectInnovationPartnershipPerson p2 = new ProjectInnovationPartnershipPerson();
        p1.setId(null);
        p1.setUserId(null);
        p2.setId(null);
        p2.setUserId(null);

        // Act & Assert
        assertEquals(p1, p2);
    }

    @Test
    void equals_WithOneNullField_ShouldReturnFalse() {
        // Arrange
        ProjectInnovationPartnershipPerson p1 = new ProjectInnovationPartnershipPerson();
        ProjectInnovationPartnershipPerson p2 = new ProjectInnovationPartnershipPerson();
        p1.setId(1L);
        p1.setUserId(100L);
        p2.setId(1L);
        p2.setUserId(null);

        // Act & Assert
        assertNotEquals(p1, p2);
    }
}
