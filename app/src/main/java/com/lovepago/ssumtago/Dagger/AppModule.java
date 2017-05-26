package com.lovepago.ssumtago.Dagger;


import android.content.Context;

import com.lovepago.ssumtago.Data.RealmDBService;
import com.lovepago.ssumtago.Data.RealmDBServiceImpl;
import com.lovepago.ssumtago.Retrofit.STGRetrofit;
import com.lovepago.ssumtago.Service.SurveyService;

import javax.inject.Singleton;

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

    @Singleton
    @Provides
    RealmDBService provideRealmDBService(){
        return new RealmDBServiceImpl(context);
    }

}
