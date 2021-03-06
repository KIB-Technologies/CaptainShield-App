package com.kibtechnologies.captainshieid;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.kibtechnologies.captainshieid.adapter.DashboardOptionsAdapter;
import com.kibtechnologies.captainshieid.adapter.ItemClickListener;
import com.kibtechnologies.captainshieid.model.ActivationResponse;
import com.kibtechnologies.captainshieid.model.SecNumResponse;
import com.kibtechnologies.captainshieid.repository.AuthenticationViewModelFactory;
import com.kibtechnologies.captainshieid.repository.AuthentictionViewModel;
import com.kibtechnologies.captainshieid.utils.PreferenceUtils;
import com.kibtechnologies.captainshieid.utils.Util;
import com.kibtechnologies.captainshieid.views.activities.EnterActivationKeyActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Welcome extends Fragment {

    View v;
    Communicator communicator;
    private AuthentictionViewModel model;
    private Button btn;
    private String info, onActRet, where, pOnAct;
    private Typeface tf;
    private Context context;
    private EditText con1, con2, app, secure, /*login_pass*/
            activation_key, registerNumber;
    private TextInputEditText login_pass;
    private DevicePolicyManager mDPM;
    private ComponentName mDeviceApp;
    public DatabaseHelper myDb;
    public SwitchCompat switONOFF;
    MyAdap myAdap;
    Cursor cursor;
    private MainMenu menu;


    SharedPreferences sharedpreferences;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator = (Communicator) activity;
        menu = (MainMenu) activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Message.tag("onCreateView Frag");
        tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/font.ttf");
        String id = getArguments().getString("id");
        where = getArguments().getString("where");
        Message.tag("onCreateView Id : " + id);
        onActRet = "null";
        mDPM = (DevicePolicyManager) getActivity().getSystemService(Context.DEVICE_POLICY_SERVICE);
        mDeviceApp = new ComponentName(getActivity(), DeviceAdmin.class);

        if ("Login".equals(id)) {
            v = inflater.inflate(R.layout.login_app, container, false);
            btn = v.findViewById(R.id.act_welcome_next_btn);
            login_pass = v.findViewById(R.id.et_login);
//               Button btnClient = v.findViewById(R.id.client_btn);
            login_pass.setTypeface(tf);
            context = v.getContext();
            onActRet = "log-in";
            //textTypeface
            TextView tv1 = v.findViewById(R.id.login_text1);
            tv1.setTypeface(tf);
//            btnClient.setTypeface(tf);
//            btnClient.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivity(new Intent(v.getContext(),Client.class));
//                }
//            });
        } else if ("Privacy".equals(id)) {
            v = inflater.inflate(R.layout.activity_privacypolicy1, container, false);
            btn = v.findViewById(R.id.get_started);
            context = v.getContext();
            String welcomeKey = Message.GetSP(context, "Welcomekey", "activation_key", "no");
            String number = Message.GetSP(context, "RegisterNumber", "register_Number", "no");
            System.out.println(welcomeKey);
            System.out.println(number);
            info = "phone";
            onActRet = "privacy";
        }
//        else if ("Welcome".equals(id)) {
//            v = inflater.inflate(R.layout.welcome, container, false);
//            btn = v.findViewById(R.id.get_welcome);
//            info = "phone";
//            onActRet = "welcome";
//            context = v.getContext();
//            int[] ids = {R.id.sub_welcome, R.id.sub_middle_text};
//            for (int i = 0; i < ids.length; i++) {
//                TextView tv = v.findViewById(ids[i]);
//                tv.setTypeface(tf);
//            }
//            activation_key = v.findViewById(R.id.activationkey);
//            registerNumber = v.findViewById(R.id.registerMobileNumber);
//        }
        else if ("Phone".equals(id)) {
            Message.tag("TEST Phone");
            v = inflater.inflate(R.layout.welcome_phone, container, false);
            btn = v.findViewById(R.id.act_welcome_next_btn);
            info = "password";
            onActRet = "phone";
            context = v.getContext();
            int[] idarray = {R.id.act_welcome_status_1, R.id.act_welcome_status_2};
            for (int i = 0; i < idarray.length; i++) {
                TextView tv = v.findViewById(idarray[i]);
                tv.setBackgroundColor(getResources().getColor(R.color.status_green));
            }

            con1 = v.findViewById(R.id.secure_phone_1);
            con1.setText("+91");
//            con2 = v.findViewById(R.id.secure_phone_2);
//            con2.setText("+91");

            String number1 = Message.GetSP(v.getContext(), "Welcome_Phone", "secure_phone1", "no");
//            String number2 = Message.GetSP(v.getContext(), "Welcome_Phone", "secure_phone2", "no");
            if (!number1.equals("no")) {
                con1.setText(number1);
            }
//            if (!number2.equals("no")) {
//                con2.setText(number2);
//            }

            TextView tv = v.findViewById(R.id.sub_middle_text);
            tv.setTypeface(tf);

            ImageButton contact1 = v.findViewById(R.id.chooseContact1);
//            ImageButton contact2 = v.findViewById(R.id.chooseContact2);
            contact1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                    startActivityForResult(intent, 1001);
                    pOnAct = "one1";
                    Message.tag("TEST B1 Press");
                }
            });

