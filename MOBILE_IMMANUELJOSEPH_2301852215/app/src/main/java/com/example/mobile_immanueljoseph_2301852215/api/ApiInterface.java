package com.example.mobile_immanueljoseph_2301852215.api;

import com.example.mobile_immanueljoseph_2301852215.api.request.DeleteTodoBodyRequest;
import com.example.mobile_immanueljoseph_2301852215.api.response.DeleteTodosResponse;
import com.example.mobile_immanueljoseph_2301852215.api.response.GetTodosResponse;
import com.example.mobile_immanueljoseph_2301852215.api.response.GetTodoDetailResponse;
import com.example.mobile_immanueljoseph_2301852215.api.response.InsertUpdateResponse;
import com.example.mobile_immanueljoseph_2301852215.api.response.LoginResponse;
import com.example.mobile_immanueljoseph_2301852215.api.response.SearchTodosResponse;
import com.example.mobile_immanueljoseph_2301852215.api.response.SearchUsersResponse;
import com.example.mobile_immanueljoseph_2301852215.util.Consts;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {
    @FormUrlEncoded
    @POST(Consts.LOGIN_URL)
    Call<LoginResponse> getUserCredentials(
        @Field("username") String username, @Field("password") String password
    );

    @GET(Consts.GET_CREATE_DELETE_TODO_URL)
    Call<GetTodosResponse> getMyTodos(
            @Path("user_id") String user_id
    );

    @GET(Consts.GETDETAIL_UPDATE_TODO_URL)
    Call<GetTodoDetailResponse> getTodoDetail(
            @Path("user_id") String user_id, @Path("todo_id") String todo_id
    );

    @FormUrlEncoded
    @POST(Consts.GET_CREATE_DELETE_TODO_URL)
    Call<InsertUpdateResponse> insertTodo(
            @Path("user_id") String user_id, @Field("title") String title, @Field("description") String desc
    );

    @FormUrlEncoded
    @PUT(Consts.GETDETAIL_UPDATE_TODO_URL)
    Call<InsertUpdateResponse> updateTodo(
            @Path("user_id") String user_id, @Path("todo_id") String todo_id, @Field("title") String title, @Field("description") String desc
    );

    @POST(Consts.DELTE_TODO_URL)
    Call<DeleteTodosResponse> deleteTodos(
            @Path("user_id") String user_id, @Body DeleteTodoBodyRequest body
    );

    @FormUrlEncoded
    @POST(Consts.SEARCH_TODOS_URL)
    Call<SearchTodosResponse> searchTodos(
            @Field("searchQuery") String searchQuery, @Field("filterUser") int filterUser, @Field("filterTodo") int filterTodo
    );

    @FormUrlEncoded
    @POST(Consts.SEARCH_USERS_URL)
    Call<SearchUsersResponse> searchUsers(
            @Field("user_id") String user_id, @Field("searchQuery") String searchQuery
    );
}
