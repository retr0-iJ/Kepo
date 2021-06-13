package com.example.mobile_immanueljoseph_2301852215.repositories;

import androidx.lifecycle.LiveData;

import com.example.mobile_immanueljoseph_2301852215.api.SearchTodosApiClient;
import com.example.mobile_immanueljoseph_2301852215.api.response.SearchTodosResponse;

public class SearchTodosRepository {
    private static SearchTodosRepository instance;
    private SearchTodosApiClient searchTodosApiClient;

    public SearchTodosRepository() {
        searchTodosApiClient = SearchTodosApiClient.getInstance();
    }

    public static SearchTodosRepository getInstance(){
        return instance == null ? instance = new SearchTodosRepository() : instance;
    }

    public void searchTodosRequestApi(String searchQuery, int filterUser, int filterTodo){
        searchTodosApiClient.searchTodosRequestApi(searchQuery, filterUser, filterTodo);
    }

    public LiveData<SearchTodosResponse> getSearchTodosResponse(){
        return searchTodosApiClient.getSearchTodosResponse();
    }
}
