package com.kibtechnologies.captainshieid.repository;

import androidx.lifecycle.MutableLiveData;

import com.kibtechnologies.captainshieid.model.ActivationResponse;
import com.kibtechnologies.captainshieid.model.AllOpratoersResponse;
import com.kibtechnologies.captainshieid.model.BannerResponse;
import com.kibtechnologies.captainshieid.model.OTPResponse;
import com.kibtechnologies.captainshieid.model.ProfileResponse;
import com.kibtechnologies.captainshieid.model.RechargePannelUser;
import com.kibtechnologies.captainshieid.model.RechargePlanResponse;

import java.util.Map;

/**
 * Created by Khushboo Jha on 6/1/21.
 */
public interface AuthenticationRepository {

    void setSignIn(Map<String, Object > body , MutableLiveData<OTPResponse> response);
    void sendOtp(Map<String, Object > body , MutableLiveData<OTPResponse> response);
    void getBanners( MutableLiveData<BannerResponse> response);
    void getOperators(String mobile, String num, MutableLiveData<RechargePannelUser> response);
    void getAllOperators(String type, MutableLiveData<AllOpratoersResponse> response);
    void getAllPlan(String type,String code, String circleCode, MutableLiveData<RechargePlanResponse> response);
    void getProfile( String token,MutableLiveData<ProfileResponse> response);
    void checkActive(String token, Map<String, Object > body , MutableLiveData<ActivationResponse> response);

}
