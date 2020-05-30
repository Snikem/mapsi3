package com.example.mapsi3;

import android.app.Activity;
import android.content.Context;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;

import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.ImageView;
import android.widget.Toast;


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
    public static ArrayList<CoordinatesAndColors> ArrayListForCoordinatesAndColors2= new ArrayList<CoordinatesAndColors>() ;




    private int counterForArray2=0;
    private int counterforserv=0;



    private volatile boolean running = true;
    private Paint p = new Paint();

    public static int xFirstTap;
    public static int yFirstTap;
    public static int xSecondTap;
    public static int ySecondTap;
    public MapsActivity mapsActivity;

    public DrawThread(Context context, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        this.context=context;



    }

    public  void StartServ() {

        thread.start();


    }
    public static  int drawpx=1;
    public static  int drawloc=1;


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


                        if (MapsActivity.counterForArray > counterForArray2&&counterforserv%2==0) {
                            counterForArray2++;
                            ArrayListForCoordinatesAndColors.add(new CoordinatesAndColors(MapsActivity.longMapClickLat, MapsActivity.longMapClickLng, redColorForPaint_INT, greenColorForPaint_INT, blueColorForPaint_INT));

                        }
                        if (MapsActivity.counterForArray > counterForArray2&&counterforserv%2==1) {

                            counterForArray2++;
                            ArrayListForCoordinatesAndColors2.add(new CoordinatesAndColors(MapsActivity.longMapClickLat, MapsActivity.longMapClickLng, redColorForPaint_INT, greenColorForPaint_INT, blueColorForPaint_INT));}


                        double cooficentx = (xSecondTap - xFirstTap) / (LngFirstTapForDrawThread - LngSecondTapForDrawThread);
                        double cooficenty = (ySecondTap - yFirstTap) / (LatFirstTapForDrawThread - LatSecondTapForDrawThread);

                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                        float g1 = (float) Math.pow(2, MapsActivity.currenZoom) / 100000;

                        double r2 = (0.00021008133 * ((ySecondTap - yFirstTap) / (LatSecondTapForDrawThread - LatFirstTapForDrawThread))) / (20.97152 * 2);
                        float r3 = (float) r2;
                        if (drawpx % 2 == 1) {

                            if(counterforserv%2==0){

                            for (int i = 0; i < ArrayListForCoordinatesAndColors.size(); i++) {
                                double LatTap = ArrayListForCoordinatesAndColors.get(i).latitude;
                                double LngTap = ArrayListForCoordinatesAndColors.get(i).longitude;

                                p.setColor(rgb(ArrayListForCoordinatesAndColors.get(i).red, ArrayListForCoordinatesAndColors.get(i).green,
                                        ArrayListForCoordinatesAndColors.get(i).blue));


                                float Totalx = (float) (((MapsActivity.currentLocationCamLng - LngTap) * (cooficentx / Math.pow(2, 21 - MapsActivity.currenZoom))) + canvas.getWidth() / 2);
                                float Totaly = (float) (((MapsActivity.currentLocationCamLat - LatTap) * (cooficenty / Math.pow(2, 21 - MapsActivity.currenZoom))) + canvas.getHeight() / 2);
                                canvas.drawRect(Totalx - g1 * r3, Totaly - g1 * r3, Totalx + g1 * r3, Totaly + g1 * r3, p);


                            }
                            }else {
                                for (int i = 0; i < ArrayListForCoordinatesAndColors2.size(); i++) {
                                    double LatTap = ArrayListForCoordinatesAndColors2.get(i).latitude;
                                    double LngTap = ArrayListForCoordinatesAndColors2.get(i).longitude;

                                    p.setColor(rgb(ArrayListForCoordinatesAndColors2.get(i).red, ArrayListForCoordinatesAndColors2.get(i).green,
                                            ArrayListForCoordinatesAndColors2.get(i).blue));


                                    float Totalx = (float) (((MapsActivity.currentLocationCamLng - LngTap) * (cooficentx / Math.pow(2, 21 - MapsActivity.currenZoom))) + canvas.getWidth() / 2);
                                    float Totaly = (float) (((MapsActivity.currentLocationCamLat - LatTap) * (cooficenty / Math.pow(2, 21 - MapsActivity.currenZoom))) + canvas.getHeight() / 2);
                                    canvas.drawRect(Totalx - g1 * r3, Totaly - g1 * r3, Totalx + g1 * r3, Totaly + g1 * r3, p);

                                }
                            }
                        }


                        p.setColor(rgb(redColorForPaint_INT, greenColorForPaint_INT, blueColorForPaint_INT));
                        canvas.drawRect(canvas.getWidth() - MapsActivity.pxForButton2 - MapsActivity.pxForButton1, canvas.getHeight() - MapsActivity.pxForButton2 - MapsActivity.pxForButton1, canvas.getWidth() - MapsActivity.pxForButton1, canvas.getHeight() - MapsActivity.pxForButton1, p);
                        if (drawloc % 2 == 1) {
                            p.setColor(rgb(255, 255, 255));
                            canvas.drawCircle((float) ((MapsActivity.currentLocationCamLng - MapsActivity.currentLocationLng) * (cooficentx / Math.pow(2, 21 - MapsActivity.currenZoom)) + canvas.getWidth() / 2), (float) ((MapsActivity.currentLocationCamLat - MapsActivity.currentLocationLat) * (cooficenty / Math.pow(2, 21 - MapsActivity.currenZoom)) + canvas.getHeight() / 2), 30, p);
                            p.setColor(rgb(23, 23, 227));
                            canvas.drawCircle((float) ((MapsActivity.currentLocationCamLng - MapsActivity.currentLocationLng) * (cooficentx / Math.pow(2, 21 - MapsActivity.currenZoom)) + canvas.getWidth() / 2), (float) ((MapsActivity.currentLocationCamLat - MapsActivity.currentLocationLat) * (cooficenty / Math.pow(2, 21 - MapsActivity.currenZoom)) + canvas.getHeight() / 2), 24, p);
                        }
                        Log.d("qweqwe","qwe");

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
                        if(counterforserv%2==0) {

                            DrawThread.ArrayListForCoordinatesAndColors2.clear();
                            DrawThread.ArrayListForCoordinatesAndColors2=response.body();
                            counterforserv++;
                        }else{
                            DrawThread.ArrayListForCoordinatesAndColors.clear();
                            DrawThread.ArrayListForCoordinatesAndColors=response.body();
                            counterforserv++;

                        }






                    }

                    @Override
                    public void onFailure(Call<ArrayList<CoordinatesAndColors>> call, Throwable t) {

                    }


                };

                    call.enqueue(callback);
                    /*IcheckIMEI service2 = retrofit.create(IcheckIMEI.class);
                    Call<Integer> call2 = service2.checkIMEI(prefs.getString("name",""),prefs.getString("IMEI", "NoIMEI"));
                    Callback<Integer> callback2 = new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if(response.body()==0){

                            ((Activity)context).finishAffinity();}



                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {

                        }
                    };
                    call2.enqueue(callback2);
*/







                Thread.sleep(7000);
                }

            } catch (Exception e) {

            }
        }
    }
}
