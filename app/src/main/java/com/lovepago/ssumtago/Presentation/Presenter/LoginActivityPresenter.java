package com.lovepago.ssumtago.Presentation.Presenter;

import com.facebook.login.LoginResult;

/**
 * Created by ParkHaeSung on 2017-05-15.
 */

public interface LoginActivityPresenter {
    void setView(View view);

    void onLoginClick(String email, String pw, String joinType);

    void onFindPwClick();

    void onFacebookLoginSuccess(LoginResult loginResult);

    interface View extends BaseViewPresenter{

        void alertCheckIdPw();
    }
}
