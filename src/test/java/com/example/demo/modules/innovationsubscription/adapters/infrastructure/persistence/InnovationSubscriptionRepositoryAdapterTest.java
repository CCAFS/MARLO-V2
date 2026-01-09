package com.example.demo.modules.innovationsubscription.adapters.infrastructure.persistence;

import com.example.demo.modules.innovationsubscription.domain.model.InnovationCatalogSubscription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InnovationSubscriptionRepositoryAdapterTest {

    private InnovationCatalogSubscriptionJpaRepository jpaRepository;
    private InnovationSubscriptionRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        jpaRepository = mock(InnovationCatalogSubscriptionJpaRepository.class);
        adapter = new InnovationSubscriptionRepositoryAdapter(jpaRepository);
    }

    @Test
    void save_ShouldDelegateToJpaRepository() {
        // Arrange
        InnovationCatalogSubscription subscription = new InnovationCatalogSubscription();
        subscription.setEmail("test@example.com");
        when(jpaRepository.save(subscription)).thenReturn(subscription);

        // Act
        InnovationCatalogSubscription result = adapter.save(subscription);

        // Assert
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(jpaRepository).save(subscription);
    }

    @Test
    void findByEmailIgnoreCase_WhenExists_ShouldReturnOptional() {
        // Arrange
        InnovationCatalogSubscription subscription = new InnovationCatalogSubscription();
        subscription.setEmail("test@example.com");
        when(jpaRepository.findByEmailIgnoreCase("test@example.com"))
            .thenReturn(Optional.of(subscription));

        // Act
        Optional<InnovationCatalogSubscription> result = adapter.findByEmailIgnoreCase("test@example.com");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().getEmail());
        verify(jpaRepository).findByEmailIgnoreCase("test@example.com");
    }

    @Test
    void findByEmailIgnoreCase_WhenNotExists_ShouldReturnEmpty() {
        // Arrange
        when(jpaRepository.findByEmailIgnoreCase("nonexistent@example.com"))
            .thenReturn(Optional.empty());

        // Act
        Optional<InnovationCatalogSubscription> result = adapter.findByEmailIgnoreCase("nonexistent@example.com");

        // Assert
        assertFalse(result.isPresent());
        verify(jpaRepository).findByEmailIgnoreCase("nonexistent@example.com");
    }
}
