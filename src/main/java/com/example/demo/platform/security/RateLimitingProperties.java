package com.example.demo.platform.security;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * External configuration for simple in-memory rate limiting.
 */
@ConfigurationProperties(prefix = "security.rate-limit")
public class RateLimitingProperties {

    private boolean enabled = true;
    private int capacity = 100;
    private int refillTokens = 100;
    private Duration refillPeriod = Duration.ofMinutes(1);
    private List<String> skipPaths = new ArrayList<>(Arrays.asList("/actuator/health", "/actuator/info"));

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRefillTokens() {
        return refillTokens;
    }

    public void setRefillTokens(int refillTokens) {
        this.refillTokens = refillTokens;
    }

    public Duration getRefillPeriod() {
        return refillPeriod;
    }

    public void setRefillPeriod(Duration refillPeriod) {
        this.refillPeriod = refillPeriod;
    }

    public List<String> getSkipPaths() {
        return skipPaths;
    }

    public void setSkipPaths(List<String> skipPaths) {
        this.skipPaths = skipPaths;
    }
}
