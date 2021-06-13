package com.example.mobile_immanueljoseph_2301852215.api.response;

import com.example.mobile_immanueljoseph_2301852215.model.UserModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

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
    private UserModel user;

    public UserModel getUserCredentials(){
        return user;
    }
}
