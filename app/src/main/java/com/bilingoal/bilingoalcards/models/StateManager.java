package com.bilingoal.bilingoalcards.models;

public class StateManager {
    private int selectedLessonId;
    private LessonResults lessonResults;
    private static StateManager instance;

    public static synchronized StateManager getInstance() {
        if(instance == null) {
            instance = new StateManager();
        }
        return instance;
    }

    public void setLessonId(int value) {
        selectedLessonId = value;
    }

    public int getSelectedLessonId() {
        return selectedLessonId;
    }

    public LessonResults getLessonResults() {
        return lessonResults;
    }

    public void setLessonResults(LessonResults lessonResults) {
        this.lessonResults = lessonResults;
    }
}
