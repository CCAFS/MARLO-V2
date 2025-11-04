package com.example.demo.platform.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Integration tests validating the security headers and rate limiter behaviour.
 */
@WebMvcTest(SecurityIntegrationTest.TestController.class)
@Import({SecurityConfig.class, SecurityIntegrationTest.TestConfig.class})
class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RateLimitingProperties rateLimitingProperties;

    @Test
    @DisplayName("Helmet-like headers are present on responses")
    void shouldIncludeSecurityHeadersOnSecureRequest() throws Exception {
        mockMvc.perform(get("/test/public").secure(true))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Security-Policy", containsString("default-src 'self'")))
                .andExpect(header().string("Strict-Transport-Security", containsString("max-age")))
                .andExpect(header().string("X-Frame-Options", "SAMEORIGIN"))
                .andExpect(header().string("X-Content-Type-Options", "nosniff"))
                .andExpect(header().string("X-XSS-Protection", "1; mode=block"))
                .andExpect(header().string("Referrer-Policy", "same-origin"))
                .andExpect(header().string("Permissions-Policy", containsString("geolocation")));
    }

    @Test
    @DisplayName("Rate limiter allows limited requests and then blocks with 429")
    void shouldEnforceRateLimit() throws Exception {
        int capacity = rateLimitingProperties.getCapacity();

        for (int i = 0; i < capacity; i++) {
            MvcResult result = mockMvc.perform(get("/test/public").secure(true))
                    .andExpect(status().isOk())
                    .andReturn();

            String remaining = result.getResponse().getHeader("X-RateLimit-Remaining");
            assertThat(remaining).isEqualTo(String.valueOf(capacity - i - 1));
        }

        MvcResult blocked = mockMvc.perform(get("/test/public").secure(true))
                .andExpect(status().isTooManyRequests())
                .andExpect(header().string(HttpHeaders.RETRY_AFTER, notNullValue()))
                .andReturn();

        assertThat(blocked.getResponse().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);
        assertThat(blocked.getResponse().getContentAsString()).contains("Rate limit exceeded");
    }

    @TestConfiguration
    @EnableConfigurationProperties(RateLimitingProperties.class)
    static class TestConfig {

        @Bean
        RateLimitingProperties rateLimitingProperties() {
            RateLimitingProperties properties = new RateLimitingProperties();
            properties.setCapacity(3);
            properties.setRefillTokens(3);
            properties.setRefillPeriod(Duration.ofHours(1));
            properties.setSkipPaths(new ArrayList<>(List.of("/actuator/**")));
            return properties;
        }

        @Bean
        RateLimitingFilter rateLimitingFilter(RateLimitingProperties properties) {
            return new RateLimitingFilter(properties);
        }
    }

    @RestController
    static class TestController {

        @GetMapping("/test/public")
        String publicEndpoint() {
            return "ok";
        }
    }
}