//            contact2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                    intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
//                    startActivityForResult(intent, 1001);
//                    pOnAct = "two2";
//                    Message.tag("TEST B2 Press");
//                }
//            });

            //hide status
            if (where.equals("M")) {
                hideStatus(v);
            }
        } else if ("Password".equals(id)) {
            v = inflater.inflate(R.layout.welcome_password, container, false);
            btn = v.findViewById(R.id.act_welcome_next_btn);
            info = "device_admin";
            context = v.getContext();
            int[] idarray = {R.id.act_welcome_status_1, R.id.act_welcome_status_2, R.id.act_welcome_status_3};
            for (int i = 0; i < idarray.length; i++) {
                TextView tv = v.findViewById(idarray[i]);
                tv.setBackgroundColor(getResources().getColor(R.color.status_green));
            }

            app = v.findViewById(R.id.app_password);
            secure = v.findViewById(R.id.secure_password);
            String pass1 = Message.GetSP(v.getContext(), "Welcome_Password", "app_pass", "no");
            String pass2 = Message.GetSP(v.getContext(), "Welcome_Password", "secure_pass", "no");
            if (!pass1.equals("no")) {
                app.setText(pass1);
            }
            if (!pass2.equals("no")) {
                secure.setText(pass2);
            }
            onActRet = "password";

            int[] ids = {R.id.sub_middle_text, R.id.act_password_app, R.id.secure_password_text, R.id.act_password_secret};
            for (int i = 0; i < ids.length; i++) {
                TextView tv = v.findViewById(ids[i]);
                tv.setTypeface(tf);
            }

            //hide status
            if (where.equals("M")) {
                hideStatus(v);
            }
        } else if ("Device_Admin".equals(id)) {
            v = inflater.inflate(R.layout.welcome_device_admin, container, false);
            btn = v.findViewById(R.id.act_welcome_next_btn);
            info = "send_gcm";
            int[] idarray = {R.id.act_welcome_status_1, R.id.act_welcome_status_2, R.id.act_welcome_status_3, R.id.act_welcome_status_4};
            for (int i = 0; i < idarray.length; i++) {
                TextView tv = v.findViewById(idarray[i]);
                tv.setBackgroundColor(getResources().getColor(R.color.colorBlack));
            }
            context = v.getContext();
            onActRet = "device_admin";

            int[] ids = {R.id.act_device_text};
            for (int i = 0; i < ids.length; i++) {
                TextView tv = v.findViewById(ids[i]);
                tv.setTypeface(tf);
            }

            switONOFF = v.findViewById(R.id.act_device_switch);
            switONOFF.setTypeface(tf);
            switONOFF.setChecked(isActiveAdmin());
            switONOFF.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Enable();
                    } else {
                        Disable();
                    }
                }
            });

            //hide button
            if (where.equals("M")) {
                btn.setVisibility(View.INVISIBLE);
            }

            //hide status
            if (where.equals("M")) {
                hideStatus(v);
            }
        } else if ("Send_Gcm".equals(id)) {
            v = inflater.inflate(R.layout.welcome_gcm, container, false);
            btn = v.findViewById(R.id.act_welcome_next_btn);
            info = "send_gcm";
            onActRet = "finish";
            context = v.getContext();
            int[] idarray = {R.id.act_welcome_status_1, R.id.act_welcome_status_2, R.id.act_welcome_status_3, R.id.act_welcome_status_4,
                    R.id.act_welcome_status_5};
//            for(int i = 0; i<idarray.length; i++){
//                TextView tv = v.findViewById(idarray[i]);
//                tv.setBackgroundColor(getResources().getColor(R.color.status_green));
//            }

            int[] tv = {R.id.sub_welcome, R.id.sub_welcome1};
            for (int i = 0; i < tv.length; i++) {
                TextView tv1 = v.findViewById(tv[i]);
                tv1.setTypeface(tf);
            }

            //hide status
            if (where.equals("M")) {
                hideStatus(v);
            }
        } else if ("Status".equals(id)) {
            v = inflater.inflate(R.layout.home_menu, container, false);
            btn = v.findViewById(R.id.act_welcome_next_btn);
            btn.setVisibility(View.INVISIBLE);
            int[] ids = {R.id.title1, R.id.title3, R.id.title4, R.id.title5, R.id.key_1_phone1, R.id.key_3_app, R.id.key_4_secure, R.id.key_5_admin};
            for (int i = 0; i < ids.length; i++) {
                TextView tv = v.findViewById(ids[i]);
                tv.setTypeface(tf);
            }
            context = v.getContext();
            TextView key1 = v.findViewById(R.id.key_1_phone1);
            TextView key3 = v.findViewById(R.id.key_3_app);
            TextView key4 = v.findViewById(R.id.key_4_secure);
            TextView key5 = v.findViewById(R.id.key_5_admin);
            RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
            DashboardOptionsAdapter adapter = new DashboardOptionsAdapter(menu);
            recyclerView.setAdapter(adapter);
            if (Message.GetSP(context, "Welcome_Phone", "secure_phone1", "NIL").equals("NIL")) {
                key1.setText("OFF");
                key1.setBackgroundColor(getResources().getColor(R.color.off));
            } else {
                key1.setText("ON");
                key1.setBackgroundColor(getResources().getColor(R.color.on));
            }
            //3
            if (Message.GetSP(context, "Welcome_Password", "app_pass", "NIL").equals("NIL")) {
                key3.setText("OFF");
                key3.setBackgroundColor(getResources().getColor(R.color.off));
            } else {
                key3.setText("ON");
                key3.setBackgroundColor(getResources().getColor(R.color.on));
            }
            //4
            if (Message.GetSP(context, "Welcome_Password", "secure_pass", "NIL").equals("NIL")) {
                key4.setText("OFF");
                key4.setBackgroundColor(getResources().getColor(R.color.off));
            } else {
                key4.setText("ON");
                key4.setBackgroundColor(getResources().getColor(R.color.on));
            }
            //5
            if (isActiveAdmin()) {
                key5.setText("ON");
                key5.setBackgroundColor(getResources().getColor(R.color.on));
            } else {
                key5.setText("OFF");
                key5.setBackgroundColor(getResources().getColor(R.color.off));
            }
        } else if ("Log".equals(id)) {
            v = inflater.inflate(R.layout.list, container, false);
            btn = v.findViewById(R.id.act_welcome_next_btn);
            TextView tv = v.findViewById(R.id.listall_info_text);
            tv.setText("Your application log");
            tv.setTypeface(tf);
            btn.setVisibility(View.INVISIBLE);
            myDb = new DatabaseHelper(v.getContext());
            myAdap = new MyAdap(v.getContext());
            populateListView(myAdap, v);
        } else if ("Settings".equals(id)) {
            v = inflater.inflate(R.layout.settings, container, false);
            btn = v.findViewById(R.id.act_welcome_next_btn);
            btn.setVisibility(View.INVISIBLE);
            int[] tvView = {R.id.setting_alert, R.id.setting_info, R.id.setting_logs};
            for (int i = 0; i < tvView.length; i++) {
                TextView tv = v.findViewById(tvView[i]);
                tv.setTypeface(tf);
            }
            CheckBox lodAdmin = v.findViewById(R.id.logadmin);
            CheckBox lodApp = v.findViewById(R.id.logapp);
            CheckBox lodDevice = v.findViewById(R.id.logdevice);
            lodAdmin.setTypeface(tf);
            lodApp.setTypeface(tf);
            lodDevice.setTypeface(tf);
            lodAdmin.setChecked(Message.GetSP(v.getContext(), "Settings", "LogAdmin", "ON").equals("ON"));

            lodApp.setChecked(Message.GetSP(v.getContext(), "Settings", "LogApp", "ON").equals("ON"));

            lodDevice.setChecked(Message.GetSP(v.getContext(), "Settings", "LogDevice", "ON").equals("ON"));

            lodAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Message.SetSP(v.getContext(), "Settings", "LogAdmin", "ON");
                    } else {
                        Message.SetSP(v.getContext(), "Settings", "LogAdmin", "OFF");
                    }
                }
            });

            lodApp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Message.SetSP(v.getContext(), "Settings", "LogApp", "ON");
                    } else {
                        Message.SetSP(v.getContext(), "Settings", "LogApp", "OFF");
                    }
                }
            });

            lodDevice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Message.SetSP(v.getContext(), "Settings", "LogDevice", "ON");
                    } else {
                        Message.SetSP(v.getContext(), "Settings", "LogDevice", "OFF");
                    }
                }
            });

            Switch response = v.findViewById(R.id.switch_smsresponse);
            context = v.getContext();
            int[] switchs = {R.id.switch_smsresponse};
            for (int i = 0; i < switchs.length; i++) {
                Switch sw = v.findViewById(switchs[i]);
                sw.setTypeface(tf);
            }
            if (Message.GetSP(context, "Settings", "Response", "ON").equals("ON")) {
                response.setChecked(true);
            }

            response.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Message.SetSP(context, "Settings", "Response", "ON");
                    } else {
                        Message.SetSP(context, "Settings", "Response", "OFF");
                    }
                }
            });
        } else if ("Help".equals(id)) {
            v = inflater.inflate(R.layout.app_help, container, false);
            btn = v.findViewById(R.id.act_welcome_next_btn);
            btn.setVisibility(View.INVISIBLE);
            Button client = v.findViewById(R.id.help_client);
//            R.id.tv12, R.id.tv13,R.id.tv14,R.id.tv15,
            int[] ids = {R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv6, R.id.tv7, R.id.tv8, R.id.tv9, R.id.tv29,
                    R.id.tv28, R.id.tv16, R.id.tv17, R.id.tv18, R.id.tv19, R.id.tv20,
                    R.id.tv21, R.id.tv24, R.id.tv25, R.id.tv26, R.id.tv27
            };
            for (int i = 0; i < ids.length; i++) {
                TextView tv = v.findViewById(ids[i]);
                tv.setTypeface(tf);
            }
            client.setTypeface(tf);
            client.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(v.getContext(), Client.class));
                }
            });
        }

        if (where.equals("W")) {
            if (id.equals("Send_Gcm")) {
                btn.setText("Finish");
            } else {
                btn.setText("Next");
            }
        } else if (where.equals("M")) {
            if ("Login".equals(id)) {
                btn.setText("Login");
            } else {
                btn.setText("Submit");
            }
        }

        btn.setTypeface(tf);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onActRet.equals("privacy")) {

                    btn.setText("loading...");
                    communicator.onWelcome(info);
                    Message.toast(context, "Privacy policy accepted");
                }
