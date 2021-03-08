package com.example.mytasksapp.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task")
public class TaskItemModel {
    @PrimaryKey
    private int id;
    private int listId;
    private String text;
    private boolean checked;

    public TaskItemModel(int id, int listId, String text, boolean checked) {
        this.id = id;
        this.listId = listId;
        this.text = text;
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public int getListId() {
        return listId;
    }

    public String getText() {
        return text;
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

    public void setText(String text) {
        this.text = text;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
