package com.example.mapsi3;

public class Colors {
    public int red;
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
    public int green;
    public int blue;

    public Colors() {
        red=0;
        green=0;
        blue=0;
    }
    public Colors(int r,int g,int b) {
        this.red=r;
        this.green=g;
        this.blue=b;
    }
}
