package com.example.demo.modules.projectinnovation.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LocElementTest {

    private LocElement locElement;

    @BeforeEach
    void setUp() {
        locElement = new LocElement();
    }

    @Test
    void constructor_NoArgsConstructor_ShouldCreateEmpty() {
        // Act
        LocElement newLocElement = new LocElement();

        // Assert
        assertNull(newLocElement.getId());
        assertNull(newLocElement.getName());
        assertNull(newLocElement.getIsoAlpha2());
    }

    @Test
    void constructor_AllArgsConstructor_ShouldSetAllFields() {
        // Arrange
        Long id = 1L;
        String name = "Test Location";
        String isoAlpha2 = "US";
        Long isoNumeric = 840L;
        Long parentId = 10L;
        Long elementTypeId = 20L;
        Long geopositionId = 30L;
        Boolean isSiteIntegration = true;
        Boolean isActive = true;
        Long createdBy = 100L;
        LocalDateTime activeSince = LocalDateTime.now();
        Long modifiedBy = 200L;
        String modificationJustification = "Test";
        Long globalUnitId = 300L;
        Long repIndRegionsId = 400L;
        String isoAlpha3 = "USA";

        // Act
        LocElement newLocElement = new LocElement(id, name, isoAlpha2, isoNumeric, parentId,
                elementTypeId, geopositionId, isSiteIntegration, isActive, createdBy,
                activeSince, modifiedBy, modificationJustification, globalUnitId,
                repIndRegionsId, isoAlpha3);

        // Assert
        assertEquals(id, newLocElement.getId());
        assertEquals(name, newLocElement.getName());
        assertEquals(isoAlpha2, newLocElement.getIsoAlpha2());
        assertEquals(isoNumeric, newLocElement.getIsoNumeric());
        assertEquals(parentId, newLocElement.getParentId());
        assertEquals(elementTypeId, newLocElement.getElementTypeId());
        assertEquals(geopositionId, newLocElement.getGeopositionId());
        assertEquals(isSiteIntegration, newLocElement.getIsSiteIntegration());
        assertEquals(isActive, newLocElement.getIsActive());
        assertEquals(createdBy, newLocElement.getCreatedBy());
        assertEquals(activeSince, newLocElement.getActiveSince());
        assertEquals(modifiedBy, newLocElement.getModifiedBy());
        assertEquals(modificationJustification, newLocElement.getModificationJustification());
        assertEquals(globalUnitId, newLocElement.getGlobalUnitId());
        assertEquals(repIndRegionsId, newLocElement.getRepIndRegionsId());
        assertEquals(isoAlpha3, newLocElement.getIsoAlpha3());
    }

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        // Arrange
        Long id = 1L;
        String name = "Location Name";
        String isoAlpha2 = "MX";
        Long isoNumeric = 484L;
        String isoAlpha3 = "MEX";

        // Act
        locElement.setId(id);
        locElement.setName(name);
        locElement.setIsoAlpha2(isoAlpha2);
        locElement.setIsoNumeric(isoNumeric);
        locElement.setIsoAlpha3(isoAlpha3);

        // Assert
        assertEquals(id, locElement.getId());
        assertEquals(name, locElement.getName());
        assertEquals(isoAlpha2, locElement.getIsoAlpha2());
        assertEquals(isoNumeric, locElement.getIsoNumeric());
        assertEquals(isoAlpha3, locElement.getIsoAlpha3());
    }

    @Test
    void equals_WithSameId_ShouldReturnTrue() {
        // Arrange
        LocElement loc1 = new LocElement();
        LocElement loc2 = new LocElement();
        loc1.setId(1L);
        loc2.setId(1L);

        // Act & Assert
        assertEquals(loc1, loc2);
    }

    @Test
    void equals_WithDifferentIds_ShouldReturnFalse() {
        // Arrange
        LocElement loc1 = new LocElement();
        LocElement loc2 = new LocElement();
        loc1.setId(1L);
        loc2.setId(2L);

        // Act & Assert
        assertNotEquals(loc1, loc2);
    }

    @Test
    void equals_WithNull_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(locElement, null);
    }

    @Test
    void equals_WithDifferentClass_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(locElement, "not a LocElement");
    }

    @Test
    void equals_WithSameInstance_ShouldReturnTrue() {
        // Act & Assert
        assertEquals(locElement, locElement);
    }

    @Test
    void equals_WithAllFieldsSame_ShouldReturnTrue() {
        // Arrange
        LocalDateTime activeSince = LocalDateTime.now();
        LocElement loc1 = new LocElement(1L, "Name", "US", 840L, 10L, 20L, 30L, true, true, 100L, activeSince, 200L, "Justification", 300L, 400L, "USA");
        LocElement loc2 = new LocElement(1L, "Name", "US", 840L, 10L, 20L, 30L, true, true, 100L, activeSince, 200L, "Justification", 300L, 400L, "USA");

        // Act & Assert
        assertEquals(loc1, loc2);
    }

    @Test
    void equals_WithDifferentFields_ShouldReturnFalse() {
        // Arrange
        LocElement loc1 = new LocElement();
        LocElement loc2 = new LocElement();
        loc1.setId(1L);
        loc1.setName("Name 1");
        loc2.setId(1L);
        loc2.setName("Name 2");

        // Act & Assert
        assertNotEquals(loc1, loc2);
    }

    @Test
    void hashCode_WithSameFields_ShouldReturnSameHashCode() {
        // Arrange
        LocElement loc1 = new LocElement();
        LocElement loc2 = new LocElement();
        loc1.setId(1L);
        loc1.setName("Name");
        loc2.setId(1L);
        loc2.setName("Name");

        // Act & Assert
        assertEquals(loc1.hashCode(), loc2.hashCode());
    }

    @Test
    void hashCode_WithDifferentFields_ShouldReturnDifferentHashCode() {
        // Arrange
        LocElement loc1 = new LocElement();
        LocElement loc2 = new LocElement();
        loc1.setId(1L);
        loc1.setName("Name 1");
        loc2.setId(2L);
        loc2.setName("Name 2");

        // Act & Assert
        assertNotEquals(loc1.hashCode(), loc2.hashCode());
    }

    @Test
    void hashCode_WithNullFields_ShouldNotThrowException() {
        // Act & Assert
        assertDoesNotThrow(() -> locElement.hashCode());
    }

    @Test
    void toString_ShouldContainClassName() {
        // Act
        String toString = locElement.toString();

        // Assert
        assertTrue(toString.contains("LocElement"));
    }

    @Test
    void equals_WithNullFields_ShouldHandleNulls() {
        // Arrange
        LocElement loc1 = new LocElement();
        LocElement loc2 = new LocElement();
        loc1.setId(null);
        loc1.setName(null);
        loc2.setId(null);
        loc2.setName(null);

        // Act & Assert
        assertEquals(loc1, loc2);
    }

    @Test
    void equals_WithOneNullField_ShouldReturnFalse() {
        // Arrange
        LocElement loc1 = new LocElement();
        LocElement loc2 = new LocElement();
        loc1.setId(1L);
        loc1.setName("Name");
        loc2.setId(1L);
        loc2.setName(null);

        // Act & Assert
        assertNotEquals(loc1, loc2);
    }
}
