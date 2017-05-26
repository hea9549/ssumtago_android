package com.lovepago.ssumtago.Retrofit;


import com.lovepago.ssumtago.Data.Model.LoginDTO;
import com.lovepago.ssumtago.Data.Model.User;

import retrofit2.http.Body;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by ParkHaeSung on 2017-05-14.
 */

public interface ApiUser {
    @GET("login")
    Observable<User> login(@Body LoginDTO loginDTO);

}
