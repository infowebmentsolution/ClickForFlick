package com.infowebmentsolution.ghosh.clickforflick.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.infowebmentsolution.ghosh.clickforflick.Model.HomeMovieList;
import com.infowebmentsolution.ghosh.clickforflick.R;
import com.infowebmentsolution.ghosh.clickforflick.Utils.Constants;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class CategoryWiseAdapter extends RecyclerView.Adapter<CategoryWiseAdapter.CategoryViewHolder> {

    private ArrayList<HomeMovieList> homeMovieLists;
    Context context;
    public ClickedOnItemListener clickedOnItemListener;

    public CategoryWiseAdapter(ClickedOnItemListener clickedOnItemListener) {
        this.clickedOnItemListener = clickedOnItemListener;
    }

    public void setData(ArrayList<HomeMovieList> homeMovieLists) {
        this.homeMovieLists = homeMovieLists;

    }
    public interface ClickedOnItemListener {
        void ClickedItem(HomeMovieList homeMovieList);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.child_rec, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        Picasso.get().load(Constants.THRUMBNAIL_URL+homeMovieLists.get(position).getThrumbnail()).into(holder.catImage);
        HomeMovieList homeMovieList1 = homeMovieLists.get(position);
        if(homeMovieList1.getIs_free().equals("1")) holder.freeImg.setVisibility(View.GONE);
        holder.catImage.setOnClickListener(v -> clickedOnItemListener.ClickedItem(homeMovieList1));
    }

    @Override
    public int getItemCount() {
        return homeMovieLists.size();
    }

    public  class CategoryViewHolder extends RecyclerView.ViewHolder {
         TextView category;
         ImageView catImage;
         ImageView freeImg;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            catImage = itemView.findViewById(R.id.child_item_image);
            freeImg=itemView.findViewById(R.id.free);
        }
    }
}