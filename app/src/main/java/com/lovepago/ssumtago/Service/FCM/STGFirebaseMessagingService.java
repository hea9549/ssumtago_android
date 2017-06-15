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
import com.google.gson.reflect.TypeToken;
import com.lovepago.ssumtago.CustomClass.StringRealmListConverter;
import com.lovepago.ssumtago.Data.Model.RealmString;
import com.lovepago.ssumtago.Data.Model.SurveyResult;
import com.lovepago.ssumtago.Presentation.Activity.MainActivity;
import com.lovepago.ssumtago.R;

import java.util.Iterator;

import io.realm.RealmList;

/**
 * Created by ParkHaeSung on 2017-05-28.
 */

public class STGFirebaseMessagingService extends FirebaseMessagingService {
    String TAG = "FCMMessagingService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Iterator keySet = remoteMessage.getData().keySet().iterator();
        String pushType = remoteMessage.getData().get("pushType");
        Iterator<String> iterator = remoteMessage.getData().keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            Log.e(TAG,"key = "+key+", value = "+remoteMessage.getData().get(key));
        }
        switch (pushType){
            case "030001":
                //
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(new TypeToken<RealmList<RealmString>>() {}.getType(),
                                new StringRealmListConverter())
                        .create();
                SurveyResult result = gson.fromJson(remoteMessage.getData().get("data"),SurveyResult.class);
                sendNotification("결과능 !! : "+result.getResult().get(0).getContent());
                break;
            default:
                Log.e(TAG,"illegal pushType. input push type = "+pushType);
        }

    }
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("FCM Push Test")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
