package com.lovepago.ssumtago.Service;

import android.util.Log;

import com.lovepago.ssumtago.Data.Model.Survey;
import com.lovepago.ssumtago.Data.RealmDBService;
import com.lovepago.ssumtago.Retrofit.ApiSurvey;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by ParkHaeSung on 2017-05-25.
 */

public class SurveyServiceImpl implements SurveyService {
    private final String TAG = "surveyServiceImpl";
    private Retrofit retrofit;
    private RealmDBService realmDBService;

    @Inject
    public SurveyServiceImpl(Retrofit retrofit,RealmDBService realmDBService) {
        this.retrofit = retrofit;
        this.realmDBService = realmDBService;
    }

    @Override
    public Observable<Survey> getSurveyById(int id) {
        if (isSurveyInDataBase()) {
            Log.e(TAG,"getSurvey by DB");
            return realmDBService.getSurveyBySurveyId(id).flatMap(Observable::from).filter(s->s.getId()==id);
        }
        Log.e(TAG,"try to get survey SERVER");

        Observable<Survey> retrofitObservable = retrofit.create(ApiSurvey.class).getSurvey("http://expirit.co.kr:5000/");
        retrofitObservable.subscribeOn(Schedulers.io()).filter(s->s.getId() == id).subscribe(a->{
            Log.e(TAG,"retrofit Observable : "+a.getName());
        });

        retrofitObservable.subscribeOn(Schedulers.io()).subscribe(
                response -> realmDBService.inputData(response)
                , error -> Log.e(TAG,"error in retrofitObserver, in getSurveyById metohd, message : "+error.getMessage()));
        return retrofitObservable.filter(s -> s.getId() == id);
    }

    private boolean isSurveyInDataBase() {
        Log.e(TAG,"get SurveyCount ? = "+ realmDBService.getSurveyCount());
        if (realmDBService.getSurveyCount() == 0) return false;
        return true;
    }
}