//                else if (onActRet.equals("welcome")) {
//                    //Activation key works number set
//                    if (registerNumber.getText().toString().length() > 9 && registerNumber.getText().length() < 11) {
//                        if (activation_key.getText().toString().length() > 7 && activation_key.getText().length() < 9) {
//                            if (!activation_key.getText().toString().isEmpty() && !registerNumber.getText().toString().isEmpty()) {
//
//                                        btn.setText("loading...");
//                                        saveUser(createRequest());
//                            }
//                        } else {
//                            Message.toast(context, "Your Activation Code is Invalid, Please Enter Correct Code.");
//                        }//Phone number finish
//                    } else {
//                        Message.toast(context,"Please Enter Valid Number");
//                    }
//
//                }
                else if (onActRet.equals("phone")) {
                    Message.tag("TEST Final Button Press");
                    //Phone number set
                    if (con1.getText().toString().length() > 9 && con1.getText().length() < 15 && con1.getText().toString().startsWith("+91")) {
                        Message.SetSP(context, "Welcome_Phone", "secure_phone1", con1.getText().toString());
                        if (!where.equals("M")) {
                            model = new ViewModelProvider((ViewModelStoreOwner) context, new AuthenticationViewModelFactory()).get(AuthentictionViewModel.class);
                            model.updateSecNumber().removeObservers((LifecycleOwner) context);
                            model.updateSecNumber().observe((LifecycleOwner) context, new Observer<SecNumResponse>() {
                                @Override
                                public void onChanged(SecNumResponse secNumResponse) {
                                    if (secNumResponse.response_code == 200) {
                                        communicator.onWelcome(info);
                                    } else {
                                        Message.toast(context, "Something went wrong, Please enter valid number.");
                                    }
                                }
                            });
                            Map<String, Object> body = new HashMap<>();
                            body.put("alternateNumber", con1.getText().toString());
                            String token = PreferenceUtils.getInstance(context).getToken();
                            if (Util.isTextValid(con1.getText().toString())) {
                                model.updateSecNumber("bearer " + token, body);
                            } else {
                                Message.toast(context, "Please enter your correct number");
                            }
                        } else {
                            success(context);
                        }
                    } else {
                        Message.toast(context, "Your phone number length is invalid, Enter with country code eg +91");
                    }//Phone number finish

                } else if (onActRet.equals("password")) {
                    int num = app.getText().toString().length();
                    int num1 = secure.getText().toString().length();
                    if (num >= 5 && num <= 10) {
                        if (num1 >= 5 && num1 <= 10) {
                            Message.SetSP(context, "Welcome_Password", "app_pass", app.getText().toString());
                            Message.SetSP(context, "Welcome_Password", "secure_pass", secure.getText().toString());
                            if (where.equals("M")) {
                                success(context);
                            } else {
                                communicator.onWelcome(info);
                            }
                        } else {
                            Message.toast(context, "Secure password should be between 5 to 10 characters");
                        }
                    } else {
                        Message.toast(context, "App password should be between 5 to 10 characters");
                    }
                } else if (onActRet.equals("device_admin")) {
                    if (isActiveAdmin()) {
                        Message.SetSP(context, "DeviceAdmin", "Admin", "ON");
                        communicator.onWelcome(info);
                    } else {
                        communicator.onWelcome(info);
                        Message.toast(context, "You can turn-on device admin later on settings option");
                    }
                } else if (onActRet.equals("finish")) {
                    Message.SetSP(context, "ACT", "welcome", "DONE");
                    context.startService(new Intent(context, RegistrationIntentService.class));
                    startActivity(new Intent(context, MainMenu.class));

                } else if (onActRet.equals("log-in")) {
                    String pass = Message.GetSP(context, "Welcome_Password", "app_pass", "null");
                    if (!pass.equals("null")) {
                        if (pass.equals(login_pass.getText().toString())) {
                            communicator.onWelcome("main");
                        } else {
                            Message.toast(context, "Incorrect Password");
                        }
                    }
                } else {
                    communicator.onWelcome(info);
                }
            }
        });


        return v;
    }

    public UserRequest createRequest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setKey(activation_key.getText().toString());
        userRequest.setPrimaryNumber(registerNumber.getText().toString());
        String phnNo = registerNumber.getText().toString();
        PreferenceUtils.getInstance(context).saveString(PreferenceUtils.USER_PHONE_NO, phnNo);
        return userRequest;

    }

