package com.weqar.weqar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.weqar.weqar.Global_url_weqar.Global_URL;
import com.weqar.weqar.JavaClasses.RecyclerViewAdapter_Category;
import com.weqar.weqar.JavaClasses.RecyclerViewAdapter_DiscountDetails_User;
import com.weqar.weqar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DiscountDetails_User extends AppCompatActivity {
    ImageView IV_discount_details_back;
    RecyclerView RV_discdet_user;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    RecyclerViewAdapter_DiscountDetails_User RecyclerViewHorizontalAdapter;
    LinearLayoutManager HorizontalLayout ;
    List<String> L_disc_det_id;
    List<String> L_disc_det_desc;
    List<String> L_disc_det_image;
    List<String> L_disc_edt_percentage;
    List<String> L_disc_det_title;
    List<String> L_det_det_logo;
    List<String> L_det_det_startdate;
    List<String> L_det_det_enddate;
    List<String> L_det_det_type;
    String s_disc_det_id,s_disc_det_desc,s_disc_det_startdate,s_disc_det_image,s_disc_edt_percentage,s_disc_det_title,s_disc_det_type,s_det_det_logo,s_disc_det_enddate;
    String s_lnw_usermailid,s_lnw_discount_id;
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (isConnectedToNetwork()) {

            IV_discount_details_back = findViewById(R.id.discount_details_back);
            RV_discdet_user = findViewById(R.id.discountdet_rv_user);
            IV_discount_details_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            L_disc_det_id = new ArrayList<String>();
            L_disc_det_desc = new ArrayList<String>();
            L_disc_det_image = new ArrayList<String>();
            L_disc_edt_percentage = new ArrayList<String>();
            L_disc_det_title = new ArrayList<String>();
            L_det_det_logo = new ArrayList<String>();
            L_det_det_startdate = new ArrayList<String>();
            L_det_det_enddate = new ArrayList<String>();
            L_det_det_type = new ArrayList<String>();
            Shared_user_details = getSharedPreferences("user_detail_mode", 0);
            s_lnw_usermailid = Shared_user_details.getString("weqar_token", null);
            Intent intent = getIntent();
            s_lnw_discount_id = intent.getStringExtra("put_disc_id");
            RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
            RV_discdet_user.setLayoutManager(RecyclerViewLayoutManager);
            RecyclerViewHorizontalAdapter = new RecyclerViewAdapter_DiscountDetails_User(L_disc_det_id, L_disc_det_desc, L_disc_det_image,
                    L_disc_edt_percentage, L_disc_det_title, L_det_det_logo,L_det_det_startdate,L_det_det_enddate, L_det_det_type,this);
            HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            RV_discdet_user.setLayoutManager(HorizontalLayout);
            RV_discdet_user.setHorizontalScrollBarEnabled(false);
            getUserCompletesubscription();
        }
        else
        {

            setContentView(R.layout.content_if_nointernet);
            ImageView but_retry = findViewById(R.id.nointernet_retry);
            but_retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DiscountDetails_User.this, HomeScreen.class);
                    startActivity(intent);
                }
            });
        }
    }
    public void getUserCompletesubscription()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String final_url=Global_URL.Vendor_discount_detail+"?Id="+s_lnw_discount_id;
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
                headers.put("x-api-key",s_lnw_usermailid);
                return headers;
            }
        };
        requestQueue.add(stringRequest);
    } private boolean isConnectedToNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}
