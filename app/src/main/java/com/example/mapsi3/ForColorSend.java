package com.example.mapsi3;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ForColorSend {
    @GET("/colorsSend/{red}/{green}/{blue}")
    public Call<ArrayList<Colors>> greeting2 (@Path("red") int red, @Path("green") int green, @Path("blue") int blue);
}
