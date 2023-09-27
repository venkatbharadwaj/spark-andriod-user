package com.aadyasystemsllc.spark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setSplashScreen();
    }

    private void setSplashScreen() {
        Handler mSplashHandler = new Handler();
        Runnable action = new Runnable() {
            @Override
            public void run() {
                Intent home = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(home);
                /*if (!PopUtils.hasPermissions(SplashActivity.this, PERMISSIONS)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(PERMISSIONS, 1);
                    }
                } else {
                    if (UserDetails.getInstance(SplashActivity.this).getUserId().equals("")){
                        Intent home = new Intent(SplashActivity.this, SigninActivity.class);
                        startActivity(home);
                    }else {
                        Intent home = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(home);
                    }

                }*/
            }
        };
        mSplashHandler.postDelayed(action, 2000);
             /*  try {

                   Intent home = new Intent(SplashActivity.this, SigninActivity.class);
                   startActivity(home);
               }catch (Exception e){
                   Log.e("ERROR",""+e);
               }


            }
        }, 10000);*/
    }

}