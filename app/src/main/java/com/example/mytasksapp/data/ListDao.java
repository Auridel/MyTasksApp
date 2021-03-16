package com.example.mytasksapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mytasksapp.pojo.MyList;

import java.util.List;

@Dao
public interface ListDao {
    @Query("SELECT * FROM mylists")
    LiveData<List<MyList>> getLists();

    @Delete
    void deleteList(MyList myList);

    @Query("DELETE FROM mylists")
    void deleteAllLists();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(MyList myList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllLists(List<MyList> myLists);

    @Update
    void updateList(MyList myList);
}
