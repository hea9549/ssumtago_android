package com.lovepago.ssumtago.Retrofit;


import com.lovepago.ssumtago.Data.Model.LoginDTO;
import com.lovepago.ssumtago.Data.Model.User;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ParkHaeSung on 2017-05-14.
 */

public interface ApiUser {
    @POST("sessions")
    Observable<User> login(@Body User user);

    @POST("users")
    Observable<User> register(@Body User user);

    @DELETE("users")
    Observable<String> deleteUser();
}
