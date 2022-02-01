package com.infowebmentsolution.ghosh.clickforflick.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

import com.infowebmentsolution.ghosh.clickforflick.BlankActivity;
import com.infowebmentsolution.ghosh.clickforflick.R;
import com.infowebmentsolution.ghosh.clickforflick.Response.isLoginCheckResponse;
import com.infowebmentsolution.ghosh.clickforflick.Utils.Constants;
import com.infowebmentsolution.ghosh.clickforflick.Utils.RestAPI;
import com.infowebmentsolution.ghosh.clickforflick.Utils.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LanguageActivity extends AppCompatActivity {
    TextView all,eng,ben,hin,kann;
    Context context;
    private String userID;
    private String android_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        all = findViewById(R.id.lang_all);
        eng = findViewById(R.id.lang_eng);
        ben = findViewById(R.id.lang_ben);
        hin = findViewById(R.id.lang_hindi);
        kann = findViewById(R.id.lang_kan);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.LOG_IN_DATA,0);
        String lang = sharedPreferences.getString(Constants.LANGUAGE,"all");
        this.userID = sharedPreferences.getString(Constants.USER_ID, "none");
        //----GET ANDROID DEVICE ID--------
        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);


        switch (lang) {
            case "all":
                all.setBackgroundColor(getResources().getColor(R.color.primaryborder));
                all.setTextColor(getResources().getColor(R.color.white));
                ben.setBackgroundColor(getResources().getColor(R.color.primaryBackGround));
                hin.setBackgroundColor(getResources().getColor(R.color.primaryBackGround));
                eng.setBackgroundColor(getResources().getColor(R.color.primaryBackGround));
                kann.setBackgroundColor(getResources().getColor(R.color.primaryBackGround));
                break;
            case "ben":
                all.setBackgroundColor(getResources().getColor(R.color.primaryBackGround));
                ben.setBackgroundColor(getResources().getColor(R.color.primaryborder));
                ben.setTextColor(getResources().getColor(R.color.white));
                hin.setBackgroundColor(getResources().getColor(R.color.primaryBackGround));
                eng.setBackgroundColor(getResources().getColor(R.color.primaryBackGround));
                kann.setBackgroundColor(getResources().getColor(R.color.primaryBackGround));
                break;
            case "en":
                all.setBackgroundColor(getResources().getColor(R.color.primaryBackGround));
                eng.setBackgroundColor(getResources().getColor(R.color.primaryborder));
                eng.setTextColor(getResources().getColor(R.color.white));
                ben.setBackgroundColor(getResources().getColor(R.color.primaryBackGround));
                hin.setBackgroundColor(getResources().getColor(R.color.primaryBackGround));
                kann.setBackgroundColor(getResources().getColor(R.color.primaryBackGround));
                break;
            case "hin":
                all.setBackgroundColor(getResources().getColor(R.color.primaryBackGround));
                ben.setBackgroundColor(getResources().getColor(R.color.primaryBackGround));
                eng.setBackgroundColor(getResources().getColor(R.color.primaryBackGround));
                hin.setBackgroundColor(getResources().getColor(R.color.primaryborder));
                hin.setTextColor(getResources().getColor(R.color.white));
                kann.setBackgroundColor(getResources().getColor(R.color.primaryBackGround));
                break;
            case "kan":
                all.setBackgroundColor(getResources().getColor(R.color.primaryBackGround));
                ben.setBackgroundColor(getResources().getColor(R.color.primaryBackGround));
                hin.setBackgroundColor(getResources().getColor(R.color.primaryBackGround));
                eng.setBackgroundColor(getResources().getColor(R.color.primaryBackGround));
                kann.setBackgroundColor(getResources().getColor(R.color.primaryborder));
                kann.setTextColor(getResources().getColor(R.color.white));
                break;
            default:
                break;
        }

        all.setOnClickListener(view -> {
            SharedPreferences preferences = getSharedPreferences(Constants.LOG_IN_DATA, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Constants.LANGUAGE,"all");
            editor.apply();
      

            Intent intent = new Intent(LanguageActivity.this, BlankActivity.class);
            startActivity(intent);
        });
        ben.setOnClickListener(view -> {
            SharedPreferences preferences = getSharedPreferences(Constants.LOG_IN_DATA, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Constants.LANGUAGE,"ben");
            editor.apply();

            Intent intent = new Intent(LanguageActivity.this, BlankActivity.class);
            startActivity(intent);

        });
        hin.setOnClickListener(view -> {
            SharedPreferences preferences = getSharedPreferences(Constants.LOG_IN_DATA, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Constants.LANGUAGE,"hin");
            editor.apply();
            Intent intent = new Intent(LanguageActivity.this, BlankActivity.class);
            startActivity(intent);

        });
        eng.setOnClickListener(view -> {
            SharedPreferences preferences = getSharedPreferences(Constants.LOG_IN_DATA, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Constants.LANGUAGE,"en");
            editor.apply();
            Intent intent = new Intent(LanguageActivity.this, BlankActivity.class);
            startActivity(intent);

        });
        kann.setOnClickListener(view -> {
            SharedPreferences preferences = getSharedPreferences(Constants.LOG_IN_DATA, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Constants.LANGUAGE,"kan");
            editor.apply();
            Intent intent = new Intent(LanguageActivity.this, BlankActivity.class);
            startActivity(intent);

        });
        if(!userID.equals("none")) isUserLoginCheck();
    }
    private static RestAPI getApiService() {
        return RetrofitClient.getRetrofitClient(Constants.API_URL).create(RestAPI.class);
    }
    private void isUserLoginCheck() {

        getApiService().isLoginCheckApi(this.userID, this.android_id).enqueue(new Callback<isLoginCheckResponse>() {
            @Override
            public void onResponse(Call<isLoginCheckResponse> call, Response<isLoginCheckResponse> response) {
                if (response.code() == 200 && response.body().getResult().equals("0")) {
                    SharedPreferences.Editor editor = LanguageActivity.this.getSharedPreferences(Constants.LOG_IN_DATA, 0).edit();
                    editor.putString(Constants.USER_ID, "none");
                    editor.apply();
                    editor.commit();
                    Intent intent_logout = new Intent(LanguageActivity.this, SignInActivity.class);
                    startActivity(intent_logout);
                }

            }

            @Override
            public void onFailure(Call<isLoginCheckResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LanguageActivity.this, BlankActivity.class);
        startActivity(intent);
    }
}