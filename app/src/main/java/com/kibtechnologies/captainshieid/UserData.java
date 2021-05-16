package com.kibtechnologies.captainshieid;

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
    Call<UserDataResponse> sendData(@Part MultipartBody.Part file, @Part("phone") RequestBody phone);
}
