package com.example.mobile_immanueljoseph_2301852215.api.response;

import com.example.mobile_immanueljoseph_2301852215.model.SearchTodoModel;
import com.example.mobile_immanueljoseph_2301852215.model.TodoModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetTodosResponse {

    @SerializedName("status")
    @Expose
    private int code;

    public int getCode(){ return code; }

    @SerializedName("message")
    @Expose
    private String msg;

    public String getMsg(){ return msg; }

    @SerializedName("data")
    @Expose
    private TodosResponseModel data;

    public TodosResponseModel getData(){
        return data;
    }

    public class TodosResponseModel {
        @SerializedName("userId")
        @Expose
        private String userId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("listTodo")
        @Expose
        private ArrayList<TodoModel> todos = null;

        public String getUserId() {
            return userId;
        }

        public String getName() {
            return name;
        }

        public String getUsername() {
            return username;
        }

        public ArrayList<TodoModel> getListTodo() {
            return todos;
        }

        public ArrayList<SearchTodoModel> getSearchTodoModelArrayList(){
            ArrayList<SearchTodoModel> searchTodosModel = new ArrayList<>();
            for (TodoModel t : this.todos) {
                searchTodosModel.add(new SearchTodoModel(this.userId, this.username, t.getTodo_id(), t.getTitle(), t.getLast_edited()));
            }
            return searchTodosModel;
        }
    }
}
