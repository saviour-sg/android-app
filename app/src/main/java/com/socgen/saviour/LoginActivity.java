package com.socgen.saviour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        next = findViewById(R.id.next);

        next.setOnClickListener(v -> {
            String mobile = ((TextInputLayout)findViewById(R.id.phone)).getEditText().getText().toString();
            Intent myIntent = new Intent(LoginActivity.this, VerifyOTP.class);
            myIntent.putExtra("mobile",mobile);
            LoginActivity.this.startActivity(myIntent);
        });
    }
}