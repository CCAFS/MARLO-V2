package com.example.demo.modules.projectinnovation.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InstitutionTest {

    private Institution institution;

    @BeforeEach
    void setUp() {
        institution = new Institution();
    }

    @Test
    void constructor_NoArgsConstructor_ShouldCreateEmptyInstitution() {
        // Act
        Institution newInstitution = new Institution();

        // Assert
        assertNull(newInstitution.getId());
        assertNull(newInstitution.getName());
        assertNull(newInstitution.getAcronym());
        assertNull(newInstitution.getWebsiteLink());
    }

    @Test
    void constructor_AllArgsConstructor_ShouldSetAllFields() {
        // Arrange
        Long id = 1L;
        String name = "Test Institution";
        String acronym = "TI";
        String websiteLink = "https://test.com";
        Long programId = 10L;
        Long institutionTypeId = 20L;
        LocalDateTime added = LocalDateTime.now();
        Long parentId = 30L;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        Boolean isActive = true;
        Long createdBy = 100L;
        Long updatedBy = 200L;
        String modificationJustification = "Test justification";

        // Act
        Institution newInstitution = new Institution(id, name, acronym, websiteLink, programId,
                institutionTypeId, added, parentId, createdAt, updatedAt, isActive,
                createdBy, updatedBy, modificationJustification);

        // Assert
        assertEquals(id, newInstitution.getId());
        assertEquals(name, newInstitution.getName());
        assertEquals(acronym, newInstitution.getAcronym());
        assertEquals(websiteLink, newInstitution.getWebsiteLink());
        assertEquals(programId, newInstitution.getProgramId());
        assertEquals(institutionTypeId, newInstitution.getInstitutionTypeId());
        assertEquals(added, newInstitution.getAdded());
        assertEquals(parentId, newInstitution.getParentId());
        assertEquals(createdAt, newInstitution.getCreatedAt());
        assertEquals(updatedAt, newInstitution.getUpdatedAt());
        assertEquals(isActive, newInstitution.getIsActive());
        assertEquals(createdBy, newInstitution.getCreatedBy());
        assertEquals(updatedBy, newInstitution.getUpdatedBy());
        assertEquals(modificationJustification, newInstitution.getModificationJustification());
    }

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        // Arrange
        Long id = 1L;
        String name = "Institution Name";
        String acronym = "IN";
        String websiteLink = "https://institution.com";
        Long programId = 10L;
        Long institutionTypeId = 20L;
        LocalDateTime added = LocalDateTime.now();
        Long parentId = 30L;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        Boolean isActive = true;
        Long createdBy = 100L;
        Long updatedBy = 200L;
        String modificationJustification = "Justification";

        // Act
        institution.setId(id);
        institution.setName(name);
        institution.setAcronym(acronym);
        institution.setWebsiteLink(websiteLink);
        institution.setProgramId(programId);
        institution.setInstitutionTypeId(institutionTypeId);
        institution.setAdded(added);
        institution.setParentId(parentId);
        institution.setCreatedAt(createdAt);
        institution.setUpdatedAt(updatedAt);
        institution.setIsActive(isActive);
        institution.setCreatedBy(createdBy);
        institution.setUpdatedBy(updatedBy);
        institution.setModificationJustification(modificationJustification);

        // Assert
        assertEquals(id, institution.getId());
        assertEquals(name, institution.getName());
        assertEquals(acronym, institution.getAcronym());
        assertEquals(websiteLink, institution.getWebsiteLink());
        assertEquals(programId, institution.getProgramId());
        assertEquals(institutionTypeId, institution.getInstitutionTypeId());
        assertEquals(added, institution.getAdded());
        assertEquals(parentId, institution.getParentId());
        assertEquals(createdAt, institution.getCreatedAt());
        assertEquals(updatedAt, institution.getUpdatedAt());
        assertEquals(isActive, institution.getIsActive());
        assertEquals(createdBy, institution.getCreatedBy());
        assertEquals(updatedBy, institution.getUpdatedBy());
        assertEquals(modificationJustification, institution.getModificationJustification());
    }

    @Test
    void setIsActive_WithFalse_ShouldSetToFalse() {
        // Act
        institution.setIsActive(false);

        // Assert
        assertFalse(institution.getIsActive());
    }

    @Test
    void setIsActive_WithNull_ShouldSetToNull() {
        // Act
        institution.setIsActive(null);

        // Assert
        assertNull(institution.getIsActive());
    }

    @Test
    void equals_WithSameId_ShouldReturnTrue() {
        // Arrange
        Institution inst1 = new Institution();
        Institution inst2 = new Institution();
        inst1.setId(1L);
        inst2.setId(1L);

        // Act & Assert
        assertEquals(inst1, inst2);
    }

    @Test
    void equals_WithDifferentIds_ShouldReturnFalse() {
        // Arrange
        Institution inst1 = new Institution();
        Institution inst2 = new Institution();
        inst1.setId(1L);
        inst2.setId(2L);

        // Act & Assert
        assertNotEquals(inst1, inst2);
    }

    @Test
    void equals_WithNull_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(institution, null);
    }

    @Test
    void equals_WithDifferentClass_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(institution, "not an institution");
    }

    @Test
    void equals_WithSameInstance_ShouldReturnTrue() {
        // Act & Assert
        assertEquals(institution, institution);
    }

    @Test
    void equals_WithAllFieldsSame_ShouldReturnTrue() {
        // Arrange
        Long id = 1L;
        String name = "Institution Name";
        String acronym = "IN";
        String websiteLink = "https://institution.com";
        Long programId = 10L;
        Long institutionTypeId = 20L;
        LocalDateTime added = LocalDateTime.now();
        Long parentId = 30L;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();
        Boolean isActive = true;
        Long createdBy = 100L;
        Long updatedBy = 200L;
        String modificationJustification = "Justification";

        Institution inst1 = new Institution(id, name, acronym, websiteLink, programId, institutionTypeId, added, parentId, createdAt, updatedAt, isActive, createdBy, updatedBy, modificationJustification);
        Institution inst2 = new Institution(id, name, acronym, websiteLink, programId, institutionTypeId, added, parentId, createdAt, updatedAt, isActive, createdBy, updatedBy, modificationJustification);

        // Act & Assert
        assertEquals(inst1, inst2);
    }

    @Test
    void equals_WithDifferentFields_ShouldReturnFalse() {
        // Arrange
        Institution inst1 = new Institution();
        Institution inst2 = new Institution();
        inst1.setId(1L);
        inst1.setName("Name 1");
        inst2.setId(1L);
        inst2.setName("Name 2");

        // Act & Assert
        assertNotEquals(inst1, inst2);
    }

    @Test
    void hashCode_WithSameFields_ShouldReturnSameHashCode() {
        // Arrange
        Institution inst1 = new Institution();
        Institution inst2 = new Institution();
        inst1.setId(1L);
        inst1.setName("Name");
        inst2.setId(1L);
        inst2.setName("Name");

        // Act & Assert
        assertEquals(inst1.hashCode(), inst2.hashCode());
    }

    @Test
    void hashCode_WithDifferentFields_ShouldReturnDifferentHashCode() {
        // Arrange
        Institution inst1 = new Institution();
        Institution inst2 = new Institution();
        inst1.setId(1L);
        inst1.setName("Name 1");
        inst2.setId(2L);
        inst2.setName("Name 2");

        // Act & Assert
        assertNotEquals(inst1.hashCode(), inst2.hashCode());
    }

    @Test
    void hashCode_WithNullFields_ShouldNotThrowException() {
        // Act & Assert
        assertDoesNotThrow(() -> institution.hashCode());
    }

    @Test
    void toString_ShouldContainClassName() {
        // Act
        String toString = institution.toString();

        // Assert
        assertTrue(toString.contains("Institution"));
    }

    @Test
    void equals_WithNullFields_ShouldHandleNulls() {
        // Arrange
        Institution inst1 = new Institution();
        Institution inst2 = new Institution();
        inst1.setId(null);
        inst1.setName(null);
        inst2.setId(null);
        inst2.setName(null);

        // Act & Assert
        assertEquals(inst1, inst2);
    }

    @Test
    void equals_WithOneNullField_ShouldReturnFalse() {
        // Arrange
        Institution inst1 = new Institution();
        Institution inst2 = new Institution();
        inst1.setId(1L);
        inst1.setName("Name");
        inst2.setId(1L);
        inst2.setName(null);

        // Act & Assert
        assertNotEquals(inst1, inst2);
    }
}
