package com.example.mobile_immanueljoseph_2301852215.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InsertUpdateResponse {
    @SerializedName("status")
    @Expose
    private int code;

    public int getCode(){ return code; }

    @SerializedName("message")
    @Expose
    private String msg;

    public String getMsg(){ return msg; }
}
