package com.example.mobile_immanueljoseph_2301852215.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.mobile_immanueljoseph_2301852215.R;
import com.example.mobile_immanueljoseph_2301852215.databinding.ActivityHomeBinding;
import com.example.mobile_immanueljoseph_2301852215.util.SharedPref;

public class HomeActivity extends AppCompatActivity {
    private static final int REQUEST_LOGIN_CODE = 1;

    private SharedPref pref;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = new SharedPref(this);

        confBinding();
        setWelcomeText();
        setListener();
    }

    private void setListener() {
        binding.cvTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MyTodoActivity.class);
                startActivity(intent);
            }
        });

        binding.cvSearchTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SearchTodoActivity.class);
                startActivity(intent);
            }
        });

        binding.cvSearchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SearchUserActivity.class);
                startActivity(intent);
            }
        });

        binding.cvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LogoutActivity.class);
                startActivity(intent);
            }
        });
    }

    private void confBinding(){
        binding = DataBindingUtil.setContentView(HomeActivity.this, R.layout.activity_home);
        binding.setLifecycleOwner(this);
    }

    private void setWelcomeText(){
        binding.tvWelcome.setText("Welcome, " + pref.getName());
    }
}