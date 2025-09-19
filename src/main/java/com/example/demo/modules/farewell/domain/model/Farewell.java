package com.example.demo.modules.farewell.domain.model;

import com.example.demo.sharedkernel.domain.Message;

import java.util.Objects;

public record Farewell(String message) implements Message {
    public Farewell {
        Objects.requireNonNull(message, "message must not be null");
    }

    @Override
    public String getMessage() {
        return message;
    }
}
