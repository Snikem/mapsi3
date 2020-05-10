package com.example.mapsi3;

import android.os.AsyncTask;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerGetForRang extends AsyncTask<String, String, String> {
     ArrayList<User> Userspoint ;








    String name=ProfileActivity.NICKNAME;
    int countpx=MapsActivity.counterpxforserver;


    @Override
    protected String doInBackground(String... strings) {
        while(true) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.161:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ForUserGetPlace service = retrofit.create(ForUserGetPlace.class);
            Call<ArrayList<User>> call = service.listTop(name, countpx);
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

            return null;
        }

    }
}
