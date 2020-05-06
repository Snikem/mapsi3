package com.example.mapsi3;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Server extends AsyncTask<String, String, String> {

    public static ArrayList<Coordinates> rezCoordinates;
    ArrayList nenuzniy;
    double lat = MapsActivity.longMapClickLat;
    double lng = MapsActivity.longMapClickLng;
    int red = MapsActivity.RedProgress;
    int green = MapsActivity.GreenProgress;
    int blue = MapsActivity.BlueProgress;

    @Override
    protected String doInBackground(String... strings) {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.165:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ForColorSend service2 = retrofit.create(ForColorSend.class);
            Call<ArrayList<Colors>> call2 = service2.greeting2(red, green, blue);
            Response<ArrayList<Colors>> ListResponse2 = call2.execute();
            nenuzniy = ListResponse2.body();

            ForCoordinates service = retrofit.create(ForCoordinates.class);
            Call<ArrayList<Coordinates>> call = service.greeting(lat, lng);
            Response<ArrayList<Coordinates>> ListResponse = call.execute();
            rezCoordinates = ListResponse.body();








        } catch (Exception e) {
            Log.d("HTTP!!!", "WTF");
        }
        return null;
    }
}
