package com.example.mapsi3.REST;

import com.example.mapsi3.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ForRangtopGetAlways {
    @GET("/getTopRang")
    public Call<ArrayList<User>> getTop();
}
