package com.example.demo.modules.innovationcomments.application.service;

import com.example.demo.modules.innovationcomments.application.exception.CommentRejectedException;
import com.example.demo.platform.config.CommentModerationProperties;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HexFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CommentModerationService {

    private static final Logger logger = LoggerFactory.getLogger(CommentModerationService.class);
    private static final Pattern LINK_PATTERN = Pattern.compile("(https?://|www\\.)\\S+", Pattern.CASE_INSENSITIVE);
    private static final Pattern WORD_PATTERN = Pattern.compile("[\\p{L}\\p{N}]+");

    private final CommentModerationProperties properties;
    private final SensitiveWordBs sensitiveWordBs;
    private final CommentAiModerationClient aiModerationClient;
    private final List<CustomWordPattern> customBannedWordPatterns;

    public CommentModerationService(CommentModerationProperties properties,
                                    ObjectProvider<CommentAiModerationClient> aiModerationClientProvider) {
        this.properties = properties;
        this.sensitiveWordBs = SensitiveWordBs.newInstance()
                .ignoreCase(true)
                .ignoreEnglishStyle(true)
                .ignoreChineseStyle(true)
                .ignoreNumStyle(true)
                .ignoreRepeat(true)
                .ignoreWidth(true)
                .enableEmailCheck(true)
                .enableNumCheck(true)
                // URL detection handled via custom link counter to avoid false positives
                .enableUrlCheck(false)
                .init();
        this.aiModerationClient = aiModerationClientProvider.getIfAvailable();
        this.customBannedWordPatterns = buildCustomPatterns(properties.getExtraBannedWords());
    }

    public void validateComment(Long innovationId, String userEmail, String commentText) {
        if (!properties.isEnabled()) {
            return;
        }

        if (!StringUtils.hasText(commentText)) {
            reject("Comment body cannot be empty.", "EMPTY_COMMENT",
                    "Comment body is empty", innovationId, userEmail, commentText);
        }

        String sanitized = commentText.trim();

        CommentModerationProperties.OpenAiProperties openAiProps = properties.getOpenAi();
        if (aiModerationClient != null && openAiProps != null && openAiProps.isEnabled()) {
            Optional<ModerationVerdict> verdictOptional = aiModerationClient.classify(sanitized);
            if (verdictOptional.isPresent()) {
                ModerationVerdict verdict = verdictOptional.get();
                boolean block = verdict.isFlagged() || verdict.getScore() >= openAiProps.getBlockThreshold();
                if (block) {
                    String reasonCode = "AI_BLOCK_" + verdict.getCategory().toUpperCase(Locale.ROOT);
                    reject("The comment was rejected because it violates the platform guidelines.",
                            reasonCode,
                            String.format("AI provider %s flagged category %s with score %.3f",
                                    verdict.getProvider(), verdict.getCategory(), verdict.getScore()),
                            innovationId, userEmail, sanitized);
                }
            }
        }

        Optional<String> customWord = findCustomBannedWord(sanitized);
        if (customWord.isPresent()) {
            reject("The comment contains offensive language that is not allowed on the platform.",
                    "CUSTOM_BANNED_WORD",
                    "Custom banned word detected: " + customWord.get(),
                    innovationId, userEmail, sanitized);
        }

        List<String> bannedWords = sensitiveWordBs.findAll(sanitized);
        if (!bannedWords.isEmpty()) {
            reject("The comment contains restricted language. Please edit it and try again.",
                    "BANNED_WORDS", "Banned entries detected: " + String.join(",", bannedWords),
                    innovationId, userEmail, sanitized);
        }

        if (countLinks(sanitized) > properties.getMaxAllowedLinks()) {
            reject("The comment contains too many links and looks like spam.",
                    "EXCESSIVE_LINKS", "Too many links detected", innovationId, userEmail, sanitized);
        }

        if (hasExcessiveCharacterRun(sanitized, properties.getMaxRepeatedCharacters())) {
            reject("The comment looks repetitive or automated.",
                    "REPEATED_CHARACTERS", "Detected high repetition pattern", innovationId, userEmail, sanitized);
        }

        if (hasExcessiveWordRepetition(sanitized, properties.getMaxRepeatedWords())) {
            reject("Please avoid repeating the same word many times.",
                    "REPEATED_WORDS", "Detected excessive word repetition", innovationId, userEmail, sanitized);
        }

        if (uppercaseRatio(sanitized) > properties.getMaxUppercaseRatio()) {
            reject("Please avoid writing the entire comment in uppercase.",
                    "UPPERCASE_RATIO", "Uppercase ratio limit exceeded", innovationId, userEmail, sanitized);
        }
    }

    private Optional<String> findCustomBannedWord(String text) {
        if (customBannedWordPatterns.isEmpty()) {
            return Optional.empty();
        }
        for (CustomWordPattern entry : customBannedWordPatterns) {
            if (entry.pattern().matcher(text).find()) {
                return Optional.of(entry.value());
            }
        }
        return Optional.empty();
    }

    private List<CustomWordPattern> buildCustomPatterns(List<String> words) {
        if (words == null || words.isEmpty()) {
            return Collections.emptyList();
        }
        List<CustomWordPattern> patterns = new ArrayList<>();
        for (String word : words) {
            if (!StringUtils.hasText(word)) {
                continue;
            }
            String normalized = word.trim().toLowerCase(Locale.ROOT);
            Pattern pattern = Pattern.compile("\\b" + Pattern.quote(normalized) + "\\b", Pattern.CASE_INSENSITIVE);
            patterns.add(new CustomWordPattern(normalized, pattern));
        }
        return List.copyOf(patterns);
    }

    private int countLinks(String text) {
        Matcher matcher = LINK_PATTERN.matcher(text);
        int counter = 0;
        while (matcher.find()) {
            counter++;
        }
        return counter;
    }

    private boolean hasExcessiveCharacterRun(String text, int threshold) {
        if (threshold <= 0) {
            return false;
        }
        int currentRun = 1;
        int maxRun = 1;
        for (int i = 1; i < text.length(); i++) {
            char current = Character.toLowerCase(text.charAt(i));
            char previous = Character.toLowerCase(text.charAt(i - 1));
            if (current == previous && Character.isLetterOrDigit(current)) {
                currentRun++;
                maxRun = Math.max(maxRun, currentRun);
                if (maxRun > threshold) {
                    return true;
                }
            } else {
                currentRun = 1;
            }
        }
        return false;
    }

    private double uppercaseRatio(String text) {
        int uppercase = 0;
        int letters = 0;
        for (char current : text.toCharArray()) {
            if (Character.isLetter(current)) {
                letters++;
                if (Character.isUpperCase(current)) {
                    uppercase++;
                }
            }
        }
        if (letters == 0) {
            return 0;
        }
        return (double) uppercase / letters;
    }

    private boolean hasExcessiveWordRepetition(String text, int threshold) {
        if (threshold <= 0) {
            return false;
        }
        Matcher matcher = WORD_PATTERN.matcher(text);
        String previous = null;
        int currentRun = 0;
        while (matcher.find()) {
            String word = matcher.group().toLowerCase(Locale.ROOT);
            if (word.equals(previous)) {
                currentRun++;
                if (currentRun > threshold) {
                    return true;
                }
            } else {
                previous = word;
                currentRun = 1;
            }
        }
        return false;
    }

    private void reject(String userMessage, String reasonCode, String logMessage,
                        Long innovationId, String userEmail, String commentText) {
        if (properties.isLogRejections()) {
            logger.warn("Comment rejected | innovation={} | user={} | reason={} | hash={}",
                    innovationId, userEmail, reasonCode, hashComment(commentText));
            logger.debug("Moderation details: {}", logMessage);
        }
        throw new CommentRejectedException(userMessage, reasonCode, logMessage);
    }

    private String hashComment(String text) {
        if (!StringUtils.hasText(text)) {
            return "EMPTY";
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            return "NA";
        }
    }

    private record CustomWordPattern(String value, Pattern pattern) {}
}
