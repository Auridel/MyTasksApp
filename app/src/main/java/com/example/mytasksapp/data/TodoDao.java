package com.example.mytasksapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mytasksapp.pojo.Todo;

import java.util.List;

@Dao
public interface TodoDao {
    @Query("SELECT * FROM mytodos")
    LiveData<List<Todo>> getAllTodos();

    @Query("SELECT * FROM mytodos WHERE listId == :listId")
    LiveData<List<Todo>> getTasksByListId(int listId);

    @Query("DELETE FROM mytodos")
    void deleteAllTodos();

    @Delete
    void deleteTodo(Todo todo);

    @Update
    void updateTodo(Todo todo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTodo(Todo todo);
}
