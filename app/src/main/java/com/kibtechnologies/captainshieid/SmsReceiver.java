package com.kibtechnologies.captainshieid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Message.tag("SMS Broadcast on Recieve");
        final Bundle bundle = intent.getExtras();

        try {
            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    if (Message.GetSP(context, "Welcome_Phone", "secure_phone1", "null").equals(phoneNumber) ||
                            Message.GetSP(context, "Welcome_Phone", "secure_phone2", "null").equals(phoneNumber)) {
                        String senderNum = phoneNumber;
                        String message = currentMessage.getDisplayMessageBody();
                        if (message.contains("ring")) {
                            Intent lockscreenintent = new Intent(context, LockScreen.class);
                            lockscreenintent.putExtra("id", "phone");
                            lockscreenintent.putExtra("num", senderNum);
                            lockscreenintent.putExtra("message", message);
                           lockscreenintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(lockscreenintent);
                        } else {
                            Intent intent1 = new Intent(context, Background.class);
                            intent1.putExtra("id", "phone");
                            intent1.putExtra("num", senderNum);
                            intent1.putExtra("message", message);
                            context.startService(intent1);
                        }
                    }
                } // end for loop
            } // bundle is null
        } catch (Exception e) {
            Message.tag("Exception smsReceiver" + e);
        }
    }
}
