package com.lovepago.ssumtago.Dagger;


import android.content.Context;

import com.lovepago.ssumtago.Retrofit.STGRetrofit;
import com.lovepago.ssumtago.Service.SurveyService;
import com.lovepago.ssumtago.Service.SurveyServiceImpl;
import com.lovepago.ssumtago.Service.UserService;
import com.lovepago.ssumtago.Service.UserServiceImpl;

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
    UserService provideUserService(){
        return new UserServiceImpl();
    }

    @Singleton
    @Provides
    SurveyService provideSurveyService(UserService userService){
        return new SurveyServiceImpl(userService);
    }

    @Provides
    Context provideContext(){
        return context;
    }
}
