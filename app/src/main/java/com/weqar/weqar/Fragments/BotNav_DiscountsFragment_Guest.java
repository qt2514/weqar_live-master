package com.weqar.weqar.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.weqar.weqar.DBJavaClasses.discountcard_list;
import com.weqar.weqar.DiscountDetails_Guest;
import com.weqar.weqar.Global_url_weqar.Global_URL;
import com.weqar.weqar.JavaClasses.RecyclerViewAdapter_Category;
import com.weqar.weqar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
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
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.thefinestartist.utils.service.ServiceUtil.getSystemService;


public class BotNav_DiscountsFragment_Guest extends Fragment {
    String s_vendor_getho_name,s_vendor_getho_id;
    ListView GV_disc_user;
    Context c;
    RecyclerView RV_home_hoizontal_scroll;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    RecyclerViewAdapter_Category RecyclerViewHorizontalAdapter;
    LinearLayoutManager HorizontalLayout ;
    List<String> L_vendor_hor_id;
    List<String> L_vendor_hor_name;
    ImageView IV_nodisc;
    String s_user_token,s_user_id;
    List<String> subjectnameid;

    String Sscrollist="1";
    MovieAdap adapter;
    SharedPreferences Shared_user_details;
    ProgressDialog dialog;


    public static BotNav_DiscountsFragment_Guest newInstance()
    {
        BotNav_DiscountsFragment_Guest fragment= new BotNav_DiscountsFragment_Guest();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bot_nav__discounts_fragment__guest, container, false);
        c = getActivity().getApplicationContext();
        GV_disc_user = view.findViewById(R.id.disc_vendor_gv);
        dialog = new ProgressDialog(getContext());
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait...");
        String URLLL = Global_URL.user_show_discount;
        new kilomilo().execute(URLLL);
        RV_home_hoizontal_scroll = view.findViewById(R.id.home_hoizontal_scroll);
        L_vendor_hor_id = new ArrayList<String>();
        L_vendor_hor_name = new ArrayList<String>();
        IV_nodisc = view.findViewById(R.id.IV_noitem_disc);
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        RV_home_hoizontal_scroll.setLayoutManager(RecyclerViewLayoutManager);
        RecyclerViewHorizontalAdapter = new RecyclerViewAdapter_Category(L_vendor_hor_id, L_vendor_hor_name, getActivity());
        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RV_home_hoizontal_scroll.setLayoutManager(HorizontalLayout);
        RV_home_hoizontal_scroll.setHorizontalScrollBarEnabled(false);

        Shared_user_details=getActivity().getSharedPreferences("user_detail_mode",0);
        subjectnameid=new ArrayList<>();

