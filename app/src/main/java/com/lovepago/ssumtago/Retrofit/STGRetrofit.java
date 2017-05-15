package com.lovepago.ssumtago.Retrofit;

import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ParkHaeSung on 2017-05-14.
 */

public class STGRetrofit {
    private static Retrofit retrofit;
    private STGRetrofit(){
    }
    public static Retrofit getInstance(){
        if(retrofit == null){
            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            OkHttpClient httpClient = new OkHttpClient().newBuilder()
                    .cookieJar(new JavaNetCookieJar(cookieManager))
                    .build();
            retrofit = new Retrofit.Builder()
                    .client(httpClient)
                    .baseUrl("https://expirit.co.kr:9090/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
