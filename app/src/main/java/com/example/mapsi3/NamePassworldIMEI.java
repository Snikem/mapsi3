package com.example.mapsi3;

public class NamePassworldIMEI {
    String name;
    String passworld;
    String IMEI;
    public NamePassworldIMEI(String name,String passworld,String IMEI) {
        this.name=name;
        this.passworld=passworld;
        this.IMEI=IMEI;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassworld() {
        return passworld;
    }
    public void setPassworld(String passworld) {
        this.passworld = passworld;
    }
    public String getOnline() {
        return IMEI;
    }
    public void setOnline(String IMEI) {
        this.IMEI =IMEI;
    }

}
