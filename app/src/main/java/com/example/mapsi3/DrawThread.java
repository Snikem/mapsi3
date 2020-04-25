package com.example.mapsi3;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.icu.text.UnicodeSetSpanner;
import android.view.SurfaceHolder;
import android.widget.Toast;

import static android.graphics.Color.RED;


public class DrawThread extends Thread {
    private SurfaceHolder surfaceHolder;
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





    /*private double coefficientByX = (xFistTap-xSecondTap)/(LngFirstTapForDrawThread-LngSecondTapForDrawThread);

    private double coefficientByY=(ySecondTap-yFirstTap)/(LatFirstTapForDrawThread-LatSecondTapForDrawThread);



    public double totalValueByX=(currentLocationByLng -39.71224654465914)*coefficientByX;

    public double totalValueByY=(currentLocationByLat-47.30355458310492)*coefficientByY;
    double totalValueByX_DOUBLE = totalValueByX;
    float totalValueByX_FLOAT = (float)totalValueByX_DOUBLE;
    double totalValueByY_DOUBLE = totalValueByY;
    float totalValueByY_FLOAT = (float)totalValueByY_DOUBLE;*/
    private double currentLocationByLat=MapsActivity.currentLocation.latitude;

    private double currentLocationByLng=MapsActivity.currentLocation.longitude;
    public float TotalTotalx(){
        double totalValueByX_DOUBLE = (currentLocationByLng -39.71224654465914)*(xFistTap-xSecondTap)/(LngFirstTapForDrawThread-LngSecondTapForDrawThread);

        return (float)totalValueByX_DOUBLE;

    }
    public float TotalTotaly(){

        double totalValueByY_DOUBLE = (currentLocationByLat -47.30355458310492)*(yFirstTap-ySecondTap)/(LatFirstTapForDrawThread-LatSecondTapForDrawThread);
        return (float)totalValueByY_DOUBLE;
    }









    public void requestStop() {
        running = false;
    }


    @Override
    public void run() {

        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                try {
                    p.setColor(RED);
                    canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, 5, p);
                    canvas.drawCircle(TotalTotalx()+canvas.getWidth()/2,TotalTotaly()+canvas.getHeight()/2,10,p);
                    if(MapsActivity.startCounterClickformap==4){
                        MapsActivity.startCounterClickformap=50;//для break point
                    }


                    // рисование на canvas
                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
