package com.lovepago.ssumtago.Presentation.Presenter;

import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.lovepago.ssumtago.Presentation.Activity.FacebookJoinActivity;
import com.lovepago.ssumtago.Presentation.Activity.LobyActivity;
import com.lovepago.ssumtago.Presentation.Activity.MainActivity;
import com.lovepago.ssumtago.Presentation.Activity.StartGuideActivity;
import com.lovepago.ssumtago.Service.UserService;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import retrofit2.adapter.rxjava.HttpException;


/**
 * Created by ParkHaeSung on 2017-05-15.
 */

public class LoginActivityPresenterImpl implements LoginActivityPresenter {
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

    @Override
    public void onLoginClick(String email, String pw, String joinType) {
        view.makeDialog("잠시만기다료주세용");
        userService.login(email, pw, joinType)
                .subscribe(success -> {
                    view.cancelDialog();
                    if (!success.isSurveyedYN()) view.navigateActivity(StartGuideActivity.class);
                    else view.navigateActivity(LobyActivity.class);
                }, fail -> {
                    view.cancelDialog();
                    if (fail instanceof HttpException) {
                        HttpException exception = (HttpException) fail;
                        //회원가입 안되어있을시 bad requst 400
                        if (exception.code() == 400) {
                            view.alertCheckIdPw();
                        }
                    }else{
                        view.makeToast("알수없는 오류가 발생했습니다.");
                        Log.e(TAG,"fail in login = "+fail.toString());
                    }
                });
    }

    @Override
    public void onFindPwClick() {

    }

    @Override
    public void onFacebookLoginSuccess(LoginResult loginResult) {
        view.makeDialog("잠시만 기다려주세요...");
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                (object, response) -> {
                    Log.e(TAG,"face token = "+loginResult.getAccessToken().getToken());
                    try {
                        userService.login(object.getString("id"), loginResult.getAccessToken().getToken(), "facebook")
                                .subscribe(success -> {
                                    view.cancelDialog();
                                    if (!success.isSurveyedYN())
                                        view.navigateActivity(StartGuideActivity.class);
                                    else view.navigateActivity(LobyActivity.class);
                                }, error -> {
                                    view.cancelDialog();
                                    if (error instanceof HttpException) {
                                        HttpException exception = (HttpException) error;
                                        //회원가입 안되어있을시 bad requst 400
                                        if (exception.code() == 400) {
                                            try {
                                                view.navigateActivity(FacebookJoinActivity.class, "accountId", object.getString("id"), "token", loginResult.getAccessToken().getToken());
                                            } catch (Exception e) {
                                                view.makeToast("페이스북 계정에 아이디 정보가 없습니다. 일반 가입을 진행해주세요");
                                                Log.e(TAG, "facebook register fail : " + e.toString());
                                            }
                                        }
                                        Log.e(TAG, "error http code = " + ((HttpException) error).code());
                                    } else {
                                        Log.e(TAG, "error in login facebook = " + error.toString());
                                    }
                                });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender");
        request.setParameters(parameters);
        request.executeAsync();
    }


}
