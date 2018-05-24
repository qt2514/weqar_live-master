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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
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
import com.squareup.picasso.Picasso;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import cn.refactor.lib.colordialog.PromptDialog;
import de.hdodenhof.circleimageview.CircleImageView;


public class Settings_Profesional_Edit_Vendor extends AppCompatActivity {
    EditText ET_pedit_companyname,ET_pedit_businesscontact,ET_pedit_bemail;
    TextView ET_pedit_location;
    TextView ET_pedit_category;
    Button BT_pedit_update;
    String v_edit_id,v_edit_companyname,v_edit_categoryid,v_edit_categoryname,v_edit_bcontact,v_edit_email,v_edit_websitelink,
    v_edit_location,v_edit_locationo,svendor_busimail,s_vendor_logo;
    private static final int SECOND_ACTIVITY_REQUEST_CODEs = 0;
    List<String> subjectnamelist;
   // List<String> subjectnameid;
    String Ssubjectkind,s_user_token;
    ImageView account_setting_professional_back,IV_S_user_profile_edit;
    CircleImageView IV_S_user_profile_image;
    int check_image_id;
    ArrayList<String> spinnerArray_id ;
    ArrayList<String> spinnerArray_name ;
    String my_lati,my_lngi;
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings__profesional__edit__vendor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (isConnectedToNetwork()) {
            Shared_user_details=getSharedPreferences("user_detail_mode",0);
            editor = Shared_user_details.edit();
            ET_pedit_companyname = findViewById(R.id.ET_pedit_companyname);
            ET_pedit_businesscontact = findViewById(R.id.ET_pedit_businesscontact);
            ET_pedit_bemail = findViewById(R.id.ET_pedit_bemail);
            //ET_pedit_websitelink=findViewById(R.id.ET_pedit_websitelink);
          ET_pedit_location = findViewById(R.id.ET_pedit_location);
            spinnerArray_id=new ArrayList<String>();
            spinnerArray_name=new ArrayList<String>();
            dialog = new ProgressDialog(this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setMessage("Loading. Please wait...");
            ET_pedit_category = findViewById(R.id.ET_pedit_category);
            BT_pedit_update = findViewById(R.id.BT_pedit_update);
            IV_S_user_profile_edit = findViewById(R.id.IV_S_user_profile_edit);
            IV_S_user_profile_image = findViewById(R.id.IV_S_user_profile_image);

try
{
    s_user_token = Shared_user_details.getString("sp_w_apikey", null);

}catch (Exception e)
{
    e.printStackTrace();
}

            IV_S_user_profile_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 1002);
                }
            });
            ET_pedit_location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  Intent inten=new Intent(Settings_Profesional_Edit_Vendor.this,SelectLocations.class);
                    startActivityForResult(inten, SECOND_ACTIVITY_REQUEST_CODEs);
                }
            });
            account_setting_professional_back = findViewById(R.id.account_setting_professional_back);
            account_setting_professional_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Settings_Profesional_Edit_Vendor.this,Settings_ProfileActivity_Vendor.class));
                }
            });

            try {
                Intent intent = getIntent();

                v_edit_id = intent.getStringExtra("put_s_userid");
                v_edit_companyname = intent.getStringExtra("put_s_companyname");
                spinnerArray_id = intent.getStringArrayListExtra("put_s_categoryid");
                spinnerArray_name = intent.getStringArrayListExtra("put_s_categoryname");

                v_edit_bcontact = intent.getStringExtra("put_s_bcontact");
                v_edit_email = intent.getStringExtra("put_s_bemail");
                v_edit_websitelink = intent.getStringExtra("put_s_websitelink");
                v_edit_location = intent.getStringExtra("put_s_location");
                v_edit_locationo = intent.getStringExtra("put_s_locationo");
                s_vendor_logo = intent.getStringExtra("put_s_logo");
                ET_pedit_companyname.setText(v_edit_companyname);
                int arraySize = spinnerArray_name.size();

//                my_lati=  Shared_user_details.getString("send_mylatitude", null);
              //  my_lngi=  Shared_user_details.getString("send_longitude", null);
                ET_pedit_location.setText(v_edit_location+","+v_edit_locationo);
                StringBuilder builders = new StringBuilder();

                for (int i = 0; i < spinnerArray_name.size(); i++) {

                    builders.append("").append(spinnerArray_name

                            .get(i)).append(",");


                }

                ET_pedit_category.setText(builders);
               // ET_pedit_category.setText(v_edit_categoryname);
                ET_pedit_businesscontact.setText(v_edit_bcontact);
                ET_pedit_bemail.setText(v_edit_email);
                Picasso.with(Settings_Profesional_Edit_Vendor.this).load(Global_URL.Image_url_load+s_vendor_logo).error(getResources().getDrawable(R.drawable.rounded)).fit().centerCrop().into(IV_S_user_profile_image);

                //  ET_pedit_location.setText(v_edit_location);
            } catch (Exception e) {
                e.printStackTrace();
            }

            BT_pedit_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callmetoupload_profdetails();
                }
            });
            ET_pedit_category.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Settings_Profesional_Edit_Vendor.this, MultiSpinner_Vendor_Category.class);

                    startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
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
                    Intent intent = new Intent(Settings_Profesional_Edit_Vendor.this, Settings_ProfileActivity_Vendor.class);
                    startActivity(intent);
                }
            });
        }
    }
