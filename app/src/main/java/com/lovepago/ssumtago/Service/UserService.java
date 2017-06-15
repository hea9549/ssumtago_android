package com.lovepago.ssumtago.Service;


import com.lovepago.ssumtago.Data.Model.User;

import rx.Observable;

/**
 * Created by ParkHaeSung on 2017-06-04.
 */

public interface UserService {
    Observable<User> login(String email, String pw, String joinType);
    Observable<User> register(String email, String pw, String joinType,String name,String sex, int age);
}
