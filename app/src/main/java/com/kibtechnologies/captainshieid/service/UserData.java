package com.kibtechnologies.captainshieid.service;

import com.kibtechnologies.captainshieid.UserDataResponse;
import com.kibtechnologies.captainshieid.model.ActivationResponse;
import com.kibtechnologies.captainshieid.model.AllOpratoersResponse;
import com.kibtechnologies.captainshieid.model.BannerResponse;
import com.kibtechnologies.captainshieid.model.OTPResponse;
import com.kibtechnologies.captainshieid.model.ProfileResponse;
import com.kibtechnologies.captainshieid.model.RechargePannelUser;
import com.kibtechnologies.captainshieid.model.RechargePlanResponse;

import java.io.Reader;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserData {
    @Multipart
    @POST("users/upload")
    Observable<UserDataResponse> sendData(@Part MultipartBody.Part file,@Part("phone") RequestBody phone,@Part("trackid") RequestBody trackid);

    //login api users/sendOTP
    @POST("/users/sendOTP")
    Observable<OTPResponse> userSigIn(@Body Map<String, Object> body);

    @POST("users/verifyOTP")
    Observable<OTPResponse> sendOtp(@Body Map<String, Object> body);

    @GET("banner")
    Observable<BannerResponse> getBanners();

    //Mobile Recharge
    @GET("recharge/operators/{operatorType}/{number}")
    Observable<RechargePannelUser> getRechargeOprators(@Path(value = "operatorType") String operatorType,@Path(value = "number") String number );

    //get All operators
    @GET("recharge/operators/{operatorType}")
    Observable<AllOpratoersResponse> getAllOperators(@Path(value = "operatorType") String operatorType);

    @GET("recharge/plan/{operatorType}/{operator_code}/{circle_code}")
    Observable<RechargePlanResponse> checkAllPlan(@Path(value = "operatorType") String operatorType,
                                                  @Path(value = "operator_code") String operator_code,
    @Path(value = "circle_code") String circle_code);

    @Headers("Content-type: application/json")
    @GET("users/profile")
    Observable<ProfileResponse> getProfile(@Header("Authorization") String token);

    @Headers("Content-type: application/json")
    @PUT("users/transfer")
    Observable<ActivationResponse> checkActivationKey(@Header("Authorization") String token, @Body Map<String, Object> body);

}
