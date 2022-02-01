package com.infowebmentsolution.ghosh.clickforflick.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.infowebmentsolution.ghosh.clickforflick.BlankActivity;
import com.infowebmentsolution.ghosh.clickforflick.R;
import com.infowebmentsolution.ghosh.clickforflick.Utils.Constants;

public class SplashScreenActivity extends AppCompatActivity {

    RelativeLayout relativeLayout;
    ImageView logo_image;
    Animation logoAnimation,layoutAnimation;
    String userid;
    String cat_size="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        relativeLayout = findViewById(R.id.splash_rel);
        logo_image=findViewById(R.id.splash_logo);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.LOG_IN_DATA,MODE_PRIVATE);
        userid = sharedPreferences.getString(Constants.USER_ID,"none");

        logoAnimation = AnimationUtils.loadAnimation(SplashScreenActivity.this,R.anim.slide_animation);
        layoutAnimation=AnimationUtils.loadAnimation(SplashScreenActivity.this,R.anim.bottom_to_top);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
           relativeLayout.setVisibility(View.VISIBLE);
           relativeLayout.setAnimation(layoutAnimation);
           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                   logo_image.setVisibility(View.VISIBLE);
                   logo_image.setAnimation(logoAnimation);
               }
           },900);
            }
        },300);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(userid.equals("none")){
                    Intent intent= new Intent(SplashScreenActivity.this,SignInActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent_main= new Intent(SplashScreenActivity.this, BlankActivity.class);
                    startActivity(intent_main);
                }
            }
        },5000);
    }
}