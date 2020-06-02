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
    double lat = MapsFragment.longMapClickLat;
    double lng = MapsFragment.longMapClickLng;
    int red = MapsFragment.RedProgress;
    int green = MapsFragment.GreenProgress;
    int blue = MapsFragment.BlueProgress;
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
            //Log.d("Qrty" , "zapros est");
            Response<Integer> res = call.execute();
            //Log.d("Qrty" , res+"otvet est1");
            MapsFragment.counterpxforserver =  res.body();
           // Log.d("Qrty" , "otvet est2");
            MapsFragment.g=true;
           // Log.d("Qrty" , MapsActivity.g+"serv");









        } catch (Exception e) {
            Log.d("HTTP!!!", "WTF");
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        MapsFragment.countpxText.setText(Integer.toString(MapsFragment.counterpxforserver));
    }
}

