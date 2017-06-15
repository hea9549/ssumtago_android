package com.lovepago.ssumtago;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.lovepago.ssumtago.Dagger.AppModule;
import com.lovepago.ssumtago.Dagger.InterfaceBindModule;
import com.lovepago.ssumtago.Presentation.Activity.HomeActivity;
import com.lovepago.ssumtago.Presentation.Activity.JoinActivity;
import com.lovepago.ssumtago.Presentation.Activity.MainActivity;
import com.lovepago.ssumtago.Service.FCM.STGFireBaseInstanceIdService;
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
        //FacebookSdk.sdkInitialize(getApplicationContext()); 아랫께 알아서부른다는데? 일단테스트 ㄱㄱ
        AppEventsLogger.activateApp(this);
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
        void inject(STGFireBaseInstanceIdService stgFireBaseInstanceIdService);

        void inject(JoinActivity joinActivity);
    }
}
