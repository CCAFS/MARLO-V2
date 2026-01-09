package com.example.demo.modules.projectinnovation.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProjectInnovationActorsTest {

    private ProjectInnovationActors projectInnovationActors;

    @BeforeEach
    void setUp() {
        projectInnovationActors = new ProjectInnovationActors();
    }

    @Test
    void constructor_NoArgsConstructor_ShouldCreateEmpty() {
        // Act
        ProjectInnovationActors newPIA = new ProjectInnovationActors();

        // Assert
        assertNull(newPIA.getId());
        assertNull(newPIA.getInnovationId());
        assertNull(newPIA.getActorId());
        assertTrue(newPIA.getIsActive()); // Default value is true
    }

    @Test
    void constructor_AllArgsConstructor_ShouldSetAllFields() {
        // Arrange
        Long id = 1L;
        Long innovationId = 100L;
        Long actorId = 200L;
        Boolean isActive = true;
        LocalDateTime activeSince = LocalDateTime.now();
        Long createdBy = 10L;
        Long modifiedBy = 20L;
        String modificationJustification = "Test justification";
        Long idPhase = 1L;
        Boolean isWomenYouth = true;
        Boolean isWomenNotYouth = false;
        Boolean isMenYouth = false;
        Boolean isMenNotYouth = false;
        Boolean isNonbinaryYouth = false;
        Boolean isNonbinaryNotYouth = false;
        Boolean isSexAgeNotApply = false;
        Integer womenYouthNumber = 10;
        Integer womenNonYouthNumber = 5;
        Integer menYouthNumber = 8;
        Integer menNonYouthNumber = 7;
        String other = "Other info";
        Integer total = 30;

        // Act
        ProjectInnovationActors newPIA = new ProjectInnovationActors(id, innovationId, actorId, null,
                isActive, activeSince, createdBy, modifiedBy, modificationJustification, idPhase,
                isWomenYouth, isWomenNotYouth, isMenYouth, isMenNotYouth, isNonbinaryYouth,
                isNonbinaryNotYouth, isSexAgeNotApply, womenYouthNumber, womenNonYouthNumber,
                menYouthNumber, menNonYouthNumber, other, total);

        // Assert
        assertEquals(id, newPIA.getId());
        assertEquals(innovationId, newPIA.getInnovationId());
        assertEquals(actorId, newPIA.getActorId());
        assertEquals(isActive, newPIA.getIsActive());
        assertEquals(activeSince, newPIA.getActiveSince());
        assertEquals(createdBy, newPIA.getCreatedBy());
        assertEquals(modifiedBy, newPIA.getModifiedBy());
        assertEquals(modificationJustification, newPIA.getModificationJustification());
        assertEquals(idPhase, newPIA.getIdPhase());
        assertEquals(isWomenYouth, newPIA.getIsWomenYouth());
        assertEquals(womenYouthNumber, newPIA.getWomenYouthNumber());
        assertEquals(total, newPIA.getTotal());
    }

    @Test
    void onCreate_WhenActiveSinceIsNull_ShouldSetToNow() {
        // Arrange
        projectInnovationActors.setActiveSince(null);
        projectInnovationActors.setIsActive(null);

        // Act
        projectInnovationActors.onCreate();

        // Assert
        assertNotNull(projectInnovationActors.getActiveSince());
        assertTrue(projectInnovationActors.getIsActive());
    }

    @Test
    void onCreate_WhenIsActiveIsNull_ShouldSetToTrue() {
        // Arrange
        LocalDateTime customDate = LocalDateTime.of(2024, 1, 1, 12, 0);
        projectInnovationActors.setActiveSince(customDate);
        projectInnovationActors.setIsActive(null);

        // Act
        projectInnovationActors.onCreate();

        // Assert
        assertEquals(customDate, projectInnovationActors.getActiveSince());
        assertTrue(projectInnovationActors.getIsActive());
    }

    @Test
    void onCreate_WhenBothSet_ShouldNotChange() {
        // Arrange
        LocalDateTime customDate = LocalDateTime.of(2024, 1, 1, 12, 0);
        projectInnovationActors.setActiveSince(customDate);
        projectInnovationActors.setIsActive(false);

        // Act
        projectInnovationActors.onCreate();

        // Assert
        assertEquals(customDate, projectInnovationActors.getActiveSince());
        assertFalse(projectInnovationActors.getIsActive());
    }

    @Test
    void onUpdate_ShouldUpdateActiveSince() {
        // Arrange
        LocalDateTime originalDate = LocalDateTime.of(2024, 1, 1, 12, 0);
        projectInnovationActors.setActiveSince(originalDate);

        // Act
        projectInnovationActors.onUpdate();

        // Assert
        assertNotNull(projectInnovationActors.getActiveSince());
        assertNotEquals(originalDate, projectInnovationActors.getActiveSince());
    }

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        // Arrange
        Long id = 1L;
        Long innovationId = 100L;
        Long actorId = 200L;
        Actor actor = new Actor();
        Boolean isActive = true;
        LocalDateTime activeSince = LocalDateTime.now();
        Long createdBy = 10L;
        Long modifiedBy = 20L;
        String modificationJustification = "Justification";
        Long idPhase = 1L;
        Boolean isWomenYouth = true;
        Integer womenYouthNumber = 10;
        Integer total = 30;

        // Act
        projectInnovationActors.setId(id);
        projectInnovationActors.setInnovationId(innovationId);
        projectInnovationActors.setActorId(actorId);
        projectInnovationActors.setActor(actor);
        projectInnovationActors.setIsActive(isActive);
        projectInnovationActors.setActiveSince(activeSince);
        projectInnovationActors.setCreatedBy(createdBy);
        projectInnovationActors.setModifiedBy(modifiedBy);
        projectInnovationActors.setModificationJustification(modificationJustification);
        projectInnovationActors.setIdPhase(idPhase);
        projectInnovationActors.setIsWomenYouth(isWomenYouth);
        projectInnovationActors.setWomenYouthNumber(womenYouthNumber);
        projectInnovationActors.setTotal(total);

        // Assert
        assertEquals(id, projectInnovationActors.getId());
        assertEquals(innovationId, projectInnovationActors.getInnovationId());
        assertEquals(actorId, projectInnovationActors.getActorId());
        assertEquals(actor, projectInnovationActors.getActor());
        assertEquals(isActive, projectInnovationActors.getIsActive());
        assertEquals(activeSince, projectInnovationActors.getActiveSince());
        assertEquals(createdBy, projectInnovationActors.getCreatedBy());
        assertEquals(modifiedBy, projectInnovationActors.getModifiedBy());
        assertEquals(modificationJustification, projectInnovationActors.getModificationJustification());
        assertEquals(idPhase, projectInnovationActors.getIdPhase());
        assertEquals(isWomenYouth, projectInnovationActors.getIsWomenYouth());
        assertEquals(womenYouthNumber, projectInnovationActors.getWomenYouthNumber());
        assertEquals(total, projectInnovationActors.getTotal());
    }

    @Test
    void onCreate_WhenOnlyActiveSinceIsNull_ShouldSetActiveSince() {
        // Arrange
        projectInnovationActors.setActiveSince(null);
        projectInnovationActors.setIsActive(true);

        // Act
        projectInnovationActors.onCreate();

        // Assert
        assertNotNull(projectInnovationActors.getActiveSince());
        assertTrue(projectInnovationActors.getIsActive());
    }

    @Test
    void onCreate_WhenOnlyIsActiveIsNull_ShouldSetIsActiveToTrue() {
        // Arrange
        LocalDateTime customDate = LocalDateTime.of(2024, 1, 1, 12, 0);
        projectInnovationActors.setActiveSince(customDate);
        projectInnovationActors.setIsActive(null);

        // Act
        projectInnovationActors.onCreate();

        // Assert
        assertEquals(customDate, projectInnovationActors.getActiveSince());
        assertTrue(projectInnovationActors.getIsActive());
    }

    @Test
    void onUpdate_WhenCalledMultipleTimes_ShouldUpdateEachTime() {
        // Arrange
        LocalDateTime firstDate = LocalDateTime.of(2024, 1, 1, 12, 0);
        projectInnovationActors.setActiveSince(firstDate);

        // Act
        projectInnovationActors.onUpdate();
        LocalDateTime secondDate = projectInnovationActors.getActiveSince();
        try {
            Thread.sleep(10); // Small delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        projectInnovationActors.onUpdate();
        LocalDateTime thirdDate = projectInnovationActors.getActiveSince();

        // Assert
        assertNotNull(secondDate);
        assertNotNull(thirdDate);
        assertNotEquals(firstDate, secondDate);
        assertNotEquals(secondDate, thirdDate);
    }
}
