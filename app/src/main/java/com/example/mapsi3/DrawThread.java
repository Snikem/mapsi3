package com.example.mapsi3;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;

import android.util.Log;
import android.view.SurfaceHolder;


import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.graphics.Color.rgb;


public class DrawThread extends Thread {
    private SurfaceHolder surfaceHolder;

    Thread thread=new Thread(new GetServer());
    GetServer anotherThread=new GetServer();
    public static ArrayList<Coordinates> ArrayListForCoordinates = new ArrayList<Coordinates>();
    public static ArrayList<Colors> ArrayListForPaint = new ArrayList<Colors>();

    private int counterForArray2=0;


    private volatile boolean running = true;
    private Paint p = new Paint();
    private double xFistTap;
    private double yFirstTap;
    private double xSecondTap;
    private double ySecondTap;
    public DrawThread(Context context, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;


    }
    public void setSecondTap(int x , int y){
        xSecondTap=x;
        ySecondTap=y;


    }
    public  void setFitstTap(int x, int y) {
        xFistTap = x;
        yFirstTap = y;
        thread.start();


    }

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
            Canvas canvas = surfaceHolder.lockCanvas();


            if (canvas != null) {
                try {


                    int redColorForPaint_INT = MapsActivity.RedProgress;
                    int greenColorForPaint_INT = MapsActivity.GreenProgress;
                    int blueColorForPaint_INT = MapsActivity.BlueProgress;


                   if(MapsActivity.counterForArray>counterForArray2){
                        counterForArray2++;
                       ArrayListForCoordinates.add(new Coordinates(MapsActivity.longMapClickLat,MapsActivity.longMapClickLng));
                       ArrayListForPaint.add(new Colors(redColorForPaint_INT,greenColorForPaint_INT,blueColorForPaint_INT));
                   }

                    double cooficentx=(xSecondTap-xFistTap)/(LngFirstTapForDrawThread-LngSecondTapForDrawThread);
                    double cooficenty=(ySecondTap-yFirstTap)/(LatFirstTapForDrawThread-LatSecondTapForDrawThread);

                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                    float g1 =(float) Math.pow(2,MapsActivity.currenZoom)/100000;

                   double r2 = (0.00021008133 * ((ySecondTap-yFirstTap)/(LatSecondTapForDrawThread-LatFirstTapForDrawThread)))/(20.97152*2);
                   float r3 = (float)r2;

                   for(int i = 0;i<ArrayListForCoordinates.size();i++){
                        double LatTap=ArrayListForCoordinates.get(i).latitude;
                        double LngTap=ArrayListForCoordinates.get(i).longitude;

                        p.setColor(rgb(ArrayListForPaint.get(i).red,ArrayListForPaint.get(i).green,
                                ArrayListForPaint.get(i).blue));




                        float Totalx=(float) (( (MapsActivity.currentLocationLng-LngTap)*(cooficentx/Math.pow(2,21-MapsActivity.currenZoom)))+canvas.getWidth()/2);
                        float Totaly=(float) (( (MapsActivity.currentLocationLat -LatTap)*(cooficenty/Math.pow(2,21-MapsActivity.currenZoom)))+canvas.getHeight()/2);
                        canvas.drawRect(Totalx-g1*r3,Totaly-g1*r3,Totalx+g1*r3,Totaly+g1*r3,p);



                    }
                    p.setColor(rgb(redColorForPaint_INT,greenColorForPaint_INT,blueColorForPaint_INT));
                    canvas.drawRect(canvas.getWidth()-MapsActivity.pxForButton2-MapsActivity.pxForButton1,canvas.getHeight()-MapsActivity.pxForButton2-MapsActivity.pxForButton1,canvas.getWidth()-MapsActivity.pxForButton1,canvas.getHeight()-MapsActivity.pxForButton1,p);










                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                   try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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
                        .baseUrl("http://192.168.1.161:8080/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ForCoordinatesGet service2 = retrofit.create(ForCoordinatesGet.class);
                Call<ArrayList<Coordinates>> call2 = service2.greeting4();
                Callback<ArrayList<Coordinates>> callback = new Callback<ArrayList<Coordinates>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Coordinates>> call, Response<ArrayList<Coordinates>> response) {
                        DrawThread.ArrayListForCoordinates = response.body();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Coordinates>> call, Throwable t) {

                    }
                };
                    call2.enqueue(callback);


                    ForColorsGet service3 = retrofit.create(ForColorsGet.class);
                    Call<ArrayList<Colors>> call3 = service3.greeting3();
                    Callback<ArrayList<Colors>> callback2 = new Callback<ArrayList<Colors>>() {

                        @Override
                        public void onResponse(Call<ArrayList<Colors>> call, Response<ArrayList<Colors>> response) {
                            DrawThread.ArrayListForPaint = response.body();
                        }

                        @Override
                        public void onFailure(Call<ArrayList<Colors>> call, Throwable t) {

                        }
                    };
                    call3.enqueue(callback2);


                Thread.sleep(1000);
                }

            } catch (Exception e) {

            }
        }
    }
}
