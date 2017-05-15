package com.lovepago.ssumtago.Activity;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lovepago.ssumtago.CustomClass.STGBaseActivity;
import com.lovepago.ssumtago.Presenter.MainActivityPresenter;
import com.lovepago.ssumtago.Presenter.MainActivityPresenterImpl;
import com.lovepago.ssumtago.R;
import com.lovepago.ssumtago.Retrofit.Api;
import com.lovepago.ssumtago.Retrofit.STGRetrofit;

import org.reactivestreams.Subscriber;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends STGBaseActivity implements MainActivityPresenter.View{
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.btn_getExercise)
    Button btn_getExercise;
    @BindView(R.id.edt_num)EditText edt_num;

    private String[] strArr = {"해성","준범"};
    private MainActivityPresenter presenter = new MainActivityPresenterImpl();

    @Override
    public void STGOnCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter.setView(this);
        presenter.alert();
    }

}
