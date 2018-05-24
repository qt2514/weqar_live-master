package com.weqar.weqar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class Settings_ProfileActivity_Vendor extends AppCompatActivity {
Context context;
    ImageView IV_profile_account_back;
    TextView TV_vendor_profile_fname,TV_vendor_profile_name,TV_vendor_profile_email,
            TV_vendor_profile_mobile,TV_vendor_profile_address,TV_vendor_profile_country,
            TV_vendor_profiletmname,TV_vendor_profilemname,TV_vendor_profiletlname,TV_vendor_profilelname,
    T_set_prof_user_edit,T_set_prof_prof_edit,T_set_prof_user_companyname,T_set_prof_user_category,T_set_prof_user_bcontact,
            T_set_prof_user_bemail,T_set_prof_user_location;

    View view_v_one,view_v_two;
    String s_vendor_id,s_vendor_token,s_vendor_p_fname,s_vendor_p_name,s_vendor_p_email,
            s_vendor_p_mobile,s_vendor_p_address,s_vendor_p_country,s_vendor_p_image,
            s_vendor_p_mname,s_vendor_p_lname,s_vendor_p_companyname,s_vendor_p_categoryid,s_vendor_p_categoryname,
            s_vendor_p_bcontact,s_vendor_p_bemail,s_vendor_p_weblink,s_vendor_p_location,s_vendor_p_locationo;
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    CircleImageView CV_vendor_profileimage;
    ArrayList<String> spinnerArray_id,spinnerArray_name ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings__profile__vendor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context=Settings_ProfileActivity_Vendor.this;
        if (isConnectedToNetwork()) {

            Shared_user_details = getSharedPreferences("user_detail_mode", 0);

            s_vendor_id = Shared_user_details.getString("sp_w_userid", null);
            s_vendor_token = Shared_user_details.getString("sp_w_apikey", null);
            getmydet(s_vendor_id);

             spinnerArray_id = new ArrayList<String>();
             spinnerArray_name = new ArrayList<String>();



            IV_profile_account_back = findViewById(R.id.account_setting_back);
            TV_vendor_profiletmname = findViewById(R.id.TV_vendor_profiletmname);
            TV_vendor_profilemname = findViewById(R.id.TV_vendor_profilemname);
            TV_vendor_profiletlname = findViewById(R.id.TV_vendor_profiletlname);
            TV_vendor_profilelname = findViewById(R.id.TV_vendor_profilelname);

            CV_vendor_profileimage=findViewById(R.id.CV_vendor_profileimage);
            view_v_one = findViewById(R.id.view_v_one);
            view_v_two = findViewById(R.id.view_v_two);

            TV_vendor_profile_fname = findViewById(R.id.TV_vendor_profilefname);
            TV_vendor_profile_name = findViewById(R.id.TV_vendor_profilename);
            TV_vendor_profile_email = findViewById(R.id.TV_vendor_profileemail);
            TV_vendor_profile_mobile = findViewById(R.id.TV_vendor_profilemobile);
            TV_vendor_profile_address = findViewById(R.id.TV_vendor_profileaddress);
            TV_vendor_profile_country = findViewById(R.id.TV_vendor_profilecountry);
            T_set_prof_user_edit=findViewById(R.id.T_set_prof_user_edit);
            T_set_prof_prof_edit=findViewById(R.id.T_set_prof_prof_edit);
            T_set_prof_user_companyname=findViewById(R.id.T_set_prof_user_companyname);
            T_set_prof_user_category=findViewById(R.id.T_set_prof_user_category);
            T_set_prof_user_bcontact=findViewById(R.id.T_set_prof_user_bcontact);
            T_set_prof_user_bemail=findViewById(R.id.T_set_prof_user_bemail);
          //  T_set_prof_user_bwebsitelink=findViewById(R.id.T_set_prof_user_bwebsitelink);
            T_set_prof_user_location=findViewById(R.id.T_set_prof_user_location);

            T_set_prof_user_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new  Intent(Settings_ProfileActivity_Vendor.this,Settings_Profile_Edit_Vendor.class);
                    intent.putExtra("put_s_userid",s_vendor_id);
                    intent.putExtra("put_s_address",s_vendor_p_address);
                    intent.putExtra("put_s_image",s_vendor_p_image);
                    intent.putExtra("put_s_fname",s_vendor_p_fname);
                    intent.putExtra("put_s_mname",s_vendor_p_mname);
                    intent.putExtra("put_s_lname",s_vendor_p_lname);
                    intent.putExtra("put_s_email",s_vendor_p_email);
                    intent.putExtra("put_s_mobile",s_vendor_p_mobile);
                    intent.putExtra("put_s_country",s_vendor_p_country);
                    startActivity(intent);
                }
            });
            T_set_prof_prof_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Settings_ProfileActivity_Vendor.this,Settings_Profesional_Edit_Vendor.class);
                    intent.putExtra("put_s_userid",s_vendor_id);
                    intent.putExtra("put_s_companyname",s_vendor_p_companyname);
                    intent.putExtra("put_s_categoryid",spinnerArray_id);
                    intent.putExtra("put_s_categoryname",spinnerArray_name);
                    intent.putExtra("put_s_bcontact",s_vendor_p_bcontact);
                    intent.putExtra("put_s_bemail",s_vendor_p_bemail);
                    intent.putExtra("put_s_websitelink",s_vendor_p_weblink);
                    intent.putExtra("put_s_location",s_vendor_p_location);
                    intent.putExtra("put_s_locationo",s_vendor_p_locationo);
                    intent.putExtra("put_s_logo",s_vendor_p_image);
                    startActivity(intent);
                }
            });
            IV_profile_account_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Settings_ProfileActivity_Vendor.this,HomeScreen_vendor.class));
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
                    Intent intent = new Intent(Settings_ProfileActivity_Vendor.this, Settings_ProfileActivity_Vendor.class);
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
                            s_vendor_p_fname=verification.getString("FirstName");
                            s_vendor_p_mname=verification.getString("MiddleName");
                            s_vendor_p_lname=verification.getString("LastName");
                            s_vendor_p_name=verification.getString("UserName");
                            s_vendor_p_email=verification.getString("Email");
                            s_vendor_p_mobile=verification.getString("PhoneNumber");
                            s_vendor_p_address=verification.getString("Address1");
                            s_vendor_p_country=verification.getString("Country");

                            TV_vendor_profile_fname.setText(s_vendor_p_fname);
                            TV_vendor_profile_name.setText(s_vendor_p_name);
                            TV_vendor_profile_email.setText(s_vendor_p_email);
                            TV_vendor_profile_mobile.setText(s_vendor_p_mobile);
                            TV_vendor_profile_address.setText(s_vendor_p_address);
                            TV_vendor_profile_country.setText(s_vendor_p_country);

                            JSONObject verifications = verification.getJSONObject("vendorProfessional");
                            s_vendor_p_image=verifications.getString("Logo");
                            s_vendor_p_companyname=verifications.getString("CompanyName");
                            s_vendor_p_bcontact=verifications.getString("BusinessContact");
                            s_vendor_p_bemail=verifications.getString("BusinessEmail");
                            s_vendor_p_weblink=verifications.getString("WebLink");
                            s_vendor_p_location=verifications.getString("Latitude");
                            s_vendor_p_locationo=verifications.getString("Longitude");

                            T_set_prof_user_companyname.setText(s_vendor_p_companyname);
                            T_set_prof_user_bcontact.setText(s_vendor_p_bcontact);
                            T_set_prof_user_bemail.setText(s_vendor_p_bemail);
                            Picasso.with(Settings_ProfileActivity_Vendor.this).load(Global_URL.Image_url_load+s_vendor_p_image).error(getResources().getDrawable(R.drawable.rounded)).fit().centerCrop().into(CV_vendor_profileimage);

                            // T_set_prof_user_bwebsitelink.setText(s_vendor_p_weblink);
                            T_set_prof_user_location.setText(s_vendor_p_location+" "+s_vendor_p_locationo+"    ");
                            JSONArray myverifyarray=verifications.getJSONArray("Categories");

                                for (int i = 0; i < myverifyarray.length(); i++) {
                                    spinnerArray_id.add(myverifyarray.getString(i));
                                }
                                JSONArray myverifyarrays=verifications.getJSONArray("CategoryNames");

