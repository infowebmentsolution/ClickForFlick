package com.infowebmentsolution.ghosh.clickforflick.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.infowebmentsolution.ghosh.clickforflick.Model.BannerList;
import com.infowebmentsolution.ghosh.clickforflick.R;
import com.infowebmentsolution.ghosh.clickforflick.Utils.Constants;
import com.smarteist.autoimageslider.SliderViewAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BannerAdapter extends SliderViewAdapter<BannerAdapter.BannerHolder> {

    ArrayList<BannerList> bannerLists;
    Context context;

    public BannerAdapter(ArrayList<BannerList> bannerLists, Context context) {
        this.bannerLists = bannerLists;
        this.context = context;
    }

    @Override
    public BannerHolder onCreateViewHolder(ViewGroup parent) {
        return new BannerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_module,parent,false));
    }

    @Override
    public void onBindViewHolder(BannerHolder viewHolder, int position) {
        Glide.with(this.context).load(Constants.POSTERURL+bannerLists.get(position).getPoster()).into(viewHolder.bannerImg);
        viewHolder.title.setText(bannerLists.get(position).getTitle());
    }

    @Override
    public int getCount() {
        return bannerLists.size();
    }

    static class BannerHolder extends SliderViewAdapter.ViewHolder{

        ImageView bannerImg;
        TextView title;
        AppCompatButton button;
        public BannerHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            bannerImg=itemView.findViewById(R.id.banner_module_img);
            title=itemView.findViewById(R.id.banner_module_title);
            button=itemView.findViewById(R.id.banner_play_now);

        }
    }
}
