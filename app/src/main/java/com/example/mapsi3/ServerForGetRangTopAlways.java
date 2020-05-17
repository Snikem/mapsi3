package com.example.mapsi3;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerForGetRangTopAlways extends Thread {
    public static ArrayList<User> Userspoint =new ArrayList<User>();
    @Override
    public void run() {
        while(true){
            try {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://proverkasbd.herokuapp.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ForRangtopGetAlways service = retrofit.create(ForRangtopGetAlways.class);
                Call<ArrayList<User>> call = service.getTop();
                Callback<ArrayList<User>> callback = new Callback<ArrayList<User>>() {

                    @Override
                    public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                        Userspoint = response.body();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<User>> call, Throwable t) {

                    }
                };
                call.enqueue(callback);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }catch(Exception e){

            }

        }
    }
}
