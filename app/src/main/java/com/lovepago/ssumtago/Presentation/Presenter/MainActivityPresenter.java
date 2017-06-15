package com.lovepago.ssumtago.Presentation.Presenter;

import android.text.Editable;

import com.facebook.login.LoginResult;

/**
 * Created by ParkHaeSung on 2017-05-15.
 */

public interface MainActivityPresenter {
    void setView(View view);

    void loginClick(String text, String pw);

    void facebookLoginClick();

    void kakaoLoginClick();

    void onFacebookLoginSuccess(LoginResult loginResult);

    interface View extends BaseViewPresenter{
    }
}
