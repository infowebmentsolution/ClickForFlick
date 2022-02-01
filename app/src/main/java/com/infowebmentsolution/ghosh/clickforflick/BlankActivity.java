package com.infowebmentsolution.ghosh.clickforflick;


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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.infowebmentsolution.ghosh.clickforflick.Activity.LanguageActivity;
import com.infowebmentsolution.ghosh.clickforflick.Activity.MainActivity;
import com.infowebmentsolution.ghosh.clickforflick.Activity.SignInActivity;
import com.infowebmentsolution.ghosh.clickforflick.Activity.SubscriptionPlanActivity;
import com.infowebmentsolution.ghosh.clickforflick.Adapter.ViewPagerAdapter;
import com.infowebmentsolution.ghosh.clickforflick.Fragment.MenuFragment;
import com.infowebmentsolution.ghosh.clickforflick.Fragment.SearchFragment;
import com.infowebmentsolution.ghosh.clickforflick.Model.AllCategoryList;
import com.infowebmentsolution.ghosh.clickforflick.Model.SharedViewModel;
import com.infowebmentsolution.ghosh.clickforflick.Response.AllCategoryResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.SubscibeCheckResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.isLoginCheckResponse;
import com.infowebmentsolution.ghosh.clickforflick.Utils.Constants;
import com.infowebmentsolution.ghosh.clickforflick.Utils.RestAPI;
import com.infowebmentsolution.ghosh.clickforflick.Utils.RetrofitClient;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlankActivity extends AppCompatActivity {

    private String doubleBackPress= "one";

    private ArrayList<AllCategoryList> allCategoryListArrayList;

    ChipNavigationBar chipNavigationBar;
    FragmentManager fragmentManager;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    Toolbar toolbar;
    SharedViewModel sharedViewModel;
    SharedViewModel viewModel;
    FrameLayout frameLayout;
    ArrayList<AllCategoryList> allCategoryListArrayList1 = new ArrayList<>();
    private final String Tag=BlankActivity.class.getSimpleName();
    private String userID;
    private String android_id;
    private String device_id;
    private String subscriptionStatus;
    int catSize;
    ViewPagerAdapter viewPagerAdapter;
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 5000;
  //  int clickback =0;
    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_blank);
        Toolbar toolbar1 = findViewById(R.id.main_bar);
        this.toolbar = toolbar1;
        setSupportActionBar(toolbar1);
        this.viewModel = ViewModelProviders.of(this).get(SharedViewModel.class);
        viewPager = findViewById(R.id.view_pager);
        tabLayout =findViewById(R.id.mainblanklayouts_tablayouts);
        chipNavigationBar=findViewById(R.id.mainblanklayout_bottomNav_view);
        frameLayout=findViewById(R.id.mainblanklayouts_frame);
        setTabs();
        SharedPreferences editor = getSharedPreferences(Constants.LOG_IN_DATA, 0);
        this.userID = editor.getString(Constants.USER_ID, "none");
        subscription(this.userID);
        viewPager.setAdapter(createCardAdapter());

        //----GET ANDROID DEVICE ID--------
        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        this.subscriptionStatus=editor.getString(Constants.SUBSCRIPTION_ID,"no");


        //SET SHARED-VIEW-MODEL FOR DATA STORING AND SHARING FROM ONE TO ANOTHER FRAGMENT OR ACTIVITY------
        this.sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel.class);


        if (savedInstanceState == null) {
            chipNavigationBar.setItemSelected(R.id.navigation_home, true);
            this.fragmentManager = getSupportFragmentManager();

        }

        this.chipNavigationBar.setOnItemSelectedListener((ChipNavigationBar.OnItemSelectedListener) new ChipNavigationBar.OnItemSelectedListener() {

            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i) {
                    case R.id.navigation_home:
                        BlankActivity.this.frameLayout.setVisibility(View.GONE);
                        BlankActivity.this.tabLayout.setVisibility(View.VISIBLE);
                        BlankActivity.this.viewPager.setVisibility(View.VISIBLE);
                        BlankActivity.this.toolbar.setVisibility(View.VISIBLE);
                        break;
                    case R.id.navigation_language:
                        Intent intent = new Intent(BlankActivity.this, LanguageActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_menu:

                        fragment = new MenuFragment();
                        BlankActivity.this.frameLayout.setVisibility(View.VISIBLE);
                        BlankActivity.this.toolbar.setVisibility(View.GONE);
                        BlankActivity.this.tabLayout.setVisibility(View.GONE);
                        BlankActivity.this.viewPager.setVisibility(View.GONE);
                        break;
                }
                if (fragment != null) {
                    BlankActivity BlankActivity = BlankActivity.this;
                    BlankActivity.fragmentManager = BlankActivity.getSupportFragmentManager();
                    BlankActivity.this.fragmentManager.beginTransaction().replace(R.id.mainblanklayouts_frame, fragment).commit();
                    return;
                }
                Log.e(BlankActivity.this.Tag, "Error");

            }
        });
    }

    private ViewPagerAdapter createCardAdapter() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        adapter.addCategory(allCategoryListArrayList1);
        return adapter;
    }
    private static RestAPI getApiService() {
        return RetrofitClient.getRetrofitClient(Constants.API_URL).create(RestAPI.class);
    }
    private void isUserLoginCheck() {

        getApiService().isLoginCheckApi(this.userID, this.android_id).enqueue(new Callback<isLoginCheckResponse>() {
            @Override
            public void onResponse(Call<isLoginCheckResponse> call, Response<isLoginCheckResponse> response) {
                if (response.code() == 200 && response.body().getResult().equals("0")) {
                    SharedPreferences.Editor editor = BlankActivity.this.getSharedPreferences(Constants.LOG_IN_DATA, 0).edit();
                    editor.putString(Constants.USER_ID, "none");
                    editor.apply();
                    editor.commit();
                    Intent intent_logout = new Intent(BlankActivity.this, SignInActivity.class);
                    startActivity(intent_logout);
                }

            }

            @Override
            public void onFailure(Call<isLoginCheckResponse> call, Throwable t) {

            }
        });
    }
    private void subscription(String user_Id) {
        getApiService().SubscibeCheckApi(user_Id).enqueue(new Callback<SubscibeCheckResponse>() {
            @Override
            public void onResponse(Call<SubscibeCheckResponse> call, Response<SubscibeCheckResponse> response) {
                if (response.code() == 200 && response.body().getResult().equals("1")) {
                    SharedPreferences.Editor editor = BlankActivity.this.getSharedPreferences(Constants.LOG_IN_DATA, 0).edit();
                    editor.putString(Constants.SUBSCRIPTION_ID, "yes");
                    editor.apply();

                }else if( response.code() != 200){

                } else if (response.code() == 200 && (response.body().getResult().equals("0")|| response.body().getResult().equals("2")|| response.body().getResult().equals("3")) ) {
                    SharedPreferences.Editor editor = BlankActivity.this.getSharedPreferences(Constants.LOG_IN_DATA, 0).edit();
                    editor.putString(Constants.SUBSCRIPTION_ID, "no");
                    editor.apply();

                }
            }

            @Override
            public void onFailure(Call<SubscibeCheckResponse> call, Throwable t) {

            }
        });
    }

    private void setTabs() {

        this.viewPagerAdapter = new ViewPagerAdapter(this);
        getApiService().allcategoriesApi().enqueue(new Callback<AllCategoryResponse>() {
            @Override
            public void onResponse(Call<AllCategoryResponse> call, Response<AllCategoryResponse> response) {
                if(response.code()==200 && response.body().getMsg().equals("success")){
                    AllCategoryResponse allCategoryResponse=response.body();

                    BlankActivity.this.allCategoryListArrayList=new ArrayList<>(Arrays.asList(allCategoryResponse.getResult()));
                    int length = allCategoryListArrayList.size();
                    for(int index=0;index<length;index++){
                        BlankActivity.this.allCategoryListArrayList1.add(index,allCategoryListArrayList.get(index));

                    }
                }

                BlankActivity.this.viewPagerAdapter.addCategory(allCategoryListArrayList1);
                BlankActivity blankActivity = BlankActivity.this;
                blankActivity.catSize = allCategoryListArrayList1.size();
                new TabLayoutMediator(tabLayout, viewPager,
                        (tab, position) -> {

                    if(position<= allCategoryListArrayList1.size()) {
                        tab.setText(allCategoryListArrayList.get(position).getCname());
                    }
                        }

                ).attach();
            }

            @Override
            public void onFailure(Call<AllCategoryResponse> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        subscription(this.userID);
    }

    @Override
    protected void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);
                SharedPreferences editor = getSharedPreferences(Constants.LOG_IN_DATA, 0);
                userID = editor.getString(Constants.USER_ID, "none");
                device_id = editor.getString(Constants.DEVICE_ID, "none");

                if (!userID.equals("none")) isUserLoginCheck();
            }
        }, delay);
        super.onResume();
    }
    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable); //stop handler when activity not visible super.onPause();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        subscription(this.userID);
        SharedPreferences editor = getSharedPreferences(Constants.LOG_IN_DATA, 0);
        this.subscriptionStatus = editor.getString(Constants.SUBSCRIPTION_ID, "none");

        if(this.subscriptionStatus.equals("yes")) {
            Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.backgroundblack);
        }else {
            Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.subscribe);
        }
        getSupportActionBar().setIcon((int) R.drawable.logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                BlankActivity.this.viewPager.setVisibility(View.GONE);
                BlankActivity.this.tabLayout.setVisibility(View.GONE);
                BlankActivity.this.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        BlankActivity.this.tabLayout.setVisibility(View.VISIBLE);
                        BlankActivity.this.viewPager.setVisibility(View.VISIBLE);
                    }
                });
                searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                    public boolean onClose() {
                        finish();
                        startActivity(getIntent());
                        return false;
                    }
                });
                BlankActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.mainblanklayouts_frame, new SearchFragment()).commit();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public boolean onQueryTextSubmit(String s) {
                BlankActivity.this.viewModel.setText(s);
                return false;
            }

            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == 16908332) {
            if(subscriptionStatus.equals("no")) startActivity(new Intent(this, SubscriptionPlanActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
//        if(Constants.clickBack==0) {
//             Constants.clickBack =1;
//            Intent intent = new Intent(BlankActivity.this,BlankActivity.class);
//            startActivity(intent);
//           // Toast.makeText(this, "Black Activity", Toast.LENGTH_SHORT).show();
//        }else{
//            Constants.clickBack =0;
//
//        }
    }
}