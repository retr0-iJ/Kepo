package com.example.mobile_immanueljoseph_2301852215.api;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mobile_immanueljoseph_2301852215.api.response.SearchUsersResponse;
import com.example.mobile_immanueljoseph_2301852215.util.AppExecutors;
import com.example.mobile_immanueljoseph_2301852215.util.SingleLiveEvent;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class SearchUsersApiClient {
    private static SearchUsersApiClient instance;
    private SingleLiveEvent<SearchUsersResponse> searchUsersResponse;

    private RetrieveSearchUsersRunnable retrieveSearchUsersRunnable;

    private SearchUsersApiClient(){
        searchUsersResponse = new SingleLiveEvent<>();
    }

    public static SearchUsersApiClient getInstance(){
        return instance == null ? instance = new SearchUsersApiClient() : instance;
    }

    public LiveData<SearchUsersResponse> getSearchUsersResponse(){
        return searchUsersResponse;
    }

    public void searchUsersRequestApi(String user_id, String searchQuery){
        if(retrieveSearchUsersRunnable != null)
            retrieveSearchUsersRunnable = null;

        retrieveSearchUsersRunnable = new RetrieveSearchUsersRunnable(user_id, searchQuery);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveSearchUsersRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Cancelling Request
                myHandler.cancel(true);
                Log.v("Tag", "canceled");
            }
        }, 5000, TimeUnit.MILLISECONDS);
    }

    private class RetrieveSearchUsersRunnable implements Runnable{
        private String user_id;
        private String searchQuery;
        boolean cancelRequest;

        public RetrieveSearchUsersRunnable(String user_id, String searchQuery) {
            this.user_id = user_id;
            this.searchQuery = searchQuery;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response<SearchUsersResponse> response = searchUsersFromApi(user_id, searchQuery).execute();

                if(response.code() == 200 || response.code() == 400 || response.code() == 500){
                    searchUsersResponse.postValue(response.body());
                }else{
                    Log.v("Tag", response.errorBody().string());
                    searchUsersResponse.postValue(null);
                }
            }catch (Exception e){
                Log.v("tag", e.getMessage());
                e.printStackTrace();
                searchUsersResponse.postValue(null);
            }
        }

        private Call<SearchUsersResponse> searchUsersFromApi(String user_id, String searchQuery){
            return RepoServices.getApiInterface().searchUsers(
                    user_id,
                    searchQuery
            );
        }
    }
}
