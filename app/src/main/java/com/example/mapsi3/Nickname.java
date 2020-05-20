package com.example.mapsi3;

import com.google.gson.Gson;

public class Nickname {
    public String name;

    public Nickname(String name){
        this.name=name;
    }
    public  String toStri()
    {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    public static Nickname fromString(String json)
    {
        Gson gson = new Gson();
        return gson.fromJson(json,Nickname.class);
    }
}
