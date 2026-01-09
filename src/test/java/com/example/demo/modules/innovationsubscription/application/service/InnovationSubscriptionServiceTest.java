package com.example.demo.modules.innovationsubscription.application.service;

import com.example.demo.modules.innovationsubscription.domain.model.InnovationCatalogSubscription;
import com.example.demo.modules.innovationsubscription.domain.port.out.InnovationSubscriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InnovationSubscriptionServiceTest {

    @Mock
    private InnovationSubscriptionRepository subscriptionRepository;

    @InjectMocks
    private InnovationSubscriptionService service;

    private InnovationCatalogSubscription testSubscription;

    @BeforeEach
    void setUp() {
        testSubscription = new InnovationCatalogSubscription("test@example.com");
        testSubscription.setId(1L);
        testSubscription.setIsActive(true);
    }

    @Test
    void subscribe_WhenNewEmail_ShouldCreateSubscription() {
        // Arrange
        String email = "new@example.com";
        when(subscriptionRepository.findByEmailIgnoreCase("new@example.com")).thenReturn(Optional.empty());
        when(subscriptionRepository.save(any(InnovationCatalogSubscription.class))).thenAnswer(invocation -> {
            InnovationCatalogSubscription sub = invocation.getArgument(0);
            sub.setId(1L);
            return sub;
        });

        // Act
        InnovationCatalogSubscription result = service.subscribe(email);

        // Assert
        assertNotNull(result);
        assertEquals("new@example.com", result.getEmail());
        assertTrue(result.getIsActive());
        verify(subscriptionRepository).findByEmailIgnoreCase("new@example.com");
        verify(subscriptionRepository).save(any(InnovationCatalogSubscription.class));
    }

    @Test
    void subscribe_WhenEmailAlreadyActive_ShouldReturnExisting() {
        // Arrange
        String email = "test@example.com";
        when(subscriptionRepository.findByEmailIgnoreCase("test@example.com"))
            .thenReturn(Optional.of(testSubscription));

        // Act
        InnovationCatalogSubscription result = service.subscribe(email);

        // Assert
        assertNotNull(result);
        assertEquals(testSubscription.getId(), result.getId());
        assertTrue(result.getIsActive());
        verify(subscriptionRepository).findByEmailIgnoreCase("test@example.com");
        verify(subscriptionRepository, never()).save(any());
    }

    @Test
    void subscribe_WhenEmailInactive_ShouldReactivate() {
        // Arrange
        String email = "inactive@example.com";
        InnovationCatalogSubscription inactiveSubscription = new InnovationCatalogSubscription(email);
        inactiveSubscription.setId(2L);
        inactiveSubscription.setIsActive(false);
        
        when(subscriptionRepository.findByEmailIgnoreCase("inactive@example.com"))
            .thenReturn(Optional.of(inactiveSubscription));
        when(subscriptionRepository.save(any(InnovationCatalogSubscription.class)))
            .thenReturn(inactiveSubscription);

        // Act
        InnovationCatalogSubscription result = service.subscribe(email);

        // Assert
        assertNotNull(result);
        assertTrue(result.getIsActive());
        verify(subscriptionRepository).findByEmailIgnoreCase("inactive@example.com");
        verify(subscriptionRepository).save(inactiveSubscription);
    }

    @Test
    void subscribe_WhenEmailWithSpaces_ShouldNormalize() {
        // Arrange
        String email = "  TEST@EXAMPLE.COM  ";
        when(subscriptionRepository.findByEmailIgnoreCase("test@example.com")).thenReturn(Optional.empty());
        when(subscriptionRepository.save(any(InnovationCatalogSubscription.class))).thenAnswer(invocation -> {
            InnovationCatalogSubscription sub = invocation.getArgument(0);
            sub.setId(1L);
            return sub;
        });

        // Act
        InnovationCatalogSubscription result = service.subscribe(email);

        // Assert
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(subscriptionRepository).findByEmailIgnoreCase("test@example.com");
    }

    @Test
    void subscribe_WhenEmailIsNull_ShouldThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> service.subscribe(null));
        assertEquals("Email is required", exception.getMessage());
        verify(subscriptionRepository, never()).findByEmailIgnoreCase(any());
    }

    @Test
    void subscribe_WhenEmailIsEmpty_ShouldThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> service.subscribe("   "));
        assertEquals("Email is required", exception.getMessage());
    }

    @Test
    void subscribe_WhenEmailInvalid_ShouldThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> service.subscribe("invalid-email"));
        assertEquals("Email must be valid", exception.getMessage());
    }

    @Test
    void unsubscribe_WhenActiveSubscriptionExists_ShouldDeactivate() {
        // Arrange
        String email = "test@example.com";
        when(subscriptionRepository.findByEmailIgnoreCase("test@example.com"))
            .thenReturn(Optional.of(testSubscription));
        when(subscriptionRepository.save(any(InnovationCatalogSubscription.class)))
            .thenReturn(testSubscription);

        // Act
        boolean result = service.unsubscribe(email);

        // Assert
        assertTrue(result);
        assertFalse(testSubscription.getIsActive());
        verify(subscriptionRepository).findByEmailIgnoreCase("test@example.com");
        verify(subscriptionRepository).save(testSubscription);
    }

    @Test
    void unsubscribe_WhenInactiveSubscriptionExists_ShouldReturnFalse() {
        // Arrange
        String email = "inactive@example.com";
        InnovationCatalogSubscription inactiveSubscription = new InnovationCatalogSubscription(email);
        inactiveSubscription.setIsActive(false);
        
        when(subscriptionRepository.findByEmailIgnoreCase("inactive@example.com"))
            .thenReturn(Optional.of(inactiveSubscription));

        // Act
        boolean result = service.unsubscribe(email);

        // Assert
        assertFalse(result);
        verify(subscriptionRepository).findByEmailIgnoreCase("inactive@example.com");
        verify(subscriptionRepository, never()).save(any());
    }

    @Test
    void unsubscribe_WhenSubscriptionNotExists_ShouldReturnFalse() {
        // Arrange
        String email = "nonexistent@example.com";
        when(subscriptionRepository.findByEmailIgnoreCase("nonexistent@example.com"))
            .thenReturn(Optional.empty());

        // Act
        boolean result = service.unsubscribe(email);

        // Assert
        assertFalse(result);
        verify(subscriptionRepository).findByEmailIgnoreCase("nonexistent@example.com");
        verify(subscriptionRepository, never()).save(any());
    }

    @Test
    void unsubscribe_WhenEmailWithSpaces_ShouldNormalize() {
        // Arrange
        String email = "  TEST@EXAMPLE.COM  ";
        when(subscriptionRepository.findByEmailIgnoreCase("test@example.com"))
            .thenReturn(Optional.of(testSubscription));
        when(subscriptionRepository.save(any(InnovationCatalogSubscription.class)))
            .thenReturn(testSubscription);

        // Act
        boolean result = service.unsubscribe(email);

        // Assert
        assertTrue(result);
        verify(subscriptionRepository).findByEmailIgnoreCase("test@example.com");
    }

    @Test
    void unsubscribe_WhenEmailIsNull_ShouldThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> service.unsubscribe(null));
        assertEquals("Email is required", exception.getMessage());
    }

    @Test
    void unsubscribe_WhenEmailInvalid_ShouldThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> service.unsubscribe("invalid-email"));
        assertEquals("Email must be valid", exception.getMessage());
    }
}
