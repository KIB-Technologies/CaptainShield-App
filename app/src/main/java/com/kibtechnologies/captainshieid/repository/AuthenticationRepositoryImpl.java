package com.kibtechnologies.captainshieid.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.kibtechnologies.captainshieid.model.ActivationResponse;
import com.kibtechnologies.captainshieid.model.AllOpratoersResponse;
import com.kibtechnologies.captainshieid.model.BannerResponse;
import com.kibtechnologies.captainshieid.model.OTPResponse;
import com.kibtechnologies.captainshieid.model.ProfileResponse;
import com.kibtechnologies.captainshieid.model.RechargePannelUser;
import com.kibtechnologies.captainshieid.model.RechargePlanResponse;
import com.kibtechnologies.captainshieid.service.APIResult;
import com.kibtechnologies.captainshieid.service.AppService;
import com.kibtechnologies.captainshieid.service.RxSchedulers;

import java.util.Map;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Khushboo Jha on 6/1/21.
 */
public class AuthenticationRepositoryImpl implements AuthenticationRepository {
    private AppService mService;

    public AuthenticationRepositoryImpl(AppService mService) {
        this.mService = mService;
    }

    @SuppressLint("CheckResult")
    @Override
    public void setSignIn(Map<String, Object> body, MutableLiveData<OTPResponse> response) {
        mService.getUserAPI().userSigIn(body).observeOn(RxSchedulers.ui())
                .subscribeOn(RxSchedulers.worker())
                .unsubscribeOn(RxSchedulers.worker())
                .subscribe(v -> response.setValue((v)),
                   throwable -> Log.e("Error", "Oops, hit an error", throwable));

    }

    @SuppressLint("CheckResult")
    @Override
    public void sendOtp(Map<String, Object> body, MutableLiveData<OTPResponse> response) {
        mService.getUserAPI().sendOtp(body).observeOn(RxSchedulers.ui())
                .subscribeOn(RxSchedulers.worker())
                .unsubscribeOn(RxSchedulers.worker())
                .subscribe(v -> response.setValue((v)),
                        throwable -> Log.e("Error", "Oops, hit an error", throwable));
    }

    @SuppressLint("CheckResult")
    @Override
    public void getBanners(MutableLiveData<BannerResponse> response) {
        mService.getUserAPI().getBanners().observeOn(RxSchedulers.ui())
                .subscribeOn(RxSchedulers.worker())
                .unsubscribeOn(RxSchedulers.worker())
                .subscribe(v -> response.setValue((v)),
                        throwable -> Log.e("Error", "Oops, hit an error", throwable));
    }

    @SuppressLint("CheckResult")
    @Override
    public void getOperators(String mobile, String num, MutableLiveData<RechargePannelUser> response) {
        mService.getUserAPI().getRechargeOprators(mobile,num).observeOn(RxSchedulers.ui())
                .subscribeOn(RxSchedulers.worker())
                .unsubscribeOn(RxSchedulers.worker())
                .subscribe(v -> response.setValue((v)),
                        throwable -> Log.e("Error", "Oops, hit an error", throwable));
    }



    @SuppressLint("CheckResult")
    @Override
    public void getAllOperators(String type,MutableLiveData<AllOpratoersResponse> response) {
        mService.getUserAPI().getAllOperators(type).observeOn(RxSchedulers.ui())
                .subscribeOn(RxSchedulers.worker())
                .unsubscribeOn(RxSchedulers.worker())
                .subscribe(v -> response.setValue((v)),
                        throwable -> Log.e("Error", "Oops, hit an error", throwable));
    }

    @SuppressLint("CheckResult")
    @Override
    public void getAllPlan(String type, String code, String circleCode, MutableLiveData<RechargePlanResponse> response) {
        mService.getUserAPI().checkAllPlan(type, code, circleCode).observeOn(RxSchedulers.ui())
                .subscribeOn(RxSchedulers.worker())
                .unsubscribeOn(RxSchedulers.worker())
                .subscribe(v -> response.setValue((v)),
                        throwable -> Log.e("Error", "Oops, hit an error", throwable));
    }

    @SuppressLint("CheckResult")
    @Override
    public void getProfile(String token,MutableLiveData<ProfileResponse> response) {
              mService.getUserAPI().getProfile(token).observeOn(RxSchedulers.ui())
                .subscribeOn(RxSchedulers.worker())
                .unsubscribeOn(RxSchedulers.worker())
                .subscribe(v -> response.setValue((v)),
                        throwable -> Log.e("Error", "Oops, hit an error", throwable));
    }

    @SuppressLint("CheckResult")
    @Override
    public void checkActive(String token, Map<String, Object > body ,MutableLiveData<ActivationResponse> response) {
        mService.getUserAPI().checkActivationKey(token, body).observeOn(RxSchedulers.ui())
                .subscribeOn(RxSchedulers.worker())
                .unsubscribeOn(RxSchedulers.worker())
                .subscribe(v -> response.setValue((v)),
                        throwable -> Log.e("Error", "Oops, hit an error", throwable));
    }
}
