package com.lovepago.ssumtago.Presentation.Presenter;

import android.content.Context;
import android.util.Log;

import com.lovepago.ssumtago.CustomClass.STGPreference;
import com.lovepago.ssumtago.Data.Model.*;
import com.lovepago.ssumtago.Service.SurveyService;
import com.lovepago.ssumtago.Service.UserService;
import com.lovepago.ssumtago.DataCode;
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
    private STGPreference preference;
    private List<PredictReport> userReports;
    private int reportIndex;

    @Inject
    public LobyActivityPresenterImpl(UserService userService, SurveyService surveyService, Context context) {
        this.surveyService = surveyService;
        this.userService = userService;
        this.preference = new STGPreference(context);
        userService.getPredictReportsObservable().subscribe(predictReports -> {
            Realm realm = Realm.getDefaultInstance();
            this.userReports = realm.copyFromRealm(predictReports);
            if (userReports.size() == 0) return;
            reportIndex = userReports.size() - 1;
            sortReports();
            setReport();
        }, error -> Log.e(TAG, "onPredictReportObservable error = " + error.toString()));
    }

    private void setReport() {
        PredictReport recentReport = userReports.get(reportIndex);
        if (recentReport.getResults().size() == 0){
            return;
        }
        onPredictClick(recentReport);
    }

    @Override
    public void setView(View view) {
        this.view = view;
        this.reportIndex = 0;
        if (userService.getUser().getPredictReports().isManaged()) {
            Realm realm = Realm.getDefaultInstance();
            this.userReports = realm.copyFromRealm(userService.getUser().getPredictReports());
        } else {
            this.userReports = userService.getUser().getPredictReports();
        }

        if (userReports.size() != 0) {
            this.reportIndex = userReports.size() - 1;
            sortReports();
            setReport();
        }

    }

    @Override
    public void onAddReportClick() {
        view.makeDialog("잠시만 기다려주세요");
        surveyService.getAllSurvey()
                .doOnNext(next -> view.cancelDialog())
                .doOnError(error -> view.cancelDialog())
                .subscribe(
                        surveys -> {
                            String lastSurvey = preference.getValue(STGPreference.PREF_LAST_SURVEYED, "");
                            if (lastSurvey.isEmpty() || userService.getUser().getRole().equals("admin")) {
                                view.makeSelectSurveyDialog(surveys);
                                return;
                            }
                            SimpleDateFormat strDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                            try {
                                Date lastSurveyTime = strDateFormat.parse(lastSurvey);
                                Date currentTime = new Date();
                                if (currentTime.getTime() - lastSurveyTime.getTime() < 1000 * 60 * 60 * 24) {
                                    long time = 1000 * 60 * 60 * 24 - (currentTime.getTime() - lastSurveyTime.getTime());
                                    view.makeAlertDialg("썸포트는 24시간에 한번만 작성하실 수 있습니다. \n남은시간 : "
                                            + (time / (1000 * 60 * 60)) + "시간" + ((time / (1000 * 60)) % 60) + "분");
                                } else {
                                    view.makeSelectSurveyDialog(surveys);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                                view.makeSelectSurveyDialog(surveys);
                            }
                        }, fail -> Log.e(TAG, "fail in addReportClick, get All surveys = " + fail.toString()));
    }

    @Override
    public void onPercentOpened(){
        PredictReport recentReport = userReports.get(reportIndex);
        if (recentReport.getResults().size() == 0) return;
        for(ResultFormat resultFormat : recentReport.getResults()){
            if (!resultFormat.getType().equals(DataCode.RESULT_TYPE_PERCENT))continue;
            view.setPercent((int)(resultFormat.getResults().get(0).getValue()*100));
        }
    }

    @Override
    public void onPredictLoveOpened(){
        PredictReport recentReport = userReports.get(reportIndex);
        if (recentReport.getResults().size() == 0) return;
        for(ResultFormat resultFormat : recentReport.getResults()){
            if (!resultFormat.getType().equals(DataCode.RESULT_TYPE_PREDICT_LOVE))continue;
            view.setPredictLove(resultFormat.getResults());
        }

    }

    @Override
    public void onPredictClick(PredictReport report) {
        view.makeDialog("");
        sortReports();
        for (int i = 0 ; i < userReports.size();i++)
            if (userReports.get(i).getId().equals(report.getId())){
                userReports.remove(i);
                userReports.add(0,report);
                reportIndex=0;
            }
        view.setSpinner(userReports);
        view.cancelDialog();
    }


    private void sortReports() {
        Collections.sort(userReports, (e1, e2) -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SZ");
            String e1StrDate = e1.getRequestTime();
            String e2StrDate = e2.getRequestTime();
            try {
                Date e1Date = simpleDateFormat.parse(e1StrDate);
                Date e2Date = simpleDateFormat.parse(e2StrDate);
                if (e2Date.getTime() - e1Date.getTime() > 0)
                    return 1;
                if (e2Date.getTime() - e1Date.getTime() < 0)
                    return -1;
                return 0;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;
        });
    }

}
