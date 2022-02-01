package com.infowebmentsolution.ghosh.clickforflick.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.infowebmentsolution.ghosh.clickforflick.Model.ChildModel;
import com.infowebmentsolution.ghosh.clickforflick.R;

import java.util.ArrayList;

public class ChildRecyclerViewAdapter extends RecyclerView.Adapter<ChildRecyclerViewAdapter.MyViewHolder> {
    public ArrayList<ChildModel> childModelArrayList;
    Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView heroImage;
        public TextView movieName;

        public MyViewHolder(View itemView) {
            super(itemView);
            heroImage = itemView.findViewById(R.id.child_rec_item_image);
          //  movieName = itemView.findViewById(R.id.movie_name);
        }
    }

    public ChildRecyclerViewAdapter(ArrayList<ChildModel> arrayList, Context mContext) {
        this.context = mContext;
        this.childModelArrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_recycleview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ChildModel currentItem = childModelArrayList.get(position);
        Glide.with(this.context).load(this.childModelArrayList.get(position).getVideoImage()).into(holder.heroImage);
    //    holder.movieName.setText(currentItem.getMovieName());

    }

    @Override
    public int getItemCount() {
        return childModelArrayList.size();
    }
}