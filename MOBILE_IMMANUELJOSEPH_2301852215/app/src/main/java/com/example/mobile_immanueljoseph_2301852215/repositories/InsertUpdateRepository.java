package com.example.mobile_immanueljoseph_2301852215.repositories;

import androidx.lifecycle.LiveData;

import com.example.mobile_immanueljoseph_2301852215.api.InsertUpdateApiClient;
import com.example.mobile_immanueljoseph_2301852215.api.response.InsertUpdateResponse;

public class InsertUpdateRepository {
    private static InsertUpdateRepository instance;

    private InsertUpdateApiClient insertUpdateApiClient;

    public static InsertUpdateRepository getInstance() {
        return instance == null ? instance = new InsertUpdateRepository() : instance;
    }

    private InsertUpdateRepository(){
        insertUpdateApiClient = InsertUpdateApiClient.getInstance();
    }

    public LiveData<InsertUpdateResponse> getInsertUpdateResponse(){
        return insertUpdateApiClient.getInsertUpdateResponse();
    }

    public void insertUpdateRequestApi(String user_id, String todo_id, String title, String desc){
        insertUpdateApiClient.insertUpdateTodo(user_id, todo_id, title, desc);
    }
}
