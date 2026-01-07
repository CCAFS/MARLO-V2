package com.example.demo.modules.innovationcomments.application.service;

import com.example.demo.modules.innovationcomments.application.service.dto.OpenAiModerationRequest;
import com.example.demo.modules.innovationcomments.application.service.dto.OpenAiModerationResponse;
import com.example.demo.platform.config.CommentModerationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

@Component
@ConditionalOnProperty(prefix = "moderation.comment.openai", name = "enabled", havingValue = "true")
public class OpenAiModerationClient implements CommentAiModerationClient {

    private static final Logger logger = LoggerFactory.getLogger(OpenAiModerationClient.class);
    private static final String BASE_URL = "https://api.openai.com/v1";

    private final RestClient restClient;
    private final CommentModerationProperties.OpenAiProperties properties;

    public OpenAiModerationClient(RestClient.Builder builder, CommentModerationProperties properties) {
        this.properties = properties.getOpenAi();
        if (!StringUtils.hasText(this.properties.getApiKey())) {
            throw new IllegalStateException("OpenAI moderation is enabled but no API key was provided");
        }
        this.restClient = builder
                .requestFactory(createRequestFactory(this.properties.getTimeout()))
                .baseUrl(BASE_URL)
                .build();
    }

    @Override
    public Optional<ModerationVerdict> classify(String text) {
        if (!StringUtils.hasText(text)) {
            return Optional.empty();
        }

        OpenAiModerationRequest payload = OpenAiModerationRequest.ofSingleInput(properties.getModel(), text);
        try {
            OpenAiModerationResponse response = restClient.post()
                    .uri("/moderations")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + properties.getApiKey())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(payload)
                    .retrieve()
                    .body(OpenAiModerationResponse.class);

            if (response == null || response.results() == null || response.results().isEmpty()) {
                return Optional.empty();
            }

            OpenAiModerationResponse.Result result = response.results().get(0);
            Map.Entry<String, Double> dominantCategory = result.highestScoreCategory();
            String category = dominantCategory != null ? dominantCategory.getKey() : "unknown";
            double score = dominantCategory != null ? dominantCategory.getValue() : 0d;
            boolean flagged = result.flagged();

            return Optional.of(new ModerationVerdict(flagged, category, score, "openai"));
        } catch (RestClientException e) {
            logger.warn("OpenAI moderation request failed: {}", e.getMessage());
            return Optional.empty();
        }
    }

    private SimpleClientHttpRequestFactory createRequestFactory(Duration timeout) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        int millis = (int) timeout.toMillis();
        factory.setConnectTimeout(millis);
        factory.setReadTimeout(millis);
        return factory;
    }
}
