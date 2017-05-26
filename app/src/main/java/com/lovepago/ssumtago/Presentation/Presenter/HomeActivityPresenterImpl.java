package com.lovepago.ssumtago.Presentation.Presenter;

import android.util.Log;

import com.lovepago.ssumtago.Presentation.Activity.HomeActivity;
import com.lovepago.ssumtago.Service.LoginService.LoginService;
import com.lovepago.ssumtago.Service.LoginService.NormalLoginService;
import com.lovepago.ssumtago.Service.SurveyService;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ParkHaeSung on 2017-05-15.
 */

public class HomeActivityPresenterImpl implements HomeActivityPresenter {
    private String TAG = "HomeActivityPresImpl";
    private Retrofit retrofit;
    private View view;
    private SurveyService surveyService;

    @Inject
    public HomeActivityPresenterImpl(Retrofit retrofit, SurveyService surveyService) {
        this.retrofit = retrofit;
        this.surveyService = surveyService;
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void init() {
        surveyService.getSurveyById(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        response -> {
                            view.makeToast("찾아왔쪙~");
                            Log.e(TAG, "find!");
                        },
                        error -> {
                            Log.e(TAG, "error!!!!! message : " + error.toString());
                        }
                );
    }

}
