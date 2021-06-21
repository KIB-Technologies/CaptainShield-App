package com.kibtechnologies.captainshieid.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kibtechnologies.captainshieid.R;
import com.kibtechnologies.captainshieid.adapter.AdapterListener;
import com.kibtechnologies.captainshieid.adapter.AllOperatorsAdapter;
import com.kibtechnologies.captainshieid.model.AllOpratoersResponse;
import com.kibtechnologies.captainshieid.model.RechargePannelUser;
import com.kibtechnologies.captainshieid.repository.AuthenticationViewModelFactory;
import com.kibtechnologies.captainshieid.repository.AuthentictionViewModel;
import com.kibtechnologies.captainshieid.utils.PreferenceUtils;
import com.kibtechnologies.captainshieid.views.components.CustomProgressDialog;

public class MobileRechargeActivity extends AppCompatActivity implements AdapterListener {
    private LinearLayout layoutHeader, layoutNext, layoutOprators;
    private AuthentictionViewModel model;
    private TextView opratorName;
    private CustomProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_recharge);
        TextInputEditText enterPhone = findViewById(R.id.et_login);
        TextInputLayout textInputLayout = findViewById(R.id.rechargeNo_id);
        layoutOprators = findViewById(R.id.recharge_operators);
        opratorName = findViewById(R.id.change_oprators);
        layoutNext = findViewById(R.id.btn_next);
        model = new ViewModelProvider(this, new AuthenticationViewModelFactory()).get(AuthentictionViewModel.class);
        model.getOptrsResults().removeObservers(this);
        model.getOptrsResults().observe(this, new Observer<RechargePannelUser>() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onChanged(RechargePannelUser rechargePannelUser) {
                if (rechargePannelUser.getStatus().equalsIgnoreCase("SUCCESS"))
                layoutNext.setVisibility(View.GONE);
                enterPhone.setFocusable(false);
                enterPhone.setEnabled(false);
                layoutOprators.setVisibility(View.VISIBLE);
                PreferenceUtils.getInstance(MobileRechargeActivity.this).saveString(PreferenceUtils.Key.NUMBER_OPERATORS.name(), rechargePannelUser.getOperator_name());
                PreferenceUtils.getInstance(MobileRechargeActivity.this).saveString(PreferenceUtils.Key.CODE_OPERATORS.name(), rechargePannelUser.getOperator_code());
                PreferenceUtils.getInstance(MobileRechargeActivity.this).saveString(PreferenceUtils.Key.CIRCLE_CODE_OPERATORS.name(), rechargePannelUser.getCircle_code());

                opratorName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        model.getAllOperatorsResults().removeObservers(MobileRechargeActivity.this);
                        model.getAllOperatorsResults().observe(MobileRechargeActivity.this, new Observer<AllOpratoersResponse>() {
                            @Override
                            public void onChanged(AllOpratoersResponse allOpratoersResponse) {
                                showProgressbar(false);
                                RecyclerView recyclerView = findViewById(R.id.recycler_view);
                                LinearLayoutManager mLayoutManager = new LinearLayoutManager(MobileRechargeActivity.this);
                                recyclerView.setLayoutManager(mLayoutManager);
                                AllOperatorsAdapter adapter = new AllOperatorsAdapter(MobileRechargeActivity.this, MobileRechargeActivity.this);
                                recyclerView.setAdapter(adapter);
                                adapter.addOperators(allOpratoersResponse.getResponse_data().getOperators());

                            }
                        });
                        PreferenceUtils.getInstance(MobileRechargeActivity.this).saveString(PreferenceUtils.Key.TYPE_OPERATORS.name(), "mobile");
                        model.getAllOrators("mobile");
                        showProgressbar(true);
                    }
                });
                opratorName.setText(PreferenceUtils.getInstance(MobileRechargeActivity.this).getString(PreferenceUtils.Key.NUMBER_OPERATORS.name(), ""));
            }
        });


        TextView checkPlan = findViewById(R.id.checkPlan);
        checkPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MobileRechargeActivity.this, ChcekPlanActivity.class);
                startActivity(intent);
            }
        });


        layoutNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!enterPhone.getText().toString().isEmpty()) {
                    // layoutNext.setVisibility(View.GONE);
                    model.getRechargeOpts("mobile", enterPhone.getText().toString());
                } else {
                    enterPhone.setError("Please enter Valid Number.");
                }
            }
        });

    }

    @Override
    public void setAdapterValue(String value) {
        opratorName.setText(value);
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