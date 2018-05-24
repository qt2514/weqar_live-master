package com.weqar.weqar.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.weqar.weqar.LoginActivity;
import com.weqar.weqar.R;
import com.weqar.weqar.Settings_HelpActivity;


public class BotNav_SettingsFragment_Guest extends Fragment {
ImageView WIV_set_signin,WIV_set_help,IV_setv_share;
TextView WTV_set_signin,WTV_set_help,TV_setv_share;
ProgressDialog dialog;
    public static BotNav_SettingsFragment_Guest newInstance() {
        BotNav_SettingsFragment_Guest fragment= new BotNav_SettingsFragment_Guest();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View v=inflater.inflate(R.layout.fragment_bot_nav__settings_fragment__guest, container, false);
        WIV_set_signin=v.findViewById(R.id.WIV_set_profile);
        WTV_set_signin=v.findViewById(R.id.WTV_set_profile);
        IV_setv_share = v.findViewById(R.id.share_IV);
        TV_setv_share = v.findViewById(R.id.share_TV);
        WTV_set_help=v.findViewById(R.id.WTV_set_help);
        dialog = new ProgressDialog(getContext());
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait...");
        WIV_set_help=v.findViewById(R.id.WIV_set_help);
        WIV_set_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        WTV_set_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        WTV_set_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Settings_HelpActivity.class));
            }
        });
        WIV_set_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Settings_HelpActivity.class));
            }
        });
        IV_setv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "I am happy with this app.Please click the link to download \n https://play.google.com/store/search?q=weqar&hl=en";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Weqar");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, " This is about service"));
            }
        });
        TV_setv_share.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "I am happy with this app.Please click the link to download \n https://play.google.com/store/search?q=weqar&hl=en";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Weqar");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, " This is about service"));
            }
        });

      return v;
    }


}
