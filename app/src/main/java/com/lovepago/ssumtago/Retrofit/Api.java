package com.lovepago.ssumtago.Retrofit;


import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;

/**
 * Created by ParkHaeSung on 2017-05-14.
 */

public interface Api {
    @GET("login")
    Observable<User> login(@Body LoginDTO loginDTO);

    @GET("exercise")
    Observable<ArrayList<Exercise>> getExerciseList();
}
