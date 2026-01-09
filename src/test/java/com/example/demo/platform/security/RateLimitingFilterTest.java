package com.example.demo.platform.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RateLimitingFilterTest {

    @Mock
    private RateLimitingProperties properties;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private RateLimitingFilter filter;

    @BeforeEach
    void setUp() {
        when(properties.isEnabled()).thenReturn(true);
    }

    @Test
    void doFilterInternal_WhenDisabled_ShouldSkipFilter() throws Exception {
        // Arrange
        when(properties.isEnabled()).thenReturn(false);

        // Act
        filter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        verify(response, never()).setStatus(anyInt());
    }

    @Test
    void doFilterInternal_WhenOptionsRequest_ShouldSkipFilter() throws Exception {
        // Arrange
        when(request.getMethod()).thenReturn("OPTIONS");

        // Act
        filter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        verify(response, never()).setStatus(anyInt());
    }

    @Test
    void doFilterInternal_WhenSkipPath_ShouldSkipFilter() throws Exception {
        // Arrange
        when(request.getRequestURI()).thenReturn("/actuator/health");
        when(properties.getSkipPaths()).thenReturn(java.util.List.of("/actuator/health"));

        // Act
        filter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        verify(response, never()).setStatus(anyInt());
    }

    @Test
    void doFilterInternal_WhenWithinLimit_ShouldAllowRequest() throws Exception {
        // Arrange
        when(request.getRequestURI()).thenReturn("/api/innovations");
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");
        when(properties.getSkipPaths()).thenReturn(java.util.List.of());
        when(properties.getCapacity()).thenReturn(100);
        when(properties.getRefillTokens()).thenReturn(100);
        when(properties.getRefillPeriod()).thenReturn(java.time.Duration.ofMinutes(1));

        // Act
        filter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        verify(response, never()).setStatus(eq(429));
    }
}
