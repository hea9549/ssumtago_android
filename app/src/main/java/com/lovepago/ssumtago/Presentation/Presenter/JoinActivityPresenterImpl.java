package com.lovepago.ssumtago.Presentation.Presenter;

import android.util.Log;

import com.lovepago.ssumtago.Data.Model.RequestAnswer;
import com.lovepago.ssumtago.Presentation.Activity.MainActivity;
import com.lovepago.ssumtago.Service.SurveyService;
import com.lovepago.ssumtago.Service.UserService;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by ParkHaeSung on 2017-05-15.
 */

public class JoinActivityPresenterImpl implements JoinActivityPresenter{
    private String TAG = "HomeActivityPresImpl";
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
    public void register(String email, String pw, String name, String sex, int age){
        view.makeDialog("회원가입 시도중");
        userService.register(email,pw,"email",name,sex,age)
                .subscribe(success->{
                    view.cancelDialog();
                    view.makeToast("회원가입 성공");
                    view.navigateActivity(MainActivity.class);
                },error->{
                    view.cancelDialog();
                    view.makeToast("회원가입 실패");
                    Log.e("joinPresenterImpl","message : "+error.getMessage());
                });
    }

}
