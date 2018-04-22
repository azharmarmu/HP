package com.homeopathy.azhar.hp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.homeopathy.azhar.hp.R;
import com.homeopathy.azhar.hp.fragments.tabs.RecentConsultationFragment;
import com.homeopathy.azhar.hp.fragments.tabs.HomeFragment;
import com.homeopathy.azhar.hp.fragments.tabs.SettingsFragment;
import com.homeopathy.azhar.hp.utils.Constants;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isUserExists();
        setBottomNavigation();
    }

    private void setBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                selectedFragment = HomeFragment.newInstance();
                                break;
                            case R.id.action_consultation:
                                selectedFragment = RecentConsultationFragment.newInstance();
                                break;
                            case R.id.action_settings:
                                selectedFragment = SettingsFragment.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, HomeFragment.newInstance());
        transaction.commit();
    }

    private void isUserExists() {
        if (Constants.AUTH.getCurrentUser() == null) {
            indexMethod();
        }
    }

    private void indexMethod() {
        Log.v(TAG, "onAuthStateChanged ---> signed_out");
        Intent indexActivity = new Intent(MainActivity.this, IndexActivity.class);
        indexActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(indexActivity);
        finish();
    }
}
