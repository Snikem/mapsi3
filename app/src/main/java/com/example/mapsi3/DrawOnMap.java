package com.example.mapsi3;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;


public class DrawOnMap extends SurfaceView implements SurfaceHolder.Callback {
    private DrawThread drawThread;
    private int startCounterClickformap2 = 0;

    public DrawOnMap(Context context, AttributeSet attributeSet){
        super(context, attributeSet);

        this.setBackgroundColor(Color.TRANSPARENT);
        this.setZOrderOnTop(true); //necessary
        getHolder().setFormat(PixelFormat.TRANSPARENT);

        getHolder().addCallback(this);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getContext(),getHolder());
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        startCounterClickformap2++;
        if(startCounterClickformap2==1){


            drawThread.setFitstTap((int)event.getX(),(int)event.getY());

        }
        if(startCounterClickformap2==2){
            drawThread.setSecondTap((int)event.getX(),(int)event.getY());

        }


        return false;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
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
