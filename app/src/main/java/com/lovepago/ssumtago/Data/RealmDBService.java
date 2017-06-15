package com.lovepago.ssumtago.Data;

import com.lovepago.ssumtago.Data.Model.Survey;

import java.util.List;

import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by ParkHaeSung on 2017-05-25.
 */

public interface RealmDBService extends DBService {
    Observable<Survey> getSurveyBySurveyId(int surveyId);

    int getSurveyCount();

    void inputDatas(List datas);

    <E extends RealmObject>E inputData(E data);
}
