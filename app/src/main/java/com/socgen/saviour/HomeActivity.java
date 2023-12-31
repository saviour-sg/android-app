package com.socgen.saviour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {

    FirebaseDatabase db;
    LinearLayout requestBlood, viewRequests;
    String name,phone,bloodGroup,location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sh = getSharedPreferences("DETAILS", MODE_PRIVATE);
         name = sh.getString("name", null);
         phone = sh.getString("mobile", null);
         bloodGroup = sh.getString("bloodGroup", null);
         location = sh.getString("location", null);

         TextView textView = findViewById(R.id.welcome);
         if(name!=null) {
             textView.setText("Hello, " + name.toUpperCase());
         }

        requestBlood = findViewById(R.id.request_blood);
        viewRequests = findViewById(R.id.view_requests);

        requestBlood.setOnClickListener(v -> {
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.fadein);
            requestBlood.startAnimation(anim);
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                Intent myIntent = new Intent(HomeActivity.this, NewRequest.class);
                HomeActivity.this.startActivity(myIntent);
            }, 200);
        });

        viewRequests.setOnClickListener(v -> {
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.fadein);
            viewRequests.startAnimation(anim);
            Handler handler = new Handler();
            handler.postDelayed(() ->{
                Intent myIntent = new Intent(HomeActivity.this, ViewRequests.class);
                HomeActivity.this.startActivity(myIntent);
            }, 200);
        });

        db = FirebaseDatabase.getInstance();
        DatabaseReference root = db.getReference("");
        root.child("stats").get().addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                Stats stats = task.getResult().getValue(Stats.class);
                ((TextView)findViewById(R.id.new_requests)).setText(stats.getNewRequests());
                ((TextView)findViewById(R.id.units_donated)).setText(stats.getUnitsDonated());
                ((TextView)findViewById(R.id.active_donors)).setText(stats.getActiveDonors());
            }
            else {
                Toast.makeText(HomeActivity.this,"Error fetching stats!",Toast.LENGTH_LONG).show();
            }

        });
    }
}