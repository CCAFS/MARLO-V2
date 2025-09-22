package com.example.demo.platform.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

@Component
public class ApiGatewayBasePathFilter extends OncePerRequestFilter {
    private static final String X_FORWARDED_PREFIX = "X-Forwarded-Prefix";
    private final String basePath;

    public ApiGatewayBasePathFilter(@Value("${app.base-path:}") String basePath) {
        if (StringUtils.hasText(basePath)) {
            this.basePath = basePath.startsWith("/") ? basePath : "/" + basePath;
        } else {
            this.basePath = null;
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (basePath == null || hasForwardedPrefix(request) || matchesContextPath(request)) {
            filterChain.doFilter(request, response);
            return;
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

    private boolean matchesContextPath(HttpServletRequest request) {
        if (basePath == null) {
            return false;
        }
        String contextPath = request.getContextPath();
        return StringUtils.hasText(contextPath) && basePath.equals(contextPath);
    }
}
