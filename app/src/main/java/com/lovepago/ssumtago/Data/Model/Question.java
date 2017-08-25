package com.lovepago.ssumtago.Data.Model;

import io.realm.RealmList;
import io.realm.RealmObject;
import lombok.Data;

/**
 * Created by ParkHaeSung on 2017-05-24.
 */

@Data
public class Question extends RealmObject {
    RealmList<Answer> answers;
    RealmList<RealmString> name;
    String desc;
    String code;
    String img;
    String category;
}
