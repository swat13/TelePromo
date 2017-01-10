package com.alindas.rssreader.Firebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.alindas.rssreader.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;


/**
 * Created by Alif on 10/5/2016.
 */

public class MessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e("NOTTTTIIFF", "From: " + remoteMessage.getFrom());
        Map<String, String> body = remoteMessage.getData();
        Log.e("###########", "Notification Message Body: " + body);
//        Toast.makeText(getApplicationContext(), body, Toast.LENGTH_SHORT).show();
        sendNotification(body.get("link"), body.get("title"), body.get("subtitle"), body.get("action"), body.get("code"), body.get("number"), body.get("serviceText"));
    }

    private void sendNotification(String url, String title, String text, String action, String code, String number,String serviceText) {
        Log.e(TAG, "sendNotification: 0000000");
        Intent resultIntent = null;
        if (action.equals("1")) {
            resultIntent = new Intent(Intent.ACTION_VIEW);
            resultIntent.setData(Uri.parse(url));
        } else if (action.equals("2")) {
            resultIntent = new Intent();
            resultIntent.setClassName(getApplicationContext(), "com.alindas.rssreader.dialog");
            resultIntent.putExtra("code", code);
            resultIntent.putExtra("num", number);
            resultIntent.putExtra("serviceText", serviceText);
        } else if (action.equals("3")) {
            resultIntent = new Intent();
            resultIntent.setClassName(getApplicationContext(), "com.alindas.rssreader.dialog");
            resultIntent.putExtra("code", code);
            resultIntent.putExtra("num", number);
            resultIntent.putExtra("serviceText", serviceText);
            resultIntent.putExtra("link", url);
        } else {
            return;
        }

        PendingIntent pending = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.image1)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(false)
                .setSound(defaultSoundUri)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentIntent(pending);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());

    }

}
