package com.bilingoal.bilingoalcards.models;

public class ProgressTracker {
    private float correctAnswers;
    private float wrongAnswers;
    private final float totalQuestions;

    public ProgressTracker(float totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public void incrementCorrectAnswers() {
        correctAnswers++;
    }

    public void incrementWrongAnswers() {
        wrongAnswers++;
    }

    public int getPercentageOfCorrectAnswers() {
        return Math.round((correctAnswers / totalQuestions) * 100);
    }

    public float getCorrectAnswers() {
        return correctAnswers;
    }

    public float getWrongAnswers() {
        return wrongAnswers;
    }

    public float getTotalQuestions() {
        return totalQuestions;
    }
}
