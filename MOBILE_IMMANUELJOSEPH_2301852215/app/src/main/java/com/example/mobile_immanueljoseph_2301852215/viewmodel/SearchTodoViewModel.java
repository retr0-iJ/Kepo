package com.example.mobile_immanueljoseph_2301852215.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobile_immanueljoseph_2301852215.api.response.SearchTodosResponse;
import com.example.mobile_immanueljoseph_2301852215.repositories.SearchTodosRepository;

public class SearchTodoViewModel extends ViewModel {
    private MutableLiveData<String> searchQuery = new MutableLiveData<>();

    private SearchTodosRepository searchTodosRepository;

    public SearchTodoViewModel(){
        searchTodosRepository = SearchTodosRepository.getInstance();
    }

    public LiveData<String> getSearchQuery() {
        return searchQuery;
    }

    public void searchTodosRequestApi(String searchQuery, int filterUser, int filterTodo){
        this.searchQuery.setValue(searchQuery);
        searchTodosRepository.searchTodosRequestApi(searchQuery, filterUser, filterTodo);
    }

    public LiveData<SearchTodosResponse> getSearchTodosResponse(){
        return searchTodosRepository.getSearchTodosResponse();
    }
}
