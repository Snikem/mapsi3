package com.example.mapsi3;

import com.google.gson.Gson;

public class FirstSettings {
    public double LngFirstTapForDrawThread, LatFirstTapForDrawThread, LatSecondTapForDrawThread,
            LngSecondTapForDrawThread;
    public float zoom;
    public int firstTapxForgrid, secondTapxForgrid, firstTapyForgrid, secondTapyForgrid;

    public FirstSettings(double LngFirstTapForDrawThread, double LatFirstTapForDrawThread, double LatSecondTapForDrawThread,
                         double LngSecondTapForDrawThread, int firstTapxForgrid,
                         int secondTapxForgrid, int firstTapyForgrid, int secondTapyForgrid, float zoom) {
        this.firstTapxForgrid = firstTapxForgrid;
        this.firstTapyForgrid = firstTapyForgrid;
        this.LatFirstTapForDrawThread = LatFirstTapForDrawThread;
        this.LngFirstTapForDrawThread = LngFirstTapForDrawThread;
        this.secondTapxForgrid = secondTapxForgrid;
        this.secondTapyForgrid = secondTapyForgrid;
        this.LatSecondTapForDrawThread = LatSecondTapForDrawThread;
        this.LngSecondTapForDrawThread = LngSecondTapForDrawThread;
        this.zoom = zoom;
    }

    public String toStri() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static FirstSettings fromString(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, FirstSettings.class);
    }
}
