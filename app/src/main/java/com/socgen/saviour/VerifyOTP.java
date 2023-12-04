package com.socgen.saviour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyOTP extends AppCompatActivity {

    long timeout = 60L;
    FirebaseAuth mAuth;
    String verificationcode;
    PhoneAuthProvider.ForceResendingToken ResendingToken;
    String phoneNumber;

    Button verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        verify = findViewById(R.id.verify);

        verify.setOnClickListener(v -> {
            verify(((TextInputLayout)findViewById(R.id.otp)).getEditText().getText().toString());
        });
        mAuth = FirebaseAuth.getInstance();
        phoneNumber = getIntent().getStringExtra("mobile");

        sendOtp("+91"+phoneNumber);
    }

    void sendOtp(String phoneNumber)
    {
        PhoneAuthOptions.Builder builder =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(timeout, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                Toast.makeText(VerifyOTP.this, "OTP verified", Toast.LENGTH_SHORT).show();
                                String code = phoneAuthCredential.getSmsCode();
                                if(code!=null)
                                {
//                                    otpval.setText(code);
                                    verify(code);
                                }
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(VerifyOTP.this, "OTP VerificationFailed", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verificationcode = s;
                                ResendingToken = forceResendingToken;
                                Toast.makeText(VerifyOTP.this,"OTP SENT",Toast.LENGTH_SHORT).show();
                            }
                        });

//        if(!resend)
//        {
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
//        }else{
//            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(ResendingToken).build());
//        }
    }

    void verify(String otp)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationcode,otp);
        mAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                Toast.makeText(VerifyOTP.this, "LOGIN SUCCESS", Toast.LENGTH_SHORT).show();
                Intent i;
                if(true){
                    i = new Intent(VerifyOTP.this, CreateAccount.class);
                    SharedPreferences sharedPreferences = getSharedPreferences("DETAILS",MODE_PRIVATE);
                    SharedPreferences.Editor sp = sharedPreferences.edit();
                    sp.putString("mobile", phoneNumber);
                    sp.apply();
                }else{
                    i = new Intent(VerifyOTP.this, LoginActivity.class);
                }
                i.putExtra("mobile",phoneNumber);
                startActivity(i);
                finish();
            }
            else
            {
                Toast.makeText(VerifyOTP.this, "LOGIN FAILED", Toast.LENGTH_SHORT).show();
            }
        });
    }

}