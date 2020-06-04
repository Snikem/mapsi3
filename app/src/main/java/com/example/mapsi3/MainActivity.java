package com.example.mapsi3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
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
    public static final String APP_PREFERENCES = "myfirstsettings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = (BottomNavigationView)findViewById(R.id.bootomNavig);
        prefs = getSharedPreferences("first_start1", MODE_PRIVATE);
        intprefs = getSharedPreferences("firstrunInt2", MODE_PRIVATE);

        intprefs.edit().putInt("firstrunInt", 0).apply();

        loadHomeFragment(new MapsFragment(), R.id.home_nav_map);
        if (prefs.getBoolean("first_start1", true)) {

            prefs.edit().putBoolean("first_start1", false).apply();





            Intent i = new Intent(MainActivity.this, FirstStart.class);
            startActivity(i);

        }






        // Process bottom navigation buttons clicks
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
                       // fragment = new StatisticsFragment(itself);
                        break;
                }

                return loadHomeFragment(fragment, itemId);
            }
        });

}
    protected void onResume() {
        Toast.makeText(getApplicationContext(),"qwe",Toast.LENGTH_SHORT).show();
        super.onResume();
        DrawThread.counterforserv=prefs.getInt("countInt",0);

        if (prefs.getBoolean("first_start", true)) {

            prefs.edit().putBoolean("first_start", false).apply();



            Intent i = new Intent(MainActivity.this, FirstStart.class);
            startActivity(i);

        } else {
            //MapsFragment.nickname.setText(load2());
        }


       /* if (intprefs.getInt("firstrunInt1", 0) > 0) {
            Log.d("save","saveFirstSe");

            FirstSettings loadTap = load();
            DrawThread.LngFirstTapForDrawThread = loadTap.LngFirstTapForDrawThread;
            DrawThread.LatFirstTapForDrawThread = loadTap.LatFirstTapForDrawThread;
            DrawThread.LatSecondTapForDrawThread = loadTap.LatSecondTapForDrawThread;
            DrawThread.LngSecondTapForDrawThread = loadTap.LngSecondTapForDrawThread;
            DrawThread.xFirstTap = loadTap.firstTapxForgrid;
            DrawThread.yFirstTap = loadTap.firstTapyForgrid;
            DrawThread.xSecondTap = loadTap.secondTapxForgrid;
            DrawThread.ySecondTap = loadTap.secondTapyForgrid;
           // DrawOnMap.startCounterClickformap2=4;

            //MapsFragment.startCounterClickformap = 4;
        }
        countPrefs++;
        intprefs.edit().putInt("firstrunInt1", countPrefs).apply();*/


    }

    @Override
    protected void onPause() {
        super.onPause();
        save();
        prefs.edit().putInt("countInt",DrawThread.counterforserv).apply();
    }

    private boolean loadHomeFragment(Fragment fragment, int id) {
        if (fragment == null) return false;

        // Remember switching fragment
        currentHomeFragmentId = id;

        // Hide fab. If fragment needs it, it can request it
      //  fabView.hide();

        // Make transition between fragments
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
                DrawThread.xSecondTap, DrawThread.yFirstTap, DrawThread.ySecondTap,MapsFragment.currenZoom);

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
