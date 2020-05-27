package com.example.mapsi3;

import android.os.AsyncTask;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Addnewusertopass  extends AsyncTask<String, String, String> {
    String name;
    String passworld;
    String IMEI;

    public static int rezultAddnewusertopass;
    public Addnewusertopass(String name,String passworld,String IMEI){
        this.name=name;
        this.passworld=passworld;
        this.IMEI=IMEI;
    }
    @Override
    protected String doInBackground(String... strings) {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://proverkasbd.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Iaddnewusertopass service = retrofit.create(Iaddnewusertopass.class);
            Call<Integer> call = service.addnewusertopass(name,passworld,IMEI);
            Response<Integer> res = call.execute();
            rezultAddnewusertopass=res.body();
            FirstStart.adduserstopass++;


        } catch (Exception e) {
            Log.d("HTTP!!!", "WTF");
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {


    }
}

