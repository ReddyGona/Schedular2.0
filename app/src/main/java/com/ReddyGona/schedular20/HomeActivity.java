package com.ReddyGona.schedular20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    CardView card;
    SharedPreferences sharedPreferences;
    private boolean loginck;
    TextView name;
    private String h_name;

    BottomNavigationView bottom_home;
    RecyclerView timerecyclar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPreferences=getSharedPreferences("Users", MODE_PRIVATE);
        h_name=sharedPreferences.getString("p_name", "");
        name=findViewById(R.id.name);

        name.setText(h_name);
//        card.setBackgroundColor(Color.RED);

        bottom_home=findViewById(R.id.bottom_nav_home);
        timerecyclar=findViewById(R.id.timett_recyclar);

        bottom_home.setSelectedItemId(R.id.HOME);

        bottom_home.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.HOME:
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
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }

        });








    }
}