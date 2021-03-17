package com.example.mytasksapp.api;

import com.example.mytasksapp.data.TodoPost;
import com.example.mytasksapp.pojo.MyList;
import com.example.mytasksapp.pojo.Todo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @GET("list")
    Observable<List<MyList>> getLists();

    @Headers("Content-Type: application/json")
    @POST("list/{id}/todo")
    Call<Todo> postTodo(@Path("id") int id, @Body TodoPost todo);
}
