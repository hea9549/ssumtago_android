package com.lovepago.ssumtago.Presentation.Presenter;

import android.util.Log;

import com.lovepago.ssumtago.Data.Model.PredictReport;
import com.lovepago.ssumtago.Service.SurveyService;
import com.lovepago.ssumtago.Service.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * Created by ParkHaeSung on 2017-05-15.
 */

public class LobyActivityPresenterImpl implements LobyActivityPresenter {
    private SurveyService surveyService;
    private String TAG = "LobyPresenter";
    private UserService userService;
    private View view;
    private List<PredictReport> userReports;
    private int reportIndex;

    @Inject
    public LobyActivityPresenterImpl(UserService userService, SurveyService surveyService) {
        this.surveyService = surveyService;
        this.userService = userService;
        userService.getPredictReportsObservable().subscribe(predictReports -> {
            Realm realm = Realm.getDefaultInstance();
            this.userReports = realm.copyFromRealm(predictReports);
            if (userReports.size() == 0) return;
            reportIndex = userReports.size() - 1;
            sortReports();
            showReportResult();
        },error->Log.e(TAG,"onPredictReportObservable error = "+error.toString()));
    }

    @Override
    public void setView(View view) {
        this.view = view;
        this.reportIndex =0;

        if (userService.getUser().getPredictReports().isManaged()){
            Realm realm = Realm.getDefaultInstance();
            this.userReports = realm.copyFromRealm(userService.getUser().getPredictReports());
        }else{
            this.userReports = userService.getUser().getPredictReports();
        }

        if (userReports.size()!=0){
            this.reportIndex =userReports.size()-1;
            sortReports();
            showReportResult();
        }

    }

    @Override
    public void onAddReportClick() {
        view.makeDialog("잠시만 기다려주세요");
        surveyService.getAllSurvey()
                .doOnNext(next -> view.cancelDialog())
                .doOnError(error -> view.cancelDialog())
                .subscribe(surveys -> view.makeSelectSurveyDialog(surveys)
                        , fail -> Log.e(TAG, "fail in addReportClick, get All surveys = " + fail.toString()));
    }

    @Override
    public void onPrevClick() {
        if(reportIndex == 0)return;
        reportIndex--;
        showReportResult();
    }

    @Override
    public void onNextClick() {
        if(reportIndex==userReports.size()-1)return;
        reportIndex++;
        showReportResult();
    }

    private void showReportResult(){
        PredictReport recentReport = userReports.get(reportIndex);
        view.setPercent((int) (recentReport.getResult().get(0).getVal() * 100));
        SimpleDateFormat serverDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String strDate = recentReport.getRequestTime();
        try {
            Date responseDate = serverDateFormat.parse(strDate);
            SimpleDateFormat viewDate = new SimpleDateFormat("yyyy:MM:dd");
            view.setDate(viewDate.format(responseDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void sortReports(){
        Collections.sort(userReports, (e1, e2) -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SZ");
            String e1StrDate = e1.getRequestTime();
            String e2StrDate = e2.getRequestTime();
            try {
                Date e1Date = simpleDateFormat.parse(e1StrDate);
                Date e2Date = simpleDateFormat.parse(e2StrDate);
                if (e2Date.getTime() - e1Date.getTime()>0)
                    return 1;
                if (e2Date.getTime() - e1Date.getTime()<0)
                    return -1;
                return 0;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;
        });
    }

}
