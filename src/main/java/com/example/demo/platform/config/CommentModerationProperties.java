package com.example.demo.platform.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "moderation.comment")
public class CommentModerationProperties {

    private boolean enabled = true;
    private int maxAllowedLinks = 3;
    private int maxRepeatedCharacters = 6;
    private double maxUppercaseRatio = 0.8;
    private boolean logRejections = true;
    private OpenAiProperties openAi = new OpenAiProperties();
    private List<String> extraBannedWords = new ArrayList<>();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getMaxAllowedLinks() {
        return maxAllowedLinks;
    }

    public void setMaxAllowedLinks(int maxAllowedLinks) {
        this.maxAllowedLinks = maxAllowedLinks;
    }

    public int getMaxRepeatedCharacters() {
        return maxRepeatedCharacters;
    }

    public void setMaxRepeatedCharacters(int maxRepeatedCharacters) {
        this.maxRepeatedCharacters = maxRepeatedCharacters;
    }

    public double getMaxUppercaseRatio() {
        return maxUppercaseRatio;
    }

    public void setMaxUppercaseRatio(double maxUppercaseRatio) {
        this.maxUppercaseRatio = maxUppercaseRatio;
    }

    public boolean isLogRejections() {
        return logRejections;
    }

    public void setLogRejections(boolean logRejections) {
        this.logRejections = logRejections;
    }

    public OpenAiProperties getOpenAi() {
        return openAi;
    }

    public void setOpenAi(OpenAiProperties openAi) {
        this.openAi = openAi;
    }

    public List<String> getExtraBannedWords() {
        return extraBannedWords;
    }

    public void setExtraBannedWords(List<String> extraBannedWords) {
        this.extraBannedWords = extraBannedWords;
    }

    public static class OpenAiProperties {
        private boolean enabled = false;
        private String apiKey;
        private String model = "omni-moderation-latest";
        private double blockThreshold = 0.5;
        private Duration timeout = Duration.ofSeconds(4);

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public double getBlockThreshold() {
            return blockThreshold;
        }

        public void setBlockThreshold(double blockThreshold) {
            this.blockThreshold = blockThreshold;
        }

        public Duration getTimeout() {
            return timeout;
        }

        public void setTimeout(Duration timeout) {
            this.timeout = timeout;
        }
    }
}
