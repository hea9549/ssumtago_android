package com.lovepago.ssumtago.Presentation.Presenter;


/**
 * Created by ParkHaeSung on 2017-05-15.
 */

public interface FacebookJoinActivityPresenter {
    void setView(View view);


    void register(String accountId, String token, String name, String sex, String birthday);

    interface View extends BaseViewPresenter{
        void joinSuccess();
    }
}
