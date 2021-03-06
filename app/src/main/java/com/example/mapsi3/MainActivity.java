package com.example.mapsi3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import static android.graphics.Color.RED;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navigation;
    int currentHomeFragmentId;
    SharedPreferences prefs = null;
    SharedPreferences intprefs;
    DrawOnMap drawOnMap;
    FrameLayout frm;
    private ImageView profile;
    public static Bitmap bitmapPof, bitmapLocOnOff, bitmapSurfaceOnOff;
    public static final String APP_PREFERENCES = "myfirstsettings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = (BottomNavigationView) findViewById(R.id.bootomNavig);
        prefs = getSharedPreferences("first_start1", MODE_PRIVATE);
        intprefs = getSharedPreferences("firstrunInt2", MODE_PRIVATE);
        intprefs.edit().putInt("firstrunInt", 0).apply();
        profile = new ImageView(getApplicationContext());
        profile.setImageDrawable(getDrawable(R.drawable.pravelnaya));
        ImageView loc = new ImageView(getApplicationContext());
        ImageView brush = new ImageView(getApplicationContext());
        loc.setImageDrawable(getDrawable(R.drawable.q2222mm));
        brush.setImageDrawable(getDrawable(R.drawable.a11111));
        bitmapLocOnOff = ((BitmapDrawable) loc.getDrawable()).getBitmap();
        bitmapSurfaceOnOff = ((BitmapDrawable) brush.getDrawable()).getBitmap();
        bitmapPof = ((BitmapDrawable) profile.getDrawable()).getBitmap();

        loadHomeFragment(new MapsFragment(), R.id.home_nav_map);
        if (prefs.getBoolean("first_start1", true)) {
            prefs.edit().putBoolean("first_start1", false).apply();
            Intent i = new Intent(MainActivity.this, FirstStart.class);
            startActivity(i);

        }
        final MainActivity itself = this;
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                int itemId = item.getItemId();

                if (currentHomeFragmentId == itemId) return false;

                switch (itemId) {
                    case R.id.home_nav_map:
                        fragment = new MapsFragment();
                        break;

                    case R.id.home_nav_rating:
                        fragment = new RaitingFragment();
                        break;

                    case R.id.home_nav_palite:
                        fragment = new PaliteFragment();
                        break;

                    case R.id.home_nav_settings:

                        break;
                }

                return loadHomeFragment(fragment, itemId);
            }
        });
    }

    protected void onResume() {
        Toast.makeText(getApplicationContext(), "qwe", Toast.LENGTH_SHORT).show();
        super.onResume();
        DrawThread.counterforserv = prefs.getInt("countInt", 0);
        if (prefs.getBoolean("first_start", true)) {
            prefs.edit().putBoolean("first_start", false).apply();
            Intent i = new Intent(MainActivity.this, FirstStart.class);
            startActivity(i);

        } else {
            //MapsFragment.nickname.setText(load2());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        save();
        prefs.edit().putInt("countInt", DrawThread.counterforserv).apply();
    }

    private boolean loadHomeFragment(Fragment fragment, int id) {
        if (fragment == null) return false;
        currentHomeFragmentId = id;
        FragmentTransaction transition = getSupportFragmentManager().beginTransaction();
        transition.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
        transition.replace(R.id.mapsfragmentmainactivity, fragment);
        transition.commit();
        return true;
    }

    String load2() {
        SharedPreferences sharedPreferences = getSharedPreferences("name", MODE_PRIVATE);
        String loadedText = sharedPreferences.getString("name", "");
        return loadedText;
    }

    public void save() {
        SharedPreferences sharedPreferences;
        FirstSettings firstSettings = new FirstSettings(DrawThread.LngFirstTapForDrawThread, DrawThread.LatFirstTapForDrawThread,
                DrawThread.LatSecondTapForDrawThread, DrawThread.LngSecondTapForDrawThread, DrawThread.xFirstTap,
                DrawThread.xSecondTap, DrawThread.yFirstTap, DrawThread.ySecondTap, MapsFragment.currenZoom);
        sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(APP_PREFERENCES, firstSettings.toStri());
        editor.apply();


    }

    FirstSettings load() {
        SharedPreferences sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        String loadedText = sharedPreferences.getString(APP_PREFERENCES, "");
        return FirstSettings.fromString(loadedText);
    }
}
