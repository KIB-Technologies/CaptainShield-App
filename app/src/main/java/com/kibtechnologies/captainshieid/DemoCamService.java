package com.kibtechnologies.captainshieid;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.androidhiddencamera.CameraConfig;
import com.androidhiddencamera.CameraError;
import com.androidhiddencamera.HiddenCameraService;
import com.androidhiddencamera.HiddenCameraUtils;
import com.androidhiddencamera.config.CameraFacing;
import com.androidhiddencamera.config.CameraImageFormat;
import com.androidhiddencamera.config.CameraResolution;
import com.kibtechnologies.captainshieid.presenters.SendHiddenImagePresenter;
import com.kibtechnologies.captainshieid.presenters.impl.SendHiddenImagePresenterImpl;
import com.kibtechnologies.captainshieid.service.AppService;
import com.kibtechnologies.captainshieid.service.ResponseListener;
import com.kibtechnologies.captainshieid.service.UserData;
import com.kibtechnologies.captainshieid.utils.PreferenceUtils;
import com.kibtechnologies.captainshieid.views.activities.EnterActivationKeyActivity;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

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

import static android.widget.Toast.LENGTH_SHORT;

public class DemoCamService extends HiddenCameraService implements ResponseListener {
    public static Retrofit retrofit = null;
    String phone;
    private String trackid;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        phone = Message.GetSP(this, "sms_number", "smsNo", "9893065506");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {

            if (HiddenCameraUtils.canOverDrawOtherApps(this)) {
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

            } else {
                HiddenCameraUtils.openDrawOverPermissionSetting(this);
            }
        } else {
            //TODO Ask your parent activity for providing runtime permission
            Toast.makeText(this, "error cannot get permission", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Camera permission not available", LENGTH_SHORT).show();
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onImageCapture(File imageFile) {
        uploadImage(imageFile);
    }

    public void uploadImage(File rurl) {
        String token = PreferenceUtils.getInstance(DemoCamService.this).getToken();
        String bereartoken = "bearer"+ token;
        SendHiddenImagePresenter presenter = new SendHiddenImagePresenterImpl(new AppService(), this);
        trackid = PreferenceUtils.getInstance(this).getString(PreferenceUtils.KEY_TRACKID, "");
//        Message.toast(this, "track id == " + trackid);
        presenter.sendData(rurl, phone, trackid, bereartoken);
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


    @Override
    public void startProgress() {

    }

    @Override
    public void onResponse(UserDataResponse respReader, int requestCode) {
//        Message.toast(this,"under on response");
        String link = respReader.getLink();
        Log.e("response", "onResponse: " + link);
//            Message.toast(this, respReader.getMessage());
        sendSMS(phone, "Open url link to see images: " + link);
        PreferenceUtils.getInstance(this).saveString(PreferenceUtils.KEY_TRACKID, respReader.getTrackid());
        Log.e("trackID", " trackID" + respReader.getTrackid());
        if (!respReader.isStop()) {
            Log.e("Response", " boolean value " + respReader.isStop());
            Intent backgroundIntent = new Intent(getApplicationContext(), DemoCamService.class);
            backgroundIntent.putExtra("id", phone);
            startService(backgroundIntent);
        }

    }

    @Override
    public void stopProgress() {

    }

    @Override
    public void onError(Throwable t, Object... args) {
        this.stopProgress();
        Message.toast(DemoCamService.this, t.getMessage());
        Message.toast(DemoCamService.this, "Something wents wrong! on failure");
    }
}