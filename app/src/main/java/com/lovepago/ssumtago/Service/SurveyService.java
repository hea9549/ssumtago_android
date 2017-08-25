package com.lovepago.ssumtago.Service;

import com.lovepago.ssumtago.Data.Model.PredictReport;
import com.lovepago.ssumtago.Data.Model.Survey;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;

/**
 * Created by ParkHaeSung on 2017-05-25.
 */

public interface SurveyService {
    Observable<List<Survey>> getAllSurvey();

    Observable<Survey> getSurveyById(int id);

    Observable<PredictReport> submitReport(PredictReport predictReport);

    Observable<ResponseBody> submitStartReport(PredictReport predictReport);
}
