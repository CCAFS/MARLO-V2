package com.example.demo.modules.innovationcomments.application.service;

public class ModerationVerdict {

    private final boolean flagged;
    private final String category;
    private final double score;
    private final String provider;

    public ModerationVerdict(boolean flagged, String category, double score, String provider) {
        this.flagged = flagged;
        this.category = category;
        this.score = score;
        this.provider = provider;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public String getCategory() {
        return category;
    }

    public double getScore() {
        return score;
    }

    public String getProvider() {
        return provider;
    }
}
