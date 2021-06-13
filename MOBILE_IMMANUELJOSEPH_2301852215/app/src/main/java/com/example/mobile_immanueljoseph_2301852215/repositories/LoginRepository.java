package com.example.mobile_immanueljoseph_2301852215.repositories;

import androidx.lifecycle.LiveData;

import com.example.mobile_immanueljoseph_2301852215.api.LoginApiClient;
import com.example.mobile_immanueljoseph_2301852215.api.response.LoginResponse;

public class LoginRepository {
    private static LoginRepository instance;

    private LoginApiClient loginApiClient;

    public static LoginRepository getInstance(){
        return instance == null ? instance = new LoginRepository() : instance;
    }

    private LoginRepository(){
        loginApiClient = LoginApiClient.getInstance();
    }

    public LiveData<LoginResponse> getLoginResponse(){
        return loginApiClient.getLoginResponse();
    }

    public void loginRequestApi(String username, String password){
        loginApiClient.searchUserCredentialsApi(username, password);
    }

}
