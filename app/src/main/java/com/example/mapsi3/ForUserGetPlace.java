package com.example.mapsi3;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ForUserGetPlace {
    @GET("/updateRang/{name}/{score}")
    public Call<ArrayList<User>> listTop (@Path("name") String name, @Path("score") int score);
}
