package com.example.mapsi3.REST;

import com.example.mapsi3.CoordinatesAndColors;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ForCoordinatesAndColorsGet {
    @GET("/getCoordinatesAndColors")
    public Call<ArrayList<CoordinatesAndColors>> getCoordAndcolors ();
}
