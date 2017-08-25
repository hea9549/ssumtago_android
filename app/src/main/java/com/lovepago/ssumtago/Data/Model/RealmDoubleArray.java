package com.lovepago.ssumtago.Data.Model;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import lombok.Data;

/**
 * Created by ParkHaeSung on 2017-08-18.
 */

@Data
public class RealmDoubleArray extends RealmObject {
    RealmList<RealmDouble> vals;

    public RealmDoubleArray(){

    }

    public RealmDoubleArray(RealmList<RealmDouble> vals){
        this.vals =vals;
    }
}
