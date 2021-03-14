package com.example.mytasksapp.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "category")
public class CategoryItem {
    @PrimaryKey
    private int id;
    private String title;
    private boolean checked;

    public CategoryItem(int id, String title, boolean checked) {
        this.id = id;
        this.title = title;
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
