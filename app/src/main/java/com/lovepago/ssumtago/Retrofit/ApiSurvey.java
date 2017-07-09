package com.lovepago.ssumtago.Retrofit;

import com.lovepago.ssumtago.Data.Model.RequestAnswer;
import com.lovepago.ssumtago.Data.Model.Survey;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by ParkHaeSung on 2017-05-25.
 */

public interface ApiSurvey {
    @GET("surveys")
    Observable<List<Survey>> getAllSurvey();

    @GET("surveys/{surveyId}")
    Observable<Survey> getSurvey(@Path("surveyId") int surveyId);

}
