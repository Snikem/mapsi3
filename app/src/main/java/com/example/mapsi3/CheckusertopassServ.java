package com.example.mapsi3;

import android.os.AsyncTask;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckusertopassServ extends AsyncTask<String, String, String> {
    String name;
    String passworld;

    public static int rezultCheckusertopassServ;
    public CheckusertopassServ(String name,String passworld){
        this.name=name;
        this.passworld=passworld;
    }
    @Override
    protected String doInBackground(String... strings) {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://proverkasbd.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ICheckusertopass service = retrofit.create(ICheckusertopass.class);
            Call<Integer> call = service.checkusertopass(name,passworld);
            Response<Integer> res = call.execute();
            rezultCheckusertopassServ=res.body();
            FirstStart.checkusertopass++;


        } catch (Exception e) {
            Log.d("HTTP!!!", "WTF");
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {


    }
}
