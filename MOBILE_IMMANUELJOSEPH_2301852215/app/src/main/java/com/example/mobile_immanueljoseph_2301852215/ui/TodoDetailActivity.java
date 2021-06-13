package com.example.mobile_immanueljoseph_2301852215.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mobile_immanueljoseph_2301852215.databinding.ActivityTodoDetailBinding;
import com.example.mobile_immanueljoseph_2301852215.model.TodoDetailModel;
import com.example.mobile_immanueljoseph_2301852215.api.response.GetTodoDetailResponse;
import com.example.mobile_immanueljoseph_2301852215.util.SharedPref;
import com.example.mobile_immanueljoseph_2301852215.viewmodel.TodoDetailViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobile_immanueljoseph_2301852215.R;

public class TodoDetailActivity extends AppCompatActivity {
    public static final String EXTRA_USERID = "EXTRA_USERID";
    public static final String EXTRA_TODOID = "EXTRA_TODOID";
    public static final int UPDATE_TODO_REQUEST = 3;

    private SharedPref pref;
    private ActivityTodoDetailBinding binding;
    private TodoDetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(TodoDetailViewModel.class);
        pref = new SharedPref(this);
        confBinding();
        confToolbarNfab();
        confResponseObserver();
        setListener();

        getTodoDetailFromApi();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == UPDATE_TODO_REQUEST && resultCode == RESULT_OK) {
            getTodoDetailFromApi();
            Toast.makeText(this, "Update todo success", Toast.LENGTH_SHORT).show();
        }
    }

    private void getTodoDetailFromApi() {
        try{
            binding.progressBarLayout.progressBar.setVisibility(View.VISIBLE);
            viewModel.todoDetailRequestApi(getIntentUserID(), getIntentTodoID());
        }catch(Exception e){
            switchViewLayout();
            binding.fillPage.tvFillPageText.setText("Error happened");
        }
    }

    private void confResponseObserver() {
        final Observer<GetTodoDetailResponse> observer = new Observer<GetTodoDetailResponse>() {
            @Override
            public void onChanged(GetTodoDetailResponse response) {
                String text;
                binding.progressBarLayout.progressBar.setVisibility(View.GONE);
                try{
                    if(response.getCode() == 200 && response.getData() != null) {
                        viewModel.setTodo(response.getData());
                        if(binding.contentDetailTodo.getRoot().getVisibility() != View.VISIBLE)
                            switchViewLayout();
                        return;
                    }
                    text = response.getMsg();
                }catch(Exception e){
                    text = "No internet connection or Api can't be reached";
                }

                switchViewLayout();
                binding.fillPage.tvFillPageText.setText(text);
            }
        };
        viewModel.getTodoDetailResponse().observe(this, observer);
    }

    private void switchViewLayout() {
        binding.contentDetailTodo.getRoot().setVisibility(binding.contentDetailTodo.getRoot().getVisibility() != View.VISIBLE ? View.VISIBLE : View.GONE);
        binding.fillPage.getRoot().setVisibility(binding.fillPage.getRoot().getVisibility() != View.VISIBLE ? View.VISIBLE : View.GONE);
        if(isMyTodo())
            binding.fab.setVisibility(binding.fab.getVisibility() != View.VISIBLE ? View.VISIBLE : View.GONE);
    }

    private void setListener() {
        binding.ivBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isMyTodo())
                    setResult(RESULT_CANCELED);
                finish();
            }
        });

        if(isMyTodo())
            binding.fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onUpdateTodo();
                }
            });
    }

    private boolean isMyTodo() {
        return getIntentUserID().equals(pref.getUserId());
    }

    private String getIntentUserID() {
        return getIntent().getStringExtra(EXTRA_USERID);
    }

    private String getIntentTodoID(){
        return getIntent().getStringExtra(EXTRA_TODOID);
    }

    private void onUpdateTodo() {
        Intent intent = new Intent(this, InsertUpdateTodoActivity.class);
        TodoDetailModel data = viewModel.getTodo().getValue();
        intent.putExtra(InsertUpdateTodoActivity.EXTRA_TODO_OBJECT, data);
        startActivityForResult(intent, UPDATE_TODO_REQUEST);
    }

    private void confBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_todo_detail);
        binding.setLifecycleOwner(this);
        binding.setMainViewmodel(viewModel);
    }

    private void confToolbarNfab() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if(!isMyTodo())
            binding.fab.setVisibility(View.GONE);
    }
}