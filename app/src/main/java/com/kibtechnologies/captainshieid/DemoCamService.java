package com.kibtechnologies.captainshieid;

import android.Manifest;
import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.androidhiddencamera.CameraConfig;
import com.androidhiddencamera.CameraError;
import com.androidhiddencamera.HiddenCameraService;
import com.androidhiddencamera.HiddenCameraUtils;
import com.androidhiddencamera.config.CameraFacing;
import com.androidhiddencamera.config.CameraImageFormat;
import com.androidhiddencamera.config.CameraResolution;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Intent.getIntent;
import static android.widget.Toast.LENGTH_SHORT;

public class DemoCamService extends HiddenCameraService {
    public static Retrofit retrofit = null;
    String phone;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        phone = intent.getStringExtra("phone");
        Log.e("demo", "onStartCommand: ");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {

          //  if (HiddenCameraUtils.canOverDrawOtherApps(this)) {
                CameraConfig cameraConfig = new CameraConfig()
                        .getBuilder(this)
                        .setCameraFacing(CameraFacing.FRONT_FACING_CAMERA)
                        .setCameraResolution(CameraResolution.MEDIUM_RESOLUTION)
                        .setImageFormat(CameraImageFormat.FORMAT_JPEG)
                        .build();
                    startCamera(cameraConfig);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        takePicture();
                    }
                }, 2000);

/*
        } else {
            HiddenCameraUtils.openDrawOverPermissionSetting(this);
        }*/

            } else {
                //TODO Ask your parent activity for providing runtime permission
                Toast.makeText(this, "error cannot get permission", Toast.LENGTH_LONG).show();
                Toast.makeText(this, "Camera permission not available", LENGTH_SHORT).show();
            }
            return START_NOT_STICKY;


    }

    @Override
    public void onImageCapture(@NonNull File imageFile) {
        uploadImage(imageFile);

    }

    public void uploadImage(File rurl) {
        SharedPreferences sp = getSharedPreferences("RegisterNumber", MODE_PRIVATE);
        String s = sp.getString("register_Number", "no number");
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://capsheild.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        RequestBody phonePart = RequestBody.create(MultipartBody.FORM, s);
        RequestBody filePart = RequestBody.create(MediaType.parse("multipart.form-data"), rurl.getAbsoluteFile());
        MultipartBody.Part parts = MultipartBody.Part.createFormData("file", rurl.getName(), filePart);
        UserData userData = retrofit.create(UserData.class);
        Call call = userData.sendData(parts, phonePart);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.code() == 201) {
                    UserDataResponse jsonResponse = (UserDataResponse) response.body();
                    String link = jsonResponse.link;
                   sendSMS(phone, "Open url link to see images: " + link);
                    Intent backgroundIntent = new Intent(getApplicationContext(), DemoCamService.class);
                    backgroundIntent.putExtra("id", "phone");
                    //  backgroundIntent.putExtra("num", senderNum);
                    // backgroundIntent.putExtra("message", message);
                    startService(backgroundIntent);
                } else if (response.code() == 404) {
                    Message.toast(DemoCamService.this, "Key Not Found");
                } else if (response.code() == 400) {
                    Message.toast(DemoCamService.this, "bad request");
                } else {
                    Message.toast(DemoCamService.this, "Something wents wrong!");
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Message.toast(DemoCamService.this, t.getMessage());
                Message.toast(DemoCamService.this, "Something wents wrong! on failure");
            }
        });
    }

    public void sendSMS(String phoneNo, String msg) {
        SharedPreferences sp = getSharedPreferences("Settings", MODE_PRIVATE);
        String s = sp.getString("register_Number", "ON");
//        Message.tag("Message Sent"+phoneNo);
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
    }

    @Override
    public void onCameraError(@CameraError.CameraErrorCodes int errorCode) {
        switch (errorCode) {
            case CameraError.ERROR_CAMERA_OPEN_FAILED:
                Toast.makeText(this, "R.string.error_cannot_open", Toast.LENGTH_LONG).show();
                break;
            case CameraError.ERROR_IMAGE_WRITE_FAILED:
                Toast.makeText(this, "R.string.error_cannot_write", Toast.LENGTH_LONG).show();
                break;
            case CameraError.ERROR_CAMERA_PERMISSION_NOT_AVAILABLE:
                Toast.makeText(this, "R.string.error_cannot_get_permission", Toast.LENGTH_LONG).show();
                break;
            case CameraError.ERROR_DOES_NOT_HAVE_OVERDRAW_PERMISSION:
                HiddenCameraUtils.openDrawOverPermissionSetting(this);
                break;
            case CameraError.ERROR_DOES_NOT_HAVE_FRONT_CAMERA:
                Toast.makeText(this, "R.string.error_not_having_camera", Toast.LENGTH_LONG).show();
                break;
        }

        stopSelf();
    }


}