package com.kibtechnologies.captainshieid.service;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.JobIntentService;
import androidx.core.content.ContextCompat;

import com.kibtechnologies.captainshieid.Message;
import com.kibtechnologies.captainshieid.utils.PreferenceUtils;
import com.kibtechnologies.captainshieid.views.activities.BottomNaveDashboardActivity;

import java.util.ArrayList;

/**
 * Created by Khushboo Jha on 5/29/21.
 */
public class SendSimInformationService extends Service {
    ArrayList<String> _mst=new ArrayList<>();
    String TAG = "SendSimInformationService";
    String wantPermission = Manifest.permission.READ_PHONE_STATE;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getPhone();
        return START_STICKY;
    }

    private ArrayList<String> getPhone() {
        TelephonyManager phoneMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), wantPermission) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        ArrayList<String> _lst =new ArrayList<>();
        _lst.add("MOBILE NUMBER :-"+phoneMgr.getLine1Number());
        String phone =  PreferenceUtils.getInstance(getApplicationContext()).getString("phone" , "");
        sendSMS( phone,phoneMgr.getLine1Number() + " " +"Phone Sim No Changes by User");
        Log.e(TAG, "getPhone: "+ phone + phoneMgr.getLine1Number() + " " +"Phone Sim No Changes by User");
        _lst.add(String.valueOf(phoneMgr.getCallState()));
        _lst.add("SIM OPERATOR NAME :-"+phoneMgr.getSimOperatorName());
        _lst.add("SIM STATE :-"+String.valueOf(phoneMgr.getSimState()));
        _lst.add("COUNTRY ISO :-"+phoneMgr.getSimCountryIso());
        return _lst;
    }


    public void sendSMS(String phoneNo, String msg) {
        SharedPreferences sp = getSharedPreferences("Settings", MODE_PRIVATE);
        String s = sp.getString("register_Number", "ON");
// Message.tag("Message Sent"+phoneNo);
        if (s.equals("ON")) {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNo, null, msg, null, null);
//                Message.tag("Message Sent"+phoneNo);
            } catch (Exception ex) {
                System.out.println(ex);
                ex.printStackTrace();
            }
        } else {
            Message.deviceLog(getBaseContext(), "SMS Response couldn't be sent, Turn on the service in settings option");
        }
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
