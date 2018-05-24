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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import cn.refactor.lib.colordialog.PromptDialog;


public class AddJobs_Vendor extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    ImageView IV_vaddjobs_back;
    EditText ET_vaddjobs_title,ET_vaddjobs_desc;
    TextView TV_vaddjobs_closingdate,TV_vaddjobs_jobtype,TV_vaddjobs_jobfield;
    Button But_add;
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    private static final int SECOND_ACTIVITY_REQUEST_CODE_two = 1;
    String Ssubjectkind,Ssubjectkinds,s_lnw_usertoken,s_lnw_userid;
     String subjectnamelist;
     String subjectnameid;
  String subjectnamelists;
    String subjectnameids;
    SimpleDateFormat simpleDateFormat;
    int one,v_years,v_months,v_days;
    String s_jobtitle,s_jobtype,s_jobfield,s_jobstartingdate,s_closingdate,s_desc;
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    Calendar calendar;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jobs__vendor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (isConnectedToNetwork()) {

            IV_vaddjobs_back = findViewById(R.id.iv_vaddjobs_back);
            ET_vaddjobs_title = findViewById(R.id.et_vaddjobs_title);
            ET_vaddjobs_desc = findViewById(R.id.et_vaddjobs_desc);
        //    ET_vaddjobs_companyinfo = findViewById(R.id.et_vaddjobs_companyinfo);
            TV_vaddjobs_jobtype = findViewById(R.id.tv_vaddjobs_jobtype);
            TV_vaddjobs_jobfield = findViewById(R.id.tv_vaddjobs_jobfield);
           // TV_vaddjobs_openingdate = findViewById(R.id.tv_vaddjobs_openingdate);
            TV_vaddjobs_closingdate = findViewById(R.id.tv_vaddjobs_closingdate);
            But_add = findViewById(R.id.but_vaddjobs_add);
            simpleDateFormat = new SimpleDateFormat("dd MM yyyy", Locale.US);
            Shared_user_details = getSharedPreferences("user_detail_mode", 0);


            dialog = new ProgressDialog(this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setMessage("Loading. Please wait...");

            s_lnw_userid = Shared_user_details.getString("sp_w_userid", null);
            s_lnw_usertoken = Shared_user_details.getString("sp_w_apikey", null);

//
//
//            ET_vaddjobs_companyinfo.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    if (v.getId() == R.id.et_vaddjobs_companyinfo) {
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
            ET_vaddjobs_desc.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (v.getId() == R.id.et_vaddjobs_desc) {
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

            IV_vaddjobs_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(AddJobs_Vendor.this,HomeScreen_vendor.class)
                    );
                    finish();
                }
            });
            TV_vaddjobs_jobtype.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddJobs_Vendor.this, MultiSpinner_Vendor_JobType.class);
                    startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
                }
            });

            TV_vaddjobs_jobfield.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddJobs_Vendor.this, MultiSpinner_Vendor_JobField.class);
                    startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE_two);
                }
            });
//            TV_vaddjobs_openingdate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    one = 1;
//                    showDate(2018, 0, 1, R.style.DatePickerSpinner);
//                }
//            });
            TV_vaddjobs_closingdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 //   one = 2;
                    showDate(2018, 0, 1, R.style.DatePickerSpinner);
                }
            });
            But_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callmetoupload_addjob();
                }
            });
        }else {
            setContentView(R.layout.content_if_nointernet);
            ImageView but_retry = findViewById(R.id.nointernet_retry);
            but_retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddJobs_Vendor.this, AddJobs_Vendor.class);
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


                        builder.append("").append(subjectnamelist);



                    TV_vaddjobs_jobtype.setText(builder);
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


                        builder.append("").append(subjectnamelists);



                    TV_vaddjobs_jobfield.setText(builder);
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

            TV_vaddjobs_closingdate.setText(date);

    }
    public void callmetoupload_addjob()
    {
        if(ET_vaddjobs_title.getText().toString().equals(""))
        {

            new PromptDialog(AddJobs_Vendor.this)
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
            if(TV_vaddjobs_jobtype.getText().toString().equals(""))
            {

                new PromptDialog(AddJobs_Vendor.this)
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
                if(TV_vaddjobs_jobfield.getText().toString().equals(""))
                {

                    new PromptDialog(AddJobs_Vendor.this)
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
//                    if(TV_vaddjobs_openingdate.getText().toString().equals(""))
//                    {
//
//                        new PromptDialog(AddJobs_Vendor.this)
//                                .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
//                                .setAnimationEnable(true)
//                                .setTitleText("Please Select Opening Date")
//                                .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
//                                    @Override
//                                    public void onClick(PromptDialog dialog) {
//                                        dialog.dismiss();
//                                    }
//                                }).show();
//                    }
//                    else
//                    {
                        if(TV_vaddjobs_closingdate.getText().toString().equals(""))
                        {

                            new PromptDialog(AddJobs_Vendor.this)
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
                            if(ET_vaddjobs_desc.getText().toString().equals(""))
                            {

                                new PromptDialog(AddJobs_Vendor.this)
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
                                s_jobtitle=ET_vaddjobs_title.getText().toString();
                                s_jobtype=TV_vaddjobs_jobtype.getText().toString();
                                s_jobfield=TV_vaddjobs_jobfield.toString();
                              //  s_jobstartingdate=TV_vaddjobs_openingdate.getText().toString();
                                s_closingdate=TV_vaddjobs_closingdate.getText().toString();
                                s_desc=ET_vaddjobs_desc.getText().toString();
                                //S_compnayinfo=ET_vaddjobs_companyinfo.getText().toString();
                                dialog.show();
                               callmetoupload_addjob_url(s_lnw_userid,s_jobtitle,subjectnameid,subjectnameids,
                                       s_desc,s_closingdate);
                            }
                        }
                }
            }
        }
    }
 public void callmetoupload_addjob_url(String id, String title, String jobtype, String jobfield, String description, String closingdate)
 {

     try {


         RequestQueue requestQueue = Volley.newRequestQueue(this);
         JSONArray jsonArray = new JSONArray();
         JSONObject jsonBody = new JSONObject();
         jsonBody.put("UserId", id);
         jsonBody.put("Name", title);
         jsonBody.put("JobTypeId", jobtype);
         jsonBody.put("JobFieldId", jobfield);
         jsonBody.put("Description", description);
         jsonBody.put("ClosingDate", closingdate);



         final String requestBody = jsonBody.toString();

         StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.Vendor_addjobs, new Response.Listener<String>() {

             public void onResponse(String response) {
                 dialog.hide();
                 // startActivity(new Intent(ProfileInfo.this, LoginActivity.class));
                 Log.i("basic_details_response",response);
                 new PromptDialog(AddJobs_Vendor.this)
                         .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                         .setAnimationEnable(true)
                         .setTitleText("Job Added Successfully")
                         .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                             @Override
                             public void onClick(PromptDialog dialog) {
                                startActivity(new Intent(AddJobs_Vendor.this,HomeScreen_vendor.class));
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

