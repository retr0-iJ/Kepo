package com.example.mobile_immanueljoseph_2301852215.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchTodoModel {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("todo_id")
    @Expose
    private String todoId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("last_edited")
    @Expose
    private String lastEdited;

    public SearchTodoModel(String userId, String username, String todoId, String title, String lastEdited) {
        this.userId = userId;
        this.username = username;
        this.todoId = todoId;
        this.title = title;
        this.lastEdited = lastEdited;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getTodoId() {
        return todoId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLastEdited() {
        return lastEdited;
    }
}
