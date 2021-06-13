package com.example.mobile_immanueljoseph_2301852215.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mobile_immanueljoseph_2301852215.R;
import com.example.mobile_immanueljoseph_2301852215.adapters.SearchTodosAdapter;
import com.example.mobile_immanueljoseph_2301852215.api.response.GetTodosResponse;
import com.example.mobile_immanueljoseph_2301852215.databinding.ActivityUserDetailBinding;
import com.example.mobile_immanueljoseph_2301852215.model.SearchTodoModel;
import com.example.mobile_immanueljoseph_2301852215.viewmodel.UserDetailViewModel;

public class UserDetailActivity extends AppCompatActivity {
    public static final String EXTRA_USERID = "EXTRA_USERID";
    public static final int DETAIL_TODO_REQUEST = 1;

    private ActivityUserDetailBinding binding;
    private UserDetailViewModel viewModel;
    private SearchTodosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(UserDetailViewModel.class);

        confBinding();
        confRv();
        confGetTodosResponse();
        confToolbar();
        setListener();
        loadTodos();
    }

    private void loadTodos() {
        binding.progressBarLayout.progressBar.setVisibility(View.VISIBLE);
        viewModel.getTodosRequestApi(getIntentUserId());
    }

    private void setListener() {
        binding.ivBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void confRv() {
        adapter = new SearchTodosAdapter();
        binding.rvTodos.rvCardviewList.setAdapter(adapter);
        binding.rvTodos.rvCardviewList.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(new SearchTodosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SearchTodoModel todos) {
                Intent intent = new Intent(UserDetailActivity.this, TodoDetailActivity.class);
                intent.putExtra(TodoDetailActivity.EXTRA_USERID, todos.getUserId());
                intent.putExtra(TodoDetailActivity.EXTRA_TODOID, todos.getTodoId());
                startActivity(intent);
            }
        });
    }

    private void confGetTodosResponse() {
        final Observer<GetTodosResponse> observer = new Observer<GetTodosResponse>() {
            @Override
            public void onChanged(GetTodosResponse response) {
                binding.progressBarLayout.progressBar.setVisibility(View.GONE);
                try {
                    GetTodosResponse.TodosResponseModel data = response.getData();
                    if (binding.rvTodos.getRoot().getVisibility() != View.VISIBLE)
                        switchViewLayout();
                    adapter.setTodos(data.getSearchTodoModelArrayList());
                    viewModel.setView(data.getUsername(), data.getName(), String.valueOf(adapter.getItemCount()));
                } catch (Exception e) {
                    switchViewLayout();
                    String text;
                    if(response == null)
                        text = "No internet connection or Api can't be reached";
                    else{
                        if(response.getCode() == 200 && response.getMsg().equals(""))
                            text = "No Data";
                        else
                            text = response.getMsg();
                    }
                    binding.fillPage.tvFillPageText.setText(text);
                }
            }
        };
        viewModel.getTodosResponse().observe(this, observer);
    }

    private void confBinding(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_detail);
        binding.setLifecycleOwner(this);
        binding.setViewmodel(viewModel);
    }

    private void confToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private String getIntentUserId(){
        return getIntent().getStringExtra(EXTRA_USERID);
    }

    private void switchViewLayout() {
        binding.rvTodos.getRoot().setVisibility(binding.rvTodos.getRoot().getVisibility() != View.VISIBLE ? View.VISIBLE : View.GONE);
        binding.fillPage.getRoot().setVisibility(binding.fillPage.getRoot().getVisibility() != View.VISIBLE ? View.VISIBLE : View.GONE);
    }
}