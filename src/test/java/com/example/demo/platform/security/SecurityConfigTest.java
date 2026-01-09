package com.example.demo.platform.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityConfigTest {

    private RateLimitingFilter rateLimitingFilter;
    private SecurityConfig securityConfig;

    @BeforeEach
    void setUp() {
        rateLimitingFilter = mock(RateLimitingFilter.class);
        securityConfig = new SecurityConfig(rateLimitingFilter);
    }

    @Test
    void constructor_ShouldSetRateLimitingFilter() {
        // Act
        SecurityConfig config = new SecurityConfig(rateLimitingFilter);

        // Assert
        assertNotNull(config);
        RateLimitingFilter filter = (RateLimitingFilter) ReflectionTestUtils.getField(config, "rateLimitingFilter");
        assertEquals(rateLimitingFilter, filter);
    }
}
