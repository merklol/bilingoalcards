package com.bilingoal.bilingoalcards.dashboard;

import com.bilingoal.bilingoalcards.base.BaseContract;
import com.bilingoal.bilingoalcards.database.entities.Lesson;

import java.util.List;

public interface DashboardContract {

    interface Presenter<V extends BaseContract.View> extends BaseContract.Presenter<View> {
        void loadLessons();
    }

    interface View extends BaseContract.View {
        void displayLastCreatedLessons(List<Lesson> lessons);
        void displayAllLessons(List<Lesson> lessons);
    }
}
