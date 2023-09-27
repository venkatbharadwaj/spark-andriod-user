package com.aadyasystemsllc.spark.utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.aadyasystemsllc.spark.R;
import com.aadyasystemsllc.spark.customviews.SnackBar;

import androidx.appcompat.app.AppCompatActivity;

public class Utility {

    static ProgressDialog progressDialog;

    public static void setSnackBarEnglish(AppCompatActivity parent, View mView, String message) {
        SnackBar snackBarIconTitle = new SnackBar();
        snackBarIconTitle.view(mView)
                .text(message, "OK")
                .textColors(Color.WHITE, Color.WHITE)
                .backgroundColor(parent.getResources().getColor(R.color.black))
                .duration(SnackBar.SnackBarDuration.LONG);
        snackBarIconTitle.show();
    }

    public static void hideLoadingDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
            progressDialog = null;
        } catch (Exception e) {
            progressDialog = null;
        }
    }

    public static void showLoadingDialog(Context context, final String title, final boolean isCancelable) {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                return;
            }
            progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(title);
            progressDialog.setCancelable(isCancelable);
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
