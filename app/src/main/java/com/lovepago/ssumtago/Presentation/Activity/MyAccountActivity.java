package com.lovepago.ssumtago.Presentation.Activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.lovepago.ssumtago.CustomClass.STGBaseActivity;
import com.lovepago.ssumtago.CustomClass.STGPreference;
import com.lovepago.ssumtago.Data.Model.User;
import com.lovepago.ssumtago.R;
import com.lovepago.ssumtago.STGApplication;
import com.lovepago.ssumtago.Service.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyAccountActivity extends STGBaseActivity {

    private final String TAG = "MyAccountActivity";
    @Inject
    UserService userService;
    @BindView(R.id.tv_myAccount_birth)
    TextView tv_birth;
    @BindView(R.id.tv_myAccount_id)
    TextView tv_id;
    @BindView(R.id.tv_myAccount_loginInfo)
    TextView tv_loginInfo;


    @Override
    public void STGOnCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_myaccount);
        ButterKnife.bind(this);
        STGApplication.getComponent().inject(this);
        if(userService.getUser()==null){
            makeToast("유저정보를 받아오는데 실패했습니다");
            finish();
        }
        User user = userService.getUser();
        tv_id.setText(user.getEmail());

        SimpleDateFormat strFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(strFormat.parse(user.getBirthday()));
            tv_birth.setText(calendar.get(Calendar.YEAR)+"년 "
                    +(calendar.get(Calendar.MONTH)+1)+"월 "
                    +calendar.get(Calendar.DAY_OF_MONTH)+"일 태어남");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_myAccount_close)
    void onCloseClick(){
        finish();
    }

    @OnClick(R.id.btn_myAccount_withdraw)
    void onWithDrawClick(){
        makeDialog("회원탈퇴 처리중입니다...");
        userService.withDraw()
                .subscribe(responseBody -> {
                    cancelDialog();
                    onLogOutClick();
                },fail->{
                    cancelDialog();
                    Log.e(TAG,"fail in withDraw, fail ="+fail.toString());
                    makeToast("회원탈퇴 처리에 실패했습니다.");
                });
    }

    @OnClick(R.id.btn_myAccount_logout)
    void onLogOutClick(){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(User.class).findAll().deleteAllFromRealm();
        realm.commitTransaction();
        STGPreference preference = new STGPreference(this);
        preference.put(STGPreference.PREF_LAST_SURVEYED,"");
        preference.put(STGPreference.PREF_ID,"");
        preference.put(STGPreference.PREF_PW,"");
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }


}
