package com.socgen.saviour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences sh = getSharedPreferences("DETAILS", MODE_PRIVATE);
        String mobile = sh.getString("mobile", null);


        new Handler().postDelayed(() -> {
            if(mobile==null) {
                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
            } else{
                startActivity(new Intent(SplashScreen.this, HomeActivity.class));
            }
            finish();
        },1000);

    }
}