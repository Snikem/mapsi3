package com.example.mapsi3;

import android.app.Activity;
import android.content.Context;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;

import android.view.SurfaceHolder;


import com.example.mapsi3.REST.ForCoordinatesAndColorsGet;
import com.example.mapsi3.REST.IcheckIMEI;

import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.graphics.Color.rgb;


public class DrawThread extends Thread {
    private SurfaceHolder surfaceHolder;
    Context context;

    Thread thread=new Thread(new GetServer());
    SharedPreferences prefs;

    public static ArrayList<CoordinatesAndColors> ArrayListForCoordinatesAndColors= new ArrayList<CoordinatesAndColors>() ;




    private int counterForArray2=0;



    private volatile boolean running = true;
    private Paint p = new Paint();
    public static int xFirstTap;
    public static int yFirstTap;
    public static int xSecondTap;
    public static int ySecondTap;

    public DrawThread(Context context, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        this.context=context;


    }
   /* public void setSecondTap(int x , int y){
        xSecondTap=x;
        ySecondTap=y;


    }*/
    public  void StartServ() {

        thread.start();


    }
    public static  int drawpx=1;

    public static double LngFirstTapForDrawThread;
    public static double LatFirstTapForDrawThread;
    public static double LatSecondTapForDrawThread;
    public static double LngSecondTapForDrawThread;

    public void requestStop() {
        running = false;
    }


    @Override
    public void run() {

        while (running) {
            synchronized (DrawThread.ArrayListForCoordinatesAndColors) {
                Canvas canvas = surfaceHolder.lockCanvas();


                if (canvas != null) {
                    try {


                        int redColorForPaint_INT = MapsActivity.RedProgress;
                        int greenColorForPaint_INT = MapsActivity.GreenProgress;
                        int blueColorForPaint_INT = MapsActivity.BlueProgress;


                        if (MapsActivity.counterForArray > counterForArray2) {
                            counterForArray2++;
                            ArrayListForCoordinatesAndColors.add(new CoordinatesAndColors(MapsActivity.longMapClickLat, MapsActivity.longMapClickLng, redColorForPaint_INT, greenColorForPaint_INT, blueColorForPaint_INT));

                        }

                        double cooficentx = (xSecondTap - xFirstTap) / (LngFirstTapForDrawThread - LngSecondTapForDrawThread);
                        double cooficenty = (ySecondTap - yFirstTap) / (LatFirstTapForDrawThread - LatSecondTapForDrawThread);

                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                        float g1 = (float) Math.pow(2, MapsActivity.currenZoom) / 100000;

                        double r2 = (0.00021008133 * ((ySecondTap - yFirstTap) / (LatSecondTapForDrawThread - LatFirstTapForDrawThread))) / (20.97152 * 2);
                        float r3 = (float) r2;
                        if (drawpx % 2 == 1) {

                            for (int i = 0; i < ArrayListForCoordinatesAndColors.size(); i++) {
                                double LatTap = ArrayListForCoordinatesAndColors.get(i).latitude;
                                double LngTap = ArrayListForCoordinatesAndColors.get(i).longitude;

                                p.setColor(rgb(ArrayListForCoordinatesAndColors.get(i).red, ArrayListForCoordinatesAndColors.get(i).green,
                                        ArrayListForCoordinatesAndColors.get(i).blue));


                                float Totalx = (float) (((MapsActivity.currentLocationLng - LngTap) * (cooficentx / Math.pow(2, 21 - MapsActivity.currenZoom))) + canvas.getWidth() / 2);
                                float Totaly = (float) (((MapsActivity.currentLocationLat - LatTap) * (cooficenty / Math.pow(2, 21 - MapsActivity.currenZoom))) + canvas.getHeight() / 2);
                                canvas.drawRect(Totalx - g1 * r3, Totaly - g1 * r3, Totalx + g1 * r3, Totaly + g1 * r3, p);


                            }
                        }
                        p.setColor(rgb(redColorForPaint_INT, greenColorForPaint_INT, blueColorForPaint_INT));
                        canvas.drawRect(canvas.getWidth() - MapsActivity.pxForButton2 - MapsActivity.pxForButton1, canvas.getHeight() - MapsActivity.pxForButton2 - MapsActivity.pxForButton1, canvas.getWidth() - MapsActivity.pxForButton1, canvas.getHeight() - MapsActivity.pxForButton1, p);


                    } finally {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        try {
                            sleep(70);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    class GetServer extends Thread {
        @Override
        public void run() {
            try {
                while(true){
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://proverkasbd.herokuapp.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ForCoordinatesAndColorsGet service = retrofit.create(ForCoordinatesAndColorsGet.class);
                Call<ArrayList<CoordinatesAndColors>> call = service.getCoordAndcolors();
                Callback<ArrayList<CoordinatesAndColors>> callback = new Callback<ArrayList<CoordinatesAndColors>>() {
                    @Override
                    public void onResponse(Call<ArrayList<CoordinatesAndColors>> call, Response<ArrayList<CoordinatesAndColors>> response) {
                        synchronized (DrawThread.ArrayListForCoordinatesAndColors) {
                            DrawThread.ArrayListForCoordinatesAndColors.clear();
                            DrawThread.ArrayListForCoordinatesAndColors.addAll(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<CoordinatesAndColors>> call, Throwable t) {

                    }


                };
                    call.enqueue(callback);
                    IcheckIMEI service2 = retrofit.create(IcheckIMEI.class);
                    Call<Integer> call2 = service2.checkIMEI(prefs.getString("name",""),prefs.getString("IMEI", "NoIMEI"));
                    Callback<Integer> callback2 = new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            ((Activity)context).finishAffinity();



                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {

                        }
                    };
                    call2.enqueue(callback2);








                Thread.sleep(10000);
                }

            } catch (Exception e) {

            }
        }
    }
}
