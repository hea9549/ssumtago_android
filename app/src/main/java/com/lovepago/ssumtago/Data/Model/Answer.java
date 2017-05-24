package com.lovepago.ssumtago.Data.Model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import lombok.Data;

/**
 * Created by ParkHaeSung on 2017-05-24.
 */
@Data
public class Answer extends RealmObject{
    private String desc;
    private String img;
    private String code;
    private RealmList<RealmString> name;
}
