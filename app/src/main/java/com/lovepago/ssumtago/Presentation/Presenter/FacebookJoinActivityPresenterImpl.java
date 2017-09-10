package com.lovepago.ssumtago.Presentation.Presenter;

import android.util.Log;

import com.lovepago.ssumtago.Service.UserService;

import javax.inject.Inject;

/**
 * Created by ParkHaeSung on 2017-05-15.
 */

public class FacebookJoinActivityPresenterImpl implements FacebookJoinActivityPresenter{
    private String TAG = "fbJoinPresenter";
    private UserService userService;
    private View view;


    @Inject
    public FacebookJoinActivityPresenterImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void register(String accountId, String token, String name, String sex, String birthday){
        view.makeDialog("회원가입 시도중");
        userService.register(accountId,token,"facebook",name,sex,birthday)
                .subscribe(success->{
                    view.cancelDialog();
                    view.joinSuccess();
                },error->{
                    view.cancelDialog();
                    view.makeToast("회원가입 실패");
                    Log.e(TAG,"message : "+error.getMessage());
                });
    }

}
