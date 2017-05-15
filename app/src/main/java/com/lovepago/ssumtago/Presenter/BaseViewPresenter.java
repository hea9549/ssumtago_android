package com.lovepago.ssumtago.Presenter;

/**
 * Created by ParkHaeSung on 2017-05-15.
 */

public interface BaseViewPresenter {
    void makeDialog(String message);
    void cancelDialog();
    void makeToast(String message);
}
