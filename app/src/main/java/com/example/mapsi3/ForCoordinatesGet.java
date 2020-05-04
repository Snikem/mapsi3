package com.example.mapsi3;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ForCoordinatesGet {
    @GET("/coordinatesGet")
    public Call<ArrayList<Coordinates>> greeting4 ();
}
