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
}
