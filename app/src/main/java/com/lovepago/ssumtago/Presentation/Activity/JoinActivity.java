package com.lovepago.ssumtago.Presentation.Activity;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.airbnb.lottie.LottieAnimationView;
import com.lovepago.ssumtago.CustomClass.STGBaseActivity;
import com.lovepago.ssumtago.Domain.StringFormatChecker;
import com.lovepago.ssumtago.Presentation.Presenter.JoinActivityPresenter;
import com.lovepago.ssumtago.R;
import com.lovepago.ssumtago.STGApplication;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Binds;

public class JoinActivity extends STGBaseActivity implements JoinActivityPresenter.View{
    @Inject
    JoinActivityPresenter presenter;

    @BindView(R.id.edt_join_eamil)
    EditText edt_email;
    @BindView(R.id.edt_join_nick)
    EditText edt_nick;
    @BindView(R.id.edt_join_pw)
    EditText edt_pw;
    @BindView(R.id.edt_join_pw2)
    EditText edt_pw2;
    @BindView(R.id.btn_join_submit)
    Button btn_register;
    @Override
    public void STGOnCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_join);
        ButterKnife.bind(this);
        STGApplication.getComponent().inject(this);
        initButtonAction();
        presenter.setView(this);
    }

    private void initButtonAction() {
        btn_register.setOnClickListener(v->presenter.register(edt_email.getText().toString(),edt_pw.getText().toString(),edt_nick.getText().toString(),"sex",1));
    }

}
