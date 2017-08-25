package com.lovepago.ssumtago.Data.Model;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import rx.Observable;

/**
 * joinType = email or facebook
 */
@Data
public class User extends RealmObject {
    @PrimaryKey
    String email;
    String password;
    String name;
    String sex;
    String birthday;
    String joinType;
    String fcmToken;
    String role;
    String lastSurveyed;
    boolean surveyedYN;
    String jwt;
    RealmList<PredictReport> predictReports;
}
