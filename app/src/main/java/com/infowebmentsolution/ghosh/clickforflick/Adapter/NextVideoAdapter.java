package com.infowebmentsolution.ghosh.clickforflick.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.infowebmentsolution.ghosh.clickforflick.Model.NextVideoList;
import com.infowebmentsolution.ghosh.clickforflick.R;
import com.infowebmentsolution.ghosh.clickforflick.Utils.Constants;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;


public class NextVideoAdapter extends RecyclerView.Adapter<NextVideoAdapter.NextMovieHolder> {

    private ArrayList<NextVideoList> NextVideoLists;
    Context context;
    public ClickedOnItemListener clickedOnItemListener;

    public NextVideoAdapter(ClickedOnItemListener clickedOnItemListener) {
        this.clickedOnItemListener = clickedOnItemListener;
    }

    public void setData(ArrayList<NextVideoList> NextVideoLists) {
        this.NextVideoLists = NextVideoLists;
    }
    public interface ClickedOnItemListener {
        void ClickedItem(NextVideoList NextVideoList);
    }

    @NonNull
    @Override
    public NextMovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NextMovieHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.next_card_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NextMovieHolder holder, int position) {
        Picasso.get().load(Constants.THRUMBNAIL_URL+NextVideoLists.get(position).getThrumbnail()).into(holder.catImage);
        NextVideoList NextVideoList1 = NextVideoLists.get(position);
        if(NextVideoList1.getIs_free().equals("1")) holder.freeImg.setVisibility(View.GONE);
        holder.catImage.setOnClickListener(v -> clickedOnItemListener.ClickedItem(NextVideoList1));
    }


    @Override
    public int getItemCount() {
        return NextVideoLists.size();
    }

    public static class NextMovieHolder extends RecyclerView.ViewHolder {
        TextView category;
        ImageView catImage;
        ImageView freeImg;
        public NextMovieHolder(@NonNull View itemView) {
            super(itemView);
            catImage = itemView.findViewById(R.id.next_child_item_image);
            freeImg=itemView.findViewById(R.id.free1);
        }
    }
}