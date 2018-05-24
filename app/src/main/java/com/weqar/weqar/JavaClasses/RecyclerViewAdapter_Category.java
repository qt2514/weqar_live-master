package com.weqar.weqar.JavaClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.weqar.weqar.R;
import com.weqar.weqar.TinyDB;

import java.util.List;

import cn.refactor.lib.colordialog.PromptDialog;

/**
 * Created by andriod on 15/3/18.
 */

public class RecyclerViewAdapter_Category extends RecyclerView.Adapter<RecyclerViewAdapter_Category.MyView>{
    Context context;

    private List<String> list_id;
    private List<String> list;

    public class MyView extends RecyclerView.ViewHolder {


        public Button But_mycategory;

        public MyView(View view) {
            super(view);

            But_mycategory = view.findViewById(R.id.but_vendor_discount_category);


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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final RecyclerViewAdapter_Category.MyView holder, final int position) {
        //   Picasso.with(context).load(list.get(position)).fit().into(holder.textView);
        context = holder.But_mycategory.getContext();
        holder.But_mycategory.setText(list.get(position));





    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}



