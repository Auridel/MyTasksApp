package com.example.mytasksapp.pojo;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

import com.example.mytasksapp.converters.Converter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "mylists")
public class MyList {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("candidate_id")
    @Expose
    private int candidateId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @Ignore
    private List<Todo> todos = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Ignore
    public List<Todo> getTodos() {
        return todos;
    }

    @Ignore
    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }

}
