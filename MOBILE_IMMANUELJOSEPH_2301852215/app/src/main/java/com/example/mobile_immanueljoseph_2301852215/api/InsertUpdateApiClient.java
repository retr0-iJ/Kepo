package com.example.mobile_immanueljoseph_2301852215.api;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mobile_immanueljoseph_2301852215.api.response.InsertUpdateResponse;
import com.example.mobile_immanueljoseph_2301852215.util.AppExecutors;
import com.example.mobile_immanueljoseph_2301852215.util.SingleLiveEvent;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class InsertUpdateApiClient {
    private static InsertUpdateApiClient instance;
    private SingleLiveEvent<InsertUpdateResponse> insertUpdateResponse;

    private RetrieveInsertUpdateRunnable retrieveInsertUpdateRunnable;

    private InsertUpdateApiClient(){
        insertUpdateResponse = new SingleLiveEvent<>();
    }

    public static InsertUpdateApiClient getInstance(){
        return instance == null ? instance = new InsertUpdateApiClient() : instance;
    }

    public LiveData<InsertUpdateResponse> getInsertUpdateResponse(){
        return insertUpdateResponse;
    }

    public void insertUpdateTodo(String user_id, String todo_id, String title, String desc){
        if(retrieveInsertUpdateRunnable != null)
            retrieveInsertUpdateRunnable = null;

        retrieveInsertUpdateRunnable = new InsertUpdateApiClient.RetrieveInsertUpdateRunnable(user_id, todo_id, title, desc);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveInsertUpdateRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling Request
                myHandler.cancel(true);
                Log.v("Tag", "canceled");
            }
        }, 5000, TimeUnit.MILLISECONDS);
    }

    private class RetrieveInsertUpdateRunnable implements Runnable{

        private String user_id, todo_id, title, desc;
        boolean cancelRequest;

        public RetrieveInsertUpdateRunnable(String user_id, String todo_id, String title, String desc) {
            this.user_id = user_id;
            this.todo_id = todo_id;
            this.title = title;
            this.desc = desc;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try{
                Response<InsertUpdateResponse> response;

                if(todo_id.equals("")) response = insertTodo(user_id, title, desc).execute();
                else response = updateTodo(user_id, todo_id, title, desc).execute();

                if(cancelRequest)
                    return;

                if(response.code() == 200 || response.code() == 400 || response.code() == 500){
                    insertUpdateResponse.postValue(response.body());
                }else{
                    Log.v("Tag", response.errorBody().string());
                    insertUpdateResponse.postValue(null);
                }

            }catch(Exception ex){
                ex.printStackTrace();
                insertUpdateResponse.postValue(null);
            }


        }

        private Call<InsertUpdateResponse> insertTodo(String user_id, String title, String desc){
            return RepoServices.getApiInterface().insertTodo(
                    user_id,
                    title,
                    desc
            );
        }

        private Call<InsertUpdateResponse> updateTodo(String user_id, String todo_id, String title, String desc){
            return RepoServices.getApiInterface().updateTodo(
                    user_id,
                    todo_id,
                    title,
                    desc
            );
        }
    }
}
