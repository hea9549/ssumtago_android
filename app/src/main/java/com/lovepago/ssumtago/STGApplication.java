package com.lovepago.ssumtago;

import android.app.Application;

import com.lovepago.ssumtago.Dagger.AppModule;
import com.lovepago.ssumtago.Dagger.PresenterModule;
import com.lovepago.ssumtago.Presentation.Activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

public class STGApplication extends Application {

    private static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerSTGApplication_ApplicationComponent.builder().appModule(new AppModule(getApplicationContext())).build();
    }

    public ApplicationComponent getComponent() {
        return applicationComponent;
    }

    @Singleton
    @Component(modules = {AppModule.class, PresenterModule.class})
    public interface ApplicationComponent {
        void inject(MainActivity mainActivity);
    }
}
