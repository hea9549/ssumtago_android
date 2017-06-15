package com.lovepago.ssumtago.Service;

import android.util.Log;

import com.lovepago.ssumtago.Data.Model.ExpectAnswer;
import com.lovepago.ssumtago.Data.Model.RequestAnswer;
import com.lovepago.ssumtago.Data.Model.Survey;
import com.lovepago.ssumtago.Data.RealmDBService;
import com.lovepago.ssumtago.Retrofit.ApiSurvey;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ParkHaeSung on 2017-05-25.
 */

public class SurveyServiceImpl implements SurveyService {
    private final String TAG = "surveyServiceImpl";
    private Retrofit retrofit;
    private RealmDBService realmDBService;

    @Inject
    public SurveyServiceImpl(Retrofit retrofit, RealmDBService realmDBService) {
        this.retrofit = retrofit;
        this.realmDBService = realmDBService;
    }

    @Override
    public Observable<Survey> getSurveyById(int id) {
        return realmDBService.getSurveyBySurveyId(id)
                .switchIfEmpty(
                        retrofit.create(ApiSurvey.class)
                        .getSurvey("http://expirit.co.kr:5000")
                        .map(survey -> realmDBService.inputData(survey)));
    }

    @Override
    public Observable<String> requestAnswer(RequestAnswer requestAnswer) {
        return retrofit.create(ApiSurvey.class).requestExpectation(requestAnswer);
    }
}