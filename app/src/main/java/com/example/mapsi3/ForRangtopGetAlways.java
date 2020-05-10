package com.example.mapsi3;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ForRangtopGetAlways {
    @GET("/RangtopGetAlways")
    public Call<ArrayList<User>> listTop2();
}
