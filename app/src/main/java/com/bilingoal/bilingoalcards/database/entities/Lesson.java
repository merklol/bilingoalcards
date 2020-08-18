package com.bilingoal.bilingoalcards.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lessons")
public class Lesson  {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    @ColumnInfo(name = "lesson_title")
    private String lessonTitle;
    @NonNull
    @ColumnInfo(name = "lesson_progress")
    private int lessonProgress;
    @NonNull
    @ColumnInfo(name = "lesson_created")
    private String lessonCreated;

    public Lesson(String lessonTitle, int lessonProgress, String lessonCreated) {
        this.lessonTitle = lessonTitle;
        this.lessonProgress = lessonProgress;
        this.lessonCreated = lessonCreated;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLessonProgress(int lessonProgress) {
        this.lessonProgress = lessonProgress;
    }

    public int getId() {
        return id;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public int getLessonProgress() {
        return lessonProgress;
    }

    public String getLessonCreated() {
        return lessonCreated;
    }
}
