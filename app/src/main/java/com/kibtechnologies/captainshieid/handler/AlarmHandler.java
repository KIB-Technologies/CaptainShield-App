package com.kibtechnologies.captainshieid.handler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.androidhiddencamera.HiddenCameraService;
import com.kibtechnologies.captainshieid.Background;
import com.kibtechnologies.captainshieid.DemoCamService;
import com.kibtechnologies.captainshieid.Receiver;
import com.kibtechnologies.captainshieid.SmsReceiver;

/**
 * Created by Khushboo Jha on 5/14/21.
 */
public class AlarmHandler {
    private Context context;

    public AlarmHandler(Context context) {
        this.context = context;
    }

    //this will call to start alarm
    public void startAlarm(){

        Intent intent = new Intent(context, DemoCamService.class);
        intent.putExtra("phone", "7388276319");
        PendingIntent sender = PendingIntent.getService(context, 2,intent, 0);
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if (am!= null) {

            long triggerAfter = 5000; //this will trigger after 2 sec
            long triggerEvery = 5000; // this will trigger at every 2 sec after that
            am.setRepeating(AlarmManager.RTC_WAKEUP, triggerAfter, triggerEvery, sender);
            Log.e("AlarmHandler", "startAlarm: " );
        }

    }

    //this will call to stop alarm
    public void  stopAlarm(){
        Intent intent = new Intent(context, DemoCamService.class);
        PendingIntent sender = PendingIntent.getService(context, 1,intent, 0);
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if (am != null) {
            am.cancel(sender);
            Log.e("AlarmHandler", "stopAlarm: " );
        }
    }
}
