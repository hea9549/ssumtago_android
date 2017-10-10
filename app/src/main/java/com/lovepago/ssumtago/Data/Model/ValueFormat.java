package com.lovepago.ssumtago.Data.Model;

import io.realm.RealmObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ParkHaeSung on 2017-09-10.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ValueFormat extends RealmObject {
    String label;
    double value;
}
