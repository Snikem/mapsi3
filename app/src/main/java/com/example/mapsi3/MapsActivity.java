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
        ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMapLongClickListener,GoogleMap.OnCameraMoveListener {


    public static GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;

    private boolean isCanceled = false;
    public static int startCounterClickformap = 0;


    public static double  currentLocationLat;
    public static double  currentLocationLng;








    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);



        final Button zoomIn = (Button) findViewById(R.id.zoom_in);
        final Button zoomOut = (Button) findViewById(R.id.zoom_out);
        final Button changeactivity = (Button) findViewById(R.id.changestylename1);


        zoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View zoomIn) {
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });
        zoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View zoomOut) {
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
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

        LatLng startLocation = new LatLng(47.30355458310492, 39.71224654465914);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLocation,20));
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


    @Override
    public void onMapClick(LatLng point) {
        startCounterClickformap++;
        if(startCounterClickformap==1){
            DrawThread.LatFirstTapForDrawThread=point.latitude;
            DrawThread.LngFirstTapForDrawThread=point.longitude;



        }
        if(startCounterClickformap==2){
            DrawThread.LatSecondTapForDrawThread=point.latitude;
            DrawThread.LngSecondTapForDrawThread=point.longitude;
            startCounterClickformap++;



        }
    }






    @Override
    public void onMapLongClick(LatLng latLng) {


    }


    @Override
    public void onCameraMove() {

        currentLocationLat = mMap.getCameraPosition().target.latitude;
        currentLocationLng = mMap.getCameraPosition().target.longitude;

    }
}
