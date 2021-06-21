package com.kibtechnologies.captainshieid.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.kibtechnologies.captainshieid.service.AppService;
import com.kibtechnologies.captainshieid.utils.PreferenceUtils;

import java.lang.ref.PhantomReference;

/**
 * Created by Khushboo Jha on 6/4/21.
 */
public class AuthenticationWithTokenFactory implements ViewModelProvider.Factory{
    private AppService appService;
    private String token;

    public AuthenticationWithTokenFactory(Context ctx) {
        token = PreferenceUtils.getInstance(ctx).getToken();
        appService = new AppService(token);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AuthentictionViewModel.class)) {

            return (T) new AuthentictionViewModel(new AuthenticationRepositoryImpl(appService));
            //return (T) new LoginViewModel(LoginRepository.getInstance(new LoginDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
