package com.kibtechnologies.captainshieid.presenters;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Headers;
import retrofit2.http.Part;

/**
 * Created by Khushboo Jha on 5/16/21.
 */
public interface SendHiddenImagePresenter {
    void sendData(File file, @Part("phone") String phone, @Part("trackid") String trackid, @Part("token") String token);
}
