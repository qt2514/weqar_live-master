package com.weqar.weqar;

import android.app.Activity;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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

import com.android.datetimepicker.time.TimePickerDialog;
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
import com.weqar.weqar.DBHandlers.SessionManager;
import com.weqar.weqar.Global_url_weqar.Global_URL;
import com.weqar.weqar.JavaClasses.ImageUtil;
import com.weqar.weqar.JavaClasses.RecyclerViewAdapter;
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
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


import cn.refactor.lib.colordialog.PromptDialog;
import de.hdodenhof.circleimageview.CircleImageView;

import static java.util.jar.Pack200.Packer.ERROR;


public class ProfileInfo extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    EditText ET_fname,ET_mname,ET_lname,ET_emailid,ET_mobile,ET_address,ET_country,
    ET_Prof_cidno,ET_Prof_memno,ET_vprof_businescontect,ET_vprof_businesemail,ET_vprof_websitel,ET_vcomplete_percentage,
    ET_vcomplete_disctitle,ET_vcomplete_discdesc,ET_vrpof_company;
    TextView ET_vprof_category,ET_Prof_valid,TV_vcomplete_skip,ET_pedit_location;
    SearchableSpinner SP_mobilepin,SP_gender_sel,SP_vendor_com_planchoose,SP_vendor_com_offertype;
    ScrollView scrollView_personal,scrollView_professional,scrollview_vendor_professional,scrollView_complete,scrollView_vendor_complete;
    Button B_saveandcontinue_personal,B_professional_next,B_vendorprofessional_next;
    CircleImageView IV_bas;
    ImageView IV_basic_image,IV_personal,IV_professional,IV_complete,IV_prof_uploadfile,IV_vendor_professional_companylogo,
    IV_vcomplete_imageupload;
    View view1,view2,view3,view4;
    Button but_complete,B_vcomplete_complete;
    Toolbar toolbar;
    String s_fname,s_mname,s_lname,s_mobilepin,s_mobile,s_address,s_emailid,s_country,
    s_prof_cidno,s_prof_memno,s_prof_valid,s_vprof_category,s_vprof_buscontact,s_vprof_busemail,s_vprof_websitelink,
    svendor_busimail,svendor_comapny;
    String s_lnw_userid,s_lnw_useremail,s_lnw_usertype,s_res_userprofimg,s_lnw_usertoken,s_lnw_getcompany,s_basic_image,
    s_fromadp_getuser_plantype_id,s_fromadp_getuser_plantype;
    Boolean s_ln_tab1,s_ln_tab2,s_ln_tab3;
    com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd;
    List<String> L_user_planid ;
    List<String> L_user_plantype ;
    List<String> L_user_planamount ;
    List<String> L_user_desc ;
    RecyclerView Rec_usersubs;
    TextInputLayout TIL_vcomplete_percentage;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    RecyclerViewAdapter RecyclerViewHorizontalAdapter;
    LinearLayoutManager HorizontalLayout ;
    String s_uplan_planid,s_uplan_plantype,s_uplan_amount,s_uplan_desc,S_vcomple_plantype_sel,S_vcomple_offertype_sel,S_vcomplete_percentage,S_vcomplete_title,
    s_vcomplete_description,s_vcomplete_image_response;
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    Context context;
    ArrayList<String> subjectnamelist;
    ArrayList<String> subjectnameid;
    String Ssubjectkind;
    private JSONArray result;
    ArrayList<String> vendor_plan = new ArrayList<String>();
    String compl_vendor_offertype[] = {"Discount","Offer"},count_on_skip_forvendor="0",gender[]={"Male","Female"};
    String serviceuid="",s_lnw_usermailid;
    private SessionManager session;
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    int check_image_id;
    private Calendar calendar;
    int v_years,v_months,v_days;
    private static final int SECOND_ACTIVITY_REQUEST_CODEs = 0;
    String v_edit_location,v_edit_locationo;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Basic Info");
        ET_fname=findViewById(R.id.et_fname);
        ET_mname=findViewById(R.id.et_mname);
        ET_lname=findViewById(R.id.et_lname);
        ET_emailid=findViewById(R.id.et_emailid);
        ET_mobile=findViewById(R.id.et_mobile);
        ET_address=findViewById(R.id.et_address);
        SP_mobilepin=findViewById(R.id.basic_spiner_countrycode);
        SP_gender_sel=findViewById(R.id.baisc_gender_sel);
        ET_country=findViewById(R.id.et_selectcountry);
        ET_Prof_cidno=findViewById(R.id.et_prof_cidno);
        ET_Prof_memno=findViewById(R.id.et_prof_membernum);
        ET_Prof_valid=findViewById(R.id.et_prof_valid);
        ET_vprof_category=findViewById(R.id.vendor_professional_category);
        ET_vprof_businescontect=findViewById(R.id.vendor_professional_buscontact);
        ET_vprof_businesemail=findViewById(R.id.vendor_professional_busemail);
        ET_vprof_websitel=findViewById(R.id.vendor_professional_websitelink);
        ET_vcomplete_percentage=findViewById(R.id.et_vcomplete_percentage);
        ET_vcomplete_disctitle=findViewById(R.id.et_vcomplete_disctitle);
        ET_vcomplete_discdesc=findViewById(R.id.et_vcomplete_discdesc);
        ET_vrpof_company=findViewById(R.id.vendor_professional_companyname);
        scrollView_personal=findViewById(R.id.scrollview_personal);
        scrollView_professional=findViewById(R.id.scrollview_professional);
        scrollview_vendor_professional=findViewById(R.id.scrollview_vendorprofessional);
        scrollView_complete=findViewById(R.id.scrollview_complete);
        scrollView_vendor_complete=findViewById(R.id.scrollview_vendor_complete);
        B_saveandcontinue_personal=findViewById(R.id.saveandcontinue_personal);
        B_professional_next=findViewById(R.id.professional_but_next);
        B_vendorprofessional_next=findViewById(R.id.vendorprofessional_but_next);
        B_vcomplete_complete=findViewById(R.id.B_vcomplete_complete);
        IV_basic_image=findViewById(R.id.profile_edit);
        IV_bas=findViewById(R.id.basic_profile_img);
        IV_personal=findViewById(R.id.IV_personaL);
        IV_professional=findViewById(R.id.IV_professional);
        IV_complete=findViewById(R.id.IV_complete);
        IV_prof_uploadfile=findViewById(R.id.IV_prof_uploadfile);
        IV_vendor_professional_companylogo=findViewById(R.id.vendor_professional_companylogo);
        IV_vcomplete_imageupload=findViewById(R.id.IV_vcomplefileupload);
        TV_vcomplete_skip=findViewById(R.id.TV_vcomplete_skipnow);
        SP_vendor_com_planchoose=findViewById(R.id.vendor_complete_plan);
        SP_vendor_com_offertype=findViewById(R.id.SP_vcomplete_offertype);
        TIL_vcomplete_percentage=findViewById(R.id.TIV_vcomplete_percentage);
        ET_pedit_location=findViewById(R.id.ET_pedit_location);

        view1=findViewById(R.id.profile_view1);
        view2=findViewById(R.id.profile_view2);
        view3=findViewById(R.id.profile_view3);
        view4=findViewById(R.id.profile_view4);
        but_complete=findViewById(R.id.complete_but);
        Rec_usersubs = findViewById(R.id.recyclerview1);
        L_user_planid= new ArrayList<String>();
        L_user_plantype= new ArrayList<String>();
        L_user_planamount= new ArrayList<String>();
        L_user_desc= new ArrayList<String>();
        calendar = Calendar.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait...");


        ET_country.setText("Kuwait");
        ET_country.setFocusable(false);
        ET_country.setFocusableInTouchMode(false);
        ET_country.setClickable(false);
        SP_mobilepin.setPrompt("+965");
        context = ProfileInfo.this;
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        Rec_usersubs.setLayoutManager(RecyclerViewLayoutManager);
        RecyclerViewHorizontalAdapter = new RecyclerViewAdapter(L_user_planid,L_user_plantype,L_user_planamount,L_user_desc,getApplicationContext());
        HorizontalLayout = new LinearLayoutManager(ProfileInfo.this, LinearLayoutManager.HORIZONTAL, false);
        Rec_usersubs.setLayoutManager(HorizontalLayout);
        Rec_usersubs.setHorizontalScrollBarEnabled(false);
        vendor_plan = new ArrayList<String>();
        session = new SessionManager(getApplicationContext());
        Shared_user_details=getSharedPreferences("user_detail_mode",0);
        editor = Shared_user_details.edit();
        s_lnw_usermailid=  Shared_user_details.getString("sp_w_useremail", null);
        s_lnw_usertype=  Shared_user_details.getString("sp_w_usertype", null);
        s_lnw_userid= Shared_user_details.getString("sp_w_userid", null);
        s_lnw_usertoken= Shared_user_details.getString("sp_w_apikey", null);

        s_ln_tab1=  Shared_user_details.getBoolean("login_tab1",false);
        s_ln_tab2=  Shared_user_details.getBoolean("login_tab2",false);
        s_ln_tab3=  Shared_user_details.getBoolean("login_tab3",false);

        ET_address.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.et_address) {
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
        ET_emailid.setText(s_lnw_usermailid);
        getVendorplan();
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, compl_vendor_offertype);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        SP_vendor_com_offertype.setAdapter(spinnerArrayAdapter);


        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, gender);
        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        SP_gender_sel.setAdapter(spinnerArrayAdapter2);
        SP_gender_sel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String gender = parent.getItemAtPosition(position).toString();
                getvendor_plannameid(position);

            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        if (isConnectedToNetwork()) {
            if (session.isLoggedIn()) {

                try {
                    if (s_lnw_usertype.equals("user")) {
                        if (s_ln_tab1) {
                            if (s_ln_tab2) {
                                if (s_ln_tab3) {

                                    Intent intent = new Intent(ProfileInfo.this, HomeScreen.class);
                                    editor = Shared_user_details.edit();
                                    editor.putString("weqar_uid", s_lnw_userid);
                                    editor.putString("weqar_token", s_lnw_usertoken);
                                    editor.apply();
                                    editor.commit();
                                    startActivity(intent);
                                } else {
                                    toolbar.setTitle("Subscription");
                                    getUserCompletesubscription();
                                    scrollView_personal.setVisibility(View.INVISIBLE);
                                    scrollView_professional.setVisibility(View.INVISIBLE);
                                    but_complete.setVisibility(View.VISIBLE);
                                    scrollView_complete.setVisibility(View.VISIBLE);
                                    IV_personal.setImageResource(R.drawable.profile_basic_three);
                                    IV_professional.setImageResource(R.drawable.profile_professional_three);
                                    IV_complete.setImageResource(R.drawable.profile_complete_two);
                                    view1.setBackgroundResource(R.color.colorAccent);
                                    view2.setBackgroundResource(R.color.colorAccent);
                                    view3.setBackgroundResource(R.color.colorAccent);
                                }
                            } else {
                                toolbar.setTitle("Professional");
                                scrollView_personal.setVisibility(View.INVISIBLE);
                                scrollView_professional.setVisibility(View.VISIBLE);
                                scrollView_complete.setVisibility(View.INVISIBLE);
                                IV_personal.setImageResource(R.drawable.profile_basic_three);
                                IV_professional.setImageResource(R.drawable.profile_professional_two);
                                IV_complete.setImageResource(R.drawable.profile_complete_one);
                                view1.setBackgroundResource(R.color.colorAccent);
                                view2.setBackgroundResource(R.color.colorAccent);
                            }
                        } else {
                            toolbar.setTitle("Basic Infos");
                            scrollView_personal.setVisibility(View.VISIBLE);
                            scrollView_professional.setVisibility(View.INVISIBLE);
                            scrollView_complete.setVisibility(View.INVISIBLE);
                            view1.setBackgroundResource(R.color.colorAccent);
                        }
                    }
                    else if (s_lnw_usertype.equals("vendor")) {
                        IV_basic_image.setVisibility(View.GONE);
                        IV_bas.setVisibility(View.GONE);
                        if (s_ln_tab1 && s_ln_tab2) {
                            Intent intent = new Intent(ProfileInfo.this, HomeScreen_vendor.class);
                            editor = Shared_user_details.edit();
                            editor.putString("weqar_uid", s_lnw_userid);
                            editor.putString("weqar_token", s_lnw_usertoken);
                            editor.apply();
                            editor.commit();
                            startActivity(intent);
                        } else {

                            if (!s_ln_tab1) {
                                toolbar.setTitle("Basic Info");
                                scrollView_personal.setVisibility(View.VISIBLE);
                                scrollView_professional.setVisibility(View.INVISIBLE);
                                scrollView_complete.setVisibility(View.INVISIBLE);
                                view1.setBackgroundResource(R.color.colorAccent);
                            } else if (!s_ln_tab2) {
                                toolbar.setTitle("Verification");
                                scrollview_vendor_professional.setVisibility(View.VISIBLE);
                                scrollView_personal.setVisibility(View.INVISIBLE);
                                scrollView_professional.setVisibility(View.INVISIBLE);
                                scrollView_complete.setVisibility(View.INVISIBLE);
                                IV_personal.setImageResource(R.drawable.profile_basic_three);
                                IV_professional.setImageResource(R.drawable.profile_professional_two);
                                IV_complete.setImageResource(R.drawable.profile_complete_one);
                                view1.setBackgroundResource(R.color.colorAccent);
                                view2.setBackgroundResource(R.color.colorAccent);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            Calendar now = Calendar.getInstance();
//            dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
//                    ProfileInfo.this,
//                    now.get(Calendar.YEAR),
//                    now.get(Calendar.MONTH),
//                    now.get(Calendar.DAY_OF_MONTH)
//            );
            focuschange();
            ET_Prof_valid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDate(2018, 0, 1, R.style.DatePickerSpinner);

                }
            });
            ET_vprof_category.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProfileInfo.this, MultiSpinner_Vendor_Category.class);

                    startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
                }
            });

            IV_basic_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 1002);
                }
            });

            IV_prof_uploadfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 100);
                }
            });
            IV_vendor_professional_companylogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 1001);
                }
            });

            IV_vcomplete_imageupload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 1005);
                }
            });
            ET_pedit_location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent inten=new Intent(ProfileInfo.this,SelectLocations.class);
                    startActivityForResult(inten, SECOND_ACTIVITY_REQUEST_CODEs);
                }
            });
            B_saveandcontinue_personal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callmetouploadbasic();
                    //  getmydet(s_lnw_userid);

                }
            });
            B_professional_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callmetouploadprofessional();
                    //getmydet(s_lnw_userid);


                }
            });
            B_vendorprofessional_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    callmetouploadprofessional_vendor();
                    //getmydet(s_lnw_userid);


                }
            });
            but_complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callmetouploadusercomplete();
                    //    getmydet(s_lnw_userid);

                }
            });
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
                        ET_vcomplete_percentage.setVisibility(View.VISIBLE);
                        TIL_vcomplete_percentage.setVisibility(View.VISIBLE);
                    } else if (S_vcomple_offertype_sel.equals("Offer") || S_vcomple_offertype_sel.matches("Offer")) {
                        ET_vcomplete_percentage.setVisibility(View.INVISIBLE);
                        TIL_vcomplete_percentage.setVisibility(View.INVISIBLE);
                    }
                }

                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            TV_vcomplete_skip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count_on_skip_forvendor = "1";
                    Intent intent = new Intent(ProfileInfo.this, HomeScreen_vendor.class);
                    editor = Shared_user_details.edit();
                    editor.putString("weqar_uid", s_lnw_userid);
                    editor.putString("weqar_token", s_lnw_usertoken);
                    editor.apply();
                    editor.commit();
                    startActivity(intent);
                }
            });
            B_vcomplete_complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    callmetouploadvendorcomplete();
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
                    Intent intent = new Intent(ProfileInfo.this, ProfileInfo.class);
                    startActivity(intent);
                }
            });


        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1002 && resultCode == RESULT_OK && data != null) {


            Uri imageUri = data.getData();

            assert imageUri != null;
            UCrop.of( imageUri,  Uri.fromFile(new File(getCacheDir(), ".png")))
                    .withAspectRatio(3 , 2)
                    .start(ProfileInfo.this);
            check_image_id=1002;
        }
                if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP && check_image_id==1002) {
                    final Uri resultUri = UCrop.getOutput(data);
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                        IV_bas.setImageBitmap(bitmap);
                        basic_image(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (resultCode == UCrop.RESULT_ERROR) {
                    final Throwable cropError = UCrop.getError(data);
                }


        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();

            assert imageUri != null;
            UCrop.of( imageUri,  Uri.fromFile(new File(getCacheDir(), ".png")))
                    .withAspectRatio(3 , 2)
                    .start(ProfileInfo.this);
            check_image_id=100;
        }
                if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP && check_image_id==100) {
                    final Uri resultUri = UCrop.getOutput(data);
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                        IV_prof_uploadfile.setImageBitmap(bitmap);
                   upload_user_profimage(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (resultCode == UCrop.RESULT_ERROR) {
                    final Throwable cropError = UCrop.getError(data);
                }
        if (requestCode == 1001 && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();

            assert imageUri != null;
            UCrop.of( imageUri,  Uri.fromFile(new File(getCacheDir(), ".png")))
                    .withAspectRatio(3 , 2)
                    .start(ProfileInfo.this);
            check_image_id=1001;

        }
                if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP && check_image_id==1001) {
                    final Uri resultUri = UCrop.getOutput(data);
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                        IV_vendor_professional_companylogo.setImageBitmap(bitmap);
                        upload_vendor_companylogo(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (resultCode == UCrop.RESULT_ERROR) {
                    final Throwable cropError = UCrop.getError(data);
                }
        if (requestCode == 1005 && resultCode == RESULT_OK && data != null) {
//            Uri imageUri = data.getData();
//
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
//                IV_vcomplete_imageupload.setImageBitmap(bitmap);
//                upload_vendor_complete_image(bitmap);
//            }
//            catch (IOException e) {
//                e.printStackTrace();
//            }
            Uri imageUri = data.getData();

            assert imageUri != null;
            UCrop.of( imageUri,  Uri.fromFile(new File(getCacheDir(), ".png")))
                    .withAspectRatio(3 , 2)
                    .start(ProfileInfo.this);
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
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                TinyDB tinydb = new TinyDB(context);
                Ssubjectkind=tinydb.getString("subjecttype");
                if (Ssubjectkind.equals("Subject")) {
                    subjectnamelist = tinydb.getListString("subjectnamelist");
                    subjectnameid = tinydb.getListString("subjectnameid");
                    StringBuilder builder = new StringBuilder();
                    // JSONArray startendarray=new JSONArray();
                    for (int i = 0; i < subjectnamelist.size(); i++) {
                        //   JSONObject obj=new JSONObject();
                        // try {
//                obj.put("start",starttimesched.get(i));
//                obj.put("end",endtimesched.get(i));
                        builder.append("").append(subjectnamelist.get(i)).append(",");

                        //   } catch (JSONException e) {
                        //      e.printStackTrace();
                    }

                    ET_vprof_category.setText(builder);

                }


                //   startendarray.put(obj);
                //   }
                //Toast.makeText(context, "i got you", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODEs) {
            if (resultCode == RESULT_OK) {
                v_edit_location=  Shared_user_details.getString("send_mylatitude", null);
                v_edit_locationo=  Shared_user_details.getString("send_longitude", null);

                ET_pedit_location.setText(v_edit_location+","+v_edit_locationo);
            }
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
                        s_basic_image=jObj.getString("Response");
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
    private void upload_user_profimage(final Bitmap bitmap) {

        Bitmap immagex=bitmap;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 50, baos);
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
                    try
                    {
                        JSONObject jObj = new JSONObject(response);
                        s_res_userprofimg=jObj.getString("Response");
                        Log.i("user_prof_image",response);

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
    private void upload_vendor_companylogo(final Bitmap bitmap)
    {
        Bitmap immagex=bitmap;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 50, baos);
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

                    try
                    {
                        JSONObject jObj = new JSONObject(response);
                        s_lnw_getcompany=jObj.getString("Response");
                      //  Log.i("user_vendor_companylogo_response",response);

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
    private void upload_vendor_complete_image(final Bitmap bitmap)
    { Bitmap immagex=bitmap;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 50, baos);
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
                    try
                    {
                        JSONObject jObj = new JSONObject(response);
                        s_vcomplete_image_response=jObj.getString("Response");
                        //Log.i("user_vendor_complete_image_response",response);


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
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    public void callmetouploadbasic()
    {



        if(ET_fname.getText().toString().equals(""))
        {
            new PromptDialog(ProfileInfo.this)
                    .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                    .setAnimationEnable(true)
                    .setTitleText("Please Enter First Name")
                    .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                        @Override
                        public void onClick(PromptDialog dialog) {
                            dialog.dismiss();
                        }
                    }).show();
        }
        else
        {
//            if(ET_mname.getText().toString().equals(""))
//            {
//                new PromptDialog(ProfileInfo.this)
//                        .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
//                        .setAnimationEnable(true)
//                        .setTitleText("Please Enter Middle Name")
//                        .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
//                            @Override
//                            public void onClick(PromptDialog dialog) {
//                                dialog.dismiss();
//                            }
//                        }).show();
//            }
//            else
//            {
                if(ET_lname.getText().toString().equals(""))
                {
                    new PromptDialog(ProfileInfo.this)
                            .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                            .setAnimationEnable(true)
                            .setTitleText("Please Enter Last Name")
                            .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
                else
                {
//                    if(ET_emailid.getText().toString().equals(""))
//                    {
//                        new PromptDialog(ProfileInfo.this)
//                                .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
//                                .setAnimationEnable(true)
//                                .setTitleText("Please Enter Your Email Id")
//                                .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
//                                    @Override
//                                    public void onClick(PromptDialog dialog) {
//                                        dialog.dismiss();
//                                    }
//                                }).show();
//                    }
//                    else
//                    {
                        if(ET_mobile.getText().toString().equals(""))
                        {
                            new PromptDialog(ProfileInfo.this)
                                    .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                    .setAnimationEnable(true)
                                    .setTitleText("Please Enter Mobile Number")
                                    .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                        @Override
                                        public void onClick(PromptDialog dialog) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                        }
                        else
                        {
                            if(ET_address.getText().toString().equals(""))
                            {
                                new PromptDialog(ProfileInfo.this)
                                        .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                        .setAnimationEnable(true)
                                        .setTitleText("Please Enter Address")
                                        .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                            @Override
                                            public void onClick(PromptDialog dialog) {
                                                dialog.dismiss();
                                            }
                                        }).show();
                            }
                            else
                            {
                                if(ET_mname.getText().toString().isEmpty())
                                {
                                    ET_mname.setText("mname");
                                }
                                if(ET_emailid.getText().toString().isEmpty())
                                {
                                    ET_emailid.setText("a@gmail.com");
                                }
                                s_fname=ET_fname.getText().toString();
                                s_mname=ET_mname.getText().toString();
                                s_lname=ET_lname.getText().toString();
                                s_emailid=ET_emailid.getText().toString();
                                s_mobilepin="+965";
                                s_mobile=ET_mobile.getText().toString();
                                s_address=ET_address.getText().toString();
                                // s_city=ET_city.getText().toString();
                                // s_zipcode=ET_zipcode.getText().toString();
                                s_country="Kuwait";
                                scrollView_personal.setVisibility(View.INVISIBLE);

                                if(s_lnw_usertype.equals("user")||s_lnw_usertype.matches("user"))
                                {
                                    scrollView_professional.setVisibility(View.VISIBLE);
                                    toolbar.setTitle("Professional");
                                }
                                else if(s_lnw_usertype.equals("vendor")||s_lnw_usertype.matches("vendor"))
                                {
                                    scrollview_vendor_professional.setVisibility(View.VISIBLE);
                                    toolbar.setTitle("Verification");
                                }
                                scrollView_complete.setVisibility(View.INVISIBLE);
                                view2.setBackgroundResource(R.color.colorAccent);
                                IV_personal.setImageResource(R.drawable.profile_basic_three);
                                IV_professional.setImageResource(R.drawable.profile_professional_two);
                                IV_complete.setImageResource(R.drawable.profile_complete_one);
                                    dialog.show();
                                callmetouploadbasicurl(s_lnw_userid,s_emailid,s_fname,s_mname,s_lname,s_mobile,s_address,s_country);
                            }
                        }
                  //  }

                }
           // }
        }



    }
    public void callmetouploadbasicurl(String user_uid,String user_email,String sfname,
                                       String smname,String slname,String smobile,String saddress,String scountry) {
        try {


            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonBody = new JSONObject();
           jsonBody.put("Address1", saddress);
        //    jsonBody.put("Address2", scity);
            jsonBody.put("Id", user_uid);
            jsonBody.put("Image", s_basic_image);
            jsonBody.put("FirstName", sfname);
            jsonBody.put("LastName", slname);
            jsonBody.put("MiddleName", smname);
            jsonBody.put("Email", user_email);
            jsonBody.put("PhoneNumber",smobile);
            jsonBody.put("Country",scountry);


            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.User_insertbasicinfo, new Response.Listener<String>() {

                public void onResponse(String response) {
                   // startActivity(new Intent(ProfileInfo.this, LoginActivity.class));
                    dialog.hide();
                    Log.i("basic_details_response",response);
                    try {
                        JSONObject jObj = new JSONObject(response);

                        String status = jObj.getString("Status");
                        if(status.equals("success"))
                        {

                            editor.putBoolean("login_tab1", true);
                            editor.apply();
                            editor.commit();

                        }
                    }catch (Exception e){

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
    public void callmetouploadprofessional()
    {



        if(ET_Prof_cidno.getText().toString().equals(""))
        {
            new PromptDialog(ProfileInfo.this)
                    .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                    .setAnimationEnable(true)
                    .setTitleText("Please Enter C.I.D.Number")
                    .setPositiveListener(("Ok"), new PromptDialog.OnPositiveListener() {
                        @Override
                        public void onClick(PromptDialog dialog) {
                            dialog.dismiss();
                        }
                    }).show();
        }
        else
        {
//            if(ET_Prof_memno.getText().toString().equals(""))
//            {
//                new PromptDialog(ProfileInfo.this)
//                    .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
//                    .setAnimationEnable(true)
//                    .setTitleText("Please Enter Member Number")
//                    .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
//                        @Override
//                        public void onClick(PromptDialog dialog) {
//                            dialog.dismiss();
//                        }
//                    }).show();
//
//            }
//            else
//            {
//                if(ET_Prof_valid.getText().toString().equals(""))
//                {
//                    new PromptDialog(ProfileInfo.this)
//                        .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
//                        .setAnimationEnable(true)
//                        .setTitleText("Please Enter Valid Date")
//                        .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
//                            @Override
//                            public void onClick(PromptDialog dialog) {
//                                dialog.dismiss();
//                            }
//                        }).show();
//
//                }
//                else
//                {
//            if(ET_Prof_memno.getText().toString().isEmpty())
//            {
//                ET_Prof_memno.setText("123");
//            }
//            if(ET_Prof_valid.getText().toString().isEmpty())
//            {
//                ET_Prof_valid.setText("12/12/12");
//            }
                    s_prof_cidno=ET_Prof_cidno.getText().toString();
                    s_prof_memno=ET_Prof_memno.getText().toString();
                    s_prof_valid=ET_Prof_valid.getText().toString(); scrollView_personal.setVisibility(View.INVISIBLE);
                    scrollView_professional.setVisibility(View.INVISIBLE);
                    scrollview_vendor_professional.setVisibility(View.INVISIBLE);
                    scrollView_complete.setVisibility(View.VISIBLE);
                    view3.setBackgroundResource(R.color.colorAccent);
                    toolbar.setTitle("Subscription");
                    IV_personal.setImageResource(R.drawable.profile_basic_three);
                    IV_professional.setImageResource(R.drawable.profile_professional_three);
                    IV_complete.setImageResource(R.drawable.profile_complete_two);
                    but_complete.setVisibility(View.VISIBLE);
                    dialog.show();
                    callmetouploadprofessionalurl(s_prof_cidno,s_prof_memno,s_lnw_userid,s_prof_valid,s_res_userprofimg);
                    getUserCompletesubscription();
                    IV_personal.setImageResource(R.drawable.profile_basic_three);
                                IV_professional.setImageResource(R.drawable.profile_professional_three);
                                IV_complete.setImageResource(R.drawable.profile_complete_three);
                                view4.setBackgroundResource(R.color.colorAccent);
               // }
          //  }

        }

    }

    public void callmetouploadprofessionalurl(String sprofcidno,String sprofmemno,String sprofuserid,String sprofvalid,String sprofusertoken)
    {
        try {


            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("CIDNO", sprofcidno);
            jsonBody.put("MemberNo", sprofmemno);
            jsonBody.put("UserId", sprofuserid);
            jsonBody.put("ValidTo", sprofvalid);
            jsonBody.put("Image",sprofusertoken);



            final String requestBody = jsonBody.toString();

            Log.i("professio_name",requestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.User_insertprofessinalinfo, new Response.Listener<String>() {

                public void onResponse(String response) {
                   // startActivity(new Intent(ProfileInfo.this, LoginActivity.class));
                    dialog.hide();
                    Log.i("user_professional",response);

                    try {
                        JSONObject jObj = new JSONObject(response);

                        String status = jObj.getString("Status");
                        if(status.equals("success"))
                        {

                            editor.putBoolean("login_tab2", true);
                            editor.apply();
                            editor.commit();

                        }
                    }catch (Exception e){

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

    public void callmetouploadusercomplete()
    {
        String  categid_book =  Shared_user_details.getString("sel_user_plantype", null);
        TinyDB tinydb = new TinyDB(context);
        s_fromadp_getuser_plantype_id=tinydb.getString("check_userplantype_id");
        s_fromadp_getuser_plantype=tinydb.getString("check_userplantype_type");
        assert categid_book != null;
        if(TextUtils.isEmpty(categid_book))
        {
            new PromptDialog(ProfileInfo.this)
                    .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                    .setAnimationEnable(true)
                    .setTitleText("Subscription Plan")
                    .setContentText("Please Choose Subscription Plan")
                    .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                        @Override
                        public void onClick(PromptDialog dialog) {
                            dialog.dismiss();
                        }
                    }).show();
        }
        else
        {
            IV_complete.setImageResource(R.drawable.profile_complete_three);
            view4.setBackgroundResource(R.color.colorAccent);
            dialog.show();
            callmetouploadusercomplete_url(s_fromadp_getuser_plantype,s_fromadp_getuser_plantype_id,s_lnw_userid);
            Intent intent = new Intent(ProfileInfo.this, HomeScreen.class);
            editor = Shared_user_details.edit();
            editor.putString("weqar_uid", s_lnw_userid);
            editor.putString("weqar_token", s_lnw_usertoken);
            editor.apply();
            editor.commit();
            startActivity(intent);        }

    }
    public void callmetouploadusercomplete_url(String user_comple_plantype,String user_comple_plantype_id,String user_compl_userid)
    {
        try {


            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("PlanType", user_comple_plantype);
            jsonBody.put("PlanTypeId", user_comple_plantype_id);
            jsonBody.put("UserId", user_compl_userid);

            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.user_insert_completedetails, new Response.Listener<String>() {

                public void onResponse(String response) {
                    dialog.hide();
                    Log.i("user_complete_response",response);
                    try {
                        JSONObject jObj = new JSONObject(response);

                        String status = jObj.getString("Status");
                        if(status.equals("success"))
                        {

                            editor.putBoolean("login_tab3", true);
                            editor.apply();
                            editor.commit();

                        }
                    }catch (Exception e){

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
                    // headers.put("content-Type", "application/json");
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
    public void callmetouploadprofessional_vendor()
    {


        if(ET_vprof_category.getText().toString().equals(""))
        {
            new PromptDialog(ProfileInfo.this)
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
            if(ET_vprof_businescontect.getText().toString().equals(""))
            {
                new PromptDialog(ProfileInfo.this)
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
                if(ET_vprof_businesemail.getText().toString().equals(""))
                {
                    new PromptDialog(ProfileInfo.this)
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

                    svendor_busimail = ET_vprof_businesemail.getText().toString().trim();
                    svendor_comapny = ET_vrpof_company.getText().toString().trim();
                    if (svendor_busimail.matches(SemailPattern)) {
//                        if (ET_vprof_websitel.getText().toString().equals(""))
//                        {
//                            new PromptDialog(ProfileInfo.this)
//                                    .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
//                                    .setAnimationEnable(true)
//                                    .setTitleText("Please Enter Website Link")
//                                    .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
//                                        @Override
//                                        public void onClick(PromptDialog dialog) {
//                                            dialog.dismiss();
//                                        }
//                                    }).show();
//
//                        }
//                        else {


                            s_vprof_category = ET_vprof_category.getText().toString();

                            if (ET_vprof_websitel.getText().toString().isEmpty())
                            {
                                s_vprof_websitelink="sdasda";
                            }
                            s_vprof_buscontact = ET_vprof_businescontect.getText().toString();
                            s_vprof_busemail = ET_vprof_businesemail.getText().toString();
                            s_vprof_websitelink = ET_vprof_websitel.getText().toString();
                            scrollView_personal.setVisibility(View.INVISIBLE);
                            scrollView_professional.setVisibility(View.INVISIBLE);
                            scrollview_vendor_professional.setVisibility(View.INVISIBLE);
                            scrollView_complete.setVisibility(View.INVISIBLE);
                            view3.setBackgroundResource(R.color.colorAccent);
                            toolbar.setTitle("Subscription");
                            IV_personal.setImageResource(R.drawable.profile_basic_three);
                            IV_professional.setImageResource(R.drawable.profile_professional_three);
                            IV_complete.setImageResource(R.drawable.profile_complete_two);
                            but_complete.setVisibility(View.INVISIBLE);
                            scrollView_vendor_complete.setVisibility(View.VISIBLE);
                            dialog.show();
                            callmetouploadprofessionalurl_vendor(s_vprof_buscontact, s_vprof_busemail, s_lnw_getcompany,
                                    s_lnw_userid, s_vprof_category,svendor_comapny,v_edit_location,v_edit_locationo);
                            getUserCompletesubscription();
                            IV_personal.setImageResource(R.drawable.profile_basic_three);
                            IV_professional.setImageResource(R.drawable.profile_professional_three);
                            IV_complete.setImageResource(R.drawable.profile_complete_two);
                            //view4.setBackgroundResource(R.color.colorAccent);

                      //  }
                    }
                    else
                    {
                        new PromptDialog(ProfileInfo.this)
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

    public void callmetouploadprofessionalurl_vendor(String buscontact,String busemail,String vpro_logo,String vpro_userid,String v_procateg,String companyname,String lati,String longi)
    {
        try {


            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("BusinessContact", buscontact);
            jsonBody.put("BusinessEmail", busemail);
            jsonBody.put("Logo", vpro_logo);
            jsonBody.put("UserId", vpro_userid);
            jsonBody.put("CompanyName", companyname);
            jsonBody.put("Latitude", lati);
            jsonBody.put("Longitude", longi);
            JSONArray array2=new JSONArray(subjectnameid);
            jsonBody.put("Categories",array2);



            final String requestBody = jsonBody.toString();
            Log.i("requsitingcompany",requestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.Vendor_insertprofessionalinfo, new Response.Listener<String>() {

                public void onResponse(String response) {
                    // startActivity(new Intent(ProfileInfo.this, LoginActivity.class));
                    dialog.hide();
                    try {
                        JSONObject jObj = new JSONObject(response);

                        String status = jObj.getString("Status");
                        if(status.equals("success"))
                        {

                            editor.putBoolean("login_tab2", true);
                            editor.apply();
                            editor.commit();

                        }
                    }catch (Exception e){

                        e.printStackTrace();
                    }

                 //   Log.i("vendor_professional_response",response);

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
       String  event_startdate_one =  (monthOfYear + 1) + "/"+dayOfMonth  + "/" + year;
        ET_Prof_valid.setText(event_startdate_one);
    }
    public void getUserCompletesubscription()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Global_URL.User_subscriptiondet_get, new Response.Listener<String>() {

            public void onResponse(String response) {
                try {

                    JSONObject jObj = new JSONObject(response);
                    JSONArray jsonArray = jObj.getJSONArray("Response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String s_uplan_plantypes = object.getString("PlanType");
                        s_uplan_plantype=s_uplan_plantypes.trim();
                        s_uplan_amount = object.getString("Amount");
                        s_uplan_desc= object.getString("Description");
                        s_uplan_planid=object.getString("Id");
                        L_user_planid.add(String.valueOf(s_uplan_planid));
                        L_user_plantype.add(String.valueOf(s_uplan_plantype));
                        L_user_planamount.add(String.valueOf(s_uplan_amount));
                        L_user_desc.add(String.valueOf(s_uplan_desc));

                    }
                    Rec_usersubs.setAdapter(RecyclerViewHorizontalAdapter);
                }

                catch (JSONException e) {
                    e.printStackTrace();
                }
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
                return headers;
            }
        };

        requestQueue.add(stringRequest);
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
        SP_vendor_com_planchoose.setAdapter(new ArrayAdapter<String>(ProfileInfo.this, android.R.layout.simple_dropdown_item_1line, vendor_plan));
    }
    public void callmetouploadvendorcomplete()
    {
        try {
            if(S_vcomple_plantype_sel.equals(""))
            {
                new PromptDialog(ProfileInfo.this)
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
                    new PromptDialog(ProfileInfo.this)
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
                        new PromptDialog(ProfileInfo.this)
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
                            new PromptDialog(ProfileInfo.this)
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
                            if(s_vcomplete_image_response.equals(""))
                            {
                                new PromptDialog(ProfileInfo.this)
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
                                S_vcomplete_percentage = ET_vcomplete_percentage.getText().toString();
                                S_vcomplete_title = ET_vcomplete_disctitle.getText().toString();
                                s_vcomplete_description = ET_vcomplete_discdesc.getText().toString();
                                dialog.show();
                                callmetouploadvendorcomplete_url(s_vcomplete_description,s_vcomplete_image_response,s_lnw_userid
                                        , S_vcomplete_title, S_vcomplete_percentage, serviceuid, S_vcomple_offertype_sel);
                                Intent intent=new Intent(ProfileInfo.this,HomeScreen_vendor.class);
                                editor = Shared_user_details.edit();
                                editor.putString("weqar_uid",s_lnw_userid);
                                editor.putString("weqar_token",s_lnw_usertoken);
                                editor.apply();
                                editor.commit();
                                TinyDB tinydb = new TinyDB(context);
                                tinydb.putBoolean("hgffh", true);

                                startActivity(intent);
                            }


                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
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
    public void callmetouploadvendorcomplete_url(String v_discdesc,String image,String id,String title,String percentage,
                                                 String plantype,String ofertype)
    {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Description", v_discdesc);
            jsonBody.put("Images", image);
            jsonBody.put("VendorId", id);
            jsonBody.put("Title", title);
            jsonBody.put("Percentage", percentage);
            jsonBody.put("DiscountPlan", plantype);
            jsonBody.put("DiscountType", ofertype);
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.vendor_insert_completedetails, new Response.Listener<String>() {
                public void onResponse(String response) {
                    dialog.hide();
                    Log.i("vendor_complete",response);
                    try {
                        JSONObject jObj = new JSONObject(response);

                        String status = jObj.getString("Status");
                        if(status.equals("success"))
                        {

                            editor.putBoolean("login_tab3", true);
                            editor.apply();
                            editor.commit();

                        }
                    }catch (Exception e){

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

    public void focuschange()
    {
        ET_fname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_fname.setTextColor((getResources().getColor(R.color.colorPrimary)));

                }
                else
                {
                    ET_fname.setTextColor((getResources().getColor(R.color.colorPrimary)));

                }
            }
        });
        ET_mname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_mname.setTextColor((getResources().getColor(R.color.colorPrimary)));

                }
                else
                {
                    ET_mname.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
        });
        ET_lname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_lname.setTextColor((getResources().getColor(R.color.colorPrimary)));

                }
                else
                {
                    ET_lname.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
        });
        ET_mobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_mobile.setTextColor((getResources().getColor(R.color.colorPrimary)));

                }
                else
                {
                    ET_mobile.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
        });
        ET_emailid.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_emailid.setTextColor((getResources().getColor(R.color.colorPrimary)));

                }
                else
                {
                    ET_emailid.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
        });
        ET_address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_address.setTextColor((getResources().getColor(R.color.colorPrimary)));
                }
                else
                {
                    ET_address.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
        });


        ET_Prof_cidno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_Prof_cidno.setTextColor((getResources().getColor(R.color.colorPrimary)));
                }
                else
                {
                    ET_Prof_cidno.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
        });
        ET_Prof_memno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_Prof_memno.setTextColor((getResources().getColor(R.color.colorPrimary)));
                }
                else
                {
                    ET_Prof_memno.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
        });
        ET_Prof_valid.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_Prof_valid.setTextColor((getResources().getColor(R.color.colorPrimary)));
                }
                else
                {
                    ET_Prof_valid.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
        });
        ET_vrpof_company.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_vrpof_company.setTextColor((getResources().getColor(R.color.colorPrimary)));
                }
                else
                {
                    ET_vrpof_company.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
        });
        ET_vprof_category.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_vprof_category.setTextColor((getResources().getColor(R.color.colorPrimary)));
                }
                else
                {
                    ET_vprof_category.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
        });
        ET_vprof_businescontect.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_vprof_businescontect.setTextColor((getResources().getColor(R.color.colorPrimary)));
                }
                else
                {
                    ET_vprof_businescontect.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
        });
        ET_vprof_businesemail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_vprof_businesemail.setTextColor((getResources().getColor(R.color.colorPrimary)));
                }
                else
                {
                    ET_vprof_businesemail.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
        });
        ET_vprof_websitel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    ET_vprof_websitel.setTextColor((getResources().getColor(R.color.colorPrimary)));
                }
                else
                {
                    ET_vprof_websitel.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
        });
        ET_vcomplete_percentage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus)
                {
                    ET_vcomplete_percentage.setTextColor((getResources().getColor(R.color.colorPrimary)));
                }
                else
                {
                    ET_vcomplete_percentage.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
         });
        ET_vcomplete_disctitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus)
                {
                    ET_vcomplete_disctitle.setTextColor((getResources().getColor(R.color.colorPrimary)));
                }
                else
                {
                    ET_vcomplete_disctitle.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
        });
        ET_vcomplete_discdesc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus)
                {
                    ET_vcomplete_discdesc.setTextColor((getResources().getColor(R.color.colorPrimary)));
                }
                else
                {
                    ET_vcomplete_discdesc.setTextColor((getResources().getColor(R.color.colorBlack)));
                }
            }
        });
    }
    private boolean isConnectedToNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
