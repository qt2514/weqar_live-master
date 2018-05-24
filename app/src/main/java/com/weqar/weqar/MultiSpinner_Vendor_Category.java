package com.weqar.weqar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import com.weqar.weqar.Global_url_weqar.Global_URL;
import com.weqar.weqar.JavaClasses.MultispinnerList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MultiSpinner_Vendor_Category extends AppCompatActivity {
    List<MultispinnerList> listViewItems;

    ListView listViewWithCheckBox;
    EditText serach_text;
    TextView textViewadd;
    Button Bdone;
    CheckBox cb;
    List<String> subjectnamelist;
    List<String> subjectnameid;
    Context context;
    ImageView IV_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_spinner__vendor__category);
        subject_name_list();
        listViewWithCheckBox = findViewById(R.id.listView);
        serach_text=findViewById(R.id.serach_subject);
        textViewadd=findViewById(R.id.add_subject_list);
        Bdone=findViewById(R.id.addsubject_button);
        context=this;
        subjectnamelist=new ArrayList<>();
        subjectnameid=new ArrayList<>();
        listViewItems = new ArrayList<MultispinnerList>();
        IV_back=findViewById(R.id.back_ima_scedule);

        IV_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listViewWithCheckBox.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cb =  view.findViewById(R.id.checkBox);
                cb.setChecked(!cb.isChecked());
            }
        });
        Bdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TinyDB tinydb = new TinyDB(context);

                tinydb.putListString("subjectnamelist", (ArrayList<String>) subjectnamelist);
                tinydb.putListString("subjectnameid", (ArrayList<String>) subjectnameid);
                tinydb.putString("subjecttype", "Subject");
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        serach_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listViewItems.clear();
                subject_name_list();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    public class CustomListView extends ArrayAdapter {
        private List<MultispinnerList> ScheduleModeList;
        private int resource;
        Context context;
        private LayoutInflater inflater;
        CustomListView(Context context, int resource, List<MultispinnerList> objects) {
            super(context, resource, objects);
            ScheduleModeList = objects;
            this.context = context;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getViewTypeCount() {
            return 1;
        }
        @Override
        public int getItemViewType(int position) {
            return position;
        }
        @NonNull
        @Override
        public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(resource, null);
                holder = new ViewHolder();
                holder.subjectname = convertView.findViewById(R.id.subjectname);
                holder.subjectid = convertView.findViewById(R.id.subjectid);
                holder.checkBox = convertView.findViewById(R.id.checkBox);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            final MultispinnerList supl = ScheduleModeList.get(position);
            holder.subjectname.setText(supl.getMulsubject());
            holder.subjectid.setText(supl.getMulsubjectid());
//            TinyDB tinydb = new TinyDB(context);
//            subjectnamelist = tinydb.getListString("subjectnamelistnewofindia");
//            for (int o=0;o<subjectnamelist.size();o++)
//            {
//                if (supl.getMulsubject().equals(subjectnamelist.get(o)))
//                {
//                    holder.checkBox.setEnabled(true);
//                }
//            }

            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        subjectnamelist.add(supl.getMulsubject());
                        subjectnameid.add(supl.getMulsubjectid());
                    }else{
                        subjectnamelist.remove(supl.getMulsubject());
                        subjectnameid.remove(supl.getMulsubjectid());
                    }
                }
            });
            return convertView;
        }
        class ViewHolder {
            TextView subjectname,subjectid;
            CheckBox checkBox;
        }
    }
    public void subject_name_list() {
        RequestQueue requestQueueq = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Global_URL.Vendor_select_categ, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray jsonArray = jObj.getJSONArray("Response");
                        Bdone.setVisibility(View.VISIBLE);
                        textViewadd.setVisibility(View.INVISIBLE);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonobject = jsonArray.getJSONObject(i);
                            String name = jsonobject.getString("Name");
                            String id = jsonobject.getString("Id");
                            listViewItems.add(new MultispinnerList(name,id));
                    }
                    CustomListView adapter = new CustomListView(getBaseContext(), R.layout.time_slot_list_multi, listViewItems);
                    listViewWithCheckBox.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
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
}
