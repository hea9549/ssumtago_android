package com.lovepago.ssumtago.Service.FCM;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.lovepago.ssumtago.CustomClass.CustomView.STGResultDialog;
import com.lovepago.ssumtago.Data.Model.PredictReport;
import com.lovepago.ssumtago.Presentation.Activity.MainActivity;
import com.lovepago.ssumtago.Presentation.Activity.ResultDialogActivity;
import com.lovepago.ssumtago.R;
import com.lovepago.ssumtago.STGApplication;
import com.lovepago.ssumtago.Service.UserService;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Iterator;

import javax.inject.Inject;

import io.realm.Realm;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by ParkHaeSung on 2017-05-28.
 */

public class STGFirebaseMessagingService extends FirebaseMessagingService {
    private String TAG = "FCMMessagingService";

    @Inject
    UserService userService;
    public STGFirebaseMessagingService(){
        super();
        STGApplication.getComponent().inject(this);
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Iterator keySet = remoteMessage.getData().keySet().iterator();
        while (keySet.hasNext()){
            String key = (String)keySet.next();
            Log.e(TAG,"key : "+key +" data = "+remoteMessage.getData().get(key));
        }
        Log.e(TAG,"message = "+remoteMessage.getNotification().getBody());
        Observable.just("makeResultDialog")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s->{
                    for (PredictReport predictReport : userService.getUser().getPredictReports()){
                        if (predictReport.getId().equals(remoteMessage.getData().get("reportId"))){
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            try {
                                JSONArray jsonArray = new JSONArray(remoteMessage.getData().get("result"));
                                predictReport.getResult().get(0).setVal(Double.valueOf(jsonArray.get(0).toString()));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e(TAG,"아 존나 제이슨에러임 ㅠ");
                            }

                            userService.alertPredictReportChange();
                            realm.commitTransaction();
                        }
                    }

                    Intent intent = new Intent(getApplicationContext(), ResultDialogActivity.class);
                    intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK |  Intent.FLAG_ACTIVITY_CLEAR_TOP ) ;
                    startActivity(intent);
                });

    }

}
