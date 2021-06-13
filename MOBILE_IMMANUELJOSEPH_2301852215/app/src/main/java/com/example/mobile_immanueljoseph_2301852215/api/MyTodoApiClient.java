package com.example.mobile_immanueljoseph_2301852215.api;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mobile_immanueljoseph_2301852215.api.request.DeleteTodoBodyRequest;
import com.example.mobile_immanueljoseph_2301852215.api.response.DeleteTodosResponse;
import com.example.mobile_immanueljoseph_2301852215.api.response.GetTodosResponse;
import com.example.mobile_immanueljoseph_2301852215.util.AppExecutors;
import com.example.mobile_immanueljoseph_2301852215.util.SingleLiveEvent;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MyTodoApiClient {

    private static MyTodoApiClient instance;
    private SingleLiveEvent<GetTodosResponse> getTodosResponse;
    private SingleLiveEvent<DeleteTodosResponse> deleteTodosResponse;

    private RetrieveGetTodosRunnable retrieveGetTodosRunnable;
    private RetrieveDeleteTodosRunnable retrieveDeleteTodosRunnable;

    private MyTodoApiClient(){
        getTodosResponse = new SingleLiveEvent<>();
        deleteTodosResponse = new SingleLiveEvent<>();
    }

    public static MyTodoApiClient getInstance(){
        return instance == null ? instance = new MyTodoApiClient() : instance;
    }

    public LiveData<GetTodosResponse> getTodosResponse(){
        return getTodosResponse;
    }

    public LiveData<DeleteTodosResponse> getDeleteTodosResponse(){
        return deleteTodosResponse;
    }

    public void getTodosRequestApi(String user_id){
        if(retrieveGetTodosRunnable != null)
            retrieveGetTodosRunnable = null;

        retrieveGetTodosRunnable = new RetrieveGetTodosRunnable(user_id);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveGetTodosRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling Request
                myHandler.cancel(true);
                Log.v("Tag", "canceled");
            }
        }, 5000, TimeUnit.MILLISECONDS);
    }

    public void deleteTodosRequestApi(String user_id, DeleteTodoBodyRequest body){
        if(retrieveDeleteTodosRunnable != null)
            retrieveDeleteTodosRunnable = null;

        retrieveDeleteTodosRunnable = new RetrieveDeleteTodosRunnable(user_id, body);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveDeleteTodosRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling Request
                myHandler.cancel(true);
                Log.v("Tag", "canceled");
            }
        }, 5000, TimeUnit.MILLISECONDS);
    }

    private class RetrieveGetTodosRunnable implements Runnable{
        private String user_id;
        boolean cancelRequest;

        public RetrieveGetTodosRunnable(String user_id) {
            this.user_id = user_id;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try{
                Response<GetTodosResponse> response = getTodosFromApi(user_id).execute();

                if(cancelRequest)
                    return;

                if (response.code() == 200 || response.code() == 500) {
                    getTodosResponse.postValue(response.body());
                } else {
                    Log.v("Tag", response.errorBody().string());
                    getTodosResponse.postValue(null);
                }

            }catch(Exception ex){
                ex.printStackTrace();
                getTodosResponse.postValue(null);
            }
        }

        private Call<GetTodosResponse> getTodosFromApi(String user_id){
            return RepoServices.getApiInterface().getMyTodos(
                    user_id
            );
        }
    }

    private class RetrieveDeleteTodosRunnable implements Runnable{
        private String user_id;
        private DeleteTodoBodyRequest body;
        boolean cancelRequest;

        public RetrieveDeleteTodosRunnable(String user_id, DeleteTodoBodyRequest body) {
            this.user_id = user_id;
            this.body = body;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try{
                Response<DeleteTodosResponse> response = deleteTodosFromApi(user_id, body).execute();

                if(cancelRequest)
                    return;

                if (response.code() == 200 || response.code() == 400 || response.code() == 500) {
                    deleteTodosResponse.postValue(response.body());
                } else {
                    Log.v("Tag", response.errorBody().string());
                    deleteTodosResponse.postValue(null);
                }

            }catch(Exception ex){
                Log.v("tag", ex.getMessage());
                ex.printStackTrace();
                deleteTodosResponse.postValue(null);
            }
        }

        private Call<DeleteTodosResponse> deleteTodosFromApi(String user_id, DeleteTodoBodyRequest body){
            return RepoServices.getApiInterface().deleteTodos(
                    user_id,
                    body
            );
        }
    }
}
