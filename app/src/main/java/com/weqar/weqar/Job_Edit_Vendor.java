package com.weqar.weqar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;
import com.weqar.weqar.Global_url_weqar.Global_URL;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import cn.refactor.lib.colordialog.PromptDialog;


public class Job_Edit_Vendor extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
EditText ET_editjobs_title,ET_editjobs_description;
TextView TV_editjob_jobtype,TV_editjob_jobfield,TV_editjob_closingdate;
Button BUT_edijob_update;
String s_lnw_userid,s_lnw_usertoken,s_edijob_jobid,s_edijob_jobname,s_editjob_type,s_editjob_field,s_editjob_descritiio,s_editjob_compnayinfo,
    s_editjob_clpsingdate;
String ss_jobname,ss_jobtypeid,ss_jobtypename,ss_jobfieldname,ss_jobfieldid,ss_jobdesc,ss_compnayinfo,
    ss_jobclosingdate;
    String Ssubjectkind,Ssubjectkinds;
    String subjectnamelist;
    String subjectnameid;
    String subjectnamelists;
    String subjectnameids;
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    ImageView IB_back;
    int v_years,v_months,v_days;
    Calendar calendar;
    ProgressDialog dialog;
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    private static final int SECOND_ACTIVITY_REQUEST_CODE_two = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job__edit__vendor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if (isConnectedToNetwork()) {

            ET_editjobs_title = findViewById(R.id.et_veditjobs_title);
            ET_editjobs_description = findViewById(R.id.et_veditjobs_description);
           // ET_editjobs_compnayinfo = findViewById(R.id.et_veditjobs_companyinfo);
            TV_editjob_jobtype = findViewById(R.id.tv_veditjobs_jobtype);
            TV_editjob_jobfield = findViewById(R.id.tv_veditjobs_jobfield);
            TV_editjob_closingdate = findViewById(R.id.tv_veditjobs_closingdate);
            BUT_edijob_update = findViewById(R.id.but_veditjobs_update);
            dialog = new ProgressDialog(this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setMessage("Loading. Please wait...");
            IB_back = findViewById(R.id.iv_vaddjobs_back);
            IB_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Job_Edit_Vendor.this,HomeScreen_vendor.class)
                    );
                    finish();
                }
            });
            Intent intent = getIntent();

            s_edijob_jobid = intent.getStringExtra("put_jobid_forjob_edit");
            s_edijob_jobname = intent.getStringExtra("put_jobname_forjob_edit");
            s_editjob_type = intent.getStringExtra("put_jobtype_forjob_edit");
            s_editjob_field = intent.getStringExtra("put_jobfield_forjob_edit");

            subjectnameid = intent.getStringExtra("put_jobtypeid_forjob_edit");
            subjectnameids = intent.getStringExtra("put_jobfieldid_forjob_edit");

            s_editjob_descritiio = intent.getStringExtra("put_jobdesc_forjob_edit");
            s_editjob_compnayinfo = intent.getStringExtra("put_companyinfo_forjob_edit");
            s_editjob_clpsingdate = intent.getStringExtra("put_closingdate_forjob_edit");
            Shared_user_details = getSharedPreferences("user_detail_mode", 0);
            s_lnw_userid = Shared_user_details.getString("sp_w_userid", null);
            s_lnw_usertoken = Shared_user_details.getString("sp_w_apikey", null);

            ET_editjobs_title.setText(s_edijob_jobname);
            ET_editjobs_description.setText(s_editjob_descritiio);
         //   ET_editjobs_compnayinfo.setText(s_editjob_compnayinfo);
            TV_editjob_jobtype.setText(s_editjob_type);
            TV_editjob_jobfield.setText(s_editjob_field);
            TV_editjob_closingdate.setText(s_editjob_clpsingdate);


