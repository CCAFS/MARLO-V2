package com.example.demo.platform.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bucket4j.Bandwidth;
import com.bucket4j.Bucket;
import com.bucket4j.Bucket4j;
import com.bucket4j.ConsumptionProbe;
import com.bucket4j.Refill;

/**
 * Simple in-memory rate limiting filter based on client identifier.
 */
@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private final RateLimitingProperties properties;
    private final Map<String, Bucket> bucketCache = new ConcurrentHashMap<>();
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public RateLimitingFilter(RateLimitingProperties properties) {
        this.properties = properties;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        if (!properties.isEnabled() || shouldSkip(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        Bucket bucket = resolveBucket(request);
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);

        if (probe.isConsumed()) {
            response.setHeader("X-RateLimit-Remaining", String.valueOf(probe.getRemainingTokens()));
            filterChain.doFilter(request, response);
            return;
        }

        long waitSeconds = Duration.ofNanos(probe.getNanosToWaitForRefill()).toSeconds();
        waitSeconds = waitSeconds <= 0 ? 1 : waitSeconds;

        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setHeader(HttpHeaders.RETRY_AFTER, String.valueOf(waitSeconds));

        String body = String.format(
                "{\"status\":429,\"error\":\"Too Many Requests\",\"message\":\"Rate limit exceeded. Try again in %d seconds.\"}",
                waitSeconds);

        response.getWriter().write(body);
        response.getWriter().flush();
    }

    private boolean shouldSkip(HttpServletRequest request) {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        String requestUri = request.getRequestURI();
        var skipPaths = properties.getSkipPaths();
        if (skipPaths == null || skipPaths.isEmpty()) {
            return false;
        }
        return skipPaths.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, requestUri));
    }

    private Bucket resolveBucket(HttpServletRequest request) {
        String clientId = resolveClientIdentifier(request);
        return bucketCache.computeIfAbsent(clientId, key -> {
            Refill refill = Refill.greedy(properties.getRefillTokens(), properties.getRefillPeriod());
            Bandwidth limit = Bandwidth.classic(properties.getCapacity(), refill);
            return Bucket4j.builder().addLimit(limit).build();
        });
    }

    private String resolveClientIdentifier(HttpServletRequest request) {
        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (StringUtils.hasText(forwardedFor)) {
            int commaIndex = forwardedFor.indexOf(',');
            return commaIndex > 0 ? forwardedFor.substring(0, commaIndex).trim() : forwardedFor.trim();
        }
        String forwarded = request.getHeader("Forwarded");
        if (StringUtils.hasText(forwarded)) {
            int start = forwarded.toLowerCase().indexOf("for=");
            if (start >= 0) {
                int end = forwarded.indexOf(';', start);
                String value = end > start ? forwarded.substring(start + 4, end) : forwarded.substring(start + 4);
                return value.replace("\"", "");
            }
        }
        return request.getRemoteAddr();
    }
}
