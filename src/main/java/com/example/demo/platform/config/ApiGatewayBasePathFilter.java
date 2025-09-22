package com.example.demo.platform.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiGatewayBasePathFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(ApiGatewayBasePathFilter.class);
    private static final String X_FORWARDED_PREFIX = "X-Forwarded-Prefix";
    private final String basePath;

    public ApiGatewayBasePathFilter(@Value("${app.base-path:}") String basePath) {
        String rawBasePath = System.getenv("BASE_PATH");
        String activeProfile = System.getenv("SPRING_PROFILES_ACTIVE");
        if (StringUtils.hasText(basePath)) {
            this.basePath = basePath.startsWith("/") ? basePath : "/" + basePath;
        } else {
            this.basePath = null;
        }
        log.info("ApiGatewayBasePathFilter initialized: property basePath='{}', env BASE_PATH='{}', SPRING_PROFILES_ACTIVE='{}'", this.basePath, rawBasePath, activeProfile);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (log.isDebugEnabled()) {
            log.debug("Incoming request uri='{}', contextPath='{}', basePath='{}', forwardedPrefix='{}'", request.getRequestURI(), request.getContextPath(), basePath, request.getHeader(X_FORWARDED_PREFIX));
        }

        if (basePath == null || hasForwardedPrefix(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (log.isDebugEnabled()) {
            log.debug("Injecting {} header with basePath '{}'", X_FORWARDED_PREFIX, basePath);
        }

        HttpServletRequest wrapper = new HttpServletRequestWrapper(request) {
            @Override
            public String getHeader(String name) {
                if (X_FORWARDED_PREFIX.equalsIgnoreCase(name)) {
                    return basePath;
                }
                return super.getHeader(name);
            }

            @Override
            public Enumeration<String> getHeaders(String name) {
                if (X_FORWARDED_PREFIX.equalsIgnoreCase(name)) {
                    return Collections.enumeration(Collections.singleton(basePath));
                }
                return super.getHeaders(name);
            }

            @Override
            public Enumeration<String> getHeaderNames() {
                List<String> headerNames = new ArrayList<>();
                Enumeration<String> existing = super.getHeaderNames();
                while (existing.hasMoreElements()) {
                    headerNames.add(existing.nextElement());
                }
                if (headerNames.stream().noneMatch(h -> X_FORWARDED_PREFIX.equalsIgnoreCase(h))) {
                    headerNames.add(X_FORWARDED_PREFIX);
                }
                return Collections.enumeration(headerNames);
            }
        };

        filterChain.doFilter(wrapper, response);
    }

    private boolean hasForwardedPrefix(HttpServletRequest request) {
        Enumeration<String> headerValues = request.getHeaders(X_FORWARDED_PREFIX);
        return headerValues != null && headerValues.hasMoreElements() &&
                headerValues.nextElement() != null;
    }
}
