package com.weqar.weqar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.weqar.weqar.DBJavaClasses.events_list_vendor;
import com.weqar.weqar.Fragments.BotNav_DiscountsFragment;
import com.weqar.weqar.Fragments.BotNav_EventsFragment;
import com.weqar.weqar.Global_url_weqar.Global_URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.refactor.lib.colordialog.PromptDialog;


public class Events_Display extends AppCompatActivity {
    String s_vendor_disc,s_vendor_token;
    SwipeMenuListView GV_user_view;
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    ImageView IV_addevent_user,events_v_back;
    List<events_list_vendor> milokilo;
    String Sscrollist="1";
    ProgressDialog dialog;
  MovieAdap adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events__display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (isConnectedToNetwork()) {

            Shared_user_details = getSharedPreferences("user_detail_mode", 0);
            s_vendor_disc = Shared_user_details.getString("weqar_uid", null);
            s_vendor_token = Shared_user_details.getString("weqar_token", null);
            GV_user_view = findViewById(R.id.events_user_list);
            IV_addevent_user = findViewById(R.id.events_u_addevent);
            dialog = new ProgressDialog(this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setMessage("Loading. Please wait...");
            events_v_back = findViewById(R.id.events_u_back);
            events_v_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Events_Display.this, HomeScreen.class));
                    finish();
                }
            });
            IV_addevent_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Events_Display.this, AddEvents_User.class));
                }
            });


            GV_user_view.setOnScrollListener(new EndlessScrollListener() {
                @Override
                public void onLoadMore(int page, int totalItemsCount) {

                    Sscrollist=String.valueOf(page);
                    String URLLL = Global_URL.Vendor_show_allevents;
                    new newkilomilo().execute(URLLL);

                }
            });




            new kilomilo().execute(Global_URL.Vendor_show_allevents);
        }
        else
        {
            setContentView(R.layout.content_if_nointernet);
            ImageView but_retry = findViewById(R.id.nointernet_retry);
            but_retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Events_Display.this, Events_Display.class);
                    startActivity(intent);
                }
            });
        }

    }

    public class MovieAdap extends ArrayAdapter
    {
        private List<events_list_vendor> movieModelList;
        private int resource;
        Context context;
        private LayoutInflater inflater;
        MovieAdap(Context context, int resource, List<events_list_vendor> objects)
        {
            super(context, resource, objects);
            movieModelList = objects;
            this.context = context;
            this.resource = resource;
            inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getViewTypeCount() {
            return 1;
        }
        @Override
        public int getItemViewType(int position) {
            return position;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            final MovieAdap.ViewHolder holder;
            if (convertView == null)
            {
                convertView = inflater.inflate(resource, null);
                holder = new MovieAdap.ViewHolder();
                holder.textone = (TextView) convertView.findViewById(R.id.events_vendor_title);
                holder.texttwo_desc = (TextView) convertView.findViewById(R.id.events_vendor_desc);
                holder.textstartd = (TextView) convertView.findViewById(R.id.event_startdate);
                holder.textendd = (TextView) convertView.findViewById(R.id.event_enddate);
                holder.menuimage = convertView.findViewById(R.id.events_vendor_image);

                convertView.setTag(holder);
            }
            else
            {
                holder = (MovieAdap.ViewHolder) convertView.getTag();
            }
            final events_list_vendor ccitacc = movieModelList.get(position);


            try
            {
                holder.textone.setText(ccitacc.getTitle());
                holder.texttwo_desc.setText("by "+ccitacc.getName());


                String dash_event_start=DateTimeUtils.formatWithPattern(ccitacc.getEventStart(), "dd-MM-yyyy");
                String dash_eventisc_end=DateTimeUtils.formatWithPattern(ccitacc.getEventEnd(), "dd-MM-yyyy");
                String time_sched=ccitacc.getEventStart().substring(11,16);
                String time_sched_end=ccitacc.getEventEnd().substring(11,16);
                holder.textstartd.setText("Start Date : "+dash_event_start+" "+time_sched);
                holder.textendd.setText("End Date : "+dash_eventisc_end+" "+time_sched_end);

                Picasso.with(context).load(Global_URL.Image_url_load+ccitacc.getImage()).error(getResources().getDrawable(R.drawable.rounded_two)).fit().centerCrop().into(holder.menuimage);
            }catch (Exception e){
                e.printStackTrace();
            }
            SwipeMenuCreator creator = new SwipeMenuCreator()
            {
                @Override
                public void create(SwipeMenu menu)
                {
                    SwipeMenuItem more_sched = new SwipeMenuItem(
                            getContext());
                    more_sched.setBackground(R.color.colorHints);
                    more_sched.setWidth(180);
                    more_sched.setTitle("Edit");
                    more_sched.setIcon(R.drawable.ic_edit_black_24dp);
                    more_sched.setTitleSize(12);
                    more_sched.setTitleColor(Color.WHITE);
                    menu.addMenuItem(more_sched);
                    SwipeMenuItem review_sched = new SwipeMenuItem(
                            getContext());
                    review_sched.setBackground(R.color.colorPrimary);
                    review_sched.setWidth(180);
                    review_sched.setTitle("Delete");
                    review_sched.setTitleSize(12);
                    review_sched.setTitleColor(Color.WHITE);
                    review_sched.setIcon(R.drawable.discount_vendor_delete);
                    menu.addMenuItem(review_sched);
                }
            };
            GV_user_view.setMenuCreator(creator);
            GV_user_view.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener()
            {
                @Override
                public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                    events_list_vendor schedule_history_list = milokilo.get(position);

                    switch (index) {
                        case 0:
                            Intent intent=new Intent(getApplicationContext(),Events_Edit_User.class);
                            intent.putExtra("put_event_uid_edit",schedule_history_list.getUserId());
                            intent.putExtra("put_event_id_edit",schedule_history_list.getId());
                            intent.putExtra("put_event_startdate_edit",schedule_history_list.getEventStart());
                            intent.putExtra("put_event_enddate_edit",schedule_history_list.getEventEnd());
                            intent.putExtra("put_event_contact_edit",schedule_history_list.getName());
                            intent.putExtra("put_event_title_edit",schedule_history_list.getTitle());
                            intent.putExtra("put_event_amount_edit",schedule_history_list.getAmount());
                            intent.putExtra("put_event_desc_edit",schedule_history_list.getDescription());
                            intent.putExtra("put_event_image_edit",schedule_history_list.getImage());

                            startActivity(intent);
                            break;
                        case 1:
                            String ed=schedule_history_list.getId();
                            // Toast.makeText(getApplicationContext(),ed, Toast.LENGTH_SHORT).show();
                            callmetodeleteiscount(ed);
                            break;
                    }
                    return false;
                }
            });
            return convertView;
        }
        class ViewHolder {
            public TextView textone,texttwo_desc,textstartd,textendd;
            private ImageView menuimage;

        }
    }
    @SuppressLint("StaticFieldLeak")
    public class kilomilo extends AsyncTask<String, String, List<events_list_vendor>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected List<events_list_vendor> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                DataOutputStream printout;
                DataInputStream inputStream;
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput (true);
                connection.setDoOutput (true);
                connection.setUseCaches (false);
                connection.setRequestProperty("Content-Type","application/json");
                connection.setRequestProperty("x-api-type","Android");
                connection.setRequestProperty("x-api-key",s_vendor_token);
                Log.i("vendor_kjn",s_vendor_token);
                connection.setRequestMethod("POST");
                connection.connect();
                JSONObject auth=new JSONObject();
                auth.put("QueryFor","User");
                auth.put("UserId",s_vendor_disc);
                auth.put("PageNumber", Sscrollist);
                auth.put("RowsPerPage", "5");
                Log.i("authme",auth.toString());
                printout = new DataOutputStream(connection.getOutputStream ());
                printout.writeBytes(auth.toString());
                printout.flush ();
                printout.close ();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONObject ed = parentObject.getJSONObject("Response");
                JSONArray parentArray = ed.getJSONArray("Data");

                milokilo = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    events_list_vendor catego = gson.fromJson(finalObject.toString(), events_list_vendor.class);
                    milokilo.add(catego);
                }
                return milokilo;
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(final List<events_list_vendor> movieMode) {
            super.onPostExecute(movieMode);
            if((movieMode != null) && (movieMode.size()>0)){
                GV_user_view.setVisibility(View.VISIBLE);
              adapter = new MovieAdap(getApplicationContext(), R.layout.content_event_list_vendor, movieMode);
                GV_user_view.setAdapter(adapter);
                GV_user_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        events_list_vendor item = movieMode.get(position);
                        Intent intent = new Intent(getApplicationContext(),EventDetails_User.class);
                        intent.putExtra("event_v_id",item.getId());
                        intent.putExtra("event_v_userid",item.getUserId());
                        intent.putExtra("event_v_title",item.getTitle());
                        intent.putExtra("event_v_name",item.getName());
                        intent.putExtra("event_v_image",item.getImage());
                        intent.putExtra("event_v_desc",item.getDescription());
                        intent.putExtra("event_v_location",item.getLocation());
                        intent.putExtra("event_v_latitude",item.getLatitude());
                        intent.putExtra("event_v_longitude",item.getLongitude());
                        intent.putExtra("event_v_startdate",item.getEventStart());
                        intent.putExtra("event_v_enddate",item.getEventEnd());
                        intent.putExtra("event_v_duration",item.getDuration());
                        intent.putExtra("event_v_isrequest",item.getRegistered());
                        intent.putExtra("event_v_isapproved",item.getApproved());
                        intent.putExtra("event_v_amount",item.getAmount());
                        intent.putExtra("event_v_regreq",item.getRegistrationRequired());
                        intent.putExtra("event_v_amountpaid",item.getIsPaid());
                        intent.putExtra("event_v_prereq",item.getRequirements());


                        startActivity(intent);
                    }
                });
                adapter.notifyDataSetChanged();
            }

        }
    }
    public class newkilomilo extends AsyncTask<String, String, List<events_list_vendor>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected List<events_list_vendor> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                DataOutputStream printout;
                DataInputStream inputStream;
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput (true);
                connection.setDoOutput (true);
                connection.setUseCaches (false);
                connection.setRequestProperty("Content-Type","application/json");
                connection.setRequestProperty("x-api-type","Android");
                connection.setRequestProperty("x-api-key",s_vendor_token);
                Log.i("vendor_kjn",s_vendor_token);
                connection.setRequestMethod("POST");
                connection.connect();
                JSONObject auth=new JSONObject();
                auth.put("QueryFor","User");
                auth.put("UserId",s_vendor_disc);
                auth.put("PageNumber", Sscrollist);
                auth.put("RowsPerPage", "5");
                Log.i("authme",auth.toString());
                printout = new DataOutputStream(connection.getOutputStream ());
                printout.writeBytes(auth.toString());
                printout.flush ();
                printout.close ();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONObject ed = parentObject.getJSONObject("Response");
                JSONArray parentArray = ed.getJSONArray("Data");

                milokilo = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    events_list_vendor catego = gson.fromJson(finalObject.toString(), events_list_vendor.class);
                    milokilo.add(catego);
                }
                return milokilo;
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(final List<events_list_vendor> movieMode) {
            super.onPostExecute(movieMode);
            if((movieMode != null) && (movieMode.size()>0)){
                GV_user_view.setVisibility(View.VISIBLE);
               adapter = new MovieAdap(getApplicationContext(), R.layout.content_event_list_vendor, movieMode);
                adapter.addAll(movieMode);
                adapter.notifyDataSetChanged();
            }

        }
    }
    public void callmetodeleteiscount(String id)
    {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Id", id);
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.Vendor_delete_events, new Response.Listener<String>() {
                public void onResponse(String response) {
                    new PromptDialog(Events_Display.this)
                            .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                            .setAnimationEnable(true)
                            .setTitleText("Your Event Deleted Successfully")
                            .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                    startActivity(new Intent(Events_Display.this,Events_Display.class));
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
                    headers.put("x-api-key",s_vendor_token);
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
    private boolean isConnectedToNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
    public abstract class EndlessScrollListener implements AbsListView.OnScrollListener {

        // The minimum amount of items to have below your current scroll position
        // before loading more.
        private int visibleThreshold = 1;
        // The current offset index of data you have loaded
        private int currentPage = 0;
        // The total number of items in the dataset after the last load
        private int previousTotalItemCount = 0;
        // True if we are still waiting for the last set of data to load.
        private boolean loading = true;
        // Sets the starting page index
        private int startingPageIndex = 0;

        public EndlessScrollListener() {
        }

        public EndlessScrollListener(int visibleThreshold) {
            this.visibleThreshold = visibleThreshold;
        }

        public EndlessScrollListener(int visibleThreshold, int startPage) {
            this.visibleThreshold = visibleThreshold;
            this.startingPageIndex = startPage;
            this.currentPage = startPage;
        }

        // This happens many times a second during a scroll, so be wary of the code you place here.
        // We are given a few useful parameters to help us work out if we need to load some more data,
        // but first we check if we are waiting for the previous load to finish.
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            // If the total item count is zero and the previous isn't, assume the
            // list is invalidated and should be reset back to initial state
            if (totalItemCount < previousTotalItemCount) {
                this.currentPage = this.startingPageIndex;
                this.previousTotalItemCount = totalItemCount;
                if (totalItemCount == 0) {
                    this.loading = true;
                }
            }

            // If it’s still loading, we check to see if the dataset count has
            // changed, if so we conclude it has finished loading and update the current page
            // number and total item count.
            if (loading && (totalItemCount > previousTotalItemCount)) {
                loading = false;
                previousTotalItemCount = totalItemCount;
                currentPage++;
            }

            // If it isn’t currently loading, we check to see if we have breached
            // the visibleThreshold and need to reload more data.
            // If we do need to reload some more data, we execute onLoadMore to fetch the data.
            if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                onLoadMore(currentPage + 1, totalItemCount);
                loading = true;
            }
        }

        // Defines the process for actually loading more data based on page
        public abstract void onLoadMore(int page, int totalItemsCount);

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            // Don't take any action on changed

        }
    }
}
