package com.example.mytasksapp.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mytasksapp.pojo.MyList;
import com.example.mytasksapp.pojo.Todo;

@Database(entities = {MyList.class, Todo.class, CategoryItem.class}, version = 8, exportSchema = false)
public abstract class MyTasksDatabase extends RoomDatabase {
    private static final String dbName = "mytasks.db";
    private static MyTasksDatabase database;
    private static final Object LOCK = new Object();

    public static MyTasksDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if(database == null) {
                database = Room.databaseBuilder(context, MyTasksDatabase.class, dbName)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration().build();
            }
            return database;
        }
    }

    public abstract ListDao listDao();

//    public abstract TaskDao taskDao();

//    public abstract CategoryDao categoryDao();
}
