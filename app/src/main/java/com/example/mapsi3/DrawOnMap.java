package com.example.mapsi3;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;


public class DrawOnMap extends SurfaceView implements SurfaceHolder.Callback {
    private DrawThread drawThread;





    private int startCounterClickformap2 = 0;
    private int counterForSurface=0;


    public DrawOnMap(Context context, AttributeSet attributeSet){
        super(context, attributeSet);




        this.setBackgroundColor(Color.TRANSPARENT); this.setZOrderOnTop(true); getHolder().setFormat(PixelFormat.TRANSPARENT);


        getHolder().addCallback(this);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getContext(),getHolder());
      if(startCounterClickformap2>2){
          drawThread.start();

      }




    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }
    @Override
    public boolean onTouchEvent (MotionEvent event) {
        startCounterClickformap2++;
        if(startCounterClickformap2==1){
            DrawThread.xFirstTap = (int)event.getX();
            DrawThread.yFirstTap = (int)event.getY();



        }
        if(startCounterClickformap2==2){
           DrawThread.xSecondTap=(int)event.getX();
            DrawThread.ySecondTap = (int)event.getY();
            drawThread.StartServ();


        }
        if(startCounterClickformap2==3){
            drawThread.start();

        }


        return false;
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
