package com.example.mapsi3;

import android.os.AsyncTask;
import android.util.Log;

import com.example.mapsi3.REST.ForImportCoordinateAndColors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class Server extends AsyncTask<String, String, String> {
    public Server(String name){
        this.name=name;
    }

    FirstStart firstStart;
    double lat = MapsActivity.longMapClickLat;
    double lng = MapsActivity.longMapClickLng;
    int red = MapsActivity.RedProgress;
    int green = MapsActivity.GreenProgress;
    int blue = MapsActivity.BlueProgress;
    String name;








    @Override
    protected String doInBackground(String... strings) {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://proverkasbd.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ForImportCoordinateAndColors service = retrofit.create(ForImportCoordinateAndColors.class);
            Call<Integer> call = service.addPx(name,lat, lng,red,green,blue);
            Callback<Integer> callback = new Callback<Integer>() {

                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    MapsActivity.counterpxforserver =  response.body();
                    MapsActivity.countpxText.setText(Integer.toString(MapsActivity.counterpxforserver));

                    MapsActivity.g=true;
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {

                }
            };
            call.enqueue(callback);








        } catch (Exception e) {
            Log.d("HTTP!!!", "WTF");
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}

