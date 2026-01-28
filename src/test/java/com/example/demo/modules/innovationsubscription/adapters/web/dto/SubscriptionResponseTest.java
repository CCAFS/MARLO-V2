package com.example.demo.modules.innovationsubscription.adapters.web.dto;

import com.example.demo.modules.innovationsubscription.domain.model.InnovationCatalogSubscription;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionResponseTest {

    @Test
    void constructor_WithNoArgs_ShouldCreateEmptyObject() {
        // Act
        SubscriptionResponse response = new SubscriptionResponse();

        // Assert
        assertNotNull(response);
        assertNull(response.getId());
        assertNull(response.getEmail());
        assertNull(response.getIsActive());
        assertNull(response.getActiveSince());
    }

    @Test
    void constructor_WithAllArgs_ShouldSetAllFields() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        // Act
        SubscriptionResponse response = new SubscriptionResponse(1L, "test@example.com", true, now);

        // Assert
        assertEquals(1L, response.getId());
        assertEquals("test@example.com", response.getEmail());
        assertTrue(response.getIsActive());
        assertEquals(now, response.getActiveSince());
    }

    @Test
    void settersAndGetters_Id_ShouldWorkCorrectly() {
        // Arrange
        SubscriptionResponse response = new SubscriptionResponse();

        // Act
        response.setId(1L);

        // Assert
        assertEquals(1L, response.getId());
    }

    @Test
    void settersAndGetters_Email_ShouldWorkCorrectly() {
        // Arrange
        SubscriptionResponse response = new SubscriptionResponse();

        // Act
        response.setEmail("user@test.com");

        // Assert
        assertEquals("user@test.com", response.getEmail());
    }

    @Test
    void settersAndGetters_IsActive_ShouldWorkCorrectly() {
        // Arrange
        SubscriptionResponse response = new SubscriptionResponse();

        // Act
        response.setIsActive(true);

        // Assert
        assertTrue(response.getIsActive());
    }

    @Test
    void settersAndGetters_IsActiveFalse_ShouldWorkCorrectly() {
        // Arrange
        SubscriptionResponse response = new SubscriptionResponse();

        // Act
        response.setIsActive(false);

        // Assert
        assertFalse(response.getIsActive());
    }

    @Test
    void settersAndGetters_ActiveSince_ShouldWorkCorrectly() {
        // Arrange
        SubscriptionResponse response = new SubscriptionResponse();
        LocalDateTime now = LocalDateTime.now();

        // Act
        response.setActiveSince(now);

        // Assert
        assertEquals(now, response.getActiveSince());
    }

    @Test
    void fromEntity_WithValidEntity_ShouldMapAllFields() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        InnovationCatalogSubscription subscription = new InnovationCatalogSubscription();
        subscription.setId(1L);
        subscription.setEmail("entity@example.com");
        subscription.setIsActive(true);
        subscription.setActiveSince(now);

        // Act
        SubscriptionResponse response = SubscriptionResponse.fromEntity(subscription);

        // Assert
        assertEquals(1L, response.getId());
        assertEquals("entity@example.com", response.getEmail());
        assertTrue(response.getIsActive());
        assertEquals(now, response.getActiveSince());
    }

    @Test
    void fromEntity_WithInactiveSubscription_ShouldMapCorrectly() {
        // Arrange
        InnovationCatalogSubscription subscription = new InnovationCatalogSubscription();
        subscription.setId(2L);
        subscription.setEmail("inactive@example.com");
        subscription.setIsActive(false);
        subscription.setActiveSince(LocalDateTime.of(2024, 1, 1, 0, 0));

        // Act
        SubscriptionResponse response = SubscriptionResponse.fromEntity(subscription);

        // Assert
        assertEquals(2L, response.getId());
        assertEquals("inactive@example.com", response.getEmail());
        assertFalse(response.getIsActive());
    }

    @Test
    void setNullValues_ShouldAcceptNulls() {
        // Arrange
        SubscriptionResponse response = new SubscriptionResponse(1L, "test@test.com", true, LocalDateTime.now());

        // Act
        response.setId(null);
        response.setEmail(null);
        response.setIsActive(null);
        response.setActiveSince(null);

        // Assert
        assertNull(response.getId());
        assertNull(response.getEmail());
        assertNull(response.getIsActive());
        assertNull(response.getActiveSince());
    }

    @Test
    void constructor_WithEmptyEmail_ShouldWork() {
        // Act
        SubscriptionResponse response = new SubscriptionResponse(0L, "", false, LocalDateTime.now());

        // Assert
        assertEquals(0L, response.getId());
        assertEquals("", response.getEmail());
        assertFalse(response.getIsActive());
    }
}
