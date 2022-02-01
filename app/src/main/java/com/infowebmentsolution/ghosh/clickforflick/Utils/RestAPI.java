package com.infowebmentsolution.ghosh.clickforflick.Utils;


import com.infowebmentsolution.ghosh.clickforflick.Response.AllCategoryResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.BannerResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.CategoryWiseResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.ForgotPasswordResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.GoogleSignInResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.HomeMovieListsResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.LikeCountResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.LoginWithOtpResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.LogoutResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.NextVideoResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.OtpVerificationResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.PaymentResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.PaymentinfoResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.PincodeResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.ProfileResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.ProfileUpdateResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.SearchResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.SignInResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.SignUpResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.SubscibeCheckResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.SubscriptionPlanResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.VideoLikeByUserResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.isLoginCheckResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestAPI {

    @FormUrlEncoded
    @POST("search")
    Call<SearchResponse> searchApi(
            @Field("keyword") String keyword
    );

    @FormUrlEncoded
    @POST("new_register")
    Call<SignUpResponse> signUpApi(


            @Field("username") String username,
            @Field("emailid") String emailid,
            @Field("name") String name,
            @Field("userphoneno") String userphooeno,
            @Field("userpassword") String password

    );

    @FormUrlEncoded
    @POST("login")
    Call<SignInResponse> signInApi(
            @Field("email") String emailid,
            @Field("password") String password,
            @Field("fingerprint") String fingerprint

    );

    @FormUrlEncoded
    @POST("loginWithGoogle")
    Call<GoogleSignInResponse> googleSignInApi(
      @Field("username")String name,
      @Field("useremail") String email,
      @Field("authId") String authid,
      @Field("userFingerPrint") String device_id
    );

    @FormUrlEncoded
    @POST("forgotPassword")
    Call<ForgotPasswordResponse> forgotPasswordApi(
            @Field("userphoneno") String emailid
    );

    @FormUrlEncoded
    @POST("isLogCheck")
    Call<isLoginCheckResponse> isLoginCheckApi(

            @Field("userid") String userid,
            @Field("fingerprint") String fingerprint
    );

    @FormUrlEncoded
    @POST("logout")
    Call<LogoutResponse> logoutApi(
            @Field("userid") String userid
    );

    @FormUrlEncoded
    @POST("profile")
    Call<ProfileResponse> profileApi(
            @Field("user_id") String userid
    );

    @FormUrlEncoded
    @POST("profileUpdateApi")
    Call<ProfileUpdateResponse> profileUpdateApi(
            @Field("name") String username,
            @Field("useraddress") String useraddress,
            @Field("userid") String userid
    );

    @GET("subscriptionPlanViewApi")
    Call<SubscriptionPlanResponse> subscriptionPlanApi(
    );


    @FormUrlEncoded
    @POST("loginWithMobileApi")
    Call<LoginWithOtpResponse> loginWithOtpApi(
        @Field("userphoneno") String phone_no
    );

    @FormUrlEncoded
    @POST("verifyOtpApi")
    Call<OtpVerificationResponse> otpVerficationAPi(

            @Field("userphoneno") String phone_no,
            @Field("otp") String otp,
            @Field("userFingerPrint") String device_id
    );

    @GET("allcatagoryApi")
    Call<AllCategoryResponse> allcategoriesApi();

    @GET("videoApi")
    Call<HomeMovieListsResponse> homeVideoApi();

    @GET("bannerApi")
    Call<BannerResponse> bannerApi();

    @FormUrlEncoded
    @POST("catwiseBVApi")
    Call<CategoryWiseResponse>categoryWiseApi(
            @Field("page") String catname
    );

    @FormUrlEncoded
    @POST("newsByPinApi")
    Call<CategoryWiseResponse>NewsByPinApi(
            @Field("userid") String userid,
            @Field("userpin") String userpin
    );


    @FormUrlEncoded
    @POST("nextVideoListApiLangwise")
    Call<NextVideoResponse>nextVideoListApi(
        @Field("videId") String videoId
    );

    @FormUrlEncoded
    @POST("likecountApi")
    Call<LikeCountResponse>likeCountAPi(
            @Field("videId") String vid
    );

    @FormUrlEncoded
    @POST("vedioLikeApi")
    Call<VideoLikeByUserResponse>videoLikeByUserApi(
            @Field("userid") String userid,
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("checkLikeApi")
    Call<VideoLikeByUserResponse>videoLikeCheckApi(
            @Field("userid") String userid,
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("PaymentAPI")
    Call<PaymentResponse>PaymentAPI(
            @Field("razorpay_payment_id") String payid,
            @Field("userid") String userid,
            @Field("amount") String amount,
            @Field("duration") String duration
    );

    @FormUrlEncoded
    @POST("SubscibeCheckApi")
    Call<SubscibeCheckResponse>SubscibeCheckApi(
            @Field("userid") String userid
    );

    @FormUrlEncoded
    @POST("PaymentinfoApi")
    Call<PaymentinfoResponse>PaymentinfoApi(
            @Field("userid") String userid
    );

    @FormUrlEncoded
    @POST("pincodeApi")
    Call<PincodeResponse> PincodeApi(
            @Field("userid") String userid,
            @Field("pincode") String pincode
    );

    @FormUrlEncoded
    @POST("lanWiseBannerApi")
    Call<CategoryWiseResponse> bannerByLangApi(
            @Field("page") String str,
            @Field("lang") String str2
    );
    
}