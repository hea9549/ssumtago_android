package com.lovepago.ssumtago.Presentation.Presenter;

import android.text.Editable;

/**
 * Created by ParkHaeSung on 2017-05-15.
 */

public interface MainActivityPresenter {
    void setView(View view);

    void loginClick(String text, String pw);

    void facebookLoginClick();

    void kakaoLoginClick();

    interface View extends BaseViewPresenter{
    }
}
