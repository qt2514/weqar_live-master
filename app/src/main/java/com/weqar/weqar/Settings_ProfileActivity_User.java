package com.weqar.weqar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.weqar.weqar.Global_url_weqar.Global_URL;
import com.weqar.weqar.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class Settings_ProfileActivity_User extends AppCompatActivity {
    ImageView IV_profile_account_back;
    CircleImageView CV_user_profileimage;
    TextView TV_user_profile_fname,TV_user_profile_name,TV_user_profile_email,
            TV_user_profile_mobile,TV_user_profile_address,TV_user_profile_country,
            TV_user_profiletmname,TV_user_profilemname,TV_user_profiletlname,TV_user_profilelname;
    String s_user_id,s_user_token,s_user_p_fname,s_user_p_mname,s_user_p_lname,s_user_p_name,s_user_p_email,
            s_user_p_mobile,s_user_p_address,s_user_p_country,s_user_p_image;
        SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    View v_one,v_two;
    TextView T_set_prof_user_edit;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings__profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (isConnectedToNetwork()) {
            Shared_user_details = getSharedPreferences("user_detail_mode", 0);

            s_user_id = Shared_user_details.getString("sp_w_userid", null);
            s_user_token = Shared_user_details.getString("sp_w_apikey", null);
            getmydet(s_user_id);

            IV_profile_account_back = findViewById(R.id.account_setting_back);
            CV_user_profileimage = findViewById(R.id.CV_user_profileimage);
            TV_user_profile_fname = findViewById(R.id.TV_user_profilefname);

            TV_user_profiletmname = findViewById(R.id.TV_user_profiletmname);
            TV_user_profilemname = findViewById(R.id.TV_user_profilemname);
            TV_user_profiletlname = findViewById(R.id.TV_user_profiletlname);
            TV_user_profilelname = findViewById(R.id.TV_user_profilelname);
            T_set_prof_user_edit=findViewById(R.id.T_set_prof_user_edit);
            dialog = new ProgressDialog(this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setMessage("Loading. Please wait...");
            T_set_prof_user_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Settings_ProfileActivity_User.this,Settings_Profile_Edit_User.class);
                    intent.putExtra("put_s_userid",s_user_id);
                    intent.putExtra("put_s_address",s_user_p_address);
                    intent.putExtra("put_s_image",s_user_p_image);
                    intent.putExtra("put_s_fname",s_user_p_fname);
                    intent.putExtra("put_s_mname",s_user_p_mname);
                    intent.putExtra("put_s_lname",s_user_p_lname);
                    intent.putExtra("put_s_email",s_user_p_email);
                    intent.putExtra("put_s_mobile",s_user_p_mobile);
                    intent.putExtra("put_s_country",s_user_p_country);
                    startActivity(intent);
                }
            });
            v_one = findViewById(R.id.view_one);
            v_two = findViewById(R.id.view_two);
            TV_user_profile_name = findViewById(R.id.TV_user_profilename);
            TV_user_profile_email = findViewById(R.id.TV_user_profileemail);
            TV_user_profile_mobile = findViewById(R.id.TV_user_profilemobile);
            TV_user_profile_address = findViewById(R.id.TV_user_profileaddress);
            TV_user_profile_country = findViewById(R.id.TV_user_profilecountry);
            IV_profile_account_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   startActivity(new Intent(Settings_ProfileActivity_User.this,HomeScreen.class));
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
                    Intent intent = new Intent(Settings_ProfileActivity_User.this, Settings_ProfileActivity_User.class);
                    startActivity(intent);
                }
            });


        }

    }
    public void getmydet(String susername)
    {

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Id", susername);



            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.getDetails, new Response.Listener<String>() {

                public void onResponse(String response) {
                    try {

                        JSONObject jObj = new JSONObject(response);

                        String status = jObj.getString("Status");
                        if(status.equals("Success"))
                        {

                            JSONObject verification = jObj.getJSONObject("Response");
                            s_user_p_fname=verification.getString("FirstName");

                            s_user_p_mname=verification.getString("MiddleName");
                            s_user_p_lname=verification.getString("LastName");
                            s_user_p_name=verification.getString("UserName");
                            s_user_p_email=verification.getString("Email");
                            s_user_p_mobile=verification.getString("PhoneNumber");
                            s_user_p_address=verification.getString("Address1");
                            s_user_p_country=verification.getString("Country");
//                            JSONObject verifications = verification.getJSONObject("vendorProfessional");
                          s_user_p_image=verification.getString("Image");
                            Picasso.with(Settings_ProfileActivity_User.this).load(Global_URL.Image_url_load+s_user_p_image).error(getResources().getDrawable(R.drawable.rounded)).fit().centerCrop().into(CV_user_profileimage);
                            TV_user_profile_fname.setText(s_user_p_fname);
                            TV_user_profile_name.setText(s_user_p_name);
                            TV_user_profile_email.setText(s_user_p_email);
                            TV_user_profile_mobile.setText(s_user_p_mobile);
                            TV_user_profile_address.setText(s_user_p_address);
                            TV_user_profile_country.setText(s_user_p_country);
                            if(s_user_p_mname.equals(null)||s_user_p_mname.equals(""))
                            {
                                TV_user_profiletmname.setVisibility(View.GONE);
                                TV_user_profilemname.setVisibility(View.GONE);
                                v_one.setVisibility(View.GONE);
                            }
                            else
                            {
                                TV_user_profilemname.setText(s_user_p_mname);
                            }
                            if(s_user_p_lname.equals(null)||s_user_p_lname.equals(""))
                            {
                                TV_user_profiletlname.setVisibility(View.GONE);
                                TV_user_profilelname.setVisibility(View.GONE);
                                v_two.setVisibility(View.GONE);

                            }
                            else
                            {
                                TV_user_profilelname.setText(s_user_p_lname);
                            }

                        }
                        else
                        {


                        }


                        //finish();
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
                    headers.put("x-api-key",s_user_token);
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

    } private boolean isConnectedToNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}
