package com.lovepago.ssumtago.Service;

import com.lovepago.ssumtago.Data.Model.PredictReport;
import com.lovepago.ssumtago.Data.Model.UpdateFcmDTO;
import com.lovepago.ssumtago.Data.Model.User;
import com.lovepago.ssumtago.Retrofit.ApiUser;
import com.lovepago.ssumtago.Retrofit.ServiceGenerator;
import com.lovepago.ssumtago.Service.FCM.STGFireBaseInstanceIdService;

import java.util.List;

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
    public UserServiceImpl(){
         predictReportsObseravable = PublishSubject.create();
    }

    @Override
    public Observable<User> login(String email, String pw, String joinType) {
        User loginUser = new User();
        loginUser.setEmail(email);
        loginUser.setPassword(pw);
        loginUser.setJoinType(joinType);
        return ServiceGenerator.createService(ApiUser.class)
                .login(loginUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(returnUser -> {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(returnUser);
                    realm.commitTransaction();
                    STGFireBaseInstanceIdService.sendRegistrationToServer(this);
                    return returnUser;
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
        return ServiceGenerator.createService(ApiUser.class,user.getJwt())
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
        return ServiceGenerator.createService(ApiUser.class,user.getJwt())
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
    public Observable<List<PredictReport>> getPredictReportsObservable(){
        return predictReportsObseravable;
    }

    @Override
    public Observable<ResponseBody> withDraw() {
        return ServiceGenerator.createService(ApiUser.class,getUser().getJwt())
                .deleteUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