public void callmetoupload_profdetails()
{   if(ET_pedit_category.getText().toString().equals(""))
{
    new PromptDialog(Settings_Profesional_Edit_Vendor.this)
            .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
            .setAnimationEnable(true)
            .setTitleText("Please Select Category")
            .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                @Override
                public void onClick(PromptDialog dialog) {
                    dialog.dismiss();
                }
            }).show();
}
else
{
    if(ET_pedit_businesscontact.getText().toString().equals(""))
    {
        new PromptDialog(Settings_Profesional_Edit_Vendor.this)
                .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                .setAnimationEnable(true)
                .setTitleText("Please Enter Business Contact")
                .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();

    }
    else
    {
        if(ET_pedit_bemail.getText().toString().equals(""))
        {
            new PromptDialog(Settings_Profesional_Edit_Vendor.this)
                    .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                    .setAnimationEnable(true)
                    .setTitleText("Please Enter Business Email")
                    .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                        @Override
                        public void onClick(PromptDialog dialog) {
                            dialog.dismiss();
                        }
                    }).show();

        }
        else
        {
            String  SemailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

            svendor_busimail = ET_pedit_bemail.getText().toString().trim();
            v_edit_companyname= ET_pedit_companyname.getText().toString().trim();
            if (svendor_busimail.matches(SemailPattern)) {



                v_edit_categoryname = ET_pedit_category.getText().toString();

                v_edit_bcontact = ET_pedit_businesscontact.getText().toString();
                v_edit_email = ET_pedit_bemail.getText().toString();
dialog.show();
                callmetouploadprofessionalurl_vendor(v_edit_bcontact, v_edit_email, v_edit_id
                        , v_edit_categoryid,v_edit_companyname,v_edit_location,v_edit_locationo,s_vendor_logo);

            }
            else
            {
                new PromptDialog(Settings_Profesional_Edit_Vendor.this)
                        .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                        .setAnimationEnable(true)
                        .setTitleText("Please Check Email format")
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

}
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                TinyDB tinydb = new TinyDB(this);
                Ssubjectkind=tinydb.getString("subjecttype");
                if (Ssubjectkind.equals("Subject")) {
                    subjectnamelist = tinydb.getListString("subjectnamelist");
                    spinnerArray_id = tinydb.getListString("subjectnameid");
                    StringBuilder builder = new StringBuilder();
                    // JSONArray startendarray=new JSONArray();
                    for (int i = 0; i < subjectnamelist.size(); i++) {

                        builder.append("").append(subjectnamelist.get(i)).append(",");


                    }

                    ET_pedit_category.setText(builder);
                }


            }
        }
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODEs) {
            if (resultCode == RESULT_OK) {
                  v_edit_location=  Shared_user_details.getString("send_mylatitude", null);
                v_edit_locationo=  Shared_user_details.getString("send_longitude", null);

                ET_pedit_location.setText(v_edit_location+","+v_edit_locationo);
            }
            }
        if (requestCode == 1002 && resultCode == RESULT_OK && data != null) {


            Uri imageUri = data.getData();

            assert imageUri != null;
            UCrop.of(imageUri, Uri.fromFile(new File(getCacheDir(), ".png")))
                    .withAspectRatio(3, 2)
                    .start(Settings_Profesional_Edit_Vendor.this);
            check_image_id = 1002;
        }
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP && check_image_id == 1002) {
            final Uri resultUri = UCrop.getOutput(data);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                IV_S_user_profile_image.setImageBitmap(bitmap);
                basic_image(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }
    private void basic_image(final Bitmap bitmap) {


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);
        try {

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONArray jsonArray= new JSONArray();
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("extension", "JPG");
            jsonBody.put("content", imageEncoded);


            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.User_uploadprofessionalimage, new Response.Listener<String>() {

                public void onResponse(String response) {


                    try {
                        JSONObject jObj = new JSONObject(response);
                        s_vendor_logo=jObj.getString("Response");
                        Log.i("basic_image_response",response);
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

    public void callmetouploadprofessionalurl_vendor(String buscontact,String busemail,String vpro_userid,String v_procateg,String companyname,
                                                     String lati,String longi,String image_ven)
    {
        try {


            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("BusinessContact", buscontact);
            jsonBody.put("BusinessEmail", busemail);
            jsonBody.put("UserId", vpro_userid);
            jsonBody.put("Logo", image_ven);
            jsonBody.put("CompanyName", companyname);
            jsonBody.put("Latitude", lati);
            jsonBody.put("Longitude", longi);
            JSONArray array2=new JSONArray(spinnerArray_id);
            jsonBody.put("Categories",array2);




            final String requestBody = jsonBody.toString();
            Log.i("requsitingcompany",requestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.Vendor_insertprofessionalinfo, new Response.Listener<String>() {

                public void onResponse(String response) {
                    // startActivity(new Intent(ProfileInfo.this, LoginActivity.class));
                    dialog.hide();
                    new PromptDialog(Settings_Profesional_Edit_Vendor.this)
                            .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                            .setAnimationEnable(true)
                            .setTitleText("Edited Successfully")
                            .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                    startActivity(new Intent(Settings_Profesional_Edit_Vendor.this,Settings_ProfileActivity_Vendor.class));
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
        }catch (JSONException e){

        }

    }
    private boolean isConnectedToNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}
