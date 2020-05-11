package com.example.mapsi3;

import android.os.AsyncTask;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerForChekPx extends AsyncTask<String, String, String> {
    double latutide = MapsActivity.longMapClickLat;
    double longitude = MapsActivity.longMapClickLng;
    String name = ProfileActivity.NICKNAME;

    @Override
    protected String doInBackground(String... strings) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.162:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ForCheckPx service = retrofit.create(ForCheckPx.class);
        Call<Integer> call = service.chekingPx(latutide, longitude, name);
        Callback<Integer> callback = new Callback<Integer>() {

            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                MapsActivity.counterpxforserver = MapsActivity.counterpxforserver + response.body();
                MapsActivity.countpxText.setText(Integer.toString(MapsActivity.counterpxforserver));

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        };
        call.enqueue(callback);
        return null;
    }
}
