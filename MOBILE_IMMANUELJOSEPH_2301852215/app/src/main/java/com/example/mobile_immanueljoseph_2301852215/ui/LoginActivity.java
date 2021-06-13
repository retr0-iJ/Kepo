package com.example.mobile_immanueljoseph_2301852215.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.mobile_immanueljoseph_2301852215.R;
import com.example.mobile_immanueljoseph_2301852215.databinding.ActivityLoginBinding;
import com.example.mobile_immanueljoseph_2301852215.model.LoginModel;
import com.example.mobile_immanueljoseph_2301852215.model.UserModel;
import com.example.mobile_immanueljoseph_2301852215.api.response.LoginResponse;
import com.example.mobile_immanueljoseph_2301852215.util.SharedPref;
import com.example.mobile_immanueljoseph_2301852215.viewmodel.LoginViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    private ActivityLoginBinding binding;

    private BottomSheetDialog mBottomSheetDialog;
    private TextView title;
    private SharedPref pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = new SharedPref(this);

        confBottomSheetDialog();

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        confBinding();
        confInputObserver();
        confLoginResponseObserver();
    }

    private void confBottomSheetDialog() {
        final View bottomSheetLayout = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null);
        title = bottomSheetLayout.findViewById(R.id.tv_detail);
        (bottomSheetLayout.findViewById(R.id.btn_sheet_dialog)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        mBottomSheetDialog = new BottomSheetDialog(this);
        mBottomSheetDialog.setContentView(bottomSheetLayout);
    }

    private void confBinding() {
        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        binding.setLifecycleOwner(this);
        binding.setLoginViewModel(loginViewModel);
    }

    private void confInputObserver() {
        final Observer<LoginModel> observer = new Observer<LoginModel>() {
            @Override
            public void onChanged(LoginModel loginModel) {
                if (TextUtils.isEmpty(Objects.requireNonNull(loginModel).getUsername()) ||
                        TextUtils.isEmpty(Objects.requireNonNull(loginModel).getPassword())) {
                    title.setText("Please input username and password");
                    mBottomSheetDialog.show();
                } else {
                    binding.btnLogin.setEnabled(false);
                    binding.progressBarLayout.progressBar.setVisibility(View.VISIBLE);
                    loginViewModel.loginRequestApi();
                }
            }
        };
        loginViewModel.getLoginData().observe(this, observer);
    }

    private void confLoginResponseObserver() {
        final Observer<LoginResponse> observer = new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                binding.btnLogin.setEnabled(true);
                binding.progressBarLayout.progressBar.setVisibility(View.GONE);
                try {
                    if (loginResponse.getCode() == 200) {
                        if (loginResponse.getMsg().equals("User not found")) {
                            title.setText("User not found");
                            mBottomSheetDialog.show();
                        } else if (loginResponse.getMsg().equals("Username or password is incorrect")) {
                            title.setText("Username or password is incorrect");
                            mBottomSheetDialog.show();
                        } else {
                            UserModel user = loginResponse.getUserCredentials();
                            pref.save(user.getUserId(), user.getUsername(), user.getName());
                            goToHomeActivity();
                        }
                    } else {
                        title.setText("Something wrong occured while logging in");
                        mBottomSheetDialog.show();
                    }
                } catch (Exception e) {
                    title.setText("No internet connection or Api request taking too long");
                    mBottomSheetDialog.show();
                }
            }
        };
        loginViewModel.getLoginResponse().observe(this, observer);
    }

    private void goToHomeActivity() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}