package com.infowebmentsolution.ghosh.clickforflick.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.common.internal.SignInButtonImpl;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.infowebmentsolution.ghosh.clickforflick.BlankActivity;
import com.infowebmentsolution.ghosh.clickforflick.R;
import com.infowebmentsolution.ghosh.clickforflick.Response.ForgotPasswordResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.GoogleSignInResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.LoginWithOtpResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.OtpVerificationResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.PincodeResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.SignInResponse;
import com.infowebmentsolution.ghosh.clickforflick.Utils.Constants;
import com.infowebmentsolution.ghosh.clickforflick.Utils.RestAPI;
import com.infowebmentsolution.ghosh.clickforflick.Utils.RetrofitClient;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity  implements OnConnectionFailedListener {


    private static final int RC_SIGN_IN = 100   ;
    AppCompatButton signIn_button;
    AppCompatButton forgot_button;
    AppCompatButton send_otp_button;
    AppCompatButton sign_in_with_otp_button;
    TextInputEditText user_email;
    TextInputEditText user_password;
    TextInputEditText user_mobile_number;
    TextInputEditText user_given_otp_number;

    TextInputLayout emailTextInputLayout;
    TextInputLayout mobileTextInputLayout;
    TextInputLayout passwordTextInputLayout;
    TextInputLayout otpTextInputLayout;
    ImageView close_signIn;
    TextView signInWithOtp;
    TextView forgot_password;
    TextView sign_in_text;
    TextView sign_in_text1;
    TextView sign_in_text2;
    LinearLayout signInWithGoogle;
    LinearLayout register;
    LinearLayout view_ll;
    SignInButtonImpl signInButton;
    SharedPreferences sharedPreferences ;
    String userID;
    String email;
    String password;
    private String android_id ;
    private String result_ok;
    private String pincode;

    private GoogleSignInClient mGoogleSignInClient;
    private FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        SharedPreferences editor = getSharedPreferences(Constants.LOG_IN_DATA, 0);
        userID = editor.getString(Constants.USER_ID, "none");

        //Edit Text
        user_email = findViewById(R.id.sign_in_email);
        user_mobile_number=findViewById(R.id.sign_in_mobile);
        user_password = findViewById(R.id.sign_in_password);
        user_given_otp_number=findViewById(R.id.sign_in_otp);
        
        //TextInputLayout
        emailTextInputLayout=findViewById(R.id.sign_in_txt_email);
        passwordTextInputLayout=findViewById(R.id.sign_in_txt_password);
        mobileTextInputLayout=findViewById(R.id.sign_in_txt_mobile);
        otpTextInputLayout=findViewById(R.id.sign_in_txt_otp);
        forgot_password=findViewById(R.id.sign_in_forgotpassword);
        
        
        register = findViewById(R.id.sign_in_register_with_us_ll);
        signInWithOtp=findViewById(R.id.sign_in_with_otp);
        signInWithGoogle=findViewById(R.id.sign_in_with_google_ll);
        signIn_button=findViewById(R.id.sign_in_button);
        forgot_button=findViewById(R.id.sign_in_forgot_button);
        send_otp_button=findViewById(R.id.sign_in_send_otp_enter);
        sign_in_with_otp_button=findViewById(R.id.sign_in_send_otp_button);
        close_signIn=findViewById(R.id.sign_in_close);

        
        sign_in_text=findViewById(R.id.sign_in_text);
        sign_in_text1 =findViewById(R.id.sign_in_sub_text);
        sign_in_text2 =findViewById(R.id.sign_in_sub_text2);
        view_ll=findViewById(R.id.sign_in_view_ll);
        signInButton=findViewById(R.id.sign_in_google);
        email=user_email.getText().toString();
        password=user_password.getText().toString();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        close_signIn.setOnClickListener(view -> {
            Intent intent_close = new Intent(SignInActivity.this, BlankActivity.class);
            Toast.makeText(this, "Welcome to home", Toast.LENGTH_SHORT).show();
            startActivity(intent_close);
        });



        if(ActivityCompat.checkSelfPermission(SignInActivity.this
        , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            getLocation();
        }else {
            ActivityCompat.requestPermissions(SignInActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }
        if (ContextCompat.checkSelfPermission(SignInActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(SignInActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(SignInActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }else{
                ActivityCompat.requestPermissions(SignInActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
        forgot_password.setOnClickListener(view->{
            sign_in_text.setVisibility(View.GONE);
            sign_in_text1.setVisibility(View.GONE);
            view_ll.setVisibility(View.GONE);
            user_password.setVisibility(View.GONE);
            passwordTextInputLayout.setVisibility(View.GONE);
            forgot_password.setVisibility(View.GONE);
            sign_in_text2.setVisibility(View.GONE);
            signInWithOtp.setVisibility(View.GONE);
            signInWithGoogle.setVisibility(View.GONE);
            signIn_button.setVisibility(View.GONE);
            send_otp_button.setVisibility(View.GONE);
            user_email.setVisibility(View.GONE);
            emailTextInputLayout.setVisibility(View.GONE);
            otpTextInputLayout.setVisibility(View.GONE);
            sign_in_with_otp_button.setVisibility(View.GONE);
            mobileTextInputLayout.setVisibility(View.VISIBLE);
            forgot_button.setVisibility(View.VISIBLE);
        });

        user_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length_name = user_email.length();

                if(length_name >=6 && user_email.getText().toString().indexOf("@") != 1 && user_email.getText().toString().contains(".com")==false){
                    emailTextInputLayout.setHelperTextEnabled(true);
                    emailTextInputLayout.setHelperText("Invalid email id");
                }else emailTextInputLayout.setHelperTextEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        user_mobile_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length_no = user_mobile_number.length();
                if(length_no <8){
                    mobileTextInputLayout.setHelperTextEnabled(true);
                    mobileTextInputLayout.setHelperText("Invalid Mobile Number");
                }else {
                    mobileTextInputLayout.setHelperTextEnabled(false);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        user_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length_no = user_password.length();
                if(length_no <8){
                    emailTextInputLayout.setHelperTextEnabled(true);
                    passwordTextInputLayout.setHelperText("Invalid Password.Password must be minimum of 8 characters");
                }else {
                    passwordTextInputLayout.setHelperTextEnabled(false);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        forgot_button.setOnClickListener(view -> {
            if(user_mobile_number.getText().length() < 10) {
                    mobileTextInputLayout.setHelperTextEnabled(true);
                    mobileTextInputLayout.setHelperText("Invalid Mobile Number");
                }else forgotPasswordApiCall();
        });

        signIn_button.setOnClickListener(view -> {
            if(TextUtils.isEmpty(SignInActivity.this.user_email.getText().toString())){
                emailTextInputLayout.setHelperTextEnabled(true);
                emailTextInputLayout.setHelperText("Required");
            }else if(user_email.getText().length() <=6 && email.contains("@") && email.contains(".com")==false){
                emailTextInputLayout.setHelperTextEnabled(true);
                emailTextInputLayout.setHelperText("Invalid Email id");
            }else if(user_password.getText().toString().length()<8){
                passwordTextInputLayout.setHelperTextEnabled(true);
                passwordTextInputLayout.setHelperText("Invalid Password.Password must be minimum of 8 characters");
            }else logIn();

        });

        //SIGN IN WITH OTP
        signInWithOtp.setOnClickListener(view -> {
            sign_in_text.setVisibility(View.GONE);
            sign_in_text1.setVisibility(View.GONE);
            view_ll.setVisibility(View.GONE);
            user_password.setVisibility(View.GONE);
            passwordTextInputLayout.setVisibility(View.GONE);
            forgot_password.setVisibility(View.GONE);
            sign_in_text2.setVisibility(View.GONE);
            signInWithOtp.setVisibility(View.GONE);
            signInWithGoogle.setVisibility(View.GONE);
            signIn_button.setVisibility(View.GONE);
            user_email.setVisibility(View.GONE);
            emailTextInputLayout.setVisibility(View.GONE);
            mobileTextInputLayout.setVisibility(View.VISIBLE);
            mobileTextInputLayout.setVisibility(View.VISIBLE);
            otpTextInputLayout.setVisibility(View.GONE);
            forgot_button.setVisibility(View.GONE);
            send_otp_button.setVisibility(View.VISIBLE);
        });

        send_otp_button.setOnClickListener(view -> {
            if(user_mobile_number.getText().length() <10) {
                mobileTextInputLayout.setHelperTextEnabled(true);
                mobileTextInputLayout.setHelperText("Invalid Mobile Number");
            }else {
                sign_in_text.setVisibility(View.GONE);
                sign_in_text1.setVisibility(View.GONE);
                view_ll.setVisibility(View.GONE);
                user_password.setVisibility(View.GONE);
                passwordTextInputLayout.setVisibility(View.GONE);
                forgot_password.setVisibility(View.GONE);
                sign_in_text2.setVisibility(View.GONE);
                signInWithOtp.setVisibility(View.GONE);
                signInWithGoogle.setVisibility(View.GONE);
                signIn_button.setVisibility(View.GONE);
                user_email.setVisibility(View.GONE);
                emailTextInputLayout.setVisibility(View.GONE);
                mobileTextInputLayout.setVisibility(View.GONE);
                mobileTextInputLayout.setVisibility(View.GONE);
                otpTextInputLayout.setVisibility(View.VISIBLE);
                sign_in_with_otp_button.setVisibility(View.VISIBLE);
                forgot_button.setVisibility(View.GONE);
                send_otp_button.setVisibility(View.GONE);

                signInWithOTPApiCall();

                sign_in_with_otp_button.setOnClickListener(view1 -> {
                        verifyOTP();
                });
            }
        });

        //GOOGLE SIGNIN
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        signInButton.setOnClickListener(view -> {
            googleSignIn();
        });


        //NEW USER
        register.setOnClickListener(view -> {
            Intent intent_sign_up = new Intent(SignInActivity.this,SignUpActivity.class);
            startActivity(intent_sign_up);
        });

    }

    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                Location location = task.getResult();
                if(location != null){
                    Geocoder geocoder = new Geocoder(SignInActivity.this
                    , Locale.getDefault());
                    try {
                        List<Address> addressesList = geocoder.getFromLocation(
                                location.getLatitude(),location.getLongitude(),1
                        );
                        System.out.println("------------------------pincode---------------------"+addressesList.get(0).getPostalCode());
                        SharedPreferences.Editor editor = SignInActivity.this.getSharedPreferences(Constants.LOG_IN_DATA,MODE_PRIVATE).edit();
                        editor.putString(Constants.PINCODE, addressesList.get(0).getPostalCode());
                        editor.apply();
                        editor.commit();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void verifyOTP() {
        getApiService().otpVerficationAPi(user_mobile_number.getText().toString(),user_given_otp_number.getText().toString(),android_id).enqueue(new Callback<OtpVerificationResponse>() {
            @Override
            public void onResponse(Call<OtpVerificationResponse> call, Response<OtpVerificationResponse> response) {

                if(response.code()==200 && response.body().getResult().equals("success")){
                    SharedPreferences.Editor editor = SignInActivity.this.getSharedPreferences(Constants.LOG_IN_DATA,MODE_PRIVATE).edit();
                    editor.putString(Constants.LOG_IN_DATA, "yes");
                    editor.putString(Constants.USER_ID,response.body().getuserID());
                    editor.putString(Constants.DEVICE_ID,android_id);
                    editor.apply();
                    editor.commit();
                    loadPinCode();
//                    Intent intent_login_with_otp = new Intent(SignInActivity.this, BlankActivity.class);
//                    startActivity(intent_login_with_otp);

                }
            }

            @Override
            public void onFailure(Call<OtpVerificationResponse> call, Throwable t) {

            }
        });
    }

    private void signInWithOTPApiCall() {


        getApiService().loginWithOtpApi(user_mobile_number.getText().toString()).enqueue(new Callback<LoginWithOtpResponse>() {
            @Override
            public void onResponse(Call<LoginWithOtpResponse> call, Response<LoginWithOtpResponse> response) {
                if(response.code()==200 && response.body().equals("success")){

                    loadPinCode();
                }
            }

            @Override
            public void onFailure(Call<LoginWithOtpResponse> call, Throwable t) {

            }
        });

    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
                insertDetailsOfGoogleSignIntoServer(personName,personEmail,personId);
            }


        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void insertDetailsOfGoogleSignIntoServer(String personName, String personEmail, String personId) {

        getApiService().googleSignInApi(personName,personEmail,personId,android_id).enqueue(new Callback<GoogleSignInResponse>() {
            @Override
            public void onResponse(Call<GoogleSignInResponse> call, Response<GoogleSignInResponse> response) {
                if(response.code()==200){
                    Toast.makeText(SignInActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = SignInActivity.this.getSharedPreferences(Constants.LOG_IN_DATA,MODE_PRIVATE).edit();
                    editor.putString(Constants.LOG_IN_DATA, "yes");
                    editor.putString(Constants.USER_ID,response.body().getUserID());
                    editor.putString(Constants.DEVICE_ID,android_id);
                    editor.putString(Constants.GOOGLE_SIGN_IN,"yes");
                    editor.apply();
                    editor.commit();
                    loadPinCode();

//                    Intent intent_login = new Intent(SignInActivity.this, BlankActivity.class);
//                    startActivity(intent_login);
                }
            }

            @Override
            public void onFailure(Call<GoogleSignInResponse> call, Throwable t) {

            }
        });
    }

    private void forgotPasswordApiCall() {

        getApiService().forgotPasswordApi(user_mobile_number.getText().toString()).enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                SharedPreferences.Editor editor = SignInActivity.this.getSharedPreferences(Constants.LOG_IN_DATA,MODE_PRIVATE).edit();
                editor.putString(Constants.LOG_IN_DATA, "yes");
                editor.commit();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {

            }
        });
    }

    private void logIn() {

        getApiService().signInApi(user_email.getText().toString(),user_password.getText().toString(),android_id).enqueue(new Callback<SignInResponse>() {
            @Override
            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                if(response.code() == 200 && response.body().getResult().equals("success")){
                    SharedPreferences.Editor editor = SignInActivity.this.getSharedPreferences(Constants.LOG_IN_DATA,MODE_PRIVATE).edit();
                    editor.putString(Constants.LOG_IN_DATA, "yes");
                    editor.putString(Constants.USER_ID,response.body().getUser());
                    editor.putString(Constants.DEVICE_ID,android_id);
                    editor.apply();
                    editor.commit();

                    loadPinCode();
//                    Intent intent_login = new Intent(SignInActivity.this, BlankActivity.class);
//                    startActivity(intent_login);

                    /** GoogleMap Integration */
                }
            }

            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {

            }
        });
    }

    private void loadPinCode() {

        getLocation();
        SharedPreferences editor = getSharedPreferences(Constants.LOG_IN_DATA, 0);
        pincode = editor.getString(Constants.PINCODE,"0000000");
        getApiService().PincodeApi(editor.getString(Constants.USER_ID,"none"),pincode).enqueue(new Callback<PincodeResponse>() {
            @Override
            public void onResponse(Call<PincodeResponse> call, Response<PincodeResponse> response) {
                if(response.code()==200 && response.body().getResult().equals("1")){
                    Intent intent_login = new Intent(SignInActivity.this, BlankActivity.class);
                   startActivity(intent_login);
                }else {
                    Intent intent_login = new Intent(SignInActivity.this, BlankActivity.class);
                    startActivity(intent_login);
                }

            }

            @Override
            public void onFailure(Call<PincodeResponse> call, Throwable t) {
                Intent intent_login = new Intent(SignInActivity.this, BlankActivity.class);
                startActivity(intent_login);
            }
        });
    }

    private RestAPI getApiService() {
        return RetrofitClient.getRetrofitClient(Constants.API_URL).create(RestAPI.class);
    }

    @Override
    public void onConnectionFailed(@NonNull @NotNull ConnectionResult connectionResult) {

    }
}