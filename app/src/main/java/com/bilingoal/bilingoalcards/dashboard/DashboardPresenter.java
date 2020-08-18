package com.bilingoal.bilingoalcards.dashboard;

import androidx.lifecycle.LiveData;
import com.bilingoal.bilingoalcards.base.BasePresenter;
import com.bilingoal.bilingoalcards.database.BilingoalDatabase;
import com.bilingoal.bilingoalcards.database.entities.Lesson;
import com.bilingoal.bilingoalcards.utils.LiveDataUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DashboardPresenter extends BasePresenter<DashboardContract.View>
        implements DashboardContract.Presenter<DashboardContract.View> {

    private final BilingoalDatabase database;

    public DashboardPresenter(BilingoalDatabase database) {
        this.database = database;
    }

    @Override
    public void loadLessons() {
        LiveData<List<Lesson>> allLessons = database.lessonDao().getAllLessons();
        LiveDataUtil.observeOnce(allLessons, lessons -> {
            List<Lesson> newestLessons = sortLessonsByDate(lessons);
            newestLessons = newestLessons.subList(newestLessons.size() - 5, newestLessons.size());
            Collections.reverse(newestLessons);
            view.displayAllLessons(lessons);
            view.displayLastCreatedLessons(newestLessons);
        });
    }

    private List<Lesson> sortLessonsByDate(List<Lesson> lessons)  {
        List<Lesson> newestLessons = new ArrayList<>(lessons);
        Collections.sort(lessons, (a, b) -> {
            Long d = parseTime(a.getLessonCreated());
            Long d2 = parseTime(b.getLessonCreated());
            return d.compareTo(d2);
        });
        return newestLessons;
    }

    private Long parseTime(String time){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        try {
            return Objects.requireNonNull(dateFormat.parse(time)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }
}
