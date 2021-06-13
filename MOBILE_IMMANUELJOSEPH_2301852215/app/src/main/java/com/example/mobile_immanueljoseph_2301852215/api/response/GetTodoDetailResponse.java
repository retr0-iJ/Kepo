package com.example.mobile_immanueljoseph_2301852215.api.response;

import com.example.mobile_immanueljoseph_2301852215.model.TodoDetailModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetTodoDetailResponse {
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
    private TodoDetailModel data;

    public TodoDetailModel getData() {
        return data;
    }
}
