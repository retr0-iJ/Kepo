package com.example.mobile_immanueljoseph_2301852215.api;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mobile_immanueljoseph_2301852215.api.response.GetTodoDetailResponse;
import com.example.mobile_immanueljoseph_2301852215.util.AppExecutors;
import com.example.mobile_immanueljoseph_2301852215.util.SingleLiveEvent;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class TodoDetailApiClient {
    private static TodoDetailApiClient instance;
    private SingleLiveEvent<GetTodoDetailResponse> todoDetailResponse;

    private RetrieveTodoDetailRunnable retrieveTodoDetailRunnable;

    private TodoDetailApiClient(){
        todoDetailResponse = new SingleLiveEvent<>();
    }

    public static TodoDetailApiClient getInstance(){
        return instance == null ? instance = new TodoDetailApiClient() : instance;
    }

    public LiveData<GetTodoDetailResponse> getTodoDetailResponse() {
        return todoDetailResponse;
    }

    public void getTodoDetailRequestApi(String user_id, String todo_id){
        if(retrieveTodoDetailRunnable != null)
            retrieveTodoDetailRunnable = null;

        retrieveTodoDetailRunnable = new TodoDetailApiClient.RetrieveTodoDetailRunnable(user_id, todo_id);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveTodoDetailRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
                Log.v("Tag", "canceled");
            }
        }, 5000, TimeUnit.MILLISECONDS);
    }

    private class RetrieveTodoDetailRunnable implements Runnable{
        private String user_id, todo_id;
        boolean cancelRequest;

        public RetrieveTodoDetailRunnable(String user_id, String todo_id) {
            this.user_id = user_id;
            this.todo_id = todo_id;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try{
                Response<GetTodoDetailResponse> response = getTodoDetailFromApi(user_id, todo_id).execute();

                if(cancelRequest)
                    return;

                if(response.code() == 200 || response.code() == 400 || response.code() == 500){
                    todoDetailResponse.postValue(response.body());
                }else{
                    Log.v("Tag", response.errorBody().string());
                    todoDetailResponse.postValue(null);
                }

            }catch(Exception ex){
                ex.printStackTrace();
                todoDetailResponse.postValue(null);
            }
        }

        private Call<GetTodoDetailResponse> getTodoDetailFromApi (String user_id, String todo_id){
            return RepoServices.getApiInterface().getTodoDetail(
                    user_id,
                    todo_id
            );
        }
    }
}
