package com.kibtechnologies.captainshieid.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.kibtechnologies.captainshieid.service.AppService;

/**
 * Created by Khushboo Jha on 6/2/21.
 */
public class AuthenticationViewModelFactory implements ViewModelProvider.Factory {
    private AppService mService;

    public AuthenticationViewModelFactory() {
        mService = new AppService() ;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AuthentictionViewModel.class)) {

            return (T) new AuthentictionViewModel(new AuthenticationRepositoryImpl(mService));
            //return (T) new LoginViewModel(LoginRepository.getInstance(new LoginDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
