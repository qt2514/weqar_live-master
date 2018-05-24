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
import android.widget.LinearLayout;
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
import com.pepperonas.materialdialog.MaterialDialog;
import com.squareup.picasso.Picasso;
import com.weqar.weqar.DBJavaClasses.jobscard_list;
import com.weqar.weqar.Global_url_weqar.Global_URL;
import com.weqar.weqar.JobDetails_User;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cn.refactor.lib.colordialog.PromptDialog;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.thefinestartist.utils.service.ServiceUtil.getSystemService;


public class BotNav_JobsFragment extends Fragment {
    ListView GV_jobs_user;
    ImageView IVhomescreen_filter;
    String s_user_jobfield_name,s_user_jobfield_id;
    RecyclerView RV_home_hoizontal_scroll;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    RecyclerViewAdapter_JobField RecyclerViewHorizontalAdapter;
    List<String> L_user_jobfield_name;
    List<String> L_user_jobfield_id;
    LinearLayoutManager HorizontalLayout ;
     List<String> subjectnameid;
    ArrayList<String> jobtypefilter;
    ArrayList<String> jobtypenewfilt;
    ArrayList<Integer> jobtypeint;
    SharedPreferences Shared_user_details;
    String s_vendor_token,s_vendor_disc;
    String[] mStringArray ;
    ProgressDialog dialog;
    String Sscrollist="1";
    MovieAdap adapter;


    ImageView IV_nojobs;
    public static BotNav_JobsFragment newInstance() {
        return new BotNav_JobsFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_bot_nav__jobs, container, false);
        Shared_user_details = getActivity().getSharedPreferences("user_detail_mode", 0);
        s_vendor_disc = Shared_user_details.getString("weqar_uid", null);
        s_vendor_token = Shared_user_details.getString("weqar_token", null);
            GV_jobs_user=view.findViewById(R.id.jobs_vendor_gv);
        RV_home_hoizontal_scroll=view.findViewById(R.id.RV_jobs_user);
        IV_nojobs=view.findViewById(R.id.IV_noitem_jobs);
        IVhomescreen_filter=view.findViewById(R.id.homescreen_filter);
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        L_user_jobfield_name= new ArrayList<String>();
        L_user_jobfield_id= new ArrayList<String>();
        RV_home_hoizontal_scroll.setLayoutManager(RecyclerViewLayoutManager);
        RecyclerViewHorizontalAdapter = new RecyclerViewAdapter_JobField(L_user_jobfield_id,L_user_jobfield_name,getActivity());
        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RV_home_hoizontal_scroll.setLayoutManager(HorizontalLayout);
        RV_home_hoizontal_scroll.setHorizontalScrollBarEnabled(false);
        dialog = new ProgressDialog(getContext());
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading...Please wait...");
        subjectnameid=new ArrayList<>();
        jobtypefilter=new ArrayList<>();
        jobtypenewfilt=new ArrayList<>();
        jobtypeint=new ArrayList<Integer>();

        GV_jobs_user.setOnScrollListener(new EndlessScrollListener() {

            @Override
            public void onLoadMore(int page, int totalItemsCount) {

                Sscrollist=String.valueOf(page);
                String URLLL = Global_URL.Vendor_showownjobs;
                new newkilomilo().execute(URLLL);
            }
        });

        String URLLL = Global_URL.Vendor_showownjobs ;
        new kilomilo().execute(URLLL);
        mStringArray=new String[jobtypenewfilt.size()];
        mStringArray = jobtypenewfilt.toArray(mStringArray);
        getUserJobfields();
        subject_name_list();

