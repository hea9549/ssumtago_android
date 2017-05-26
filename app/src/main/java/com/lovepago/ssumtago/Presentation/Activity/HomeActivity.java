package com.lovepago.ssumtago.Presentation.Activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lovepago.ssumtago.CustomClass.STGBaseActivity;
import com.lovepago.ssumtago.Presentation.Presenter.HomeActivityPresenter;
import com.lovepago.ssumtago.R;
import com.lovepago.ssumtago.STGApplication;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class HomeActivity extends STGBaseActivity implements HomeActivityPresenter.View{
    @Inject
    HomeActivityPresenter presenter;

    @Override
    public void STGOnCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        ((STGApplication)getApplication()).getComponent().inject(this);
        presenter.setView(this);
        presenter.init();
    }
}
