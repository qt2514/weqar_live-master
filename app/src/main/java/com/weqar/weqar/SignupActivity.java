package com.weqar.weqar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.weqar.weqar.Global_url_weqar.Global_URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import cn.refactor.lib.colordialog.PromptDialog;

public class SignupActivity extends AppCompatActivity {
    EditText ET_username,ET_emailid,ET_password,ET_confirmpassword;
    String S_username,S_emailid,S_password,S_confirmpassword,SemailPattern,SemailInput,S_user_type;
    Button B_signup,B_sel_user,B_sel_vendor;
    Context context;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_signup);
        ET_username=findViewById(R.id.signup_username);
        ET_emailid=findViewById(R.id.signup_email);
        ET_password=findViewById(R.id.signup_password);
        ET_confirmpassword=findViewById(R.id.signup_confirm);
        B_signup=findViewById(R.id.but_signup);
        B_sel_user=findViewById(R.id.Signup_But_user);
        B_sel_vendor=findViewById(R.id.Signup_But_vendor);
        context=this;
        S_user_type="user";
        dialog = new ProgressDialog(this);
         dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait...");

        ET_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_username.setBackgroundResource( R.drawable.edittext_selected);
                    ET_username.setTextColor(getResources().getColor(R.color.colorPrimary));

                }
                else
                {
                    ET_username.setBackgroundResource( R.drawable.edittext_unselected);
                    ET_username.setTextColor(getResources().getColor(R.color.colorBlack));

                }
            }
        });
        ET_emailid.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_emailid.setBackgroundResource( R.drawable.edittext_selected);
                    ET_emailid.setTextColor(getResources().getColor(R.color.colorPrimary));

                }
                else
                {
                    ET_emailid.setBackgroundResource( R.drawable.edittext_unselected);
                    ET_emailid.setTextColor(getResources().getColor(R.color.colorBlack));

                }
            }
        });
        ET_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_password.setBackgroundResource( R.drawable.edittext_selected);
                    ET_password.setTextColor(getResources().getColor(R.color.colorPrimary));

                }
                else
                {
                    ET_password.setBackgroundResource( R.drawable.edittext_unselected);
                    ET_password.setTextColor(getResources().getColor(R.color.colorBlack));

                }
            }
        });
        ET_confirmpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_confirmpassword.setBackgroundResource( R.drawable.edittext_selected);
                    ET_confirmpassword.setTextColor(getResources().getColor(R.color.colorPrimary));

                }
                else
                {
                    ET_confirmpassword.setBackgroundResource( R.drawable.edittext_unselected);
                    ET_confirmpassword.setTextColor(getResources().getColor(R.color.colorBlack));

                }
            }
        });
        B_sel_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                S_user_type="user";
                B_sel_user.setBackgroundResource(R.drawable.but_selected);
               B_sel_vendor.setBackgroundResource(R.drawable.but_unselected);
            }
        });
        B_sel_vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                S_user_type="vendor";

                B_sel_vendor.setBackgroundResource(R.drawable.but_selected);
                B_sel_user.setBackgroundResource(R.drawable.but_unselected);

            }
        });
        B_signup.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                SemailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

                if (isConnectedToNetwork()) {
                    if(ET_username.getText().toString().equals(""))
                    {
                        new PromptDialog(SignupActivity.this)
                                .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                .setAnimationEnable(true)
                                .setTitleText("Please Enter Username")
                                .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                    @Override
                                    public void onClick(PromptDialog dialog) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    }
                    else
                    {
                        if(ET_emailid.getText().toString().equals(""))
                        {
                            new PromptDialog(SignupActivity.this)
                                    .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                    .setAnimationEnable(true)
                                    .setTitleText("Please Enter Your Email Id")
                                    .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                        @Override
                                        public void onClick(PromptDialog dialog) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                        }
                        else
                        {
                            SemailInput = ET_emailid.getText().toString().trim();
                            if (SemailInput.matches(SemailPattern))
                            {
                                if(ET_password.getText().toString().equals(""))
                                {
                                    new PromptDialog(SignupActivity.this)
                                            .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                            .setAnimationEnable(true)
                                            .setTitleText("Please Enter Password")
                                            .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                                @Override
                                                public void onClick(PromptDialog dialog) {
                                                    dialog.dismiss();
                                                }
                                            }).show();
                                }
                                else
                                {
                                    if(ET_confirmpassword.getText().toString().equals(""))
                                    {
                                        new PromptDialog(SignupActivity.this)
                                                .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                                .setAnimationEnable(true)
                                                .setTitleText("Please Enter Confirm Password")
                                                .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                                    @Override
                                                    public void onClick(PromptDialog dialog) {
                                                        dialog.dismiss();
                                                    }
                                                }).show();
                                    }
                                    else
                                    {
                                        S_username=ET_username.getText().toString();
                                        S_emailid=ET_emailid.getText().toString();
                                        S_password=ET_password.getText().toString();
                                        S_confirmpassword=ET_confirmpassword.getText().toString();
                                        if (!S_password.equals(S_confirmpassword))
                                        {
                                            new PromptDialog(SignupActivity.this)
                                                    .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                                    .setAnimationEnable(true)
                                                    .setTitleText("Password Mismatch")
                                                    .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                                        @Override
                                                        public void onClick(PromptDialog dialog) {
                                                            dialog.dismiss();
                                                        }
                                                    }).show();
                                        }
                                        else
                                        {
                                            dialog.show();

                                            signup_twotr(S_user_type,S_username, S_emailid, S_password);

                                        }
                                    }
                                }
                            }
                            else
                            {
                                new PromptDialog(SignupActivity.this)
                                        .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                        .setAnimationEnable(true)
                                        .setTitleText("Please Check Your Email Id")
                                        .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                            @Override
                                            public void onClick(PromptDialog dialog) {
                                                dialog.dismiss();
                                            }
                                        }).show();
                            }
                        }
                    }
                }
                else
                {

                    setContentView(R.layout.content_if_nointernet);
                    ImageView but_retry = findViewById(R.id.nointernet_retry);
                    but_retry.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(SignupActivity.this, SignupActivity.class);
                            startActivity(intent);
                        }
                    });

                }
            }
        }
        );
    }
    private boolean isConnectedToNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
    public void signup_twotr(String s_user_type,String s_username,String s_emailid,String s_password) {

        try {

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONArray jsonArray= new JSONArray();
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("UserType", s_user_type);
            jsonBody.put("UserName", s_username);
            jsonBody.put("Email", s_emailid);
            jsonBody.put("Password", s_password);


            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("weqar_emailid",s_emailid);

            editor.apply();

            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.User_signup, new Response.Listener<String>() {

                public void onResponse(String response) {
                    dialog.hide();
                   // startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                    new PromptDialog(SignupActivity.this)
                            .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                            .setAnimationEnable(true)
                            .setTitleText("Weqar Welcomes You!")
                            .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                    startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                                }
                            }).show();


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.hide();
                    Log.i("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {

                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    // headers.put("content-Type", "application/json");
                 //   headers.put("X-API-TYPE", "Android");
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
