package com.lovepago.ssumtago.Retrofit;

import com.lovepago.ssumtago.Data.Model.RequestAnswer;
import com.lovepago.ssumtago.Data.Model.Survey;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by ParkHaeSung on 2017-05-25.
 */

public interface ApiSurvey {
    @GET
    Observable<List<Survey>> getAllSurvey(@Url String url);

    @GET
    Observable<Survey> getSurvey(@Url String url);

    @POST("predictReports")
    Observable<String> requestExpectation(@Body RequestAnswer requestAnswer);
}
