package com.lovepago.ssumtago.Presenter;

/**
 * Created by ParkHaeSung on 2017-05-15.
 */

public interface MainActivityPresenter {
    void setView(View view);

    void alert();

    interface View extends BaseViewPresenter{

    }
}
