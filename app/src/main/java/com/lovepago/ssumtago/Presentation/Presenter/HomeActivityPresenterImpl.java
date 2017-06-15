package com.lovepago.ssumtago.Presentation.Presenter;

import android.util.Log;

import com.lovepago.ssumtago.Data.Model.RequestAnswer;
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
                .doOnError(error->Log.e(TAG, "error!!!!! message : " + error.toString()))
                .subscribe(response -> {
                    view.makeToast("찾아왔쪙~");
                    view.setList(response.getQuestions());
                });
    }

    @Override
    public void onSubmitClick(RequestAnswer requestAnswer) {
        for(int i = 0 ; i < requestAnswer.getData().size();i++){
            if(requestAnswer.getData().get(i).getAnswerCode().isEmpty()){
                view.makeToast("답변을 모두 해주세요");
                return;
            }
        }
        view.makeDialog("왈ㄹ규디기다룔 ㅠㅠ");
        requestAnswer.setSurveyId(1);
        requestAnswer.setModelId(1);
        requestAnswer.setVersion("1.0.1");
        surveyService.requestAnswer(requestAnswer)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response->{
                    view.cancelDialog();
                    view.makeToast("요청 보내기 성공~");
                },error->Log.e(TAG,"error on Submit , message : "+error.toString()));
    }

}
