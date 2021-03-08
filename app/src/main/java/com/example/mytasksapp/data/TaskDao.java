package com.example.mytasksapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM task")
    LiveData<List<TaskItemModel>> getAllTasks();

    @Query("SELECT * FROM task WHERE listId == :listId")
    LiveData<List<TaskItemModel>> getTasksByListId(int listId);

    @Query("DELETE FROM task")
    void deleteAllTasks();

    @Delete
    void deleteTask(TaskItemModel taskItemModel);

    @Update
    void updateTask(TaskItemModel taskItemModel);

    @Insert
    void insertTask(TaskItemModel taskItemModel);
}