//                                for (int i = 0; i < myverifyarrays.length(); i++) {
//                                //    spinnerArray=myverifyarrays.getString(i);
//                                    spinnerArray_name.add(myverifyarrays.getString(i));
//                                    T_set_prof_user_category.append(myverifyarrays.getString(i));
//
//                                }
                            StringBuilder builders = new StringBuilder();

                            for (int i = 0; i < myverifyarrays.length(); i++) {
                                spinnerArray_name.add(myverifyarrays.getString(i));

                                builders.append("").append(myverifyarrays.get(i)).append(",");


                            }
                            TinyDB tinydb = new TinyDB(context);

                            tinydb.putListString("subjectnamelistnewofindia",  spinnerArray_name);
                            T_set_prof_user_category.setText(builders);
                         try {
                             if(s_vendor_p_mname.equals(""))
                             {
                                 TV_vendor_profiletmname.setVisibility(View.GONE);
                                 TV_vendor_profilemname.setVisibility(View.GONE);
                                 view_v_one.setVisibility(View.GONE);
                             }
                             else
                             {
                                 TV_vendor_profilemname.setText(s_vendor_p_mname);
                             }
                             if(s_vendor_p_lname.equals(""))
                             {
                                 TV_vendor_profiletlname.setVisibility(View.GONE);
                                 TV_vendor_profilelname.setVisibility(View.GONE);
                                 view_v_two.setVisibility(View.GONE);

                             }
                             else
                             {
                                 TV_vendor_profilelname.setText(s_vendor_p_lname);
                             }

                         }catch (Exception e)
                         {
                             e.printStackTrace();
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

    }
    private boolean isConnectedToNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
