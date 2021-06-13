package com.example.mobile_immanueljoseph_2301852215.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.mobile_immanueljoseph_2301852215.adapters.MyTodoAdapter;
import com.example.mobile_immanueljoseph_2301852215.api.request.DeleteTodoBodyRequest;
import com.example.mobile_immanueljoseph_2301852215.api.response.DeleteTodosResponse;
import com.example.mobile_immanueljoseph_2301852215.databinding.ActivityMyTodoBinding;
import com.example.mobile_immanueljoseph_2301852215.api.response.GetTodosResponse;
import com.example.mobile_immanueljoseph_2301852215.util.SharedPref;
import com.example.mobile_immanueljoseph_2301852215.util.SnackBarBehaviour;
import com.example.mobile_immanueljoseph_2301852215.viewmodel.MyTodoViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;
import android.widget.Toast;

import com.example.mobile_immanueljoseph_2301852215.R;
import com.google.android.material.snackbar.Snackbar;

public class MyTodoActivity extends AppCompatActivity {
    public static final int ADD_TODO_REQUEST = 1;
    public static final int OTHER_TODO_REQUEST = 2;

    private ActivityMyTodoBinding binding;
    private SharedPref pref;
    private MyTodoViewModel viewModel;
    private MyTodoAdapter adapter;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        confBinding();
        pref = new SharedPref(this);

        viewModel = new ViewModelProvider(this).get(MyTodoViewModel.class);
        confGetTodosObserver();
        confDeleteTodosObserver();
        confRv();
        confToolbar();
        setListener();
        loadTodos();
    }

    private void setListener() {
        binding.ivBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddNewTodo();
            }
        });
    }

    private void confBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_todo);
        binding.setLifecycleOwner(this);
    }

    private void confToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void loadTodos() {
        binding.progressBarLayout.progressBar.setVisibility(View.VISIBLE);
        viewModel.getTodosRequestApi(pref.getUserId());
    }

    private void confGetTodosObserver(){
        final Observer<GetTodosResponse> observer = new Observer<GetTodosResponse>() {
            @Override
            public void onChanged(GetTodosResponse response) {
                binding.progressBarLayout.progressBar.setVisibility(View.GONE);
                try{
                    response.getData().getListTodo().get(0);
                    if(binding.rvTodos.getRoot().getVisibility() != View.VISIBLE)
                        switchViewLayout();
                    adapter.setTodos(response.getData().getListTodo());
                }catch(Exception e){
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

    private void confDeleteTodosObserver(){
        final Observer<DeleteTodosResponse> observer = new Observer<DeleteTodosResponse>() {
            @Override
            public void onChanged(DeleteTodosResponse response) {
                String text;
                if(response == null)
                    text = "No Internet connection or Api can't be reached";
                else {
                    loadTodos();
                    text = response.getMsg();
                }
                Toast.makeText(MyTodoActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        };
        viewModel.deleteTodosResponse().observe(this, observer);
    }

    private void confRv(){
        adapter = new MyTodoAdapter();
        binding.rvTodos.rvCardviewList.setAdapter(adapter);
        binding.rvTodos.rvCardviewList.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(new MyTodoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String todo_id) {
                Intent intent = new Intent(MyTodoActivity.this, TodoDetailActivity.class);
                intent.putExtra(TodoDetailActivity.EXTRA_USERID, pref.getUserId());
                intent.putExtra(TodoDetailActivity.EXTRA_TODOID, todo_id);
                startActivityForResult(intent, OTHER_TODO_REQUEST);
            }
        });

        adapter.setOnCheckBoxClickListener(new MyTodoAdapter.OnCheckBoxClickListener() {
            @Override
            public void onCheckBoxClick(String todo_id, boolean isChecked) {
                if(isChecked)
                    adapter.addSelectedTodos(todo_id);
                else
                    adapter.removeSelectedTodo(todo_id);
                int count = adapter.getSelectedTodosCount();
                if(count > 0) {
                    snackbar = Snackbar.make(binding.getRoot().getRootView(), count + " item(s)", Snackbar.LENGTH_INDEFINITE)
                        .setBehavior(new SnackBarBehaviour())
                        .setAction("DELETE", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MyTodoActivity.this);
                                builder
                                .setTitle("Delete Todo")
                                .setMessage("Are you sure want to delete all this todos?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                onDeleteSelectedTodos();
                                            }
                                        })
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                snackbar.show();
                                            }
                                        });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }
                        })
                        .setActionTextColor(Color.RED);
                    snackbar.show();
                }else
                    snackbar.dismiss();
            }
        });
    }

    private void onDeleteSelectedTodos() {
        DeleteTodoBodyRequest body = new DeleteTodoBodyRequest(adapter.getSelectedTodos());
        binding.progressBarLayout.progressBar.setVisibility(View.VISIBLE);
        viewModel.deleteTodosRequestApi(pref.getUserId(), body);
        resetSnackbar();
    }

    private void switchViewLayout() {
        binding.rvTodos.getRoot().setVisibility(binding.rvTodos.getRoot().getVisibility() != View.VISIBLE ? View.VISIBLE : View.GONE);
        binding.fillPage.getRoot().setVisibility(binding.fillPage.getRoot().getVisibility() != View.VISIBLE ? View.VISIBLE : View.GONE);
    }

    private void onAddNewTodo() {
        Intent intent = new Intent(MyTodoActivity.this, InsertUpdateTodoActivity.class);
        startActivityForResult(intent, ADD_TODO_REQUEST);
    }

    private void resetSnackbar(){
        adapter.clearSelectedTodos();
        if(snackbar.isShown())
            snackbar.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(snackbar != null)
            resetSnackbar();
        loadTodos();
        if(requestCode == ADD_TODO_REQUEST && resultCode == RESULT_OK){
            Toast.makeText(this, "Todo created successfully", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}