package com.example.mapsi3;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import static android.content.Context.MODE_PRIVATE;


public class DrawOnMap extends SurfaceView implements SurfaceHolder.Callback {
    private DrawThread drawThread;
    private  SharedPreferences prefs;
    private String APP_PREFERENCES = "myfirstsettings";





    public static int startCounterClickformap2 = 0;
    private int counterForSurface=0;



    public DrawOnMap(Context context, AttributeSet attributeSet){

        super(context, attributeSet);
        getHolder().addCallback(this);
        prefs = context.getSharedPreferences("DrawMap",MODE_PRIVATE);
        this.setBackgroundColor(Color.TRANSPARENT);
        this.setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.RGBA_8888);







    }




    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("draw","created");
        drawThread = new DrawThread(getContext(),getHolder());
        if(prefs.getBoolean("first_start_Draw_map", true)){
            startCounterClickformap2=0;
        }
        else{

            startCounterClickformap2=4;




            FirstSettings loadTap = load();
            DrawThread.LngFirstTapForDrawThread = loadTap.LngFirstTapForDrawThread;
            DrawThread.LatFirstTapForDrawThread = loadTap.LatFirstTapForDrawThread;
            DrawThread.LatSecondTapForDrawThread = loadTap.LatSecondTapForDrawThread;
            DrawThread.LngSecondTapForDrawThread = loadTap.LngSecondTapForDrawThread;
            DrawThread.xFirstTap = loadTap.firstTapxForgrid;
            DrawThread.yFirstTap = loadTap.firstTapyForgrid;
            DrawThread.xSecondTap = loadTap.secondTapxForgrid;
            DrawThread.ySecondTap = loadTap.secondTapyForgrid;
            MapsFragment.currenZoom=loadTap.zoom;
            Log.d("draw","zpustilsy");
            drawThread.start();
            drawThread.StartServ();

        }
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }
    @Override
    public boolean onTouchEvent (MotionEvent event) {
        startCounterClickformap2++;
        if(startCounterClickformap2==1){
            if(prefs.getBoolean("first_start_Draw_map", true)) {
                DrawThread.xFirstTap = (int) event.getX();
                DrawThread.yFirstTap = (int) event.getY();
            }



        }
        if(startCounterClickformap2==2){

            if(prefs.getBoolean("first_start_Draw_map", true)){
                DrawThread.xSecondTap=(int)event.getX();
                DrawThread.ySecondTap = (int)event.getY();


                drawThread.StartServ();}



        }
        if(startCounterClickformap2==3){
            if(prefs.getBoolean("first_start_Draw_map", true)){
            prefs.edit().putBoolean("first_start_Draw_map",false).apply();
            drawThread.start();}

        }


        return false;
    }
    FirstSettings load() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        String loadedText = sharedPreferences.getString(APP_PREFERENCES, "");
        return FirstSettings.fromString(loadedText);
    }

    @Override
    public  void surfaceDestroyed(SurfaceHolder holder) {
        drawThread.requestStop();
        boolean retry = true;
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
                //
            }
        }
    }


}
