package com.lovepago.ssumtago.Presentation.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lovepago.ssumtago.CustomClass.STGBaseActivity;
import com.lovepago.ssumtago.Data.Model.User;
import com.lovepago.ssumtago.R;
import com.lovepago.ssumtago.Retrofit.ApiUser;
import com.lovepago.ssumtago.Retrofit.ServiceGenerator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ApiTestActivity extends STGBaseActivity {

    @BindView(R.id.tv_test_id)
    EditText tvTestId;
    @BindView(R.id.tv_test_pw)
    EditText tvTestPw;
    @BindView(R.id.btn_test_login)
    Button btnTestLogin;
    @BindView(R.id.btn_test_auth)
    Button btnTestAuth;

    User user = new User();

    @Override
    public void STGOnCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_api_test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_test_login)
    public void onLoginClick() {

        user.setEmail(tvTestId.getText().toString());
        user.setPassword(tvTestPw.getText().toString());
        user.setJoinType("email");
        ServiceGenerator.createService(ApiUser.class)
                .login(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(returnUser -> {
                    user = returnUser;
                    Toast.makeText(this, "로그인성공 =" + returnUser.getName(), Toast.LENGTH_SHORT).show();
                }, fail -> Log.e("테스트중", "로그인실패 = " + fail.toString()));
    }

    @OnClick(R.id.btn_test_auth)
    void onAuthClick() {
        ServiceGenerator.createService(ApiUser.class,user.getJwt())
                .getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(returnUser -> {
                    Toast.makeText(this, "Auth성공", Toast.LENGTH_SHORT).show();
                }, fail -> Toast.makeText(this, "Auth 실패", Toast.LENGTH_SHORT).show());
    }

}
