package com.example.mobile_immanueljoseph_2301852215.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mobile_immanueljoseph_2301852215.R;
import com.example.mobile_immanueljoseph_2301852215.databinding.ActivityLogoutBinding;
import com.example.mobile_immanueljoseph_2301852215.util.SharedPref;
import com.example.mobile_immanueljoseph_2301852215.viewmodel.LogoutViewModel;

public class LogoutActivity extends AppCompatActivity {
    ActivityLogoutBinding binding;
    LogoutViewModel viewModel;
    SharedPref pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        pref = new SharedPref(this);
        viewModel = new ViewModelProvider(this).get(LogoutViewModel.class);

        confBinding();
        confToolbar();
        setListener();
        setUserView();
    }

    private void setUserView() {
        viewModel.setUserViewValue(pref.getUsername(), pref.getName());
    }

    private void setListener() {
        binding.ivBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupAlertDialog();
            }
        });
    }

    private void popupAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LogoutActivity.this);
        builder
                .setTitle("Logout")
                .setMessage("Are you sure want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        pref.clearSharedPref();
                        Intent intent = new Intent(LogoutActivity.this, SplashScreenActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void confBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_logout);
        binding.setLifecycleOwner(this);
        binding.setViewmodel(viewModel);
    }

    private void confToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}