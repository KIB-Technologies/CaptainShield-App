package com.kibtechnologies.captainshieid.presenters.impl;

import android.content.SharedPreferences;
import android.util.Log;
import com.kibtechnologies.captainshieid.Message;
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
import retrofit2.http.Multipart;

import static android.content.Context.MODE_PRIVATE;

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
    public void sendData( File file, String phone, String trackid,String token) {
        Log.e("DataSend","Under Data Send on end point");
        RequestBody phonePart = RequestBody.create(MultipartBody.FORM, phone);
        Log.e("phoneNumber",phone);
        RequestBody idPart = RequestBody.create(MultipartBody.FORM, trackid);
        Log.e("trackID",trackid);
        RequestBody tokenPart = RequestBody.create(MultipartBody.FORM, token);
        Log.e("file", String.valueOf(file.getName()));
        RequestBody filePart = RequestBody.create(MediaType.parse("multipart.form-data"), file.getAbsoluteFile());
        MultipartBody.Part parts = MultipartBody.Part.createFormData("file", file.getName(), filePart);
        service.getUserAPI().sendData(parts,phonePart,idPart,tokenPart)
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
                        Log.e("onError in sendHidden",e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
