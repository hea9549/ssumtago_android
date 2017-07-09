package com.lovepago.ssumtago.Presentation.Presenter;

import android.util.Log;

import com.lovepago.ssumtago.Presentation.Activity.MainActivity;
import com.lovepago.ssumtago.Service.UserService;

import javax.inject.Inject;

/**
 * Created by ParkHaeSung on 2017-05-15.
 */

public class LoginActivityPresenterImpl implements LoginActivityPresenter{
    private String TAG = "LoginActivityPresImpl";
    private UserService userService;
    private View view;


    @Inject
    public LoginActivityPresenterImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }


}
