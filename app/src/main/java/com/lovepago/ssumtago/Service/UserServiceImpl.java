package com.lovepago.ssumtago.Service;

import android.content.Context;

import com.lovepago.ssumtago.CustomClass.STGPreference;
import com.lovepago.ssumtago.Data.Model.PredictReport;
import com.lovepago.ssumtago.Data.Model.UpdateFcmDTO;
import com.lovepago.ssumtago.Data.Model.User;
import com.lovepago.ssumtago.Data.Model.VersionDTO;
import com.lovepago.ssumtago.Retrofit.ApiUser;
import com.lovepago.ssumtago.Retrofit.ServiceGenerator;
import com.lovepago.ssumtago.Service.FCM.STGFireBaseInstanceIdService;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by ParkHaeSung on 2017-06-04.
 */

public class UserServiceImpl implements UserService {
    private PublishSubject<List<PredictReport>> predictReportsObseravable;
    private STGPreference preference;

    @Inject
    public UserServiceImpl(Context context) {
        predictReportsObseravable = PublishSubject.create();
        preference = new STGPreference(context);
    }

    @Override
    public Observable<User> login(String email, String pw, String joinType) {
        User loginUser = new User();
        loginUser.setEmail(email);
        loginUser.setPassword(pw);
        loginUser.setJoinType(joinType);
        preference.put(STGPreference.PREF_ID,email);
        preference.put(STGPreference.PREF_PW,pw);
        preference.put(STGPreference.PREF_LOGIN_TYPE,joinType);
        return ServiceGenerator.createService(ApiUser.class)
                .login(loginUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(returnUser -> {
                    Realm realm = Realm.getDefaultInstance();
                    if(realm.where(User.class).findFirst()!=null) {
                        if (!realm.where(User.class).findFirst().getEmail().equals(returnUser.getEmail())) {
                            realm.beginTransaction();
                            realm.where(User.class).findAll().deleteAllFromRealm();
                            realm.commitTransaction();
                        }
                    }
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(returnUser);
                    realm.commitTransaction();
                    STGFireBaseInstanceIdService.sendRegistrationToServer(this);
                    return returnUser;
                })
                .doOnError(err->{
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    realm.where(User.class).findAll().deleteAllFromRealm();
                    realm.commitTransaction();
                    preference.put(STGPreference.PREF_LAST_SURVEYED,"");
                    preference.put(STGPreference.PREF_ID,"");
                    preference.put(STGPreference.PREF_PW,"");
                });
    }

    @Override
    public Observable<User> register(String email, String pw, String joinType, String name, String sex, String birthday) {
        User registerUser = new User();
        registerUser.setEmail(email);
        registerUser.setPassword(pw);
        registerUser.setJoinType(joinType);
        registerUser.setName(name);
        registerUser.setSex(sex);
        registerUser.setBirthday(birthday);
        return ServiceGenerator.createService(ApiUser.class)
                .register(registerUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(returnUser -> {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    realm.where(User.class).findAll().deleteAllFromRealm();
                    realm.copyToRealmOrUpdate(returnUser);
                    realm.commitTransaction();
                    STGFireBaseInstanceIdService.sendRegistrationToServer(this);
                    return returnUser;
                });
    }

    @Override
    public Observable<User> updateUserFCM(String fcmToken) {
        Realm realm = Realm.getDefaultInstance();
        User user = realm.where(User.class).findFirst();
        if (user == null)return Observable.empty();
        return ServiceGenerator.createService(ApiUser.class, user.getJwt())
                .updateFcm(new UpdateFcmDTO(fcmToken))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(response -> {
                    realm.beginTransaction();
                    realm.where(User.class).findFirst().setFcmToken(fcmToken);
                    realm.commitTransaction();
                    return response;
                });
    }

    @Override
    public Observable<User> updateUser(User updateUser) {
        Realm realm = Realm.getDefaultInstance();
        User user = realm.where(User.class).findFirst();
        return ServiceGenerator.createService(ApiUser.class, user.getJwt())
                .modifyUser(updateUser.getEmail(), updateUser)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(returnUser -> {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(returnUser);
                    realm.commitTransaction();
                    return returnUser;
                });
    }

    @Override
    public Observable<User> getUserObservable() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(User.class).findFirst().asObservable();
    }

    @Override
    public User getUser() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(User.class).findFirst();
    }

    @Override
    public Observable<ResponseBody> checkFacebook(String token) {
        return ServiceGenerator.createService(ApiUser.class)
                .checkFaceBook(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void alertPredictReportChange() {
        predictReportsObseravable.onNext(getUser().getPredictReports());
    }

    @Override
    public Observable<List<PredictReport>> getPredictReportsObservable() {
        return predictReportsObseravable;
    }

    @Override
    public Observable<ResponseBody> withDraw() {
        return ServiceGenerator.createService(ApiUser.class, getUser().getJwt())
                .deleteUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<VersionDTO> getVersions() {
        return ServiceGenerator.createService(ApiUser.class)
                .getVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
