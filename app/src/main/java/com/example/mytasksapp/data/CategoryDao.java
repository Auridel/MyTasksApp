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
public interface CategoryDao {
    @Query("SELECT * FROM mycategory")
    LiveData<List<CategoryItem>> getAllCategories();

    @Query("DELETE FROM mycategory")
    void deleteAllCategories();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategory(CategoryItem categoryItem);

    @Delete
    void deleteCategory(CategoryItem categoryItem);

    @Update
    void updateCategory(CategoryItem categoryItem);

    @Query("UPDATE mycategory SET checked = 0 WHERE checked == 1")
    void uncheckCategories();

    @Query("UPDATE mycategory SET checked = 1 WHERE id == :id")
    void checkCategory(int id);

    @Query("SELECT * FROM mycategory WHERE checked == 1")
    LiveData<List<CategoryItem>> getCheckegCategory();
}
