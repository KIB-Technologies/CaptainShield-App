package com.kibtechnologies.captainshieid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.kibtechnologies.captainshieid.utils.PreferenceUtils;

import static android.content.Context.MODE_PRIVATE;


public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Message.tag("BOOT COMPLETE");
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String phone = PreferenceUtils.getInstance(context).getString("phone", "");
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        sendSMS(phone, tm.getLine1Number() + " " + "Phone Sim No Changes by User", context);
        Log.e("SendSImSms", "getPhone: "+ phone + tm.getLine1Number() + " " +"Phone Sim No Changes by User");
        if(Message.GetSP(context,"Sim","SimNo","null").equals(tm.getSimSerialNumber())){
            Message.tag("SIM Number Match");
        }else {
            Intent intent1 = new Intent(context,Background.class);
            intent1.putExtra("id","sms");
            intent1.putExtra("message","Your sim card has been changed");
            intent1.putExtra("phone",Message.GetSP(context,"Welcome_Phone","secure_phone1","null"));
            context.startService(intent1);
            Message.tag("SIM Number Doesn't Match");
        }
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
