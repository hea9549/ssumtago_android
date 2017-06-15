package com.lovepago.ssumtago.Service;

import com.lovepago.ssumtago.Data.Model.ExpectAnswer;
import com.lovepago.ssumtago.Data.Model.RequestAnswer;
import com.lovepago.ssumtago.Data.Model.Survey;

import rx.Observable;

/**
 * Created by ParkHaeSung on 2017-05-25.
 */

public interface SurveyService {
    Observable<Survey> getSurveyById(int id);

    Observable<String> requestAnswer(RequestAnswer requestAnswer);
}