//    public void saveUser(UserRequest userRequest) {
//        System.out.println("Under saveUser.");
//        System.out.println(userRequest);
//        Call<UserResponse> userResponseCall = NetworkClient.getUserService().checkKey(userRequest);
//        userResponseCall.enqueue(new Callback<UserResponse>() {
//            @Override
//            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//                System.out.println(response.message());
//                System.out.println(response.body());
//                if (response.isSuccessful()) {
//                    Message.SetSP(context, "Welcomekey", "activation_key", activation_key.getText().toString());
//                    Message.SetSP(context, "RegisterNumber", "register_Number", registerNumber.getText().toString());
//                    Message.toast(context, "Your Code Activate Successfully");
//                    success(context);
//                    communicator.onWelcome(info);
//                } else if(response.code() == 404){
//                    Message.toast(context, "Key Not Found");
//                }else if(response.code() == 400){
//                    Message.toast(context, "Activation Key is Already in Use");
//                }else{
//                    Message.toast(context, "Something wents wrong!");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserResponse> call, Throwable t) {
//                Message.toast(context, "Request Failed" + t.getLocalizedMessage());
//            }
//        });
//    }

    public void hideStatus(View vw) {
        int[] idarray = {R.id.act_welcome_status_1, R.id.act_welcome_status_2, R.id.act_welcome_status_3, R.id.act_welcome_status_4,
                R.id.act_welcome_status_5};
        for (int i = 0; i < idarray.length; i++) {
            TextView tv = vw.findViewById(idarray[i]);
            tv.setVisibility(View.INVISIBLE);
        }
    }

    public void success(Context context) {
        Message.toast(context, "Success");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1001:

                if (data != null) {
                    Uri uri = data.getData();

                    if (uri != null) {
                        Cursor c = null;
                        try {
                            c = context.getContentResolver().query(uri, new String[]{
                                            ContactsContract.CommonDataKinds.Phone.NUMBER,
                                            ContactsContract.CommonDataKinds.Phone.TYPE},
                                    null, null, null);

                            if (c != null && c.moveToFirst()) {
                                String number = c.getString(0);
                                int type = c.getInt(1);
                                String remo = number.replace(" ", "");
                                if (pOnAct.equals("one1")) {
                                    con1.setText(remo);
                                    onActRet = "phone";
                                    Message.tag("TEST B1 Clicked");

                                } else if (pOnAct.equals("two2")) {
                                    con2.setText(remo);
                                    onActRet = "phone";
                                    Message.tag("TEST B2 Clicked");
                                }
                            }
                        } finally {
                            if (c != null) {
                                c.close();
                            }
                        }
                    }
                }

                break;

        }

    }

    private boolean isActiveAdmin() {
        return mDPM.isAdminActive(mDeviceApp);
    }

    public void Enable() {
        Message.tag("Enable Admin");
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        startActivity(intent);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceApp);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, this.getString(R.string.add_admin_extra_text));
        startActivityForResult(intent, 1);
    }

    public void Disable() {
        Message.tag("Disable Admin");
        mDPM.removeActiveAdmin(mDeviceApp);
    }

    public void populateListView(MyAdap ba, View vv) {

        cursor = myDb.getAllData();


        if (cursor.getCount() >= 1) {
            String[] FieldNames = new String[]{DatabaseHelper.COL_2, DatabaseHelper.COL_4};
            int[] toViewIds = new int[]{R.id.item_layout_date, R.id.item_layout_message};
            //SimpleCursorAdapter simpleCursorAdapter;
            //simpleCursorAdapter = new SimpleCursorAdapter(vv.getContext(), R.layout.item_layout,cursor,FieldNames, toViewIds, 0);
            myAdap.getValue(cursor);
            ListView listView = vv.findViewById(R.id.lv);
            listView.setAdapter(ba);

        } else {
            Message.tag("Something is wrong..!! Reinstall the app");
        }
    }


    interface Communicator {
        void onCreate(Bundle savedInstanceState);

        void onWelcome(String string);
    }
}

