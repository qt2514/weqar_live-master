package com.weqar.weqar;

import android.annotation.SuppressLint;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;
import com.weqar.weqar.Global_url_weqar.Global_URL;
import com.yalantis.ucrop.UCrop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import cn.refactor.lib.colordialog.PromptDialog;


public class AddDiscount_Vendor extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    public Boolean s_check_discount;
    String  s_lnw_usertoken,S_vcomple_plantype_sel,S_vcomple_offertype_sel,serviceuid="",s_lnw_userid;
    ScrollView SV_adddisc_firstpage,SV_adddisc_secondpage;
    SearchableSpinner SP_vendor_com_planchoose,SP_vendor_com_offertype;
    private JSONArray result;
    ArrayList<String> vendor_plan = new ArrayList<String>();
    TextInputLayout TIL_vcomplete_percentage;
    EditText ET_vcomplete_percentage,ET_vcomplete_disctitle,ET_vcomplete_discdesc;
    String compl_vendor_offertype[] = {"Discount","Offer"};
    String S_vcomplete_percentage,S_vcomplete_title,s_vcomplete_description,s_vcomplete_image_response;
    ImageView IV_vcomplete_imageupload;
    Button B_vcomplete_complete;
    TextView TV_vcomplete_skip;
    TextView et_adddiscount_startdate,et_adddiscount_enddate;
    SearchableSpinner SP_vcomplete_offertype_s;
    EditText et_vcomplete_percentage_s,et_vcomplete_disctitle_s,et_vcomplete_discdesc_s;
    ImageView IV_vcomplefileupload_s;
    TextInputLayout TIV_vcomplete_percentage_s;
    Button B_vcomplete_completed_s;
    String s_disc_startdate,s_disc_enddate,s_disc_offertype,s_disc_percentage,s_disc_title,s_disc_desc,s_image,s_images;
    String check_discounttype_vendor_discount, check_discounttype_vendor_discount_o;
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    ImageView IV_set_account_back;
    int check_image_id,one,v_years,v_months,v_days;
    Calendar calendar;
    ProgressDialog dialog;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_discount__vendor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Discount");
        setSupportActionBar(toolbar);
        if (isConnectedToNetwork()) {

            SV_adddisc_firstpage = findViewById(R.id.scrollview_first_adddisc);
            SV_adddisc_secondpage = findViewById(R.id.scrollview_second_adddisc);
            SP_vendor_com_planchoose = findViewById(R.id.vendor_complete_plan);
            SP_vendor_com_offertype = findViewById(R.id.SP_vcomplete_offertype);
            TIL_vcomplete_percentage = findViewById(R.id.TIV_vcomplete_percentage);
            ET_vcomplete_percentage = findViewById(R.id.et_vcomplete_percentage);
            ET_vcomplete_disctitle = findViewById(R.id.et_vcomplete_disctitle);
            ET_vcomplete_discdesc = findViewById(R.id.et_vcomplete_discdesc);
            IV_vcomplete_imageupload = findViewById(R.id.IV_vcomplefileupload);
            B_vcomplete_complete = findViewById(R.id.B_vcomplete_complete);
            TV_vcomplete_skip = findViewById(R.id.TV_vcomplete_skipnow);
            et_adddiscount_startdate = findViewById(R.id.et_adddiscount_startdate);
            et_adddiscount_enddate = findViewById(R.id.et_adddiscount_enddate);
            SP_vcomplete_offertype_s = findViewById(R.id.SP_vcomplete_offertype_s);
            et_vcomplete_percentage_s = findViewById(R.id.et_vcomplete_percentage_s);
            et_vcomplete_disctitle_s = findViewById(R.id.et_vcomplete_disctitle_s);
            et_vcomplete_discdesc_s = findViewById(R.id.et_vcomplete_discdesc_s);
            IV_vcomplefileupload_s = findViewById(R.id.IV_vcomplefileupload_s);
            TIV_vcomplete_percentage_s = findViewById(R.id.TIV_vcomplete_percentage_s);
            B_vcomplete_completed_s = findViewById(R.id.B_vcomplete_completed_s);
            dialog = new ProgressDialog(this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setMessage("Loading. Please wait...");
            IV_set_account_back = findViewById(R.id.addjobs_back);
            IV_set_account_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(AddDiscount_Vendor.this,HomeScreen_vendor.class));
                    finish();
                }
            });

            TV_vcomplete_skip.setVisibility(View.INVISIBLE);
            Shared_user_details = getSharedPreferences("user_detail_mode", 0);
            TinyDB tinydb = new TinyDB(this);
            s_check_discount = tinydb.getBoolean("hgffh");

            s_lnw_userid = Shared_user_details.getString("sp_w_userid", null);
            s_lnw_usertoken = Shared_user_details.getString("sp_w_apikey", null);

            ET_vcomplete_discdesc.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (v.getId() == R.id.et_vcomplete_discdesc) {
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


            et_vcomplete_discdesc_s.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (v.getId() == R.id.et_vcomplete_discdesc_s) {
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
            getVendorplan();

            if (s_check_discount) {
                SV_adddisc_secondpage.setVisibility(View.VISIBLE);
                SV_adddisc_firstpage.setVisibility(View.INVISIBLE);

            } else {
                SV_adddisc_firstpage.setVisibility(View.VISIBLE);
                SV_adddisc_secondpage.setVisibility(View.INVISIBLE);
            }
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, compl_vendor_offertype);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
            SP_vendor_com_offertype.setAdapter(spinnerArrayAdapter);
            SP_vendor_com_planchoose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    S_vcomple_plantype_sel = parent.getItemAtPosition(position).toString();
                    getvendor_plannameid(position);

                }

                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            SP_vendor_com_offertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    S_vcomple_offertype_sel = parent.getItemAtPosition(position).toString();
                    if (S_vcomple_offertype_sel.equals("Discount") || S_vcomple_offertype_sel.matches("Discount")) {
                        check_discounttype_vendor_discount_o = "1";
                        ET_vcomplete_percentage.setVisibility(View.VISIBLE);
                        TIL_vcomplete_percentage.setVisibility(View.VISIBLE);
                    } else if (S_vcomple_offertype_sel.equals("Offer") || S_vcomple_offertype_sel.matches("Offer")) {
                        check_discounttype_vendor_discount_o = "2";
                        ET_vcomplete_percentage.setVisibility(View.INVISIBLE);
                        TIL_vcomplete_percentage.setVisibility(View.INVISIBLE);
                    }
                }

                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            ArrayAdapter<String> spinnerArrayAdapters = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, compl_vendor_offertype);
            spinnerArrayAdapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
            SP_vcomplete_offertype_s.setAdapter(spinnerArrayAdapters);
            SP_vcomplete_offertype_s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    s_disc_offertype = parent.getItemAtPosition(position).toString();
                    if (s_disc_offertype.equals("Discount") || s_disc_offertype.matches("Discount")) {
                        check_discounttype_vendor_discount = "1";
                        et_vcomplete_percentage_s.setVisibility(View.VISIBLE);
                        TIV_vcomplete_percentage_s.setVisibility(View.VISIBLE);
                    } else if (s_disc_offertype.equals("Offer") || s_disc_offertype.matches("Offer")) {
                        check_discounttype_vendor_discount = "2";
                        et_vcomplete_percentage_s.setVisibility(View.INVISIBLE);
                        TIV_vcomplete_percentage_s.setVisibility(View.INVISIBLE);
                    }
                }

                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            IV_vcomplete_imageupload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 1005);
                }
            });
            B_vcomplete_complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callmetouploadvendorcomplete();
                }
            });


            //second
            IV_vcomplefileupload_s.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 1006);
                }
            });

            et_adddiscount_startdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    one = 1;
                    showDate(2018, 0, 1, R.style.DatePickerSpinner);
                }
            });
            et_adddiscount_enddate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    one = 2;
                    showDate(2018, 0, 1, R.style.DatePickerSpinner);
                }
            });
            B_vcomplete_completed_s.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callmetoupload_seconddiscount();

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
                    Intent intent = new Intent(AddDiscount_Vendor.this, AddDiscount_Vendor.class);
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

        if(one==1)
        {
            et_adddiscount_startdate.setText(date);

        }
        else  if(one==2)
        {
            et_adddiscount_enddate.setText(date);

        }
    }

    private void getvendor_plannameid(int position){

        try {
            JSONObject json = result.getJSONObject(position);
            serviceuid = json.getString("Id");
            // Toast.makeText(PartnerSerSel.this,serviceuid , Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void getVendorplan() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Global_URL.Vendor_complete_chooseplan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j = null;
                try {
                    j = new JSONObject(response);
                    result = j.getJSONArray("Response");
                    getStudents(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

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
        };
        requestQueue.add(stringRequest);
    }

    private void getStudents(JSONArray j) {
        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                vendor_plan.add(json.getString("Name"));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        SP_vendor_com_planchoose.setAdapter(new ArrayAdapter<String>(AddDiscount_Vendor.this, android.R.layout.simple_dropdown_item_1line, vendor_plan));
    }
    public void callmetouploadvendorcomplete()
    {
        try {
            if(S_vcomple_plantype_sel.equals(""))
            {
                new PromptDialog(AddDiscount_Vendor.this)
                        .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                        .setAnimationEnable(true)
                        .setTitleText("Please Choose Plan")
                        .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                            @Override
                            public void onClick(PromptDialog dialog) {
                                dialog.dismiss();
                            }
                        }).show();
            }
            else {
                if (S_vcomple_offertype_sel.equals("")) {
                    new PromptDialog(AddDiscount_Vendor.this)
                            .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                            .setAnimationEnable(true)
                            .setTitleText("Please Choose Offer type")
                            .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    if (ET_vcomplete_disctitle.getText().toString().equals("")) {
                        new PromptDialog(AddDiscount_Vendor.this)
                                .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                .setAnimationEnable(true)
                                .setTitleText("Please Enter Title Name")
                                .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                    @Override
                                    public void onClick(PromptDialog dialog) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    } else {
                        if (ET_vcomplete_discdesc.getText().toString().equals("")) {
                            new PromptDialog(AddDiscount_Vendor.this)
                                    .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                    .setAnimationEnable(true)
                                    .setTitleText("Please Enter Description")
                                    .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                        @Override
                                        public void onClick(PromptDialog dialog) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                        } else {

                                S_vcomplete_percentage = ET_vcomplete_percentage.getText().toString();
                                S_vcomplete_title = ET_vcomplete_disctitle.getText().toString();
                                s_vcomplete_description = ET_vcomplete_discdesc.getText().toString();
                                dialog.show();





                                callmetouploadvendorcomplete_url(s_vcomplete_description,s_images,s_lnw_userid
                                        , S_vcomplete_title, S_vcomplete_percentage, serviceuid, S_vcomple_offertype_sel);
                                Intent intent=new Intent(AddDiscount_Vendor.this,HomeScreen_vendor.class);
                                TinyDB tinydb = new TinyDB(this);
                                tinydb.putBoolean("hgffh", true);
                                startActivity(intent);
                        }
                    }
                }
            }
        }catch (Exception e){}

    }

    public void callmetouploadvendorcomplete_url(String v_discdesc,String image,String id,String title,String percentage,
                                                 String plantype,String ofertype)
    {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Description", v_discdesc);
            jsonBody.put("Image", image);
            jsonBody.put("VendorId", id);
            jsonBody.put("Title", title);
            jsonBody.put("Percentage", percentage);
            jsonBody.put("DiscountPlan", plantype);
            jsonBody.put("DiscountType", ofertype);
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.vendor_insert_completedetails, new Response.Listener<String>() {
                public void onResponse(String response) {

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1005 && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();

            assert imageUri != null;
            UCrop.of( imageUri,  Uri.fromFile(new File(getCacheDir(), ".png")))
                    .withAspectRatio(3 , 2)
                    .start(AddDiscount_Vendor.this);
            check_image_id=1005;
        }
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP && check_image_id==1005) {
            final Uri resultUri = UCrop.getOutput(data);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                IV_vcomplete_imageupload.setImageBitmap(bitmap);
              upload_vendor_complete_image(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
       else if (requestCode == 1006 && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();

            assert imageUri != null;
            UCrop.of( imageUri,  Uri.fromFile(new File(getCacheDir(), ".png")))
                    .withAspectRatio(3 , 2)
                    .start(AddDiscount_Vendor.this);
            check_image_id=1006;
        }
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP && check_image_id==1006) {
            final Uri resultUri = UCrop.getOutput(data);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                



                IV_vcomplefileupload_s.setImageBitmap(bitmap);
              upload_vendor_complete_image_s(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }

    }
    private void upload_vendor_complete_image(final Bitmap bitmap)
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
                    dialog.hide();
                    try
                    {
                        JSONObject jObj = new JSONObject(response);
                        s_images=jObj.getString("Response");
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
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
    }  private void upload_vendor_complete_image_s(final Bitmap bitmap)
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

    public void callmetoupload_seconddiscount()
    {
        if(et_adddiscount_startdate.getText().toString().equals(""))
        {
            new PromptDialog(AddDiscount_Vendor.this)
                    .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                    .setAnimationEnable(true)
                    .setTitleText("Please Select Start Date")
                    .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                        @Override
                        public void onClick(PromptDialog dialog) {
                            dialog.dismiss();
                        }
                    }).show();
        }
        else {
            if (et_adddiscount_enddate.getText().toString().equals(""))
            {
                new PromptDialog(AddDiscount_Vendor.this)
                        .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                        .setAnimationEnable(true)
                        .setTitleText("Please Select End Date")
                        .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                            @Override
                            public void onClick(PromptDialog dialog) {
                                dialog.dismiss();
                            }
                        }).show();
            }
            else {
                try
                {


                if (s_disc_offertype.equals(""))
                {
                    new PromptDialog(AddDiscount_Vendor.this)
                            .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                            .setAnimationEnable(true)
                            .setTitleText("Please Select Offer Type")
                            .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
                else
                {
                    if(et_vcomplete_disctitle_s.getText().toString().equals(""))
                    {
                        new PromptDialog(AddDiscount_Vendor.this)
                                .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                .setAnimationEnable(true)
                                .setTitleText("Please Enter Title")
                                .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                    @Override
                                    public void onClick(PromptDialog dialog) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    }
                    else
                    {
                        if(et_vcomplete_discdesc_s.getText().toString().equals(""))
                        {
                            new PromptDialog(AddDiscount_Vendor.this)
                                    .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                    .setAnimationEnable(true)
                                    .setTitleText("Please Enter Description")
                                    .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                        @Override
                                        public void onClick(PromptDialog dialog) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                        }
                        else
                        {
                            if(s_image.equals(""))
                            {
                                new PromptDialog(AddDiscount_Vendor.this)
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

                                    s_disc_startdate=et_adddiscount_startdate.getText().toString();
                                    s_disc_enddate=et_adddiscount_enddate.getText().toString();
                                    s_disc_percentage=et_vcomplete_percentage_s.getText().toString();
                                    s_disc_title=et_vcomplete_disctitle_s.getText().toString();
                                    s_disc_desc=et_vcomplete_discdesc_s.getText().toString();
                                    dialog.show();


                                    callmetoupload_seconddiscount_url(s_disc_desc,s_lnw_userid,s_image,s_disc_title,
                                            s_disc_startdate,s_disc_enddate,s_disc_percentage,check_discounttype_vendor_discount);


                                    startActivity(new Intent(AddDiscount_Vendor.this,HomeScreen_vendor.class));


                            }


                        }
                    }
                }
            }catch (Exception e){}
            }
        }

    }
    public void callmetoupload_seconddiscount_url(final String s_disc_desc,final String s_lnw_userid,
                                                  final String s_image,final String s_disc_title,
                                                  final String s_disc_startdate,final String s_disc_enddate,
                                                  final String s_disc_percentage,final String s_disc_offertype)
    {
        try {


            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Description", s_disc_desc);
            jsonBody.put("VendorId", s_lnw_userid);
            jsonBody.put("Image", s_image);
            jsonBody.put("Title", s_disc_title);
            jsonBody.put("StartDate", s_disc_startdate);
            jsonBody.put("EndDate", s_disc_enddate);
            jsonBody.put("Percentage", s_disc_percentage);
            jsonBody.put("DiscountType", s_disc_offertype);

            final String requestBody = jsonBody.toString();
            Log.i("checkandro",requestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.Vendor_insert_second_Discount, new Response.Listener<String>() {

                public void onResponse(String response) {
                    dialog.hide();
                    // startActivity(new Intent(ProfileInfo.this, LoginActivity.class));
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
