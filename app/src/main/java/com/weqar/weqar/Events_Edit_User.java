package com.weqar.weqar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;
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
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;
import com.weqar.weqar.Global_url_weqar.Global_URL;
import com.yalantis.ucrop.UCrop;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import cn.refactor.lib.colordialog.PromptDialog;


public class Events_Edit_User extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{
    ImageView iv_uedit_events_back,iv_editevent_image;
    TextView et_editevent_startdate,et_editevent_enddate,ET_pedit_location;
    EditText et_editevent_contactperson,et_editevent_title,et_editevent_amount,et_editevent_desc;
    Button but_editevent_update;
    String s_lnw_userid,s_lnw_usertoken,s_eventedit_uid,s_eventedit_id,s_eventedit_startdate,s_eventdedit_enddate,s_eventedit_contact,
        s_evenedit_title,s_eventedit_amount,s_eventedit_desc,s_eventedit_iamge,event_startdate_ones;
    int one,check_image_id,two;
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    private Calendar calendar;
    private SimpleDateFormat timeFormat;
    private static final String TIME_PATTERN = "HH:mm";
    int v_years,v_months,v_days,v_hours,v_minutes;
    private static final int SECOND_ACTIVITY_REQUEST_CODEs = 0;
ProgressDialog dialog;
    String v_edit_location,v_edit_locationo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events__edit__user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (isConnectedToNetwork()) {
            iv_uedit_events_back = findViewById(R.id.iv_uedit_events_back);
            iv_editevent_image = findViewById(R.id.iv_editevent_image);
            et_editevent_startdate = findViewById(R.id.et_editevent_startdate);
            et_editevent_enddate = findViewById(R.id.et_editevent_enddate);
            ET_pedit_location = findViewById(R.id.ET_pedit_location);
            et_editevent_contactperson = findViewById(R.id.et_editevent_contactperson);
            et_editevent_title = findViewById(R.id.et_editevent_title);
            et_editevent_amount = findViewById(R.id.et_editevent_amount);
            et_editevent_desc = findViewById(R.id.et_editevent_desc);
            but_editevent_update = findViewById(R.id.but_editevent_update);
            calendar = Calendar.getInstance();
            timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());


            dialog = new ProgressDialog(this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setMessage("Loading. Please wait...");
            Shared_user_details = getSharedPreferences("user_detail_mode", 0);
            s_lnw_userid = Shared_user_details.getString("sp_w_userid", null);
            s_lnw_usertoken = Shared_user_details.getString("sp_w_apikey", null);
            try {
                Intent intent = getIntent();
                s_eventedit_uid = intent.getStringExtra("put_event_uid_edit");
                s_eventedit_id = intent.getStringExtra("put_event_id_edit");
                s_eventedit_startdate = intent.getStringExtra("put_event_startdate_edit");
                s_eventdedit_enddate = intent.getStringExtra("put_event_enddate_edit");
                s_eventedit_contact = intent.getStringExtra("put_event_contact_edit");
                s_evenedit_title = intent.getStringExtra("put_event_title_edit");
                s_eventedit_amount = intent.getStringExtra("put_event_amount_edit");
                s_eventedit_desc = intent.getStringExtra("put_event_desc_edit");
                s_eventedit_iamge = intent.getStringExtra("put_event_image_edit");


                ET_pedit_location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent inten=new Intent(Events_Edit_User.this,SelectLocations.class);
                        startActivityForResult(inten, SECOND_ACTIVITY_REQUEST_CODEs);
                    }
                });

                String dash_event_start= DateTimeUtils.formatWithPattern(s_eventedit_startdate, "dd-MM-yyyy");
                String dash_eventisc_end=DateTimeUtils.formatWithPattern(s_eventdedit_enddate, "dd-MM-yyyy");
                String time_sched=s_eventedit_startdate.substring(11,16);
                String time_sched_end=s_eventdedit_enddate.substring(11,16);
                et_editevent_startdate.setText(dash_event_start+" "+time_sched);
                et_editevent_enddate.setText(dash_eventisc_end+" "+time_sched_end);

