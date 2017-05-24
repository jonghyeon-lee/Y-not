package com.hnd.y_not_proto2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    String SENDER_ID = "402782999562";
    static final String TAG = "H&D";
    GoogleCloudMessaging gcm;
    SharedPreferences prefs;
    Context context;
    String regid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            finish();
            return;
        }

        prefs = getSharedPreferences("Chat", 0);
        context = getApplicationContext();
        if(!prefs.getString("REG_FROM","").isEmpty()){

        } else if(!prefs.getString("REG_ID", "").isEmpty()){
            //Fragment reg = new LoginFragment();
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
        }else if(checkPlayServices()){
            new Register().execute();
        }else{
            Toast.makeText(getApplicationContext(), R.string.gcm_not_support, Toast.LENGTH_SHORT).show();
        }
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_name_mem));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_name_cht));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_name_sns));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_name_sns_uk));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_name_set));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
    private class Register extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {
            try {
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(context);
                    regid = gcm.register(SENDER_ID);
                    Log.e("RegId", regid);

                    SharedPreferences.Editor edit = prefs.edit();
                    edit.putString("REG_ID", regid);
                    edit.commit();

                }

                return  regid;

            } catch (IOException ex) {
                Log.e("Error", ex.getMessage());
                return "Fails";

            }
        }
        @Override
        protected void onPostExecute(String json) {
            /*Fragment reg = new LoginFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, reg);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();*/
            Intent login = new Intent(getApplication(), LoginActivity.class);
            startActivity(login);
        }
    }
}