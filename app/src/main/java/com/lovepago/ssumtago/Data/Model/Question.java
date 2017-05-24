package com.lovepago.ssumtago.Data.Model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import lombok.Data;

/**
 * Created by ParkHaeSung on 2017-05-24.
 */

@Data
public class Question extends RealmObject {
    private RealmList<Answer> answers;
    private String desc;
    private RealmList<RealmString> name;
    private String code;

}
