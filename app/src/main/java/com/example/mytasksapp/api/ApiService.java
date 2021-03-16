package com.example.mytasksapp.api;

import com.example.mytasksapp.pojo.MyList;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    @GET("list")
    Observable<List<MyList>> getLists();
}
