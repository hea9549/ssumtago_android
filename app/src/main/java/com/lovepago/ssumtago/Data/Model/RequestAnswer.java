package com.lovepago.ssumtago.Data.Model;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;
import lombok.Data;

/**
 * Created by ParkHaeSung on 2017-06-04.
 */
@Data
public class RequestAnswer extends RealmObject {
    private int surveyId;
    private int modelId;
    private String version;
    private RealmList<RequestObject> data;
}
