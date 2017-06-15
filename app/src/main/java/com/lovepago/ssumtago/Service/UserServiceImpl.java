package com.lovepago.ssumtago.Service;

import com.lovepago.ssumtago.Data.Model.User;
import com.lovepago.ssumtago.Retrofit.ApiUser;

import retrofit2.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ParkHaeSung on 2017-06-04.
 */

public class UserServiceImpl implements UserService {
    private User user;
    private Retrofit retrofit;
    public UserServiceImpl(Retrofit retrofit){
        this.retrofit = retrofit;
        user = new User();
    }

    @Override
    public Observable<User> login(String email, String pw, String joinType){
        this.user.setEmail(email);
        this.user.setPassword(pw);
        this.user.setJoinType(joinType);

        return retrofit.create(ApiUser.class)
                .login(this.user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(returnUser->user=returnUser);
    }

    @Override
    public Observable<User> register(String email, String pw, String joinType, String name, String sex, int age) {
        this.user.setEmail(email);
        this.user.setPassword(pw);
        this.user.setJoinType(joinType);
        this.user.setName(name);
        this.user.setSex(sex);
        this.user.setAge(age);
        return retrofit.create(ApiUser.class)
                .register(this.user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(retunUser->user=retunUser);
    }
}
