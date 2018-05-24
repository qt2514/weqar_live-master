package com.weqar.weqar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.weqar.weqar.DBHandlers.SessionManager;

public class SplashActivity extends AppCompatActivity {
    ImageView IM_gif;
    private Thread mSplashThread;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_splash);
        session = new SessionManager(getApplicationContext());


        if (session.isLoggedIn()) {
            Intent intent = new Intent(SplashActivity.this, ProfileInfo.class);
            startActivity(intent);
        }
        else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }



    }


}
