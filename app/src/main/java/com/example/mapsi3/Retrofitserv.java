package com.example.mapsi3;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Retrofitserv {
    @GET("/coordinates/{latitude}/{longitude}")
    public Call<List<Coordinates>> greeting(@Path("latitude")  double latitude, @Path("longitude") double longitude);
}
