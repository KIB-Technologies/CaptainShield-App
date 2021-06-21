package com.kibtechnologies.captainshieid.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;

import com.kibtechnologies.captainshieid.R;
import com.kibtechnologies.captainshieid.adapter.AllOperatorsAdapter;
import com.kibtechnologies.captainshieid.adapter.RechargePlansAdapater;
import com.kibtechnologies.captainshieid.model.RechargePlanResponse;
import com.kibtechnologies.captainshieid.repository.AuthenticationViewModelFactory;
import com.kibtechnologies.captainshieid.repository.AuthentictionViewModel;
import com.kibtechnologies.captainshieid.utils.PreferenceUtils;
import com.kibtechnologies.captainshieid.views.components.CustomProgressDialog;

public class ChcekPlanActivity extends AppCompatActivity {
    private AuthentictionViewModel model;
    private String operatorCode, circleCode;
    private CustomProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chcek_plan);
        RechargePlansAdapater rechargePlansAdapater = new RechargePlansAdapater(this);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(rechargePlansAdapater);
        model = new ViewModelProvider(this, new AuthenticationViewModelFactory()).get(AuthentictionViewModel.class);
        model.getAllPlane().removeObservers(this);
        model.getAllPlane().observe(this, new Observer<RechargePlanResponse>() {
            @Override
            public void onChanged(RechargePlanResponse rechargePlanResponse) {
                showProgressbar(false);
                rechargePlansAdapater.addPlanDetails(rechargePlanResponse.getPlandetail());
            }
        });
        circleCode = PreferenceUtils.getInstance(this).getString(PreferenceUtils.Key.CIRCLE_CODE_OPERATORS.name(), "");
        operatorCode = PreferenceUtils.getInstance(this).getString(PreferenceUtils.Key.CODE_OPERATORS.name(), "");
        model.getRechargePlan("mobile",operatorCode,circleCode);
        showProgressbar(true);
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