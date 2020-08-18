package com.bilingoal.bilingoalcards.results;

import com.bilingoal.bilingoalcards.models.LessonResults;
import com.bilingoal.bilingoalcards.base.BaseContract;
import com.github.mikephil.charting.data.PieDataSet;

public interface ResultsContract {

    interface Presenter<V extends BaseContract.View> extends BaseContract.Presenter<View> {
    }

    interface View extends BaseContract.View {
        void displayPieChartView(PieDataSet pieDataSet, LessonResults lessonResults);
    }
}
