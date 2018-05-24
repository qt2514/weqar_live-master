package com.weqar.weqar;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.weqar.weqar.Fragments.BotNav_DiscountsFragment_Guest;
import com.weqar.weqar.Fragments.BotNav_EventsFragment;
import com.weqar.weqar.Fragments.BotNav_EventsFragment_Guest;
import com.weqar.weqar.Fragments.BotNav_JobsFragment_Guest;
import com.weqar.weqar.Fragments.BotNav_SettingsFragment_Guest;


public class HomeScreen_Guest extends AppCompatActivity {
    BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen__guest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (isConnectedToNetwork()) {

            bottomBar = findViewById(R.id.bottomBar_guest);
            bottomBar.setDefaultTab(R.id.botnav_event_guest);
            bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
                @Override
                public void onTabSelected(@IdRes int tabId) {
                    android.support.v4.app.Fragment selectedFragment = null;
                    if (tabId == R.id.botnav_event_guest) {
                        selectedFragment = BotNav_EventsFragment_Guest.newInstance();
                    } else if (tabId == R.id.botnav_discount_guest) {
                        selectedFragment = BotNav_DiscountsFragment_Guest.newInstance();

                    } else if (tabId == R.id.botnav_jobs_guest) {
                        selectedFragment = BotNav_JobsFragment_Guest.newInstance();

                    } else if (tabId == R.id.botnav_settings_guest) {
                        selectedFragment = BotNav_SettingsFragment_Guest.newInstance();

                    }
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.contentContainer, selectedFragment);
                    transaction.commit();
                }
            });
        }
        else
        {


            setContentView(R.layout.content_if_nointernet);
            ImageView but_retry = findViewById(R.id.nointernet_retry);
            but_retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeScreen_Guest.this, HomeScreen_Guest.class);
                    startActivity(intent);
                }
            });
        }
    }
    @Override
    public void onBackPressed() {

        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
    private boolean isConnectedToNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
