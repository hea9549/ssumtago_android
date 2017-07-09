package com.lovepago.ssumtago.Data.Model;

import io.realm.RealmObject;
import lombok.Data;

/**
 * Created by ParkHaeSung on 2017-06-04.
 */

public class Ssum extends RealmObject {
    String name;
    int age;
    String sex;
}
