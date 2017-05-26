package com.lovepago.ssumtago.Service.LoginService;

import com.lovepago.ssumtago.Data.Model.User;

import rx.Observable;

/**
 * Created by ParkHaeSung on 2017-05-25.
 */

public interface LoginService {
    boolean isAvailable();
    Observable<User> login();
    interface builder{
        LoginService.builder setId(String id);
        LoginService.builder setPw(String pw);
        LoginService.builder setToken(String token);
        LoginService build();
    }
}
