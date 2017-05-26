package com.lovepago.ssumtago.Presentation.Presenter;

import android.util.Log;

import com.lovepago.ssumtago.Presentation.Activity.HomeActivity;
import com.lovepago.ssumtago.Service.LoginService.LoginService;
import com.lovepago.ssumtago.Service.LoginService.NormalLoginService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Qualifier;
import javax.inject.Singleton;

import dagger.Provides;
import retrofit2.Retrofit;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ParkHaeSung on 2017-05-15.
 */

public class MainActivityPresenterImpl implements MainActivityPresenter {

    private Retrofit retrofit;
    private View view;

    @Inject
    public MainActivityPresenterImpl(Retrofit retrofit){
        this.retrofit = retrofit;
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void loginClick(String id, String pw) {
        LoginService loginService = new NormalLoginService.Builder().setId(id).setPw(pw).build();
        if(!loginService.isAvailable()){
            view.makeToast("입력을 확인해주세요");
            return;
        }
        view.makeDialog("잠시만 기다려주세요...");
        Subscription loginSubscription = loginService.login()
                .subscribe(
                        user->{
                            view.cancelDialog();
                            view.navigateActivity(HomeActivity.class);
                        },
                        error->{
                            view.cancelDialog();
                            view.makeToast("로그인에러... "+error.getMessage());
                        });
    }

    @Override
    public void facebookLoginClick() {
        view.navigateActivity(HomeActivity.class);
    }

    @Override
    public void kakaoLoginClick() {
        view.makeToast("아직 안만들었쪄영 > <");
    }
}
