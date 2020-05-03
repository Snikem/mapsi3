package com.example.mapsi3;

import android.content.Context;
import android.content.Entity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.icu.text.UnicodeSetSpanner;
import android.os.AsyncTask;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.graphics.Color.RED;
import static android.graphics.Color.red;
import static android.graphics.Color.rgb;


public class DrawThread extends Thread {
    private SurfaceHolder surfaceHolder;

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
                    int countForPaint=0;
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
                        countForPaint++;


                    }
                    p.setColor(rgb(redColorForPaint_INT,greenColorForPaint_INT,blueColorForPaint_INT));
                    canvas.drawCircle(10,canvas.getHeight()-10,10,p);










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
