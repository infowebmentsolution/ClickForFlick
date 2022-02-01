package com.infowebmentsolution.ghosh.clickforflick.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.material.tabs.TabLayout;
import com.infowebmentsolution.ghosh.clickforflick.Activity.MoviePlayActivity;
import com.infowebmentsolution.ghosh.clickforflick.Adapter.BannerAdapter;
import com.infowebmentsolution.ghosh.clickforflick.Adapter.BannerMoviesPagerAdapter;
import com.infowebmentsolution.ghosh.clickforflick.Adapter.CategoryWiseAdapter;
import com.infowebmentsolution.ghosh.clickforflick.Adapter.ParentRecyclerViewAdapter;
import com.infowebmentsolution.ghosh.clickforflick.Model.AllCategoryList;
import com.infowebmentsolution.ghosh.clickforflick.Model.BannerList;
import com.infowebmentsolution.ghosh.clickforflick.Model.HomeMovieList;
import com.infowebmentsolution.ghosh.clickforflick.Model.ParentModel;
import com.infowebmentsolution.ghosh.clickforflick.R;
import com.infowebmentsolution.ghosh.clickforflick.Response.AllCategoryResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.BannerResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.CategoryWiseResponse;
import com.infowebmentsolution.ghosh.clickforflick.Utils.Constants;
import com.infowebmentsolution.ghosh.clickforflick.Utils.RestAPI;
import com.infowebmentsolution.ghosh.clickforflick.Utils.RetrofitClient;
import com.smarteist.autoimageslider.SliderView;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment  implements CategoryWiseAdapter.ClickedOnItemListener {
    private static final String ARG_OBJECT = "Object";
    static int indexCount = 0;
    final long DELAY_MS = 500;
    List<HomeMovieList> HomeBannerList;
    TabLayout IndicatorTab;
    final long PERIOD_MS = 3000;
    private RecyclerView.Adapter ParentAdapter;
    ArrayList<AllCategoryList> allCategoryListArrayList;
    ArrayList<AllCategoryList> allCategoryListArrayListAdd = new ArrayList<>();
    ViewPager bannerMoviesLViewPager;
    CategoryWiseAdapter categoryWiseAdapter;
    String cname;
    /* access modifiers changed from: private */
    public Integer counter;
    int currentPage = 0;
    final Handler handler = new Handler();
    ArrayList<HomeMovieList> homeMovieLists;
    ArrayList<HomeMovieList> homeMovieLists1;
    ArrayList<HomeMovieList> homeMovieListsAdd = new ArrayList<>();
    ArrayList<HomeMovieList> homeMovieListsAdd1;
    View itemView;
    String lang;
    private RecyclerView.LayoutManager parentLayoutManager;
    ArrayList<ParentModel> parentModelArrayList = new ArrayList<>();
    /* access modifiers changed from: private */
    public RecyclerView parentRecyclerView;
    String pincode;
    RelativeLayout relativeLayout;
    Timer timer;
    String userID;




    private final Handler sliderHandler = new Handler();
    public HomeFragment() {
    }

    public static HomeFragment newInstance(Integer counter) {
        HomeFragment fragment = new HomeFragment();
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

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        parentLayoutManager = new LinearLayoutManager(getContext());
        parentRecyclerView = itemView.findViewById(R.id.frag_home_rec);
        if(counter == 0){
            ParentAdapter = new ParentRecyclerViewAdapter(parentModelArrayList, this.getContext(),allCategoryListArrayListAdd,homeMovieListsAdd);
        }
        categoryWiseAdapter = new CategoryWiseAdapter(this::ClickedItem);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(Constants.LOG_IN_DATA,0);
        lang = sharedPreferences.getString(Constants.LANGUAGE,"all");
        userID=sharedPreferences.getString(Constants.USER_ID,"none");
        pincode=sharedPreferences.getString(Constants.PINCODE,"0000000");
        loadAllCategory();
        loadRec();

        this.bannerMoviesLViewPager = (ViewPager) this.itemView.findViewById(R.id.frag_home_viewpager3);
        this.IndicatorTab = (TabLayout) this.itemView.findViewById(R.id.tab_indicator);
        ArrayList arrayList = new ArrayList();
        this.HomeBannerList = arrayList;
        setBannerMoviesPagerAdapter(arrayList);
        new ArrayList();
        return itemView;
    }

    private void setBannerMoviesPagerAdapter(List<HomeMovieList> bannerMoviesList) {
        this.bannerMoviesLViewPager.setAdapter(new BannerMoviesPagerAdapter(getActivity(), bannerMoviesList));
        this.IndicatorTab.setupWithViewPager(this.bannerMoviesLViewPager);
        this.IndicatorTab.setupWithViewPager(this.bannerMoviesLViewPager, true);
    }


    /* access modifiers changed from: private */
    public void loadBanner(String cat_name) {
        indexCount = 0;
        String catName = cat_name.replaceAll(" ", "").toLowerCase(Locale.ROOT);
        PrintStream printStream = System.out;
        printStream.println("----------------categoryName-------------" + cat_name);
        this.homeMovieListsAdd1 = new ArrayList<>();
        getApiService().categoryWiseApi(catName).enqueue(new Callback<CategoryWiseResponse>() {


            public void onResponse(Call<CategoryWiseResponse> call, Response<CategoryWiseResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (!(response.body().getMsg().equals("Data do not found!"))) {
                        CategoryWiseResponse categoryWiseResponse = response.body();
                        if (categoryWiseResponse != null) {
                            HomeFragment.this.homeMovieLists1 = new ArrayList<>(Arrays.asList(categoryWiseResponse.getCatBanner()));
                            HomeFragment.this.bannerMoviesLViewPager.setAdapter(new BannerMoviesPagerAdapter(HomeFragment.this.getActivity(), HomeFragment.this.homeMovieLists1));
                            HomeFragment.this.IndicatorTab.setupWithViewPager(HomeFragment.this.bannerMoviesLViewPager);
                            HomeFragment.this.IndicatorTab.setupWithViewPager(HomeFragment.this.bannerMoviesLViewPager, true);
                            final Runnable Update = new Runnable() {
                                public void run() {
                                    if (HomeFragment.this.currentPage == HomeFragment.this.homeMovieLists1.size()) {
                                        HomeFragment.this.currentPage = 0;
                                    }
                                    ViewPager viewPager = HomeFragment.this.bannerMoviesLViewPager;
                                    HomeFragment homeFragment = HomeFragment.this;
                                    int i = homeFragment.currentPage;
                                    homeFragment.currentPage = i + 1;
                                    viewPager.setCurrentItem(i, true);
                                }
                            };
                            HomeFragment.this.timer = new Timer();
                            HomeFragment.this.timer.schedule(new TimerTask() {
                                public void run() {
                                    HomeFragment.this.handler.post(Update);
                                }
                            }, 500, 3000);
                            return;
                        }
                        throw new AssertionError();
                    }
                }
            }

            public void onFailure(Call<CategoryWiseResponse> call, Throwable t) {
            }
        });
    }

    public void loadBannerbyLang(String cat_name, String lang2) {
        indexCount = 0;
        getApiService().bannerByLangApi(cat_name.replaceAll(" ", "").toLowerCase(Locale.ROOT), lang2).enqueue(new Callback<CategoryWiseResponse>() {

            public void onResponse(Call<CategoryWiseResponse> call, Response<CategoryWiseResponse> response) {
                if (response.code() == 200 && response.body().getMsg().equals("success")) {
                    CategoryWiseResponse categoryWiseResponse = response.body();
                    if (categoryWiseResponse != null) {
                        HomeFragment.this.homeMovieListsAdd1 = new ArrayList<>(Arrays.asList(categoryWiseResponse.getCatBanner()));
                        HomeFragment.this.bannerMoviesLViewPager.setAdapter(new BannerMoviesPagerAdapter(HomeFragment.this.getActivity(), HomeFragment.this.homeMovieListsAdd1));
                        HomeFragment.this.IndicatorTab.setupWithViewPager(HomeFragment.this.bannerMoviesLViewPager);
                        HomeFragment.this.IndicatorTab.setupWithViewPager(HomeFragment.this.bannerMoviesLViewPager, true);
                        final Runnable Update = new Runnable() {
                            public void run() {
                                if (HomeFragment.this.currentPage == HomeFragment.this.homeMovieListsAdd1.size()) {
                                    HomeFragment.this.currentPage = 0;
                                }
                                ViewPager viewPager = HomeFragment.this.bannerMoviesLViewPager;
                                HomeFragment homeFragment = HomeFragment.this;
                                int i = homeFragment.currentPage;
                                homeFragment.currentPage = i + 1;
                                viewPager.setCurrentItem(i, true);
                            }
                        };
                        HomeFragment.this.timer = new Timer();
                        HomeFragment.this.timer.schedule(new TimerTask() {
                            public void run() {
                                HomeFragment.this.handler.post(Update);
                            }
                        }, 500, 3000);
                        return;
                    }
                    throw new AssertionError();
                }
            }

            public void onFailure(Call<CategoryWiseResponse> call, Throwable t) {
            }
        });
    }


    private void loadRec() {
        parentRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        parentRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));

    }




    public ArrayList<ParentModel> loadAllCategory() {
        getApiService().allcategoriesApi().enqueue(new Callback<AllCategoryResponse>() {


            public void onResponse(Call<AllCategoryResponse> call, Response<AllCategoryResponse> response) {
                if (response.code() != 200) {
                    return;
                }
                if (response.body() == null) {
                    throw new AssertionError();
                } else if (response.body().getMsg().equals("success")) {
                    HomeFragment.this.allCategoryListArrayList = new ArrayList<>(Arrays.asList(response.body().getResult()));
                    int length = HomeFragment.this.allCategoryListArrayList.size();
                    for (int i = 0; i < length; i++) {
                        if (HomeFragment.this.allCategoryListArrayList.get(i).getCatstatus().equals(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE)) {
                            HomeFragment.this.parentModelArrayList.add(new ParentModel(HomeFragment.this.allCategoryListArrayList.get(i).getCname()));
                            HomeFragment.this.allCategoryListArrayListAdd.add(i, HomeFragment.this.allCategoryListArrayList.get(i));
                        }
                    }
                    if (HomeFragment.this.counter.intValue() <= HomeFragment.this.allCategoryListArrayList.size()) {
                        HomeFragment homeFragment = HomeFragment.this;
                        homeFragment.cname = homeFragment.allCategoryListArrayListAdd.get(HomeFragment.this.counter.intValue()).getCname();
                        if (HomeFragment.this.allCategoryListArrayListAdd.get(HomeFragment.this.counter.intValue()).getCname().equals("News")) {
                            HomeFragment homeFragment2 = HomeFragment.this;
                            homeFragment2.loadNewsDataByPin(homeFragment2.allCategoryListArrayListAdd.get(HomeFragment.this.counter.intValue()).getCname(), HomeFragment.this.pincode);
                        } else {
                            HomeFragment homeFragment3 = HomeFragment.this;
                            homeFragment3.loadCategoryDataList(homeFragment3.allCategoryListArrayListAdd.get(HomeFragment.this.counter.intValue()).getCname());
                        }
                        if (!HomeFragment.this.lang.equals(Constants.LANGUAGE)) {
                            HomeFragment homeFragment4 = HomeFragment.this;
                            homeFragment4.loadBannerbyLang(homeFragment4.allCategoryListArrayListAdd.get(HomeFragment.this.counter.intValue()).getCname(), HomeFragment.this.lang);
                            return;
                        }
                        HomeFragment homeFragment5 = HomeFragment.this;
                        homeFragment5.loadBanner(homeFragment5.allCategoryListArrayListAdd.get(HomeFragment.this.counter.intValue()).getCname());
                    }
                }
            }

            public void onFailure(Call<AllCategoryResponse> call, Throwable t) {
            }
        });
        return this.parentModelArrayList;
    }


    private void loadNewsDataByPin(String c_name,String pincode) {
        indexCount=0;
        String catName = c_name.replaceAll(" ","");
        catName = catName.toLowerCase(Locale.ROOT);
        homeMovieListsAdd = new ArrayList<HomeMovieList>();
        getApiService().NewsByPinApi(userID,pincode).enqueue(new Callback<CategoryWiseResponse>() {
            @Override
            public void onResponse(@NonNull Call<CategoryWiseResponse> call, @NonNull Response<CategoryWiseResponse> response) {
                if(response.code()==200 && !(Objects.requireNonNull(response.body()).getMsg().equals("Data do not found!"))){
                    CategoryWiseResponse categoryWiseResponse =response.body();
                    homeMovieLists = new ArrayList<>(Arrays.asList(categoryWiseResponse.getCatVideo()));

                    for(int index=0;index<homeMovieLists.size();index++){
                        if((lang.equals("all")))homeMovieListsAdd.add(index,homeMovieLists.get(index));
                        else if(homeMovieLists.get(index).getLang().equals(lang)){
                            homeMovieListsAdd.add(indexCount,homeMovieLists.get(index));
                            indexCount++;
                        }
                            System.out.println("-------------------homeList--------..............--------------------"+homeMovieLists.get(0).getCname()+"------------"+lang);
                    }
                    categoryWiseAdapter.setData(homeMovieListsAdd);
                    parentRecyclerView.setAdapter(categoryWiseAdapter);

                }else {
                    loadCategoryDataList(c_name);
                }
            }


            @Override
            public void onFailure(@NonNull Call<CategoryWiseResponse> call, @NonNull Throwable t) {

            }
        });
    }



    public void loadCategoryDataList(String cname2) {
        String string = getActivity().getSharedPreferences(Constants.LOG_IN_DATA, 0).getString(Constants.LANGUAGE, Constants.LANGUAGE);
        this.lang = string;
        if (string.equals(Constants.SUBSCRIPTION_ID)) {
            this.lang = Constants.LANGUAGE;
        }
        indexCount = 0;
        String catName = cname2.replaceAll(" ", "").toLowerCase(Locale.ROOT);
        this.homeMovieListsAdd = new ArrayList<>();
        getApiService().categoryWiseApi(catName).enqueue(new Callback<CategoryWiseResponse>() {
            public void onResponse(Call<CategoryWiseResponse> call, Response<CategoryWiseResponse> response) {

                if (response.code() == 200) {
                    assert response.body() != null;
                    if (!(response.body().getMsg().equals("Data do not found!"))) {
                        HomeFragment.this.homeMovieLists = new ArrayList<>(Arrays.asList(response.body().getCatVideo()));
                        for (int index = 0; index < HomeFragment.this.homeMovieLists.size(); index++) {
                            if (HomeFragment.this.lang.equals(Constants.LANGUAGE)) {
                                HomeFragment.this.homeMovieListsAdd.add(index, HomeFragment.this.homeMovieLists.get(index));
                            } else if (HomeFragment.this.homeMovieLists.get(index).getLang().equals(HomeFragment.this.lang)) {
                                HomeFragment.this.homeMovieListsAdd.add(HomeFragment.indexCount, HomeFragment.this.homeMovieLists.get(index));
                                HomeFragment.indexCount++;
                            }
                        }
                        HomeFragment.this.categoryWiseAdapter.setData(HomeFragment.this.homeMovieListsAdd);
                        HomeFragment.this.parentRecyclerView.setAdapter(HomeFragment.this.categoryWiseAdapter);
                    }
                }
            }

            public void onFailure(Call<CategoryWiseResponse> call, Throwable t) {
            }
        });
    }



    private static RestAPI getApiService() {
        return RetrofitClient.getRetrofitClient(Constants.API_URL).create(RestAPI.class);
    }

    @Override
    public void ClickedItem(HomeMovieList homeMovieList) {
        Intent intent = new Intent(getActivity(), MoviePlayActivity.class);
        intent.putExtra("tUrl",homeMovieList.getTrailername());
        intent.putExtra("url",homeMovieList.getVideoname());
        intent.putExtra("vId",homeMovieList.getUploadid());
        intent.putExtra("vname",homeMovieList.getTitle());
        intent.putExtra("vdec",homeMovieList.getDescription());
        intent.putExtra("tUrl",homeMovieList.getTrailername());
        intent.putExtra("isFree",homeMovieList.getIs_free());
        intent.putExtra("cname",cname);
        intent.putExtra("W.T.","no");
        startActivity(intent);
    }
}