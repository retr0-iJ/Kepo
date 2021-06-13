package com.example.mobile_immanueljoseph_2301852215.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.mobile_immanueljoseph_2301852215.R;
import com.example.mobile_immanueljoseph_2301852215.adapters.SearchTodosAdapter;
import com.example.mobile_immanueljoseph_2301852215.api.response.SearchTodosResponse;
import com.example.mobile_immanueljoseph_2301852215.databinding.ActivitySearchTodoBinding;
import com.example.mobile_immanueljoseph_2301852215.model.SearchTodoModel;
import com.example.mobile_immanueljoseph_2301852215.util.SharedPref;
import com.example.mobile_immanueljoseph_2301852215.viewmodel.SearchTodoViewModel;

import java.util.ArrayList;

public class SearchTodoActivity extends AppCompatActivity {
    public static final int DETAIL_TODO_REQUEST = 1;

    private ActivitySearchTodoBinding binding;
    private SharedPref pref;
    private SearchTodoViewModel viewModel;
    private SearchTodosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = new SharedPref(this);
        viewModel = new ViewModelProvider(this).get(SearchTodoViewModel.class);

        confBinding();
        confRv();
        confToolbar();
        confSearchResponseObserver();
        setListener();
    }

    private void setListener() {
        binding.toolbarLayout.ivBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.toolbarLayout.ivSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = binding.toolbarLayout.etSearchBar.getText().toString();
                int filterUser = binding.cbByUser.isChecked() ? 1 : 0;
                int filterTodo = binding.cbByTodo.isChecked() ? 1 : 0;

                if(searchQuery.equals("")){
                    Toast.makeText(SearchTodoActivity.this, "Text field cannot be empty", Toast.LENGTH_SHORT).show();
                }else if (filterUser == 0 && filterTodo == 0){
                    Toast.makeText(SearchTodoActivity.this, "You must choose either to search by user, todo, or both", Toast.LENGTH_SHORT).show();
                }else{
                    hideItemVisibility();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    binding.progressBarLayout.progressBar.setVisibility(View.VISIBLE);
                    viewModel.searchTodosRequestApi(searchQuery, filterUser, filterTodo);
                }
            }
        });
    }

    private void confSearchResponseObserver() {
        final Observer<SearchTodosResponse> observer = new Observer<SearchTodosResponse>() {
            @Override
            public void onChanged(SearchTodosResponse response) {
                binding.progressBarLayout.progressBar.setVisibility(View.GONE);
                binding.tvResult.setVisibility(View.VISIBLE);
                try{
                    response.getData().get(0);
                    if(binding.rvTodos.getRoot().getVisibility() != View.VISIBLE)
                        switchViewLayout();
                    adapter.setTodos(response.getData());
                }catch(Exception e){
                    String text;
                    adapter.setTodos(new ArrayList<>());
                    if(response == null)
                        text = "No internet connection or Api can't be reached";
                    else if(response.getCode() == 200)
                        text = "No Data";
                    else
                        text = response.getMsg();
                    if(binding.fillPage.getRoot().getVisibility() != View.VISIBLE)
                        switchViewLayout();
                    binding.fillPage.tvFillPageText.setText(text);
                }
            }
        };
        viewModel.getSearchTodosResponse().observe(this, observer);
    }

    private void confRv() {
        adapter = new SearchTodosAdapter();
        binding.rvTodos.rvCardviewList.setAdapter(adapter);
        binding.rvTodos.rvCardviewList.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(new SearchTodosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SearchTodoModel todos) {
                Intent intent = new Intent(SearchTodoActivity.this, TodoDetailActivity.class);
                intent.putExtra(TodoDetailActivity.EXTRA_USERID, todos.getUserId());
                intent.putExtra(TodoDetailActivity.EXTRA_TODOID, todos.getTodoId());
                startActivityForResult(intent, DETAIL_TODO_REQUEST);
            }
        });
    }

    private void confBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_todo);
        binding.setLifecycleOwner(this);
        binding.setViewmodel(viewModel);
    }

    private void confToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void switchViewLayout() {
        binding.rvTodos.getRoot().setVisibility(binding.rvTodos.getRoot().getVisibility() != View.VISIBLE ? View.VISIBLE : View.GONE);
        binding.fillPage.getRoot().setVisibility(binding.fillPage.getRoot().getVisibility() != View.VISIBLE ? View.VISIBLE : View.GONE);
    }

    private void resetCheckBox(){
        if(binding.cbByUser.isChecked())
            binding.cbByUser.setChecked(false);
        if(binding.cbByTodo.isChecked())
            binding.cbByTodo.setChecked(false);
    }

    private void hideItemVisibility(){
        binding.toolbarLayout.etSearchBar.setText("");
        binding.tvResult.setVisibility(View.GONE);
        if(binding.fillPage.getRoot().getVisibility() == View.VISIBLE)
            binding.fillPage.tvFillPageText.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        resetCheckBox();
    }
}