package com.infowebmentsolution.ghosh.clickforflick.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.infowebmentsolution.ghosh.clickforflick.Activity.SplashScreenActivity;
import com.infowebmentsolution.ghosh.clickforflick.R;
import com.infowebmentsolution.ghosh.clickforflick.Model.BannerList;
import java.util.ArrayList;

public class BannerAdapter2 extends PagerAdapter {
    ArrayList<BannerList> bannerMoviesList;
    Context context;

    public BannerAdapter2(Context context2, ArrayList<BannerList> bannerMoviesList2) {
        this.context = context2;
        this.bannerMoviesList = bannerMoviesList2;
    }

    public int getCount() {
        return this.bannerMoviesList.size();
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.banner_module, (ViewGroup) null);
        ShapeableImageView bannerimage = (ShapeableImageView) view.findViewById(R.id.banner_module_img);
        Glide.with(this.context).load(this.bannerMoviesList.get(position).getCat_url()).into((ImageView) bannerimage);
        container.addView(view);
        bannerimage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                BannerAdapter2.this.context.startActivity(new Intent(BannerAdapter2.this.context, SplashScreenActivity.class));
            }
        });
        ((FloatingActionButton) view.findViewById(R.id.banner_play_now)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(BannerAdapter2.this.context, SplashScreenActivity.class);
                intent.putExtra("video_episode", BannerAdapter2.this.bannerMoviesList.get(position).getEpisode());
                intent.putExtra("video_lang", BannerAdapter2.this.bannerMoviesList.get(position).getLang());
                BannerAdapter2.this.context.startActivity(intent);
            }
        });
        return view;
    }
}