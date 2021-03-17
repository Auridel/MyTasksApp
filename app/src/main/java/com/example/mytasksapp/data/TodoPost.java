package com.example.mytasksapp.data;

import com.google.gson.annotations.SerializedName;

public class TodoPost {

    @SerializedName("list_id")
    private int list_id;

    @SerializedName("text")
    private String text;

    public TodoPost(int list_id, String text) {
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
