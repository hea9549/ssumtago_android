package com.lovepago.ssumtago;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.lovepago.ssumtago.Dagger.AppModule;
import com.lovepago.ssumtago.Dagger.InterfaceBindModule;
import com.lovepago.ssumtago.Presentation.Activity.HomeActivity;
import com.lovepago.ssumtago.Presentation.Activity.JoinActivity;
import com.lovepago.ssumtago.Presentation.Activity.LoginActivity;
import com.lovepago.ssumtago.Presentation.Activity.MainActivity;
import com.lovepago.ssumtago.Retrofit.JWTAddInterceptor;
import com.lovepago.ssumtago.Service.FCM.STGFireBaseInstanceIdService;
import com.lovepago.ssumtago.Service.LoginService.NormalLoginService;
import com.lovepago.ssumtago.Service.SurveyServiceImpl;
import com.tsengvn.typekit.Typekit;

import javax.inject.Singleton;

import dagger.Component;
import io.realm.Realm;

public class STGApplication extends Application {

    private static ApplicationComponent applicationComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerSTGApplication_ApplicationComponent.builder().appModule(new AppModule(getApplicationContext())).build();
        AppEventsLogger.activateApp(this);
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this,"fonts/NanumSquareR.otf"))
                .addBold(Typekit.createFromAsset(this,"fonts/NanumSquareB.otf"));
        Realm.init(this);
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
        void inject(JWTAddInterceptor jwtAddInterceptor);
        void inject(LoginActivity loginActivity);
    }
}
