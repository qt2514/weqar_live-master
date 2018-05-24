package com.weqar.weqar.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.weqar.weqar.DBJavaClasses.dashboard_list;
import com.weqar.weqar.DashboardDetails_Vendor;
import com.weqar.weqar.Events_Display_Vendor;
import com.weqar.weqar.Global_url_weqar.Global_URL;
import com.weqar.weqar.News_Display_Vendor;
import com.weqar.weqar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class BotNav_EventsFragment_Vendor extends Fragment {
ImageView IV_adddiscount;
ListView LV_vendor_events;
    private FloatingActionMenu dash_menu;
    ImageView IV_event;
    SharedPreferences Shared_user_details;
    String s_vendor_token,s_vendor_disc;
    FloatingActionButton But_dash_u_events,But_dash_u_news;
    String Sscrollist="1";
    MovieAdap adapter;
    com.github.clans.fab.FloatingActionButton fab1;
     com.github.clans.fab.FloatingActionButton fab2;
    private com.github.clans.fab.FloatingActionButton fab3;
    private com.github.clans.fab.FloatingActionButton fab4;

    public static BotNav_EventsFragment_Vendor newInstance() {
        BotNav_EventsFragment_Vendor fragment= new BotNav_EventsFragment_Vendor();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bot_nav__events_fragment__vendor, container, false);


          //  IV_adddiscount = view.findViewById(R.id.homescreen_addevent);
            LV_vendor_events = view.findViewById(R.id.events_vendor_listview);
        dash_menu = view.findViewById(R.id.menu_red);
        IV_event = view.findViewById(R.id.IV_noitem_disc);

        But_dash_u_events=view.findViewById(R.id.dashboard_button_v_events);
        But_dash_u_news=view.findViewById(R.id.dashboard_button_v_news);
        But_dash_u_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Events_Display_Vendor.class));
            }
        });
        But_dash_u_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),News_Display_Vendor.class));
            }
        });

        Shared_user_details = getActivity().getSharedPreferences("user_detail_mode", 0);
        s_vendor_disc = Shared_user_details.getString("weqar_uid", null);
        s_vendor_token = Shared_user_details.getString("weqar_token", null);
        dash_menu.setClosedOnTouchOutside(true);

        fab1 = view.findViewById(R.id.fab1);
        fab2 = view.findViewById(R.id.fab2);
        fab3 = view.findViewById(R.id.fab3);
        fab4 = view.findViewById(R.id.fab4);
        fab1.setOnClickListener(clickListener);
        fab2.setOnClickListener(clickListener);
        fab3.setOnClickListener(clickListener);
        fab4.setOnClickListener(clickListener);
        LV_vendor_events.setOnScrollListener(new EndlessScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }


            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }

            @Override
            public void onLoadMore(int page, int totalItemsCount) {

                Sscrollist=String.valueOf(page);
                String URLLL = Global_URL.user_show_dashboard;
                new newkilomilo().execute(URLLL);

            }
        });






        String URLLL = Global_URL.user_show_dashboard;
        new kilomilo().execute(URLLL);
