package com.aadyasystemsllc.spark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aadyasystemsllc.spark.utilities.Utility;

public class SignupActivity extends AppCompatActivity {
TextView txtDone,titleTx;
EditText edtMobilenumber,edtUsername,edtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        txtDone=findViewById(R.id.txtDone);
        titleTx=findViewById(R.id.title_tx);
        titleTx.setText("Register");
        edtMobilenumber=findViewById(R.id.edtMobilenumber);
        edtEmail=findViewById(R.id.edtEmail);
        edtUsername=findViewById(R.id.edtUsername);
        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent done=new Intent(SignupActivity.this,HomeActivity.class);
                startActivity(done);*/
                if (validations()) {
                    // callSendOtp();
                    Intent done=new Intent(SignupActivity.this,HomeActivity.class);
                    startActivity(done);
                    finish();
                } else {
                    //   Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean validations() {
        boolean validate = false;
        String validNumber = "^[6-9][0-9]{9}$";
      //  ph_num = regsterdMbno.getText().toString();
        if (edtUsername.getText().toString().isEmpty()) {
            Utility.setSnackBarEnglish(this,edtUsername,"Please Enter Name");
        }else if (edtEmail.getText().toString().isEmpty()) {
            //  Toast.makeText(this, "Please Enter Email Id", Toast.LENGTH_SHORT).show();
            Utility.setSnackBarEnglish(this,edtEmail,"Please Enter Email Id");

        } else if (!isValidEmail(edtEmail.getText().toString().trim())) {
            //   Toast.makeText(this, "Please Enter Valid Email Id", Toast.LENGTH_SHORT).show();
            Utility.setSnackBarEnglish(this,edtEmail,"Please Enter Valid Email Id");

        }
       else if (edtMobilenumber.getText().toString().isEmpty()) {
            Utility.setSnackBarEnglish(this,edtMobilenumber,"Please Enter Mobile Number");
//            Toast.makeText(this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
        } /*else if (edtMobilenumber.getText().toString().startsWith("0") || edtMobilenumber.getText().toString().startsWith("1") || edtMobilenumber.getText().toString().startsWith("2") || edtMobilenumber.getText().toString().startsWith("3") ||
                edtMobilenumber.getText().toString().startsWith("4") || edtMobilenumber.getText().toString().startsWith("5")) {
            Toast.makeText(this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
        }*/ else if (edtMobilenumber.length() < 10) {
        //    Toast.makeText(this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
            Utility.setSnackBarEnglish(this,edtMobilenumber,"Please Enter Valid Mobile Number");
        } /*else if (!ph_num.matches(validNumber)) {
            Toast.makeText(this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
        }*/ else {
            validate = true;
        }
        return validate;
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


}