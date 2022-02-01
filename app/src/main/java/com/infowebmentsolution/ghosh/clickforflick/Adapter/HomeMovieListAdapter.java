package com.infowebmentsolution.ghosh.clickforflick.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.infowebmentsolution.ghosh.clickforflick.Model.HomeMovieList;
import com.infowebmentsolution.ghosh.clickforflick.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeMovieListAdapter extends RecyclerView.Adapter<HomeMovieListAdapter.HomeMovieListHolder> {

    private ArrayList<HomeMovieList> homeMovieLists;
    Context context;
    @NonNull
    @NotNull
    @Override
    public HomeMovieListHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new HomeMovieListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.child_recycleview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HomeMovieListHolder holder, int position) {
        Glide.with(this.context).load(this.homeMovieLists.get(position).getPoster()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return homeMovieLists.size();
    }

    public class HomeMovieListHolder extends RecyclerView.ViewHolder{

        ImageView img;
        public HomeMovieListHolder(@NonNull @NotNull View itemView) {
            super(itemView);
           img =itemView.findViewById(R.id.child_rec_item_image);
        }
    }
}
