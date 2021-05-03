package com.example.personalisedmobilepaindiary.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.personalisedmobilepaindiary.dao.PainRecordDAO;
import com.example.personalisedmobilepaindiary.entity.PainRecord;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {PainRecord.class}, version = 4, exportSchema = false)
public abstract class PainRecordDatabase extends RoomDatabase {
    public abstract PainRecordDAO painRecordDAO();
    private static PainRecordDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static synchronized PainRecordDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    PainRecordDatabase.class, "PainRecordDatabase")
                    .fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }
}
