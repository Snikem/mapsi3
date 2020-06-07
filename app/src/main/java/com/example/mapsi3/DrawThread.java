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
import android.view.Surface;
import android.view.SurfaceHolder;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.mapsi3.REST.ForCoordinatesAndColorsGet;
import com.example.mapsi3.REST.ForRangtopGetAlways;
import com.example.mapsi3.REST.IcheckIMEI;
import com.example.mapsi3.REST.Igetuser;

import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static android.graphics.Color.BLACK;
import static android.graphics.Color.rgb;


public class DrawThread extends Thread {
    private SurfaceHolder surfaceHolder;
    Context context;


    Thread thread = new Thread(new GetServer());
    SharedPreferences prefs;

    private ArrayList<CoordinatesAndColors> ArrayListForCoordinatesAndColors = new ArrayList<CoordinatesAndColors>();
    private ArrayList<CoordinatesAndColors> ArrayListForCoordinatesAndColors2 = new ArrayList<CoordinatesAndColors>();
    public static ArrayList<User> Userspoint = new ArrayList<User>();


    private int counterForArray2 = 0;
    public static int counterForArray1 = 0;
    public static int pauseThread = 1;
    public static int counterforserv = 0;


    private volatile boolean running = true;
    private Paint p = new Paint();

    public static int xFirstTap;
    public static int yFirstTap;
    public static int xSecondTap;
    public static int ySecondTap;


    public DrawThread(Context context, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        this.context = context;
    }

    public void StartServ() {
        thread.start();
    }

