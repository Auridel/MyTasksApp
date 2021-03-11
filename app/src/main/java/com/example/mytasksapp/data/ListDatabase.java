package com.example.mytasksapp.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = ListItemModel.class, version = 4, exportSchema = false)
public abstract class ListDatabase extends RoomDatabase {
    private static final String dbName = "list.db";
    private static ListDatabase database;
    private static final Object LOCK = new Object();

    public static ListDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if(database == null) {
                database = Room.databaseBuilder(context, ListDatabase.class, dbName)
                        .fallbackToDestructiveMigration().build();
            }
            return database;
        }
    }

    public abstract ListDao listDao();
}
