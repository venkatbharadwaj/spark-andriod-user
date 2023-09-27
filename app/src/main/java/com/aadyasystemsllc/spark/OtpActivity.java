package com.aadyasystemsllc.spark;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aadyasystemsllc.spark.utilities.Utility;
import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    // string for storing our verification ID
    private String verificationId;

    TextView verify_but,txtResend;
    String mobilenumber,code;
    PinView txtPinEntry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        getIntentData();
        verify_but=findViewById(R.id.verify_but);
        txtResend=findViewById(R.id.txtResend);
        txtPinEntry=findViewById(R.id.txt_pin_entry);
        mAuth = FirebaseAuth.getInstance();
        verify_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtPinEntry.getText() != null) {
                    if (txtPinEntry.length() < 6) {
                        Toast.makeText(OtpActivity.this, "Please enter Valid OTP", Toast.LENGTH_SHORT).show();
                    } else {
                        // userDetails.setLoggedIn();
                       /* if(txtPinEntry.getText().toString().equals(code)) {
                         *//*   Intent intent = new Intent(OtpActivity.this, PatnerRegistrationActivity.class);
                            intent.putExtra("number", ph_num);
                            startActivity(intent);
                            finish();*//*


                            verifyCode(code);
                        }else{
                            Toast.makeText(OtpActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                        }*/
                        Intent intent = new Intent(OtpActivity.this, SignupActivity.class);
                        startActivity(intent);
                        finish();
                       // verifyCode(code);
                    }
                }else{
                    Toast.makeText(OtpActivity.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Utility.showLoadingDialog(OtpActivity.this, "Loading...", false);

                sendVerificationCode(mobilenumber);*/
            }
        });
    }

    private void sendVerificationCode(String number) {
        // this method is used for getting
        // OTP on user phone number.
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)            // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)           // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // callback method is called on Phone auth provider.
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // when we receive the OTP it
            // contains a unique id which
            // we are storing in our string
            // which we have already created.
            verificationId = s;
        }

        // this method is called when user
        // receive OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();

            // checking if the code
            // is null or not.
            if (code != null) {
                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.
                // edtOTP.setText(code);


                Toast.makeText(OtpActivity.this, "OTP sent Successfully...", Toast.LENGTH_SHORT).show();
                // after setting this code
                // to OTP edittext field we
                // are calling our verifycode method.

            }
        }

        // this method is called when firebase doesn't

        // sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.
            Toast.makeText(OtpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    // below method is use to verify code from Firebase.
    private void verifyCode(String code) {
        // below line is used for getting
        // credentials from our verification id and code.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // if the code is correct and the task is successful
                            // we are sending our user to new activity.
                            Intent i = new Intent(OtpActivity.this, HomeActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.
                            Toast.makeText(OtpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    private void getIntentData() {
        Intent intent = getIntent();
      //  code = intent.getStringExtra("otp");

        mobilenumber=intent.getStringExtra("number");

    }
}