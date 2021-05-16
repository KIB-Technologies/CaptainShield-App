package com.kibtechnologies.captainshieid;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textview.MaterialTextView;
import com.kibtechnologies.captainshieid.handler.AlarmHandler;
import com.kibtechnologies.captainshieid.handler.DemoCamActivity;

public class LockScreen extends AppCompatActivity {
//    Disable Back Key
    @Override
    public void onBackPressed() {

        return;
        //return nothing
    }

//    @Override
//    public void onAttachedToWindow() {
//        this.getWindow().setType(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        super.onAttachedToWindow();
//    }

//    Disable Tasks Key

    @Override
    protected void onPause() {
        super.onPause();

        ActivityManager activityManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            activityManager = (ActivityManager) getApplicationContext()
                    .getSystemService(Context.ACTIVITY_SERVICE);
        }

        activityManager.moveTaskToFront(getTaskId(), 0);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        boolean result;
        switch( event.getKeyCode() ) {
            case KeyEvent.KEYCODE_VOLUME_UP:
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                result = true;
                break;

            default:
                result= super.dispatchKeyEvent(event);
                break;
        }

        return result;
    }
//
//    Disable Volume Key
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            //Do your thing

            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_pin);
        EditText alt1 = (EditText) findViewById(R.id.alt1);
        EditText alt2 = (EditText) findViewById(R.id.alt2);
        String number1 = Message.GetSP(LockScreen.this, "Welcome_Phone", "secure_phone1", "no");
        String number2 = Message.GetSP(LockScreen.this, "Welcome_Phone", "secure_phone2", "no");
        if (!number1.equals("no")) {
            alt1.setText(number1);
        }
        if (!number2.equals("no")) {
            alt2.setText(number2);
        }

        Intent intent = getIntent();
        String phone = intent.getStringExtra("id");
        String senderNum = intent.getStringExtra("num");
        String message = intent.getStringExtra("message");

       /* AlarmHandler h = new AlarmHandler(this);
        h.stopAlarm();
        h.startAlarm();*/


       // startActivity(new Intent(this, DemoCamActivity.class));

/*int i = 0;
while (i <3) {
    Intent backgroundIntent2 = new Intent(getApplicationContext(), DemoCamService.class);
    backgroundIntent2.putExtra("id", "phone");
   *//* backgroundIntent2.putExtra("num", senderNum);
    backgroundIntent2.putExtra("message", message);*//*
    startService(backgroundIntent2);
    i++;
}*/

        Intent backgroundIntent = new Intent(getApplicationContext(), DemoCamService.class);
        backgroundIntent.putExtra("id", "phone");
      //  backgroundIntent.putExtra("num", senderNum);
       // backgroundIntent.putExtra("message", message);
        startService(backgroundIntent);


   /*     final Handler handler = new Handler();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

        }, 5000);*/





       /* AlarmHandler ah  = new AlarmHandler(this);
        ah.stopAlarm();
        ah.startAlarm();*/


    }
}
