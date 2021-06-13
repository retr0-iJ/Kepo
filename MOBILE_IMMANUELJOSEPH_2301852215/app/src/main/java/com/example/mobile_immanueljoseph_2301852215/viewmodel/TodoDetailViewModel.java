package com.example.mobile_immanueljoseph_2301852215.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobile_immanueljoseph_2301852215.model.TodoDetailModel;
import com.example.mobile_immanueljoseph_2301852215.repositories.TodoDetailRepository;
import com.example.mobile_immanueljoseph_2301852215.api.response.GetTodoDetailResponse;
import com.example.mobile_immanueljoseph_2301852215.util.SingleLiveEvent;

public class TodoDetailViewModel extends ViewModel {
    private MutableLiveData<TodoDetailModel> todo = new MutableLiveData<>();

    private TodoDetailRepository todoDetailRepository;

    public TodoDetailViewModel(){
        todoDetailRepository = TodoDetailRepository.getInstance();
    }

    public void setTodo(TodoDetailModel todo) {
        this.todo.setValue(todo);
    }

    public LiveData<TodoDetailModel> getTodo() {
        return todo;
    }

    public LiveData<GetTodoDetailResponse> getTodoDetailResponse(){
        return todoDetailRepository.getTodoDetailResponse();
    }

    public void todoDetailRequestApi(String user_id, String todo_id){
        todoDetailRepository.todoDetailRequestApi(user_id, todo_id);
    }
}
