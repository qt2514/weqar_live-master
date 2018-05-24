package com.weqar.weqar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import cn.refactor.lib.colordialog.PromptDialog;
import de.hdodenhof.circleimageview.CircleImageView;


public class JobDetails_User extends AppCompatActivity {
    ImageView IV_jobdetails_back;
    CircleImageView CIV_Ujobdet_logo;
    TextView TV_ujobdet_jobtype,TV_ujob_jobdeadline,TV_ujob_desc,TV_job_name;
           // TV_ujobdet_jobfield,
    String s_jobdet_logo,s_jobdet_jobtype,s_jobdet_jobfield,s_jobdet_jobdeadline,s_jobdet_desc,s_jobdet_name,s_user_id,s_job_id,
                   s_user_token;
    Button But_apply_job_user;
    SharedPreferences Shared_user_details;
    Boolean put_jobs_user_applied;
ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (isConnectedToNetwork()) {

            IV_jobdetails_back = findViewById(R.id.job_details_back);
            IV_jobdetails_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            Shared_user_details = getSharedPreferences("user_detail_mode", 0);
            s_user_id = Shared_user_details.getString("weqar_uid", null);
            s_user_token = Shared_user_details.getString("weqar_token", null);
            CIV_Ujobdet_logo = findViewById(R.id.logo_user_jobdet);
            TV_ujobdet_jobtype = findViewById(R.id.text_jobtype_user_jobdet);
            TV_job_name = findViewById(R.id.title_jobdesc_user);
           // TV_ujobdet_jobfield = findViewById(R.id.text_jobfield_user_jobdet);
            TV_ujob_jobdeadline = findViewById(R.id.text_deadline_user_jobdet);
            TV_ujob_desc = findViewById(R.id.text_desc_user_jobdet);
            But_apply_job_user = findViewById(R.id.apply_job_user);
            dialog = new ProgressDialog(this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setMessage("Loading. Please wait...");
            Intent intent = getIntent();
            s_jobdet_logo = intent.getStringExtra("put_jobs_user_logo");
            s_jobdet_jobtype = intent.getStringExtra("put_jobs_user_jobtype");
            s_jobdet_name = intent.getStringExtra("put_jobs_user_jobname");
            s_jobdet_jobfield = intent.getStringExtra("put_jobs_user_jobfield");
            s_jobdet_jobdeadline = intent.getStringExtra("put_jobs_user_deadline");
            s_jobdet_desc = intent.getStringExtra("put_jobs_user_desc");
            s_job_id = intent.getStringExtra("put_jobs_user_id");
            put_jobs_user_applied=intent.getBooleanExtra("put_jobs_user_applied",false);
if (put_jobs_user_applied)
{
    But_apply_job_user.setVisibility(View.GONE);

}
else
{
    But_apply_job_user.setVisibility(View.VISIBLE);
}
            But_apply_job_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
callmetojobapply(s_user_id,s_job_id);
                }
            });

            try {

                Picasso.with(this).load(Global_URL.Image_url_load + s_jobdet_logo).error(getResources().getDrawable(R.drawable.rounded)).fit().centerCrop().into(CIV_Ujobdet_logo);
                TV_ujobdet_jobtype.setText(s_jobdet_name);
                //TV_ujobdet_jobfield.setText(s_jobdet_jobtype);
                TV_ujob_jobdeadline.setText("Deadline : "+s_jobdet_jobdeadline);
                TV_ujob_desc.setText(s_jobdet_desc);
                TV_job_name.setText(s_jobdet_jobtype);

            } catch (Exception e) {
            }
        }
        else
        {


            setContentView(R.layout.content_if_nointernet);
            ImageView but_retry = findViewById(R.id.nointernet_retry);
            but_retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(JobDetails_User.this, HomeScreen.class);
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
    public void callmetojobapply(String userid, String discid)
    {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(JobDetails_User.this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Id", discid);
            jsonBody.put("UserId", userid);

            final String requestBody = jsonBody.toString();
            Log.i("muomuao",requestBody);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.user_job_apply, new Response.Listener<String>() {
                public void onResponse(String response) {
                    Log.i("muomuo",response);
                    new PromptDialog(JobDetails_User.this)
                            .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                            .setAnimationEnable(true)
                            .setContentText("Your request is Successfull")
                            .setPositiveListener(("Ok"), new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
//                                    AppCompatActivity activity = (AppCompatActivity) getActivity();
//                                    Fragment myFragment = new BotNav_DiscountsFragment_Vendor();
//                                    activity.getSupportFragmentManager().beginTransaction()
//                                            .replace(R.id.contentContainer, myFragment).addToBackStack(null).commit();
                                    dialog.dismiss();
                                }
                            }).show();
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
                    // headers.put("x-tutor-app-id", "tutor-app-android");
                    headers.put("x-api-type","Android");
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
    }
}
