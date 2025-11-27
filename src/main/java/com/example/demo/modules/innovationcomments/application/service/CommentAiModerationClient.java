package com.example.demo.modules.innovationcomments.application.service;

import java.util.Optional;

public interface CommentAiModerationClient {

    Optional<ModerationVerdict> classify(String text);
}
