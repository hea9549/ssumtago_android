package com.lovepago.ssumtago.Presentation.Presenter;

import android.util.Log;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.lovepago.ssumtago.Domain.StringFormatChecker;
import com.lovepago.ssumtago.Presentation.Activity.HomeActivity;
import com.lovepago.ssumtago.Presentation.Activity.JoinActivity;
import com.lovepago.ssumtago.Service.LoginService.LoginService;
import com.lovepago.ssumtago.Service.LoginService.NormalLoginService;
import com.lovepago.ssumtago.Service.UserService;

import org.json.JSONObject;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Qualifier;
import javax.inject.Singleton;

import dagger.Provides;
import retrofit2.Retrofit;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.Exceptions;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ParkHaeSung on 2017-05-15.
 */

public class MainActivityPresenterImpl implements MainActivityPresenter {
    private Retrofit retrofit;
    private View view;
    private UserService userService;

    @Inject
    public MainActivityPresenterImpl(Retrofit retrofit,UserService userService){
        this.retrofit = retrofit;
        this.userService = userService;
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void loginClick(String id, String pw) {
        if(!StringFormatChecker.isValidEmail(id)){
            view.makeToast("입력을 확인해주세요");
            return;
        }
        view.makeDialog("잠시만 기다려주세요...");
        userService.login(id,pw,"email")
                .doOnError(error->{
                    view.cancelDialog();
                    view.makeToast("로그인에러... "+error.getMessage());
                })
                .subscribe(user->{
                    view.cancelDialog();
                    view.navigateActivity(HomeActivity.class);
                });
    }

    @Override
    public void facebookLoginClick() {
        view.navigateActivity(HomeActivity.class);
    }

    @Override
    public void kakaoLoginClick() {
        view.navigateActivity(JoinActivity.class);
    }

    @Override
    public void onFacebookLoginSuccess(LoginResult loginResult) {
        view.makeDialog("잠시만 기다려주세요...");
        Log.e("페북토큰 = ",loginResult.getAccessToken().getToken());
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            String facebookEmail = "";
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    facebookEmail = object.getString("email");
                    Log.e("페북토큰 = ",loginResult.getAccessToken().getToken());
                    Log.e("페북이멜 = ",facebookEmail);
                }catch (Exception e){

                }
            userService.login(facebookEmail,loginResult.getAccessToken().getToken(),"facebook")
                        .subscribe(user->{
                            view.cancelDialog();
                            view.navigateActivity(HomeActivity.class);
                        },error->{
                            view.cancelDialog();
                            view.makeToast("로그인에러... "+error.getMessage());
                        });
            }
        });
        request.executeAsync();

    }
}
