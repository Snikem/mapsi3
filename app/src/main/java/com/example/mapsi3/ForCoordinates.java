package com.example.mapsi3;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ForCoordinates {
    @GET("/coordinates/{latitude}/{longitude}")
    public Call<ArrayList<Coordinates>> greeting(@Path("latitude")  double latitude, @Path("longitude") double longitude);
}
