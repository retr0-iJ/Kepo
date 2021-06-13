package com.example.mobile_immanueljoseph_2301852215.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LogoutViewModel extends ViewModel {
    private MutableLiveData<String> username;
    private MutableLiveData<String> name;

    public LogoutViewModel(){
        username = new MutableLiveData<>();
        name = new MutableLiveData<>();
    }

    public void setUserViewValue(String username, String name) {
        this.username.setValue(username);
        this.name.setValue(name);
    }

    public MutableLiveData<String> getUsername() {
        return username;
    }

    public MutableLiveData<String> getName() {
        return name;
    }
}
