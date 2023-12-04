package com.socgen.saviour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

public class DonorAcceptance extends AppCompatActivity {

    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_acceptance);

        submit = findViewById(R.id.submit);

        submit.setOnClickListener(v -> {
            Boolean checkBox1 = ((CheckBox) findViewById(R.id.checkbox1)).isChecked();
            Boolean checkBox2 = ((CheckBox) findViewById(R.id.checkbox2)).isChecked();
            Boolean checkBox3 = ((CheckBox) findViewById(R.id.checkbox3)).isChecked();
            Boolean checkBox4 = ((CheckBox) findViewById(R.id.checkbox4)).isChecked();
            Boolean checkBox5 = ((CheckBox) findViewById(R.id.checkbox5)).isChecked();
            Boolean checkBox6 = ((CheckBox) findViewById(R.id.checkbox6)).isChecked();
            Boolean checkBox7 = ((CheckBox) findViewById(R.id.checkbox7)).isChecked();
            Boolean checkBox8 = ((CheckBox) findViewById(R.id.checkbox8)).isChecked();

            if (checkBox1 || checkBox2 || checkBox3 || checkBox4 | checkBox5 || checkBox6
                    || checkBox7 || checkBox8) {
                Intent myIntent = new Intent(DonorAcceptance.this, ThanksFailure.class);
//                    myIntent.putExtra("key", value); //Optional parameters
                DonorAcceptance.this.startActivity(myIntent);
            } else {
                Intent myIntent = new Intent(DonorAcceptance.this, ThanksSuccess.class);
                DonorAcceptance.this.startActivity(myIntent);
            }
        });
    }
}