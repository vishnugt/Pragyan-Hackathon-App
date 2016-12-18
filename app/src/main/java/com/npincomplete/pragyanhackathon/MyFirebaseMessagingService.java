package com.npincomplete.pragyanhackathon;

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

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //sendNotification(remoteMessage.getData().get("Lat"));
        //Log.d(TAG, "From: " + remoteMessage.getFrom());
        //Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

        String temp = "Your Ambulance will arrive in " + remoteMessage.getData().get("Time") + " minutes.  Phone Number of Driver (" + (remoteMessage.getData().get("Name")) + ") is " + remoteMessage.getData().get("Phone");
        sendNotification(temp);

        Intent intent = new Intent(this, hospital_activity.class);
        intent.putExtra("Lat", remoteMessage.getData().get("Lat"));
        intent.putExtra("Long", remoteMessage.getData().get("Long"));
        intent.putExtra("Time", remoteMessage.getData().get("Time"));
        intent.putExtra("Vehicle_no", remoteMessage.getData().get("Vehicle_no"));
        intent.putExtra("Phone", remoteMessage.getData().get("Phone") );
        intent.putExtra("Name", remoteMessage.getData().get("Name"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }


    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_icon1)
                .setContentTitle("Chiron")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody));

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
