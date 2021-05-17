package com.kibtechnologies.captainshieid;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

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
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_VOLUME_UP:
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                result = true;
                break;

            default:
                result = super.dispatchKeyEvent(event);
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


        Intent backgroundIntent = new Intent(getApplicationContext(), Background.class);
        backgroundIntent.putExtra("id", phone);
        backgroundIntent.putExtra("num", senderNum);
        backgroundIntent.putExtra("message", message);
        startService(backgroundIntent);


    }
}
