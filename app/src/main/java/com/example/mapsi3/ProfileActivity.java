package com.example.mapsi3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    public static String NICKNAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageButton settings =(ImageButton) findViewById(R.id.settings);
        AdapterForRangList adapter = new AdapterForRangList(this,makeArrayPlace());
        ListView lv = (ListView) findViewById(R.id.list23);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        lv.setAdapter(adapter);
        final EditText nickname = (EditText)findViewById(R.id.edittextforname);
        Button apply = (Button)findViewById(R.id.applyforname);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NICKNAME= nickname.getText().toString();
                MapsActivity.nickname.setText(NICKNAME);
                new ServerGetForRang().execute();
                nickname.setText(NICKNAME);

            }
        });

    }
    ArrayList<MyPlaceList>makeArrayPlace(){
        ArrayList<MyPlaceList> arrP = new ArrayList<MyPlaceList>();
        int size = ServerForGetRangTopAlways.Userspoint.size()-1;

        for(int i = size-1;i>0;i--){
            MyPlaceList place = new MyPlaceList(size-i+1,ServerForGetRangTopAlways.Userspoint.get(i).name,ServerForGetRangTopAlways.Userspoint.get(i).score);
            arrP.add(place);
        }
        return arrP;

    }


}
