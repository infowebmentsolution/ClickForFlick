package com.infowebmentsolution.ghosh.clickforflick;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infowebmentsolution.ghosh.clickforflick.Utils.Constants;


public class LanguageFragment extends Fragment {


    View itemView ;
    TextView all,eng,ben,hin,kann;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        itemView = inflater.inflate(R.layout.fragment_language, container, false);
        all = itemView.findViewById(R.id.lang_all);
        eng = itemView.findViewById(R.id.lang_eng);
        ben = itemView.findViewById(R.id.lang_ben);
        hin = itemView.findViewById(R.id.lang_hin);
        kann = itemView.findViewById(R.id.lang_kan);

        all.setOnClickListener(view -> {
            SharedPreferences preferences = getActivity().getSharedPreferences(Constants.LOG_IN_DATA, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Constants.LANGUAGE,"all");
            editor.apply();
            editor.commit();

        });
        ben.setOnClickListener(view -> {
            SharedPreferences preferences = getActivity().getSharedPreferences(Constants.LOG_IN_DATA, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Constants.LANGUAGE,"ben");
            editor.apply();
            editor.commit();

        });
        hin.setOnClickListener(view -> {
            SharedPreferences preferences = getActivity().getSharedPreferences(Constants.LOG_IN_DATA, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Constants.LANGUAGE,"hin");
            editor.apply();
            editor.commit();

        });
        eng.setOnClickListener(view -> {
            SharedPreferences preferences = getActivity().getSharedPreferences(Constants.LOG_IN_DATA, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Constants.LANGUAGE,"en");
            editor.apply();
            editor.commit();

        });
        kann.setOnClickListener(view -> {
            SharedPreferences preferences = getActivity().getSharedPreferences(Constants.LOG_IN_DATA, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Constants.LANGUAGE,"kan");
            editor.apply();
            editor.commit();

        });

        return itemView;
    }
}