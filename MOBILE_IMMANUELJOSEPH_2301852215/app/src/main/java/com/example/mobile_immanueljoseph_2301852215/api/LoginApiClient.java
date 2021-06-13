package com.example.mobile_immanueljoseph_2301852215.api;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mobile_immanueljoseph_2301852215.api.response.LoginResponse;
import com.example.mobile_immanueljoseph_2301852215.util.AppExecutors;
import com.example.mobile_immanueljoseph_2301852215.util.SingleLiveEvent;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class LoginApiClient {

    private static LoginApiClient instance;
    private SingleLiveEvent<LoginResponse> loginResponse;

    private RetrieveLoginRunnable retrieveLoginRunnable;

    private LoginApiClient(){
        loginResponse = new SingleLiveEvent<>();
    }

    public static LoginApiClient getInstance(){
        return instance == null ? instance = new LoginApiClient() : instance;
    }

    public LiveData<LoginResponse> getLoginResponse(){
        return loginResponse;
    }

    public void searchUserCredentialsApi(String username, String password){
        if(retrieveLoginRunnable != null)
            retrieveLoginRunnable = null;

        retrieveLoginRunnable = new RetrieveLoginRunnable(username, password);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveLoginRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling Request
                myHandler.cancel(true);
                Log.v("Tag", "canceled");
            }
        }, 5000, TimeUnit.MILLISECONDS);
    }

    private class RetrieveLoginRunnable implements Runnable{

        private String username;
        private String password;
        boolean cancelRequest;

        public RetrieveLoginRunnable(String username, String password) {
            this.username = username;
            this.password = password;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try{
                Response<LoginResponse> response = getUserCredentials(username, password).execute();

                if(cancelRequest)
                    return;

                if(response.code() == 200 || response.code() == 500){
                    loginResponse.postValue(response.body());
                }else{
                    Log.v("Tag", response.errorBody().string());
                    loginResponse.postValue(null);
                }

            }catch(Exception ex){
                ex.printStackTrace();
                loginResponse.postValue(null);
            }


        }

        private Call<LoginResponse> getUserCredentials(String username, String password){
            return RepoServices.getApiInterface().getUserCredentials(
                    username,
                    password
            );
        }
    }
}


