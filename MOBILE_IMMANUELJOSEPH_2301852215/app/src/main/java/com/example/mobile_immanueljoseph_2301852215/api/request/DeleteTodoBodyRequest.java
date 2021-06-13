package com.example.mobile_immanueljoseph_2301852215.api.request;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DeleteTodoBodyRequest {
    @SerializedName("todos")
    private ArrayList<String> todos;

    public DeleteTodoBodyRequest(ArrayList<String> todos) {
        this.todos = todos;
    }

    public ArrayList<String> getTodos() {
        return todos;
    }
}
