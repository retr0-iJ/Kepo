package com.example.mobile_immanueljoseph_2301852215.ui;

import android.graphics.Color;
import android.os.Bundle;

import com.example.mobile_immanueljoseph_2301852215.databinding.ActivityInsertUpdateTodoBinding;
import com.example.mobile_immanueljoseph_2301852215.model.InsertUpdateModel;
import com.example.mobile_immanueljoseph_2301852215.model.TodoDetailModel;
import com.example.mobile_immanueljoseph_2301852215.api.response.InsertUpdateResponse;
import com.example.mobile_immanueljoseph_2301852215.util.SharedPref;
import com.example.mobile_immanueljoseph_2301852215.viewmodel.InsertUpdateTodoViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.example.mobile_immanueljoseph_2301852215.R;

import java.util.Objects;

public class InsertUpdateTodoActivity extends AppCompatActivity {
    public static final String EXTRA_TODO_OBJECT = "EXTRA_TODO_OBJECT";

    private SharedPref pref;
    private ActivityInsertUpdateTodoBinding binding;
    private InsertUpdateTodoViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(InsertUpdateTodoViewModel.class);
        pref = new SharedPref(this);

        confBinding();
        confToolbar();
        initViewValues();
        setListener();
        confInputObserver();
        confInputUpdateTodoResponseObserver();
    }

    private void initViewValues() {
        String insertUpdateTitle;
        viewModel.descLength.setValue(0);
        if(getIntent().hasExtra(EXTRA_TODO_OBJECT)) {
            insertUpdateTitle = "Update Todo";
            viewModel.setUpdateValue((TodoDetailModel) getIntent().getSerializableExtra(EXTRA_TODO_OBJECT));
        }else
            insertUpdateTitle = "Create Todo";
        binding.tvInsertUpdateTitle.setText(insertUpdateTitle);
    }

    private void setListener() {
        binding.ivBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.contentInsertUpdateTodo.etDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.toString().replaceAll("[\\n\\t ]", "").length();
                if(viewModel.warning.getValue() != null)
                    viewModel.warning.setValue(null);
                if(length > 100)
                    binding.contentInsertUpdateTodo.tvDescLength.setTextColor(Color.RED);
                else
                    binding.contentInsertUpdateTodo.tvDescLength.setTextColor(binding.tvSubtitle.getCurrentTextColor());

                viewModel.descLength.postValue(length);
            }
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String todo_id;

                try{
                    todo_id = ((TodoDetailModel)getIntent().getSerializableExtra(EXTRA_TODO_OBJECT)).getTodo_id();
                }catch(Exception e){
                    todo_id = "";
                }

                viewModel.onClickFab(todo_id);
            }
        });
    }

    private void confToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void confBinding() {
        binding = DataBindingUtil.setContentView(InsertUpdateTodoActivity.this, R.layout.activity_insert_update_todo);
        binding.setLifecycleOwner(this);
        binding.setMainViewmodel(viewModel);
    }

    private void confInputObserver(){
        final Observer<InsertUpdateModel> observer = new Observer<InsertUpdateModel>() {
            @Override
            public void onChanged(InsertUpdateModel insertUpdateModel) {
                try {
                    InsertUpdateModel data = viewModel.getInsertUpdateData().getValue();
                    if(TextUtils.isEmpty(Objects.requireNonNull(data).getTitle()) ||
                            TextUtils.isEmpty(Objects.requireNonNull(data).getDescription())) {
                        viewModel.warning.setValue("Text field cannot be empty");
                    }else if(data.isDescValid() == -1)
                        viewModel.warning.setValue("Your description exceeded the maximum words");
                    else{
                        binding.progressBarLayout.progressBar.setVisibility(View.VISIBLE);
                        viewModel.insertUpdateRequestApi(pref.getUserId(), data.getTodo_id(), data.getTitle(), data.getDescription());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        viewModel.getInsertUpdateData().observe(this, observer);
    }

    private void confInputUpdateTodoResponseObserver(){
        final Observer<InsertUpdateResponse> observer = new Observer<InsertUpdateResponse>() {
            @Override
            public void onChanged(InsertUpdateResponse response) {
                binding.progressBarLayout.progressBar.setVisibility(View.GONE);
                if(response == null)
                    viewModel.warning.setValue("No internet connection or Api can't be reached");
                else if(response.getCode() == 200) {
                    setResult(RESULT_OK);
                    finish();
                }
                else if(response.getCode() == 500)
                    viewModel.warning.setValue(response.getMsg());
            }
        };
        viewModel.getInsertUpdateResponse().observe(this, observer);
    }
}