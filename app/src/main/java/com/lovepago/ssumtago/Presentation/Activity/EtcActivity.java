package com.lovepago.ssumtago.Presentation.Activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lovepago.ssumtago.CustomClass.STGBaseActivity;
import com.lovepago.ssumtago.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class EtcActivity extends STGBaseActivity{

    @Override
    public void STGOnCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_etc);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_etc_back)
    void onBackButtonClick(){
        finish();
    }

    @OnClick(R.id.btn_etc_devInfo)
    void onDevInfoClick(){
        popActivity(DeveloperInfoActivity.class);
    }

    @OnClick(R.id.btn_etc_lovepagoInfo)
    void onLovepagoInfoClick(){
        popActivity(AppInfoActivity.class);
    }

    @OnClick(R.id.btn_etc_mypage)
    void onMyPageClick(){
        popActivity(MyAccountActivity.class);
    }
}
