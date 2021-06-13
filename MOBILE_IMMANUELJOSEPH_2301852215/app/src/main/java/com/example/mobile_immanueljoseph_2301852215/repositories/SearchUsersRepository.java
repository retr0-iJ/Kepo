package com.example.mobile_immanueljoseph_2301852215.repositories;

import androidx.lifecycle.LiveData;

import com.example.mobile_immanueljoseph_2301852215.api.SearchUsersApiClient;
import com.example.mobile_immanueljoseph_2301852215.api.response.SearchUsersResponse;

public class SearchUsersRepository {
    private static SearchUsersRepository instance;
    private SearchUsersApiClient searchUsersApiClient;

    private SearchUsersRepository(){
        searchUsersApiClient = SearchUsersApiClient.getInstance();
    }

    public static SearchUsersRepository getInstance(){
        return instance == null ? instance = new SearchUsersRepository() : instance;
    }

    public void searchUsersRequestApi(String user_id, String searchQuery){
        searchUsersApiClient.searchUsersRequestApi(user_id, searchQuery);
    }

    public LiveData<SearchUsersResponse> getSearchUsersResponse(){
        return searchUsersApiClient.getSearchUsersResponse();
    }
}
