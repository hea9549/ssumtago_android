package com.lovepago.ssumtago.Presentation.Presenter;

/**
 * Created by ParkHaeSung on 2017-05-15.
 */

public interface HomeActivityPresenter {
    void setView(View view);
    void init();
    interface View extends BaseViewPresenter{
    }
}
