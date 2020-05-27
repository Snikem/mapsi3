package com.example.mapsi3;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Iupdateimei{
    @GET("/updateimei/{name}/{IMEI}")
    public Call<Integer> updateIMEI(@Path("name") String name, @Path("IMEI") String IMEI);
}
