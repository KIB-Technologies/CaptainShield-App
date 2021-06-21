package com.kibtechnologies.captainshieid.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

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

public class AllDTHActivity extends AppCompatActivity implements AdapterListener {
    private AuthentictionViewModel model;
    private CustomProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_d_t_h);
        model = new ViewModelProvider(this, new AuthenticationViewModelFactory()).get(AuthentictionViewModel.class);

                        model.getAllOperatorsResults().removeObservers(AllDTHActivity.this);
                        model.getAllOperatorsResults().observe(AllDTHActivity.this, new Observer<AllOpratoersResponse>() {
                            @Override
                            public void onChanged(AllOpratoersResponse allOpratoersResponse) {
                                showProgressbar(false);
                                RecyclerView recyclerView = findViewById(R.id.recycler_view);
                                LinearLayoutManager mLayoutManager = new LinearLayoutManager(AllDTHActivity.this);
                                recyclerView.setLayoutManager(mLayoutManager);
                                AllOperatorsAdapter adapter = new AllOperatorsAdapter(AllDTHActivity.this,AllDTHActivity.this);
                                recyclerView.setAdapter(adapter);
                                adapter.addOperators(allOpratoersResponse.getResponse_data().getOperators());

                            }
                        });
        PreferenceUtils.getInstance(this).saveString(PreferenceUtils.Key.TYPE_OPERATORS.name(), "dth");
                        model.getAllOrators("dth");
                        showProgressbar(true);
                    }


    @Override
    public void setAdapterValue(String value) {

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