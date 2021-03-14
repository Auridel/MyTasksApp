package com.example.mytasksapp.data;

public class TaskRequest {
    private int list_id;
    private String text;

    public TaskRequest(int list_id, String text) {
        this.list_id = list_id;
        this.text = text;
    }

    public int getList_id() {
        return list_id;
    }

    public String getText() {
        return text;
    }

    public void setList_id(int list_id) {
        this.list_id = list_id;
    }

    public void setText(String text) {
        this.text = text;
    }
}
