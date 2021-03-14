package com.example.mytasksapp.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Server {
        @POST("user/addemail")
        Call<List<AddTaskResult>> addTask(@Body TaskRequest taskRequest);
}
