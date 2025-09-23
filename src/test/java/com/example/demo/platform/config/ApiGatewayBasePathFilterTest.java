package com.example.demo.platform.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
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
        request.addHeader("Existing-Header", "value");
        AtomicReference<HttpServletRequest> requestSeenByChain = new AtomicReference<>();
        FilterChain chain = (servletRequest, servletResponse) -> requestSeenByChain.set((HttpServletRequest) servletRequest);

        filter.doFilter(request, response, chain);

        HttpServletRequest wrapped = requestSeenByChain.get();
        assertThat(wrapped).isNotNull();
        assertThat(wrapped.getHeader(X_FORWARDED_PREFIX)).isEqualTo("/api");

        Enumeration<String> headerValues = wrapped.getHeaders(X_FORWARDED_PREFIX);
        assertThat(Collections.list(headerValues)).containsExactly("/api");

        Enumeration<String> existingValues = wrapped.getHeaders("Existing-Header");
        assertThat(Collections.list(existingValues)).containsExactly("value");
        assertThat(wrapped.getHeader("Existing-Header")).isEqualTo("value");

        Enumeration<String> headerNames = wrapped.getHeaderNames();
        assertThat(Collections.list(headerNames)).containsExactlyInAnyOrder(X_FORWARDED_PREFIX, "Existing-Header");
    }

    @Test
    void keepsExistingForwardedPrefixHeader() throws ServletException, IOException {
        ApiGatewayBasePathFilter filter = new ApiGatewayBasePathFilter("api");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(X_FORWARDED_PREFIX, "/already-set");
        MockFilterChain chain = new MockFilterChain();

        filter.doFilter(request, response, chain);

        HttpServletRequest processed = (HttpServletRequest) chain.getRequest();
        assertThat(processed).isSameAs(request);
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

    @Test
    void normalizesBasePathThatAlreadyStartsWithDelimiter() throws ServletException, IOException {
        ApiGatewayBasePathFilter filter = new ApiGatewayBasePathFilter("/api");
        MockHttpServletRequest request = new MockHttpServletRequest();
        AtomicReference<HttpServletRequest> requestSeenByChain = new AtomicReference<>();
        FilterChain chain = (servletRequest, servletResponse) -> requestSeenByChain.set((HttpServletRequest) servletRequest);

        filter.doFilter(request, response, chain);

        HttpServletRequest wrapped = requestSeenByChain.get();
        assertThat(wrapped.getHeader(X_FORWARDED_PREFIX)).isEqualTo("/api");
    }

    @Test
    void doesNotDuplicateForwardedPrefixHeaderWhenPresentWithNullValue() throws ServletException, IOException {
        ApiGatewayBasePathFilter filter = new ApiGatewayBasePathFilter("api");
        MockHttpServletRequest baseRequest = new MockHttpServletRequest();
        baseRequest.addHeader("Another", "value");
        HttpServletRequest request = new HttpServletRequestWrapper(baseRequest) {
            @Override
            public Enumeration<String> getHeaders(String name) {
                if (X_FORWARDED_PREFIX.equalsIgnoreCase(name)) {
                    return Collections.enumeration(Collections.singletonList((String) null));
                }
                return super.getHeaders(name);
            }

            @Override
            public String getHeader(String name) {
                if (X_FORWARDED_PREFIX.equalsIgnoreCase(name)) {
                    return null;
                }
                return super.getHeader(name);
            }

            @Override
            public Enumeration<String> getHeaderNames() {
                List<String> names = Collections.list(super.getHeaderNames());
                if (names.stream().noneMatch(X_FORWARDED_PREFIX::equalsIgnoreCase)) {
                    names.add(X_FORWARDED_PREFIX);
                }
                return Collections.enumeration(names);
            }
        };
        AtomicReference<HttpServletRequest> requestSeenByChain = new AtomicReference<>();
        FilterChain chain = (servletRequest, servletResponse) -> requestSeenByChain.set((HttpServletRequest) servletRequest);

        filter.doFilter(request, response, chain);

        HttpServletRequest wrapped = requestSeenByChain.get();
        assertThat(wrapped.getHeader(X_FORWARDED_PREFIX)).isEqualTo("/api");

        List<String> headerNames = Collections.list(wrapped.getHeaderNames());
        assertThat(Collections.frequency(headerNames, X_FORWARDED_PREFIX)).isEqualTo(1);
        assertThat(headerNames).contains("Another");
        assertThat(Collections.list(wrapped.getHeaders("Another"))).containsExactly("value");
    }
}
