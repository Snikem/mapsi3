package com.example.mapsi3;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ForCheckPx {
    @GET("/checkPx/{latitude}/{longitude}/{name}")
    public Call<Integer> chekingPx(@Path("latitude")double latitude , @Path("longitude") double longitude , @Path("name") String name);
}