        IVhomescreen_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getActivity())
                        .title("Job Type")
                        .listItems(true,mStringArray)
                        .itemClickListener(new MaterialDialog.ItemClickListener() {
                            @Override
                            public void onClick(View v, int position, long id) {
                                super.onClick(v, position, id);
                                Log.i("meomeopunae", mStringArray[position]);
                                if (position==0)
                                {
                                    jobtypefilter.clear();
                                    String URLLL = Global_URL.Vendor_showownjobs;
                                    new kilomilo().execute(URLLL);
                                }
                                else
                                {
                                    jobtypefilter.add(String.valueOf(position));
                                    String URLLL = Global_URL.Vendor_showownjobs;
                                    new kilomilo().execute(URLLL);

                                }

                            }
                        })
                        .show();
            }
        });
        jobtypefilter.clear();

        return view;
    }



    public class MovieAdap extends ArrayAdapter {
        private List<jobscard_list> movieModelList;
        private int resource;
        Context context;
        private LayoutInflater inflater;
        MovieAdap(Context context, int resource, List<jobscard_list> objects) {
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
                holder.text_jobtype =  convertView.findViewById(R.id.fag_jobs_type_user);
                holder.textjobfield = convertView.findViewById(R.id.fag_jobs_field_user);
                holder.textdesc = convertView.findViewById(R.id.fag_jobs_desc_user);
                holder.textdeadline = convertView.findViewById(R.id.fag_jobs_deadline_user);
                holder.IV_logo=convertView.findViewById(R.id.IV_logo_jobuser);
                convertView.setTag(holder);
            }
            else
            {
                holder = (MovieAdap.ViewHolder) convertView.getTag();
            }
            jobscard_list ccitacc = movieModelList.get(position);
            holder.text_jobtype.setText(ccitacc.getName());
            holder.textjobfield.setText(ccitacc.getJobType());
            holder.textdesc.setText(ccitacc.getDescription());
            String first=ccitacc.getClosingDate();
            holder.textdeadline.setText("Deadline "+first);
            try {
                Picasso.with(context).load(Global_URL.Image_url_load+ccitacc.getLogo()).error(getResources().getDrawable(R.drawable.rounded)).fit().centerCrop().into(holder.IV_logo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertView;
        }
        class ViewHolder {
            public TextView text_jobtype,textjobfield,textdesc,textdeadline,textdeadlines;
            public CircleImageView IV_logo;
        }
    }


    @SuppressLint("StaticFieldLeak")
    public class kilomilo extends AsyncTask<String, String, List<jobscard_list>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }
        @Override
        protected List<jobscard_list> doInBackground(String... params) {
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
                connection.setRequestProperty("x-api-key",s_vendor_token);
                Log.i("ssventoken",s_vendor_token);
                connection.setRequestMethod("POST");
                connection.connect();
                JSONObject auth=new JSONObject();
                auth.put("QueryFor","user");
                auth.put("UserId",s_vendor_disc);
                auth.put("PageNumber", Sscrollist);
                auth.put("RowsPerPage", "5");
                if (!subjectnameid.isEmpty())
                {
                    JSONArray array = new JSONArray();

                    for (int p = 0; p < subjectnameid.size(); p++) {

                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("Id", subjectnameid.get(p));
                        array.put(jsonObject);


                    }
                    auth.put("JobFields",array);
                }
                Log.i("jobtypefilter",jobtypefilter.toString());
                if (!jobtypefilter.isEmpty())
                {
                    JSONArray jobtype = new JSONArray();

                    for (int p = 0; p < jobtypefilter.size(); p++) {

                        JSONObject jsonObjectype = new JSONObject();
                        jsonObjectype.put("Id", jobtypefilter.get(p));
                        jobtype.put(jsonObjectype);


                    }
                    auth.put("JobTypes",jobtype);
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
                List<jobscard_list> milokilo = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    jobscard_list catego = gson.fromJson(finalObject.toString(), jobscard_list.class);
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
        protected void onPostExecute(final List<jobscard_list> movieMode) {
            super.onPostExecute(movieMode);
             dialog.hide();
            if((movieMode != null) && (movieMode.size()>0) &&getActivity()!=null ){
                 adapter = new MovieAdap(getActivity(), R.layout.content_jobs_user, movieMode);
                GV_jobs_user.setAdapter(adapter);
                GV_jobs_user.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        jobscard_list item = movieMode.get(position);
                        Intent intent = new Intent(getActivity(),JobDetails_User.class);
                        intent.putExtra("put_jobs_user_logo",item.getLogo());
                        intent.putExtra("put_jobs_user_jobtype",item.getJobField());
                        intent.putExtra("put_jobs_user_jobname",item.getName());
                        intent.putExtra("put_jobs_user_jobfield",item.getJobType());
                        intent.putExtra("put_jobs_user_deadline",item.getClosingDate());
                        intent.putExtra("put_jobs_user_desc",item.getDescription());
                        intent.putExtra("put_jobs_user_id",item.getId());
                        intent.putExtra("put_jobs_user_applied",item.getApplied());
                        startActivity(intent);
                    }
                });
                adapter.notifyDataSetChanged();


            }
            else
            {
                GV_jobs_user.setVisibility(View.INVISIBLE);
                IV_nojobs.setVisibility(View.VISIBLE);
            }
        }
    }
    public class newkilomilo extends AsyncTask<String, String, List<jobscard_list>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }
        @Override
        protected List<jobscard_list> doInBackground(String... params) {
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
                connection.setRequestProperty("x-api-key",s_vendor_token);
                Log.i("ssventoken",s_vendor_token);
                connection.setRequestMethod("POST");
                connection.connect();
                JSONObject auth=new JSONObject();
                auth.put("QueryFor","user");
                auth.put("UserId",s_vendor_disc);
                auth.put("PageNumber", Sscrollist);
                auth.put("RowsPerPage", "5");
                if (!subjectnameid.isEmpty())
                {
                    JSONArray array = new JSONArray();

                    for (int p = 0; p < subjectnameid.size(); p++) {

                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("Id", subjectnameid.get(p));
                        array.put(jsonObject);


                    }
                    auth.put("JobFields",array);
                }
                Log.i("jobtypefilter",jobtypefilter.toString());
                if (!jobtypefilter.isEmpty())
                {
                    JSONArray jobtype = new JSONArray();

                    for (int p = 0; p < jobtypefilter.size(); p++) {

                        JSONObject jsonObjectype = new JSONObject();
                        jsonObjectype.put("Id", jobtypefilter.get(p));
                        jobtype.put(jsonObjectype);


                    }
                    auth.put("JobTypes",jobtype);
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
                List<jobscard_list> milokilo = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    jobscard_list catego = gson.fromJson(finalObject.toString(), jobscard_list.class);
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
        protected void onPostExecute(final List<jobscard_list> movieMode) {
            super.onPostExecute(movieMode);
            dialog.hide();
            if((movieMode != null) && (movieMode.size()>0) &&getActivity()!=null ){
                adapter.addAll(movieMode);
                adapter.notifyDataSetChanged();


            }
            else
            {
                GV_jobs_user.setVisibility(View.INVISIBLE);
                IV_nojobs.setVisibility(View.VISIBLE);
            }
        }
    }
    public void getUserJobfields()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Global_URL.Vendor_getjobfield, new Response.Listener<String>() {
            public void onResponse(String response) {
                try {

                    JSONObject jObj = new JSONObject(response);
                    JSONArray jsonArray = jObj.getJSONArray("Response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        s_user_jobfield_name= object.getString("Description");
                        s_user_jobfield_id= object.getString("Id");
                        L_user_jobfield_id.add(String.valueOf(s_user_jobfield_id));
                        L_user_jobfield_name.add(String.valueOf(s_user_jobfield_name));
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
    public class RecyclerViewAdapter_JobField extends RecyclerView.Adapter<RecyclerViewAdapter_JobField.MyView>{
        Context context;
        private List<String> u_list_id;
        private List<String> u_list_name;

        public class MyView extends RecyclerView.ViewHolder {

            Button TV_descdet_title;
            TextView TV_descdet_id;

            public MyView(View view) {
                super(view);

                TV_descdet_title = view.findViewById(R.id.but_vendor_discount_category);
                TV_descdet_id=view.findViewById(R.id.myjobvendor_id);


            }

        }


        public RecyclerViewAdapter_JobField(List<String> horizontalList_id,
                                            List<String> horizontalList_desc,


                                            Context context) {
            this.u_list_id = horizontalList_id;
            this.u_list_name = horizontalList_desc;
            this.context=context;

        }

        @Override
        public RecyclerViewAdapter_JobField.MyView onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_discount_hscroll, parent, false);
            return new RecyclerViewAdapter_JobField.MyView(itemView);
        }


        @Override
        public void onBindViewHolder( final RecyclerViewAdapter_JobField.MyView holder, final int position) {

            context = holder.TV_descdet_title.getContext();
            holder.TV_descdet_title.setText(u_list_name.get(position));
            holder.TV_descdet_id.setText(u_list_id.get(position));
            holder.TV_descdet_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.TV_descdet_title.setBackground(null);
                    String checkme=u_list_id.get(position);
                    Log.i("kanamuchiraerae",checkme);
                    if (subjectnameid.contains(checkme))
                    {

                        holder.TV_descdet_title.setBackground(getResources().getDrawable(R.drawable.but_selected));
                        subjectnameid.remove(holder.TV_descdet_id.getText().toString());
                    }
                    else
                    {


                        holder.TV_descdet_title.setBackground(getResources().getDrawable(R.drawable.but_new_selected));

                        subjectnameid.add(holder.TV_descdet_id.getText().toString());

                    }
                    String URLLL = Global_URL.Vendor_showownjobs;
                    new kilomilo().execute(URLLL);
                }
            });



        }

        @Override
        public int getItemCount() {
            return u_list_id.size();
        }

    }
    public void subject_name_list() {
        RequestQueue requestQueueq = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Global_URL.Vendor_getjobtype, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray jsonArray = jObj.getJSONArray("Response");
                    jobtypenewfilt.add("Clear all filters");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        String name = jsonobject.getString("Description");
                        int id = jsonobject.getInt("Id");
                       // listViewItems.add(new MultispinnerList(name,id));
                        jobtypenewfilt.add(name);
                        jobtypeint.add(id);

                    }
                    mStringArray=new String[jobtypenewfilt.size()];
                    mStringArray = jobtypenewfilt.toArray(mStringArray);
                //    mIntArray = jobtypeint.toArray(new Integer[jobtypeint.size()]);

                } catch (JSONException e) {
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
        requestQueueq.add(stringRequest);
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
