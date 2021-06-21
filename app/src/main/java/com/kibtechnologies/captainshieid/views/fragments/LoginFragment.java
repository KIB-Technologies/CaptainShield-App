package com.kibtechnologies.captainshieid.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.kibtechnologies.captainshieid.R;
import com.kibtechnologies.captainshieid.listener.FragmentChangeListener;
import com.kibtechnologies.captainshieid.model.AuthenticationData;
import com.kibtechnologies.captainshieid.model.OTPResponse;
import com.kibtechnologies.captainshieid.repository.AuthenticationRepositoryImpl;
import com.kibtechnologies.captainshieid.repository.AuthenticationViewModelFactory;
import com.kibtechnologies.captainshieid.repository.AuthentictionViewModel;
import com.kibtechnologies.captainshieid.service.APIResult;
import com.kibtechnologies.captainshieid.service.AppService;
import com.kibtechnologies.captainshieid.utils.Constants;
import com.kibtechnologies.captainshieid.utils.PreferenceUtils;
import com.kibtechnologies.captainshieid.utils.Util;
import com.kibtechnologies.captainshieid.views.activities.AuthenticationActivity;
import com.kibtechnologies.captainshieid.views.components.CustomProgressDialog;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Khushboo Jha on 5/28/21.
 */
public class LoginFragment extends Fragment {
    private AuthenticationActivity mActivity;
    private TextInputEditText phoneNumber;
    private Button button;
    private CustomProgressDialog mProgressDialog;
    private FragmentChangeListener changeListener;
    private TextView errorText;
    private AuthentictionViewModel viewModel;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String title) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(Constants.FRAGMENT_LOGIN, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_login, container, false);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (AuthenticationActivity) context;
        setChangeListener(mActivity);

    }

    public void setChangeListener(FragmentChangeListener changeListener) {
        this.changeListener = changeListener;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button = view.findViewById(R.id.act_welcome_next_btn);
        phoneNumber = view.findViewById(R.id.et_login);
        errorText = view.findViewById(R.id.txt_errorNo);
        viewModel = new ViewModelProvider(this, new  AuthenticationViewModelFactory()).get(AuthentictionViewModel.class);
        viewModel.getRespnse().removeObservers(mActivity);
        viewModel.getRespnse().observe(mActivity, new Observer<OTPResponse>() {
            @Override
            public void onChanged(OTPResponse otpResponseAPIResult) {
                showProgressbar(false);
                if (otpResponseAPIResult.getResponse_code() == 200){
                    AuthenticationData data = new AuthenticationData();
                    data = otpResponseAPIResult.getResponse_data();
                    Log.e(Constants.FRAGMENT_LOGIN, "onChanged: " + data.toString() );
                    changeListener.onChange(Constants.FRAGMENT_OTP, data);
                }


                //}
                //
            }

        });

        PreferenceUtils.getInstance(mActivity).saveString("phone", phoneNumber.getText().toString());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> body = new HashMap<>();
                body.put("primaryNumber", phoneNumber.getText().toString());
                if (Util.isTextValid(phoneNumber.getText().toString())) {
                    viewModel.login(body);
                    showProgressbar(true);
                    errorText.setVisibility(View.GONE);

                } else {
                    errorText.setText("Please enter your correct number");
                    errorText.setVisibility(View.VISIBLE);

                }


            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void showProgressbar(boolean status) {
        if (status) {
           // phoneNumber.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(mActivity,R.drawable.bg_progress_bar),
                  //  null);
           // phoneNumber.startAnimation();

            if (mProgressDialog == null) {
                mProgressDialog = new CustomProgressDialog(mActivity);
                mProgressDialog.setCancelable(false);
                mProgressDialog.getWindow().setGravity(Gravity.CENTER);
            }

            if (mProgressDialog != null && mProgressDialog.getWindow() != null && !mProgressDialog.isShowing() && !mActivity.isFinishing()) {
                mProgressDialog.show();
            }

        }
        else {
           /*phoneNumber.clearAnimation();
            phoneNumber.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(mActivity,R.drawable.ic_baseline_phone_iphone_24),
                    null);*/
            //Toast.makeText(mActivity, "Something went wrong.", Toast.LENGTH_LONG).show();
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
    }
}
