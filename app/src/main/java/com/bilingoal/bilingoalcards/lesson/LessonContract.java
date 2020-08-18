package com.bilingoal.bilingoalcards.lesson;

import com.bilingoal.bilingoalcards.base.BaseContract;
import com.bilingoal.bilingoalcards.database.entities.Word;

import java.util.List;
import java.util.Map;

public interface LessonContract {

    interface Presenter<V extends BaseContract.View> extends BaseContract.Presenter<View> {
        void initLesson();
        void nextWord();
        void nextDialog(String selected);
        String getSoundToPlay();
    }

    interface View extends BaseContract.View {
        void setDefaultChoices(List<Word> words);
        void setFirstWord(Word word);
        void displayDialog(Map<String, String> dialogMessage);
        void displayNextChoices(List<String> choices);
        void showResults();
        void updateDeckCard(Word word);
        void playSound(boolean isCorrectAnswer);
        void setLessonTitle(String str);
    }
}
