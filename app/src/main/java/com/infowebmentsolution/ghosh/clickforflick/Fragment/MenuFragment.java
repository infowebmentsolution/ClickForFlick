package com.infowebmentsolution.ghosh.clickforflick.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
//import androidx.fragment.app.FragmentManager;
import com.infowebmentsolution.ghosh.clickforflick.Activity.Privacy_and_AboutActivity;
import com.infowebmentsolution.ghosh.clickforflick.Activity.ProfileActivity;
import com.infowebmentsolution.ghosh.clickforflick.Activity.SignInActivity;
import com.infowebmentsolution.ghosh.clickforflick.Activity.SignUpActivity;
import com.infowebmentsolution.ghosh.clickforflick.Activity.SubscriptionPlanActivity;
import com.infowebmentsolution.ghosh.clickforflick.BlankActivity;
import com.infowebmentsolution.ghosh.clickforflick.Model.ProfileList;
import com.infowebmentsolution.ghosh.clickforflick.R;
import com.infowebmentsolution.ghosh.clickforflick.Response.LogoutResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.ProfileResponse;
import com.infowebmentsolution.ghosh.clickforflick.Utils.Constants;
import com.infowebmentsolution.ghosh.clickforflick.Utils.RestAPI;
import com.infowebmentsolution.ghosh.clickforflick.Utils.RetrofitClient;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuFragment extends Fragment {

    private ArrayList<ProfileList> profileLists;
   // FragmentManager fragmentManager;
    ProgressBar progressBar;
    TextView name;
    TextView email;
    TextView logout;
    TextView buyPlans;
    TextView myTransaction;
    TextView mySubscription;
    TextView about_us;
    TextView privacyPolicy;
    String userId;
    ScrollView scrollView;
    View rootview;
    View view_logout;
    LinearLayout profile_ll;
    FrameLayout frameLayout;
    private FragmentActivity findFragmentByTag;
    private String google_sign_in;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       // Constants.clickBack=0;

        rootview = inflater.inflate(R.layout.fragment_menu, container, false);

        SharedPreferences sharedPreferences=  this.requireActivity().getSharedPreferences(Constants.LOG_IN_DATA,0);
        userId=sharedPreferences.getString(Constants.USER_ID,"none");
        google_sign_in=sharedPreferences.getString(Constants.GOOGLE_SIGN_IN,"no");

        frameLayout=rootview.findViewById(R.id.frag_menu);
        progressBar = rootview.findViewById(R.id.frag_menu_progressbar);
        scrollView = rootview.findViewById(R.id.frag_menu_scroll);
        profile_ll=rootview.findViewById(R.id.frag_menu_profile_ll);
        name = rootview.findViewById(R.id.frag_menu_username);
        email=rootview.findViewById(R.id.frag_menu_login_or_email);
        buyPlans=rootview.findViewById(R.id.frag_menu_buyplan);
        myTransaction=rootview.findViewById(R.id.frag_menu_mytrasaction);
        mySubscription=rootview.findViewById(R.id.frag_menu_mysubscription);
        about_us=rootview.findViewById(R.id.frag_menu_abouus);
        logout=rootview.findViewById(R.id.frag_menu_logout);
        view_logout=rootview.findViewById(R.id.frag_menu_view_logout);
        privacyPolicy=rootview.findViewById(R.id.frag_menu_privacy_policy);

        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        if(!this.userId.equals("none")){
            progressBar.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
            profileView();
        }else {
            progressBar.setVisibility(View.GONE);
            logout.setVisibility(View.GONE);
            view_logout.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }

        profile_ll.setOnClickListener(view -> {
            if(userId.equals("none")){
                Intent intent_sign_up = new Intent(getActivity(), SignUpActivity.class);
                startActivity(intent_sign_up);
            }else {
                Intent intent_profile = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent_profile);
            }
        });

        logout.setOnClickListener(view -> callLogoutApi());

        buyPlans.setOnClickListener(view->{
            Intent intent_buy_plans = new Intent(getActivity(), SubscriptionPlanActivity.class);
            startActivity(intent_buy_plans);
        });

        privacyPolicy.setOnClickListener(view->{
            Intent intent_privacy_policy = new Intent(getActivity(), Privacy_and_AboutActivity.class);
            intent_privacy_policy.putExtra("activity_name","privacy_policy");
            startActivity(intent_privacy_policy);
        });

        about_us.setOnClickListener(view->{
            Intent intent_about_us = new Intent(getActivity(),Privacy_and_AboutActivity.class);
            intent_about_us.putExtra("activity_name","about_us");
            startActivity(intent_about_us);
        });

        return rootview;
    }

        private void callLogoutApi() {
        getApiService().logoutApi(this.userId).enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(@NotNull Call<LogoutResponse> call, @NotNull Response<LogoutResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getResult().equals("success")) {
                        SharedPreferences.Editor editor = requireActivity().getSharedPreferences(Constants.LOG_IN_DATA, 0).edit();
                        editor.putString(Constants.USER_ID, "none");
                        editor.putString(Constants.SUBSCRIPTION_ID, "no");
                        editor.putString(Constants.GOOGLE_SIGN_IN,"no");
                        editor.putString(Constants.LANGUAGE,Constants.LANGUAGE);
                        editor.apply();
                        editor.commit();
//                        Fragment currentFragment = getFragmentManager().findFragmentByTag(String.valueOf(MenuFragment.this));
//                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction()
//                              //  .detach(currentFragment)
//                                .attach(currentFragment);
//                        fragmentTransaction.commit();

                        Intent intent = new Intent(getActivity(), BlankActivity.class);
                        startActivity(intent);

    //                    Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.frag_menu);
    //                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    //                    fragmentTransaction.detach(currentFragment);
    //                    fragmentTransaction.attach(currentFragment);
    //                    fragmentTransaction.commit();

                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<LogoutResponse> call, @NotNull Throwable t) {

            }
        });
    }

    private void profileView() {
        getApiService().profileApi(userId).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(@NotNull Call<ProfileResponse> call, @NotNull Response<ProfileResponse> response) {
                ProfileResponse profileResponse = response.body();
                if(response.code() == 200) {
                    assert profileResponse != null;
                    if (profileResponse.getMsg().equals("success")) {
                        profileLists = new ArrayList<>(Arrays.asList(profileResponse.getResult()));

                        if (profileLists.get(0).getName().equals("")) {
                            name.setText(profileLists.get(0).getUserphoneno());
                        } else name.setText(profileLists.get(0).getName());
                        email.setText(profileLists.get(0).getUseremail());
                        logout.setVisibility(View.VISIBLE);
                        view_logout.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<ProfileResponse> call, @NotNull Throwable t) {

            }
        });
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        findFragmentByTag = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    private RestAPI getApiService() {
        return RetrofitClient.getRetrofitClient(Constants.API_URL).create(RestAPI.class);
    }
}