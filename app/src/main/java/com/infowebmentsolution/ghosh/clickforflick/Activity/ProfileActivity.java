package com.infowebmentsolution.ghosh.clickforflick.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.infowebmentsolution.ghosh.clickforflick.BlankActivity;
import com.infowebmentsolution.ghosh.clickforflick.Model.ProfileList;
import com.infowebmentsolution.ghosh.clickforflick.Model.ProfileUpdateList;
import com.infowebmentsolution.ghosh.clickforflick.R;
import com.infowebmentsolution.ghosh.clickforflick.Response.ProfileResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.ProfileUpdateResponse;
import com.infowebmentsolution.ghosh.clickforflick.Utils.Constants;
import com.infowebmentsolution.ghosh.clickforflick.Utils.RestAPI;
import com.infowebmentsolution.ghosh.clickforflick.Utils.RetrofitClient;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    AppCompatButton save_update_btn;
    Toolbar toolbar;
    TextView toolbar_text;
    TextView username_txt;
    TextView userEmail_txt;
    TextView profile_edit;
    LinearLayout show_profile_ll;
    LinearLayout edit_profile_ll;
  //  LinearLayout subscription_ll;
    TextInputEditText username_edit;
    TextInputEditText name_edit;
    TextInputEditText userEmail_edit;
    TextInputEditText userMobile_no_edit;
    TextInputEditText user_city_edit;
    ProgressBar progressBar;
    boolean doubleBackToExitPressedOnce = false;
    String userId;
    String old_name="";
    String old_city="";
    private ArrayList<ProfileList> profileLists;
    private ArrayList<ProfileUpdateList> profileUpdateLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.LOG_IN_DATA,0);
        this.userId = sharedPreferences.getString(Constants.USER_ID,"none");
        save_update_btn=findViewById(R.id.profile_update_save_btn);
        toolbar=(Toolbar) findViewById(R.id.profile_toolbar);
        toolbar_text=findViewById(R.id.profile_toolbar_text);
        username_txt = findViewById(R.id.profile_username_txt);
        userEmail_txt=findViewById(R.id.profile_email_txt);
        show_profile_ll=findViewById(R.id.profile_ll_show_profile);
        edit_profile_ll=findViewById(R.id.profile_edit_ll);
   //     subscription_ll=findViewById(R.id.profile_subscribe_button_ll);
        username_edit=findViewById(R.id.profile_username_edit);
        name_edit=findViewById(R.id.profile_name_edit);
        userEmail_edit=findViewById(R.id.profile_email_edit);
        userMobile_no_edit=findViewById(R.id.profile_mobile_edit);
        user_city_edit=findViewById(R.id.profile_city_edit);
        profile_edit= findViewById(R.id.profile_edit_click);
        setSupportActionBar(toolbar);
        progressBar = findViewById(R.id.profile_progressbar);
        progressBar.setVisibility(View.VISIBLE);
        final Drawable upArrow = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_arrow_back_ios_24, null);
        upArrow.setColorFilter(getColor(R.color.white),PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setTitle((CharSequence)"");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profileView();
        toolbar_text.setText("My Profile");
        show_profile_ll.setVisibility(View.GONE);
        edit_profile_ll.setVisibility(View.GONE);
        save_update_btn.setVisibility(View.GONE);
   //     subscription_ll.setVisibility(View.GONE);

        profile_edit.setOnClickListener(view -> {
            toolbar_text.setText("Edit Profile");
            show_profile_ll.setVisibility(View.GONE);
            edit_profile_ll.setVisibility(View.VISIBLE);
            save_update_btn.setVisibility(View.VISIBLE);
        });

//        subscription_ll.setOnClickListener(view -> {
//            Intent intent_subscribe_plan = new Intent(ProfileActivity.this, SubscriptionPlanActivity.class);
//            startActivity(intent_subscribe_plan);
//        });
        save_update_btn.setOnClickListener(view->{
            if(( !old_city.equals(user_city_edit.getText().toString()) || !(old_name.equals(name_edit.getText().toString())))){
                profileUpdate();
            }

        });
    }

    private void profileUpdate() {

        getApiService().profileUpdateApi(name_edit.getText().toString(),user_city_edit.getText().toString(),userId).enqueue(new Callback<ProfileUpdateResponse>() {
            @Override
            public void onResponse(Call<ProfileUpdateResponse> call, Response<ProfileUpdateResponse> response) {
                Toast.makeText(ProfileActivity.this, "Profile Update Successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ProfileUpdateResponse> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void profileView() {
        getApiService().profileApi(userId).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                ProfileResponse profileResponse = response.body();
                if(response.code()==200 && profileResponse.getMsg().equals("success")){

                    profileLists = new ArrayList<>(Arrays.asList(profileResponse.getResult()));

                    if(profileLists.get(0).getName().equals("")){
                        username_txt.setText(profileLists.get(0).getUserphoneno());
                    }else username_txt.setText(profileLists.get(0).getName());
                    System.out.println("----------------------->"+profileLists.get(0).getName());
                    userEmail_txt.setText(profileLists.get(0).getUseremail());
                     username_edit.setText(profileLists.get(0).getUsername());
                    name_edit.setText(profileLists.get(0).getName());
                    userEmail_edit.setText(profileLists.get(0).getUseremail());
                    userMobile_no_edit.setText(profileLists.get(0).getUserphoneno());
                    user_city_edit.setText(profileLists.get(0).getUseraddress());
                    old_name=profileLists.get(0).getName();
                    old_city=profileLists.get(0).getUseraddress();
                    progressBar.setVisibility(View.GONE);
                    show_profile_ll.setVisibility(View.VISIBLE);
               //     subscription_ll.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

            }
        });
    }


    private RestAPI getApiService() {
        return RetrofitClient.getRetrofitClient(Constants.API_URL).create(RestAPI.class);
    }

    @Override
    public void onBackPressed() {

        if(toolbar_text.getText().toString() == "My Profile"){
            startActivity(new Intent(ProfileActivity.this, BlankActivity.class));
            return;
        }else if(toolbar_text.getText().toString() == "Edit Profile" && !doubleBackToExitPressedOnce){
            show_profile_ll.setVisibility(View.VISIBLE);
            edit_profile_ll.setVisibility(View.GONE);
            save_update_btn.setVisibility(View.GONE);
            this.doubleBackToExitPressedOnce = true;
        }else {
            startActivity(new Intent(ProfileActivity.this, BlankActivity.class));
            return;

        }
    }
}