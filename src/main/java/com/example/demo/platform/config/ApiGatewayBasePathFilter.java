package com.example.demo.platform.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
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
        if (!StringUtils.hasText(basePath)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (log.isDebugEnabled()) {
            log.debug("Incoming request uri='{}', contextPath='{}', forwardedPrefix='{}'", request.getRequestURI(), request.getContextPath(), request.getHeader(X_FORWARDED_PREFIX));
        }

        HttpServletRequest requestToUse = request;
        if (!hasForwardedPrefix(request)) {
            if (log.isDebugEnabled()) {
                log.debug("Injecting {} header with basePath '{}'", X_FORWARDED_PREFIX, basePath);
            }
            requestToUse = wrapRequest(request);
        }

        HttpServletResponse responseToUse = wrapResponse(response);
        filterChain.doFilter(requestToUse, responseToUse);
    }

    private HttpServletRequest wrapRequest(HttpServletRequest request) {
        return new HttpServletRequestWrapper(request) {
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
    }

    private HttpServletResponse wrapResponse(HttpServletResponse response) {
        return new HttpServletResponseWrapper(response) {
            @Override
            public void sendRedirect(String location) throws IOException {
                String adjustedLocation = adjustLocation(location);
                if (log.isDebugEnabled()) {
                    log.debug("sendRedirect called with location='{}', adjusted='{}'", location, adjustedLocation);
                }
                super.sendRedirect(adjustedLocation);
            }
        };
    }

    private String adjustLocation(String location) {
        if (!StringUtils.hasText(location) || location.startsWith("http://") || location.startsWith("https://")) {
            return location;
        }

        String normalizedBasePath = basePath;
        if ("/".equals(normalizedBasePath)) {
            normalizedBasePath = "";
        }

        String normalizedLocation = location;
        if (!location.startsWith("/")) {
            normalizedLocation = "/" + location;
        }

        if (normalizedLocation.startsWith(normalizedBasePath + "/") || normalizedLocation.equals(normalizedBasePath)) {
            return normalizedLocation;
        }

        return normalizedBasePath + normalizedLocation;
    }

    private boolean hasForwardedPrefix(HttpServletRequest request) {
        Enumeration<String> headerValues = request.getHeaders(X_FORWARDED_PREFIX);
        return headerValues != null && headerValues.hasMoreElements() && headerValues.nextElement() != null;
    }
}
