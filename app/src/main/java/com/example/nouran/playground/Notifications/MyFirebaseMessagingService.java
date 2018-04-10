package com.example.nouran.playground.Notifications;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nouran on 4/4/2018.
 */


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public MyFirebaseMessagingService() {
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Map<String,String> map = remoteMessage.getData();
        for (String key :map.keySet())
        {
            Log.e("data",key);
        }

        Log.d("firebase",  "From:" + remoteMessage.getFrom());
        Log.d("firebase", "Notification Message Body:"  +
                remoteMessage.getNotification().getBody());

    }
}