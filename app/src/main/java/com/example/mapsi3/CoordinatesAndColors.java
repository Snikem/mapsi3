package com.example.mapsi3;

public class CoordinatesAndColors {
    double latitude;
    double longitude;
    int red;
    int green;
    int blue;
    public CoordinatesAndColors(double latitude,double longitude,int red,int green,int blue) {
        this.latitude=latitude;
        this.longitude=longitude;
        this.red=red;
        this.green=green;
        this.blue=blue;
    }
    public double   getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public int getRed() {
        return red;
    }
    public void setRed(int red) {
        this.red = red;
    }
    public int getGreen() {
        return green;
    }
    public void setGreen(int green) {
        this.green = green;
    }
    public int getBlue() {
        return blue;
    }
    public void setBlue(int blue) {
        this.blue = blue;
    }

}