        s_user_token = Shared_user_details.getString("sp_w_apikey", null);
        s_user_id = Shared_user_details.getString("sp_w_userid", null);
        GV_disc_user.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {

                Sscrollist=String.valueOf(page);
                String URLLL = Global_URL.user_show_discount;
                new lopkilomilo().execute(URLLL);

            }
        });
        getUserCompletesubscription();


        return view;

    }
    public class MovieAdap extends ArrayAdapter {
        private List<discountcard_list> movieModelList;
        private int resource;
        Context context;
        private LayoutInflater inflater;
        MovieAdap(Context context, int resource, List<discountcard_list> objects) {
            super(context, resource, objects);
            movieModelList = objects;
            this.context = context;
            this.resource = resource;
            inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            final MovieAdap.ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(resource, null);
                holder = new MovieAdap.ViewHolder();
                holder.textone = (TextView) convertView.findViewById(R.id.TV_disc_percentage);
                holder.TV_enddate = (TextView) convertView.findViewById(R.id.text_enddate);
                holder.TV_startdate = (TextView) convertView.findViewById(R.id.text_startdate);
                holder.menuimage = convertView.findViewById(R.id.roundimg_one);
                holder.RIV_logo = convertView.findViewById(R.id.roundedImageView);
                holder.Bfollowdis=convertView.findViewById(R.id.button);

                //3holder.ratingbar=convertView.findViewById(R.id.RB_vendr_rating);
                convertView.setTag(holder);
            }//ino
            else {
                holder = (MovieAdap.ViewHolder) convertView.getTag();
            }
          final   discountcard_list ccitacc = movieModelList.get(position);
            String getdiscount_type= ccitacc.getDiscountType();

                holder.textone.setText(ccitacc.getTitle());


//            String gg=ccitacc.getPercentage();
//            Integer k=Integer.parseInt(gg);
//            Integer kk=k/20;
//            Float g=(float) kk;
//            holder.ratingbar.setRating(g);
            holder.TV_enddate.setText("Start Date: "+ccitacc.getStartDate());
            holder.TV_startdate.setText("End Date: "+ccitacc.getEndDate());
            String ing=ccitacc.getImage().trim();
            String ings=ccitacc.getLogo().trim();
            if (ccitacc.getFollowed())
            {
                holder.Bfollowdis.setText("Unfollow");
            }
            else
            {
                holder.Bfollowdis.setText("Follow");

            }
            holder.Bfollowdis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.Bfollowdis.getText().equals("Follow"))
                    {
                        callmetofollowdis(s_user_id,ccitacc.getId(),"follow");
                        String URLLL = Global_URL.user_show_discount;
                        new kilomilo().execute(URLLL);
                    }
                    else
                    {
                        callmetofollowdis(s_user_id,ccitacc.getId(),"unfollow");
                        String URLLL = Global_URL.user_show_discount;
                        new kilomilo().execute(URLLL);


                    }
                }
            });
            try
            {
                Picasso.with(context).load(Global_URL.Image_url_load+ings).error(getResources().getDrawable(R.drawable.rounded)).fit().centerCrop().into(holder.RIV_logo);
                Picasso.with(context).load(Global_URL.Image_url_load+ing).error(getResources().getDrawable(R.drawable.rounded_two)).fit().centerCrop().into(holder.menuimage);
            }catch (Exception e){}
            return convertView;
        }
        class ViewHolder {
            public TextView textone,TV_enddate,TV_startdate;
            private ImageView menuimage;
            private CircleImageView RIV_logo;
            Button Bfollowdis;

            // RatingBar ratingbar;
        }
    }
    @SuppressLint("StaticFieldLeak")
    public class kilomilo extends AsyncTask<String, String, List<discountcard_list>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }
        @Override
        protected List<discountcard_list> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                DataOutputStream printout;

                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput (true);
                connection.setDoOutput (true);
                connection.setUseCaches (false);
                connection.setRequestProperty("Content-Type","application/json");
                connection.setRequestProperty("x-api-type","Android");
                connection.setRequestMethod("POST");
                connection.connect();
                JSONObject auth=new JSONObject();
                auth.put("QueryFor","user");
                auth.put("UserId",s_user_id);
                auth.put("PageNumber", "1");
                auth.put("RowsPerPage", "5");
                if (!subjectnameid.isEmpty())
                {
                    JSONArray array = new JSONArray();

                    for (int p = 0; p < subjectnameid.size(); p++) {

                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("Id", subjectnameid.get(p));
                        Log.i("poonae",subjectnameid.get(p));
                        array.put(jsonObject);


                    }
                    auth.put("CategoryTypes",array);
                }
                printout = new DataOutputStream(connection.getOutputStream ());
                printout.writeBytes(auth.toString());
                Log.i("myjobres",auth.toString());
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
                List<discountcard_list> milokilo = new ArrayList<>();

                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    discountcard_list catego = gson.fromJson(finalObject.toString(), discountcard_list.class);
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
        protected void onPostExecute(final List<discountcard_list> movieMode) {
            super.onPostExecute(movieMode);
            dialog.dismiss();
            if((movieMode != null) && (movieMode.size()>0)&&getActivity()!=null  ){

                 adapter = new MovieAdap(getActivity(), R.layout.fragment_discount_card, movieMode);
                GV_disc_user.setAdapter(adapter);
                GV_disc_user.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        discountcard_list item = movieMode.get(position);
                        Intent intent = new Intent(getActivity(),DiscountDetails_Guest.class);
                        intent.putExtra("put_disc_id",item.getId());

                        startActivity(intent);
                    }
                });


                adapter.notifyDataSetChanged();
            }
            else
            {
                GV_disc_user.setVisibility(View.INVISIBLE);
                IV_nodisc.setVisibility(View.VISIBLE);
            }



        }
    }

    @SuppressLint("StaticFieldLeak")
    public class lopkilomilo extends AsyncTask<String, String, List<discountcard_list>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }
        @Override
        protected List<discountcard_list> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                DataOutputStream printout;

                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput (true);
                connection.setDoOutput (true);
                connection.setUseCaches (false);
                connection.setRequestProperty("Content-Type","application/json");
                connection.setRequestProperty("x-api-type","Android");
                connection.setRequestMethod("POST");
                connection.connect();
                JSONObject auth=new JSONObject();
                auth.put("QueryFor","user");
                auth.put("UserId",s_user_id);
                auth.put("PageNumber", "1");
                auth.put("RowsPerPage", "5");
                if (!subjectnameid.isEmpty())
                {
                    JSONArray array = new JSONArray();

                    for (int p = 0; p < subjectnameid.size(); p++) {

                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("Id", subjectnameid.get(p));
                        Log.i("poonae",subjectnameid.get(p));
                        array.put(jsonObject);


                    }
                    auth.put("CategoryTypes",array);
                }
                printout = new DataOutputStream(connection.getOutputStream ());
                printout.writeBytes(auth.toString());
                Log.i("myjobres",auth.toString());
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

                List<discountcard_list> milokilo = new ArrayList<>();

                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    discountcard_list catego = gson.fromJson(finalObject.toString(), discountcard_list.class);
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
        protected void onPostExecute(final List<discountcard_list> movieMode) {
            super.onPostExecute(movieMode);
dialog.dismiss();
            if((movieMode != null) && (movieMode.size()>0)&&getActivity()!=null  ){

     adapter.addAll(movieMode);

                adapter.notifyDataSetChanged();
            }
            else
            {
                GV_disc_user.setVisibility(View.INVISIBLE);
                IV_nodisc.setVisibility(View.VISIBLE);
            }



        }
    }

    public void getUserCompletesubscription()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Global_URL.Vendor_select_categ, new Response.Listener<String>() {

            public void onResponse(String response) {
                try {

                    JSONObject jObj = new JSONObject(response);
                    JSONArray jsonArray = jObj.getJSONArray("Response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        s_vendor_getho_name= object.getString("Name");
                        s_vendor_getho_id= object.getString("Id");

                        L_vendor_hor_id.add(String.valueOf(s_vendor_getho_id));
                        L_vendor_hor_name.add(String.valueOf(s_vendor_getho_name));


                    }
                    RV_home_hoizontal_scroll.setAdapter(RecyclerViewHorizontalAdapter);
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
    private boolean isConnectedToNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
    public class RecyclerViewAdapter_Category extends RecyclerView.Adapter<RecyclerViewAdapter_Category.MyView>{
        Context context;

        private List<String> list_id;
        private List<String> list;

        public class MyView extends RecyclerView.ViewHolder {
            Button But_mycategory;
            TextView TV_descdet_id;

            public MyView(View view) {
                super(view);

                But_mycategory = view.findViewById(R.id.but_vendor_discount_category);
                TV_descdet_id=view.findViewById(R.id.myjobvendor_id);


            }

        }


        public RecyclerViewAdapter_Category(List<String> horizontalList_id,List<String> horizontalList,Context context) {
            this.list_id = horizontalList_id;
            this.list = horizontalList;


            this.context=context;

        }

        @Override
        public MyView onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_discount_hscroll, parent, false);
            return new MyView(itemView);
        }

        @Override
        public void onBindViewHolder(final MyView holder, final int position) {
            //   Picasso.with(context).load(list.get(position)).fit().into(holder.textView);
            context = holder.But_mycategory.getContext();
            holder.But_mycategory.setText(list.get(position));
            holder.TV_descdet_id.setText(list_id.get(position));
            holder.But_mycategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.But_mycategory.setBackground(null);
                    String checkme=list_id.get(position);
                    Log.i("kanamuchiraerae",checkme);
                    if (subjectnameid.contains(checkme))
                    {

                        holder.But_mycategory.setBackground(getResources().getDrawable(R.drawable.but_selected));
                        subjectnameid.remove(holder.TV_descdet_id.getText().toString());
                    }
                    else
                    {


                        holder.But_mycategory.setBackground(getResources().getDrawable(R.drawable.but_new_selected));

                        subjectnameid.add(holder.TV_descdet_id.getText().toString());

                    }

                    String URLLL = Global_URL.user_show_discount;
                    new kilomilo().execute(URLLL);
                }
            });





        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }

    public void callmetofollowdis(String userid, String discid, final String follow_status)
    {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("DiscountId", discid);
            jsonBody.put("UserId", userid);

            final String requestBody = jsonBody.toString();
            Log.i("muomuao",requestBody);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.user_discount_follow+follow_status, new Response.Listener<String>() {
                public void onResponse(String response) {
                    Log.i("muomuo",response);
                    new PromptDialog(getActivity())
                            .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                            .setAnimationEnable(true)
                            .setContentText("Your request to "+follow_status+" is Successfull")
                            .setPositiveListener(("Ok"), new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
//                                    AppCompatActivity activity = (AppCompatActivity) getActivity();
//                                    Fragment myFragment = new BotNav_DiscountsFragment_Vendor();
//                                    activity.getSupportFragmentManager().beginTransaction()
//                                            .replace(R.id.contentContainer, myFragment).addToBackStack(null).commit();
                                    dialog.dismiss();
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
