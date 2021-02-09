package com.ReddyGona.schedular20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {
    BottomNavigationView bottom_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        bottom_profile=findViewById(R.id.bottom_nav_profile);

        bottom_profile.setSelectedItemId(R.id.PROFILE);

        bottom_profile.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.HOME:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.CLASSES:
                        startActivity(new Intent(getApplicationContext(), ClassesActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.OTHERS:
                        startActivity(new Intent(getApplicationContext(), OtherActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.PROFILE:
                        return true;

                }
                return false;
            }

        });
    }
}