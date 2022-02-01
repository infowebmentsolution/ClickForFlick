package com.infowebmentsolution.ghosh.clickforflick.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.infowebmentsolution.ghosh.clickforflick.Activity.SplashScreenActivity;
import com.infowebmentsolution.ghosh.clickforflick.Model.SearchList;
import com.infowebmentsolution.ghosh.clickforflick.R;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {



    private ArrayList<SearchList> searchLists;
    Context context;
    private ClickListener clickListener;

    public interface  ClickListener{
        void ClickItem(SearchList list);
    }

    public SearchAdapter(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setData(ArrayList<SearchList> searchLists) {

        this.searchLists = searchLists;
    }
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.searched_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return searchLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ShapeableImageView search_imgae;
        TextView title;
        TextView episode;
        TextView language;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
//            search_imgae = itemView.findViewById(R.id.search_imageview);
//            title = itemView.findViewById(R.id.search_title);
//            episode=itemView.findViewById(R.id.search_episode);
//            language=itemView.findViewById(R.id.search_language);
        }
    }


}
