package com.kibtechnologies.captainshieid.views.activities.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.kibtechnologies.captainshieid.R;
import com.kibtechnologies.captainshieid.model.ActivationResponse;
import com.kibtechnologies.captainshieid.model.ProfileResponse;
import com.kibtechnologies.captainshieid.repository.AuthenticationViewModelFactory;
import com.kibtechnologies.captainshieid.repository.AuthentictionViewModel;
import com.kibtechnologies.captainshieid.utils.PreferenceUtils;
import com.kibtechnologies.captainshieid.utils.Util;
import com.kibtechnologies.captainshieid.views.activities.AuthenticationActivity;
import com.kibtechnologies.captainshieid.views.activities.BottomNaveDashboardActivity;
import com.kibtechnologies.captainshieid.views.activities.EnterActivationKeyActivity;
import com.kibtechnologies.captainshieid.views.components.CustomProgressDialog;

import java.util.HashMap;
import java.util.Map;


public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private AuthentictionViewModel model;
    private CustomProgressDialog mProgressDialog;
    private BottomNaveDashboardActivity mActivity;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView logout = root.findViewById(R.id.logout);
        final TextView keydata = root.findViewById(R.id.keyId);
        final TextView number = root.findViewById(R.id.user_no);
        final TextView premiumTag = root.findViewById(R.id.premiumUser);
        final TextView expiryDate = root.findViewById(R.id.keyexpiry);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceUtils.getInstance(getContext()).saveString(PreferenceUtils.Key.TOKEN.name(), null);
                Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                startActivity(intent);
                mActivity.finish();
            }
        });

     /*   notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
              //  textView.setText(s);
            }
        });*/
        number.setText(PreferenceUtils.getInstance(mActivity).getString(PreferenceUtils.Key.PHONE_NUMBER.name(),""));
        model = new ViewModelProvider(this, new AuthenticationViewModelFactory()).get(AuthentictionViewModel.class);
        model.getProfileResult().removeObservers(this);
        model.getProfileResult().observe(mActivity, new Observer<ProfileResponse>() {
            @Override
            public void onChanged(ProfileResponse profileResponse) {
               // showProgressbar(false);
                if (profileResponse.getResponse_code() == 200){
                    number.setText(profileResponse.getResponse_data().getPrimaryNumber());
                    if(profileResponse.premium == true){
                        premiumTag.setText("Premium User".toUpperCase());
                        keydata.setText("Activation key : "+ profileResponse.key);
                        expiryDate.setText("Activation Expire On : "+profileResponse.expiry);
                    }else{
                        premiumTag.setText("Get Premium Today!");
                        keydata.setText("Key : No Key Available");
                    }
                }
            }
        });
        String token = PreferenceUtils.getInstance(mActivity).getToken();
        model.getUserProfile("bearer "+token);
       // showProgressbar(true);
        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (BottomNaveDashboardActivity) context;
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