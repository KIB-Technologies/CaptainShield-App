package com.kibtechnologies.captainshieid.views.activities.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.kibtechnologies.captainshieid.R;
import com.kibtechnologies.captainshieid.adapter.SliderAdapterExample;
import com.kibtechnologies.captainshieid.model.BannerResponse;
import com.kibtechnologies.captainshieid.model.SliderItem;
import com.kibtechnologies.captainshieid.repository.AuthenticationViewModelFactory;
import com.kibtechnologies.captainshieid.repository.AuthentictionViewModel;
import com.kibtechnologies.captainshieid.utils.Constants;
import com.kibtechnologies.captainshieid.views.activities.AllDTHActivity;
import com.kibtechnologies.captainshieid.views.activities.BottomNaveDashboardActivity;
import com.kibtechnologies.captainshieid.views.activities.MobileRechargeActivity;
import com.kibtechnologies.captainshieid.views.components.CustomProgressDialog;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements View.OnClickListener {
    private BottomNaveDashboardActivity mActivity;
    private AuthentictionViewModel homeViewModel;
    SliderView sliderView;
    private SliderAdapterExample adapter;
    private CardView mobileRecharge, billPayRecharge;
    private CustomProgressDialog mProgressDialog;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String title) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(Constants.FRAGMENT_HOME, title);
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
        mActivity = (BottomNaveDashboardActivity) context;


    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this,new  AuthenticationViewModelFactory()).get(AuthentictionViewModel.class);
        return  inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sliderView = view.findViewById(R.id.imageSlider);
        adapter = new SliderAdapterExample(mActivity);
        sliderView.setSliderAdapter(adapter);
     /*   sliderView.setIndicatorAnimation(IndicatorAnimationType.SCALE_DOWN); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();*/

        mobileRecharge = view.findViewById(R.id.recharge_card);
        mobileRecharge.setOnClickListener(this);
        billPayRecharge = view.findViewById(R.id.paybills_card);
        billPayRecharge.setOnClickListener(this);




        homeViewModel.getResult().removeObservers(mActivity);
        homeViewModel.getResult().observe(mActivity, new Observer<BannerResponse>() {
            @Override
            public void onChanged(BannerResponse bannerResponse) {
                showProgressbar(false);
                Log.e("home", "onChanged: " + bannerResponse.getResponse_code() + " " +
                        bannerResponse.getResponse_data().getBanners().toString());
                List<SliderItem> sliderItems =  bannerResponse.getResponse_data().getBanners();

                adapter.renewItems(sliderItems);
            }
        });
        homeViewModel.getBanners();
        showProgressbar(true);




        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });
       /* homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
                addNewItem();

            }
        });*/
    }

    public void renewItems(List<SliderItem> list) {
        /*List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data
        for (int i = 0; i < 5; i++) {
            SliderItem sliderItem = new SliderItem();
            sliderItem.setDescription("Slider Item " + i);
            if (i % 2 == 0) {
                sliderItem.setImageUrl("https://tineye.com/images/widgets/mona.jpg");
            } else {
                sliderItem.setImageUrl("https://tineye.com/images/widgets/mona.jpg");
            }
            sliderItemList.add(sliderItem);
        }*/

    }

    public void removeLastItem() {
        if (adapter.getCount() - 1 >= 0)
            adapter.deleteItem(adapter.getCount() - 1);
    }

    public void addNewItem() {
       /* SliderItem sliderItem = new SliderItem();
        sliderItem.setDescription("Slider Item Added Manually");
        sliderItem.setImageUrl("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
        adapter.addItem(sliderItem);*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.recharge_card:
                Intent intent = new Intent(mActivity, MobileRechargeActivity.class);
                startActivity(intent);
                break;

            case R.id.paybills_card:
                Intent intent1 = new Intent(mActivity, AllDTHActivity.class);
                startActivity(intent1);
                break;
        }
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