package com.lovepago.ssumtago.Presentation.Presenter;

import android.util.Log;

import com.lovepago.ssumtago.Presentation.Activity.LobyActivity;
import com.lovepago.ssumtago.Presentation.Activity.MainActivity;
import com.lovepago.ssumtago.Presentation.Activity.StartGuideActivity;
import com.lovepago.ssumtago.Service.UserService;

import javax.inject.Inject;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by ParkHaeSung on 2017-05-15.
 */

public class JoinActivityPresenterImpl implements JoinActivityPresenter{
    private String TAG = "joinPresenterImpl";
    private UserService userService;
    private View view;


    @Inject
    public JoinActivityPresenterImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void register(String email, String pw, String name, String sex, String birthday){
        view.makeDialog("회원가입 시도중");
        userService.register(email,pw,"email",name,sex,birthday)
                .subscribe(success->{
                    view.cancelDialog();
                    view.makeToast("회원가입 성공");
                    view.joinSuccess();
                },error->{
                    view.cancelDialog();
                    if (error instanceof HttpException){
                        HttpException exception = (HttpException)error;
                        if (exception.code() == 400){
                            view.makeToast("이미 존재하는 아이디입니다.");
                            return;
                        }
                    }
                    view.makeToast("회원가입 실패");
                    Log.e(TAG,"message : "+error.getMessage());
                });
    }

}
