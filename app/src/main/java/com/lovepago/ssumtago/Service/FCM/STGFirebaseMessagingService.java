package com.lovepago.ssumtago.Service.FCM;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.lovepago.ssumtago.CustomClass.CustomView.STGResultDialog;
import com.lovepago.ssumtago.Data.Model.PredictReport;
import com.lovepago.ssumtago.Data.Model.PushMessageBody;
import com.lovepago.ssumtago.Data.Model.ReportResultBody;
import com.lovepago.ssumtago.Presentation.Activity.LoginActivity;
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

    public STGFirebaseMessagingService() {
        super();
        STGApplication.getComponent().inject(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Iterator keySet = remoteMessage.getData().keySet().iterator();
        while (keySet.hasNext()) {
            String key = (String) keySet.next();
            Log.e(TAG, "key : " + key + " data = " + remoteMessage.getData().get(key));
        }

        switch (remoteMessage.getData().get("code")) {
            case "100": // 메세지
                makeNotification(remoteMessage);
                break;
            case "200": // 설문결과
                makeResultNotification(remoteMessage);
                break;
        }

    }

    private void makeResultNotification(RemoteMessage remoteMessage) {
        Gson gson = new GsonBuilder().create();
        ReportResultBody reportResultBody = gson.fromJson(remoteMessage.getData().get("body"), ReportResultBody.class);
        Observable.just("makeResultDialog")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    for (PredictReport predictReport : userService.getUser().getPredictReports()) {
                        if (predictReport.getId().equals(reportResultBody.getReportId())) {
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            predictReport.setResult(reportResultBody.getResults());
                            userService.alertPredictReportChange();
                            realm.commitTransaction();
                        }
                    }

                    Intent intent = new Intent(getApplicationContext(), ResultDialogActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                });

    }


    private void makeNotification(RemoteMessage remoteMessage) {
        Gson gson = new GsonBuilder().create();
        PushMessageBody pushMessageBody = gson.fromJson(remoteMessage.getData().get("body"), PushMessageBody.class);
        PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "MH24_SCREENLOCK");
        wl.acquire(10000);
        PowerManager.WakeLock wl_cpu = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MH24_SCREENLOCK");
        wl_cpu.acquire(10000);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(pushMessageBody.getTitle())
                .setContentText(pushMessageBody.getMessage())
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{1, 1000});

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mBuilder.setContentIntent(contentIntent);
        mBuilder.setAutoCancel(true);
        notificationManager.notify(0 /* ID of notification */, mBuilder.build());
    }
}
