package com.weqar.weqar;

import android.content.Context;
import android.content.Intent;
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

import de.hdodenhof.circleimageview.CircleImageView;


public class DiscountDetails_Vendor extends AppCompatActivity {
TextView TV_disc_desc_titlet,TV_disc_desc_title,TV_disc_desc_desc,disc_desc_titlestartdate;
///RatingBar disc_desc_rating;
String s_disc_desc_title,s_disc_desc_desc,s_disc_desc_rating,s_disc_image,s_disc_logo,s_disc_enddate,s_type,s_disc_startdate;
ImageView IV_disc_back_vendor,IV_discdet_image;
CircleImageView CV_discdet_logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_details__vendor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (isConnectedToNetwork()) {

            TV_disc_desc_title = findViewById(R.id.disc_desc_title);
            TV_disc_desc_titlet = findViewById(R.id.disc_desc_titlet);
            TV_disc_desc_desc = findViewById(R.id.disc_dec_det);
            disc_desc_titlestartdate = findViewById(R.id.disc_desc_titlestartdate);
         //   disc_desc_rating = findViewById(R.id.disc_desc_rating);
            IV_disc_back_vendor = findViewById(R.id.disc_back_vendor);
            IV_discdet_image = findViewById(R.id.v_disc_det_image);
            CV_discdet_logo = findViewById(R.id.v_disc_det_det_logo);
            IV_disc_back_vendor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            Intent intent = getIntent();
            s_disc_desc_title = intent.getStringExtra("put_title");
            s_disc_image = intent.getStringExtra("put_image");
            s_type = intent.getStringExtra("put_type");
            s_disc_logo = intent.getStringExtra("put_logo");
            s_disc_desc_rating = intent.getStringExtra("put_per");
            s_disc_desc_desc = intent.getStringExtra("put_desc");
            s_disc_enddate = intent.getStringExtra("put_enddate");
            s_disc_startdate = intent.getStringExtra("put_startdate");
            Integer k = Integer.parseInt(s_disc_desc_rating);
//           .
            if(s_type.equals("1"))
            {
                TV_disc_desc_title.setText(s_disc_desc_rating + "% " + s_disc_desc_title);

            }
            else
            {
                TV_disc_desc_title.setText(s_disc_desc_title);

            }
            Spanned htmlAsSpanned = Html.fromHtml(s_disc_desc_desc);
            TV_disc_desc_desc.setText(htmlAsSpanned);

           // TV_disc_desc_desc.setText(s_disc_desc_desc);
            disc_desc_titlestartdate.setText("Start Date: "+s_disc_startdate);
            TV_disc_desc_titlet.setText("End Date: "+s_disc_enddate);
            try {
                Picasso.with(this).load(Global_URL.Image_url_load + s_disc_image).error(getResources().getDrawable(R.drawable.rounded_two)).fit().centerCrop().into(IV_discdet_image);


                Picasso.with(this).load(Global_URL.Image_url_load + s_disc_logo).error(getResources().getDrawable(R.drawable.rounded)).fit().centerCrop().into(CV_discdet_logo);
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
        else
        {


            setContentView(R.layout.content_if_nointernet);
            ImageView but_retry = findViewById(R.id.nointernet_retry);
            but_retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DiscountDetails_Vendor.this, HomeScreen_vendor.class);
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
