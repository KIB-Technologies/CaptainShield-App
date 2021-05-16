package com.kibtechnologies.captainshieid;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserServices {

    @POST("users/activate")
    Call<UserResponse> checkKey(@Body UserRequest userRequest);
}
  