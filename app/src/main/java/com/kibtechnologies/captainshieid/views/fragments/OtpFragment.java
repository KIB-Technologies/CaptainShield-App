package com.kibtechnologies.captainshieid.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.kibtechnologies.captainshieid.R;
import com.kibtechnologies.captainshieid.model.AuthenticationData;
import com.kibtechnologies.captainshieid.model.OTPResponse;
import com.kibtechnologies.captainshieid.repository.AuthenticationViewModelFactory;
import com.kibtechnologies.captainshieid.repository.AuthentictionViewModel;
import com.kibtechnologies.captainshieid.utils.Constants;
import com.kibtechnologies.captainshieid.utils.PreferenceUtils;
import com.kibtechnologies.captainshieid.utils.Util;
import com.kibtechnologies.captainshieid.views.activities.AuthenticationActivity;
import com.kibtechnologies.captainshieid.views.activities.BottomNaveDashboardActivity;
import com.kibtechnologies.captainshieid.views.components.OTPEnterView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Khushboo Jha on 5/28/21.
 */
public class OtpFragment extends Fragment {
    private AuthenticationActivity mActivity;
    private OTPEnterView mOtpView;
    private View view;
    private Button mButton;
    private AuthentictionViewModel viewModel;
    private static AuthenticationData authenticationData;
    private static String LOGIN_DATA = "login_data";
    private TextView errorText, txtHeading;


    public OtpFragment() {
        // Required empty public constructor
    }

    public static OtpFragment newInstance(String title, AuthenticationData data) {
        OtpFragment fragment = new OtpFragment();
        Bundle args = new Bundle();
        args.putString(Constants.FRAGMENT_OTP, title);
        args.putSerializable(LOGIN_DATA, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            authenticationData = (AuthenticationData) getArguments().getSerializable(LOGIN_DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_otp, container, false);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (AuthenticationActivity) context;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this, new AuthenticationViewModelFactory()).get(AuthentictionViewModel.class);
        mOtpView = view.findViewById(R.id.otp_entry_border);
        errorText = view.findViewById(R.id.txt_errorNo);
        txtHeading = view.findViewById(R.id.txt_heading);
        txtHeading.setText("Otp Sent your"  + " " + authenticationData.getPrimaryNumber() + " " +
                        "Please verify.");
        mButton = view.findViewById(R.id.act_welcome_next_btn);
        errorText.setVisibility(View.GONE);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = mOtpView.getText().toString();
                if (Util.isTextValid(otp)){
                    Map<String, Object> map = new HashMap<>();
                    map.put("otp",otp );
                    map.put("primaryNumber", authenticationData.getPrimaryNumber());
                    map.put("hash", authenticationData.getHash());
                    loginWithOtp(map);
                }else {
                    errorText.setVisibility(View.VISIBLE);
                    errorText.setText("Enter Correct OTP");
                }
            }
        });


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void loginWithOtp(Map<String, Object> body) {
        if (mOtpView.getText().toString() == null || mOtpView.getText().toString().trim().length() == 0) {
            return;
        }
        viewModel.getRespnse().removeObservers(mActivity);

        viewModel.getRespnse().observe(this, new Observer<OTPResponse>() {
            @Override
            public void onChanged(OTPResponse otpResponse) {
                if (otpResponse.getResponse_code() == 200) {
                    Log.i("DATA", "" + otpResponse.getResponse_data().toString());

                   PreferenceUtils.getInstance(mActivity).saveString(PreferenceUtils.Key.TOKEN.name(), otpResponse.getResponse_data().getToken());
                    PreferenceUtils.getInstance(mActivity).saveString(PreferenceUtils.Key.PHONE_NUMBER.name(), authenticationData.getPrimaryNumber());
                    Intent  intent = new Intent(mActivity, BottomNaveDashboardActivity.class);
                    startActivity(intent);
                    mActivity.finish();
                } else {
                     Toast.makeText(mActivity, otpResponse.getResponse_data().getMessage(),Toast.LENGTH_LONG);

                }
            }
        });
        viewModel.send(body);

    }


}
