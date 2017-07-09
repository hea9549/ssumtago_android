package com.lovepago.ssumtago.Data.Model;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import lombok.Data;

/**
 * Created by ParkHaeSung on 2017-05-24.
 */

@Data
public class Survey extends RealmObject {
    private RealmList<Question> questions = new RealmList<>();
    private String desc;
    private String name;
    private String version;
    private int id;
}
