package com.kibtechnologies.captainshieid.service;

import com.kibtechnologies.captainshieid.UserDataResponse;

import java.io.Reader;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserData {
    @Multipart
    @POST("users/upload")
    Observable<UserDataResponse> sendData(@Part MultipartBody.Part file, @Part("phone") RequestBody phone);
}
