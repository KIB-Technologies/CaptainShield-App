package com.kibtechnologies.captainshieid.presenters.impl;

import com.kibtechnologies.captainshieid.UserDataResponse;
import com.kibtechnologies.captainshieid.presenters.SendHiddenImagePresenter;
import com.kibtechnologies.captainshieid.service.AppService;
import com.kibtechnologies.captainshieid.service.ResponseListener;

import java.io.File;
import java.io.Reader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Khushboo Jha on 5/16/21.
 */
public class SendHiddenImagePresenterImpl implements SendHiddenImagePresenter {

    private AppService service;
    private ResponseListener mListener;

    public SendHiddenImagePresenterImpl(AppService service, ResponseListener mListener) {
        this.service = service;
        this.mListener = mListener;
    }


    @Override
    public void sendData(File file, String phone) {
        RequestBody phonePart = RequestBody.create(MultipartBody.FORM, phone);
        RequestBody filePart = RequestBody.create(MediaType.parse("multipart.form-data"), file.getAbsoluteFile());
        MultipartBody.Part parts = MultipartBody.Part.createFormData("file", file.getName(), filePart);
        service.getUserAPI().sendData(parts,phonePart)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.newThread())
                .subscribe(new DisposableObserver<UserDataResponse>() {
                    @Override
                    public void onNext(@NonNull UserDataResponse reader) {
                        mListener.onResponse(reader, 0);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
