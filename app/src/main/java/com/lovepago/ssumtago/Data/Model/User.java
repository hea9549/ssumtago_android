package com.lovepago.ssumtago.Data.Model;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import lombok.Data;
import rx.Observable;

/**
 * Created by ParkHaeSung on 2017-05-25.
 */
@Data
public class User extends RealmObject {
    private String email;
    private String password;
    private String name;
    private String sex;
    private int age;
    private String joinType;
    private String fcmToken;
   // private RealmList<Ssum> ssums;
    private String id;
    private String jwt;

    public static Observable<User> getUserFromDB() {
        Realm mRealm;
        mRealm = Realm.getDefaultInstance();
        return mRealm.where(User.class).findFirst().asObservable();
    }
}
