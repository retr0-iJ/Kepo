package com.example.mobile_immanueljoseph_2301852215.api;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mobile_immanueljoseph_2301852215.api.response.SearchTodosResponse;
import com.example.mobile_immanueljoseph_2301852215.util.AppExecutors;
import com.example.mobile_immanueljoseph_2301852215.util.SingleLiveEvent;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class SearchTodosApiClient {
    private static SearchTodosApiClient instance;
    private SingleLiveEvent<SearchTodosResponse> searchTodosResponse;

    private RetrieveSearchTodosRunnable retrieveSearchTodosRunnable;

    public SearchTodosApiClient() {
        searchTodosResponse = new SingleLiveEvent<>();
    }

    public static SearchTodosApiClient getInstance(){
        return instance == null ? instance = new SearchTodosApiClient() : instance;
    }

    public LiveData<SearchTodosResponse> getSearchTodosResponse(){
        return searchTodosResponse;
    }

    public void searchTodosRequestApi(String searchQuery, int filterUser, int filterTodo){
        if(retrieveSearchTodosRunnable != null)
            retrieveSearchTodosRunnable = null;

        retrieveSearchTodosRunnable = new RetrieveSearchTodosRunnable(searchQuery, filterUser, filterTodo);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveSearchTodosRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Cancelling Request
                myHandler.cancel(true);
                Log.v("Tag", "canceled");
            }
        }, 5000, TimeUnit.MILLISECONDS);
    }

    private class RetrieveSearchTodosRunnable implements  Runnable{
        private String searchQuery;
        private int filterUser;
        private int filterTodo;
        boolean cancelRequest;

        public RetrieveSearchTodosRunnable(String searchQuery, int filterUser, int filterTodo) {
            this.searchQuery = searchQuery;
            this.filterUser = filterUser;
            this.filterTodo = filterTodo;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response<SearchTodosResponse> response = searchTodosFromApi(searchQuery, filterUser, filterTodo).execute();

                if(response.code() == 200 || response.code() == 400 || response.code() == 500){
                    searchTodosResponse.postValue(response.body());
                }else{
                    Log.v("Tag", response.errorBody().string());
                    searchTodosResponse.postValue(null);
                }
            }catch (Exception e){
                Log.v("tag", e.getMessage());
                e.printStackTrace();
                searchTodosResponse.postValue(null);
            }
        }

        private Call<SearchTodosResponse> searchTodosFromApi(String searchQuery, int filterUser, int filterTodo){
            return RepoServices.getApiInterface().searchTodos(
                    searchQuery, filterUser, filterTodo
            );
        }
    }
}
