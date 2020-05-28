package com.example.mapsi3.REST;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Iaddnewusertopass {
    @GET("/addnewusertopass/{name}/{passworld}/{IMEI}")
    public Call<Integer> addnewusertopass(@Path("name") String name,@Path("passworld")String passworld,@Path("IMEI") String IMEI);
}
