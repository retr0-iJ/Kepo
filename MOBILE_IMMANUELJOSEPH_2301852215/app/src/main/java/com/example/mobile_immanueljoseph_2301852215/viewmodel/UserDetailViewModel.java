package com.example.mobile_immanueljoseph_2301852215.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobile_immanueljoseph_2301852215.api.response.GetTodosResponse;
import com.example.mobile_immanueljoseph_2301852215.repositories.MyTodoRepository;

public class UserDetailViewModel extends ViewModel {
    private MutableLiveData<String> username = new MutableLiveData<>();
    private MutableLiveData<String> name = new MutableLiveData<>();
    private MutableLiveData<String> todosCount = new MutableLiveData<>("");

    private MyTodoRepository userDetailRepository;

    public UserDetailViewModel(){
        userDetailRepository = MyTodoRepository.getInstance();
    }

    public MutableLiveData<String> getUsername() {
        return username;
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public MutableLiveData<String> getTodosCount() {
        return todosCount;
    }

    public void setView(String username, String name, String todosCount){
        this.username.setValue(username);
        this.name.setValue(name);
        this.todosCount.setValue(todosCount);
    }

    public void getTodosRequestApi(String user_id){
        userDetailRepository.getTodosRequestApi(user_id);
    }

    public LiveData<GetTodosResponse> getTodosResponse(){
        return userDetailRepository.getTodosResponse();
    }
}
