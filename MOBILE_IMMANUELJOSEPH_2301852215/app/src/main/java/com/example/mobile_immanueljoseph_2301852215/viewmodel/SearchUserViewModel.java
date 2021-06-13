package com.example.mobile_immanueljoseph_2301852215.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobile_immanueljoseph_2301852215.api.response.SearchUsersResponse;
import com.example.mobile_immanueljoseph_2301852215.repositories.SearchUsersRepository;

public class SearchUserViewModel extends ViewModel {
    private MutableLiveData<String> searchQuery = new MutableLiveData<>();

    private SearchUsersRepository searchUsersRepository;

    public SearchUserViewModel(){
        searchUsersRepository = SearchUsersRepository.getInstance();
    }

    public LiveData<String> getSearchQuery() {
        return searchQuery;
    }

    public void searchUsersRequestApi(String user_id, String searchQuery){
        this.searchQuery.setValue(searchQuery);
        searchUsersRepository.searchUsersRequestApi(user_id, searchQuery);
    }

    public LiveData<SearchUsersResponse> getSearchUsersResponse(){
        return searchUsersRepository.getSearchUsersResponse();
    }
}
