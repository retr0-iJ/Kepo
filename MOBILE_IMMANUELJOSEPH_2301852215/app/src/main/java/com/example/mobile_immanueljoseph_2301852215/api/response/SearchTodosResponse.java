package com.example.mobile_immanueljoseph_2301852215.api.response;

import com.example.mobile_immanueljoseph_2301852215.model.SearchTodoModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchTodosResponse {
    @SerializedName("status")
    @Expose
    private int code;

    public int getCode(){ return code; }

    @SerializedName("message")
    @Expose
    private String msg;

    public String getMsg(){ return msg; }

    @SerializedName("data")
    @Expose
    private ArrayList<SearchTodoModel> data;

    public ArrayList<SearchTodoModel> getData() {
        return data;
    }
}
