package com.kibtechnologies.captainshieid.recievers;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.kibtechnologies.captainshieid.DemoCamService;
import com.kibtechnologies.captainshieid.Message;
import com.kibtechnologies.captainshieid.service.SendSimInformationService;
import com.kibtechnologies.captainshieid.utils.PreferenceUtils;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Khushboo Jha on 5/27/21.
 */
public class StartMyServiceAtBootReceiver extends BroadcastReceiver {
    String TAG = "SendSimInformationService";
    String wantPermission = Manifest.permission.READ_PHONE_STATE;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Message.tag("BOOT COMPLETE2");
            getPhone(context);
     /*       Intent serviceIntent = new Intent(context, SendSimInformationService.class);
         context.startService(serviceIntent);*/
        }
    }

    private ArrayList<String> getPhone(Context context) {
        TelephonyManager phoneMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, wantPermission) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        ArrayList<String> _lst =new ArrayList<>();
        _lst.add("MOBILE NUMBER :-"+phoneMgr.getLine1Number());
        String phone =  PreferenceUtils.getInstance(context).getString("phone" , "");
        sendSMS( phone,phoneMgr.getLine1Number() + " " +"Phone Sim No Changes by User", context);
        Log.e(TAG, "getPhone: "+ phone + phoneMgr.getLine1Number() + " " +"Phone Sim No Changes by User");
        _lst.add(String.valueOf(phoneMgr.getCallState()));
        _lst.add("SIM OPERATOR NAME :-"+phoneMgr.getSimOperatorName());
        _lst.add("SIM STATE :-"+String.valueOf(phoneMgr.getSimState()));
        _lst.add("COUNTRY ISO :-"+phoneMgr.getSimCountryIso());
        return _lst;
    }


    public void sendSMS(String phoneNo, String msg, Context context) {
        SharedPreferences sp = context.getSharedPreferences("Settings", MODE_PRIVATE);
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
            Message.deviceLog(context, "SMS Response couldn't be sent, Turn on the service in settings option");
        }
    }

}
