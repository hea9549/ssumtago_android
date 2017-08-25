package com.lovepago.ssumtago.Presentation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.lovepago.ssumtago.CustomClass.STGBaseActivity;
import com.lovepago.ssumtago.Domain.StringFormatChecker;
import com.lovepago.ssumtago.Presentation.Presenter.LoginActivityPresenter;
import com.lovepago.ssumtago.R;
import com.lovepago.ssumtago.STGApplication;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class LoginActivity extends STGBaseActivity implements LoginActivityPresenter.View {
    private static final String TAG = "LoginActivity";
    @Inject
    LoginActivityPresenter presenter;

    @BindView(R.id.edt_login_id)
    EditText edtId;
    @BindView(R.id.edt_login_pw)
    EditText edtPw;
    @BindView(R.id.cb_login_autologin)
    CheckBox cbAutoLogin;
    @BindView(R.id.btn_login_join)
    Button btnJoin;
    @BindView(R.id.btn_login_findPw)
    Button btnFindPw;
    @BindView(R.id.btn_login_login)
    Button btnLogin;
    @BindView(R.id.btn_login_facebook)
    Button btnFacebook;
    @BindView(R.id.tv_login_checkIdPw)
    TextView tv_checkIdPw;

    private CallbackManager callbackManager;

    @Override
    public void STGOnCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        STGApplication.getComponent().inject(this);
        presenter.setView(this);
        callbackManager = CallbackManager.Factory.create();
    }

    @OnTextChanged(R.id.edt_login_id)
    void onIdTextChange(CharSequence sequence) {
        if (StringFormatChecker.isValidId(sequence.toString()) && StringFormatChecker.isValidPassword(edtPw.getText().toString()))
            btnLogin.setEnabled(true);
        else
            btnLogin.setEnabled(false);
    }
    @OnTextChanged(R.id.edt_login_pw)
    void onPwTextChange(CharSequence sequence) {
        if (StringFormatChecker.isValidPassword(sequence.toString()) && StringFormatChecker.isValidId(edtId.getText().toString()))
            btnLogin.setEnabled(true);
        else
            btnLogin.setEnabled(false);
    }
    /**
     * view Interface Implementation :)
     * */


    /**
     * onClick Implementaion >-<
     */
    @OnClick(R.id.btn_login_join)
    void onJoinClick() {
        navigateActivity(JoinActivity.class);
    }

    @OnClick(R.id.btn_login_login)
    void onLoginClick() {
        presenter.onLoginClick(edtId.getText().toString(), edtPw.getText().toString(), "email");
    }

    @OnClick(R.id.btn_login_facebook)
    void onFacebookClick() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email"));
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
                Log.e(TAG, "facebook onError = " + error.toString());
            }
        });
    }

    @OnClick(R.id.btn_login_findPw)
    void onFindPwClick() {
        presenter.onFindPwClick();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void alertCheckIdPw(){
        tv_checkIdPw.setVisibility(View.VISIBLE);
        Observable.timer(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .take(1)
                .subscribe(tick->tv_checkIdPw.setVisibility(View.INVISIBLE)
                ,fail->Log.e(TAG,"fail in tick alertcheckIdwPw . fail ="+fail.toString()));
    }
}
