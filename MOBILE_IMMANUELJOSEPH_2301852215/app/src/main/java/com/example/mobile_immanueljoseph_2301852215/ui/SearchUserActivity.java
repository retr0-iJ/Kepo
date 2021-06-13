package com.example.mobile_immanueljoseph_2301852215.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.mobile_immanueljoseph_2301852215.R;
import com.example.mobile_immanueljoseph_2301852215.adapters.SearchUsersAdapter;
import com.example.mobile_immanueljoseph_2301852215.api.response.SearchUsersResponse;
import com.example.mobile_immanueljoseph_2301852215.databinding.ActivitySearchUserBinding;
import com.example.mobile_immanueljoseph_2301852215.model.UserModel;
import com.example.mobile_immanueljoseph_2301852215.util.SharedPref;
import com.example.mobile_immanueljoseph_2301852215.viewmodel.SearchUserViewModel;

import java.util.ArrayList;

public class SearchUserActivity extends AppCompatActivity {

    private ActivitySearchUserBinding binding;
    private SharedPref pref;
    private SearchUserViewModel viewModel;
    private SearchUsersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = new SharedPref(this);
        viewModel = new ViewModelProvider(this).get(SearchUserViewModel.class);

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

                if(searchQuery.equals("")){
                    Toast.makeText(SearchUserActivity.this, "Text field cannot be empty", Toast.LENGTH_SHORT).show();
                }else{
                    hideItemVisibility();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    binding.progressBarLayout.progressBar.setVisibility(View.VISIBLE);
                    viewModel.searchUsersRequestApi(pref.getUserId(), searchQuery);
                }
            }
        });
    }

    private void confSearchResponseObserver() {
        final Observer<SearchUsersResponse> observer = new Observer<SearchUsersResponse>() {
            @Override
            public void onChanged(SearchUsersResponse response) {
                binding.progressBarLayout.progressBar.setVisibility(View.GONE);
                binding.tvResult.setVisibility(View.VISIBLE);
                try{
                    response.getData().get(0);
                    if(binding.rvUsers.getRoot().getVisibility() != View.VISIBLE)
                        switchViewLayout();
                    adapter.setUsers(response.getData());
                }catch(Exception e){
                    String text;
                    adapter.setUsers(new ArrayList<>());
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
        viewModel.getSearchUsersResponse().observe(this, observer);
    }

    private void confToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        binding.toolbarLayout.etSearchBar.setHint("Search User...");
    }

    private void confRv() {
        adapter = new SearchUsersAdapter();
        binding.rvUsers.rvCardviewList.setAdapter(adapter);
        binding.rvUsers.rvCardviewList.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(new SearchUsersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(UserModel user) {
                Intent intent = new Intent(SearchUserActivity.this, UserDetailActivity.class);
                intent.putExtra(UserDetailActivity.EXTRA_USERID, user.getUserId());
                startActivity(intent);
            }
        });
    }

    private void confBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_user);
        binding.setLifecycleOwner(this);
        binding.setViewmodel(viewModel);
    }

    private void hideItemVisibility(){
        binding.toolbarLayout.etSearchBar.setText("");
        binding.tvResult.setVisibility(View.GONE);
        if(binding.fillPage.getRoot().getVisibility() == View.VISIBLE)
            binding.fillPage.tvFillPageText.setText("");
    }

    private void switchViewLayout() {
        binding.rvUsers.getRoot().setVisibility(binding.rvUsers.getRoot().getVisibility() != View.VISIBLE ? View.VISIBLE : View.GONE);
        binding.fillPage.getRoot().setVisibility(binding.fillPage.getRoot().getVisibility() != View.VISIBLE ? View.VISIBLE : View.GONE);
    }
}