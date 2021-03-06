package com.kibtechnologies.captainshieid;

import android.Manifest;
import android.app.ActivityManager;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.kibtechnologies.captainshieid.adapter.ItemClickListener;
import com.kibtechnologies.captainshieid.utils.AutoStartHelper;
import com.kibtechnologies.captainshieid.views.activities.BottomNaveDashboardActivity;

import java.util.Objects;


public class MainMenu extends AppCompatActivity implements  Welcome.Communicator, ItemClickListener {

    private DrawerLayout drawerLayout;
    private ListView listview;
    private ActionBarDrawerToggle drawerListener;
    protected com.kibtechnologies.captainshieid.MyAdapter MyAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        AutoStartHelper.getInstance(this).getAutoStartPermission(this);
       // listview = (ListView) findViewById(R.id.drawerList);
       // MyAdapter = new MyAdapter(this);
        //listview.setAdapter(MyAdapter);
        //listview.setOnItemClickListener(this);

        drawerListener = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };


        String status = Message.GetSP(getBaseContext(), "ACT", "welcome", "DEFAULT");

        if (Message.GetSP(getBaseContext(), "ACT", "SimCH1", "DONE").equals("OK")) {
//            simInsert();
            login();
        }
        if (status.equals("DEFAULT")) {
            Message.tag("onCreate DEFAULT");
            Activate_Welcome("Privacy", "W");
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            login();
//            try {
//                getSupportActionBar().hide();
//            } catch (Exception e) {
//                System.out.println(e);
////                Message.toast(getBaseContext(), "Error: Contact Developer");
//            }
        }
    }

    //main initial
    public void Main() {
        Message.tag("onCreate DEFAULT Else");
      // drawerLayout.setDrawerListener(drawerListener);
       // getSupportActionBar().setHomeButtonEnabled(true);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initial();
        if (Message.GetSP(getBaseContext(), "Permission", "SMS", "OFF").equals("OFF")) {
            check();
        } else {
            Message.SetSP(getBaseContext(), "ACT", "SimCH1", "OK");
        }
        Message.appLog(getBaseContext(), "App Opened");
//        try {
//            getSupportActionBar().show();
//        } catch (Exception e) {
//            Message.toast(getBaseContext(), "Error: Contact Developer");
//        }
    }



    //check permission
    public void check() {
        ActivityCompat.requestPermissions(MainMenu.this, new String[]{
                        Manifest.permission.READ_SMS,
                        Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_PRECISE_PHONE_STATE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.SYSTEM_ALERT_WINDOW},
                133);
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 0);
            }
        }

    }

    public void simInsert() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        Message.SetSP(getBaseContext(), "Sim", "SimNo", tm.getSimSerialNumber());
        Message.tag(tm.getSimSerialNumber());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 133: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Message.SetSP(getBaseContext(), "Permission", "SMS", "ON");
                } else {
                    Message.SetSP(getBaseContext(), "Permission", "SMS", "OFF");
                }
                return;
            }
        }
    }

    //Activate
    public void Activate_Welcome(String id, String where) {
        Welcome welcomeFrag = new Welcome();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("where", where);
        welcomeFrag.setArguments(bundle);
        FragmentManager FM = getFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        FT.replace(R.id.relative_main, welcomeFrag);
        FT.addToBackStack(where);
        FT.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
        }
        else {
            super.onBackPressed();
            Intent intent = new Intent(this, BottomNaveDashboardActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onWelcome(String string) {
        Message.tag("onWelcome :" + string);
        if (string.equals("welcome")) {
            Activate_Welcome("Welcome", "W");
            setTitle("Captain Shield");
        }
        else if (string.equals("phone")) {
            Activate_Welcome("Phone", "W");
            setTitle("Trusted Phone Number");
        } else if (string.equals("password")) {
            Activate_Welcome("Password", "W");
            setTitle("Password Setup");
        } else if (string.equals("device_admin")) {
            Activate_Welcome("Device_Admin", "W");
            setTitle("Device Admin Permission");
        } else if (string.equals("send_gcm")) {
            Activate_Welcome("Send_Gcm", "W");
            setTitle("Completed");
        } else if (string.equals("main")) {
            Main();
        }
    }

    public void ifstatement(int num) {
        if (num == -1) {
            initial();
        } else if (num == 0) {
            setTitle("Phone Number");
            Activate_Welcome("Phone", "M");
        } else if (num == 1) {
            setTitle("Password Setup");
            Activate_Welcome("Password", "M");
        } else if (num == 2) {
            setTitle("Device Administrator");
            Activate_Welcome("Device_Admin", "M");
        } else if (num == 3) {
            Activate_Welcome("Log", "M");
        } else if (num == 4) {
            setTitle("Settings");
            Activate_Welcome("Settings", "M");
        } else if (num == 5) {
            setTitle("Help");
            Activate_Welcome("Help", "M");
        }
    }


    public void initial() {
        setTitle("Status");
        Activate_Welcome("Status", "M");
    }

    public void login() {
        Activate_Welcome("Login", "M");
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerListener.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerListener.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (drawerListener.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

  /*  @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ifstatement(position);
        selectItem(position);
        drawerLayout.closeDrawers();
    }*/

    public void selectItem(int position) {
        listview.setItemChecked(position, true);
    }

    public void setTitle(String title) {
//        this.getSupportActionBar().setTitle(title);
//        getSupportActionBar().setTitle(title);

    }

    @Override
    public void onItemClick(int position, boolean status) {
        ifstatement(position);
       // selectItem(position);
    }
}

class MyAdapter extends BaseAdapter {

    private MainMenu context=null;
    int[] images = {
            R.mipmap.ic_textsms_white_24dp,
            R.mipmap.ic_contact_phone_white_24dp,
            R.mipmap.ic_fingerprint_white_24dp,
            R.mipmap.ic_security_white_24dp,
            R.mipmap.ic_library_books_white_24dp,
            R.mipmap.ic_settings_white_24dp,
            R.mipmap.ic_help_outline_white_24dp};

    public MyAdapter(MainMenu context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.side_menu_layout, parent, false);

        } else {
            row = convertView;
        }
        ImageView titleImageView = (ImageView) row.findViewById(R.id.imageview1);
        titleImageView.setImageResource(images[position]);

        return row;
    }
}
