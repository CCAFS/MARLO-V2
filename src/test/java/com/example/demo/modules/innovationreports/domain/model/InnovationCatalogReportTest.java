package com.example.demo.modules.innovationreports.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InnovationCatalogReportTest {

    @Test
    void constructor_WithNoArgs_ShouldCreateEmptyObject() {
        // Act
        InnovationCatalogReport report = new InnovationCatalogReport();

        // Assert
        assertNotNull(report);
        assertNull(report.getId());
        assertNull(report.getInnovationId());
        assertNull(report.getUserName());
        assertNull(report.getUserLastname());
        assertNull(report.getUserEmail());
        assertNull(report.getInterestNarrative());
    }

    @Test
    void constructor_WithArgs_ShouldSetAllFields() {
        // Act
        InnovationCatalogReport report = new InnovationCatalogReport(
                1L, "John", "Doe", "john.doe@example.com", "I'm interested in this innovation");

        // Assert
        assertEquals(1L, report.getInnovationId());
        assertEquals("John", report.getUserName());
        assertEquals("Doe", report.getUserLastname());
        assertEquals("john.doe@example.com", report.getUserEmail());
        assertEquals("I'm interested in this innovation", report.getInterestNarrative());
    }

    @Test
    void settersAndGetters_Id_ShouldWorkCorrectly() {
        // Arrange
        InnovationCatalogReport report = new InnovationCatalogReport();

        // Act
        report.setId(1L);

        // Assert
        assertEquals(1L, report.getId());
    }

    @Test
    void settersAndGetters_InnovationId_ShouldWorkCorrectly() {
        // Arrange
        InnovationCatalogReport report = new InnovationCatalogReport();

        // Act
        report.setInnovationId(100L);

        // Assert
        assertEquals(100L, report.getInnovationId());
    }

    @Test
    void settersAndGetters_UserName_ShouldWorkCorrectly() {
        // Arrange
        InnovationCatalogReport report = new InnovationCatalogReport();

        // Act
        report.setUserName("Jane");

        // Assert
        assertEquals("Jane", report.getUserName());
    }

    @Test
    void settersAndGetters_UserLastname_ShouldWorkCorrectly() {
        // Arrange
        InnovationCatalogReport report = new InnovationCatalogReport();

        // Act
        report.setUserLastname("Smith");

        // Assert
        assertEquals("Smith", report.getUserLastname());
    }

    @Test
    void settersAndGetters_UserEmail_ShouldWorkCorrectly() {
        // Arrange
        InnovationCatalogReport report = new InnovationCatalogReport();

        // Act
        report.setUserEmail("jane.smith@example.com");

        // Assert
        assertEquals("jane.smith@example.com", report.getUserEmail());
    }

    @Test
    void settersAndGetters_InterestNarrative_ShouldWorkCorrectly() {
        // Arrange
        InnovationCatalogReport report = new InnovationCatalogReport();

        // Act
        report.setInterestNarrative("This is my interest narrative");

        // Assert
        assertEquals("This is my interest narrative", report.getInterestNarrative());
    }

    @Test
    void settersAndGetters_IsActive_ShouldWorkCorrectly() {
        // Arrange
        InnovationCatalogReport report = new InnovationCatalogReport();

        // Act
        report.setIsActive(false);

        // Assert
        assertFalse(report.getIsActive());
    }

    @Test
    void settersAndGetters_ActiveSince_ShouldWorkCorrectly() {
        // Arrange
        InnovationCatalogReport report = new InnovationCatalogReport();
        LocalDateTime now = LocalDateTime.now();

        // Act
        report.setActiveSince(now);

        // Assert
        assertEquals(now, report.getActiveSince());
    }

    @Test
    void settersAndGetters_ModificationJustification_ShouldWorkCorrectly() {
        // Arrange
        InnovationCatalogReport report = new InnovationCatalogReport();

        // Act
        report.setModificationJustification("Updated for accuracy");

        // Assert
        assertEquals("Updated for accuracy", report.getModificationJustification());
    }

    @Test
    void settersAndGetters_AllFields_ShouldWorkCorrectly() {
        // Arrange
        InnovationCatalogReport report = new InnovationCatalogReport();
        LocalDateTime now = LocalDateTime.now();

        // Act
        report.setId(1L);
        report.setInnovationId(100L);
        report.setUserName("John");
        report.setUserLastname("Doe");
        report.setUserEmail("john.doe@test.com");
        report.setInterestNarrative("My narrative");
        report.setIsActive(true);
        report.setActiveSince(now);
        report.setModificationJustification("Initial creation");

        // Assert
        assertEquals(1L, report.getId());
        assertEquals(100L, report.getInnovationId());
        assertEquals("John", report.getUserName());
        assertEquals("Doe", report.getUserLastname());
        assertEquals("john.doe@test.com", report.getUserEmail());
        assertEquals("My narrative", report.getInterestNarrative());
        assertTrue(report.getIsActive());
        assertEquals(now, report.getActiveSince());
        assertEquals("Initial creation", report.getModificationJustification());
    }

    @Test
    void onCreate_WithNullActiveSince_ShouldSetActiveSince() {
        // Arrange
        InnovationCatalogReport report = new InnovationCatalogReport();
        report.setActiveSince(null);

        // Act
        report.onCreate();

        // Assert
        assertNotNull(report.getActiveSince());
    }

    @Test
    void onCreate_WithNullIsActive_ShouldSetIsActiveTrue() {
        // Arrange
        InnovationCatalogReport report = new InnovationCatalogReport();
        report.setIsActive(null);

        // Act
        report.onCreate();

        // Assert
        assertTrue(report.getIsActive());
    }

    @Test
    void onCreate_WithExistingValues_ShouldNotOverwrite() {
        // Arrange
        InnovationCatalogReport report = new InnovationCatalogReport();
        LocalDateTime originalTime = LocalDateTime.of(2024, 1, 1, 0, 0);
        report.setActiveSince(originalTime);
        report.setIsActive(false);

        // Act
        report.onCreate();

        // Assert
        assertEquals(originalTime, report.getActiveSince());
        assertFalse(report.getIsActive());
    }

    @Test
    void onUpdate_ShouldUpdateActiveSince() {
        // Arrange
        InnovationCatalogReport report = new InnovationCatalogReport();
        LocalDateTime originalTime = LocalDateTime.of(2024, 1, 1, 0, 0);
        report.setActiveSince(originalTime);

        // Act
        report.onUpdate();

        // Assert
        assertNotEquals(originalTime, report.getActiveSince());
        assertNotNull(report.getActiveSince());
    }

    @Test
    void setNullValues_ShouldAcceptNulls() {
        // Arrange
        InnovationCatalogReport report = new InnovationCatalogReport(
                1L, "John", "Doe", "john@test.com", "Narrative");

        // Act
        report.setId(null);
        report.setInnovationId(null);
        report.setUserName(null);
        report.setUserLastname(null);
        report.setUserEmail(null);
        report.setInterestNarrative(null);
        report.setIsActive(null);
        report.setActiveSince(null);
        report.setModificationJustification(null);

        // Assert
        assertNull(report.getId());
        assertNull(report.getInnovationId());
        assertNull(report.getUserName());
        assertNull(report.getUserLastname());
        assertNull(report.getUserEmail());
        assertNull(report.getInterestNarrative());
        assertNull(report.getIsActive());
        assertNull(report.getActiveSince());
        assertNull(report.getModificationJustification());
    }

    @Test
    void defaultValues_ShouldHaveIsActiveTrue() {
        // Act
        InnovationCatalogReport report = new InnovationCatalogReport();

        // Assert - Default values from field initialization
        assertTrue(report.getIsActive());
        assertNotNull(report.getActiveSince());
    }
}
