package com.lovepago.ssumtago.Presentation.Activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.lovepago.ssumtago.CustomClass.STGBaseActivity;
import com.lovepago.ssumtago.Presentation.Presenter.MainActivityPresenter;
import com.lovepago.ssumtago.Presentation.Presenter.MainActivityPresenterImpl;
import com.lovepago.ssumtago.R;
import com.lovepago.ssumtago.STGApplication;

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

    @Override
    public void STGOnCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ((STGApplication)getApplication()).getComponent().inject(this);
        presenter.setView(this);
        buttonEventSetting();
    }

    private void buttonEventSetting(){
        btn_login.setOnClickListener(v->presenter.loginClick(edt_id.getText().toString(),edt_pw.getText().toString()));
        btn_facebookLogin.setOnClickListener(v->presenter.facebookLoginClick());
        btn_kakaoLogin.setOnClickListener(v->presenter.kakaoLoginClick());
    }

}
