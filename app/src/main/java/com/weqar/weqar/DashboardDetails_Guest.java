package com.weqar.weqar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.squareup.picasso.Picasso;
import com.weqar.weqar.Global_url_weqar.Global_URL;
import com.weqar.weqar.JavaClasses.RecyclerViewAdapter_DashboardDetails_Guest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.refactor.lib.colordialog.PromptDialog;

public class DashboardDetails_Guest extends AppCompatActivity {
    ScrollView SV_discount_det,SV_event_det,SV_new_det;
    String S_dashboard_id,S_dashboard_type,S_dashboard_logo,s_vendor_disc,s_vendor_token;
    TextView TV_toolbar_title, TV_d_eventdet_title,TV_d_eventdet_startdate,TV_d_eventdet_enddate,TV_d_eventdet_place,TV_d_eventdet_desc,
                    event_det_u_name,event_det_u_amount,event_det_u_requirements,
                    news_det_u_title,news_det_u_type,news_det_u_desc,news_det_u_url;
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    String s_d_event_user_id,s_d_event_id,s_d_event_image,s_d_event_title,s_d_event_startdate,s_d_event_place,s_d_event_desc,
    s_d_event_name,s_d_event_amount,s_d_event_lati,s_d_event_longi,s_d_event_ispaid,s_d_event_regreq,s_d_event_prereq,
    s_d_news_userid,s_d_newsid,s_d_image,s_d_url,s_d_attachment,s_d_title,s_d_content,s_d_newtype;
RelativeLayout dahlc;
    ImageView dashboard_back_user,IV_d_eventdet_image,news_udet_image;
    Button dashboard_det_disc_edit,dashboard_det_disc_follow,dashboard_event_location_locate,dashboard_det_event_follow;
    List<String> L_disc_det_id;
    List<String> L_disc_det_desc;
    List<String> L_disc_det_image;
    List<String> L_disc_edt_percentage;
    List<String> L_disc_det_title;
    List<String> L_det_det_logo;
    List<String> L_det_det_startdate;
    List<String> L_det_det_enddate;
    List<String> L_det_det_type;
    String s_disc_det_id,s_disc_det_desc,s_disc_det_startdate,s_d_event_enddate,s_disc_det_image,s_disc_edt_percentage,s_disc_det_title,s_disc_det_type,s_det_det_logo,s_disc_det_enddate;
    RecyclerView RV_discdet_user;
    String typess;
    Toolbar toolbar;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    RecyclerViewAdapter_DashboardDetails_Guest RecyclerViewHorizontalAdapter;
    LinearLayoutManager HorizontalLayout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_details__guest);
         toolbar = (Toolbar) findViewById(R.id.toolbar_user);
        setSupportActionBar(toolbar);
        if (isConnectedToNetwork()) {
s_vendor_token="00000000-0000-0000-0000-000000000000";
        Shared_user_details = getSharedPreferences("user_detail_mode", 0);
        s_vendor_disc = Shared_user_details.getString("weqar_uid", null);
    //    s_vendor_token = Shared_user_details.getString("weqar_token", null);
        Intent intent = getIntent();
        try {
            S_dashboard_type = intent.getStringExtra("put_dashboard_type");
            S_dashboard_id = intent.getStringExtra("put_dashboard_id");
            S_dashboard_logo = intent.getStringExtra("put_dashboard_logo");

            L_disc_det_id = new ArrayList<String>();
            L_disc_det_desc = new ArrayList<String>();
            L_disc_det_image = new ArrayList<String>();
            L_disc_edt_percentage = new ArrayList<String>();
            L_disc_det_title = new ArrayList<String>();
            L_det_det_logo = new ArrayList<String>();
            L_det_det_startdate = new ArrayList<String>();
            L_det_det_enddate = new ArrayList<String>();
            L_det_det_type = new ArrayList<String>();

            dashboard_back_user = findViewById(R.id.dashboard_back_user);
            SV_discount_det = findViewById(R.id.dashboard_discount_scroll);
            SV_event_det = findViewById(R.id.dashboard_event_scroll);
            dahlc=findViewById(R.id.dahlc);
            SV_new_det = findViewById(R.id.dashboard_news_scroll);
            TV_d_eventdet_enddate = findViewById(R.id.TV_d_eventdet_enddate);
            TV_toolbar_title = findViewById(R.id.dashboard_toolbar_name_user);
            news_det_u_url = findViewById(R.id.news_det_u_url);
            event_det_u_name = findViewById(R.id.event_det_u_name);
            event_det_u_amount = findViewById(R.id.event_det_u_amount);
            event_det_u_requirements = findViewById(R.id.event_det_u_requirements);
            dashboard_event_location_locate=findViewById(R.id.dashboard_event_location_locate);
            dashboard_event_location_locate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String strUri = "http://maps.google.com/maps?q=loc:" + s_d_event_lati + "," + s_d_event_longi + " (" + "Label which you want" + ")";
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));

                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

                    startActivity(intent);
                }
            });

            RV_discdet_user = findViewById(R.id.discount_rv_guest);
            news_udet_image=findViewById(R.id.news_udet_image);

            news_det_u_title = findViewById(R.id.news_det_u_title);
            news_det_u_type = findViewById(R.id.news_det_u_type);
            news_det_u_desc = findViewById(R.id.news_det_u_desc);

            RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
            RV_discdet_user.setLayoutManager(RecyclerViewLayoutManager);
            RecyclerViewHorizontalAdapter = new RecyclerViewAdapter_DashboardDetails_Guest(L_disc_det_id, L_disc_det_desc, L_disc_det_image,
                    L_disc_edt_percentage, L_disc_det_title, L_det_det_logo,L_det_det_startdate,L_det_det_enddate, L_det_det_type,this);
            HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            RV_discdet_user.setLayoutManager(HorizontalLayout);
            RV_discdet_user.setHorizontalScrollBarEnabled(false);

            TV_d_eventdet_title = findViewById(R.id.TV_d_eventdet_title);
            TV_d_eventdet_startdate = findViewById(R.id.TV_d_eventdet_startdate);
            TV_d_eventdet_place = findViewById(R.id.TV_d_eventdet_place);
            TV_d_eventdet_desc = findViewById(R.id.TV_d_eventdet_desc);
            IV_d_eventdet_image = findViewById(R.id.IV_d_eventdet_image);

            dashboard_det_disc_follow = findViewById(R.id.dashboard_det_disc_follow);
            dashboard_det_event_follow = findViewById(R.id.dashboard_det_event_follow);
            dashboard_det_disc_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new PromptDialog(DashboardDetails_Guest.this)
                            .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                            .setAnimationEnable(true)
                            .setTitleText("Please Sign In")
                            .setContentText("You need to Signin/Signup to edit a Discount")
                            .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                    startActivity(new Intent(DashboardDetails_Guest.this, LoginActivity.class));
                                }
                            }).show();
                }
            });
            dashboard_det_disc_follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new PromptDialog(DashboardDetails_Guest.this)
                            .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                            .setAnimationEnable(true)
                            .setTitleText("Please Sign In")
                            .setContentText("You need to Signin/Signup to follow a Discount")
                            .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                    startActivity(new Intent(DashboardDetails_Guest.this, LoginActivity.class));
                                }
                            }).show();
                }
            });   dashboard_det_event_follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new PromptDialog(DashboardDetails_Guest.this)
                            .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                            .setAnimationEnable(true)
                            .setTitleText("Please Sign In")
                            .setContentText("You need to Signin/Signup to follow a Discount")
                            .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                    startActivity(new Intent(DashboardDetails_Guest.this, LoginActivity.class));
                                }
                            }).show();
                }
            });





        } catch (Exception e) {
            e.printStackTrace();
        }


        if (S_dashboard_type.equals("Discount")) {
            getUserCompletesubscription(S_dashboard_id);
            SV_discount_det.setVisibility(View.VISIBLE);
            TV_toolbar_title.setText("Discount");

            SV_discount_det.setBackgroundColor(getResources().getColor(R.color.colorBlack));
            dahlc.setBackgroundColor(getResources().getColor(R.color.colorBlack));
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorBlack));

        } else if (S_dashboard_type.equals("Event")) {
            getmydashboard_event_det(S_dashboard_id);
            TV_toolbar_title.setText("Event");

            SV_discount_det.setBackgroundColor(getResources().getColor(R.color.colorWhite));

            SV_event_det.setVisibility(View.VISIBLE);
        } else if (S_dashboard_type.equals("News")) {
            TV_toolbar_title.setText("News");
getmydashboard_news_det(S_dashboard_id);
            SV_discount_det.setBackgroundColor(getResources().getColor(R.color.colorWhite));

            SV_new_det.setVisibility(View.VISIBLE);

        }
        dashboard_back_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardDetails_Guest.this, HomeScreen_Guest.class));
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
                    Intent intent = new Intent(DashboardDetails_Guest.this, HomeScreen_Guest.class);
                    startActivity(intent);
                }
            });
        }

    }
    public void getUserCompletesubscription(String c)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String final_url=Global_URL.Vendor_discount_detail+"?Id="+S_dashboard_id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, final_url, new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray jsonArray = jObj.getJSONArray("Response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        s_disc_det_id= object.getString("Id");
                        s_disc_det_desc= object.getString("Description");
                        s_disc_det_image= object.getString("Image");
                        s_disc_det_startdate= object.getString("StartDate");
                        s_disc_det_enddate= object.getString("EndDate");
                        s_disc_edt_percentage= object.getString("Percentage");
                        s_disc_det_title= object.getString("Title");
                        s_det_det_logo= object.getString("Logo");
                        s_disc_det_type= object.getString("DiscountType");
                        try
                        {
                            L_disc_det_id.add(String.valueOf(s_disc_det_id));
                            L_disc_det_desc.add(String.valueOf(s_disc_det_desc));
                            L_disc_det_image.add(String.valueOf(s_disc_det_image));
                            L_disc_edt_percentage.add(String.valueOf(s_disc_edt_percentage));
                            L_disc_det_title.add(String.valueOf(s_disc_det_title));
                            L_det_det_logo.add(String.valueOf(s_det_det_logo));
                            L_det_det_startdate.add(String.valueOf(s_disc_det_startdate));
                            L_det_det_enddate.add(String.valueOf(s_disc_det_enddate));
                            L_det_det_type.add(String.valueOf(s_disc_det_type));
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    }
                    RV_discdet_user.setAdapter(RecyclerViewHorizontalAdapter);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //// headers.put("Content-Type", "application/json");
                headers.put("x-api-type","Android");
                headers.put("x-api-key",s_vendor_token);
                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void getmydashboard_event_det(String susername)
    {

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Id", susername);
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.user_dashboard_event_det, new Response.Listener<String>() {

                public void onResponse(String response) {
                    try {

                        JSONObject jObj = new JSONObject(response);

                        String status = jObj.getString("Status");
                        if(status.equals("Success"))
                        {
                            JSONObject verification = jObj.getJSONObject("Response");
                            s_d_event_user_id=verification.getString("UserId");
                            s_d_event_id=verification.getString("Id");
                            s_d_event_image=verification.getString("Image");
                            s_d_event_title=verification.getString("Title");
                            s_d_event_startdate=verification.getString("EventStart");
                            s_d_event_enddate=verification.getString("EventEnd");
                            s_d_event_place=verification.getString("Location");
                            s_d_event_desc=verification.getString("Description");
                            s_d_event_name=verification.getString("Name");
                            s_d_event_amount=verification.getString("Amount");
                            s_d_event_lati=verification.getString("Latitude");
                            s_d_event_longi=verification.getString("Longitude");
                            s_d_event_ispaid=verification.getString("IsPaid");
                            s_d_event_regreq=verification.getString("RegistrationRequired");
                            s_d_event_prereq=verification.getString("Requirements");

                            TV_d_eventdet_title.setText(s_d_event_title);
                            if(s_d_event_regreq.equals("true"))
                            {
                                dashboard_det_event_follow.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                dashboard_det_event_follow.setVisibility(View.GONE);
                            }

                            if(s_d_event_ispaid.equals("true"))
                            {
                                event_det_u_amount.setText("Registration Amount : "+s_d_event_amount);
                            }
                            else
                            {
                                event_det_u_amount.setVisibility(View.GONE);
                            }
                            event_det_u_name.setText("by "+s_d_event_name);
                            event_det_u_requirements.setText(s_d_event_prereq);
                            String dash_event_start= DateTimeUtils.formatWithPattern(s_d_event_startdate, "dd-MM-yyyy");
                            String dash_eventisc_end=DateTimeUtils.formatWithPattern(s_d_event_enddate, "dd-MM-yyyy");
                            String time_sched=s_d_event_startdate.substring(11,16);
                            String time_sched_end=s_d_event_enddate.substring(11,16);
                            TV_d_eventdet_startdate.setText("Start Date : "+dash_event_start+" "+time_sched);
                            TV_d_eventdet_enddate.setText("End Date : "+dash_eventisc_end+" "+time_sched_end);
                            TV_d_eventdet_place.setText(s_d_event_place);

                            Spanned htmlAsSpanned = Html.fromHtml(s_d_event_desc);
                            TV_d_eventdet_desc.setText(htmlAsSpanned);
                            Picasso.with(DashboardDetails_Guest.this).load(Global_URL.Image_url_load + s_d_event_image).error(getResources().getDrawable(R.drawable.rounded_two)).fit().centerCrop().into(IV_d_eventdet_image);
                        }
                        else
                        {


                        }

                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                }
            }) {
                @Override
                public String getBodyContentType() {

                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("x-api-type", "Android");
                    //headers.put("content-Type", "application/json");
                    headers.put("x-api-key",s_vendor_token);
                    return headers;

                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");

                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }




            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }public void getmydashboard_news_det(String susername)
    {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject jsonBody = new JSONObject();

        String news_url=Global_URL.user_dashboard_news_det+"?="+susername;
        final String requestBody = jsonBody.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, news_url, new Response.Listener<String>() {

            public void onResponse(String response) {
                try {

                    JSONObject jObj = new JSONObject(response);

                    JSONArray jsonArray = jObj.getJSONArray("Response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        s_d_news_userid = object.getString("UserId");

                        s_d_newsid = object.getString("Id");
                        s_d_image = object.getString("Image");
                        s_d_title = object.getString("Title");
                        s_d_url = object.getString("URL");
                        s_d_attachment = object.getString("Attachment");
                        s_d_content = object.getString("Content");
                        s_d_newtype = object.getString("NewsTypeId");

                        TV_d_eventdet_title.setText(s_d_event_title);
                        if (s_d_newtype.equals("1")) {
                            typess = "News";
                        } else {
                            typess = "Article";
                        }

                        news_det_u_title.setText(s_d_title);
                        news_det_u_type.setText(typess);
                        news_det_u_url.setText(s_d_url);
                        Spanned htmlAsSpanneds = Html.fromHtml(s_d_content);
                        news_det_u_desc.setText(htmlAsSpanneds);
                        Picasso.with(DashboardDetails_Guest.this).load(Global_URL.Image_url_load + s_d_image).error(getResources().getDrawable(R.drawable.rounded_two)).fit().centerCrop().into(news_udet_image

                        );
                    }

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {
            @Override
            public String getBodyContentType() {

                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("x-api-type", "Android");
                //headers.put("content-Type", "application/json");
                headers.put("x-api-key",s_vendor_token);
                return headers;

            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");

                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }




        };

        requestQueue.add(stringRequest);

    }
    private boolean isConnectedToNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
