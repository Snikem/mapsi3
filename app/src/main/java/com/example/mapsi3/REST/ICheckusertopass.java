package com.example.mapsi3.REST;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ICheckusertopass {
    @GET("/Checkusertopass/{name}/{passworld}")
    public Call<Integer> checkusertopass(@Path("name") String name, @Path("passworld")String passworld);

}
