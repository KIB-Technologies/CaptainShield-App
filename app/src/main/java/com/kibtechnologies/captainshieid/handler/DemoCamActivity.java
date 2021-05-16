package com.kibtechnologies.captainshieid.handler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.androidhiddencamera.CameraConfig;
import com.androidhiddencamera.CameraError;
import com.androidhiddencamera.HiddenCameraActivity;
import com.androidhiddencamera.HiddenCameraUtils;
import com.androidhiddencamera.config.CameraFacing;
import com.androidhiddencamera.config.CameraImageFormat;
import com.androidhiddencamera.config.CameraResolution;
import com.androidhiddencamera.config.CameraRotation;
import com.kibtechnologies.captainshieid.Message;
import com.kibtechnologies.captainshieid.R;
import com.kibtechnologies.captainshieid.UserData;
import com.kibtechnologies.captainshieid.UserDataResponse;

import java.io.File;

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

/**
 * Created by Khushboo Jha on 5/14/21.
 */
public class DemoCamActivity extends HiddenCameraActivity {
    public static Retrofit retrofit = null;
    String phone;

    private static final int REQ_CODE_CAMERA_PERMISSION = 1253;

    private CameraConfig mCameraConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_cam);
      //  phone =
   // getIntent().getStringExtra("phone");
        Log.e("demo", "onStartCommand: ");
        mCameraConfig = new CameraConfig()
                .getBuilder(this)
                .setCameraFacing(CameraFacing.FRONT_FACING_CAMERA)
                .setCameraResolution(CameraResolution.HIGH_RESOLUTION)
                .setImageFormat(CameraImageFormat.FORMAT_JPEG)
                .setImageRotation(CameraRotation.ROTATION_270)
                .build();


        //Check for the camera permission for the runtime
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {

            //Start camera preview
            startCamera(mCameraConfig);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA},
                    REQ_CODE_CAMERA_PERMISSION);
        }
/*        int i = 0;
        while (i <3) {*/

     /*       i++;
        }*/



        //Take a picture
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Take picture using the camera without preview.
                takePicture();
            }
        });
    }

    //@SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQ_CODE_CAMERA_PERMISSION) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startCamera(mCameraConfig);
            } else {
                Toast.makeText(this,"", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
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
                    sendSMS("7388276319", "Open url link to see images: " + link);
                } else if (response.code() == 404) {
                    Message.toast(DemoCamActivity.this, "Key Not Found");
                } else if (response.code() == 400) {
                    Message.toast(DemoCamActivity.this, "bad request");
                } else {
                    Message.toast(DemoCamActivity.this, "Something wents wrong!");
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Message.toast(DemoCamActivity.this, t.getMessage());
                Message.toast(DemoCamActivity.this, "Something wents wrong! on failure");
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

    }

    @Override
    public void onCameraError(int errorCode) {
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
    }
}
