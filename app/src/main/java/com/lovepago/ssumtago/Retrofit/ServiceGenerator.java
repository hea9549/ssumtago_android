package com.lovepago.ssumtago.Retrofit;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lovepago.ssumtago.CustomClass.Double2DimArrayConverter;
import com.lovepago.ssumtago.CustomClass.DoubleRealmListConverter;
import com.lovepago.ssumtago.CustomClass.StringRealmListConverter;
import com.lovepago.ssumtago.Data.Model.RealmDouble;
import com.lovepago.ssumtago.Data.Model.RealmDoubleArray;
import com.lovepago.ssumtago.Data.Model.RealmString;

import java.io.IOException;

import io.realm.RealmList;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ParkHaeSung on 2017-07-09.
 */

public class ServiceGenerator {
    public static final String API_BASE_URL = "http://expirit.co.kr:8000/";
    private static Retrofit retrofit;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(new TypeToken<RealmList<RealmString>>() {}.getType(),new StringRealmListConverter())
            .registerTypeAdapter(new TypeToken<RealmList<RealmDoubleArray>>(){}.getType(),new Double2DimArrayConverter())
            .registerTypeAdapter(new TypeToken<RealmList<RealmDouble>>(){}.getType(),new DoubleRealmListConverter())
            .create();


    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson));


    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }

    public static <S> S createService(Class<S> serviceClass, final String authToken) {
        retrofit = builder.build();
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(authToken);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);
                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }

        return retrofit.create(serviceClass);
    }

    public static class AuthenticationInterceptor implements Interceptor {

        private String jwt;

        public AuthenticationInterceptor(String token) {
            this.jwt = token;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();

            Request.Builder builder = original.newBuilder()
                    .header("jwt", jwt);

            Request request = builder.build();
            return chain.proceed(request);
        }
    }
}