package com.lovepago.ssumtago.Retrofit;

import com.lovepago.ssumtago.STGApplication;
import com.lovepago.ssumtago.Service.UserService;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ParkHaeSung on 2017-07-09.
 */

public class JWTAddInterceptor implements Interceptor {
    @Inject
    UserService userService;

    @Override
    public Response intercept(Chain chain) throws IOException {
        STGApplication.getComponent().inject(this);
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("Key", "jwt");
        builder.addHeader("Value",userService.getUser().getJwt());
        return chain.proceed(builder.build());
    }
}