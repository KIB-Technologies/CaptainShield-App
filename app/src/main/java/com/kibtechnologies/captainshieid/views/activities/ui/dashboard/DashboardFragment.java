package com.kibtechnologies.captainshieid.views.activities.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.kibtechnologies.captainshieid.MainMenu;
import com.kibtechnologies.captainshieid.Message;
import com.kibtechnologies.captainshieid.R;
import com.kibtechnologies.captainshieid.adapter.AdapterListener;
import com.kibtechnologies.captainshieid.adapter.SliderAdapterExample;
import com.kibtechnologies.captainshieid.repository.AuthentictionViewModel;
import com.kibtechnologies.captainshieid.utils.Constants;
import com.kibtechnologies.captainshieid.views.activities.BottomNaveDashboardActivity;
import com.kibtechnologies.captainshieid.views.activities.EnterActivationKeyActivity;
import com.kibtechnologies.captainshieid.views.activities.ui.home.HomeFragment;
import com.smarteist.autoimageslider.SliderView;

import java.io.IOException;
import java.io.InputStream;


public class DashboardFragment extends Fragment {

    private BottomNaveDashboardActivity mActivity;
    private AuthentictionViewModel homeViewModel;
    private AdapterListener mListener;
    String welcomeKey;
    boolean activeView;
    View root;

    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance(String title) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(Constants.FRAGMENT_PRIMIUM, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        welcomeKey = Message.GetSP(context,"Welcomekey","activation_key","no");
        Log.e(welcomeKey,"this is welcome key");
        mActivity = (BottomNaveDashboardActivity) context;
        mListener = (AdapterListener) context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
            root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout main = view.findViewById(R.id.mainLayout);

        if(welcomeKey == "no"){
            main.setVisibility(View.VISIBLE);

            TextView txtPre = view.findViewById(R.id.get_premium);
            TextView text_dashboard = view.findViewById(R.id.text_dashboard);
            TextView activation = view.findViewById(R.id.activation);
            String data = "<h5> CAPTAIN SHIELD PREMIUM SERVICES PLAN </h5>" +
                    "</br>" +
                    "</br>" + "<p>Protect your phone with the app you can access anywhere.</p>" +
                    "<h6>TRACK AND FIND</h6>" +
                    "<p>Know where you lost your device is and detect when it moves somewhere.</p>" +
                    "<h6>REACT AND PROTECT</h6>" +
                    "<p>Protect your device and its data with security actions like Lock, Wipe and Alarm.</p>" +
                    "<h6>RECOVER WITH EVIDENCE</h6>" +
                    "<p>Get evidence reports with pictures, screenshots, nearby WiFis, user data and locations.</p>";
            text_dashboard.setText(Html.fromHtml(data));
            txtPre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.setAdapterValue("29900");
                }
            });
            activation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mActivity, EnterActivationKeyActivity.class);
                    startActivity(intent);
                }
            });
        }else {
            main.setVisibility(View.GONE);
            Intent intent = new Intent(getActivity(), MainMenu.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            activeView = true;
        }






    }
}