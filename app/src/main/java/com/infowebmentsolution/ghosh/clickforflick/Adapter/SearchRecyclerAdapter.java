package com.infowebmentsolution.ghosh.clickforflick.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.infowebmentsolution.ghosh.clickforflick.Activity.MoviePlayActivity;
import com.infowebmentsolution.ghosh.clickforflick.BlankActivity;
import com.infowebmentsolution.ghosh.clickforflick.Model.HomeMovieList;
import com.infowebmentsolution.ghosh.clickforflick.Model.SearchList;
import com.infowebmentsolution.ghosh.clickforflick.Model.SearchModel;
import com.infowebmentsolution.ghosh.clickforflick.R;
import com.infowebmentsolution.ghosh.clickforflick.Utils.Constants;
import com.squareup.picasso.Picasso;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.SearchViewHolder> {
    Context context;
    List<SearchModel> searchModelList;
    public ClickedOnItemListener clickedOnItemListener;
    public SearchRecyclerAdapter(ClickedOnItemListener clickedOnItemListener) {
        this.clickedOnItemListener = clickedOnItemListener;
    }

    public void setData(List<SearchModel> searchModelList) {

        this.searchModelList = searchModelList;
    }
    public interface ClickedOnItemListener {
        void ClickedItem(SearchModel searchModel);
    }
    @NonNull
    @NotNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new SearchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.searched_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SearchViewHolder holder, int position) {

        Picasso.get().load(searchModelList.get(position).getThrumbnail()).into(holder.imageView);
        holder.title.setText(searchModelList.get(position).getTitle());
        holder.episode.setText(searchModelList.get(position).getEpisode());
        holder.language.setText(searchModelList.get(position).getLang());
        SearchModel searchModel = searchModelList.get(position);
        holder.imageView.setOnClickListener(v-> clickedOnItemListener.ClickedItem(searchModel));

    }

    @Override
    public int getItemCount() {
        return searchModelList.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView imageView;
        TextView title,episode,language;

        public SearchViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.searchthumble);
            title = itemView.findViewById(R.id.title);
            episode = itemView.findViewById(R.id.episode);
            language = itemView.findViewById(R.id.language);
        }
    }
}