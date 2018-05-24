
package com.weqar.weqar.JavaClasses;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;


import com.weqar.weqar.ProfileInfo;
import com.weqar.weqar.R;
import com.weqar.weqar.TinyDB;

import java.util.ArrayList;
import java.util.List;

import cn.refactor.lib.colordialog.PromptDialog;

/**
 * Created by andriod on 19/2/18.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyView>{
    Context context;

    private List<String> list_id;
    private List<String> list;
    private List<String> list2;
    private List<String> list3;
    String check_userplan,check_userplan_id;
    SharedPreferences Shared_user_details;
    SharedPreferences.Editor editor;
    public class MyView extends RecyclerView.ViewHolder {

        public TextView textView,textView2,textView3,textView_id;

        public Button But_chooseplan;

        public MyView(View view) {
            super(view);
            textView_id=view.findViewById(R.id.grade_planid);
            textView = view.findViewById(R.id.grade_plantype);
                textView2 = view.findViewById(R.id.grade_amount);
                textView3 = view.findViewById(R.id.grade_desc);
              But_chooseplan = view.findViewById(R.id.choose_subs_but);


        }

    }


    public RecyclerViewAdapter(List<String> horizontalList_id,List<String> horizontalList, List<String> horizontalList2, List<String> horizontalList3, Context context) {
        this.list_id = horizontalList_id;
        this.list = horizontalList;
        this.list2 = horizontalList2;
        this.list3 = horizontalList3;

        this.context=context;

    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_user_subscription_horizontalpage, parent, false);
        return new MyView(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyView holder, final int position) {
        //   Picasso.with(context).load(list.get(position)).fit().into(holder.textView);
         context = holder.But_chooseplan.getContext();
        holder.textView_id.setText(list_id.get(position));
        holder.textView.setText(list.get(position));
        holder.textView2.setText("\u20B9 "+list2.get(position));

        String htmlAsString = list3.get(position);
        Spanned htmlAsSpanned = Html.fromHtml(htmlAsString);
        holder.textView3.setText(htmlAsSpanned);
        holder.But_chooseplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                new PromptDialog(context)
                        .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                        .setAnimationEnable(true)
                        .setTitleText("Confirm Subscription")
                        .setContentText(" For PlanType - "+list.get(position)+" With Cost of "+"\u20B9"+list2.get(position))
                        .setPositiveListener(("ok"), new PromptDialog.OnPositiveListener() {
                            @Override
                            public void onClick(PromptDialog dialog) {
                                dialog.dismiss();
                                check_userplan_id=  holder.textView_id.getText().toString();
                                check_userplan= holder.textView.getText().toString();
                                TinyDB tinydb = new TinyDB(context);
                                tinydb.putString("check_userplantype_id", check_userplan_id);
                                tinydb.putString("check_userplantype_type",check_userplan);
                                Shared_user_details =context. getSharedPreferences("user_detail_mode", 0);
                                editor = Shared_user_details.edit();

                                editor = Shared_user_details.edit();
                                editor.putString("sel_user_plantype",check_userplan_id);


                                editor.apply();
                                editor.commit();
                            }
                        }).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}



