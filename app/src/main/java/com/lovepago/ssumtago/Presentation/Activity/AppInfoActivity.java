package com.lovepago.ssumtago.Presentation.Activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.lovepago.ssumtago.CustomClass.STGBaseActivity;
import com.lovepago.ssumtago.R;
import com.lovepago.ssumtago.STGApplication;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppInfoActivity extends STGBaseActivity{
    @BindView(R.id.tv_appInfo_accuracy)
    TextView tv_accuracy;
    @BindView(R.id.tv_appInfo_learningDataSet)
    TextView tv_learningDataSet;
    @BindView(R.id.tv_appInfo_versionInfo)
    TextView tv_versionInfo;
    @Override
    public void STGOnCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_ssumtago_explain);
        ButterKnife.bind(this);
        setVersionInfo();
        setAccuracy(80);
        setDataSet(462);
    }

    private void setVersionInfo(){
        tv_versionInfo.setText("버전정보 : "
                +STGApplication.majorVersion+"."
                + STGApplication.subVersion+"."
                + STGApplication.minorVersion);
    }

    public void setAccuracy(int percent){
        tv_accuracy.setText("정확도 : "+percent+"%");
    }

    public void setDataSet(int dataNum){
        tv_learningDataSet.setText("학습데이터 수 : "+dataNum);
    }
}

