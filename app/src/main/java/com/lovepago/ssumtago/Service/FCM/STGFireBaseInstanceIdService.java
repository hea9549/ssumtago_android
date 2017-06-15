package com.lovepago.ssumtago.Service.FCM;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.lovepago.ssumtago.STGApplication;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * Created by ParkHaeSung on 2017-05-28.
 */

public class STGFireBaseInstanceIdService extends FirebaseInstanceIdService {
    @Inject
    Retrofit retrofit;

    @Override
    public void onCreate() {
        ((STGApplication)getApplication()).getComponent().inject(this);
        super.onCreate();
    }

    @Override
    public void onTokenRefresh() {
        String refreshToekn = FirebaseInstanceId.getInstance().getToken();
        Log.e("FireBaseIdService","token = "+refreshToekn);

    }
}
