package com.kibtechnologies.captainshieid;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import com.google.android.gms.gcm.GcmListenerService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GCMListenerService extends GcmListenerService {

    com.kibtechnologies.captainshieid.DatabaseHelper MyDb;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        MyDb = new com.kibtechnologies.captainshieid.DatabaseHelper(getBaseContext());
        AddData(message);
        Toast.makeText(getApplicationContext(), "Get message successfully", Toast.LENGTH_SHORT).show();
        sendNotification(getBaseContext(), message, MainMenu.class);
    }

    public static void sendNotification(Context context,String Message, Class clas){
        Intent intent = new Intent(context, clas);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Captain shield")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentText(Message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(Message))
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

    public void AddData(String message){

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        MyDb.insertData(currentDateandTime, message);
    }


}
