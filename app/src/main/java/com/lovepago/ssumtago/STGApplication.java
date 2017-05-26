package com.lovepago.ssumtago;

import android.app.Application;

import com.lovepago.ssumtago.Dagger.AppModule;
import com.lovepago.ssumtago.Dagger.InterfaceBindModule;
import com.lovepago.ssumtago.Presentation.Activity.HomeActivity;
import com.lovepago.ssumtago.Presentation.Activity.MainActivity;
import com.lovepago.ssumtago.Service.LoginService.NormalLoginService;
import com.lovepago.ssumtago.Service.SurveyServiceImpl;

import javax.inject.Singleton;

import dagger.Component;

public class STGApplication extends Application {

    private static ApplicationComponent applicationComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerSTGApplication_ApplicationComponent.builder().appModule(new AppModule(getApplicationContext())).build();
    }

    public static ApplicationComponent getComponent() {
        return applicationComponent;
    }

    @Singleton
    @Component(modules = {AppModule.class, InterfaceBindModule.class})
    public interface ApplicationComponent {
        void inject(MainActivity mainActivity);
        void inject(HomeActivity homeActivity);
        void inject(NormalLoginService normalLoginService);
    }
}
