package com.bilingoal.bilingoalcards.lesson;

import com.bilingoal.bilingoalcards.base.BasePresenter;
import com.bilingoal.bilingoalcards.components.BilingoalDialog;
import com.bilingoal.bilingoalcards.database.BilingoalDatabase;
import com.bilingoal.bilingoalcards.database.entities.Word;
import com.bilingoal.bilingoalcards.models.*;
import com.bilingoal.bilingoalcards.utils.LiveDataUtil;

import java.util.*;

public class LessonPresenter extends BasePresenter<LessonContract.View> implements
        LessonContract.Presenter<LessonContract.View> {
    private final BilingoalDatabase database;
    private ProgressTracker progressTracker;
    private final StateManager stateManager;
    private List<Word> wordList;
    private Router router;
    private String soundToPlay = "";

    private static final String POSITIVE_TITLE = "You are correct!";
    private static final String NEGATIVE_TITLE = "Correct answer:";


    public LessonPresenter(BilingoalDatabase database) {
        this.database = database;
        stateManager = StateManager.getInstance();
    }

    @Override
    public void initLesson() {
        int lessonId = StateManager.getInstance().getSelectedLessonId();
        LiveDataUtil.observeOnce(database.lessonDao()
                .getLessonTitle(lessonId), s -> view.setLessonTitle(s));

        LiveDataUtil.observeOnce(database.wordDao().getAllWords(lessonId), words -> {
            wordList = words;
            Collections.shuffle(wordList);
            progressTracker = new ProgressTracker(words.size());
            view.setDefaultChoices(words);
            view.setFirstWord(words.get(0));

            soundToPlay = words.get(0).getSoundName();
            router = new Router(words);
        });
    }

    @Override
    public void nextWord() {
        if(router.hasNextWord()) {
            router.toNextWord();
            view.updateDeckCard(router.getCurrentWord());
            changeSoundToPlay();
            ShuffledChoices shuffledChoices = ShuffledChoices.create(
                    wordList, router.getCurrentWord().getEng()
            );
            view.displayNextChoices(shuffledChoices.toList());
        } else {
            stateManager.setLessonResults(createLessonResults());
            view.showResults();
        }
    }

    @Override
    public void nextDialog(String selected) {
        final String current = router.getCurrentWord().getEng().toLowerCase();
        Map<String, String> dialogMessage = createDialogMessage(selected, current);
        view.displayDialog(dialogMessage);
    }

    @Override
    public String getSoundToPlay() {
        return soundToPlay;
    }

    private boolean isCorrectAnswer(String selected, String current) {
        if(selected.equals(current)) {
            progressTracker.incrementCorrectAnswers();
            view.playSound(true);
            return true;
        } else {
            progressTracker.incrementWrongAnswers();
            view.playSound(false);
            return false;
        }
    }

    private LessonResults createLessonResults() {
        return new LessonResults(
                progressTracker.getCorrectAnswers(),
                progressTracker.getWrongAnswers(),
                progressTracker.getPercentageOfCorrectAnswers()
        );
    }

    private void changeSoundToPlay() {
        soundToPlay = router.getCurrentWord().getSoundName();
    }

    private Map<String, String> createDialogMessage(String selected, String current) {
        final Map<String, String> map = new HashMap<>();
        if(isCorrectAnswer(selected, current)) {
            map.put(BilingoalDialog.TITLE, POSITIVE_TITLE);
            map.put(BilingoalDialog.MESSAGE, "");
        } else {
            map.put(BilingoalDialog.TITLE, NEGATIVE_TITLE);
            map.put(BilingoalDialog.MESSAGE, router.getCurrentWord().getCh());
        }
        return map;
    }
}
