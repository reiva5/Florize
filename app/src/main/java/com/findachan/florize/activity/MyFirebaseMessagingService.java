package com.findachan.florize.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    private NotificationManager mNotifyManager;
    private static final int NOTIFICATION_ID = 0;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.w(TAG, "From: " + remoteMessage.getFrom());
        Log.w(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("4655", "not", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotifyManager =(NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this,"4655")
                .setContentTitle("FLORIZE")
                .setContentText(remoteMessage.getNotification().getBody())
                .setContentIntent(notificationPendingIntent)
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setDefaults(Notification.DEFAULT_ALL);
        Notification myNotification = notifyBuilder.build();
        mNotifyManager.notify((int)(System.currentTimeMillis()/1000), myNotification);
    }
}