package com.example.mapsi3.REST;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IcheckIMEI {
    @GET("/checkIMEI/{name}/{IMEI}")
    public Call<Integer> checkIMEI(@Path("name") String name,@Path("IMEI") String IMEI);
}

