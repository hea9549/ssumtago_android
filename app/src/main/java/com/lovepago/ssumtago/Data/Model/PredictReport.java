package com.lovepago.ssumtago.Data.Model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;

/**
 * Created by ParkHaeSung on 2017-08-05.
 */

@Data
public class PredictReport extends RealmObject{
    @PrimaryKey
    String id;

    int surveyId;
    String version;
    String modelId;
    String requestTime;
    String responseTime;
    String startTime;
    String endTime;
    RealmList<RealmDouble> result;
    RealmList<SsumJi> data;
}
