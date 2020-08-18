package com.bilingoal.bilingoalcards.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "words")
public class Word {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    @ColumnInfo(name = "lesson_id")
    private final int lessonId;
    @NonNull
    @ColumnInfo(name = "sound_name")
    private final String soundName;
    @NonNull
    @ColumnInfo(name = "ch")
    private final String ch;
    @NonNull
    @ColumnInfo(name = "eng")
    private final String eng;
    @NonNull
    @ColumnInfo(name = "pinyin")
    private final String pinyin;

    public Word(int lessonId, @NonNull String soundName, @NonNull String ch,
                @NonNull String eng, @NonNull String pinyin) {
        this.lessonId = lessonId;
        this.soundName = soundName;
        this.ch = ch;
        this.eng = eng;
        this.pinyin = pinyin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getLessonId() {
        return lessonId;
    }

    @NonNull
    public String getSoundName() {
        return soundName;
    }

    @NonNull
    public String getCh() {
        return ch;
    }

    @NonNull
    public String getEng() {
        return eng;
    }

    @NonNull
    public String getPinyin() {
        return pinyin;
    }
}