//            ET_editjobs_compnayinfo.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    if (v.getId() == R.id.et_veditjobs_companyinfo) {
//                        v.getParent().requestDisallowInterceptTouchEvent(true);
//                        switch (event.getAction() & MotionEvent.ACTION_MASK) {
//                            case MotionEvent.ACTION_UP:
//                                v.getParent().requestDisallowInterceptTouchEvent(false);
//                                break;
//                        }
//                    }
//                    return false;
//                }
//            });
            ET_editjobs_description.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (v.getId() == R.id.et_veditjobs_description) {
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        switch (event.getAction() & MotionEvent.ACTION_MASK) {
                            case MotionEvent.ACTION_UP:
                                v.getParent().requestDisallowInterceptTouchEvent(false);
                                break;
                        }
                    }
                    return false;
                }
            });
            BUT_edijob_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callmetoeditjob();

                }
            });
            TV_editjob_jobtype.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Job_Edit_Vendor.this, MultiSpinner_Vendor_JobType.class);
                    startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
                }
            });

            TV_editjob_jobfield.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Job_Edit_Vendor.this, MultiSpinner_Vendor_JobField.class);
                    startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE_two);
                }
            });

            TV_editjob_closingdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    
                    showDate(2018, 0, 1, R.style.DatePickerSpinner);
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
                    Intent intent = new Intent(Job_Edit_Vendor.this, HomeScreen_vendor.class);
                    startActivity(intent);
                }
            });


        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                TinyDB tinydb = new TinyDB(this);
                Ssubjectkind=tinydb.getString("jobtype");
                if (Ssubjectkind.equals("job")) {
                    subjectnamelist = tinydb.getString("vendoraddjobs_name");
                    subjectnameid = tinydb.getString("vendoraddjobs_id");
                    StringBuilder builder = new StringBuilder();

                    builder.append("").append(subjectnamelist).append("");

                    TV_editjob_jobtype.setText(builder);
                }


            }
        }
        else if (requestCode == SECOND_ACTIVITY_REQUEST_CODE_two) {
            if (resultCode == RESULT_OK) {
                TinyDB tinydb = new TinyDB(this);
                Ssubjectkinds=tinydb.getString("jobfield");
                if (Ssubjectkinds.equals("jobF")) {
                    subjectnamelists = tinydb.getString("vendoraddjobsfield_name");
                    subjectnameids = tinydb.getString("vendoraddjobsfield_id");
                    StringBuilder builder = new StringBuilder();

                    builder.append("").append(subjectnamelists).append("");

                    TV_editjob_jobfield.setText(builder);
                }


            }
        }
        else
        {
            Toast.makeText(this, "Check me", Toast.LENGTH_SHORT).show();
        }
    }

    @VisibleForTesting
    void showDate(int year, int monthOfYear, int dayOfMonth, int spinnerTheme) {
        calendar = Calendar.getInstance();
        v_years = calendar.get(Calendar.YEAR);

        v_months = calendar.get(Calendar.MONTH);
        v_days = calendar.get(Calendar.DAY_OF_MONTH);

        new SpinnerDatePickerDialogBuilder()
                .context(this)
                .callback((DatePickerDialog.OnDateSetListener) this)
                .spinnerTheme(spinnerTheme)
                .defaultDate(v_years, v_months, v_days)
                .minDate(v_years, v_months, v_days)
                .build()
                .show();
    }

    @Override
    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        String date =dayOfMonth +"-"+(monthOfYear+1)+"-"+year;

        TV_editjob_closingdate.setText(date);


    }
   public void callmetoeditjob()
   {
       if(ET_editjobs_title.getText().toString().equals(""))
       {

           new PromptDialog(Job_Edit_Vendor.this)
                   .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                   .setAnimationEnable(true)
                   .setTitleText("Please Add Job Title")
                   .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                       @Override
                       public void onClick(PromptDialog dialog) {
                           dialog.dismiss();
                       }
                   }).show();
       }
       else
       {
           if(TV_editjob_jobtype.getText().toString().equals(""))
           {

               new PromptDialog(Job_Edit_Vendor.this)
                       .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                       .setAnimationEnable(true)
                       .setTitleText("Please Select Job Type")
                       .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                           @Override
                           public void onClick(PromptDialog dialog) {
                               dialog.dismiss();
                           }
                       }).show();
           }
           else
           {
               if(TV_editjob_jobfield.getText().toString().equals(""))
               {

                   new PromptDialog(Job_Edit_Vendor.this)
                           .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                           .setAnimationEnable(true)
                           .setTitleText("Please Select Job Field")
                           .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                               @Override
                               public void onClick(PromptDialog dialog) {
                                   dialog.dismiss();
                               }
                           }).show();
               }
               else
               {
//                   if(ET_editjobs_compnayinfo.getText().toString().equals(""))
//                   {
//
//                       new PromptDialog(Job_Edit_Vendor.this)
//                               .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
//                               .setAnimationEnable(true)
//                               .setTitleText("Please Enter Company Info")
//                               .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
//                                   @Override
//                                   public void onClick(PromptDialog dialog) {
//                                       dialog.dismiss();
//                                   }
//                               }).show();
//                   }
//                   else
//                   {
                       if(TV_editjob_closingdate.getText().toString().equals(""))
                       {

                           new PromptDialog(Job_Edit_Vendor.this)
                                   .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                                   .setAnimationEnable(true)
                                   .setTitleText("Please Select Closing Date")
                                   .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                       @Override
                                       public void onClick(PromptDialog dialog) {
                                           dialog.dismiss();
                                       }
                                   }).show();
                       }
                       else
                       {
                           if(ET_editjobs_description.getText().toString().equals(""))
                           {

                               new PromptDialog(Job_Edit_Vendor.this)
                                       .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                                       .setAnimationEnable(true)
                                       .setTitleText("Please Enter Job Description")
                                       .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                           @Override
                                           public void onClick(PromptDialog dialog) {
                                               dialog.dismiss();
                                           }
                                       }).show();
                           }
                           else
                           {
                               ss_jobname=ET_editjobs_title.getText().toString();
                               ss_jobdesc=ET_editjobs_description.getText().toString();
                              // ss_compnayinfo=ET_editjobs_compnayinfo.getText().toString();
                               ss_jobclosingdate=TV_editjob_closingdate.getText().toString();
dialog.show();

                               callmetouploadedijob_url(s_lnw_userid,s_edijob_jobid,ss_jobname,subjectnameid,subjectnameids,
                                       ss_jobdesc,ss_jobclosingdate);
                           }
                       }
                  // }
               }

           }
       }
   }
    public void callmetouploadedijob_url(String uid, String id, String name, String type, String field,
                                          String desc,String clodsingdate)
    {

        try {


            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("UserId", uid);
            jsonBody.put("Id", id);
            jsonBody.put("Name", name);
            jsonBody.put("JobTypeId", type);
            jsonBody.put("JobFieldId", field);
            jsonBody.put("Description",desc);
           // jsonBody.put("CompanyInfo", info);
            jsonBody.put("ClosingDate", clodsingdate);



            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.Vendor_job_edit, new Response.Listener<String>() {

                public void onResponse(String response) {
                    // startActivity(new Intent(ProfileInfo.this, LoginActivity.class));
                    dialog.hide();
                    Log.i("basic_details_response",response);
                    new PromptDialog(Job_Edit_Vendor.this)
                            .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                            .setAnimationEnable(true)
                            .setTitleText("Job Edited Successfully")
                            .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                    startActivity(new Intent(Job_Edit_Vendor.this,HomeScreen_vendor.class));
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
                    headers.put("X-API-TYPE", "Android");
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
        }catch (JSONException e){

        }
    }
    private boolean isConnectedToNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
