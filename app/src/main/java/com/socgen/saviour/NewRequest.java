package com.socgen.saviour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class NewRequest extends AppCompatActivity {

    Button requestBtn;
    FirebaseDatabase db;
    String name,phone,bloodGroup,location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_request);

        SharedPreferences sh = getSharedPreferences("DETAILS", MODE_PRIVATE);
        name = sh.getString("name", null);
        phone = sh.getString("mobile", null);
        bloodGroup = sh.getString("bloodGroup", null);
        location = sh.getString("location", null);
        db = FirebaseDatabase.getInstance();
        requestBtn = findViewById(R.id.request);
        requestBtn.setOnClickListener(v -> {

            String name = ((TextInputLayout)findViewById(R.id.patientName)).getEditText().getText().toString();
            String hospital = ((TextInputLayout)findViewById(R.id.hospital)).getEditText().getText().toString();
            String address = ((TextInputLayout)findViewById(R.id.address)).getEditText().getText().toString();
            String city = ((TextInputLayout)findViewById(R.id.city)).getEditText().getText().toString();
            String bloodGroup = ((TextInputLayout)findViewById(R.id.blood_group)).getEditText().getText().toString();
            String units = ((TextInputLayout)findViewById(R.id.units)).getEditText().getText().toString();
            String relationship = ((TextInputLayout)findViewById(R.id.relationship)).getEditText().getText().toString();

            Request request = new Request();
            request.setPatientName(name);
            request.setHospital(hospital);
            request.setAddress(address);
            request.setCity(city);
            request.setBloodGroup(bloodGroup);
            request.setUnits(units);
            request.setRelationship(relationship);
            request.setTimestamp(System.currentTimeMillis());
            request.setRequesterNumber(phone);

            DatabaseReference requestdb = db.getReference("requests");
            String requestId = requestdb.push().getKey();

            requestdb.child(requestId).setValue(request).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(NewRequest.this,"Request posted successfully", Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(NewRequest.this, HomeActivity.class);
                    NewRequest.this.startActivity(myIntent);
                }else{
                    Toast.makeText(NewRequest.this,"Sorry! Try Again.",Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}