//                et_editevent_startdate.setText(s_eventedit_startdate);
//                et_editevent_enddate.setText(s_eventdedit_enddate);
                et_editevent_contactperson.setText(s_eventedit_contact);
                et_editevent_title.setText(s_evenedit_title);
                et_editevent_amount.setText(s_eventedit_amount);
                et_editevent_desc.setText(s_eventedit_desc);
                Picasso.with(this).load(Global_URL.Image_url_load + s_eventedit_iamge).error(getResources().getDrawable(R.drawable.rounded_two)).fit().centerCrop().into(iv_editevent_image);


            } catch (Exception e) {
                e.printStackTrace();
            }
            iv_uedit_events_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(Events_Edit_User.this, Events_Display.class));
                    finish();
                }
            });
            et_editevent_desc.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (v.getId() == R.id.et_editevent_desc) {
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
            et_editevent_startdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    one = 1;
                    showDate(2018, 0, 1, R.style.DatePickerSpinner);
                }
            });
            et_editevent_enddate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    one = 2;
                    showDate(2018, 0, 1, R.style.DatePickerSpinner);
                }
            });
            iv_editevent_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 1006);
                }
            });
            but_editevent_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callmetoupload_editevnts();
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
                    Intent intent = new Intent(Events_Edit_User.this, Events_Display.class);
                    startActivity(intent);
                }
            });
        }

    }
    @VisibleForTesting
    void showDate(int year, int monthOfYear, int dayOfMonth, int spinnerTheme) {
        calendar = Calendar.getInstance();
        v_years = calendar.get(Calendar.YEAR);

        v_months = calendar.get(Calendar.MONTH);
        v_days = calendar.get(Calendar.DAY_OF_MONTH);
        v_hours=calendar.get(Calendar.HOUR_OF_DAY);
        v_minutes=calendar.get(Calendar.MINUTE);

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
        event_startdate_ones = dayOfMonth+ "-" +(monthOfYear + 1)   + "-" + year;
        if (one == 1) {
            two=1;
            TimePickerDialog.newInstance((TimePickerDialog.OnTimeSetListener) this,v_hours,
                    v_minutes, true).show(getFragmentManager(), "timePicker");
        }
        else if (one == 2) {
            two=2;
            TimePickerDialog.newInstance((TimePickerDialog.OnTimeSetListener) this,v_hours,
                    v_minutes, true).show(getFragmentManager(), "timePicker");
        }
    }
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        if(two==1)
        {
            et_editevent_startdate.setText(event_startdate_ones+" "+timeFormat.format(calendar.getTime()));

        }
        else if(two==2)
        {
            et_editevent_enddate.setText(event_startdate_ones+" "+timeFormat.format(calendar.getTime()));

        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1006 && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();

            assert imageUri != null;
            UCrop.of( imageUri,  Uri.fromFile(new File(getCacheDir(), ".png")))
                    .withAspectRatio(3 , 2)
                    .start(Events_Edit_User.this);
            check_image_id=1006;
        }
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP && check_image_id==1006) {
            final Uri resultUri = UCrop.getOutput(data);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                iv_editevent_image.setImageBitmap(bitmap);
                upload_vendor_complete_image_s(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODEs) {
            if (resultCode == RESULT_OK) {
                v_edit_location=  Shared_user_details.getString("send_mylatitude", null);
                v_edit_locationo=  Shared_user_details.getString("send_longitude", null);

                ET_pedit_location.setText(v_edit_location+","+v_edit_locationo);
            }
        }


    }
    private void upload_vendor_complete_image_s(final Bitmap bitmap)
    {

        Bitmap immagex=bitmap;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);


        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("extension", "JPG");
            jsonBody.put("content", imageEncoded);
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.User_uploadprofessionalimage, new Response.Listener<String>() {
                public void onResponse(String response) {
                    try
                    {
                        JSONObject jObj = new JSONObject(response);
                        s_eventedit_iamge=jObj.getString("Response");


                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
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
                    headers.put("x-api-type", "Android");
                    //  headers.put("content-Type", "application/json");
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
    public void callmetoupload_editevnts()
    {
        try
        {



        if (et_editevent_startdate.getText().toString().equals("")) {
            new PromptDialog(Events_Edit_User.this)
                    .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                    .setAnimationEnable(true)
                    .setTitleText("Please Choose Start Date")
                    .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                        @Override
                        public void onClick(PromptDialog dialog) {
                            dialog.dismiss();
                        }
                    }).show();
        } else {
            if (et_editevent_enddate.equals("")) {
                new PromptDialog(Events_Edit_User.this)
                        .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                        .setAnimationEnable(true)
                        .setTitleText("Please Choose End Date")
                        .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                            @Override
                            public void onClick(PromptDialog dialog) {
                                dialog.dismiss();
                            }
                        }).show();
            } else {
                if (et_editevent_contactperson.getText().toString().equals("")) {
                    new PromptDialog(Events_Edit_User.this)
                            .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                            .setAnimationEnable(true)
                            .setTitleText("Please Enter Contact Name")
                            .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    if (et_editevent_title.getText().toString().equals("")) {
                        new PromptDialog(Events_Edit_User.this)
                                .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                .setAnimationEnable(true)
                                .setTitleText("Please Enter Event title")
                                .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                    @Override
                                    public void onClick(PromptDialog dialog) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    }
                    else {
                        if(et_editevent_amount.getText().toString().equals("")) {
                            new PromptDialog(Events_Edit_User.this)
                                    .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                    .setAnimationEnable(true)
                                    .setTitleText("Please Enter Amount")
                                    .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                        @Override
                                        public void onClick(PromptDialog dialog) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                        }


                        else{
                            if(s_eventedit_iamge.equals(""))
                            {
                                new PromptDialog(Events_Edit_User.this)
                                        .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                        .setAnimationEnable(true)
                                        .setTitleText("Please Select Image")
                                        .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                            @Override
                                            public void onClick(PromptDialog dialog) {
                                                dialog.dismiss();
                                            }
                                        }).show();
                            }
                            else
                            {

                                s_eventedit_startdate=et_editevent_startdate.getText().toString();
                                s_eventdedit_enddate=et_editevent_enddate.getText().toString();
                                s_eventedit_contact=et_editevent_contactperson.getText().toString();
                                s_evenedit_title=et_editevent_title.getText().toString();
                                s_eventedit_amount=et_editevent_amount.getText().toString();
                                s_eventedit_desc=et_editevent_desc.getText().toString();
                                dialog.show();

                                callmetouploadvendorcomplete_url(s_lnw_userid,s_eventedit_id,s_eventedit_startdate,s_eventdedit_enddate,s_eventedit_contact,
                                        s_evenedit_title,
                                        s_eventedit_amount,s_eventedit_desc,s_eventedit_iamge,v_edit_location,v_edit_locationo);

                            }

                        }
                    }

                }
            }
        }
        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public void callmetouploadvendorcomplete_url(final String s_lnw_user_ids,final String id,
                                                 final String start,final String end,
                                                 final String contact,final String title,
                                                 final String amount,final String desc,final String image,final String lati,final String longi)
    {
        try {

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Id", id);
            jsonBody.put("UserId", s_lnw_user_ids);
            jsonBody.put("EventStart", start);
            jsonBody.put("EventEnd", end);
            jsonBody.put("Name", contact);
            jsonBody.put("Image", image);
            jsonBody.put("Title", title);
            jsonBody.put("Description", desc);
            jsonBody.put("Amount", amount);
            jsonBody.put("Latitude", lati);
            jsonBody.put("Longitude", longi);





            final String requestBody = jsonBody.toString();
            Log.i("checkandro",requestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.user_event_edit, new Response.Listener<String>() {

                public void onResponse(String response) {
                    dialog.hide();

                    new PromptDialog(Events_Edit_User.this)
                            .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                            .setAnimationEnable(true)
                            .setTitleText("Event Edited Successfully")
                            .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                   startActivity(new Intent(Events_Edit_User.this,Events_Display.class));
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
                    headers.put("x-api-type", "Android");
                    //headers.put("content-Type", "application/json");
                    headers.put("x-api-key",s_lnw_usertoken);
                    Log.i("checkandroheader",headers.toString());

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
