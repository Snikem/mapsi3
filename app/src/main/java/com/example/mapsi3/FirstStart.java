package com.example.mapsi3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FirstStart extends AppCompatActivity {

     String NICKNAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start);
        final EditText nickname = (EditText)findViewById(R.id.edittextforname1);
        Button apply = (Button)findViewById(R.id.applyforname1);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NICKNAME= nickname.getText().toString();
                MapsActivity.nickname.setText(load());





                save(NICKNAME);


            }
        });
    }
    public void save(String name){

        SharedPreferences sharedPreferences;


        sharedPreferences = getSharedPreferences("name",MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();

        editor.putString("name",name);
        editor.apply();



    }
    public String load(){
        SharedPreferences sharedPreferences = getSharedPreferences("name",MODE_PRIVATE);
        String loadedText = sharedPreferences.getString("name","");
        return loadedText;
    }

}
