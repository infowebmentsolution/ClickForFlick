//package com.infowebmentsolution.ghosh.clickforflick;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import com.infowebmentsolution.ghosh.clickforflick.Model.AllCategoryList;
//import java.util.ArrayList;
//
//
//public class CardFragment extends Fragment {
//
//    public static final String ARG_OBJECT = "object";
//    private Integer counter;
//    private ArrayList<AllCategoryList> allCategoryListArrayList;
//
//    public CardFragment() {
//    }
//
//    public static CardFragment newInstance(Integer counter) {
//        CardFragment fragment = new CardFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_OBJECT, counter);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        if (getArguments() != null) {
//            counter = getArguments().getInt(ARG_OBJECT);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        return inflater.inflate(R.layout.fragment_blank, container, false);
//    }
//
//    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        TextView textViewCounter = view.findViewById(R.id.tv_counter);
//        if(counter == 0){
//            textViewCounter.setText("HEllo Home");
//        }else {
//            textViewCounter.setText("a"+counter);
//        }
//        System.out.println("----------------------");
//
//    }
//
//}

package com.infowebmentsolution.ghosh.clickforflick.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.infowebmentsolution.ghosh.clickforflick.Adapter.BannerAdapter;
import com.infowebmentsolution.ghosh.clickforflick.Adapter.ParentRecyclerViewAdapter;
import com.infowebmentsolution.ghosh.clickforflick.Model.AllCategoryList;
import com.infowebmentsolution.ghosh.clickforflick.Model.BannerList;
import com.infowebmentsolution.ghosh.clickforflick.Model.HomeMovieList;
import com.infowebmentsolution.ghosh.clickforflick.Model.ParentModel;
import com.infowebmentsolution.ghosh.clickforflick.R;
import com.infowebmentsolution.ghosh.clickforflick.Response.AllCategoryResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.BannerResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.HomeMovieListsResponse;
import com.infowebmentsolution.ghosh.clickforflick.Utils.Constants;
import com.infowebmentsolution.ghosh.clickforflick.Utils.RestAPI;
import com.infowebmentsolution.ghosh.clickforflick.Utils.RetrofitClient;
import com.smarteist.autoimageslider.SliderView;
import java.util.ArrayList;
import java.util.Arrays;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CardFragment extends Fragment {
    private static final String ARG_OBJECT = "Object";
    private RecyclerView parentRecyclerView;
    private RecyclerView.Adapter ParentAdapter;
    ArrayList<ParentModel> parentModelArrayList = new ArrayList<>();
    ArrayList<AllCategoryList> allCategoryListArrayList;
    ArrayList<AllCategoryList> allCategoryListArrayListAdd = new ArrayList<>();
    ArrayList<HomeMovieList> homeMovieLists;
    ArrayList<HomeMovieList> homeMovieListsAdd = new ArrayList<>();
    private RecyclerView.LayoutManager parentLayoutManager;
    ArrayList<BannerList> sliderDataArrayList = new ArrayList<>();
    ArrayList<BannerList> bannerLists;
    BannerAdapter bannerAdapter;
    SliderView sliderView;
    private Integer counter;
    View itemView ;

    public CardFragment() {
    }

    public static CardFragment newInstance(Integer counter) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_OBJECT, counter);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            counter = getArguments().getInt(ARG_OBJECT);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        itemView = inflater.inflate(R.layout.fragment_home, container, false);
        parentLayoutManager = new LinearLayoutManager(getContext());
        parentRecyclerView = itemView.findViewById(R.id.frag_home_rec);
        ParentAdapter = new ParentRecyclerViewAdapter(parentModelArrayList, this.getContext(),allCategoryListArrayListAdd,homeMovieListsAdd);
        sliderView = itemView.findViewById(R.id.frag_home_sliderView);
        loadAllCategory();
        loadBannerPageViewLoader();
        bannerAdapter = new BannerAdapter( sliderDataArrayList,this.getContext());
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        return itemView;
    }

    private void loadBannerPageViewLoader() {
        getApiService().bannerApi().enqueue(new Callback<BannerResponse>() {
            @Override
            public void onResponse(Call<BannerResponse> call, Response<BannerResponse> response) {
                BannerResponse bannerResponse= response.body();
                if(response.code()==200 && bannerResponse.getMsg().equals("success")){
                    bannerLists= new ArrayList<>(Arrays.asList(bannerResponse.getResult()));
                    for(int index=0;index< bannerLists.size();index++){
                        if(bannerLists.get(index).getIs_home().equals("home")){
                            sliderDataArrayList.add(index,bannerLists.get(index));
                        }
                    }
                    sliderView.setSliderAdapter(bannerAdapter);
                    sliderView.setScrollTimeInSec(3);
                    sliderView.setAutoCycle(true);
                    sliderView.startAutoCycle();
                }
            }

            @Override
            public void onFailure(Call<BannerResponse> call, Throwable t) {

            }
        });
    }

    public ArrayList<ParentModel> loadAllCategory() {
        getApiService().allcategoriesApi().enqueue(new Callback<AllCategoryResponse>() {
            @Override
            public void onResponse(Call<AllCategoryResponse> call, Response<AllCategoryResponse> response) {
                if(response.code()==200 && response.body().getMsg().equals("success")){
                    AllCategoryResponse allCategoryResponse=response.body();

                    allCategoryListArrayList=new  ArrayList<>(Arrays.asList(allCategoryResponse.getResult()));
                    int length = allCategoryListArrayList.size();

                    for(int i = 0 ; i < length; i++)
                    {
                        if(allCategoryListArrayList.get(i).getCatstatus().equals("1"))
                        {
                            parentModelArrayList.add(new ParentModel(allCategoryListArrayList.get(i).getCname()));
                            allCategoryListArrayListAdd.add(i,allCategoryListArrayList.get(i));
                        }
                    }
                    loadHomePageViewLoader();
                }
            }

            @Override
            public void onFailure(Call<AllCategoryResponse> call, Throwable t) {

            }
        });
        return parentModelArrayList;
    }
    private static RestAPI getApiService() {
        return (RestAPI) RetrofitClient.getRetrofitClient(Constants.API_URL).create(RestAPI.class);
    }
    private void loadHomePageViewLoader() {
        getApiService().homeVideoApi().enqueue(new Callback<HomeMovieListsResponse>() {
            @Override
            public void onResponse(Call<HomeMovieListsResponse> call, Response<HomeMovieListsResponse> response) {
                if(response.code()==200 && response.body().getMsg().equals("success")){
                    HomeMovieListsResponse homeMovieListsResponse =response.body();
                    homeMovieLists = new ArrayList<>(Arrays.asList(homeMovieListsResponse.getResult()));

                    for(int index=0;index<homeMovieLists.size();index++){
                        if(homeMovieLists.get(index).getIs_home().equals("home")){
                            homeMovieListsAdd.add(index,homeMovieLists.get(index));

                        }
                    }

                    parentRecyclerView.setHasFixedSize(true);
                    parentRecyclerView.setLayoutManager(parentLayoutManager);
                    parentRecyclerView.setAdapter(ParentAdapter);
                    ParentAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<HomeMovieListsResponse> call, Throwable t) {

            }
        });
    }

}