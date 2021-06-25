package com.kibtechnologies.captainshieid.views.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kibtechnologies.captainshieid.LockScreen;
import com.kibtechnologies.captainshieid.MainMenu;
import com.kibtechnologies.captainshieid.Message;
import com.kibtechnologies.captainshieid.R;
import com.kibtechnologies.captainshieid.adapter.AdapterListener;
import com.kibtechnologies.captainshieid.model.GenratedKey;
import com.kibtechnologies.captainshieid.model.PremiumResponse;
import com.kibtechnologies.captainshieid.repository.AuthenticationViewModelFactory;
import com.kibtechnologies.captainshieid.repository.AuthentictionViewModel;
import com.kibtechnologies.captainshieid.utils.Constants;
import com.kibtechnologies.captainshieid.utils.PreferenceUtils;
import com.kibtechnologies.captainshieid.utils.Util;
import com.kibtechnologies.captainshieid.views.activities.ui.home.HomeFragment;
import com.kibtechnologies.captainshieid.views.fragments.LoginFragment;
import com.kibtechnologies.captainshieid.views.fragments.OtpFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public class BottomNaveDashboardActivity extends AppCompatActivity implements NavController.OnDestinationChangedListener, AdapterListener {
    Activity activity;
    String TAG = "BottomNaveDashboardActivity";
    String wantPermission = Manifest.permission.READ_PHONE_STATE;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private AuthentictionViewModel model;
    private TextView toolbarTitle;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        String number1 = Message.GetSP(BottomNaveDashboardActivity.this, "Welcomekey", "activation_key", "no");
//        Toast.makeText(activity, "Shared Pref is ==="+number1, Toast.LENGTH_LONG).show();
//        System.out.println("Shared pref value is === "+ number1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nave_dashboard);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        // check();

               /*
         To ensure faster loading of the Checkout form,
          call this method as early as possible in your checkout flow.
         */
        //Checkout.preload(getApplicationContext());

    }

    private void requestPermission(String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            Toast.makeText(activity, "Phone state permission allows us to get phone number. Please allow it for additional functionality.", Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(activity, new String[]{permission}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Message.SetSP(getBaseContext(), "Permission", "SMS", "ON");
                } else {
                    Message.SetSP(getBaseContext(), "Permission", "SMS", "OFF");
                }

                break;
        }
    }


    //check permission
    public void check() {
        ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.READ_SMS,
                        Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_PRECISE_PHONE_STATE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.SYSTEM_ALERT_WINDOW},
                PERMISSION_REQUEST_CODE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 0);
            }
        }

    }

    private FragmentManager mFragmentManager;

    private void addFragment(String fragmentTag) {
        Fragment fragment = mFragmentManager.findFragmentByTag(fragmentTag);
        if (fragment == null) {
            fragment = getFragment(fragmentTag);
        }
        mFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.open_anim, R.anim.close_anim)
                .replace(R.id.nav_host_fragment, fragment, fragmentTag)
                .commit();
    }

    private Fragment getFragment(String fragmentTag) {
        Fragment fragment = null;
        switch (fragmentTag) {
            case Constants.FRAGMENT_HOME:
                fragment = HomeFragment.newInstance(Constants.FRAGMENT_HOME);
                break;
           /* case Constants.FRAGMENT_OTP:
                fragment = OtpFragment.newInstance(Constants.FRAGMENT_OTP);
                break;*/
        }
        return fragment;
    }


    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
        switch (destination.getId()) {
            case R.id.navigation_notifications:
                addFragment(Constants.FRAGMENT_HOME);
                break;
        }
    }

    @Override
    public void setAdapterValue(String value) {
        startPayment(value);
    }

    public void startPayment(String amt) {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_m4pFBUjG8AKUXD");
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Prom You Tech");
            options.put("description", "Captain Shield Premium");
            options.put("send_sms_hash", true);
            options.put("allow_rotation", true);
            //You can omit the image option to fetch the image from dashboard
//            options.put("image", "https://thecaptainshield.com/img/applogo.png");
            options.put("currency", "INR");
            options.put("amount", amt);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "promyoutech@gmail.com");
            preFill.put("contact", PreferenceUtils.getInstance(this).getString(PreferenceUtils.Key.PHONE_NUMBER.name(), ""));

            options.put("prefill", preFill);

            checkout.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
            String token = PreferenceUtils.getInstance(activity).getToken();
            model = new ViewModelProvider(this, new AuthenticationViewModelFactory()).get(AuthentictionViewModel.class);
            model.getGenratedKeyResult().observe(this, genratedKey -> {
                if (genratedKey.getResponse_code() == 200) {
                    Message.toast(getApplicationContext(),"genrate key"+key);
                    key = genratedKey.getResponse_data();
                    if (key != null) {
                        Message.toast(getApplicationContext(),"genrate key"+key);
                            Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                            startActivity(intent);
                    }
                }
            });
            model.getGenerateKey("bearer " + token,razorpayPaymentID);
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }
}