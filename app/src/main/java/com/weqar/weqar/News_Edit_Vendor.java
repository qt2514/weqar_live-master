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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
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


public class News_Edit_Vendor extends AppCompatActivity {
    ImageView iv_editnews_back,iv_editnews_image;
    EditText et_editnews_title,et_editnews_url,et_editnews_desc;
    SearchableSpinner sp_editnews_newstype;
    Button but_editnews_add;
    String news_edit_userid,news_edit_newsid,news_edit_newsurl,news_edit_title,news_edit_typeid,news_edit_typename,news_edit_image,
    news_edit_content,s_lnw_usertoken;
    private JSONArray result;
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    ArrayList<String> vendor_plan = new ArrayList<String>();
int check_image_id;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news__edit__vendor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (isConnectedToNetwork()) {

            iv_editnews_back = findViewById(R.id.iv_editnews_back);
            iv_editnews_image = findViewById(R.id.iv_editnews_image);
            et_editnews_title = findViewById(R.id.et_editnews_title);
            et_editnews_url = findViewById(R.id.et_editnews_url);
            sp_editnews_newstype = findViewById(R.id.sp_editnews_newstype);
            et_editnews_desc = findViewById(R.id.et_editnews_desc);
            but_editnews_add = findViewById(R.id.but_editnews_add);
            dialog = new ProgressDialog(this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setMessage("Loading. Please wait...");
            getnewstype();
            Shared_user_details = getSharedPreferences("user_detail_mode", 0);
            s_lnw_usertoken = Shared_user_details.getString("sp_w_apikey", null);
            try {
                Intent intent = getIntent();
                news_edit_userid = intent.getStringExtra("put_userid_fornews_edit");
                news_edit_newsid = intent.getStringExtra("put_newsid_fornews_edit");
                news_edit_newsurl = intent.getStringExtra("put_url_fornews_edit");
                news_edit_title = intent.getStringExtra("put_title_fornews_edit");
                news_edit_typeid = intent.getStringExtra("put_type_fornews_edit");
                news_edit_image = intent.getStringExtra("put_image_fornews_edit");
                news_edit_content = intent.getStringExtra("put_content_fornews_edit");

                et_editnews_title.setText(news_edit_title);
                et_editnews_url.setText(news_edit_newsurl);
                et_editnews_desc.setText(news_edit_content);
                Picasso.with(this).load(Global_URL.Image_url_load + news_edit_image).error(getResources().getDrawable(R.drawable.rounded_two)).fit().centerCrop().into(iv_editnews_image);
                if (news_edit_typeid.equals("1")) {
                    news_edit_typename = "News";
                } else {
                    news_edit_typename = "Article";

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            iv_editnews_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(News_Edit_Vendor.this, News_Display_Vendor.class));
                }
            });
            but_editnews_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callme_toupdate_news_vendor();
                }
            });
            et_editnews_desc.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (v.getId() == R.id.et_editnews_desc) {
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
            sp_editnews_newstype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    news_edit_typename = parent.getItemAtPosition(position).toString();
                    getvendor_plannameid(position);

                }

                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            iv_editnews_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 1006);
                }
            });
        } else
        {
            setContentView(R.layout.content_if_nointernet);
            ImageView but_retry = findViewById(R.id.nointernet_retry);
            but_retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(News_Edit_Vendor.this, News_Display_Vendor.class);
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
        sp_editnews_newstype.setAdapter(new ArrayAdapter<String>(News_Edit_Vendor.this, android.R.layout.simple_dropdown_item_1line, vendor_plan));
    }
    private void getvendor_plannameid(int position){

        try {
            JSONObject json = result.getJSONObject(position);
            news_edit_typeid = json.getString("Id");
          //  Toast.makeText(News_Edit_Vendor.this,s_getmynews_type_id , Toast.LENGTH_SHORT).show();
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
                    .start(News_Edit_Vendor.this);
            check_image_id=1006;
        }
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP && check_image_id==1006) {
            final Uri resultUri = UCrop.getOutput(data);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                iv_editnews_image.setImageBitmap(bitmap);
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
                        news_edit_image=jObj.getString("Response");


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

    public void callme_toupdate_news_vendor()
{
    if (et_editnews_title.getText().toString().equals(""))
    {
        new PromptDialog(News_Edit_Vendor.this)
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
    else {
        if(et_editnews_url.getText().toString().equals(""))
        {
            new PromptDialog(News_Edit_Vendor.this)
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
            if(et_editnews_desc.getText().toString().equals(""))
            {
                new PromptDialog(News_Edit_Vendor.this)
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
                news_edit_title=et_editnews_title.getText().toString();
                news_edit_newsurl=et_editnews_url.getText().toString();
                news_edit_content=et_editnews_desc.getText().toString();
                dialog.show();
                callme_toupdate_news_vendor_url(news_edit_userid,news_edit_newsurl,news_edit_title
                        ,news_edit_typeid,news_edit_image,news_edit_content,news_edit_newsid);

            }
        }
    }
}
public void callme_toupdate_news_vendor_url(String user_id,String url,String title,
                                            String typeid,String image,String content,String newsid)
{
    try {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("UserId", user_id);
        jsonBody.put("Url", url);
        jsonBody.put("Title", title);
        jsonBody.put("NewsTypeId", typeid);
        jsonBody.put("Image", image);
        jsonBody.put("Content", content);
        jsonBody.put("Id", newsid);
        jsonBody.put("ModifiedBy", user_id);

        final String requestBody = jsonBody.toString();
        Log.i("checkandro",requestBody);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.user_edit_news, new Response.Listener<String>() {
            public void onResponse(String response) {
                dialog.hide();
                new PromptDialog(News_Edit_Vendor.this)
                        .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                        .setAnimationEnable(true)
                        .setTitleText("News Edited Successfully")
                        .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                            @Override
                            public void onClick(PromptDialog dialog) {
                                startActivity(new Intent(News_Edit_Vendor.this,News_Display_Vendor
                                        .class));
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
