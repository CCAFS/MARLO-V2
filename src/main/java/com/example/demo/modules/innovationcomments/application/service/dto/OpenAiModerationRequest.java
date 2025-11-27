package com.example.demo.modules.innovationcomments.application.service.dto;

import java.util.Collections;
import java.util.List;

public record OpenAiModerationRequest(String model, List<String> input) {

    public static OpenAiModerationRequest ofSingleInput(String model, String text) {
        return new OpenAiModerationRequest(model, Collections.singletonList(text));
    }
}
