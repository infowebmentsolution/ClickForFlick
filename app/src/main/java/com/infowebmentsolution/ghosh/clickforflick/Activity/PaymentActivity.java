package com.infowebmentsolution.ghosh.clickforflick.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.infowebmentsolution.ghosh.clickforflick.BlankActivity;
import com.infowebmentsolution.ghosh.clickforflick.R;
import com.infowebmentsolution.ghosh.clickforflick.Response.PaymentResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.SubscibeCheckResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.SubscriptionPlanResponse;
import com.infowebmentsolution.ghosh.clickforflick.Utils.Constants;
import com.infowebmentsolution.ghosh.clickforflick.Utils.RestAPI;
import com.infowebmentsolution.ghosh.clickforflick.Utils.RetrofitClient;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {
    private static final String TAG = PaymentActivity.class.getSimpleName();
    AppCompatButton button;
    TextView rzp_amount;
//    String promocode_val;
//    String showDiscountText="";
    int Flag =0;
//    String promoStr;
    Intent getAmount;
//    Intent intentdata;
    String userId;
    String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        button = (AppCompatButton) findViewById(R.id.payment_btn);
        rzp_amount =(TextView) findViewById(R.id.payment_amount);
        getAmount = getIntent();
        amount =getIntent().getStringExtra("amount");
        rzp_amount.setText(getAmount.getStringExtra("amount"));
        SharedPreferences editor = getSharedPreferences(Constants.LOG_IN_DATA, 0);
        this.userId = editor.getString(Constants.USER_ID, "none");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });
    }

    private void startPayment() {

        Checkout checkout = new Checkout();
        checkout.setKeyID("<YOUR_KEY_ID>");
        float inramount = Float.parseFloat(rzp_amount.getText().toString());
        inramount = (float) (inramount * 100.0);
        String amountInStr = Float.toString(inramount);
        checkout.setImage(R.drawable.logo);
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Razorpay Corp");
            options.put("description", "Demoing Charges");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("theme.color", "#f4a460");
            options.put("amount", amountInStr);
            JSONObject preFill = new JSONObject();
            preFill.put("email", "clickforflick@gmail.com");
            preFill.put("contact", "0123456789");
            options.put("prefill", preFill);
            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    /**
     * The name of the function has to be
     * onPaymentSuccess
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     **/

    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            paymentDetailsInsert(razorpayPaymentID,userId,getIntent().getStringExtra("amount"),getIntent().getStringExtra("duration"));
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }



    @Override
    public void onPaymentError(int i, String s) {

    }

    private void paymentDetailsInsert(String pid,String userId1, String amount_, String durartion) {


        getInterface().PaymentAPI(pid,userId1,amount_,durartion).enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                if(response.code() == 200) {
                    assert response.body() != null;
                    if ((response.body().getMsg().equals("success to payment update premium user!"))) {
                        subscription(userId);
                    }
                }
            }

            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {

            }
        });
    }

    private void subscription(String user_Id) {
        getInterface().SubscibeCheckApi(user_Id).enqueue(new Callback<SubscibeCheckResponse>() {
            @Override
            public void onResponse(Call<SubscibeCheckResponse> call, Response<SubscibeCheckResponse> response) {
             if(response.code() == 200) {
                 assert response.body() != null;
                 if (response.body().getResult().equals("1")) {
                     Toast.makeText(PaymentActivity.this, "You are successfully subscribed this plan", Toast.LENGTH_SHORT).show();
                     Intent intent = new Intent(PaymentActivity.this, SubscriptionPlanActivity.class);
                     startActivity(intent);
                 }else if(Objects.requireNonNull(response.body()).getMsg().equals("Not buy any plan")){

                 }
             }
            }

            @Override
            public void onFailure(Call<SubscibeCheckResponse> call, Throwable t) {

            }
        });
    }

    private static RestAPI getInterface(){
        return RetrofitClient.getRetrofitClient(Constants.API_URL).create(RestAPI.class);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(PaymentActivity.this,SubscriptionPlanActivity.class));
    }
}