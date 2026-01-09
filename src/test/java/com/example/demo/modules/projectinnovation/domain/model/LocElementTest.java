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
}
