package com.example.mytasksapp.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task")
public class TaskItemModel {
    @PrimaryKey
    private int id;
    private int listId;
    private String title;
    private boolean checked;

    public TaskItemModel(int id, int listId, String title, boolean checked) {
        this.id = id;
        this.listId = listId;
        this.title = title;
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public int getListId() {
        return listId;
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

    public void setListId(int listId) {
        this.listId = listId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
