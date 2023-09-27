package com.aadyasystemsllc.spark;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aadyasystemsllc.spark.utilities.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    TextView btnVerify,titleTx,btnSignup;
    EditText regsterdMbno;
    String ph_num, token;

    // variable for FirebaseAuth class
    private FirebaseAuth mAuth;
    // string for storing our verification ID
    private String verificationId;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnVerify = findViewById(R.id.btnVerify);
        btnSignup=findViewById(R.id.btnSignup);
        regsterdMbno=findViewById(R.id.regsterd_mbno);
        titleTx=findViewById(R.id.title_tx);
        //StartFirebaseLogin();

        // below line is for getting instance
        // of our FirebaseAuth.
        mAuth = FirebaseAuth.getInstance();

        titleTx.setText("Mobile Verification");
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validations()) {
                   // callSendOtp();
                    Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                    startActivity(intent);
                } else {
                    //   Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void callSendOtp() {
        Utility.showLoadingDialog(this, "Loading...", false);

        sendVerificationCode("+91 "+regsterdMbno.getText());
        Log.e("fnumber","number is"+regsterdMbno.getText());

      
    }

    private boolean validations() {
        boolean validate = false;
        String validNumber = "^[6-9][0-9]{9}$";
        ph_num = regsterdMbno.getText().toString();
        if (regsterdMbno.getText().toString().isEmpty()) {
            Utility.setSnackBarEnglish(this,regsterdMbno,"Please Enter Mobile Number");
//            Toast.makeText(this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
        }/* else if (ph_num.startsWith("0") || ph_num.startsWith("1") || ph_num.startsWith("2") || ph_num.startsWith("3") ||
            ph_num.startsWith("4") || ph_num.startsWith("5")) {
            Toast.makeText(this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
        }*/ else if (regsterdMbno.length() < 10) {
            Toast.makeText(this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
        } else if (!ph_num.matches(validNumber)) {
            Toast.makeText(this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
        } else {
            validate = true;
        }
        return validate;
    }

    /*private void signInWithCredential(PhoneAuthCredential credential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // if the code is correct and the task is successful
                            // we are sending our user to new activity.
                            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }*/


    private void sendVerificationCode(String number) {
        // this method is used for getting
        // OTP on user phone number.
        Log.e("number","number is" +number);
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
            Log.e("code","code is" +code);
            // checking if the code
            // is null or not.
            if (code != null) {
                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.
               // edtOTP.setText(code);
                Log.e("code null","code is" +code);

                Utility.hideLoadingDialog();
                Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                intent.putExtra("otp", code);
                intent.putExtra("number", "91"+regsterdMbno.getText().toString());
                startActivity(intent);
                Toast.makeText(LoginActivity.this, "OTP sent Successfully...", Toast.LENGTH_SHORT).show();
                // after setting this code
                // to OTP edittext field we
                // are calling our verifycode method.

            }
        }

        // this method is called when firebase doesn't

        // sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            Utility.hideLoadingDialog();
            // displaying error message with firebase exception.
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("eroor",""+e.getMessage());
        }
    };/*

            mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            Utility.hideLoadingDialog();
            Toast.makeText(LoginActivity.this,"verification completed",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Utility.hideLoadingDialog();
            Toast.makeText(LoginActivity.this,"verification fialed",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
            Toast.makeText(LoginActivity.this,"Code sent",Toast.LENGTH_SHORT).show();
            Utility.hideLoadingDialog();
            Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
            intent.putExtra("number", "91"+regsterdMbno.getText().toString());
            startActivity(intent);
        }
    };*/

    /*private void StartFirebaseLogin() {

        mAuth = FirebaseAuth.getInstance();
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(LoginActivity.this,"verification completed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Utility.hideLoadingDialog();

                Toast.makeText(LoginActivity.this,"verification fialed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationId = s;
                Utility.hideLoadingDialog();

                Toast.makeText(LoginActivity.this,"Code sent",Toast.LENGTH_SHORT).show();
            }
        };
    }*/
}