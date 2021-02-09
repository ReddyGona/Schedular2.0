package com.ReddyGona.schedular20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OtherActivity extends AppCompatActivity {

    BottomNavigationView bottom_other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        bottom_other=findViewById(R.id.bottom_nav_others);

        bottom_other.setSelectedItemId(R.id.OTHERS);

        bottom_other.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
                        return true;

                    case R.id.PROFILE:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }

        });
    }
}