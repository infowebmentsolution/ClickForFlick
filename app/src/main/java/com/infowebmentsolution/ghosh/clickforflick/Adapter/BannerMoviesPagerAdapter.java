package com.infowebmentsolution.ghosh.clickforflick.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.infowebmentsolution.ghosh.clickforflick.Activity.MoviePlayActivity;
import com.infowebmentsolution.ghosh.clickforflick.R;
import com.infowebmentsolution.ghosh.clickforflick.Model.HomeMovieList;
import com.infowebmentsolution.ghosh.clickforflick.Utils.Constants;
import java.util.List;

public class BannerMoviesPagerAdapter extends PagerAdapter {
    List<HomeMovieList> bannerMoviesList;
    Context context;

    public BannerMoviesPagerAdapter(Context context2, List<HomeMovieList> bannerMoviesList2) {
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
        View view = LayoutInflater.from(this.context).inflate(R.layout.banner_movies, (ViewGroup) null);
        ImageView bannerimage = (ImageView) view.findViewById(R.id.imageView2);
        RequestManager with = Glide.with(this.context);
        with.load(Constants.POSTERURL + this.bannerMoviesList.get(position).getPoster()).into(bannerimage);
        container.addView(view);
        bannerimage.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                Intent intent = new Intent(BannerMoviesPagerAdapter.this.context, MoviePlayActivity.class);
                intent.putExtra("tUrl", BannerMoviesPagerAdapter.this.bannerMoviesList.get(position).getTrailername());
                intent.putExtra("episodename", BannerMoviesPagerAdapter.this.bannerMoviesList.get(position).getEpisode());
                intent.putExtra(ImagesContract.URL, BannerMoviesPagerAdapter.this.bannerMoviesList.get(position).getVideoname());
                intent.putExtra("vId", BannerMoviesPagerAdapter.this.bannerMoviesList.get(position).getUploadid());
                intent.putExtra("vname", BannerMoviesPagerAdapter.this.bannerMoviesList.get(position).getTitle());
                intent.putExtra("vdec", BannerMoviesPagerAdapter.this.bannerMoviesList.get(position).getDescription());
                intent.putExtra("tUrl", BannerMoviesPagerAdapter.this.bannerMoviesList.get(position).getTrailername());
                intent.putExtra("isFree", BannerMoviesPagerAdapter.this.bannerMoviesList.get(position).getIs_free());
                intent.putExtra("cname", BannerMoviesPagerAdapter.this.bannerMoviesList.get(position).getCname());
                intent.putExtra("W.T.", "no");
                BannerMoviesPagerAdapter.this.context.startActivity(intent);
            }
        });
        ((FloatingActionButton) view.findViewById(R.id.floatingActionButton)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(BannerMoviesPagerAdapter.this.context, MoviePlayActivity.class);
                intent.putExtra("tUrl", BannerMoviesPagerAdapter.this.bannerMoviesList.get(position).getTrailername());
                intent.putExtra("episodename", BannerMoviesPagerAdapter.this.bannerMoviesList.get(position).getEpisode());
                intent.putExtra(ImagesContract.URL, BannerMoviesPagerAdapter.this.bannerMoviesList.get(position).getVideoname());
                intent.putExtra("vId", BannerMoviesPagerAdapter.this.bannerMoviesList.get(position).getUploadid());
                intent.putExtra("vname", BannerMoviesPagerAdapter.this.bannerMoviesList.get(position).getTitle());
                intent.putExtra("vdec", BannerMoviesPagerAdapter.this.bannerMoviesList.get(position).getDescription());
                intent.putExtra("tUrl", BannerMoviesPagerAdapter.this.bannerMoviesList.get(position).getTrailername());
                intent.putExtra("isFree", BannerMoviesPagerAdapter.this.bannerMoviesList.get(position).getIs_free());
                intent.putExtra("cname", BannerMoviesPagerAdapter.this.bannerMoviesList.get(position).getCname());
                intent.putExtra("W.T.", "no");
                BannerMoviesPagerAdapter.this.context.startActivity(intent);
            }
        });
        return view;
    }
}
