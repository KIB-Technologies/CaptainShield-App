package com.kibtechnologies.captainshieid.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.core.content.ContextCompat;

import com.kibtechnologies.captainshieid.Message;

/**
 * Created by Khushboo Jha on 5/26/21.
 */
public class Util {


    private Util() {
    }

    public static void sendSMS(String phoneNo, String msg, Context context) {

        if(Message.GetSP(context,"Settings","Response","ON").equals("ON")) {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNo, null, msg, null, null);
                Message.tag("Message Sent"+phoneNo);

            } catch (Exception ex) {
                Message.tag(ex.getMessage());
                ex.printStackTrace();
            }
            Message.tag("Reply : " + msg);
        }else{
            Message.deviceLog(context, "SMS Response couldn't be sent, Turn on the service in settings option");
        }
    }

    public static boolean checkPermission(String permission, Context context){
        if (Build.VERSION.SDK_INT >= 23) {
            int result = ContextCompat.checkSelfPermission(context, permission);
            if (result == PackageManager.PERMISSION_GRANTED){
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean isTextValid(String text) {
        if (text == null || text.trim().equals("") || text.trim().equalsIgnoreCase("null"))
            return false;
        else
            return true;
    }

    public static boolean isEmailContains(String email) {

        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    // Checks Validity of Phone number
    public static boolean isPhoneNumberValid(String target) {
        String regex = "^[6-9][0-9]{9}$";
        return target.matches(regex);
    }

    public static boolean isNameValid(String name) {
        String regex = "^[a-zA-Z\\s]+";
        return name.matches(regex);
    }
}
