package com.lovepago.ssumtago.Presentation.Presenter;

/**
 * Created by ParkHaeSung on 2017-05-15.
 */

public interface MainActivityPresenter {
    void setView(View view);

    void alert();

    void logRetrofit();

    interface View extends BaseViewPresenter{

    }
}
