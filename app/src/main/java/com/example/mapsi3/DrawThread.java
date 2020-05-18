package com.example.mapsi3;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;

import android.util.Log;
import android.view.SurfaceHolder;


import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.graphics.Color.rgb;


public class DrawThread extends Thread {
    private SurfaceHolder surfaceHolder;


    Thread thread=new Thread(new GetServer());

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
    public void requestStart(){running = true;}

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
                       ArrayListForCoordinatesAndColors.add(new CoordinatesAndColors(MapsActivity.longMapClickLat,MapsActivity.longMapClickLng,redColorForPaint_INT,greenColorForPaint_INT,blueColorForPaint_INT));

                   }

                    double cooficentx=(xSecondTap-xFirstTap)/(LngFirstTapForDrawThread-LngSecondTapForDrawThread);
                    double cooficenty=(ySecondTap-yFirstTap)/(LatFirstTapForDrawThread-LatSecondTapForDrawThread);

                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                    float g1 =(float) Math.pow(2,MapsActivity.currenZoom)/100000;

                   double r2 = (0.00021008133 * ((ySecondTap-yFirstTap)/(LatSecondTapForDrawThread-LatFirstTapForDrawThread)))/(20.97152*2);
                   float r3 = (float)r2;
                   if(drawpx%2==1){

                   for(int i = 0;i<ArrayListForCoordinatesAndColors.size();i++){
                        double LatTap=ArrayListForCoordinatesAndColors.get(i).latitude;
                        double LngTap=ArrayListForCoordinatesAndColors.get(i).longitude;

                        p.setColor(rgb(ArrayListForCoordinatesAndColors.get(i).red,ArrayListForCoordinatesAndColors.get(i).green,
                                ArrayListForCoordinatesAndColors.get(i).blue));




                        float Totalx=(float) (( (MapsActivity.currentLocationLng-LngTap)*(cooficentx/Math.pow(2,21-MapsActivity.currenZoom)))+canvas.getWidth()/2);
                        float Totaly=(float) (( (MapsActivity.currentLocationLat -LatTap)*(cooficenty/Math.pow(2,21-MapsActivity.currenZoom)))+canvas.getHeight()/2);
                        canvas.drawRect(Totalx-g1*r3,Totaly-g1*r3,Totalx+g1*r3,Totaly+g1*r3,p);



                    }
                   }
                    p.setColor(rgb(redColorForPaint_INT,greenColorForPaint_INT,blueColorForPaint_INT));
                    canvas.drawRect(canvas.getWidth()-MapsActivity.pxForButton2-MapsActivity.pxForButton1,canvas.getHeight()-MapsActivity.pxForButton2-MapsActivity.pxForButton1,canvas.getWidth()-MapsActivity.pxForButton1,canvas.getHeight()-MapsActivity.pxForButton1,p);










                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                   try {
                        sleep(30);
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
                        .baseUrl("https://proverkasbd.herokuapp.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ForCoordinatesAndColorsGet service = retrofit.create(ForCoordinatesAndColorsGet.class);
                Call<ArrayList<CoordinatesAndColors>> call = service.getCoordAndcolors();
                Callback<ArrayList<CoordinatesAndColors>> callback = new Callback<ArrayList<CoordinatesAndColors>>() {
                    @Override
                    public void onResponse(Call<ArrayList<CoordinatesAndColors>> call, Response<ArrayList<CoordinatesAndColors>> response) {
                        DrawThread.ArrayListForCoordinatesAndColors= response.body();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<CoordinatesAndColors>> call, Throwable t) {

                    }


                };
                    call.enqueue(callback);







                Thread.sleep(10000);
                }

            } catch (Exception e) {

            }
        }
    }
}
