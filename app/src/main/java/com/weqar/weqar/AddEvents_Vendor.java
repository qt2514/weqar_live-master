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
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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


public class AddEvents_Vendor extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {
    TextView TV_eventstart, TV_evenetend,ET_pedit_location;
    EditText ET_contactname, ET_title, ET_amount, ET_descrition,et_addevent_prerequirements;
    ImageView IV_imageupload;
    Button But_upolad;
    String s_eventstart, s_eventend, s_contactname, s_title, s_amount, s_descrition, s_image,s_prerequiremet;
    Boolean is_reg_Req,is_amount_req;
    int one,check_image_id,two;
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    String s_lnw_userid, s_lnw_usertoken,event_startdate_one;
    ImageView IV_back;
    private Calendar calendar;
    private SimpleDateFormat timeFormat;
    private static final String TIME_PATTERN = "HH:mm";
    int v_years,v_months,v_days,v_hours,v_minutes;
    private static final int SECOND_ACTIVITY_REQUEST_CODEs = 0;
ProgressDialog dialog;
    String v_edit_location,v_edit_locationo;

    private CheckBox addevent_registrationrequired,addevent_amountrequired;
    TextInputLayout tet_addevent_amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_events__vendor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (isConnectedToNetwork()) {

            TV_eventstart = findViewById(R.id.et_addevent_startdate);
            TV_evenetend = findViewById(R.id.et_addevent_enddate);
            ET_contactname = findViewById(R.id.et_addevent_contactperson);
            ET_pedit_location = findViewById(R.id.ET_pedit_location);
            ET_title = findViewById(R.id.et_addevent_title);
            ET_amount = findViewById(R.id.et_addevent_amount);
            ET_descrition = findViewById(R.id.et_addevent_desc);
            IV_imageupload = findViewById(R.id.iv_addevent_image);
            But_upolad = findViewById(R.id.but_addevent_add);
            calendar = Calendar.getInstance();
            timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());
            et_addevent_prerequirements=findViewById(R.id.et_addevent_prerequirements);
            addevent_registrationrequired=findViewById(R.id.addevent_registrationrequired);
            addevent_amountrequired=findViewById(R.id.addevent_amountrequired);
            tet_addevent_amount=findViewById(R.id.tet_addevent_amount);
            dialog = new ProgressDialog(this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setMessage("Loading. Please wait...");
            Shared_user_details = getSharedPreferences("user_detail_mode", 0);
            IV_back = findViewById(R.id.iv_vaddjobs_back);
            ET_pedit_location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent inten=new Intent(AddEvents_Vendor.this,SelectLocations.class);
                    startActivityForResult(inten, SECOND_ACTIVITY_REQUEST_CODEs);
                }
            });
            IV_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(AddEvents_Vendor.this,HomeScreen_vendor.class));
                    finish();
                }
            });
            s_lnw_userid = Shared_user_details.getString("sp_w_userid", null);
            s_lnw_usertoken = Shared_user_details.getString("sp_w_apikey", null);
            ET_amount.setVisibility(View.GONE);//TO HIDE THE BUTTON
            tet_addevent_amount.setVisibility(View.GONE);//TO HIDE THE BUTTON
            is_amount_req=false;
            is_reg_Req=false;
            addevent_amountrequired.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                    {
                        ET_amount.setVisibility(View.VISIBLE);//TO HIDE THE BUTTON
                        tet_addevent_amount.setVisibility(View.VISIBLE);//TO HIDE THE BUTTON
                        is_amount_req=true;

                    }
                    else
                    {
                        ET_amount.setVisibility(View.GONE);//TO HIDE THE BUTTON
                        tet_addevent_amount.setVisibility(View.GONE);//TO HIDE THE BUTTON
                        is_amount_req=false;

                    }
                }
            });  addevent_registrationrequired.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                    {
                        is_reg_Req=true;

                    }
                    else
                    {
                        is_reg_Req=false;

                    }
                }
            });
            ET_descrition.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (v.getId() == R.id.et_addevent_desc) {
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
            TV_eventstart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    one = 1;
                    showDate(2018, 0, 1, R.style.DatePickerSpinner);
                }
            });
            TV_evenetend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    one = 2;
                    showDate(2018, 0, 1, R.style.DatePickerSpinner);
                }
            });
            IV_imageupload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 1006);
                }
            });
            But_upolad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callmetoupload_addevnts();
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
                    Intent intent = new Intent(AddEvents_Vendor.this, AddEvents_Vendor.class);
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
        event_startdate_one = dayOfMonth+ "-" +(monthOfYear + 1)   + "-" + year;
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
            TV_eventstart.setText(event_startdate_one+" "+timeFormat.format(calendar.getTime()));

        }
        else
        {
            TV_evenetend.setText(event_startdate_one+" "+timeFormat.format(calendar.getTime()));

        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1006 && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();

            assert imageUri != null;
            UCrop.of( imageUri,  Uri.fromFile(new File(getCacheDir(), ".png")))
                    .withAspectRatio(3 , 2)
                    .start(AddEvents_Vendor.this);
            check_image_id=1006;
        }
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP && check_image_id==1006) {
            final Uri resultUri = UCrop.getOutput(data);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                IV_imageupload.setImageBitmap(bitmap);
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
                        s_image=jObj.getString("Response");


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



    public void callmetoupload_addevnts() {

        if (TV_eventstart.getText().toString().equals("")) {
            new PromptDialog(AddEvents_Vendor.this)
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
            if (TV_evenetend.equals("")) {
                new PromptDialog(AddEvents_Vendor.this)
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
                if (ET_contactname.getText().toString().equals("")) {
                    new PromptDialog(AddEvents_Vendor.this)
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
                    if (ET_title.getText().toString().equals("")) {
                        new PromptDialog(AddEvents_Vendor.this)
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



                        s_eventstart = TV_eventstart.getText().toString();
                        s_eventend = TV_evenetend.getText().toString();
                        s_contactname = ET_contactname.getText().toString();
                        s_title = ET_title.getText().toString();
                        s_amount= ET_amount.getText().toString();
                        s_descrition= ET_descrition.getText().toString();
                            s_prerequiremet= et_addevent_prerequirements.getText().toString();
dialog.show();
                            callmetouploadvendorcomplete_url(s_lnw_userid, s_eventstart, s_eventend
                                ,s_contactname, s_title, s_descrition, s_image,s_amount,s_prerequiremet,is_reg_Req,is_amount_req,v_edit_location,v_edit_locationo);
                        Intent intent = new Intent(AddEvents_Vendor.this, HomeScreen_vendor.class);
                        startActivity(intent);

                    }

                }
            }
        }
    }

    public void callmetouploadvendorcomplete_url(final String s_lnw_userid,final String start,
                                                  final String end,final String contact,
                                                  final String title,final String desc,
                                                  final String img,final String amount,
                                                 final String prerequirement,final Boolean isregreq,final Boolean isamountreq,final String lati,final String longi)
    {
        try {

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("UserId", s_lnw_userid);
            jsonBody.put("EventStart", start);
            jsonBody.put("EventEnd", end);
            jsonBody.put("Name", contact);
            jsonBody.put("Title", title);
            jsonBody.put("Description", desc);
            jsonBody.put("Image", s_image);
            jsonBody.put("RegistrationRequired", isregreq);
            jsonBody.put("IsPaid", isamountreq);
            jsonBody.put("Requirements", prerequirement);
            jsonBody.put("Amount", amount);
            jsonBody.put("Latitude", lati);
            jsonBody.put("Longitude", longi);




            final String requestBody = jsonBody.toString();
            Log.i("checkandro",requestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.Vendor_add_event, new Response.Listener<String>() {

                public void onResponse(String response) {
                    // startActivity(new Intent(ProfileInfo.this, LoginActivity.class));
                   dialog.hide();

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
