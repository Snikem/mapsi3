package com.example.mapsi3;

public class Coordinates {
    public double latitude ;
    public double longitude;
    public Coordinates() {
        latitude=0.0;
        longitude=0.0;
    }
    public Coordinates(double lat,double lng) {
        this.latitude=lat;
        this.longitude=lng;
    }
    public static boolean equals (Coordinates fresh , Coordinates old ) {
        boolean result;
        if (fresh.latitude == old.latitude && fresh.longitude == old.longitude) {
            result=true;
        }
        else {result=false;}
        return result;
    }
    public double getLatitude() {
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
}
