package com.example.mapsi3;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;

import static android.content.Context.MODE_PRIVATE;


public class MapsFragment extends Fragment implements
        OnMapReadyCallback, GoogleMap.OnMapClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnCameraMoveListener {
    public static GoogleMap mMap;
    private LatLng prefsloc;
    private float prefszoom;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    SupportMapFragment mapFragment;
    private boolean d = false;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;

    private boolean isCanceled = false;
    public static int startCounterClickformap = 0;
    public static int counterpxforserver = 0;

    public static final String APP_PREFERENCES = "myfirstsettings";

    public static double currentLocationCamLat;
    public static double currentLocationCamLng;
    public static double currentLocationLat;
    public static double currentLocationLng;
    public static float currenZoom = 21;

    public static double longMapClickLat;
    public static double longMapClickLng;
    public static int counterForArray = 0;
    private double firstTapLngForGrid, secondTapLngForGrid, secondTapLatForGrid, firstTapLatForGrid;

    public static int counterForClick = 0;
    public static int counterForClickserv = 1;
    public static boolean g = true;

    private int countForSeekbar = 0;
    private float alphaForSeekBar;
    public static float pxForButton1;
    public static float pxForButton2;
    private ImageButton profileButton;
    DrawOnMap drawOnMap;
    FrameLayout frm;
    SharedPreferences prefs;
    private double prefsLocationLat;
    private double prefsLocationLng;

    public int forFirstStart = 0;
    private int currentBillTotal;
    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            currentLocationLat = location.getLatitude();
            currentLocationLng = location.getLongitude();
            Log.d("anton",prefs.getBoolean("firstStartTomap",true)+"  "+prefs.getBoolean("qwertyasd",false));

            if ( prefs.getBoolean("firstStartTomap",true)) {
                Log.d("pused","w22er");

                LatLng startLocation = new LatLng(currentLocationLat, currentLocationLng);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLocation, 21));
                prefsLocationLat = currentLocationLat;
                prefsLocationLng = currentLocationLng;
                prefs.edit().putBoolean("firstStartTomap",false).apply();



            } else {
                if(prefs.getBoolean("qwertyasd",false)){
                    startCounterClickformap = 5;
                }
            }

        }
    };
    public static TextView nickname, countpxText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_maps, container, false);

        prefs = this.getActivity().getSharedPreferences("mapsFragment", MODE_PRIVATE);
        Button locOnOffButton = (Button) layout.findViewById(R.id.loacationonoff_button);
        Button brushButton = (Button) layout.findViewById(R.id.brush_button2);
        locOnOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawThread.drawloc++;
            }

        });
        brushButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawThread.drawpx++;
            }
        });
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        return layout;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.setOnCameraMoveListener(this);
        mMap.setOnMyLocationChangeListener(myLocationChangeListener);
        enableMyLocation();
        Log.d("qwe", "onMapReady");
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(prefsloc, prefszoom));
        mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        getContext(), R.raw.map_slyle_retro_unname));
        currentLocationCamLat = mMap.getCameraPosition().target.latitude;
        currentLocationCamLng = mMap.getCameraPosition().target.longitude;
        //mMap.getProjection().fromScreenLocation()

    }

    String load2() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("name", MODE_PRIVATE);
        String loadedText = sharedPreferences.getString("name", "");
        return loadedText;
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission((MainActivity) this.getActivity(), LOCATION_PERMISSION_REQUEST_CODE,
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
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getChildFragmentManager(), "dialog");
    }

    private double makeGridForLng(double coor) {
        double cof = 0.00030897111;
        double r = coor / cof;
        r = Math.round(r);
        coor = cof * r;
        return coor;
    }

    public void save() {
        SharedPreferences sharedPreferences;
        FirstSettings firstSettings = new FirstSettings(DrawThread.LngFirstTapForDrawThread, DrawThread.LatFirstTapForDrawThread,
                DrawThread.LatSecondTapForDrawThread, DrawThread.LngSecondTapForDrawThread, DrawThread.xFirstTap,
                DrawThread.xSecondTap, DrawThread.yFirstTap, DrawThread.ySecondTap, MapsFragment.currenZoom);
        sharedPreferences = this.getActivity().getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(APP_PREFERENCES, firstSettings.toStri());
        editor.apply();
    }

    FirstSettings load() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        String loadedText = sharedPreferences.getString(APP_PREFERENCES, "");
        return FirstSettings.fromString(loadedText);
    }

    private double makeGridForLat(double coor) {
        double cof = 0.00021008133;
        double r = coor / cof;
        r = Math.round(r);
        coor = cof * r;
        return coor;
    }


    @Override
    public void onMapClick(LatLng point) {
        startCounterClickformap++;
        if (startCounterClickformap == 1) {
            pxForButton1 = dpToPx(11);
            pxForButton2 = dpToPx(45);
            Log.d("settt","Map1");
            DrawThread.LatFirstTapForDrawThread = point.latitude;
            DrawThread.LngFirstTapForDrawThread = point.longitude;
            firstTapLngForGrid = point.longitude;
            firstTapLatForGrid = point.latitude;

        }
        if (startCounterClickformap == 2) {
            Log.d("settt","Map2");
            DrawThread.LatSecondTapForDrawThread = point.latitude;
            DrawThread.LngSecondTapForDrawThread = point.longitude;
            secondTapLngForGrid = point.longitude;
            secondTapLatForGrid = point.latitude;
        }
        if (startCounterClickformap == 3) {
            prefs.edit().putBoolean("qwertyasd",true).apply();
        }


        if (startCounterClickformap > 3) {


            double LatitudePx = point.latitude;
            double LongitudePx = point.longitude;
            if (Math.abs(currentLocationLat - LatitudePx) < 0.000349 * 5 && Math.abs(currentLocationLng - LongitudePx) < 0.00027899999 * 9.5) {
                LatitudePx = makeGridForLat(LatitudePx);
                LongitudePx = makeGridForLng(LongitudePx);
                DrawThread.counterForArray1++;
                longMapClickLat = LatitudePx;
                longMapClickLng = LongitudePx;
                counterForClick++;
                new Server(load2()).execute();
            } else {
                Toast.makeText(getContext(), "Подойдите ближе", Toast.LENGTH_SHORT).show();
            }


        }
    }

    @Override
    public void onCameraMove() {
        currenZoom =   mMap.getCameraPosition().zoom;

        currentLocationCamLat = mMap.getCameraPosition().target.latitude;
        currentLocationCamLng = mMap.getCameraPosition().target.longitude;

    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @Override
    public void onPause() {
        String latcam = Double.toString(currentLocationCamLat);
        String lngcam = Double.toString(currentLocationCamLng);
        String loclat = Double.toString(currentLocationLat);
        String loclng = Double.toString(currentLocationCamLng);



            prefs.edit().putString("lat", latcam).apply();
            prefs.edit().putString("lng", lngcam).apply();
            prefs.edit().putFloat("zoom", currenZoom).apply();
            prefs.edit().putString("locLat", loclat).apply();
            prefs.edit().putString("locLng", loclng).apply();
            save();
            Log.d("pused","wersave");


        Log.d("pused","paused");
        if (DrawThread.drawAll % 2 == 1) {
            DrawThread.drawAll++;
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.d("qweasd", currentBillTotal + "as");
        if (DrawThread.drawAll % 2 == 0) {
            DrawThread.drawAll++;
        }
        double lat = Double.parseDouble(prefs.getString("lat", "47"));
        double lng = Double.parseDouble(prefs.getString("lng", "47"));
        prefsLocationLat = Double.parseDouble(prefs.getString("locLat", "0"));
        prefsLocationLng = Double.parseDouble(prefs.getString("locLng", "0"));
        prefszoom = prefs.getFloat("zoom", 21);
        prefsloc = new LatLng(lat, lng);
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        if (savedInstanceState == null) {
            Log.d("qweasd", currentBillTotal + "a122");
            currentBillTotal = 0;
        } else {
            currentBillTotal = 1;
        }
    }

    public MapsFragment() {
        // Required empty public constructor
    }

    public static MapsFragment newInstance(String param1, String param2) {
        MapsFragment fragment = new MapsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
}
