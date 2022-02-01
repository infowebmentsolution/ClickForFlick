package com.infowebmentsolution.ghosh.clickforflick.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.infowebmentsolution.ghosh.clickforflick.Model.AllCategoryList;
import com.infowebmentsolution.ghosh.clickforflick.Model.ChildModel;
import com.infowebmentsolution.ghosh.clickforflick.Model.HomeMovieList;
import com.infowebmentsolution.ghosh.clickforflick.Model.ParentModel;
import com.infowebmentsolution.ghosh.clickforflick.R;
import com.infowebmentsolution.ghosh.clickforflick.Response.AllCategoryResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.HomeMovieListsResponse;
import com.infowebmentsolution.ghosh.clickforflick.Utils.Constants;
import com.infowebmentsolution.ghosh.clickforflick.Utils.RestAPI;
import com.infowebmentsolution.ghosh.clickforflick.Utils.RetrofitClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ParentRecyclerViewAdapter extends RecyclerView.Adapter<ParentRecyclerViewAdapter.ParentViewHolder> {
    public ArrayList<ParentModel> parentModelArrayList;
    public ArrayList<AllCategoryList> allCategoryListArrayList;
    public Context context;
    public ArrayList<HomeMovieList> homeMovieLists;
    public static Retrofit retrofit = null;

    public ParentRecyclerViewAdapter(ArrayList<ParentModel> parentModelArrayList, Context context,ArrayList<AllCategoryList>allCategoryListArrayList,ArrayList<HomeMovieList> homeMovieLists) {
        this.parentModelArrayList = parentModelArrayList;
        this.allCategoryListArrayList = allCategoryListArrayList;
        this.homeMovieLists=homeMovieLists;
        this.context = context;

    }

    @Override
    public ParentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ParentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.parent_recycleview, parent, false));
    }

    @Override
    public int getItemCount() {
        return parentModelArrayList.size();
    }

    @Override
    public void onBindViewHolder(ParentViewHolder holder, int position) {
        ArrayList<ChildModel> arrayList = new ArrayList<>();

        ParentModel currentItem = parentModelArrayList.get(position);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.childRecyclerView.setLayoutManager(layoutManager);
        holder.childRecyclerView.setHasFixedSize(true);
        holder.category.setText(currentItem.movieCategory());
        if (parentModelArrayList.get(position).movieCategory().equals(allCategoryListArrayList.get(position).getCname())) {

            for(int index=0;index<homeMovieLists.size();index++){
                if(allCategoryListArrayList.get(position).getCname().equals(homeMovieLists.get(index).getCname())){

                    arrayList.add(new ChildModel(Constants.THRUMBNAIL_URL+homeMovieLists.get(index).getThrumbnail(), ""));
                }
            }
        }
        ChildRecyclerViewAdapter childRecyclerViewAdapter = new ChildRecyclerViewAdapter(arrayList,holder.childRecyclerView.getContext());
        holder.childRecyclerView.setAdapter(childRecyclerViewAdapter);
    }

    public static class ParentViewHolder extends RecyclerView.ViewHolder {
        public TextView category;
        public RecyclerView childRecyclerView;

        public ParentViewHolder(View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.parent_item_title);
            childRecyclerView = itemView.findViewById(R.id.parent_recyclerview);
        }
    }



}

