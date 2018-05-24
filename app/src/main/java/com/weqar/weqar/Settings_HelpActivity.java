package com.weqar.weqar;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thefinestartist.finestwebview.FinestWebView;

public class Settings_HelpActivity extends AppCompatActivity {
    ImageView IV_profile_account_back,IV_settings_faq,IV_settings_contact,IV_settings_tc;
    TextView TV_setings_faq,TV_settings_contact,TV_settings_tc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings__help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (isConnectedToNetwork()) {

            IV_profile_account_back = findViewById(R.id.set_account_help_back);
            IV_settings_faq = findViewById(R.id.WIV_set_faq);
            TV_setings_faq = findViewById(R.id.WTV_set_faq);
            IV_settings_contact = findViewById(R.id.WIV_set_contact);
            TV_settings_contact = findViewById(R.id.WTV_set_contact);
            IV_settings_tc = findViewById(R.id.WIV_set_tc);
            TV_settings_tc = findViewById(R.id.WTV_set_tc);
            IV_profile_account_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            IV_settings_faq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new FinestWebView.Builder(getApplicationContext())
                            .titleDefault("Weqar")
                            .showUrl(false)
                            .dividerHeight(0)
                            .toolbarScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS)
                            .gradientDivider(false)
                            .titleColorRes(R.color.finestWhite)
                            .urlColorRes(R.color.finestWhite)
                            .statusBarColorRes(R.color.colorPrimaryDark)
                            .toolbarColorRes(R.color.colorPrimary)
                            .iconPressedColorRes(R.color.colorPrimary)
                            .progressBarColorRes(R.color.colorPrimary)
                            .backPressToClose(false)
                            .gradientDivider(false)
                            .setCustomAnimations(R.anim.activity_open_enter, R.anim.activity_open_exit, R.anim.activity_close_enter, R.anim.activity_close_exit)
                            .show("http://weqar.co/#about");
                }
            });
            TV_setings_faq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new FinestWebView.Builder(getApplicationContext())
                            .titleDefault("Weqar")
                            .showUrl(false)
                            .dividerHeight(0)
                            .toolbarScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS)
                            .gradientDivider(false)
                            .titleColorRes(R.color.finestWhite)
                            .urlColorRes(R.color.finestWhite)
                            .statusBarColorRes(R.color.colorPrimaryDark)
                            .toolbarColorRes(R.color.colorPrimary)
                            .iconPressedColorRes(R.color.colorPrimary)
                            .progressBarColorRes(R.color.colorPrimary)
                            .backPressToClose(false)
                            .gradientDivider(false)
                            .setCustomAnimations(R.anim.activity_open_enter, R.anim.activity_open_exit, R.anim.activity_close_enter, R.anim.activity_close_exit)
                            .show("http://weqar.co/#about");
                }
            });

            IV_settings_contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new FinestWebView.Builder(getApplicationContext())
                            .titleDefault("Weqar")
                            .showUrl(false)
                            .dividerHeight(0)
                            .toolbarScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS)
                            .gradientDivider(false)
                            .titleColorRes(R.color.finestWhite)
                            .urlColorRes(R.color.finestWhite)
                            .statusBarColorRes(R.color.colorPrimaryDark)
                            .toolbarColorRes(R.color.colorPrimary)
                            .iconPressedColorRes(R.color.colorPrimary)
                            .progressBarColorRes(R.color.colorPrimary)
                            .backPressToClose(false)
                            .gradientDivider(false)
                            .setCustomAnimations(R.anim.activity_open_enter, R.anim.activity_open_exit, R.anim.activity_close_enter, R.anim.activity_close_exit)
                            .show("http://weqar.co/#about");
                }
            });
            TV_settings_contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new FinestWebView.Builder(getApplicationContext())
                            .titleDefault("Weqar")
                            .showUrl(false)
                            .dividerHeight(0)
                            .toolbarScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS)
                            .gradientDivider(false)
                            .titleColorRes(R.color.finestWhite)
                            .urlColorRes(R.color.finestWhite)
                            .statusBarColorRes(R.color.colorPrimaryDark)
                            .toolbarColorRes(R.color.colorPrimary)
                            .iconPressedColorRes(R.color.colorPrimary)
                            .progressBarColorRes(R.color.colorPrimary)
                            .backPressToClose(false)
                            .gradientDivider(false)
                            .setCustomAnimations(R.anim.activity_open_enter, R.anim.activity_open_exit, R.anim.activity_close_enter, R.anim.activity_close_exit)
                            .show("http://weqar.co/#about");
                }
            });

            IV_settings_tc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new FinestWebView.Builder(getApplicationContext())
                            .titleDefault("Weqar")
                            .showUrl(false)
                            .dividerHeight(0)
                            .toolbarScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS)
                            .gradientDivider(false)
                            .titleColorRes(R.color.finestWhite)
                            .urlColorRes(R.color.finestWhite)
                            .statusBarColorRes(R.color.colorPrimaryDark)
                            .toolbarColorRes(R.color.colorPrimary)
                            .iconPressedColorRes(R.color.colorPrimary)
                            .progressBarColorRes(R.color.colorPrimary)
                            .backPressToClose(false)
                            .gradientDivider(false)
                            .setCustomAnimations(R.anim.activity_open_enter, R.anim.activity_open_exit, R.anim.activity_close_enter, R.anim.activity_close_exit)
                            .show("http://weqar.co/#about");
                }
            });
            TV_settings_tc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new FinestWebView.Builder(getApplicationContext())
                            .titleDefault("Weqar")
                            .showUrl(false)
                            .dividerHeight(0)
                            .toolbarScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS)
                            .gradientDivider(false)
                            .titleColorRes(R.color.finestWhite)
                            .urlColorRes(R.color.finestWhite)
                            .statusBarColorRes(R.color.colorPrimaryDark)
                            .toolbarColorRes(R.color.colorPrimary)
                            .iconPressedColorRes(R.color.colorPrimary)
                            .progressBarColorRes(R.color.colorPrimary)
                            .backPressToClose(false)
                            .gradientDivider(false)
                            .setCustomAnimations(R.anim.activity_open_enter, R.anim.activity_open_exit, R.anim.activity_close_enter, R.anim.activity_close_exit)
                            .show("http://weqar.co/#about");
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
                    Intent intent = new Intent(Settings_HelpActivity.this, Settings_HelpActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
    private boolean isConnectedToNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
