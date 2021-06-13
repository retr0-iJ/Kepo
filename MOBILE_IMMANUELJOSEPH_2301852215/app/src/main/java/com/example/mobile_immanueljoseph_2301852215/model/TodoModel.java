package com.example.mobile_immanueljoseph_2301852215.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TodoModel {

    @SerializedName("todo_id")
    @Expose
    private String todoId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("last_edited")
    @Expose
    private String lastEdited;

    public TodoModel(String todo_id, String title, String last_edited) {
        this.todoId = todo_id;
        this.title = title;
        this.lastEdited = last_edited;
    }

    public String getTodo_id() {
        return todoId;
    }

    public void setTodo_id(String todo_id) {
        this.todoId = todo_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLast_edited() {
        return lastEdited;
    }

    public void setLast_edited(String last_edited) {
        this.lastEdited = last_edited;
    }
}
