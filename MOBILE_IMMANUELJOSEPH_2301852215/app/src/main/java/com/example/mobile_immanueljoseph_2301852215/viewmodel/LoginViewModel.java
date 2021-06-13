package com.example.mobile_immanueljoseph_2301852215.viewmodel;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobile_immanueljoseph_2301852215.model.LoginModel;
import com.example.mobile_immanueljoseph_2301852215.repositories.LoginRepository;
import com.example.mobile_immanueljoseph_2301852215.api.response.LoginResponse;

public class LoginViewModel extends ViewModel {
    public MutableLiveData<String> username = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();

    private MutableLiveData<LoginModel> loginMutableLiveData;

    private LoginRepository loginRepository;

    public LoginViewModel() {
        loginRepository = LoginRepository.getInstance();
    }

    public LiveData<LoginModel> getLoginData(){
        return loginMutableLiveData == null ? loginMutableLiveData = new MutableLiveData<>() : loginMutableLiveData;
    }

    public void onClick(View view){
        LoginModel loginModel = new LoginModel(username.getValue(), password.getValue());

        loginMutableLiveData.setValue(loginModel);
    }

    public LiveData<LoginResponse> getLoginResponse(){
        return loginRepository.getLoginResponse();
    }

    public void loginRequestApi(){
        loginRepository.loginRequestApi(this.getLoginData().getValue().getUsername(), this.getLoginData().getValue().getPassword());
    }
}
