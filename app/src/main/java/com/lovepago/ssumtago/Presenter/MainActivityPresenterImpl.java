package com.lovepago.ssumtago.Presenter;

/**
 * Created by ParkHaeSung on 2017-05-15.
 */

public class MainActivityPresenterImpl implements MainActivityPresenter {
    private View view;
    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void alert(){
        view.makeToast("base View Presenter 테스트");
    }
}
