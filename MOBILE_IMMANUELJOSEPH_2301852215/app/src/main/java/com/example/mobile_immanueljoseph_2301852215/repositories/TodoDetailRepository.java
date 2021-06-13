package com.example.mobile_immanueljoseph_2301852215.repositories;

import androidx.lifecycle.LiveData;

import com.example.mobile_immanueljoseph_2301852215.api.TodoDetailApiClient;
import com.example.mobile_immanueljoseph_2301852215.api.response.GetTodoDetailResponse;

public class TodoDetailRepository {
    private static TodoDetailRepository instance;

    private TodoDetailApiClient todoDetailApiClient;

    private TodoDetailRepository(){
        todoDetailApiClient = TodoDetailApiClient.getInstance();
    }

    public static TodoDetailRepository getInstance(){
        return instance == null ? instance = new TodoDetailRepository() : instance;
    }

    public LiveData<GetTodoDetailResponse> getTodoDetailResponse(){
        return todoDetailApiClient.getTodoDetailResponse();
    }

    public void todoDetailRequestApi(String user_id, String todo_id){
        todoDetailApiClient.getTodoDetailRequestApi(user_id, todo_id);
    }
}