//
//        IV_adddiscount.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivity(new Intent(getActivity(), AddEvents_Vendor.class));
//                }
//            });


        return view;
    }
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LV_vendor_events.setClickable(false);
            String URLLL = Global_URL.user_show_dashboard;

            switch (v.getId()) {
                case R.id.fab1:

                    new kilomilo().execute(URLLL+"/news");
                    dash_menu.close(true);
                    break;
                case R.id.fab2:

                    new kilomilo().execute(URLLL+"/event");
                    dash_menu.close(true);

                    break;
                case R.id.fab3:

                    new kilomilo().execute(URLLL+"/discount");
                    dash_menu.close(true);

                    break;
                    case R.id.fab4:

                    new kilomilo().execute(URLLL);
                    dash_menu.close(true);

                    break;

            }
        }
    };
    public class MovieAdap extends ArrayAdapter {
        private List<dashboard_list> movieModelList;
        private int resource;
        Context context;
        private LayoutInflater inflater;
        MovieAdap(Context context, int resource, List<dashboard_list> objects) {
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
                holder.image_event_user = convertView.findViewById(R.id.image_event_user);
                holder.image_two_event_user = convertView.findViewById(R.id.image_two_event_user);
                holder.logo_event_user = convertView.findViewById(R.id.logo_event_user);
                holder.event_discount_layout = convertView.findViewById(R.id.event_discount_layout);
                holder.event_event_layout = convertView.findViewById(R.id.event_event_layout);
                holder.TV_event_texttitle = convertView.findViewById(R.id.TV_event_texttitle);
                holder.TV_disc_textstartdate = convertView.findViewById(R.id.TV_disc_textstartdate);
                holder.TV_disc_textenddate = convertView.findViewById(R.id.TV_disc_textenddate);
                holder.TV_event_etitle = convertView.findViewById(R.id.TV_event_etitle);
                holder.TV_event_edesc = convertView.findViewById(R.id.TV_event_edesc);
                holder.TV_event_end = convertView.findViewById(R.id.TV_event_end);
                holder.TV_event_start = convertView.findViewById(R.id.TV_event_start);



                convertView.setTag(holder);
            }
            else
            {
                holder = (MovieAdap.ViewHolder) convertView.getTag();
            }
            dashboard_list ccitacc = movieModelList.get(position);
            String getdashboardtype=ccitacc.getType();
            switch (getdashboardtype) {
                case "Discount":
                    holder.event_discount_layout.setVisibility(View.VISIBLE);
                    holder.event_event_layout.setVisibility(View.INVISIBLE);
                    holder.image_two_event_user.setVisibility(View.VISIBLE);
                    holder.image_event_user.setVisibility(View.VISIBLE);
                    holder.logo_event_user.setVisibility(View.VISIBLE);
                    holder.TV_event_texttitle.setVisibility(View.VISIBLE);
                    holder.TV_disc_textenddate.setVisibility(View.VISIBLE);
                    holder.TV_disc_textstartdate.setVisibility(View.VISIBLE);
                    holder.TV_event_etitle.setTextColor(getResources().getColor(R.color.colorWhite));
                    holder.TV_event_edesc.setTextColor(getResources().getColor(R.color.colorWhite));
                    holder.TV_event_texttitle.setText(ccitacc.getTitle());
                  //  String dash_disc_start= DateTimeUtils.formatWithPattern(ccitacc.getStartDate(), "MM/dd/yyyy");
                    //String dash_disc_end=DateTimeUtils.formatWithPattern(ccitacc.getEndDate(), "MM/dd/yyyy");
                    holder.TV_disc_textstartdate.setText("Start Date : "+ccitacc.getStartDate());
                    holder.TV_disc_textenddate.setText("End Date : "+ccitacc.getEndDate());
                    try {
                        Picasso.with(context).load(Global_URL.Image_url_load + ccitacc.getLogo()).error(getResources().getDrawable(R.drawable.rounded)).fit().centerCrop().into(holder.logo_event_user);
                        Picasso.with(context).load(Global_URL.Image_url_load + ccitacc.getImage()).error(getResources().getDrawable(R.drawable.rounded_two)).fit().centerCrop().into(holder.image_event_user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "News":
                    holder.TV_event_texttitle.setVisibility(View.INVISIBLE);
                    holder.TV_disc_textstartdate.setVisibility(View.INVISIBLE);
                    holder.TV_disc_textenddate.setVisibility(View.INVISIBLE);
                    holder.image_two_event_user.setVisibility(View.INVISIBLE);
                    holder.event_event_layout.setVisibility(View.VISIBLE);
                    holder.logo_event_user.setVisibility(View.INVISIBLE);
                    holder.image_event_user.setVisibility(View.VISIBLE);
                    holder.TV_event_start.setVisibility(View.GONE);
                    holder.TV_event_end.setVisibility(View.GONE);
                    holder.image_two_event_user.setVisibility(View.INVISIBLE);
                    holder.TV_event_etitle.setTextColor(getResources().getColor(R.color.colorWhite));
                    holder.TV_event_edesc.setTextColor(getResources().getColor(R.color.colorWhite));

                    holder.event_event_layout.setBackgroundColor(getResources().getColor(R.color.colorBlacks));
                    holder.TV_event_etitle.setText(ccitacc.getTitle());
                    String hh=ccitacc.getDescription();
                    Spanned htmlAsSpanned = Html.fromHtml(hh);
                    holder.TV_event_edesc.setText(htmlAsSpanned);
                    try {
                        Picasso.with(context).load(Global_URL.Image_url_load + ccitacc.getImage()).error(getResources().getDrawable(R.drawable.rounded_two)).fit().centerCrop().into(holder.image_event_user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "Event":
                    holder.TV_event_texttitle.setVisibility(View.INVISIBLE);
                    holder.TV_disc_textstartdate.setVisibility(View.INVISIBLE);
                    holder.TV_disc_textenddate.setVisibility(View.INVISIBLE);
                    holder.image_two_event_user.setVisibility(View.INVISIBLE);
                    holder.event_event_layout.setVisibility(View.VISIBLE);
                    holder.logo_event_user.setVisibility(View.INVISIBLE);
                    holder.image_event_user.setVisibility(View.VISIBLE);
                    holder.image_two_event_user.setVisibility(View.INVISIBLE);

                    holder.TV_event_start.setVisibility(View.VISIBLE);
                    holder.TV_event_end.setVisibility(View.VISIBLE);
                    holder.event_event_layout.setBackgroundColor(getResources().getColor(R.color.colorBlacks));
                    holder.TV_event_etitle.setTextColor(getResources().getColor(R.color.colorWhite));
                    holder.TV_event_edesc.setTextColor(getResources().getColor(R.color.colorWhite));
                    holder.TV_event_etitle.setText(ccitacc.getTitle());

//                    String dash_event_start=DateTimeUtils.formatWithPattern(ccitacc.getStartDate(), "MM/dd/yyyy");
//                    String dash_eventisc_end=DateTimeUtils.formatWithPattern(ccitacc.getEndDate(), "MM/dd/yyyy");
//                    String time_sched=ccitacc.getStartDate().substring(11,16);
//                    String time_sched_end=ccitacc.getEndDate().substring(11,16);
//                    holder.TV_event_start.setText("Start Date : "+dash_event_start+" "+time_sched);
//                    holder.TV_event_end.setText("End Date : "+dash_eventisc_end+" "+time_sched_end);
                    holder.TV_event_start.setText("Start Date : "+ccitacc.getStartDate());
                    holder.TV_event_end.setText("End Date : "+ccitacc.getEndDate());
                    String hhs=ccitacc.getDescription();
                    Spanned htmlAsSpanneds = Html.fromHtml(hhs);
                    holder.TV_event_edesc.setText(htmlAsSpanneds);
                    try {
                        Picasso.with(context).load(Global_URL.Image_url_load + ccitacc.getImage()).error(getResources().getDrawable(R.drawable.rounded_two)).fit().centerCrop().into(holder.image_event_user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:

                    break;
            }
            return convertView;
        }
        class ViewHolder {
            public ImageView image_event_user,image_two_event_user;
            CircleImageView logo_event_user;
            LinearLayout event_discount_layout,event_event_layout;
            TextView TV_event_texttitle,TV_disc_textstartdate,TV_disc_textenddate,TV_event_etitle,TV_event_edesc,
                    TV_event_start,TV_event_end;

        }
    }

    @SuppressLint("StaticFieldLeak")
    public class kilomilo extends AsyncTask<String, String, List<dashboard_list>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected List<dashboard_list> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                DataOutputStream printout;
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Content-Type","application/json");
                connection.setRequestProperty("x-api-type","Android");
                connection.setRequestProperty("x-api-key",s_vendor_token);
                connection.setRequestMethod("GET");
                connection.connect();



                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("Response");
                List<dashboard_list> milokilo = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    dashboard_list catego = gson.fromJson(finalObject.toString(), dashboard_list.class);
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
        protected void onPostExecute(final List<dashboard_list> movieMode) {
            super.onPostExecute(movieMode);
            if((movieMode != null) && (movieMode.size()>0) &&getActivity()!=null ){
                adapter = new MovieAdap(getActivity(), R.layout.conent_dashboard_user, movieMode);
                LV_vendor_events.setAdapter(adapter);

                IV_event.setVisibility(View.INVISIBLE);
                LV_vendor_events.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        dashboard_list item = movieMode.get(position);
                        Intent intent = new Intent(getActivity(),DashboardDetails_Vendor.class);
                        intent.putExtra("put_dashboard_id",item.getId());
                        intent.putExtra("put_dashboard_type",item.getType());
                        intent.putExtra("put_dashboard_logo",item.getLogo());

                        startActivity(intent);
                    }
                });
                adapter.notifyDataSetChanged();
            }
            else
            {
                LV_vendor_events.setVisibility(View.INVISIBLE);
                IV_event.setVisibility(View.VISIBLE);
            }
        }



    }
    public class newkilomilo extends AsyncTask<String, String, List<dashboard_list>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected List<dashboard_list> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                DataOutputStream printout;
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Content-Type","application/json");
                connection.setRequestProperty("x-api-type","Android");
                connection.setRequestProperty("x-api-key",s_vendor_token);
                connection.setRequestMethod("GET");
                connection.connect();



                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("Response");
                List<dashboard_list> milokilo = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    dashboard_list catego = gson.fromJson(finalObject.toString(), dashboard_list.class);
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
        protected void onPostExecute(final List<dashboard_list> movieMode) {
            super.onPostExecute(movieMode);
            if((movieMode != null) && (movieMode.size()>0) &&getActivity()!=null ){ adapter = new MovieAdap(getActivity(), R.layout.conent_dashboard_user, movieMode);

                adapter.notifyDataSetChanged();
            }
            else
            {
                LV_vendor_events.setVisibility(View.INVISIBLE);
                IV_event.setVisibility(View.VISIBLE);
            }
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
