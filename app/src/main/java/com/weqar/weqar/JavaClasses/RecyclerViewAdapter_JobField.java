package com.weqar.weqar.JavaClasses;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.weqar.weqar.Global_url_weqar.Global_URL;
import com.weqar.weqar.R;
import com.weqar.weqar.TinyDB;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by andriod on 22/3/18.
 */

public class RecyclerViewAdapter_JobField extends RecyclerView.Adapter<RecyclerViewAdapter_JobField.MyView>{
    Context context;
    private List<String> subjectnameid;

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
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_discount_hscroll, parent, false);
        subjectnameid=new ArrayList<>();
        return new MyView(itemView);
    }


    @Override
    public void onBindViewHolder(final MyView holder, final int position) {
        //   Picasso.with(context).load(list.get(position)).fit().into(holder.textView);
        context = holder.TV_descdet_title.getContext();
        holder.TV_descdet_title.setText(u_list_name.get(position));
holder.TV_descdet_id.setText(u_list_id.get(position));
holder.TV_descdet_title.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        subjectnameid.add(holder.TV_descdet_id.getText().toString());

        Toast.makeText(context, holder.TV_descdet_id.getText(), Toast.LENGTH_SHORT).show();
    }
});

        TinyDB tinydb = new TinyDB(context);
        tinydb.putListString("jobsubjectid", (ArrayList<String>) subjectnameid);


    }

    @Override
    public int getItemCount() {
        return u_list_id.size();
    }

}