    public static int drawpx = 1;
    public static int drawloc = 1;
    public static int drawAll = 1;


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
            Canvas canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas();
            } catch (Exception ex) {
                Log.d("Bang!!!", "WTF");
                try {
                    sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    continue;
                }
            }
            if (canvas != null) {
                try {
                    synchronized (surfaceHolder) {
                        Log.d("asdfg", Userspoint.size() + "  ");
                        int redColorForPaint_INT = PaliteFragment.RedProgress;
                        int greenColorForPaint_INT = PaliteFragment.GreenProgress;
                        int blueColorForPaint_INT = PaliteFragment.BlueProgress;

                        if (counterForArray1 > counterForArray2) {
                            counterForArray2++;
                            ArrayListForCoordinatesAndColors.add(new CoordinatesAndColors(MapsFragment.longMapClickLat, MapsFragment.longMapClickLng, redColorForPaint_INT, greenColorForPaint_INT, blueColorForPaint_INT));

                        }

                        double cooficentx = (xSecondTap - xFirstTap) / (LngFirstTapForDrawThread - LngSecondTapForDrawThread);
                        double cooficenty = (ySecondTap - yFirstTap) / (LatFirstTapForDrawThread - LatSecondTapForDrawThread);
                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        float g1 = (float) Math.pow(2, MapsFragment.currenZoom) / 100000;
                        double r2 = (0.00021008133 * ((ySecondTap - yFirstTap) / (LatSecondTapForDrawThread - LatFirstTapForDrawThread))) / (20.97152 * 2);
                        float r3 = (float) r2;
                        Log.d("rty", drawpx + "     " + drawloc);
                        if (drawAll % 2 == 1) {
                            if (drawpx % 2 == 1) {
                                for (int i = 0; i < ArrayListForCoordinatesAndColors.size(); i++) {
                                    double LatTap = ArrayListForCoordinatesAndColors.get(i).latitude;
                                    double LngTap = ArrayListForCoordinatesAndColors.get(i).longitude;

                                    p.setColor(rgb(ArrayListForCoordinatesAndColors.get(i).red, ArrayListForCoordinatesAndColors.get(i).green,
                                            ArrayListForCoordinatesAndColors.get(i).blue));

                                    float Totalx = (float) (((MapsFragment.currentLocationCamLng - LngTap) * (cooficentx / Math.pow(2, 21 - MapsFragment.currenZoom))) + canvas.getWidth() / 2);
                                    float Totaly = (float) (((MapsFragment.currentLocationCamLat - LatTap) * (cooficenty / Math.pow(2, 21 - MapsFragment.currenZoom))) + canvas.getHeight() / 2);
                                    canvas.drawRect(Totalx - g1 * r3, Totaly - g1 * r3, Totalx + g1 * r3, Totaly + g1 * r3, p);
                                }
                            }

                            if (drawloc % 2 == 1) {
                                p.setColor(rgb(255, 255, 255));
                                canvas.drawCircle((float) ((MapsFragment.currentLocationCamLng - MapsFragment.currentLocationLng) * (cooficentx / Math.pow(2, 21 - MapsFragment.currenZoom)) + canvas.getWidth() / 2), (float) ((MapsFragment.currentLocationCamLat - MapsFragment.currentLocationLat) * (cooficenty / Math.pow(2, 21 - MapsFragment.currenZoom)) + canvas.getHeight() / 2), 30, p);
                                p.setColor(rgb(23, 23, 227));
                                canvas.drawCircle((float) ((MapsFragment.currentLocationCamLng - MapsFragment.currentLocationLng) * (cooficentx / Math.pow(2, 21 - MapsFragment.currenZoom)) + canvas.getWidth() / 2), (float) ((MapsFragment.currentLocationCamLat - MapsFragment.currentLocationLat) * (cooficenty / Math.pow(2, 21 - MapsFragment.currenZoom)) + canvas.getHeight() / 2), 24, p);
                            }
                            //код снизу ризует кнопки и иконку профиля , пока работает только у меня на смартфоне
                            canvas.drawBitmap(MainActivity.bitmapPof, -43, canvas.getHeight() - 280, p);
                            canvas.drawBitmap(MainActivity.bitmapLocOnOff, canvas.getWidth() - 208, canvas.getHeight() - 367, p);
                            canvas.drawBitmap(MainActivity.bitmapSurfaceOnOff, canvas.getWidth() - 208, canvas.getHeight() - 400, p);
                            p.setTextSize(42.0f);
                            p.setColor(BLACK);
                            canvas.drawText(load2(), 210, canvas.getHeight() - 90, p);
                            canvas.drawText("Очков:", 210, canvas.getHeight() - 30, p);
                            canvas.drawText(Integer.toString(MapsFragment.counterpxforserver), 350, canvas.getHeight() - 30, p);
                        }
                    }
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

    public String load2() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("name", MODE_PRIVATE);
        String loadedText = sharedPreferences.getString("name", "");
        return loadedText;
    }

    class GetServer extends Thread {

        @Override
        public void run() {
            try {
                while (true) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://proverkasbd.herokuapp.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    ForCoordinatesAndColorsGet service = retrofit.create(ForCoordinatesAndColorsGet.class);
                    Call<ArrayList<CoordinatesAndColors>> call = service.getCoordAndcolors();
                    Callback<ArrayList<CoordinatesAndColors>> callback = new Callback<ArrayList<CoordinatesAndColors>>() {
                        @Override
                        public void onResponse(Call<ArrayList<CoordinatesAndColors>> call, Response<ArrayList<CoordinatesAndColors>> response) {
                            ArrayListForCoordinatesAndColors = response.body();
                            counterforserv++;
                        }

                        @Override
                        public void onFailure(Call<ArrayList<CoordinatesAndColors>> call, Throwable t) {

                        }
                    };
                    call.enqueue(callback);
                    ForRangtopGetAlways service1 = retrofit.create(ForRangtopGetAlways.class);
                    Call<ArrayList<User>> call1 = service1.getTop();
                    Response<ArrayList<User>> response = call1.execute();
                    Userspoint = response.body();
                    Log.d("serv", "zapros zakoncen");
                    Thread.sleep(10000);
                }
            } catch (Exception e) {

            }
        }
    }
}
