package com.lovepago.ssumtago.Data.Model;

import io.realm.RealmObject;
import lombok.Data;

/**
 * Created by ParkHaeSung on 2017-09-10.
 */
@Data
public class ValueFormat extends RealmObject {
    String label;
    double value;
}
