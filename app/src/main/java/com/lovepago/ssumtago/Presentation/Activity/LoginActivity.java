package com.lovepago.ssumtago.Presentation.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.lovepago.ssumtago.CustomClass.STGBaseActivity;
import com.lovepago.ssumtago.Domain.StringFormatChecker;
import com.lovepago.ssumtago.Presentation.Presenter.LoginActivityPresenter;
import com.lovepago.ssumtago.R;
import com.lovepago.ssumtago.STGApplication;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends STGBaseActivity {
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

    @Override
    public void STGOnCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        STGApplication.getComponent().inject(this);
        edtId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(StringFormatChecker.isValidEmail(s.toString()) && StringFormatChecker.isValidPassword(edtPw.getText().toString()))
                    btnLogin.setEnabled(true);
                else
                    btnLogin.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(StringFormatChecker.isValidPassword(s.toString()) && StringFormatChecker.isValidEmail(edtId.getText().toString()))
                    btnLogin.setEnabled(true);
                else
                    btnLogin.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnJoin.setOnClickListener(i->navigateActivity(JoinActivity.class));
    }

}
