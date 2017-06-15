package com.lovepago.ssumtago.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lovepago.ssumtago.CustomClass.StringRealmListConverter;
import com.lovepago.ssumtago.Data.Model.RealmString;

import java.net.CookieManager;
import java.net.CookiePolicy;

import io.realm.RealmList;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
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

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(new TypeToken<RealmList<RealmString>>() {}.getType(),
                            new StringRealmListConverter())
                    .create();

            retrofit = new Retrofit.Builder()
                    .client(httpClient)
                    .baseUrl("http://expirit.co.kr:3000/")
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
