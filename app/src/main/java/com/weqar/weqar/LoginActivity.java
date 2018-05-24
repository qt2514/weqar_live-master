package com.weqar.weqar;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
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
import com.wang.avi.AVLoadingIndicatorView;
import com.weqar.weqar.DBHandlers.SessionManager;
import com.weqar.weqar.Global_url_weqar.Global_URL;
import com.weqar.weqar.JavaClasses.AbsRuntimePermission;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import at.markushi.ui.CircleButton;
import cn.refactor.lib.colordialog.PromptDialog;

public class LoginActivity extends AbsRuntimePermission {
    EditText ET_username,ET_password;
    Button But_newaxccount,but_guest;
    CircleButton But_login;
    String S_username,S_password;
    private static final int REQUEST_PERMISSION = 10;

    String s_ln_userid,s_ln_username,s_ln_usermail,s_ln_usertype,s_ln_usertoken,s_username,s_image;
    Boolean s_ln_tab1,s_ln_tab2,s_ln_tab3;
    private SessionManager session;
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;

    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_login);
        ET_username = findViewById(R.id.login_username);
        ET_password = findViewById(R.id.login_password);
        But_newaxccount = findViewById(R.id.but_newaccount);
        but_guest = findViewById(R.id.but_guest);
        But_login = findViewById(R.id.login_but);
        requestAppPermissions(new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION

                },

                R.string.msg,REQUEST_PERMISSION);
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait...");
        session = new SessionManager(getApplicationContext());
        Shared_user_details = getSharedPreferences("user_detail_mode", 0);
        if (session.isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, ProfileInfo.class);
            startActivity(intent);
        }
        if (isConnectedToNetwork()) {
        ET_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ET_username.setBackgroundResource(R.drawable.edittext_selected);
                    ET_username.setTextColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    ET_username.setBackgroundResource(R.drawable.edittext_unselected);
                    ET_username.setTextColor(getResources().getColor(R.color.colorBlack));

                }

            }
        });
        ET_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ET_password.setBackgroundResource(R.drawable.edittext_selected);
                    ET_password.setTextColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    ET_password.setBackgroundResource(R.drawable.edittext_unselected);
                    ET_password.setTextColor(getResources().getColor(R.color.colorBlack));

                }
            }
        });
        But_newaxccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
            but_guest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LoginActivity.this,HomeScreen_Guest.class));
                }
            });
        But_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (ET_username.getText().toString().equals("")) {
                        new PromptDialog(LoginActivity.this)
                                .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                .setAnimationEnable(true)
                                .setTitleText("Please Enter Username")
                                .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                    @Override
                                    public void onClick(PromptDialog dialog) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    } else {
                        if (ET_password.getText().toString().equals("")) {
                            new PromptDialog(LoginActivity.this)
                                    .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                    .setAnimationEnable(true)
                                    .setTitleText("Please Enter Password")
                                    .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                        @Override
                                        public void onClick(PromptDialog dialog) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                        } else {
                            S_username = ET_username.getText().toString();
                            S_password = ET_password.getText().toString();
                            signin_verif(S_username, S_password);
                            dialog.show();
//                                 startActivity(new Intent(LoginActivity.this,HomeScreen.class));

                        }
                    }
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
                    Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });


        }
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    private boolean isConnectedToNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
    public void signin_verif(String susername, String spass)
    {

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("UserName", susername);
            jsonBody.put("Password", spass);
            jsonBody.put("DeviceId", "dummyid");
            jsonBody.put("DeviceType", "android");


            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.User_signin, new Response.Listener<String>() {

                public void onResponse(String response) {
                    try {

                        JSONObject jObj = new JSONObject(response);

                        String status = jObj.getString("Status");
                        if(status.equals("success"))
                        {
                            JSONObject verification = jObj.getJSONObject("Response");
                            s_ln_username=verification.getString("UserName");
                            s_ln_userid=verification.getString("Id");
                            if(s_ln_userid.equals("00000000-0000-0000-0000-000000000000"))
                            {
                                dialog.dismiss();
                                new PromptDialog(LoginActivity.this)
                                        .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                        .setAnimationEnable(true)
                                        .setTitleText(s_ln_username)
                                        .setPositiveListener(("Ok"), new PromptDialog.OnPositiveListener() {
                                            @Override
                                            public void onClick(PromptDialog dialog) {
                                                dialog.dismiss();

                                            }
                                        }).show();
                            }
                            else {
                                session.setLogin(true);
                                s_ln_usermail = verification.getString("Email");
                                s_ln_usertype = verification.getString("UserType");
                                s_ln_usertoken = verification.getString("APIKey");
                                s_ln_tab1 = verification.getBoolean("Tab1");
                                s_ln_tab2 = verification.getBoolean("Tab2");
                                s_ln_tab3 = verification.getBoolean("Tab3");
                                s_username = verification.getString("UserName");
                                s_image = verification.getString("Image");
                                dialog.dismiss();
                                new PromptDialog(LoginActivity.this)
                                        .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                                        .setAnimationEnable(true)
                                        .setTitleText("Login Success")


                                        .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                            @Override
                                            public void onClick(PromptDialog dialog) {


                                                Intent intent = new Intent(LoginActivity.this, ProfileInfo.class);
                                                intent.putExtra("w_userid", s_ln_userid);
                                                intent.putExtra("w_useremail", s_ln_usermail);
                                                intent.putExtra("w_usertype", s_ln_usertype);
                                                intent.putExtra("APIKey", s_ln_usertoken);
                                                intent.putExtra("login_tab1", s_ln_tab1);
                                                intent.putExtra("login_tab2", s_ln_tab2);
                                                intent.putExtra("login_tab3", s_ln_tab3);
                                                editor = Shared_user_details.edit();
                                                editor.putString("sp_w_usertype", s_ln_usertype);
                                                editor.putString("sp_w_useremail", s_ln_usermail);
                                                editor.putString("sp_w_userid", s_ln_userid);
                                                editor.putString("sp_w_apikey", s_ln_usertoken);
                                                editor.putString("sp_w_username", s_username);
                                                editor.putString("sp_w_image", s_image);
                                                editor.putBoolean("login_tab1", s_ln_tab1);
                                                editor.putBoolean("login_tab2", s_ln_tab2);
                                                editor.putBoolean("login_tab3", s_ln_tab3);

                                                editor.apply();
                                                editor.commit();
                                                startActivity(intent);
                                            }
                                        }).show();
                            }
                        }
                        else
                        {
                            String verification = jObj.getString("Response");
                       dialog.dismiss();

                            new PromptDialog(LoginActivity.this)
                                .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                .setAnimationEnable(true)
                                .setTitleText("Login Failed!")
                                .setContentText(verification)
                                .setPositiveListener(("Ok"), new PromptDialog.OnPositiveListener() {
                                    @Override
                                    public void onClick(PromptDialog dialog) {
                                        dialog.dismiss();
                                    }
                                }).show();

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
                    dialog.dismiss();

                    new PromptDialog(LoginActivity.this)
                            .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                            .setAnimationEnable(true)
                            .setTitleText("Login Failed!")
                            .setPositiveListener(("Ok"), new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                    dialog.dismiss();
                                }
                            }).show();

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
                   // headers.put("x-tutor-app-id", "tutor-app-android");
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

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
