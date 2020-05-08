package com.example.mapsi3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        MyPlaceList[] mass=  makePlace();


        AdapterForRangList adapter = new AdapterForRangList(this,mass);
        ListView lv = (ListView) findViewById(R.id.list23);
        lv.setAdapter(adapter);

    }

    MyPlaceList[] makePlace(){
        MyPlaceList[] arr = new MyPlaceList[10];
        int[] place = {1,2,3,4,5,6,7,8,9,10};
        String[] name = {"Ron", "Bob", "Bolic", "Tolik", "Helen", "Mazda", "Klaus", "Robert", "Andrey", "Jack"};
        int[] px = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31};
        for (int i = 0; i < arr.length; i++) {
           MyPlaceList placeList = new MyPlaceList();
           placeList.countPx = px[i];
           placeList.name = name[i];
           placeList.Place = place[i];
            arr[i] = placeList;
        }
        return arr;
    }
}
