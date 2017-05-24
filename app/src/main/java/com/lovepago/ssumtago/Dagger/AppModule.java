package com.lovepago.ssumtago.Dagger;


import android.content.Context;

import com.lovepago.ssumtago.Presentation.Presenter.MainActivityPresenter;
import com.lovepago.ssumtago.Presentation.Presenter.MainActivityPresenterImpl;
import com.lovepago.ssumtago.Retrofit.STGRetrofit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by ParkHaeSung on 2017-05-21.
 */

@Module
public class AppModule {
    private Context context;

    public AppModule(Context context){
        this.context = context;
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(){
        return STGRetrofit.getInstance();
    }


}