class MyAdap extends BaseAdapter {

    String[] menuoptions;
    String[] menuTime;
    String date, msg;

    public void getValue(Cursor c) {
        menuoptions = new String[c.getCount()];
        menuTime = new String[c.getCount()];
        List<String> list_msg = new ArrayList<String>();
        List<String> list_tym = new ArrayList<String>();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    date = c.getString(c.getColumnIndex("DATE"));
                    msg = c.getString(c.getColumnIndex("MESSAGE"));
                    list_msg.add(msg);
                    list_tym.add(date);
                } while (c.moveToNext());
            }
        }
        menuoptions = list_msg.toArray(menuoptions);
        menuTime = list_tym.toArray(menuTime);
    }


    private final Context context;

    Typeface tf;

    public MyAdap(Context context) {
        this.context = context;
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/font.ttf");
    }

    @Override
    public int getCount() {
        return menuoptions.length;
    }

    @Override
    public Object getItem(int position) {
        return menuoptions[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.item_layout, parent, false);
        } else {
            row = convertView;
        }
        TextView titleTextView = row.findViewById(R.id.item_layout_message);
        TextView titleDate = row.findViewById(R.id.item_layout_date);
        titleDate.setText(menuTime[position]);
        titleTextView.setText(menuoptions[position]);
        titleTextView.setTypeface(tf);
        titleDate.setTypeface(tf);

        return row;
    }
}

