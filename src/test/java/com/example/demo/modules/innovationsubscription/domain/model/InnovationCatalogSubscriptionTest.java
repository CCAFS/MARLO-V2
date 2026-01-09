package com.example.demo.modules.innovationsubscription.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InnovationCatalogSubscriptionTest {

    private InnovationCatalogSubscription subscription;

    @BeforeEach
    void setUp() {
        subscription = new InnovationCatalogSubscription();
    }

    @Test
    void constructor_WithNoArgsConstructor_ShouldCreateEmpty() {
        // Act
        InnovationCatalogSubscription sub = new InnovationCatalogSubscription();

        // Assert
        assertNotNull(sub);
        assertNull(sub.getId());
        assertNull(sub.getEmail());
    }

    @Test
    void constructor_WithEmail_ShouldSetValues() {
        // Act
        InnovationCatalogSubscription sub = new InnovationCatalogSubscription("test@example.com");

        // Assert
        assertEquals("test@example.com", sub.getEmail());
        assertTrue(sub.getIsActive());
        assertNotNull(sub.getActiveSince());
    }

    @Test
    void onCreate_WhenActiveSinceIsNull_ShouldSetToNow() {
        // Arrange
        subscription.setActiveSince(null);
        subscription.setIsActive(null);

        // Act
        subscription.onCreate();

        // Assert
        assertNotNull(subscription.getActiveSince());
        assertTrue(subscription.getIsActive());
    }

    @Test
    void onCreate_WhenIsActiveIsNull_ShouldSetToTrue() {
        // Arrange
        subscription.setActiveSince(LocalDateTime.now());
        subscription.setIsActive(null);

        // Act
        subscription.onCreate();

        // Assert
        assertTrue(subscription.getIsActive());
    }

    @Test
    void setIsActive_ShouldUpdateActiveSince() {
        // Arrange
        LocalDateTime originalDate = LocalDateTime.now().minusDays(1);
        subscription.setActiveSince(originalDate);

        // Act
        subscription.setIsActive(false);

        // Assert
        assertFalse(subscription.getIsActive());
        assertNotEquals(originalDate, subscription.getActiveSince());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Act
        subscription.setId(1L);
        subscription.setEmail("test@example.com");
        subscription.setIsActive(true);
        subscription.setActiveSince(LocalDateTime.now());
        subscription.setModificationJustification("Justification");

        // Assert
        assertEquals(1L, subscription.getId());
        assertEquals("test@example.com", subscription.getEmail());
        assertTrue(subscription.getIsActive());
        assertNotNull(subscription.getActiveSince());
        assertEquals("Justification", subscription.getModificationJustification());
    }
}
