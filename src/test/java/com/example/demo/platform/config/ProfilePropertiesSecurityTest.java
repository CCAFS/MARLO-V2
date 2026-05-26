package com.example.demo.platform.config;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProfilePropertiesSecurityTest {

    private static final String FALSE_VALUE = "false";

    @Test
    void productionPropertiesShouldUseSafePoolAndExternalizedSecrets() throws IOException {
        Properties properties = loadProfile("application-prod.properties");

        assertEquals("${DB_USERNAME}", properties.getProperty("spring.datasource.username"));
        assertEquals("${DB_PASSWORD}", properties.getProperty("spring.datasource.password"));
        assertEquals("${MODERATION_OPENAI_API_KEY:}", properties.getProperty("moderation.comment.openai.api-key"));
        assertEquals(FALSE_VALUE, properties.getProperty("spring.jpa.show-sql"));
        assertEquals(FALSE_VALUE, properties.getProperty("spring.jpa.properties.hibernate.format_sql"));
        assertEquals(FALSE_VALUE, properties.getProperty("spring.jpa.properties.hibernate.use_sql_comments"));
        assertPoolSizeAtLeast(properties, 10);
        assertTrue(properties.containsKey("spring.datasource.hikari.leak-detection-threshold"));
    }

    @Test
    void developmentPropertiesShouldNotCommitPlaintextSecrets() throws IOException {
        Properties properties = loadProfile("application-dev.properties");

        assertFalse(properties.getProperty("spring.datasource.password", "").contains("will=ORDER"));
        assertFalse(properties.getProperty("moderation.comment.openai.api-key", "").startsWith("sk-"));
    }

    private Properties loadProfile(String resourceName) throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            properties.load(inputStream);
        }
        return properties;
    }

    private void assertPoolSizeAtLeast(Properties properties, int minimum) {
        String configuredValue = properties.getProperty("spring.datasource.hikari.maximum-pool-size");
        String defaultValue = configuredValue.replaceAll("^\\$\\{[^:]+:([^}]+)}$", "$1");
        assertTrue(Integer.parseInt(defaultValue) >= minimum);
    }
}
