package com.bilingoal.bilingoalcards.results;

import com.bilingoal.bilingoalcards.models.LessonResults;
import com.bilingoal.bilingoalcards.models.StateManager;
import com.bilingoal.bilingoalcards.base.BasePresenter;
import com.bilingoal.bilingoalcards.database.BilingoalDatabase;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class ResultsPresenter extends BasePresenter<ResultsContract.View> implements
        ResultsContract.Presenter<ResultsContract.View> {
    private final StateManager stateManager;
    private final BilingoalDatabase database;
    private LessonResults lessonResults;
    private int selectedLessonId;


    public ResultsPresenter(BilingoalDatabase database) {
        this.database = database;
        stateManager = StateManager.getInstance();
    }

    public void displayLessonResults() {
        getLessonResults();
        PieDataSet dataSet = preparePieChartData();
        view.displayPieChartView(dataSet, lessonResults);
        updateLessonProgress();
    }

    private void getLessonResults() {
        lessonResults = stateManager.getLessonResults();
        selectedLessonId = stateManager.getSelectedLessonId();
    }

    private void updateLessonProgress() {
        new Thread(() -> {
            database.lessonDao().updateLessonAt(
                    selectedLessonId, lessonResults.getPercentageOfCorrectAnswers()
            );
            lessonResults.invalidate();
        }).start();
    }

    private PieDataSet preparePieChartData() {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(lessonResults.getCorrectAnswers(), "Right Answers"));
        entries.add(new PieEntry(lessonResults.getWrongAnswers(), "Wrong Answers"));

        PieDataSet dataSet = new PieDataSet(entries, "Number Of Employees");
        dataSet.setValueFormatter(new DefaultValueFormatter(0));
        return dataSet;
    }
}
