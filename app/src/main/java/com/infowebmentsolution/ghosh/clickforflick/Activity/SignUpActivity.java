package com.infowebmentsolution.ghosh.clickforflick.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import com.google.android.material.textfield.TextInputLayout;
import com.infowebmentsolution.ghosh.clickforflick.BlankActivity;
import com.infowebmentsolution.ghosh.clickforflick.R;
import com.infowebmentsolution.ghosh.clickforflick.Response.SignUpResponse;
import com.infowebmentsolution.ghosh.clickforflick.Utils.Constants;
import com.infowebmentsolution.ghosh.clickforflick.Utils.RestAPI;
import com.infowebmentsolution.ghosh.clickforflick.Utils.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    ImageView close_layout;
    EditText username;
    EditText name;
    EditText emailId;
    EditText phonon;
    EditText password;
    AppCompatButton signUp_button;
    String getUser_name;
    String get_name;
    String getUser_email;
    String getUser_phonon;
    String getUser_password;
    TextInputLayout txt_username;
    TextInputLayout txt_name;
    TextInputLayout txt_email;
    TextInputLayout txt_phone;
    TextInputLayout txt_password;
    LinearLayout signIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //----FIND VIEW BY ID------
        username = findViewById(R.id.sign_up_user_name);
        name = findViewById(R.id.sign_up_name);
        emailId=findViewById(R.id.sign_up_email);
        phonon =findViewById(R.id.sign_up_mobile_no);
        password=findViewById(R.id.sign_up_password);
        signUp_button = findViewById(R.id.sign_up_button);
        close_layout= findViewById(R.id.sign_up_close);
        txt_username = findViewById(R.id.sign_up_txt_user_name);
        txt_name=findViewById(R.id.sign_up_txt_name);
        txt_email=findViewById(R.id.sign_up_txt_user_email);
        txt_phone=findViewById(R.id.sign_up_txt_user_mob_no);
        txt_password=findViewById(R.id.sign_up_txt_user_password);
        signIn = findViewById(R.id.sign_up_login_with_us);
        getUser_name = username.getText().toString();
        get_name = name.getText().toString();
        getUser_phonon = phonon.getText().toString();
        getUser_email = emailId.getText().toString();
        getUser_password=password.getText().toString();


        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length_user_name = username.length();
                if(length_user_name >0){
                    txt_username.setHelperTextEnabled(true);
                    txt_username.setHelperText("Required");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(username.getText().toString().length()>0){
                    txt_username.setHelperTextEnabled(false);
                }else {
                    txt_username.setHelperTextEnabled(true);
                    txt_username.setHelperText("Required");
                }
                int length_name = name.length();
                if(length_name >0){
                    txt_name.setHelperTextEnabled(true);
                    txt_name.setHelperText("Required");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        emailId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(username.getText().toString().length()>0){
                    txt_username.setHelperTextEnabled(false);
                }else {
                    txt_username.setHelperTextEnabled(true);
                }
                if(name.getText().toString().length()>0){
                    txt_name.setHelperTextEnabled(false);
                }else txt_name.setHelperTextEnabled(true);
                int length_name = emailId.length();
                if(length_name >=6 && emailId.getText().toString().indexOf("@") != 1 && !(emailId.getText().toString().contains(".com"))){
                    txt_email.setHelperTextEnabled(true);
                    txt_email.setHelperText("Invalid email id");
                }else txt_email.setHelperTextEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        phonon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(username.getText().toString().length()>0){
                    txt_username.setHelperTextEnabled(false);
                }else {
                    txt_username.setHelperTextEnabled(true);
                 //   txt_username.setHelperText("Required");
                }
                if(name.getText().toString().length()>0){
                    txt_name.setHelperTextEnabled(false);
                }else {
                    txt_name.setHelperTextEnabled(true);
                    txt_name.setHelperText("Required");
                }
                if(emailId.getText().toString().length() <6 && emailId.getText().toString().length() >0 ) {
                    txt_email.setHelperTextEnabled(true);
                    txt_email.setHelperText("Invalid email id");
                }else if(emailId.getText().toString().length() == 0){
                    txt_email.setHelperTextEnabled(true);
                    txt_email.setHelperText("Required");
                }
                int length_no = phonon.length();
                if(length_no < 10){
                    txt_phone.setHelperTextEnabled(true);
                    txt_phone.setHelperText("Invalid Mobile number");
                }else txt_phone.setHelperTextEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(username.getText().toString().length()>0){
                    txt_username.setHelperTextEnabled(false);
                }else {
                    txt_username.setHelperTextEnabled(true);
                    //txt_username.setHelperText("Required");
                }
                if(name.getText().toString().length()>0){
                    txt_name.setHelperTextEnabled(false);
                }else {
                    txt_name.setHelperTextEnabled(true);
                    txt_name.setHelperText("Required");
                }
                if(emailId.getText().toString().length() <6 && emailId.getText().toString().length() >0 ) {
                    txt_email.setHelperTextEnabled(true);
                    txt_email.setHelperText("Invalid email id");
                }else if(emailId.getText().toString().length() == 0){
                    txt_email.setHelperTextEnabled(true);
                    txt_email.setHelperText("Required");
                }
                if(phonon.getText().toString().length() == 0){
                    txt_phone.setHelperTextEnabled(true);
                    txt_phone.setHelperText("Required");
                }else if(phonon.getText().toString().length() >0 && phonon.getText().toString().length() <10){
                    txt_phone.setHelperTextEnabled(true);
                    txt_phone.setHelperText("Invalid Mobile number");
                }
                int length_no = password.length();
                if(length_no <8){
                    txt_password.setHelperTextEnabled(true);
                    txt_password.setHelperText("Password must be minimum of 8 characters");
                }else {
                    txt_password.setHelperTextEnabled(false);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        close_layout.setOnClickListener(view -> {
            Intent intent_close = new Intent(SignUpActivity.this, BlankActivity.class);
            Toast.makeText(this, "Welcome to home", Toast.LENGTH_SHORT).show();
            startActivity(intent_close);
        });
        
        signUp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(SignUpActivity.this.username.getText().toString())){
                    txt_username.setHelperTextEnabled(true);
                    txt_username.setHelperText("Required");
                }else if(TextUtils.isEmpty(SignUpActivity.this.name.getText().toString())){
                    txt_name.setHelperTextEnabled(true);
                    txt_name.setHelperText("Required");
                }else if(TextUtils.isEmpty(SignUpActivity.this.phonon.getText().toString())){
                    txt_phone.setHelperTextEnabled(true);
                    txt_phone.setHelperText("Required");
                }else if(TextUtils.isEmpty(SignUpActivity.this.emailId.getText().toString())){
                    txt_email.setHelperTextEnabled(true);
                    txt_email.setHelperText("Required");
                }else if(emailId.getText().length() <=6 && getUser_email.contains("@") && getUser_email.contains(".com")==false){
                    txt_email.setHelperTextEnabled(true);
                    txt_email.setHelperText("Invalid Email id");
                }else if(password.getText().toString().length()<8){
                    txt_password.setHelperTextEnabled(true);
                    txt_password.setHelperText("Password must be minimum of 8 characters");
                }else if(emailId.getText().toString().length()>=6  ) createNewUser();

            }
        });

        signIn.setOnClickListener(view -> {
            Intent intent_sign_in = new Intent(SignUpActivity.this, SignInActivity.class);
            startActivity(intent_sign_in);
        });



    }
    private static RestAPI getApiService(){
        return RetrofitClient.getRetrofitClient(Constants.API_URL).create(RestAPI.class);
    }

    private void createNewUser() {

        getApiService().signUpApi(username.getText().toString(), emailId.getText().toString(), name.getText().toString(), phonon.getText().toString(), password.getText().toString()).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if(response.code() == 200 && response.body().getResult().equals("success")){
                    Toast.makeText(SignUpActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                    Intent intent_signIn = new Intent(SignUpActivity.this, SignInActivity.class);
                    startActivity(intent_signIn);
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Something went wrong.Check your connection", Toast.LENGTH_SHORT).show();

            }
        });

    }

}