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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
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
import java.util.HashMap;
import java.util.Map;

import cn.refactor.lib.colordialog.PromptDialog;

public class AddNews_Vendor extends AppCompatActivity {
    EditText et_addnews_title, et_addnews_url, et_addnews_desc;
    SearchableSpinner sp_addnews_newstype;
    ImageView iv_addnews_image,iv_addnews_back;
    Button but_addnews_add;
    private JSONArray result;
    ArrayList<String> vendor_plan = new ArrayList<String>();
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    String s_lnw_userid,s_lnw_usertoken,s_getmynews_type_id,s_getmynews_type_name,s_addnews_title,s_addnews_url,s_addnews_desc,s_addnews_image;
    int check_image_id;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news__user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (isConnectedToNetwork()) {
            et_addnews_title = findViewById(R.id.et_addnews_title);
            et_addnews_url = findViewById(R.id.et_addnews_url);
            et_addnews_desc = findViewById(R.id.et_addnews_desc);
            sp_addnews_newstype = findViewById(R.id.sp_addnews_newstype);
            iv_addnews_image = findViewById(R.id.iv_addnews_image);
            but_addnews_add = findViewById(R.id.but_addnews_add);
            iv_addnews_back = findViewById(R.id.iv_addnews_back);
            dialog = new ProgressDialog(this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setMessage("Loading. Please wait...");
            Shared_user_details = getSharedPreferences("user_detail_mode", 0);
            s_lnw_userid = Shared_user_details.getString("sp_w_userid", null);
            s_lnw_usertoken = Shared_user_details.getString("sp_w_apikey", null);
            getnewstype();
            iv_addnews_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(AddNews_Vendor.this, News_Display_Vendor.class));
                }
            });
            sp_addnews_newstype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    s_getmynews_type_name = parent.getItemAtPosition(position).toString();
                    getvendor_plannameid(position);

                }

                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            but_addnews_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callmetoupload_news();
                }
            });
            iv_addnews_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 1006);
                }
            });
            et_addnews_desc.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (v.getId() == R.id.et_addnews_desc) {
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
        }
        else
        {
            setContentView(R.layout.content_if_nointernet);
            ImageView but_retry = findViewById(R.id.nointernet_retry);
            but_retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddNews_Vendor.this, AddNews_Vendor.class);
                    startActivity(intent);
                }
            });
        }

    }

    private void getnewstype() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Global_URL.user_getnews_type, new Response.Listener<String>() {
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
                    public void onErrorResponse(VolleyError error) { }
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
                headers.put("x-api-key", s_lnw_usertoken);
                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void getStudents(JSONArray j) {
        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                vendor_plan.add(json.getString("Description"));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        sp_addnews_newstype.setAdapter(new ArrayAdapter<String>(AddNews_Vendor.this, android.R.layout.simple_dropdown_item_1line, vendor_plan));
    }

    private void getvendor_plannameid(int position){

        try {
            JSONObject json = result.getJSONObject(position);
            s_getmynews_type_id = json.getString("Id");
            // Toast.makeText(AddNews_User.this,s_getmynews_type_id , Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1006 && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();

            assert imageUri != null;
            UCrop.of( imageUri,  Uri.fromFile(new File(getCacheDir(), ".png")))
                    .withAspectRatio(3 , 2)
                    .start(AddNews_Vendor.this);
            check_image_id=1006;
        }
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP && check_image_id==1006) {
            final Uri resultUri = UCrop.getOutput(data);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                iv_addnews_image.setImageBitmap(bitmap);
                upload_addnews_image(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }
    private void upload_addnews_image(final Bitmap bitmap)
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
                        s_addnews_image=jObj.getString("Response");


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
    public void callmetoupload_news()
    {
        try
        {


            if(et_addnews_title.getText().toString().equals(""))
            {
                new PromptDialog(AddNews_Vendor.this)
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
                if(s_getmynews_type_id.equals(""))
                {
                    new PromptDialog(AddNews_Vendor.this)
                            .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                            .setAnimationEnable(true)
                            .setTitleText("Please Select Type")
                            .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
                else
                {
                    if(et_addnews_url.getText().toString().equals(""))
                    {
                        new PromptDialog(AddNews_Vendor.this)
                                .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                .setAnimationEnable(true)
                                .setTitleText("Please Enter Url")
                                .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                    @Override
                                    public void onClick(PromptDialog dialog) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    }
                    else
                    {
                        if(et_addnews_desc.getText().toString().equals(""))
                        {
                            new PromptDialog(AddNews_Vendor.this)
                                    .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                    .setAnimationEnable(true)
                                    .setTitleText("Please Enter Content")
                                    .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                        @Override
                                        public void onClick(PromptDialog dialog) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                        }
                        else
                        {


                            s_addnews_title=et_addnews_title.getText().toString();
                            s_addnews_url=et_addnews_url.getText().toString();
                            s_addnews_desc=et_addnews_desc.getText().toString();
                            dialog.show();
                            callmetoupload_news_url(s_lnw_userid,s_addnews_url,s_addnews_title,s_getmynews_type_id,s_addnews_image,s_addnews_desc);


                        }
                    }
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void callmetoupload_news_url(final String news_userid,final String news_url,
                                        final String news_title,final String news_type,
                                        final String news_image,final String new_content)
    {
        try {

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("UserId", news_userid);
            jsonBody.put("Url", news_url);
            jsonBody.put("Title", news_title);
            jsonBody.put("NewsTypeId", news_type);
            jsonBody.put("Image", news_image);
            jsonBody.put("Content", new_content);
            jsonBody.put("CreatedBy", news_userid);

            final String requestBody = jsonBody.toString();
            Log.i("checkandro",requestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.user_addnews, new Response.Listener<String>() {
                public void onResponse(String response) {
                    dialog.hide();
                    new PromptDialog(AddNews_Vendor.this)
                            .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                            .setAnimationEnable(true)
                            .setTitleText("News Added Successfully")
                            .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                    startActivity(new Intent(AddNews_Vendor.this,News_Display_Vendor.class));
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
