package com.weqar.weqar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.squareup.picasso.Picasso;
import com.weqar.weqar.Global_url_weqar.Global_URL;


public class EventDetails_Vendor extends AppCompatActivity {
    ImageView event_vdet_back,event_vdet_image;
    String get_event_id,s_vendor_disc,s_vendor_token;
    String event_id,user_id,event_title,event_name,event_image,event_desc,event_location,event_lati,event_longi,
    event_start,event_end,event_duration,event_amount,event_isreqrequired,event_ispaid,event_requirements;
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    TextView event_det_u_enddate,TV_title,TV_startdate,event_det_u_name,event_det_u_amount,event_det_u_requirements
    ,TV_place,TV_desc;
    Button events_det_register,event_location_locate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details__vendor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (isConnectedToNetwork()) {

            event_vdet_back = findViewById(R.id.event_vdet_back);
            event_vdet_image = findViewById(R.id.event_vdet_image);
            TV_title = findViewById(R.id.event_det_v_title);
            TV_startdate = findViewById(R.id.event_det_v_startdate);
            TV_place = findViewById(R.id.event_det_v_place);
            TV_desc = findViewById(R.id.event_det_v_desc);
            event_det_u_enddate = findViewById(R.id.event_det_u_enddate);
            event_det_u_name=findViewById(R.id.event_det_u_name);
            event_det_u_amount=findViewById(R.id.event_det_u_amount);
            event_det_u_requirements=findViewById(R.id.event_det_u_requirements);
            events_det_register=findViewById(R.id.events_det_register);
            event_location_locate=findViewById(R.id.event_location_locate);



            event_vdet_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(EventDetails_Vendor.this, Events_Display_Vendor.class));
                }
            });
            Shared_user_details = getSharedPreferences("user_detail_mode", 0);
            s_vendor_disc = Shared_user_details.getString("weqar_uid", null);
            s_vendor_token = Shared_user_details.getString("weqar_token", null);
            try {


                Intent intent = getIntent();
                get_event_id = intent.getStringExtra("event_v_id");

                user_id = intent.getStringExtra("event_v_userid");
                event_title = intent.getStringExtra("event_v_title");
                event_name = intent.getStringExtra("event_v_name");
                event_image = intent.getStringExtra("event_v_image");
                event_desc = intent.getStringExtra("event_v_desc");
                event_location = intent.getStringExtra("event_v_location");
                event_lati = intent.getStringExtra("event_v_latitude");
                event_longi = intent.getStringExtra("event_v_longitude");
                event_start = intent.getStringExtra("event_v_startdate");
                event_end = intent.getStringExtra("event_v_enddate");
                event_duration = intent.getStringExtra("event_v_duration");
                event_amount = intent.getStringExtra("event_v_amount");
                event_isreqrequired=intent.getStringExtra("event_v_regreq");
                event_ispaid=intent.getStringExtra("event_v_amountpaid");
                event_requirements=intent.getStringExtra("event_v_prereq");
                TV_title.setText(event_title);
                event_det_u_name.setText("by "+event_name);
                event_det_u_requirements.setText(event_requirements);
                event_location_locate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String strUri = "http://maps.google.com/maps?q=loc:" + event_lati + "," + event_longi + " (" + "Label which you want" + ")";
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));

                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

                        startActivity(intent);              }
                });
                boolean correct = s_vendor_disc.equals(user_id);
                if(correct)
                {
                    events_det_register.setVisibility(View.INVISIBLE);

                }
                else
                {
                    if(event_isreqrequired.equals("true"))
                    {
                        events_det_register.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        events_det_register.setVisibility(View.GONE);
                    }
                }
                if(event_ispaid.equals("true"))
                {
                    event_det_u_amount.setText("Registration Amount : "+event_amount);
                }
                else
                {
                    event_det_u_amount.setVisibility(View.GONE);
                }


                String dash_event_start= DateTimeUtils.formatWithPattern(event_start, "dd-MM-yyyy");
                String dash_eventisc_end=DateTimeUtils.formatWithPattern(event_end, "dd-MM-yyyy");
                String time_sched=event_start.substring(11,16);
                String time_sched_end=event_end.substring(11,16);
                TV_startdate.setText("Start Date : "+dash_event_start+" "+time_sched);
                event_det_u_enddate.setText("End Date : "+dash_eventisc_end+" "+time_sched_end);

                // TV_place.setText(city+state);
                Spanned htmlAsSpanneds = Html.fromHtml(event_desc);
                TV_desc.setText(htmlAsSpanneds);
                TV_title.setText(event_title);
                Picasso.with(EventDetails_Vendor.this).load(Global_URL.Image_url_load + event_image).error(getResources().getDrawable(R.drawable.rounded_two)).fit().centerCrop().into(event_vdet_image);
                TV_place.setText(event_location);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //   getallmyeventdetails(get_event_id);

        }
        else {
            setContentView(R.layout.content_if_nointernet);
            ImageView but_retry = findViewById(R.id.nointernet_retry);
            but_retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EventDetails_Vendor.this, Events_Display_Vendor.class);
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
