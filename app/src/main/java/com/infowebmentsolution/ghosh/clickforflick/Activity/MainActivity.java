package com.infowebmentsolution.ghosh.clickforflick.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.infowebmentsolution.ghosh.clickforflick.Fragment.HomeFragment;
import com.infowebmentsolution.ghosh.clickforflick.Fragment.MenuFragment;
import com.infowebmentsolution.ghosh.clickforflick.Fragment.SearchFragment;
import com.infowebmentsolution.ghosh.clickforflick.Model.AllCategoryList;
import com.infowebmentsolution.ghosh.clickforflick.Model.BannerList;
import com.infowebmentsolution.ghosh.clickforflick.Model.ParentModel;
import com.infowebmentsolution.ghosh.clickforflick.Model.SearchList;
import com.infowebmentsolution.ghosh.clickforflick.Model.SharedViewModel;
import com.infowebmentsolution.ghosh.clickforflick.R;
import com.infowebmentsolution.ghosh.clickforflick.Response.AllCategoryResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.isLoginCheckResponse;
import com.infowebmentsolution.ghosh.clickforflick.Utils.Constants;
import com.infowebmentsolution.ghosh.clickforflick.Utils.RestAPI;
import com.infowebmentsolution.ghosh.clickforflick.Utils.RetrofitClient;
import com.infowebmentsolution.ghosh.clickforflick.Adapter.ViewPagerAdapter;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
//
//
//    ChipNavigationBar chipNavigationBar;
//    FragmentManager fragmentManager;
//    ProgressBar progressBar;
//    Toolbar toolbar;
//    TabLayout tabLayout;
//    SearchList searchList;
//    SharedViewModel sharedViewModel;
//    ViewPager2 viewPager;
//    FrameLayout frameLayout;
//    String Tag = MainActivity.class.getSimpleName();
//
//    Handler handler = new Handler();
//    Runnable runnable;
//    int delay = 5000;
//    private String android_id;
//    private String device_id;
//    private String userID;
//    SharedViewModel viewModel;
//    private ArrayList<AllCategoryList> allCategoryListArrayList;
//    ArrayList<AllCategoryList> allCategoryListArrayList1 = new ArrayList<>();
//    private RecyclerView parentRecyclerView;
//    private RecyclerView.Adapter ParentAdapter;
//    ArrayList<ParentModel> parentModelArrayList = new ArrayList<>();
//    private RecyclerView.LayoutManager parentLayoutManager;
//    ArrayList<BannerList> bannerLists;
//    int click =0;
//
//    //----------------------------------ON-CREATE----------------------------------
//    @SuppressLint("HardwareIds")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main_layout);
//
//        //------------IDs-----
//        this.tabLayout = findViewById(R.id.mainlayouts_tablayouts);
//        Toolbar toolbar1 = findViewById(R.id.main_bar);
//        ChipNavigationBar chipNavigationBar1 = findViewById(R.id.mainlayout_bottomNav_view);
//        progressBar = findViewById(R.id.mainlayouts_progressbar);
//        this.frameLayout=findViewById(R.id.mainlayouts_frame);
//        this.viewPager = findViewById(R.id.mainlayouts_viewpager);
//        this.chipNavigationBar = chipNavigationBar1;
//        this.toolbar = toolbar1;
//        setSupportActionBar(toolbar1);
//        this.viewModel = ViewModelProviders.of(this).get(SharedViewModel.class);
//        progressBar.setVisibility(View.VISIBLE);
//        viewPager.setAdapter(createCardAdapter());
//        //----GET ANDROID DEVICE ID--------
//        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
//
//
//        //--------SHARED PREFERENCE---------
//        SharedPreferences editor = getSharedPreferences(Constants.LOG_IN_DATA, 0);
//        this.userID = editor.getString(Constants.USER_ID, "none");
//
//
//        //SET SHARED-VIEW-MODEL FOR DATA STORING AND SHARING FROM ONE TO ANOTHER FRAGMENT OR ACTIVITY------
//        this.sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel.class);
//
//        if (savedInstanceState == null) {
//            chipNavigationBar.setItemSelected(R.id.navigation_home, true);
//            this.fragmentManager = getSupportFragmentManager();
//        }
//
//
//        // this.viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(),this.tabLayout.getTabCount()));
//        progressBar.setVisibility(View.GONE);
//
//        this.chipNavigationBar.setOnItemSelectedListener((ChipNavigationBar.OnItemSelectedListener) new ChipNavigationBar.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(int i) {
//                Fragment fragment = null;
//                switch (i) {
//                    case R.id.navigation_home:
//                        MainActivity.this.frameLayout.setVisibility(View.GONE);
//                        MainActivity.this.tabLayout.setVisibility(View.VISIBLE);
//                        MainActivity.this.viewPager.setVisibility(View.VISIBLE);
//                        MainActivity.this.toolbar.setVisibility(View.VISIBLE);
//                        break;
//                    case R.id.navigation_language:
//                        fragment = new HomeFragment();
//                        MainActivity.this.frameLayout.setVisibility(View.VISIBLE);
//                        MainActivity.this.progressBar.setVisibility(View.GONE);
//                        MainActivity.this.toolbar.setVisibility(View.GONE);
//                        MainActivity.this.tabLayout.setVisibility(View.GONE);
//                        MainActivity.this.viewPager.setVisibility(View.GONE);
//                        break;
//
//                    case R.id.navigation_menu:
//                        fragment = new MenuFragment();
//                        MainActivity.this.frameLayout.setVisibility(View.VISIBLE);
//                        MainActivity.this.progressBar.setVisibility(View.GONE);
//                        MainActivity.this.toolbar.setVisibility(View.GONE);
//                        MainActivity.this.tabLayout.setVisibility(View.GONE);
//                        MainActivity.this.viewPager.setVisibility(View.GONE);
//                        break;
//
//
//                }
//                if (fragment != null) {
//                    MainActivity mainActivity = MainActivity.this;
//                    mainActivity.fragmentManager = mainActivity.getSupportFragmentManager();
//                    MainActivity.this.fragmentManager.beginTransaction().replace(R.id.mainlayouts_frame, fragment).commit();
//                    return;
//                }
//                Log.e(MainActivity.this.Tag, "Error");
//
//            }
//        });
//
//      //  setTabs();
//    }
//
//    @Override
//    protected void onStart() {
//        Intent intent = new Intent(MainActivity.this,MainActivity.class);
//        startActivity(intent);
//        super.onStart();
//    }
//
//    private ViewPagerAdapter createCardAdapter() {
//        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
//        return adapter;
//    }
//
//    private void setTabs() {
//
//        ArrayList<AllCategoryList> categoryListArrayList = new ArrayList<>();
//        getApiService().allcategoriesApi().enqueue(new Callback<AllCategoryResponse>() {
//            @Override
//            public void onResponse(Call<AllCategoryResponse> call, Response<AllCategoryResponse> response) {
//                if(response.code()==200 && response.body().getMsg().equals("success")){
//                    AllCategoryResponse allCategoryResponse=response.body();
//
//                    allCategoryListArrayList=new  ArrayList<>(Arrays.asList(allCategoryResponse.getResult()));
//                    int length = allCategoryListArrayList.size();
//                    for(int index=0;index<length;index++){
//                        allCategoryListArrayList1.add(index,allCategoryListArrayList.get(index));
//
//                    }
//             //   System.out.println("??????????????????????????????????????"+allCategoryListArrayList1.size());
//
//                new TabLayoutMediator(tabLayout, viewPager,
//                        (tab, position) -> {
//                            if(position == 0){
//                                tab.setText("Home");
//                            }else
//                                tab.setText(allCategoryListArrayList1.get(position-1).getCname());
//                        }
//
//                ).attach();
//
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<AllCategoryResponse> call, Throwable t) {
//
//            }
//        });
//    }
//
//
//    @Override
//    protected void onResume() {
//        handler.postDelayed(runnable = new Runnable() {
//            public void run() {
//                handler.postDelayed(runnable, delay);
//                SharedPreferences editor = getSharedPreferences(Constants.LOG_IN_DATA, 0);
//                userID = editor.getString(Constants.USER_ID, "none");
//                device_id = editor.getString(Constants.DEVICE_ID, "none");
//
//                if (!userID.equals("none")) isUserLoginCheck();
//            }
//        }, delay);
//        super.onResume();
//    }
//
//    private static RestAPI getApiService() {
//        return RetrofitClient.getRetrofitClient(Constants.API_URL).create(RestAPI.class);
//    }
//
//    private void isUserLoginCheck() {
//
//        getApiService().isLoginCheckApi(this.userID, this.android_id).enqueue(new Callback<isLoginCheckResponse>() {
//            @Override
//            public void onResponse(Call<isLoginCheckResponse> call, Response<isLoginCheckResponse> response) {
//
//                if (response.code() == 200 && response.body().getResult().equals("0")) {
//                    SharedPreferences.Editor editor = MainActivity.this.getSharedPreferences(Constants.LOG_IN_DATA, 0).edit();
//                    editor.putString(Constants.USER_ID, "none");
//                    editor.apply();
//                    editor.commit();
//                    Intent intent_logout = new Intent(MainActivity.this, SignInActivity.class);
//                    startActivity(intent_logout);
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<isLoginCheckResponse> call, Throwable t) {
//
//
//            }
//        });
//    }
//
//    @Override
//    protected void onPause() {
//        handler.removeCallbacks(runnable); //stop handler when activity not visible super.onPause();
//        super.onPause();
//    }
//    public boolean onCreateOptionsMenu(Menu menu) {
//        //searchView
//        getMenuInflater().inflate(R.menu.search, menu);
//        MenuItem item = menu.findItem(R.id.action_search);
//
//        getSupportActionBar().setIcon((int) R.drawable.logo);
//        getSupportActionBar().setHomeAsUpIndicator((int) R.drawable.buy);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        final SearchView searchView = (SearchView) item.getActionView();
//
//        searchView.setOnSearchClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                MainActivity.this.viewPager.setVisibility(View.GONE);
//                MainActivity.this.tabLayout.setVisibility(View.GONE);
//                MainActivity.this.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                    public void onClick(View v) {
//                        MainActivity.this.tabLayout.setVisibility(View.VISIBLE);
//                        MainActivity.this.viewPager.setVisibility(View.VISIBLE);
//                    }
//                });
//                searchView.setOnCloseListener(new SearchView.OnCloseListener() {
//                    public boolean onClose() {
//                        MainActivity.this.tabLayout.setVisibility(View.VISIBLE);
//                        MainActivity.this.viewPager.setVisibility(View.VISIBLE);
//                        return false;
//                    }
//                });
//                MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.mainlayouts_frame, new SearchFragment()).commit();
//
//            }
//        });
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            public boolean onQueryTextSubmit(String s) {
//                MainActivity.this.viewModel.setText(s);
//                return false;
//            }
//
//            public boolean onQueryTextChange(String s) {
//                return false;
//            }
//        });
//
////        menu.findItem(R.id.Bengali).setOnMenuItemClick                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            Listener(new MenuItem.OnMenuItemClickListener() {
////            public boolean onMenuItemClick(MenuItem item) {
////                Toast.makeText(MainActivity.this, "Bengali", Toast.LENGTH_LONG).show();
////                return false;
////            }
////        });
//
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == 16908332) {
//            startActivity(new Intent(this, SubscriptionPlanActivity.class));
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onBackPressed() {
//        finishAffinity();
//    }
}