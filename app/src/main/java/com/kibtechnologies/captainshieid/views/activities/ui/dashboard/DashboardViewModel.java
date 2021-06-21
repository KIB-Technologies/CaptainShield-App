package com.kibtechnologies.captainshieid.views.activities.ui.dashboard;

import android.content.res.AssetManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("<h5> CAPTAIN SHIELD PREMIUM SERVICES PLANS FOR COMPLETELY SECURE YOUR DEVICE </h5>" +
                "</br>" +
                "</br>" + " <p>Protect your phone with the app you can access anywhere.</p>" +
                "<h6>TRACK AND FIND</h6>" +
                "</br> <p>Know where you lost your device is and detect when it moves somewhere.</p>" +
                "<h6>REACT AND PROTECT</h6>" +
                "</br> <p>Protect your device and its data with security actions like Lock, Wipe and Alarm.</p>" +
                "<h6>RECOVER WITH EVIDENCE</h6>"+
                "</br> <p>Get evidence reports with pictures, screenshots, nearby WiFis, user data and locations.</p>");
    }

    public LiveData<String> getText() {
        return mText;
    }
}