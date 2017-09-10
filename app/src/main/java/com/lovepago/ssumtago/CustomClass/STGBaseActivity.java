package com.lovepago.ssumtago.CustomClass;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.lovepago.ssumtago.CustomClass.CustomView.STGLoadingDialog;
import com.lovepago.ssumtago.Presentation.Presenter.BaseViewPresenter;
import com.lovepago.ssumtago.STGApplication;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.Serializable;

/**
 * @since 2017-05-15
 * @implNote
 * do not override onCreate in sub class.
 * just use STGOnCreate
 * */
public abstract class STGBaseActivity extends AppCompatActivity implements BaseViewPresenter {
    private STGLoadingDialog stgLoadingDialog;
    private String TAG = "STGBaseActivity";

    public abstract void STGOnCreate(@Nullable Bundle savedInstanceState);

    @Deprecated
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stgLoadingDialog = new STGLoadingDialog(this);
        STGOnCreate(savedInstanceState);
        Tracker mTracker = ((STGApplication)getApplication()).getDefaultTracker();
        mTracker.setScreenName(getClass().getSimpleName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void makeDialog(String message) {
        if(stgLoadingDialog == null){
            Log.e(TAG,"in make Dialog, dialog is null");
            return;
        }
        if(stgLoadingDialog.isShowing()){
            Log.e(TAG,"dialog is already showing... cancel showing and show new one");
            stgLoadingDialog.dismiss();
        }
        stgLoadingDialog.show();
    }

    @Override
    public void cancelDialog() {
        if(stgLoadingDialog == null)return;
        if(stgLoadingDialog.isShowing())stgLoadingDialog.dismiss();
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

    @Override
    public void navigateActivity(Class navigateClass, Serializable... datas){
        Intent intent = new Intent(this,navigateClass);
        if (datas.length%2 == 1)throw new IllegalArgumentException("navigate params not match. please make pair...");
        for(int i = 0;i<datas.length;i+=2){
            intent.putExtra((String)datas[i],datas[i+1]);
        }
        startActivity(intent);
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    public void popActivity(Class popClass) {
        Intent intent = new Intent(this,popClass);
        startActivity(intent);
    }

    @Override
    public void popActivity(Class popClass, Serializable... datas){
        Intent intent = new Intent(this,popClass);
        if (datas.length%2 == 1)throw new IllegalArgumentException("popClass params not match. please make pair...");
        for(int i = 0;i<datas.length;i+=2){
            intent.putExtra((String)datas[i],datas[i+1]);
        }
        startActivity(intent);
    }
}
