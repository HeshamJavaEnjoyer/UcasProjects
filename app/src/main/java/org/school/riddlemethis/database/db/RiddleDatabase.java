package org.school.riddlemethis.database.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.school.riddlemethis.database.daos.LevelDao;
import org.school.riddlemethis.database.daos.RiddleDao;
import org.school.riddlemethis.database.models.Levels;
import org.school.riddlemethis.database.models.Riddles;
import org.school.riddlemethis.source_data.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Riddles.class, Levels.class}, version = 1, exportSchema = false)
public abstract class RiddleDatabase extends RoomDatabase {
    //todo create a DAO for each class
    public abstract RiddleDao riddleDao();

    public abstract LevelDao levelDao();

    private static volatile RiddleDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //TEMP CHANGE -1 2- Accuses Public seated
    static RiddleDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RiddleDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext()
                                    , RiddleDatabase.class, "riddle_database")
                            //Builder design pattern I Added a Listener that Puts all the Data
                            .addCallback(onCreateCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback onCreateCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new Thread(() -> Utils.getInstance().readRiddles()).start();
        }
    };
}

