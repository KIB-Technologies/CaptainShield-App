package com.kibtechnologies.captainshieid.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kibtechnologies.captainshieid.model.ActivationResponse;
import com.kibtechnologies.captainshieid.model.AllOpratoersResponse;
import com.kibtechnologies.captainshieid.model.BannerResponse;
import com.kibtechnologies.captainshieid.model.GenratedKey;
import com.kibtechnologies.captainshieid.model.OTPResponse;
import com.kibtechnologies.captainshieid.model.PremiumResponse;
import com.kibtechnologies.captainshieid.model.ProfileResponse;
import com.kibtechnologies.captainshieid.model.RechargePannelUser;
import com.kibtechnologies.captainshieid.model.RechargePlanResponse;
import com.kibtechnologies.captainshieid.model.SecNumResponse;
import com.kibtechnologies.captainshieid.service.APIResult;

import java.util.Map;

/**
 * Created by Khushboo Jha on 6/2/21.
 */
public class AuthentictionViewModel extends ViewModel {

    private AuthenticationRepository repository;
    private MutableLiveData<OTPResponse> respnse = new MutableLiveData<>();
    private MutableLiveData<BannerResponse> bannerResult = new MutableLiveData<>();
    private MutableLiveData<RechargePannelUser> optrsResults = new MutableLiveData<>();
    private MutableLiveData<AllOpratoersResponse> allOperatorsResult = new MutableLiveData<>();
    private MutableLiveData<RechargePlanResponse> allPlane = new MutableLiveData<>();
    private MutableLiveData<ActivationResponse> checkActive = new MutableLiveData<>();
    private MutableLiveData<ProfileResponse> profileResult = new MutableLiveData<>();
    private MutableLiveData<PremiumResponse> checkResult = new MutableLiveData<>();
    private MutableLiveData<GenratedKey> genratedKeyResult = new MutableLiveData<>();
    private MutableLiveData<SecNumResponse> updateSecNumberResult = new MutableLiveData<>();
//    private MutableLiveData<TransferdKey> getTransferedKeyResult = new MutableLiveData<>();

    public AuthentictionViewModel(AuthenticationRepository repository) {
        this.repository = repository;
    }

    public LiveData<PremiumResponse> getPremiumResult() {
        return checkResult;
    }

    public LiveData<GenratedKey> getGenratedKeyResult() {
        return genratedKeyResult;
    }

    public LiveData<BannerResponse> getResult() {
        return bannerResult;
    }

    public LiveData<OTPResponse> getRespnse() {
        return respnse;
    }

    public LiveData<RechargePannelUser> getOptrsResults() {
        return optrsResults;
    }

    public LiveData<AllOpratoersResponse> getAllOperatorsResults() {
        return allOperatorsResult;
    }

    public LiveData<RechargePlanResponse> getAllPlane() {
        return allPlane;
    }

    public LiveData<ActivationResponse> getCheckActive() {
        return checkActive;
    }

    public LiveData<SecNumResponse> updateSecNumber() {
        return updateSecNumberResult;
    }


    public LiveData<ProfileResponse> getProfileResult() {
        return profileResult;
    }

    public void login(Map<String, Object> body) {
        repository.setSignIn(body, respnse);
    }

    public void send(Map<String, Object> body) {
        repository.sendOtp(body, respnse);
    }

    public void getBanners() {
        repository.getBanners(bannerResult);
    }

    public void getRechargeOpts(String type, String num) {
        repository.getOperators(type, num, optrsResults);
    }

    public void getAllOrators(String type) {
        repository.getAllOperators(type, allOperatorsResult);
    }

    public void getRechargePlan(String type, String code, String circleCode) {
        repository.getAllPlan(type, code, circleCode, allPlane);

    }

    public void getActiveStatus(String token, Map<String, Object> body) {
        repository.checkActive(token, body, checkActive);
    }

    public void getUserProfile(String token) {
        repository.getProfile(token, profileResult);
    }

    public void checkPremium(String token) {
        repository.checkPremiumSub(token, checkResult);
    }

    public void getGenerateKey(String token, String paymentID) {
        repository.getGeneratedKey(token, paymentID, genratedKeyResult);
    }

    public void updateSecNumber(String token, Map<String, Object> body) {
        repository.updateSecNumber(token, body,updateSecNumberResult);
    }
}
