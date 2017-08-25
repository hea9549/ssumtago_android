package com.lovepago.ssumtago.Data.Model;

import io.realm.RealmList;
import io.realm.RealmObject;
import lombok.Data;

/**
 * Created by ParkHaeSung on 2017-08-05.
 */

@Data
public class Answer extends RealmObject{
    String desc;
    String img;
    RealmList<RealmString> name;
    String code;
}
