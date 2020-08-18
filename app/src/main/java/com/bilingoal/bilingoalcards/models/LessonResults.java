package com.bilingoal.bilingoalcards.models;

public class LessonResults {
    private float correctAnswers;
    private float wrongAnswers;
    private int percentageOfCorrectAnswers;

    public LessonResults(float correctAnswers, float wrongAnswers, int percentageOfCorrectAnswers) {
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
        this.percentageOfCorrectAnswers = percentageOfCorrectAnswers;
    }

    public float getCorrectAnswers() {
        return correctAnswers;
    }

    public float getWrongAnswers() {
        return wrongAnswers;
    }

    public int getPercentageOfCorrectAnswers() {
        return percentageOfCorrectAnswers;
    }

    public void invalidate() {
        correctAnswers = 0;
        wrongAnswers = 0;
        percentageOfCorrectAnswers = 0;
    }
}
