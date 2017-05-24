package com.lovepago.ssumtago.Presentation.Presenter;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Qualifier;
import javax.inject.Singleton;

import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by ParkHaeSung on 2017-05-15.
 */

public class MainActivityPresenterImpl implements MainActivityPresenter {

    private Retrofit retrofit;
    private View view;


    @Inject
    public MainActivityPresenterImpl(Retrofit retrofit){
        this.retrofit = retrofit;
        Log.e("프리젠터 객체생성","배애액");
    }

    @Singleton
    @Named("MainPresenter")
    public MainActivityPresenterImpl getPresenterImpl(){
        return this;
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void alert(){
        view.makeToast("base View Presenter 테스트");
    }

    @Override
    public void logRetrofit(){
        Log.e("과연 레트로핏은?","null = "+retrofit+"  @AND , = "+retrofit.toString());
    }
}
