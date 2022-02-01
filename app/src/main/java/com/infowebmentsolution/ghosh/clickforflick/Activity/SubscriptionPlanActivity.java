package com.infowebmentsolution.ghosh.clickforflick.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.infowebmentsolution.ghosh.clickforflick.Adapter.SubscriptionPlanAdapter;
import com.infowebmentsolution.ghosh.clickforflick.BlankActivity;
import com.infowebmentsolution.ghosh.clickforflick.Model.PaymentinfoList;
import com.infowebmentsolution.ghosh.clickforflick.Model.SubscriptionPlansList;
import com.infowebmentsolution.ghosh.clickforflick.R;
import com.infowebmentsolution.ghosh.clickforflick.Response.PaymentinfoResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.SubscibeCheckResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.SubscriptionPlanResponse;
import com.infowebmentsolution.ghosh.clickforflick.Utils.Constants;
import com.infowebmentsolution.ghosh.clickforflick.Utils.RestAPI;
import com.infowebmentsolution.ghosh.clickforflick.Utils.RetrofitClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscriptionPlanActivity extends AppCompatActivity implements SubscriptionPlanAdapter.ClickedOnItemListener {

    private ArrayList<SubscriptionPlansList> lists;
    RecyclerView recyclerView;
    Toolbar toolbar;
    ScrollView scrollView;
    ScrollView scrollView_Lin;
    TextView pay_dt,ext_dt,pay_amount;
    TextView tooltext;
    private SubscriptionPlanAdapter subscriptionPlanAdapter;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_plan);
        scrollView=findViewById(R.id.subscription_scroll);
        tooltext = findViewById(R.id.subscription_tooltext);
        scrollView_Lin=findViewById(R.id.subscription_scroll_lin);
        pay_dt=findViewById(R.id.subscription_pay_date);
        ext_dt=findViewById(R.id.subscription_exp_date);
        pay_amount=findViewById(R.id.subscription_pay_amount);
        subscriptionPlanAdapter= new SubscriptionPlanAdapter(this);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.LOG_IN_DATA,0);
        userId=sharedPreferences.getString(Constants.USER_ID,"none");

        if(userId.equals("none")){
            itemViews();
        }else {

            subscription(userId);
        }
    }

    private void itemViews() {
        tooltext.setText("Choose Your Plan");
        recyclerView = findViewById(R.id.subscription_plan_rec);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        loadSubscriptionPlan();
    }
    private void subscription(String user_Id) {
        getApiService().SubscibeCheckApi(user_Id).enqueue(new Callback<SubscibeCheckResponse>() {
            @Override
            public void onResponse(Call<SubscibeCheckResponse> call, Response<SubscibeCheckResponse> response) {
                if(response.code()==200 &&( (Objects.requireNonNull(response.body()).getResult().equals("0")) || (Objects.requireNonNull(response.body()).getResult().equals("2")))){

                    itemViews();
                }else  if(response.code()==200 && Objects.requireNonNull(response.body()).getResult().equals("1")){

                    loadPaymentInfo();

                    }
            }

            @Override
            public void onFailure(Call<SubscibeCheckResponse> call, Throwable t) {

            }
        });
    }

    private void loadPaymentInfo() {
        getApiService().PaymentinfoApi(userId).enqueue(new Callback<PaymentinfoResponse>() {
            @Override
            public void onResponse(Call<PaymentinfoResponse> call, Response<PaymentinfoResponse> response) {

                if(response.code()==200 && response.body().getMsg().equals("payment info")){
                    ArrayList<PaymentinfoList> paymentinfoLists = new ArrayList<>(Arrays.asList(response.body().getResult()));
                    tooltext.setText("Your Subscription Plan");
                    scrollView_Lin.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.GONE);
                    pay_dt.setText(paymentinfoLists.get(0).getDate());
                    pay_amount.setText(paymentinfoLists.get(0).getAmount());
                    ext_dt.setText(paymentinfoLists.get(0).getExpirationdate());
                }else{
                    itemViews();
                }
            }

            @Override
            public void onFailure(Call<PaymentinfoResponse> call, Throwable t) {

            }
        });
    }

    private void loadSubscriptionPlan() {

        getApiService().subscriptionPlanApi().enqueue(new Callback<SubscriptionPlanResponse>() {
            @Override
            public void onResponse(Call<SubscriptionPlanResponse> call, Response<SubscriptionPlanResponse> response) {
                SubscriptionPlanResponse subscriptionPlanResponse = response.body();
                lists = new ArrayList<>(Arrays.asList(subscriptionPlanResponse.getResult()));
                subscriptionPlanAdapter.SetData(lists);
                recyclerView.setAdapter(subscriptionPlanAdapter);
                scrollView_Lin.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<SubscriptionPlanResponse> call, Throwable t) {

            }
        });
    }

    private RestAPI getApiService() {
        return RetrofitClient.getRetrofitClient(Constants.API_URL).create(RestAPI.class);
    }

    @Override
    public void ClickedItem(SubscriptionPlansList list) {
        if(list.getSubscriptionstatus().equals("0")){
            Toast.makeText(SubscriptionPlanActivity.this, "This plan is not available.Choose another plan.", Toast.LENGTH_SHORT).show();
        }else {
            if(userId.equals("none")){
                Intent intent_go_next_signup = new Intent(SubscriptionPlanActivity.this,SignInActivity.class);
                startActivity(intent_go_next_signup);
            }else {
                Intent intent_go_next = new Intent(SubscriptionPlanActivity.this,PaymentActivity.class);
                intent_go_next.putExtra("amount",list.getSubscriptionamount());
                intent_go_next.putExtra("duration",list.getSubscriptionduration());
                intent_go_next.putExtra("subid",list.getSubscriptionid());
                startActivity(intent_go_next);
            }
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SubscriptionPlanActivity.this,BlankActivity.class));
    }
}