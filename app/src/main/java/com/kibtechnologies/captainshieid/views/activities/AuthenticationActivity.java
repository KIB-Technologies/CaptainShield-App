package com.kibtechnologies.captainshieid.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.TextView;

import com.kibtechnologies.captainshieid.R;
import com.kibtechnologies.captainshieid.listener.FragmentChangeListener;
import com.kibtechnologies.captainshieid.model.AuthenticationData;
import com.kibtechnologies.captainshieid.utils.Constants;
import com.kibtechnologies.captainshieid.utils.Util;
import com.kibtechnologies.captainshieid.views.fragments.LoginFragment;
import com.kibtechnologies.captainshieid.views.fragments.OtpFragment;

public class AuthenticationActivity extends AppCompatActivity implements FragmentChangeListener {
    private TextView tvHeader;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        mFragmentManager = getSupportFragmentManager();
        init();
    }

    private void init() {
        addFragment(Constants.FRAGMENT_LOGIN, null);
    }

    private void addFragment(String fragmentTag, AuthenticationData m) {
        Fragment fragment = mFragmentManager.findFragmentByTag(fragmentTag);
        if (fragment == null) {
            fragment = getFragment(fragmentTag, m);
        }
        mFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.open_anim, R.anim.close_anim)
                .replace(R.id.fragment_container, fragment, fragmentTag)
                .commit();
    }
    private Fragment getFragment(String fragmentTag , AuthenticationData m) {
        Fragment fragment = null;
        switch (fragmentTag) {
            case  Constants.FRAGMENT_LOGIN:
                fragment = LoginFragment.newInstance(Constants.FRAGMENT_LOGIN);
                break;
            case Constants.FRAGMENT_OTP:
                fragment = OtpFragment.newInstance(Constants.FRAGMENT_OTP, m);
                break;
        }
        return fragment;
    }

    @Override
    public void onChange(String value, AuthenticationData obj) {
        addFragment(value,obj);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }
}