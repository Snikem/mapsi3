package com.example.mapsi3;

import android.os.AsyncTask;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateIMEI extends AsyncTask<String, String, String> {
    String name;
    String IMEI;
    public static int rezultCheckusertopassServ;
    public UpdateIMEI(String name,String IMEI){
        this.name=name;
        this.IMEI=IMEI;
    }
    @Override
    protected String doInBackground(String... strings) {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://proverkasbd.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Iupdateimei service = retrofit.create(Iupdateimei.class);
            Call<Integer> call = service.updateIMEI(name,IMEI);
            Callback<Integer> callback = new Callback<Integer>() {

                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                   response.body();
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


}
