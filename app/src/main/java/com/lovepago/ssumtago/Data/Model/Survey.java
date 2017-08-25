package com.lovepago.ssumtago.Data.Model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;

/**
 * Created by ParkHaeSung on 2017-05-24.
 */

@Data
public class Survey extends RealmObject {
    @PrimaryKey
    int surveyId;
    RealmList<Question> questions;
    RealmList<RealmString> excludeCodes;
    String desc;
    String name;
    String version;
    String isAvailable;
}
