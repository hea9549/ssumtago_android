package com.lovepago.ssumtago;

import android.app.Application;

import com.facebook.appevents.AppEventsLogger;
import com.lovepago.ssumtago.Dagger.AppModule;
import com.lovepago.ssumtago.Dagger.InterfaceBindModule;
import com.lovepago.ssumtago.Presentation.Activity.FacebookJoinActivity;
import com.lovepago.ssumtago.Presentation.Activity.JoinActivity;
import com.lovepago.ssumtago.Presentation.Activity.LobyActivity;
import com.lovepago.ssumtago.Presentation.Activity.LoginActivity;
import com.lovepago.ssumtago.Presentation.Activity.MainActivity;
import com.lovepago.ssumtago.Presentation.Activity.MyAccountActivity;
import com.lovepago.ssumtago.Presentation.Activity.SurveyActivity;
import com.lovepago.ssumtago.Service.FCM.STGFireBaseInstanceIdService;
import com.lovepago.ssumtago.Service.FCM.STGFirebaseMessagingService;
import com.tsengvn.typekit.Typekit;

import javax.inject.Singleton;

import dagger.Component;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class STGApplication extends Application {

    private static ApplicationComponent applicationComponent;
    public static int majorVersion = 1;
    public static int subVersion = 0;
    public static int minorVersion = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerSTGApplication_ApplicationComponent.builder().appModule(new AppModule(getApplicationContext())).build();
        Realm.init(getApplicationContext());
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
        AppEventsLogger.activateApp(this);
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NanumSquareR.otf"))
                .addBold(Typekit.createFromAsset(this, "fonts/NanumSquareB.otf"))
                .addCustom1(Typekit.createFromAsset(this, "fonts/NanumBarunGothic.otf"));
    }

    public static ApplicationComponent getComponent() {
        return applicationComponent;
    }

    @Singleton
    @Component(modules = {AppModule.class, InterfaceBindModule.class})
    public interface ApplicationComponent {
        void inject(MainActivity mainActivity);

        void inject(JoinActivity joinActivity);

        void inject(LoginActivity loginActivity);

        void inject(SurveyActivity surveyActivity);

        void inject(FacebookJoinActivity facebookJoinActivity);

        void inject(LobyActivity lobyActivity);

        void inject(STGFireBaseInstanceIdService stgFireBaseInstanceIdService);

        void inject(STGFirebaseMessagingService stgFirebaseMessagingService);

        void inject(MyAccountActivity myAccountActivity);
    }
}
