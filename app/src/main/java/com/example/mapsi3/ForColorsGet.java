package com.example.mapsi3;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ForColorsGet {
    @GET("/colorsGet")
    public Call<ArrayList<Colors>> greeting3 ();
}
