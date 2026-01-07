package com.example.demo.sharedkernel.application;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PersonalizedMessageGenerator {
    private static final Logger logger = LoggerFactory.getLogger(PersonalizedMessageGenerator.class);

    public String generate(String template, String name) {
        Objects.requireNonNull(template, "template must not be null");
        Objects.requireNonNull(name, "name must not be null");
        String safeTemplate = sanitizeForLog(template);
        String safeName = sanitizeForLog(name);
        logger.info("Generating message using template '{}' for name: {}", safeTemplate, safeName);
        return String.format(template, name);
    }

    private String sanitizeForLog(String input) {
        // Replace newline characters to avoid log forging.
        return input.replaceAll("[\\r\\n]", "_");
    }
}
