package com.example.mobile_immanueljoseph_2301852215.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobile_immanueljoseph_2301852215.model.InsertUpdateModel;
import com.example.mobile_immanueljoseph_2301852215.model.TodoDetailModel;
import com.example.mobile_immanueljoseph_2301852215.repositories.InsertUpdateRepository;
import com.example.mobile_immanueljoseph_2301852215.api.response.InsertUpdateResponse;
import com.example.mobile_immanueljoseph_2301852215.util.SingleLiveEvent;

public class InsertUpdateTodoViewModel extends ViewModel {
    public MutableLiveData<String> title = new MutableLiveData<>();
    public MutableLiveData<String> description = new MutableLiveData<>();
    public MutableLiveData<Integer> descLength = new MutableLiveData<>();
    public MutableLiveData<String> warning = new MutableLiveData<>();

    private MutableLiveData<InsertUpdateModel> insertUpdateData;

    private InsertUpdateRepository insertUpdateRepository;

    public LiveData<InsertUpdateModel> getInsertUpdateData(){
        return insertUpdateData == null ? insertUpdateData = new MutableLiveData<>() : insertUpdateData;
    }

    public InsertUpdateTodoViewModel(){
        insertUpdateRepository = InsertUpdateRepository.getInstance();
    }

    public void setUpdateValue(TodoDetailModel data){
        title.setValue(data.getTitle());
        description.setValue(data.getDescription());
    }

    public void insertUpdateRequestApi(String user_id, String todo_id, String title, String desc){
        insertUpdateRepository.insertUpdateRequestApi(user_id, todo_id, title, desc);
    }

    public LiveData<InsertUpdateResponse> getInsertUpdateResponse(){
        return insertUpdateRepository.getInsertUpdateResponse();
    }

    public void onClickFab(String todo_id){
        InsertUpdateModel insertUpdateInput = new InsertUpdateModel(todo_id, title.getValue(), description.getValue());

        insertUpdateData.setValue(insertUpdateInput);
    }
}
