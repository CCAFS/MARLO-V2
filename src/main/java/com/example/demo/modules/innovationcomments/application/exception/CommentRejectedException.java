package com.example.demo.modules.innovationcomments.application.exception;

public class CommentRejectedException extends RuntimeException {

    private final String userMessage;
    private final String reasonCode;

    public CommentRejectedException(String userMessage, String reasonCode, String internalMessage) {
        super(internalMessage);
        this.userMessage = userMessage;
        this.reasonCode = reasonCode;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public String getReasonCode() {
        return reasonCode;
    }
}
