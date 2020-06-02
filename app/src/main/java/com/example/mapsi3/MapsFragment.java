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
import android.widget.ImageButton;
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
        ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnCameraMoveListener, SeekBar.OnSeekBarChangeListener {
    public static GoogleMap mMap;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    SupportMapFragment mapFragment;



    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;

    private boolean isCanceled = false;
    public static int startCounterClickformap = 0;
    public static int counterpxforserver = 0;
    Thread thread = new Thread(new ServerForGetRangTopAlways());


    public static final String APP_PREFERENCES = "myfirstsettings";


    public static double currentLocationCamLat;
    public static double currentLocationCamLng;
    public static double currentLocationLat;
    public static double currentLocationLng;
    public static float currenZoom = 21;


    private SeekBar RedColor;
    private SeekBar GreenColor;
    private SeekBar BlueColor;
    public static int RedProgress = 0;
    public static int GreenProgress = 0;
    public static int BlueProgress = 0;
    public static double longMapClickLat;
    public static double longMapClickLng;
    public static int counterForArray = 0;
    private double firstTapLngForGrid, secondTapLngForGrid, secondTapLatForGrid, firstTapLatForGrid;

    public static int counterForClick=0;
    public static int counterForClickserv=1;
    public static boolean g=true;


    private int countForSeekbar = 0;
    private float alphaForSeekBar;
    public static float pxForButton1;
    public static float pxForButton2;
    private ImageButton profileButton;


    SharedPreferences prefs = null;
    SharedPreferences intprefs;
    public int forFirstStart = 0;
    double currentBillTotal;
    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {


            currentLocationLat = location.getLatitude();
            currentLocationLng = location.getLongitude();
            if(forFirstStart==0) {
                LatLng startLocation = new LatLng(currentLocationLat, currentLocationLng);

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLocation, 21));
                forFirstStart++;
            }


        }
    };


    public static TextView nickname, countpxText;

    //private OnFragmentInteractionListener mListener;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.fragment_maps, container, false);

        if (savedInstanceState == null) {

            currentBillTotal = 0.0;

        }



        RedColor = (SeekBar) layout.findViewById(R.id.RedSeekBar);
        GreenColor = (SeekBar) layout.findViewById(R.id.GreenSeekBar);
        BlueColor = (SeekBar) layout.findViewById(R.id.BlueSeekBar);
        profileButton = (ImageButton) layout.findViewById(R.id.profile);
        nickname = (TextView) layout.findViewById(R.id.nickname);
        countpxText = (TextView) layout.findViewById(R.id.countpx);
        intprefs = this.getActivity().getSharedPreferences("firstrunInt", MODE_PRIVATE);
        intprefs.edit().putInt("firstrunInt", 0).apply();



        RedColor.setOnSeekBarChangeListener(this);
        GreenColor.setOnSeekBarChangeListener(this);
        BlueColor.setOnSeekBarChangeListener(this);
        final TableLayout table = (TableLayout) layout.findViewById(R.id.tablelayout);
        alphaForSeekBar = RedColor.getAlpha();
        RedColor.setAlpha(0);
        RedColor.setEnabled(false);
        GreenColor.setAlpha(0);
        GreenColor.setEnabled(false);
        BlueColor.setAlpha(0);
        BlueColor.setEnabled(false);
        table.setColumnCollapsed(0, !table.isColumnCollapsed(0));

        /*profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(i);

            }
        });*/


        final Button OffSur = (Button) layout.findViewById(R.id.offsurface);
        final Button zoomIn = (Button) layout.findViewById(R.id.zoom_in);
        final Button zoomOut = (Button) layout.findViewById(R.id.zoom_out);
        final Button changeactivity = (Button) layout.findViewById(R.id.changestylename1);
        Button offonLoc = (Button)layout.findViewById(R.id.locationoffon);
        prefs = this.getActivity().getSharedPreferences("first_start", MODE_PRIVATE);
        OffSur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View Offsur) {

                DrawThread.drawpx++;

            }
        });
        offonLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawThread.drawloc++;

            }
        });


        zoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View zoomIn) {
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
                currenZoom = mMap.getCameraPosition().zoom;
            }
        });
        zoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View zoomOut) {
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
                currenZoom = mMap.getCameraPosition().zoom;
            }
        });
        changeactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View changestylename2) {
                countForSeekbar++;
                table.setColumnCollapsed(0, !table.isColumnCollapsed(0));
                if (countForSeekbar % 2 != 1) {
                    RedColor.setAlpha(0);
                    RedColor.setEnabled(false);
                    GreenColor.setAlpha(0);
                    GreenColor.setEnabled(false);
                    BlueColor.setAlpha(0);
                    BlueColor.setEnabled(false);
                } else {
                    RedColor.setAlpha(alphaForSeekBar);
                    RedColor.setEnabled(true);
                    GreenColor.setAlpha(alphaForSeekBar);
                    GreenColor.setEnabled(true);
                    BlueColor.setAlpha(alphaForSeekBar);
                    BlueColor.setEnabled(true);


                }


            }
        });

        mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        if(mapFragment==null){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        mapFragment = SupportMapFragment.newInstance();
        ft.replace(R.id.map,mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
       return layout;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMapClickListener(this);
        mMap.setOnCameraMoveListener(this);
        mMap.setOnMyLocationChangeListener(myLocationChangeListener);
        enableMyLocation();




        mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        getContext(), R.raw.map_slyle_retro_unname));



        currentLocationCamLat = mMap.getCameraPosition().target.latitude;
        currentLocationCamLng = mMap.getCameraPosition().target.longitude;

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
                DrawThread.xSecondTap, DrawThread.yFirstTap, DrawThread.ySecondTap);

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
            DrawThread.LatFirstTapForDrawThread = point.latitude;
            DrawThread.LngFirstTapForDrawThread = point.longitude;
            firstTapLngForGrid = point.longitude;
            firstTapLatForGrid = point.latitude;
           // thread.start();


        }
        if (startCounterClickformap == 2) {
            DrawThread.LatSecondTapForDrawThread = point.latitude;
            DrawThread.LngSecondTapForDrawThread = point.longitude;
            secondTapLngForGrid = point.longitude;
            secondTapLatForGrid = point.latitude;
            startCounterClickformap++;

        }
        if (startCounterClickformap > 3 && g) {
            Log.d("Qrty","MapClickWork");


            double LatitudePx = point.latitude;
            double LongitudePx = point.longitude;
            if (Math.abs(currentLocationLat - LatitudePx) < 0.000349 *5 && Math.abs(currentLocationLng - LongitudePx) < 0.00027899999 *9.5 ) {
                LatitudePx = makeGridForLat(LatitudePx);
                LongitudePx = makeGridForLng(LongitudePx);
                counterForArray++;

                longMapClickLat = LatitudePx;
                longMapClickLng = LongitudePx;
                counterForClick++;

                g=false;
                Log.d("Qrty",g+" ");

                new Server(load2()).execute();

            } else {
                Toast.makeText(getContext(), "Подойдите ближе", Toast.LENGTH_SHORT).show();
            }


        }
    }


    @Override
    public void onMapLongClick(LatLng latLng) {


    }


    @Override
    public void onCameraMove() {
        currenZoom = mMap.getCameraPosition().zoom;
        currentLocationCamLat = mMap.getCameraPosition().target.latitude;
        currentLocationCamLng = mMap.getCameraPosition().target.longitude;

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
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }





































    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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




   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }*/
}
