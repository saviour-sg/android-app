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

public class CreateAccount extends AppCompatActivity {

    Button register;
    FirebaseDatabase db;

    String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        SharedPreferences sh = getSharedPreferences("DETAILS", MODE_PRIVATE);
        phone = sh.getString("mobile", null);
        if(phone!=null){
            ((TextInputLayout)findViewById(R.id.mobile)).getEditText().setText(phone);
        }

        db = FirebaseDatabase.getInstance();
        register = findViewById(R.id.register);
        register.setOnClickListener(v -> {
            String name = ((TextInputLayout)findViewById(R.id.name)).getEditText().getText().toString();
            String empId = ((TextInputLayout)findViewById(R.id.empId)).getEditText().getText().toString();
            String email = ((TextInputLayout)findViewById(R.id.email)).getEditText().getText().toString();
            String bloodGroup = ((TextInputLayout)findViewById(R.id.blood_group)).getEditText().getText().toString();
            String baseLocation = ((TextInputLayout)findViewById(R.id.base_location)).getEditText().getText().toString();

            User user = new User(phone,name,empId,email,bloodGroup,baseLocation);
            DatabaseReference userdb = db.getReference("users");
//            String uid = userdb.push().getKey();

            userdb.child(user.getPhone()).setValue(user).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(CreateAccount.this,"Registration Successful", Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(CreateAccount.this, HomeActivity.class);

                    SharedPreferences sharedPreferences = getSharedPreferences("DETAILS",MODE_PRIVATE);
                    SharedPreferences.Editor sp = sharedPreferences.edit();
                    sp.putString("name", name);
                    sp.putString("bloodGroup", bloodGroup);
                    sp.putString("location", baseLocation);
                    sp.apply();

                    CreateAccount.this.startActivity(myIntent);
                }else{
                    Toast.makeText(CreateAccount.this,"Sorry! Try Again!",Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}