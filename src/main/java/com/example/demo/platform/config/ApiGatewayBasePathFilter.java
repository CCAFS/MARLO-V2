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
import org.springframework.util.AntPathMatcher;
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
    private static final String PATH_DELIMITER = AntPathMatcher.DEFAULT_PATH_SEPARATOR;
    private final String basePath;

    public ApiGatewayBasePathFilter(@Value("${app.base-path:}") String basePath) {
        if (StringUtils.hasText(basePath)) {
            this.basePath = basePath.startsWith(PATH_DELIMITER) ? basePath : PATH_DELIMITER + basePath;
        } else {
            this.basePath = null;
        }
        log.info("ApiGatewayBasePathFilter initialized with basePath: {}", this.basePath);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (!StringUtils.hasText(basePath)) {
            filterChain.doFilter(request, response);
            return;
        }

        HttpServletRequest requestToUse = request;
        if (!hasForwardedPrefix(request)) {
            requestToUse = wrapRequest(request);
        }

        filterChain.doFilter(requestToUse, response);
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
                if (headerNames.stream().noneMatch(X_FORWARDED_PREFIX::equalsIgnoreCase)) {
                    headerNames.add(X_FORWARDED_PREFIX);
                }
                return Collections.enumeration(headerNames);
            }
        };
    }

    private boolean hasForwardedPrefix(HttpServletRequest request) {
        Enumeration<String> headerValues = request.getHeaders(X_FORWARDED_PREFIX);
        return headerValues != null && headerValues.hasMoreElements() && headerValues.nextElement() != null;
    }
}



