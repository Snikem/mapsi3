package com.example.mapsi3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = (BottomNavigationView)findViewById(R.id.bootomNavig);
        prefs = getSharedPreferences("first_start1", MODE_PRIVATE);
        intprefs = getSharedPreferences("firstrunInt", MODE_PRIVATE);

        intprefs.edit().putInt("firstrunInt", 0).apply();

        loadHomeFragment(new MapsFragment(), R.id.home_nav_map);





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
        int countPrefs = intprefs.getInt("firstrunInt", 0);
       if (prefs.getBoolean("first_start1", true)) {

            prefs.edit().putBoolean("first_start1", false).apply();
            countPrefs--;

            intprefs.edit().putInt("firstrunInt", countPrefs).apply();
            countPrefs = intprefs.getInt("firstrunInt", 0);


            Intent i = new Intent(MainActivity.this, FirstStart.class);
            startActivity(i);

       } else {
         //   MapsFragment.nickname.setText(load2());
        }
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

}
