package com.bilingoal.bilingoalcards.database;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.bilingoal.bilingoalcards.database.entities.Word;

import java.util.List;

@Dao
public interface WordDao {
    @Insert
    void insert(Word word);
    @Update
    void update(Word word);
    @Delete
    void delete(Word word);

    @Query("SELECT * FROM words WHERE lesson_id = :id")
    LiveData<List<Word>> getAllWords(int id);
}
