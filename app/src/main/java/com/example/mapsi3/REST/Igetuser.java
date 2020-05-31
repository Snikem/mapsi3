package com.example.mapsi3.REST;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Igetuser {
    @GET("/getuser/{name}")
    public Call<Integer> GETuser(@Path("name") String name);

}

