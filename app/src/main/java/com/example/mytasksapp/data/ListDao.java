package com.example.mytasksapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ListDao {
    @Query("SELECT * FROM list")
    LiveData<List<ListItemModel>> getLists();

    @Delete
    void deleteList(ListItemModel listItemModel);

    @Query("DELETE FROM list")
    void deleteAllLists();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(ListItemModel listItemModel);

    @Update
    void updateList(ListItemModel listItemModel);
}
