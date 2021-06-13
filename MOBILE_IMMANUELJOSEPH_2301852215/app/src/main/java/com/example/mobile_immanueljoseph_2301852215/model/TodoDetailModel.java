package com.example.mobile_immanueljoseph_2301852215.model;

import com.example.mobile_immanueljoseph_2301852215.util.DateUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TodoDetailModel implements Serializable {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("todo_id")
    @Expose
    private String todo_id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("last_edited")
    @Expose
    private String last_edited;

    public TodoDetailModel(String username, String todo_id, String title, String description, String last_edited) {
        this.username = username;
        this.todo_id = todo_id;
        this.title = title;
        this.description = description;
        this.last_edited = last_edited;
    }

    public String getUsername() {
        return username;
    }

    public String getTodo_id() {
        return todo_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLast_edited() {
        return DateUtils.dateFromServer(last_edited);
    }
}
