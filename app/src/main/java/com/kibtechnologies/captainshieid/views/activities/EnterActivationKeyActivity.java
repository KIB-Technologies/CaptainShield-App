package com.kibtechnologies.captainshieid.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.kibtechnologies.captainshieid.MainMenu;
import com.kibtechnologies.captainshieid.R;
import com.kibtechnologies.captainshieid.model.ActivationResponse;
import com.kibtechnologies.captainshieid.repository.AuthenticationViewModelFactory;
import com.kibtechnologies.captainshieid.repository.AuthenticationWithTokenFactory;
import com.kibtechnologies.captainshieid.repository.AuthentictionViewModel;
import com.kibtechnologies.captainshieid.utils.PreferenceUtils;
import com.kibtechnologies.captainshieid.utils.Util;
import com.kibtechnologies.captainshieid.views.components.CustomProgressDialog;

import java.util.HashMap;
import java.util.Map;

public class EnterActivationKeyActivity extends AppCompatActivity {
    private AuthentictionViewModel model;
    private CustomProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_activation_key);
        TextInputEditText text = findViewById(R.id.et_login);
        TextView error = findViewById(R.id.error);
        TextView submit = findViewById(R.id.checkPlan);
        model = new ViewModelProvider(this, new AuthenticationViewModelFactory()).get(AuthentictionViewModel.class);
        model.getCheckActive().removeObservers(this);
        model.getCheckActive().observe(this, new Observer<ActivationResponse>() {
            @Override
            public void onChanged(ActivationResponse activationResponse) {
                showProgressbar(false);
                if (activationResponse.getResponse_code() == 200){

                    Intent intent = new Intent(EnterActivationKeyActivity.this, MainMenu.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    error.setText("Something went wrong, Please enter valid key.");

                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> body = new HashMap<>();
                body.put("key", text.getText().toString());
                String token = PreferenceUtils.getInstance(EnterActivationKeyActivity.this).getToken();
                if (Util.isTextValid(text.getText().toString())) {
                    model.getActiveStatus("bearer "+token,body);
                    showProgressbar(true);
                    //error.setVisibility(View.GONE);

                } else {
                    text.setError("Please enter your correct number");
                    //error.setVisibility(View.VISIBLE);

                }


            }
        });
    }

    public void showProgressbar(boolean status) {
        if (status) {
            // phoneNumber.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(mActivity,R.drawable.bg_progress_bar),
            //  null);
            // phoneNumber.startAnimation();

            if (mProgressDialog == null) {
                mProgressDialog = new CustomProgressDialog(this);
                mProgressDialog.setCancelable(false);
                mProgressDialog.getWindow().setGravity(Gravity.CENTER);
            }

            if (mProgressDialog != null && mProgressDialog.getWindow() != null && !mProgressDialog.isShowing() && !this.isFinishing()) {
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