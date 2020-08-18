package com.bilingoal.bilingoalcards.database;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.bilingoal.bilingoalcards.database.entities.Lesson;
import com.bilingoal.bilingoalcards.database.entities.Word;

@Database(entities = {Lesson.class, Word.class}, version = 1, exportSchema = false)
public abstract class BilingoalDatabase extends RoomDatabase {
    private static BilingoalDatabase instance;
    private static final String TAG = BilingoalDatabase.class.getSimpleName();

    public static synchronized BilingoalDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, BilingoalDatabase.class, "bilingoal")
                    .createFromAsset("database/bilingoal.db")
                    .addMigrations(new Migration(1, 2) {
                        @Override
                        public void migrate(@NonNull SupportSQLiteDatabase database) {
                            Log.w(TAG, "Migrate from version 1 to 2");
                        }
                    })
                    .build();
        }
        return instance;
    }

    public abstract LessonDao lessonDao();
    public abstract WordDao wordDao();
}
