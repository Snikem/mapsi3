package com.example.mapsi3;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;


import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import static android.graphics.Color.RED;


public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback, GoogleMap.OnMapClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnCameraMoveListener, SeekBar.OnSeekBarChangeListener {


    public static GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;

    private boolean isCanceled = false;
    public static int startCounterClickformap = 0;


    public static double  currentLocationLat;
    public static double  currentLocationLng;
    public static float currenZoom=21;
    private SeekBar RedColor;
    private SeekBar GreenColor;
    private SeekBar BlueColor;
    public static int RedProgress=0;
    public static int GreenProgress=0;
    public static int BlueProgress=0;
    public static double longMapClickLat;
    public static double longMapClickLng;
    public static int counterForArray=0;
    private double firstTapLngForGrid,secondTapLngForGrid;













    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
         RedColor = (SeekBar) findViewById(R.id.RedSeekBar);
         GreenColor = (SeekBar) findViewById(R.id.GreenSeekBar);
         BlueColor = (SeekBar) findViewById(R.id.BlueSeekBar);
        RedColor.setOnSeekBarChangeListener(this);
        GreenColor.setOnSeekBarChangeListener(this);
        BlueColor.setOnSeekBarChangeListener(this);




        final Button zoomIn = (Button) findViewById(R.id.zoom_in);
        final Button zoomOut = (Button) findViewById(R.id.zoom_out);
        final Button changeactivity = (Button) findViewById(R.id.changestylename1);


        zoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View zoomIn) {
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
                currenZoom= mMap.getCameraPosition().zoom;
            }
        });
        zoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View zoomOut) {
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
                currenZoom= mMap.getCameraPosition().zoom;
            }
        });
        changeactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View changestylename2) {



            }
        });





        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }





    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMapClickListener(this);
        mMap.setOnCameraMoveListener(this);

        enableMyLocation();
        mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        getApplicationContext(), R.raw.map_slyle_retro_unname));

        LatLng startLocation = new LatLng(47.230484, 39.822347);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLocation,21));
        currentLocationLat = mMap.getCameraPosition().target.latitude;
        currentLocationLng = mMap.getCameraPosition().target.longitude;
    }



    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);

        }
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }
    private double makeGridForLng(double coor) {
       // (xSecondTap-xFistTap)/(LngFirstTapForDrawThread-LngSecondTapForDrawThread)
        double cof = (secondTapLngForGrid-firstTapLngForGrid)/(DrawOnMap.secondTapxForgrid-DrawOnMap.firstTapxForgrid);
        cof = cof*461.37344;


        double r =coor/cof;

        r = Math.round(r);
        coor = cof*r;
        return coor;
    }
    private double makeGridForLat(double coor){

        double r =coor/0.00021008133;
        System.out.println(r);
        r = Math.round(r);
        coor = 0.00021008133*r;
        return coor;
    }


    @Override
    public void onMapClick(LatLng point) {
        startCounterClickformap++;
        if(startCounterClickformap==1){
            DrawThread.LatFirstTapForDrawThread=point.latitude;
            DrawThread.LngFirstTapForDrawThread=point.longitude;
            firstTapLngForGrid=point.longitude;



        }
        if(startCounterClickformap==2){
            DrawThread.LatSecondTapForDrawThread=point.latitude;
            DrawThread.LngSecondTapForDrawThread=point.longitude;
            secondTapLngForGrid=point.longitude;
            startCounterClickformap++;
        }
        if(startCounterClickformap>3){
            counterForArray++;
            double LatitudePx = point.latitude;
            double LongitudePx = point.longitude;
           LatitudePx=makeGridForLat(LatitudePx);
            LongitudePx=makeGridForLng(LongitudePx);

            longMapClickLat = LatitudePx;
            longMapClickLng = LongitudePx;
        }
    }






    @Override
    public void onMapLongClick(LatLng latLng) {
       /*(40000/360)*cos(n)
       counterForArray++;
       double LatitudePx = latLng.latitude;
       double LongitudePx = latLng.longitude;
        longMapClickLat = LatitudePx;
        longMapClickLng = LongitudePx;*/

    }


    @Override
    public void onCameraMove() {
       currenZoom= mMap.getCameraPosition().zoom;
        currentLocationLat = mMap.getCameraPosition().target.latitude;
        currentLocationLng = mMap.getCameraPosition().target.longitude;

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        RedProgress = RedColor.getProgress();
        GreenProgress = GreenColor.getProgress();
        BlueProgress = BlueColor.getProgress();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
