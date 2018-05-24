package com.weqar.weqar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.weqar.weqar.Global_url_weqar.Global_URL;

public class NewsDetails_Vendor extends AppCompatActivity {
    ImageView IV_news_det_back,IV_news_det_image;
    TextView TV_news_det_title,TV_news_det_type,TV_nes_det_desc,news_det_v_url;
    String s_news_id,s_news_newsid,s_news_title,s_news_name,s_news_image,s_news_type,s_news_url,s_news_content;
    String s_vendor_disc,s_vendor_token,s_type_name;
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details__vendor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (isConnectedToNetwork()) {
        Shared_user_details = getSharedPreferences("user_detail_mode", 0);
        s_vendor_disc = Shared_user_details.getString("weqar_uid", null);
        s_vendor_token = Shared_user_details.getString("weqar_token", null);
        Intent intent=getIntent();
        try {
            s_news_id=intent.getStringExtra("news_v_id");
            s_news_newsid=intent.getStringExtra ("news_v_userid");
            s_news_title =intent.getStringExtra ("news_v_title");
            s_news_name  =intent.getStringExtra("news_v_name");
            s_news_image   =intent.getStringExtra("news_v_image");
            s_news_type =intent.getStringExtra("news_v_typeid");
            s_news_url  =intent.getStringExtra("news_v_url");
            s_news_content   =intent.getStringExtra("news_v_content");
            IV_news_det_back=findViewById(R.id.news_udet_back);
            IV_news_det_image=findViewById(R.id.news_udet_image);
            TV_news_det_title=findViewById(R.id.news_det_u_title);
            TV_news_det_type=findViewById(R.id.news_det_u_type);
            news_det_v_url=findViewById(R.id.news_det_v_url);
            TV_nes_det_desc=findViewById(R.id.news_det_u_desc);
            Spanned htmlAsSpanned = Html.fromHtml(s_news_content);
            if(s_news_type.equals("1"))
            {
                s_type_name="News";
            }
            else
            {
                s_type_name="Article";

            }
            TV_news_det_title.setText(s_news_title);
            TV_news_det_type.setText(s_type_name);
            news_det_v_url.setText(s_news_url);
            TV_nes_det_desc.setText(htmlAsSpanned);
            Picasso.with(this).load(Global_URL.Image_url_load + s_news_image).error(getResources().getDrawable(R.drawable.rounded)).fit().centerCrop().into(IV_news_det_image);
        } catch (Exception e) {

            e.printStackTrace();
        }
        IV_news_det_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewsDetails_Vendor.this,News_Display_Vendor.class));
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
                    Intent intent = new Intent(NewsDetails_Vendor.this, News_Display_Vendor.class);
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
