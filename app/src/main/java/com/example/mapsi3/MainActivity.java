package com.example.mapsi3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navigation;
    int currentHomeFragmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = (BottomNavigationView)findViewById(R.id.bootomNavig);

        loadHomeFragment(new MapsFragment(), R.id.home_nav_list);

        // Process bottom navigation buttons clicks
        final MainActivity itself = this;
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                int itemId = item.getItemId();

                if (currentHomeFragmentId == itemId) return false;

                switch (itemId) {
                    case R.id.home_nav_first:
                        fragment = new MapsFragment();
                        break;

                    case R.id.home_nav_list:
                        //fragment = new WhitelistFragment(itself);
                        break;

                    case R.id.home_nav_stats:
                       // fragment = new StatisticsFragment(itself);
                        break;
                }

                return loadHomeFragment(fragment, itemId);
            }
        });
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

}
