package com.ReddyGona.schedular20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {
    BottomNavigationView bottom_profile;
    CardView changepasswd, logout;
    SharedPreferences sharedPreferences;
    String name_p, email_p, mobile_p, regno_p, dept_p, year_p, batch_p;
    TextView p_name, p_email, p_mobile, p_regno, p_dept, p_year, p_batch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sharedPreferences=getSharedPreferences("Users", MODE_PRIVATE);

        boolean logins =sharedPreferences.getBoolean("loginS", false);

        if (logins==true) {

            name_p = sharedPreferences.getString("p_name", "");
            email_p = sharedPreferences.getString("p_email", "");
            mobile_p = sharedPreferences.getString("p_mobile", "");
            regno_p = sharedPreferences.getString("p_regno", "");
            dept_p = sharedPreferences.getString("p_dept", "");
            year_p = sharedPreferences.getString("p_year", "");
            batch_p = sharedPreferences.getString("p_batch", "");
        }

        //declaring ids


        bottom_profile=findViewById(R.id.bottom_nav_profile);

        bottom_profile.setSelectedItemId(R.id.PROFILE);

        changepasswd=findViewById(R.id.profile_password);
        logout=findViewById(R.id.logout_card);

        p_name=findViewById(R.id.profile_name);
        p_regno=findViewById(R.id.profile_regno);
        p_email=findViewById(R.id.profile_email);
        p_mobile=findViewById(R.id.profile_mobile);
        p_dept=findViewById(R.id.profile_dept);
        p_year=findViewById(R.id.profile_year);
        p_batch=findViewById(R.id.profile_batch);

        p_name.setText(name_p);
        p_regno.setText(regno_p);
        p_email.setText(email_p);
        p_mobile.setText(mobile_p);
        p_dept.setText(dept_p);
        p_year.setText(year_p);
        p_batch.setText(batch_p);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences login = getSharedPreferences("Users", MODE_PRIVATE);
                SharedPreferences.Editor editor =login.edit();
                editor.putBoolean("loginS", false);
                editor.clear();
                editor.commit();

                Intent intent=new Intent(ProfileActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            }
        });

        changepasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });




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