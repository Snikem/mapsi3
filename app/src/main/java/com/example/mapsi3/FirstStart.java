package com.example.mapsi3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;


public class FirstStart extends AppCompatActivity {

    String NICKNAME;
    SharedPreferences prefs;
    String IMEINumber;
    public static int checkusertopass = 0;
    public static int adduserstopass = 0;
    TelephonyManager telephonyManager;
    private TextInputLayout textInputLayout;
    int counterforRegistr = 1;
    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start);
        textInputLayout = (TextInputLayout) findViewById(R.id.outlinedTextField3);
        final EditText nickname1 = (EditText) findViewById(R.id.edittextforname1);
        final Button apply = (Button) findViewById(R.id.applyforname1);
        final Button changeentry = (Button) findViewById(R.id.changentry1);
        final EditText passworld = (EditText) findViewById(R.id.passworld);
        final EditText passworldconfirm = (EditText) findViewById(R.id.passworldconfirm);

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        prefs = getSharedPreferences("IMEI", MODE_PRIVATE);
        changeentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counterforRegistr % 2 == 1) {
                    textInputLayout.setVisibility(View.INVISIBLE);
                    passworldconfirm.setVisibility(View.INVISIBLE);
                    changeentry.setText("Пройти регистрацию");
                    apply.setText("Войти");
                } else {
                    passworldconfirm.setVisibility(View.VISIBLE);
                    textInputLayout.setVisibility(View.VISIBLE);
                    changeentry.setText("Есть аккаунт");
                    apply.setText("Регистрация");
                }
                counterforRegistr++;
            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counterforRegistr % 2 != 1) {
                    if (isCheckusertopass(nickname1.getText().toString(), passworld.getText().toString())) {
                        new UpdateIMEI(nickname1.getText().toString(), prefs.getString("IMEI", IMEINumber)).execute();
                        NICKNAME = nickname1.getText().toString();
                        save(NICKNAME);
                        Intent i = new Intent(FirstStart.this, MainActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "проверьте данные", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (passworld.getText().toString().equals(passworldconfirm.getText().toString())) {
                        if (isAdduserstopass(nickname1.getText().toString(), passworld.getText().toString(), prefs.getString("IMEI", IMEINumber))) {
                            NICKNAME = nickname1.getText().toString();
                            save(NICKNAME);
                            Intent i = new Intent(FirstStart.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "Такое имя уже есть ", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "проверьте пароль", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    }
                    IMEINumber = telephonyManager.getDeviceId();
                    prefs.edit().putString("IMEI", IMEINumber).apply();
                    // Toast.makeText(this, "Permission granted.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public boolean isCheckusertopass(String name, String IMEI) {
        boolean rezbool;
        new CheckusertopassServ(name, IMEI).execute();
        while (checkusertopass % 2 == 0) {

        }
        if (CheckusertopassServ.rezultCheckusertopassServ == 1) {
            rezbool = true;
        } else {
            rezbool = false;
        }
        checkusertopass++;
        return rezbool;

    }

    public boolean isAdduserstopass(String name, String passworld, String IMEI) {
        boolean rezbool;
        new Addnewusertopass(name, passworld, IMEI).execute();
        while (adduserstopass % 2 == 0) {

            Log.d("ждем сервер", "asd");
        }
        if (Addnewusertopass.rezultAddnewusertopass == 1) {
            rezbool = true;
        } else {
            rezbool = false;
        }
        adduserstopass++;

        return rezbool;

    }

    public void save(String name) {

        SharedPreferences sharedPreferences;


        sharedPreferences = getSharedPreferences("name", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("name", name);
        editor.apply();
    }

    public String load() {
        SharedPreferences sharedPreferences = getSharedPreferences("name", MODE_PRIVATE);
        String loadedText = sharedPreferences.getString("name", "");
        return loadedText;
    }
}
