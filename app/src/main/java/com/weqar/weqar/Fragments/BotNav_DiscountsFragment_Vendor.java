package com.weqar.weqar.Fragments;
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
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.weqar.weqar.AddDiscount_Vendor;
import com.weqar.weqar.DBJavaClasses.discountcard_list_vendor;
import com.weqar.weqar.DiscountDetails_Vendor;
import com.weqar.weqar.Discount_Edit_Vendor;
import com.weqar.weqar.Global_url_weqar.Global_URL;
import com.weqar.weqar.R;

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
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.thefinestartist.utils.service.ServiceUtil.getSystemService;

public class BotNav_DiscountsFragment_Vendor  extends Fragment
{
    String s_vendor_disc,s_vendor_token;
    SwipeMenuListView GV_vendor_view;
    ImageView IV_adddiscount_vendor;
    SharedPreferences Shared_user_details;
    ImageView IV_nodiscount_items;
    SharedPreferences.Editor editor;
    List<discountcard_list_vendor> milokilo;
    String Sscrollist="1";
    ProgressDialog dialog;
    MovieAdap adapter;


    public static BotNav_DiscountsFragment_Vendor newInstance()
    {
        BotNav_DiscountsFragment_Vendor fragment= new BotNav_DiscountsFragment_Vendor();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view= inflater.inflate(R.layout.fragment_bot_nav__discounts_fragment__vendor, container, false);

            Shared_user_details = getActivity().getSharedPreferences("user_detail_mode", 0);
            s_vendor_disc = Shared_user_details.getString("weqar_uid", null);
            s_vendor_token = Shared_user_details.getString("weqar_token", null);
            GV_vendor_view = view.findViewById(R.id.disc_vendor_gv);
        dialog = new ProgressDialog(getContext());
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait...");
            IV_adddiscount_vendor = view.findViewById(R.id.homescreen_adddiscount);
            IV_nodiscount_items = view.findViewById(R.id.IV_noitem_disc);
            IV_adddiscount_vendor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), AddDiscount_Vendor.class));
                }
            });

        GV_vendor_view.setOnScrollListener(new EndlessScrollListener() {

            @Override
            public void onLoadMore(int page, int totalItemsCount) {

                Sscrollist=String.valueOf(page);
                String URLLL = Global_URL.Vendor_showown_discounts;
                new newkilomilo().execute(URLLL);

            }
        });

        new kilomilo().execute(Global_URL.Vendor_showown_discounts);

        return view;
    }
    public class MovieAdap extends ArrayAdapter
    {
        private List<discountcard_list_vendor> movieModelList;
        private int resource;
        Context context;
        private LayoutInflater inflater;
        MovieAdap(Context context, int resource, List<discountcard_list_vendor> objects)
        {
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
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            final ViewHolder holder;
            if (convertView == null)
            {
                convertView = inflater.inflate(resource, null);
                holder = new ViewHolder();
                holder.textone = (TextView) convertView.findViewById(R.id.TV_disc_percentage_vendor);
                holder.texttwo_desc = (TextView) convertView.findViewById(R.id.text_vendor_discount_desc);
                holder.text_vendor_discount_startdate = (TextView) convertView.findViewById(R.id.text_vendor_discount_startdate);
                holder.menuimage = convertView.findViewById(R.id.roundimg_one);
              //  holder.RB_vendor_rating = (RatingBar)convertView.findViewById(R.id.RB_vendr_rating_vendor);
                holder.RIV_logo=convertView.findViewById(R.id.RIV_vendor_logo_vendor);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            final discountcard_list_vendor ccitacc = movieModelList.get(position);
            String getdiscount_type= ccitacc.getDiscountType();

                holder.textone.setText(ccitacc.getTitle());


//            String gg=ccitacc.getPercentage();
//            Integer k=Integer.parseInt(gg);
//            Integer kk=k/20;
//            Float g=(float) kk;
//            holder.RB_vendor_rating.setRating(g);
            holder.texttwo_desc.setText("End Date: "+ccitacc.getEndDate());
            holder.text_vendor_discount_startdate.setText("Start Date: "+ccitacc.getStartDate());
            try
            {
                Picasso.with(context).load(Global_URL.Image_url_load+ccitacc.getImage()).error(getResources().getDrawable(R.drawable.rounded_two)).fit().centerCrop().into(holder.menuimage);
                Picasso.with(context).load(Global_URL.Image_url_load+ccitacc.getLogo()).error(getResources().getDrawable(R.drawable.rounded)).fit().centerCrop().into(holder.RIV_logo);
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
            GV_vendor_view.setMenuCreator(creator);
            GV_vendor_view.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener()
            {
                @Override
                public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                    final discountcard_list_vendor schedule_history_list = milokilo.get(position);

                    switch (index) {
                        case 0:
                            Intent intent=new Intent(getActivity(),Discount_Edit_Vendor.class);
                            intent.putExtra("put_discountid_fordisc_edit",schedule_history_list.getId());
                            intent.putExtra("put_discounttype_fordisc_edit",schedule_history_list.getDiscountType());
                            intent.putExtra("put_discounttitle_fordisc_edit",schedule_history_list.getTitle());
                            intent.putExtra("put_discountdesc_fordisc_edit",schedule_history_list.getDescription());
                            intent.putExtra("put_discountimage_fordisc_edit",schedule_history_list.getImage());
                            intent.putExtra("put_discountper_fordisc_edit",schedule_history_list.getPercentage());
                            intent.putExtra("put_discountsdate_fordisc_edit",schedule_history_list.getStartDate());
                            intent.putExtra("put_discountedate_fordisc_edit",schedule_history_list.getEndDate());
                            startActivity(intent);
                            break;
                        case 1:
                            String ed=schedule_history_list.getId();
                           // Toast.makeText(getActivity(), schedule_history_list.getTitle(), Toast.LENGTH_SHORT).show();
                           callmetodeleteiscount(ed);
                            break;
                    }
                    return false;
                }
            });
            return convertView;
        }
        class ViewHolder {
            public TextView textone,texttwo_desc,text_vendor_discount_startdate;
            private ImageView menuimage;
           CircleImageView RIV_logo;
          //  RatingBar RB_vendor_rating;
        }
    }
    @SuppressLint("StaticFieldLeak")
    public class kilomilo extends AsyncTask<String, String, List<discountcard_list_vendor>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected List<discountcard_list_vendor> doInBackground(String... params) {
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
                connection.setRequestMethod("POST");
                connection.connect();
                JSONObject auth=new JSONObject();
                auth.put("QueryFor","vendor");
                auth.put("UserId",s_vendor_disc);
                auth.put("PageNumber", Sscrollist);
                auth.put("RowsPerPage", "5");
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
                    discountcard_list_vendor catego = gson.fromJson(finalObject.toString(), discountcard_list_vendor.class);
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
        protected void onPostExecute(final List<discountcard_list_vendor> movieMode) {
            super.onPostExecute(movieMode);
            if((movieMode != null) && (movieMode.size()>0)&&getActivity()!=null ){
              GV_vendor_view.setVisibility(View.VISIBLE);
          adapter = new MovieAdap(getActivity(), R.layout.fragment_discountcard_vendor, movieMode);
                GV_vendor_view.setAdapter(adapter);
                GV_vendor_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        discountcard_list_vendor item = movieMode.get(position);
                        Intent intent = new Intent(getActivity(),DiscountDetails_Vendor.class);
                        intent.putExtra("put_image",item.getImage());
                        intent.putExtra("put_logo",item.getLogo());
                        intent.putExtra("put_title",item.getTitle());
                        intent.putExtra("put_type",item.getDiscountType());
                        intent.putExtra("put_per",item.getPercentage());
                        intent.putExtra("put_desc",item.getDescription());
                        intent.putExtra("put_enddate",item.getEndDate());
                        intent.putExtra("put_startdate",item.getStartDate());
                        startActivity(intent);
                    }
                });
                adapter.notifyDataSetChanged();
            }
            else {
                GV_vendor_view.setVisibility(View.INVISIBLE);
                IV_nodiscount_items.setVisibility(View.VISIBLE);
            }
        }
    }
    @SuppressLint("StaticFieldLeak")
    public class newkilomilo extends AsyncTask<String, String, List<discountcard_list_vendor>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected List<discountcard_list_vendor> doInBackground(String... params) {
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
                connection.setRequestMethod("POST");
                connection.connect();
                JSONObject auth=new JSONObject();
                auth.put("QueryFor","vendor");
                auth.put("UserId",s_vendor_disc);
                auth.put("PageNumber", Sscrollist);
                auth.put("RowsPerPage", "5");
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
                    discountcard_list_vendor catego = gson.fromJson(finalObject.toString(), discountcard_list_vendor.class);
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
        protected void onPostExecute(final List<discountcard_list_vendor> movieMode) {
            super.onPostExecute(movieMode);
         //   if((movieMode != null) && (movieMode.size()>0)&&getActivity()!=null ){
         adapter.addAll(movieMode);
                adapter.notifyDataSetChanged();
//            }
//            else {
//                GV_vendor_view.setVisibility(View.INVISIBLE);
//                IV_nodiscount_items.setVisibility(View.VISIBLE);
//            }
        }
    }
    public void callmetodeleteiscount(String id)
    {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Id", id);
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_URL.Vendor_delete_discounts, new Response.Listener<String>() {
                public void onResponse(String response) {
                    new PromptDialog(getActivity())
                            .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                            .setAnimationEnable(true)
                            .setTitleText("Your Discount Deleted Successfully")
                            .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                    AppCompatActivity activity = (AppCompatActivity) getActivity();
                                    Fragment myFragment = new BotNav_DiscountsFragment_Vendor();
                                    activity.getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.contentContainer, myFragment).addToBackStack(null).commit();
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
