package com.lovepago.ssumtago.Service.LoginService;

import com.lovepago.ssumtago.Data.Model.LoginDTO;
import com.lovepago.ssumtago.DataCode;
import com.lovepago.ssumtago.Domain.StringFormatChecker;
import com.lovepago.ssumtago.Retrofit.ApiUser;
import com.lovepago.ssumtago.STGApplication;
import com.lovepago.ssumtago.Service.Exception.NotCallNecessaryBuilderMethod;

import javax.inject.Inject;

import lombok.Data;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by ParkHaeSung on 2017-05-25.
 */
@Data
public class NormalLoginService implements LoginService {
    @Inject
    Retrofit retrofit;
    private String id;
    private String pw;

    private NormalLoginService() {
        STGApplication.getComponent().inject(this);
    }

    @Override
    public boolean isAvailable() {
        if(retrofit == null || id == null || pw == null)return false;
        if (!StringFormatChecker.isValidEmail(id) || pw.isEmpty())return false;
        return true;
    }

    @Override
    public Observable login() {
        if(retrofit == null)return null; // not injected Exception 만들기
        if(id == null) throw new NotCallNecessaryBuilderMethod(Builder.class,"setId");
        if(pw == null) throw new NotCallNecessaryBuilderMethod(Builder.class,"setPw");
        LoginDTO loginDTO = new LoginDTO(DataCode.LOGIN_TYPE_NORMAL);
        loginDTO.setId(id);
        loginDTO.setPw(pw);
        return null;
    }

    public static class Builder implements LoginService.builder{
        NormalLoginService normalLoginService = new NormalLoginService();
        @Override
        public builder setId(String id) {
            normalLoginService.setId(id);
            return this;
        }

        @Override
        public builder setPw(String pw) {
            normalLoginService.setPw(pw);
            return this;
        }

        @Override
        public builder setToken(String token) {
            return this;
        }

        @Override
        public LoginService build() {
            return normalLoginService;
        }
    }
}
