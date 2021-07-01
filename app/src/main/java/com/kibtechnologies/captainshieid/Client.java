package com.kibtechnologies.captainshieid;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kibtechnologies.captainshieid.handler.AlarmHandler;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;


public class Client extends AppCompatActivity {

    private Button send;
    private TextView info, query;
    private EditText phone, password, optional;
    private Typeface tf;
    private String status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_app);

//        //mukesh
//        Button demo= (Button) findViewById(R.id.client_demo);
//        demo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i=new Intent(getApplicationContext(),LockScreen.class);
//                startActivity(i);
//            }
//        });
        //end mukesh

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        tf = Typeface.createFromAsset(getAssets(), "fonts/font.ttf");
        phone = (EditText) findViewById(R.id.phone);
        phone.setText("+91");
        password = (EditText) findViewById(R.id.password);
        optional = (EditText) findViewById(R.id.optional);
        info = (TextView) findViewById(R.id.client_info);
        query = (TextView) findViewById(R.id.client_query);
        send = (Button) findViewById(R.id.client_final_button);
        info.setTypeface(tf);
        query.setTypeface(tf);
        phone.setTypeface(tf);
        password.setTypeface(tf);
        optional.setTypeface(tf);
        optional.setVisibility(View.INVISIBLE);
        send.setTypeface(tf);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Select Any Option");
//        categories.add("Lock");
        categories.add("Erase");
        categories.add("Display Lock");
//        categories.add("Silent Mode");
        categories.add("Normal Mode");
//        categories.add("Wifi ON");
//        categories.add("Wifi OFF");
        categories.add("Wifi Details");
        categories.add("Location");
//        categories.add("SIM Serial No");
        categories.add("Phone Number");
        categories.add("ring");
        categories.add("stop");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                switch (position) {
//                    case 1:
//                        status = "lock";
//                        optional.setVisibility(View.VISIBLE);
//                        optional.setHint("Enter new password to lock");
//
//                        break;

                    case 1:
                        status = "erase";
                        optional.setVisibility(View.INVISIBLE);
                        break;

                    case 2:
                        status = "lockdisplay";
                        optional.setVisibility(View.INVISIBLE);
                        break;

//                    case 4:
//                        status = "silent";
//                        optional.setVisibility(View.INVISIBLE);
//                        break;

                    case 3:
                        status = "normal";
                        optional.setVisibility(View.INVISIBLE);
                        break;

//                    case 6:
//                        status = "wifion";
//                        optional.setVisibility(View.INVISIBLE);
//                        break;
//
//                    case 7:
//                        status = "wifioff";
//                        optional.setVisibility(View.INVISIBLE);
//                        break;

                    case 4:
                        status = "wifiname";
                        optional.setVisibility(View.INVISIBLE);
                        break;

                    case 5:
                        status = "location";
                        optional.setVisibility(View.INVISIBLE);
                        break;

//                    case 10:
//                        status = "siminfo";
//                        optional.setVisibility(View.INVISIBLE);
//                        break;

                    case 6:
                        status = "phonenumber";
                        optional.setVisibility(View.INVISIBLE);
                        break;

                    case 7:
                        status = "ring";
                        optional.setVisibility(View.INVISIBLE);
                        break;

                    case 8:
                        status = "stop";
                        optional.setVisibility(View.INVISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ph = phone.getText().toString();
                String pass = password.getText().toString();
                if (ph.startsWith("+")) {
                    if (ph.length() > 9 && ph.length() < 14) {
                        if (pass.length() >= 5 && pass.length() <= 10) {
                            if (status.equals("lock")) {
                                String optnl = optional.getText().toString();
                                if (optnl.length() >= 5 && optnl.length() <= 10) {
                                    SecureRandom random = new SecureRandom();
                                    String ranNum = new BigInteger(30, random).toString();
                                    String command = "captain" + " " + Message.md5(pass + ranNum) + " " + ranNum + " " + status + " " + optnl;
                                    query.setText("Your SMS Command : " + command);
                                    sendSMS(ph, command);
                                } else {
                                    Message.toast(getBaseContext(), "New password length is invalid");
                                }
                            } else {
                                SecureRandom random = new SecureRandom();
                                String ranNum = new BigInteger(30, random).toString();
                                String command = "captain" + " " + Message.md5(pass + ranNum) + " " + ranNum + " " + status;
                                query.setText("Your SMS Command : " + command);
                                sendSMS(ph, command);
                            }
                        }
                    } else {
                        Message.toast(getBaseContext(), "Phone number length is invalid");
                    }
                } else {
                    Message.toast(getBaseContext(), "Enter your phone number with + and country code eg +91");
                }
            }
        });
    }

    public void sendSMS(String phone, String message) {
        Message.tag("Phone : " + phone + " Message : " + message);
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone, null, message, null, null);
            Message.toast(getBaseContext(), "Message Sent");
        } catch (Exception ex) {
            Message.toast(getBaseContext(), "Message failed to send");
        }
    }

}
