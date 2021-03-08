package com.example.mytasksapp.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = TaskItemModel.class, version = 2, exportSchema = false)
public abstract class TaskDatabase extends RoomDatabase {
    private static final String dbName = "task.db";
    private static TaskDatabase database;
    private static final Object LOCK = new Object();

    public static TaskDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if(database == null) {
                database = Room.databaseBuilder(context, TaskDatabase.class, dbName)
                        .fallbackToDestructiveMigration().build();
            }
            return database;
        }
    }

    public abstract TaskDao taskDao();
}
