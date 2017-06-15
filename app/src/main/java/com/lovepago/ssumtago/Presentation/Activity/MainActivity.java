package com.lovepago.ssumtago.Presentation.Activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.lovepago.ssumtago.CustomClass.STGBaseActivity;
import com.lovepago.ssumtago.Presentation.Presenter.MainActivityPresenter;
import com.lovepago.ssumtago.Presentation.Presenter.MainActivityPresenterImpl;
import com.lovepago.ssumtago.R;
import com.lovepago.ssumtago.STGApplication;

import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Qualifier;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends STGBaseActivity implements MainActivityPresenter.View{
    @Inject
    MainActivityPresenter presenter;

    @BindView(R.id.btn_main_login)
    Button btn_login;
    @BindView(R.id.btn_main_facebookLogin)
    Button btn_facebookLogin;
    @BindView(R.id.btn_main_kakaoLogin)
    Button btn_kakaoLogin;
    @BindView(R.id.edt_main_id)
    EditText edt_id;
    @BindView(R.id.edt_main_pw)
    EditText edt_pw;
    @BindView(R.id.btn_main_getFcm)
    Button btn_getFcm;
    @BindView(R.id.edt_main_fcmToken)
    EditText edt_fcmToken;
    @BindView(R.id.btn_main_gotoDesigin)
    Button btn_gotoDesgin;

    private CallbackManager callbackManager;
    private String TAG = "MainActivity";
    @Override
    public void STGOnCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        callbackManager = CallbackManager.Factory.create();
        ButterKnife.bind(this);
        STGApplication.getComponent().inject(this);
        presenter.setView(this);
        buttonEventSetting();
        Log.e("FCMTAG:",""+FirebaseInstanceId.getInstance().getToken());
    }


    private void buttonEventSetting(){
        btn_login.setOnClickListener(v->presenter.loginClick(edt_id.getText().toString(),edt_pw.getText().toString()));
        btn_facebookLogin.setOnClickListener(v->{
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email","public_profile"));
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    presenter.onFacebookLoginSuccess(loginResult);
                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {

                }
            });
        });
        btn_kakaoLogin.setOnClickListener(v->navigateActivity(TestViewActivity.class));
        btn_gotoDesgin.setOnClickListener(v->navigateActivity(JoinActivity.class));
        btn_getFcm.setOnClickListener(v->{
            if(FirebaseInstanceId.getInstance().getToken() == null)
                Toast.makeText(this, "토큰생성아직안댐", Toast.LENGTH_SHORT).show();
            else
                edt_fcmToken.setText(FirebaseInstanceId.getInstance().getToken());
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
}
