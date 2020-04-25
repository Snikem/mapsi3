package com.example.mapsi3;

import android.content.Context;
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

    private double coefficientByX = (xFistTap-xSecondTap)/(MapsActivity.LngFirstTap-MapsActivity.LngSecondTap);

    private double coefficientByY=(ySecondTap-yFirstTap)/(MapsActivity.LatFirstTap-MapsActivity.LatSecondTap);

    private double currentLocationByLat=MapsActivity.currentLocation.latitude;

    private double currentLocationByLng=MapsActivity.currentLocation.longitude;

    public double totalValueByX=(currentLocationByLng -39.71224654465914)*coefficientByX;

    public double totalValueByY=(currentLocationByLat-47.30355458310492)*coefficientByY;
    double totalValueByX_DOUBLE = totalValueByX;
    float totalValueByX_FLOAT = (float)totalValueByX_DOUBLE;
    double totalValueByY_DOUBLE = totalValueByY;
    float totalValueByY_FLOAT = (float)totalValueByY_DOUBLE;









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
                    canvas.drawCircle(totalValueByX_FLOAT+canvas.getWidth()/2,totalValueByY_FLOAT+canvas.getHeight()/2,100000,p);



                    // рисование на canvas
                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
