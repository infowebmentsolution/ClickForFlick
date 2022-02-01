package com.infowebmentsolution.ghosh.clickforflick.Utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static Retrofit retrofit = null;
    public static  Retrofit getRetrofitClient(String baseURL){
        if (retrofit == null){
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .client(new OkHttpClient.Builder()
                            .addInterceptor(httpLoggingInterceptor)
                            .connectTimeout(90, TimeUnit.SECONDS)
                            .readTimeout(90,TimeUnit.SECONDS)
                            .writeTimeout(90,TimeUnit.SECONDS)
                            .build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
