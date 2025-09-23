package com.example.demo.platform.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

class ApiGatewayBasePathFilterTest {

    private static final String X_FORWARDED_PREFIX = "X-Forwarded-Prefix";

    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        response = new MockHttpServletResponse();
    }

    @Test
    void addsForwardedPrefixHeaderWhenMissing() throws ServletException, IOException {
        ApiGatewayBasePathFilter filter = new ApiGatewayBasePathFilter("api");
        MockHttpServletRequest request = new MockHttpServletRequest();
        AtomicReference<HttpServletRequest> requestSeenByChain = new AtomicReference<>();
        FilterChain chain = (servletRequest, servletResponse) -> requestSeenByChain.set((HttpServletRequest) servletRequest);

        filter.doFilter(request, response, chain);

        HttpServletRequest wrapped = requestSeenByChain.get();
        assertThat(wrapped).isNotNull();
        assertThat(wrapped.getHeader(X_FORWARDED_PREFIX)).isEqualTo("/api");

        Enumeration<String> headerNames = wrapped.getHeaderNames();
        assertThat(java.util.Collections.list(headerNames)).contains(X_FORWARDED_PREFIX);
    }

    @Test
    void keepsExistingForwardedPrefixHeader() throws ServletException, IOException {
        ApiGatewayBasePathFilter filter = new ApiGatewayBasePathFilter("api");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(X_FORWARDED_PREFIX, "/already-set");
        MockFilterChain chain = new MockFilterChain();

        filter.doFilter(request, response, chain);

        HttpServletRequest processed = (HttpServletRequest) chain.getRequest();
        assertThat(processed.getHeader(X_FORWARDED_PREFIX)).isEqualTo("/already-set");
    }

    @Test
    void skipsWrappingWhenBasePathIsBlank() throws ServletException, IOException {
        ApiGatewayBasePathFilter filter = new ApiGatewayBasePathFilter(" ");
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockFilterChain chain = new MockFilterChain();

        filter.doFilter(request, response, chain);

        assertThat(chain.getRequest()).isSameAs(request);
    }
}

