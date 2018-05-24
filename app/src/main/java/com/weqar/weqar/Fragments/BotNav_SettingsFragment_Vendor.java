package com.weqar.weqar.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.weqar.weqar.DBHandlers.SessionManager;
import com.weqar.weqar.Global_url_weqar.Global_URL;
import com.weqar.weqar.LoginActivity;
import com.weqar.weqar.R;
import com.weqar.weqar.Settings_HelpActivity;
import com.weqar.weqar.Settings_ProfileActivity_Vendor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class BotNav_SettingsFragment_Vendor extends Fragment {
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    String s_lnw_usermailid,s_lnw_usertype,s_lnw_userid,s_lnw_usertoken,s_lnw_companyname,s_lnw_image,s_vendor_p_image;
    CircleImageView CV_uersset_image;
    public static BotNav_SettingsFragment_Vendor newInstance() {
        BotNav_SettingsFragment_Vendor fragment = new BotNav_SettingsFragment_Vendor();
        return fragment;
    }

    ImageView IV_set_profile, IV_set_account,IV_set_logout,WIV_setv_help,IV_setv_share;
    TextView TV_set_profile, TV_set_account,TV_set_logout,TV_user_name,TV_user_email,WTV_setv_help,TV_setv_share;
    private SessionManager session;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bot_nav__settings_fragment__vendor, container, false);
        IV_set_profile = view.findViewById(R.id.WIV_set_profile);
        TV_set_profile = view.findViewById(R.id.WTV_set_profile);
//        IV_set_account = view.findViewById(R.id.WIV_set_account);
//        TV_set_account = view.findViewById(R.id.WTV_set_account);

        IV_setv_share= view.findViewById(R.id.settings_vendor_share_IV);
        TV_setv_share = view.findViewById(R.id.settings_vendor_share_TV);

        WTV_setv_help=view.findViewById(R.id.WTV_setv_help);
        WIV_setv_help=view.findViewById(R.id.WIV_setv_help);
        IV_set_logout = view.findViewById(R.id.IV_set_v_logout);
        TV_set_logout = view.findViewById(R.id.TV_set_v_logout);
        TV_user_name=view.findViewById(R.id.Settings_company_name);
        TV_user_email=view.findViewById(R.id.Settings_company_email);
        CV_uersset_image=view.findViewById(R.id.Settings_company_image);
        session = new SessionManager(getActivity());
        session = new SessionManager(getActivity());
        Shared_user_details=getActivity().getSharedPreferences("user_detail_mode",0);
        s_lnw_usermailid=  Shared_user_details.getString("sp_w_useremail", null);
        s_lnw_usertype=  Shared_user_details.getString("sp_w_usertype", null);
        s_lnw_userid= Shared_user_details.getString("sp_w_userid", null);
        s_lnw_usertoken= Shared_user_details.getString("sp_w_apikey", null);
        s_lnw_companyname= Shared_user_details.getString("sp_w_username", null);
        s_lnw_image=Shared_user_details.getString("sp_w_image",null);
        TV_user_name.setText(s_lnw_companyname);
        TV_user_email.setText(s_lnw_usermailid);
        getmydet(s_lnw_userid);

        IV_set_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Settings_ProfileActivity_Vendor.class));
            }
        });
        TV_set_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Settings_ProfileActivity_Vendor.class));
            }
        });
        IV_setv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "I am happy with this app.Please click the link to download \n https://play.google.com/store/search?q=weqar&hl=en";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Weqar");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, " This is about service"));            }
        });
        TV_setv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "I am happy with this app.Please click the link to download \n https://play.google.com/store/search?q=weqar&hl=en";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Weqar");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, " This is about service"));            }
        });

//        IV_set_account.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), Settings_AccountActivity.class));
//            }
//        });
//        TV_set_account.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), Settings_AccountActivity.class));
//            }
//        });
        IV_set_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),LoginActivity.class);
                session.setLogin(false);
                PreferenceManager.getDefaultSharedPreferences(getActivity()).
                        edit().clear().apply();
                startActivity(intent);
            }
        });
        TV_set_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),LoginActivity.class);
                session.setLogin(false);
                PreferenceManager.getDefaultSharedPreferences(getActivity()).
                        edit().clear().apply();
                startActivity(intent);
            }
        });
        WTV_setv_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Settings_HelpActivity.class));
            }
        });
        WIV_setv_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Settings_HelpActivity.class));
            }
        });

        return view;
    }
    public void getmydet(String susername)
    {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Id", susername);
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.getDetails, new Response.Listener<String>() {
                public void onResponse(String response) {
                    try {
                        JSONObject jObj = new JSONObject(response);
                        String status = jObj.getString("Status");
                        if (status.equals("Success")) {
                            JSONObject verification = jObj.getJSONObject("Response");
                            JSONObject verifications = verification.getJSONObject("vendorProfessional");
                            s_vendor_p_image = verifications.getString("Logo");
                            Picasso.with(getActivity()).load(Global_URL.Image_url_load + s_vendor_p_image).error(getResources().getDrawable(R.drawable.rounded)).fit().centerCrop().into(CV_uersset_image);
                        }
                    } catch (Exception e) {
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
                    headers.put("x-api-key",s_lnw_usertoken);
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
}
