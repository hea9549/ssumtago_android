package com.lovepago.ssumtago.Service;

import com.lovepago.ssumtago.Data.Model.PredictReport;
import com.lovepago.ssumtago.Data.Model.Survey;
import com.lovepago.ssumtago.Retrofit.ApiReport;
import com.lovepago.ssumtago.Retrofit.ApiSurvey;
import com.lovepago.ssumtago.Retrofit.ServiceGenerator;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ParkHaeSung on 2017-05-25.
 */

public class SurveyServiceImpl implements SurveyService {
    private final String TAG = "surveyService";
    private UserService userService;

    @Inject
    public SurveyServiceImpl(UserService userService){
        this.userService=userService;
    }
    @Override
    public Observable<List<Survey>> getAllSurvey(){
        Realm realm = Realm.getDefaultInstance();
        List<Survey> realmSurveys = realm.where(Survey.class).findAll();
        Observable<List<Survey>> dbSurvey = Observable.just(realmSurveys)
                .filter(surveys -> !surveys.isEmpty());
        Observable<List<Survey>> netWorkSurvey =
                ServiceGenerator.createService(ApiSurvey.class)
                        .getAllSurvey()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(survey -> {
                            realm.beginTransaction();
                            realm.copyToRealmOrUpdate(survey);
                            realm.commitTransaction();
                        });
        return Observable.concat(dbSurvey,netWorkSurvey)
                .observeOn(AndroidSchedulers.mainThread())
                .first();
    }
    @Override
    public Observable<Survey> getSurveyById(int id) {
        Realm realm = Realm.getDefaultInstance();

        Observable<Survey> dbSurvey = Observable.just(
                realm.where(Survey.class).equalTo("surveyId",id).findFirst())
                .filter(survey -> survey!=null)
                .map(realm::copyFromRealm);
        Observable<Survey> netWorkSurvey =
            ServiceGenerator.createService(ApiSurvey.class)
                    .getSurvey(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(survey -> {
                        realm.beginTransaction();
                        realm.copyToRealmOrUpdate(survey);
                        realm.commitTransaction();
                    });

        return Observable.concat(dbSurvey,netWorkSurvey)
                .observeOn(AndroidSchedulers.mainThread())
                .first();
    }

    @Override
    public Observable<PredictReport> submitReport(PredictReport predictReport) {
        return ServiceGenerator.createService(ApiReport.class,userService.getUser().getJwt())
                .makeReport(predictReport)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Observable<ResponseBody> submitStartReport(PredictReport predictReport) {
        return ServiceGenerator.createService(ApiReport.class,userService.getUser().getJwt())
                .makePreviousReports(predictReport)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}