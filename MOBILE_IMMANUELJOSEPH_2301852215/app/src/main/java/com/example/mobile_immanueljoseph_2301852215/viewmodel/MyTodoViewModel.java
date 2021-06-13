package com.example.mobile_immanueljoseph_2301852215.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobile_immanueljoseph_2301852215.api.request.DeleteTodoBodyRequest;
import com.example.mobile_immanueljoseph_2301852215.api.response.DeleteTodosResponse;
import com.example.mobile_immanueljoseph_2301852215.repositories.MyTodoRepository;
import com.example.mobile_immanueljoseph_2301852215.api.response.GetTodosResponse;

public class MyTodoViewModel extends ViewModel {
    private MyTodoRepository myTodoRepository;

    public MyTodoViewModel(){
        myTodoRepository = MyTodoRepository.getInstance();
    }

    public LiveData<GetTodosResponse> getTodosResponse(){
        return myTodoRepository.getTodosResponse();
    }

    public LiveData<DeleteTodosResponse> deleteTodosResponse(){
        return myTodoRepository.deleteTodosResponse();
    }

    public void getTodosRequestApi(String user_id){
        myTodoRepository.getTodosRequestApi(user_id);
    }

    public void deleteTodosRequestApi(String user_id, DeleteTodoBodyRequest body){
        myTodoRepository.deleteTodosRequestApi(user_id, body);
    }
}
