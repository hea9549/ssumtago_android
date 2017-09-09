package com.lovepago.ssumtago.Service.FCM;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.lovepago.ssumtago.Data.Model.PushData;
import com.lovepago.ssumtago.STGApplication;
import com.lovepago.ssumtago.Service.UserService;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * Created by ParkHaeSung on 2017-05-28.
 */

public class STGFireBaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "STGFireBaseIdService";
    @Inject
    UserService userService;
    @Override
    public void onCreate() {
        super.onCreate();
        STGApplication.getComponent().inject(this);
    }

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "Refreshed token: " + refreshedToken);
        userService.updateUserFCM(refreshedToken);
    }

    public static void sendRegistrationToServer(UserService userService) {
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "register to server token = " + refreshToken);

        userService.updateUserFCM(refreshToken).subscribe(success -> Log.e(TAG, "update User Fcm Token success")
        , error -> Log.e(TAG, "error in sendRegistrationFCM = " + error.toString()));
    }
}
