package com.lovepago.ssumtago.Data.Model;

import io.realm.RealmList;
import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ParkHaeSung on 2017-06-11.
 */

@Getter
@Setter
public class SurveyResult extends RealmObject {
    String _id;
    int surveyId;
    int modelId;
    String version;
    String requestTime;
    boolean isProcessed;
    RealmList<RequestObject> data;
    RealmList<RealmString> result;
}
