package com.example.demo.modules.innovationsubscription.adapters.web.controller;

import com.example.demo.modules.innovationsubscription.adapters.web.dto.CreateSubscriptionRequest;
import com.example.demo.modules.innovationsubscription.adapters.web.dto.SubscriptionResponse;
import com.example.demo.modules.innovationsubscription.domain.model.InnovationCatalogSubscription;
import com.example.demo.modules.innovationsubscription.domain.port.in.InnovationSubscriptionUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InnovationSubscriptionControllerTest {

    @Mock
    private InnovationSubscriptionUseCase subscriptionUseCase;

    @InjectMocks
    private InnovationSubscriptionController controller;

    private InnovationCatalogSubscription testSubscription;

    @BeforeEach
    void setUp() {
        testSubscription = new InnovationCatalogSubscription("test@example.com");
        testSubscription.setId(1L);
        testSubscription.setIsActive(true);
    }

    @Test
    void subscribe_WhenValidEmail_ShouldReturnOk() {
        // Arrange
        CreateSubscriptionRequest request = new CreateSubscriptionRequest("test@example.com");
        when(subscriptionUseCase.subscribe("test@example.com")).thenReturn(testSubscription);

        // Act
        ResponseEntity<SubscriptionResponse> result = controller.subscribe(request);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(subscriptionUseCase).subscribe("test@example.com");
    }

    @Test
    void subscribe_WhenInvalidEmail_ShouldReturnBadRequest() {
        // Arrange
        CreateSubscriptionRequest request = new CreateSubscriptionRequest("invalid-email");
        when(subscriptionUseCase.subscribe("invalid-email"))
            .thenThrow(new IllegalArgumentException("Email must be valid"));

        // Act + Assert
        IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> controller.subscribe(request)
        );
        assertEquals("Email must be valid", thrown.getMessage());
        verify(subscriptionUseCase).subscribe("invalid-email");
    }

    @Test
    void unsubscribe_WhenValidEmail_ShouldReturnNoContent() {
        // Arrange
        String email = "test@example.com";
        when(subscriptionUseCase.unsubscribe(email)).thenReturn(true);

        // Act
        ResponseEntity<?> result = controller.unsubscribe(email);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(subscriptionUseCase).unsubscribe(email);
    }

    @Test
    void unsubscribe_WhenNotSubscribed_ShouldReturnNotFound() {
        // Arrange
        String email = "notfound@example.com";
        when(subscriptionUseCase.unsubscribe(email)).thenReturn(false);

        // Act
        ResponseEntity<?> result = controller.unsubscribe(email);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
}
