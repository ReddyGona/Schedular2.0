package com.ReddyGona.schedular20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class SplashScreenActivity extends AppCompatActivity {

    Animation animation;
    ImageView logo;
    private static int SPLASH=1000;

    SharedPreferences sharedPreferences;
    private boolean loginV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logo=findViewById(R.id.logo_1);
        animation= AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        logo.setAnimation(animation);

        sharedPreferences=getSharedPreferences("Users",MODE_PRIVATE);
        loginV=sharedPreferences.getBoolean("loginS",false);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                 if (loginV==true){
                    Intent intent=new Intent(SplashScreenActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, SPLASH);

    }
}