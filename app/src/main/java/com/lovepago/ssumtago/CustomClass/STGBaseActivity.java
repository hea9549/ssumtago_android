package com.lovepago.ssumtago.CustomClass;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.lovepago.ssumtago.Presentation.Presenter.BaseViewPresenter;
import com.lovepago.ssumtago.STGApplication;

/**
 * @since 2017-05-15
 * @implNote
 * do not override onCreate in sub class.
 * just use STGOnCreate
 * */
public abstract class STGBaseActivity extends AppCompatActivity implements BaseViewPresenter {
    private ProgressDialog progressDialog;
    private String TAG = "STGBaseActivity";

    public abstract void STGOnCreate(@Nullable Bundle savedInstanceState);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        STGOnCreate(savedInstanceState);
    }

    @Override
    public void makeDialog(String message) {
        if(progressDialog == null){
            Log.e(TAG,"in make Dialog, dialog is null");
            return;
        }
        if(progressDialog.isShowing()){
            Log.e(TAG,"dialog is already showing... cancel showing and show new one");
            progressDialog.dismiss();
        }
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void cancelDialog() {
        if(progressDialog == null)return;
        if(progressDialog.isShowing())progressDialog.dismiss();
    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateActivity(Class navigateClass) {
        Intent intent = new Intent(this,navigateClass);
        startActivity(intent);
        finish();
    }
}
