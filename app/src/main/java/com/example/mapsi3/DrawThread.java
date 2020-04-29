package com.example.mapsi3;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.icu.text.UnicodeSetSpanner;
import android.view.SurfaceHolder;
import android.widget.Toast;

import static android.graphics.Color.RED;
import static android.graphics.Color.red;
import static android.graphics.Color.rgb;


public class DrawThread extends Thread {
    private SurfaceHolder surfaceHolder;
    MyArrayListForPaint myArrayListForPaint = new MyArrayListForPaint();
    MyArrayListForCoordinates myArrayListForCoordinates = new MyArrayListForCoordinates(1);
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
                   if(MapsActivity.counterForArray>counterForArray2){
                        counterForArray2++;
                        myArrayListForCoordinates.add(MapsActivity.longMapClickLat);
                        myArrayListForCoordinates.add(MapsActivity.longMapClickLng);
                    }
                    p.setColor(RED);
                    double cooficentx=(xSecondTap-xFistTap)/(LngFirstTapForDrawThread-LngSecondTapForDrawThread);
                    double cooficenty=(ySecondTap-yFirstTap)/(LatFirstTapForDrawThread-LatSecondTapForDrawThread);

                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                    float Totalx1=(float) (( (MapsActivity.currentLocationLng-39.822347)*(cooficentx/Math.pow(2,21-MapsActivity.currenZoom)))+canvas.getWidth()/2);
                    float Totaly1=(float) (( (MapsActivity.currentLocationLat -47.230484)*(cooficenty/Math.pow(2,21-MapsActivity.currenZoom)))+canvas.getHeight()/2);

                    double forCoffLat=(cooficentx/Math.pow(2,21-MapsActivity.currenZoom));
                    double forCoffLng=(cooficenty/Math.pow(2,21-MapsActivity.currenZoom));
                    float g1 =(float) Math.pow(2,MapsActivity.currenZoom)/100000;
                    int redColorForPaint_INT = MapsActivity.RedProgress;
                    int greenColorForPaint_INT = MapsActivity.GreenProgress;
                    int blueColorForPaint_INT = MapsActivity.BlueProgress;
                    p.setColor(rgb(redColorForPaint_INT,greenColorForPaint_INT,blueColorForPaint_INT));


                    for(int i = 0;i<myArrayListForCoordinates.getSize();i=i+2){
                        double LatTap=myArrayListForCoordinates.get(i);
                        double LngTap=myArrayListForCoordinates.get(i+1);

                        float Totalx=(float) (( (MapsActivity.currentLocationLng-LngTap)*(cooficentx/Math.pow(2,21-MapsActivity.currenZoom)))+canvas.getWidth()/2);
                        float Totaly=(float) (( (MapsActivity.currentLocationLat -LatTap)*(cooficenty/Math.pow(2,21-MapsActivity.currenZoom)))+canvas.getHeight()/2);
                        canvas.drawRect(Totalx-g1*5,Totaly-g1*5,Totalx+g1*5,Totaly+g1*5,p);


                    }
                    canvas.drawRect(Totalx1-g1*5,Totaly1-g1*5,Totalx1+g1*5,Totaly1+g1*5,p);




                    if(MapsActivity.startCounterClickformap==10){
                        MapsActivity.startCounterClickformap=50;//для break point
                    }


                    // рисование на canvas
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
}
