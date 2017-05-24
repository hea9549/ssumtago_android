package com.lovepago.ssumtago.Presentation.Activity;

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

    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.btn_getExercise)
    Button btn_getExercise;
    @BindView(R.id.edt_num)EditText edt_num;

    private String[] strArr = {"해성","준범"};

    @Override
    public void STGOnCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ((STGApplication)getApplication()).getComponent().inject(this);
        presenter.logRetrofit();
    }

}
