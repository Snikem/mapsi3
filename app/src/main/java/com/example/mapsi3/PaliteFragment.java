package com.example.mapsi3;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import static android.content.Context.MODE_PRIVATE;


public class PaliteFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private SeekBar RedColor;
    private SeekBar GreenColor;
    private SeekBar BlueColor;
    public static int RedProgress;
    public static int GreenProgress;
    public static int BlueProgress;
    SharedPreferences SeekPrefs;
    View changecolor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_palite, container, false);
        SeekPrefs = this.getActivity().getSharedPreferences("Seek", MODE_PRIVATE);
        View rgb163_0_0 = (View) layout.findViewById(R.id.rgb163_0_0);
        View rgb235_30_80 = (View) layout.findViewById(R.id.rgb235_30_80);
        View rgb160_23_194 = (View) layout.findViewById(R.id.rgb160_23_194);
        View rgb69_23_194 = (View) layout.findViewById(R.id.rgb69_23_194);
        View rgb13_19_140 = (View) layout.findViewById(R.id.rgb13_19_140);
        View rgb18_108_199 = (View) layout.findViewById(R.id.rgb18_108_199);
        View rgb18_199_78 = (View) layout.findViewById(R.id.rgb18_199_78);
        View rgb18_199_184 = (View) layout.findViewById(R.id.rgb18_199_184);
        View rgb36_105_19 = (View) layout.findViewById(R.id.rgb36_105_19);
        View rgb227_216_11 = (View) layout.findViewById(R.id.rgb227_216_11);
        View rgb194_114_10 = (View) layout.findViewById(R.id.rgb194_114_10);
        View rgb_250_150_0 = (View) layout.findViewById(R.id.rgb_250_150_0);
        View rgb0_0_0 = (View) layout.findViewById(R.id.rgb0_0_0);
        View rgb84_14_102 = (View) layout.findViewById(R.id.rgb84_14_102);
        View rgb255_255_255 = (View) layout.findViewById(R.id.rgb255_255_255);
        View rgb255_0_0 = (View) layout.findViewById(R.id.rgb255_0_0);
        changecolor = (View) layout.findViewById(R.id.changecolor);
        RedColor = (SeekBar) layout.findViewById(R.id.RedSeekBar);
        GreenColor = (SeekBar) layout.findViewById(R.id.GreenSeekBar);
        BlueColor = (SeekBar) layout.findViewById(R.id.BlueSeekBar);
        RedColor.setOnSeekBarChangeListener(this);
        GreenColor.setOnSeekBarChangeListener(this);
        BlueColor.setOnSeekBarChangeListener(this);
        rgb163_0_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RedColor.setProgress(163);
                GreenColor.setProgress(0);
                BlueColor.setProgress(0);
            }
        });
        rgb235_30_80.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RedColor.setProgress(235);
                GreenColor.setProgress(30);
                BlueColor.setProgress(80);
            }
        });

        rgb160_23_194.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RedColor.setProgress(160);
                GreenColor.setProgress(23);
                BlueColor.setProgress(194);
            }
        });
        rgb69_23_194.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RedColor.setProgress(69);
                GreenColor.setProgress(23);
                BlueColor.setProgress(194);
            }
        });
        rgb13_19_140.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RedColor.setProgress(13);
                GreenColor.setProgress(19);
                BlueColor.setProgress(140);
            }
        });
        rgb18_108_199.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RedColor.setProgress(18);
                GreenColor.setProgress(108);
                BlueColor.setProgress(199);
            }
        });
        rgb18_199_78.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RedColor.setProgress(18);
                GreenColor.setProgress(199);
                BlueColor.setProgress(78);
            }
        });
        rgb18_199_184.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RedColor.setProgress(18);
                GreenColor.setProgress(199);
                BlueColor.setProgress(184);
            }
        });
        rgb36_105_19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RedColor.setProgress(36);
                GreenColor.setProgress(105);
                BlueColor.setProgress(19);
            }
        });
        rgb227_216_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RedColor.setProgress(227);
                GreenColor.setProgress(216);
                BlueColor.setProgress(11);
            }
        });
        rgb194_114_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RedColor.setProgress(194);
                GreenColor.setProgress(114);
                BlueColor.setProgress(10);
            }
        });
        rgb_250_150_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RedColor.setProgress(250);
                GreenColor.setProgress(150);
                BlueColor.setProgress(0);
            }
        });
        rgb0_0_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RedColor.setProgress(0);
                GreenColor.setProgress(0);
                BlueColor.setProgress(0);
            }
        });
        rgb84_14_102.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RedColor.setProgress(84);
                GreenColor.setProgress(14);
                BlueColor.setProgress(102);
            }
        });
        rgb255_255_255.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RedColor.setProgress(255);
                GreenColor.setProgress(255);
                BlueColor.setProgress(255);
            }
        });
        rgb255_0_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RedColor.setProgress(255);
                GreenColor.setProgress(0);
                BlueColor.setProgress(0);
            }
        });
        return layout;
    }

    public PaliteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        RedColor.setProgress(SeekPrefs.getInt("red", 0));
        GreenColor.setProgress(SeekPrefs.getInt("green", 0));
        BlueColor.setProgress(SeekPrefs.getInt("blue", 0));

    }

    @Override
    public void onPause() {
        super.onPause();
        SeekPrefs.edit().putInt("red", RedProgress).apply();
        SeekPrefs.edit().putInt("green", GreenProgress).apply();
        SeekPrefs.edit().putInt("blue", BlueProgress).apply();


    }

    public static PaliteFragment newInstance(String param1, String param2) {
        PaliteFragment fragment = new PaliteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        RedProgress = RedColor.getProgress();
        GreenProgress = GreenColor.getProgress();
        BlueProgress = BlueColor.getProgress();
        changecolor.setBackgroundColor(Color.rgb(RedProgress, GreenProgress, BlueProgress));

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
