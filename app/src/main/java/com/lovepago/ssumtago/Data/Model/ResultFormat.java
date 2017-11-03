package com.lovepago.ssumtago.Data.Model;

import io.realm.RealmList;
import io.realm.RealmObject;
import lombok.Data;

/**
 * Created by ParkHaeSung on 2017-09-10.
 */
@Data
public class ResultFormat extends RealmObject {

    String type;
    RealmList<ValueFormat> results;


}
