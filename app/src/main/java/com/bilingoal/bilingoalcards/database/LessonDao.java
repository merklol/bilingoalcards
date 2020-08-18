package com.bilingoal.bilingoalcards.database;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.bilingoal.bilingoalcards.database.entities.Lesson;

import java.util.List;

@Dao
public interface LessonDao {
    @Insert
    void insert(Lesson lesson);
    @Update
    void update(Lesson lesson);
    @Delete
    void delete(Lesson lesson);
    @Query("SELECT * FROM lessons")
    LiveData<List<Lesson>> getAllLessons();
    @Query("UPDATE lessons SET lesson_progress=:progress WHERE id=:id")
    void updateLessonAt(int id, int progress);
    @Query("SELECT lesson_title FROM lessons WHERE id=:id")
    LiveData<String> getLessonTitle(int id);
}
