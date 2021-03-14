package com.example.mytasksapp.data;

import com.google.gson.annotations.SerializedName;

public class AddTaskResult {
    @SerializedName("id")
    private int id;
    @SerializedName("text")
    private String text;
    @SerializedName("list_id")
    private int list_id;
    @SerializedName("checked")
    private boolean checked;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getList_id() {
        return list_id;
    }

    public boolean isChecked() {
        return checked;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setList_id(int list_id) {
        this.list_id = list_id;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
