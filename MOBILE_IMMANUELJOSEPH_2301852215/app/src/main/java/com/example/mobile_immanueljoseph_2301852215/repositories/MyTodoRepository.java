package com.example.mobile_immanueljoseph_2301852215.repositories;

import androidx.lifecycle.LiveData;

import com.example.mobile_immanueljoseph_2301852215.api.MyTodoApiClient;
import com.example.mobile_immanueljoseph_2301852215.api.request.DeleteTodoBodyRequest;
import com.example.mobile_immanueljoseph_2301852215.api.response.DeleteTodosResponse;
import com.example.mobile_immanueljoseph_2301852215.api.response.GetTodosResponse;
import com.example.mobile_immanueljoseph_2301852215.util.SingleLiveEvent;

public class MyTodoRepository {
    private static MyTodoRepository instance;

    private MyTodoApiClient myTodoApiClient;

    public static MyTodoRepository getInstance(){
        return instance == null ? instance = new MyTodoRepository() : instance;
    }

    private MyTodoRepository(){
        myTodoApiClient = MyTodoApiClient.getInstance();
    }

    public LiveData<GetTodosResponse> getTodosResponse(){
        return myTodoApiClient.getTodosResponse();
    }

    public LiveData<DeleteTodosResponse> deleteTodosResponse(){
        return myTodoApiClient.getDeleteTodosResponse();
    }

    public void getTodosRequestApi(String user_id){
        myTodoApiClient.getTodosRequestApi(user_id);
    }

    public void deleteTodosRequestApi(String user_id, DeleteTodoBodyRequest body){
        myTodoApiClient.deleteTodosRequestApi(user_id, body);
    }
}
